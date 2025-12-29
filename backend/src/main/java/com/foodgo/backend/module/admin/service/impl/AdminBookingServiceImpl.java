package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminBookingService;
import com.foodgo.backend.module.booking.dto.criteria.BookingSpecification;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBookingServiceImpl
    extends BaseServiceImpl<
        Booking,
        UUID,
        BookingCreateRequest,
        BookingUpdateRequest,
        BookingFilterRequest,
        BookingResponse>
    implements AdminBookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;
  private final PaymentRepository paymentRepository;
  private final NotificationService notificationService;

  private final String bookingEntityName = EntityName.BOOKING.getFriendlyName() + " (Admin)";

  @Override
  protected JpaRepository<Booking, UUID> getRepository() {
    return bookingRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Booking> getSpecRepository() {
    return bookingRepository;
  }

  @Override
  protected BaseMapper<Booking, BookingCreateRequest, BookingUpdateRequest, BookingResponse>
      getMapper() {
    return bookingMapper;
  }

  @Override
  protected String getEntityName() {
    return bookingEntityName;
  }

  @Override
  protected void ensurePermission(Booking entity) {
    // Chỉ cần đảm bảo người gọi là Admin
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException("Chỉ Admin mới có quyền thao tác này.");
    }
  }

  @Override
  protected Specification<Booking> buildSpecification(BookingFilterRequest filter) {
    return new BookingSpecification(filter);
  }

  @Override
  @Transactional
  public BookingResponse forceCancel(UUID id, String reason) {
    Booking booking = findByIdOrThrow(id);
    
    if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.REJECTED) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Đơn này đã bị hủy trước đó.");
    }
    
    booking.setStatus(BookingStatus.CANCELLED);
    String adminNote = "Đã bị hủy bởi Quản trị viên" + (reason != null ? ". Lý do: " + reason : "");
    booking.setOwnerNotes(adminNote);
    
    // Process refund
    processRefund(booking);
    
    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho user và owner
    try {
      if (booking.getUser() != null) {
        notificationService.createNotificationAsync(
            booking.getUser().getId(),
            "BOOKING_CANCELLED",
            "Đơn đặt bàn đã bị hủy",
            String.format("Đơn đặt bàn #%s của bạn đã bị Admin hủy. Lý do: %s", 
                booking.getId().toString().substring(0, 8), reason != null ? reason : "Không rõ"),
            booking.getId()
        );
      }
      
      if (booking.getOutlet() != null && booking.getOutlet().getOwner() != null) {
        notificationService.createNotificationAsync(
            booking.getOutlet().getOwner().getId(),
            "BOOKING_CANCELLED",
            "Đơn đặt bàn đã bị hủy",
            String.format("Đơn đặt bàn #%s tại outlet của bạn đã bị Admin hủy.", 
                booking.getId().toString().substring(0, 8)),
            booking.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for cancelled booking {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã hủy đơn thành công (Quyền Admin).");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse forceConfirm(UUID id) {
    Booking booking = findByIdOrThrow(id);
    
    if (booking.getStatus() == BookingStatus.CONFIRMED) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Đơn này đã được xác nhận trước đó.");
    }
    
    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Không thể xác nhận đơn đã hoàn thành.");
    }
    
    booking.setStatus(BookingStatus.CONFIRMED);
    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho user
    try {
      if (booking.getUser() != null) {
        notificationService.createNotificationAsync(
            booking.getUser().getId(),
            "BOOKING_CONFIRMED",
            "Đơn đặt bàn đã được xác nhận",
            String.format("Đơn đặt bàn của bạn tại %s đã được Admin xác nhận. Thời gian: %s %s", 
                booking.getOutlet().getName(),
                booking.getBookingDate(),
                booking.getBookingTime()),
            booking.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for confirmed booking {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã xác nhận đơn thành công (Quyền Admin).");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse forceReject(UUID id, String reason) {
    Booking booking = findByIdOrThrow(id);
    
    if (booking.getStatus() == BookingStatus.REJECTED) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Đơn này đã bị từ chối trước đó.");
    }
    
    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Không thể từ chối đơn đã hoàn thành.");
    }
    
    booking.setStatus(BookingStatus.REJECTED);
    booking.setOwnerNotes("Đã bị từ chối bởi Quản trị viên. Lý do: " + (reason != null ? reason : "Không rõ"));
    
    // Process refund
    processRefund(booking);
    
    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho user
    try {
      if (booking.getUser() != null) {
        notificationService.createNotificationAsync(
            booking.getUser().getId(),
            "BOOKING_REJECTED",
            "Đơn đặt bàn đã bị từ chối",
            String.format("Đơn đặt bàn của bạn tại %s đã bị Admin từ chối. Lý do: %s", 
                booking.getOutlet().getName(), reason != null ? reason : "Không rõ"),
            booking.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for rejected booking {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã từ chối đơn thành công (Quyền Admin).");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse updateBooking(UUID id, com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest request) {
    Booking booking = findByIdOrThrow(id);
    
    // Admin có thể update bất cứ booking nào
    if (request.bookingDate() != null) {
      booking.setBookingDate(request.bookingDate());
    }
    if (request.bookingTime() != null) {
      booking.setBookingTime(request.bookingTime());
    }
    if (request.numberOfGuests() != null) {
      booking.setNumberOfGuests(request.numberOfGuests());
    }
    if (request.userNotes() != null) {
      booking.setUserNotes(request.userNotes());
    }
    
    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho user và owner
    try {
      if (booking.getUser() != null) {
        notificationService.createNotificationAsync(
            booking.getUser().getId(),
            "BOOKING_UPDATED",
            "Đơn đặt bàn đã được cập nhật",
            String.format("Đơn đặt bàn #%s của bạn đã được Admin cập nhật.", 
                booking.getId().toString().substring(0, 8)),
            booking.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for updated booking {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã cập nhật đơn thành công (Quyền Admin).");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public List<BookingResponse> bulkCancel(List<UUID> ids, String reason) {
    if (ids == null || ids.isEmpty()) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Danh sách ID không được để trống.");
    }
    
    List<Booking> bookings = bookingRepository.findAllById(ids);
    
    if (bookings.size() != ids.size()) {
      throw new ResourceNotFoundException("Một số booking không tồn tại.");
    }
    
    bookings.forEach(booking -> {
      if (booking.getStatus() != BookingStatus.CANCELLED && booking.getStatus() != BookingStatus.REJECTED) {
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setOwnerNotes("Đã bị hủy bởi Quản trị viên" + (reason != null ? ". Lý do: " + reason : ""));
        processRefund(booking);
      }
    });
    
    List<Booking> saved = bookingRepository.saveAll(bookings);
    
    // Gửi thông báo
    saved.forEach(booking -> {
      try {
        if (booking.getUser() != null) {
          notificationService.createNotificationAsync(
              booking.getUser().getId(),
              "BOOKING_CANCELLED",
              "Đơn đặt bàn đã bị hủy",
              String.format("Đơn đặt bàn #%s của bạn đã bị Admin hủy.", 
                  booking.getId().toString().substring(0, 8)),
              booking.getId()
          );
        }
      } catch (Exception e) {
        log.warn("Failed to send notification for cancelled booking {}: {}", booking.getId(), e.getMessage());
      }
    });
    
    SuccessMessageContext.setMessage(String.format("Đã hủy %d đơn thành công.", saved.size()));
    
    return saved.stream()
        .map(bookingMapper::toResponse)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  @Transactional
  public List<BookingResponse> bulkConfirm(List<UUID> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new com.foodgo.backend.common.exception.BadRequestException("Danh sách ID không được để trống.");
    }
    
    List<Booking> bookings = bookingRepository.findAllById(ids);
    
    if (bookings.size() != ids.size()) {
      throw new ResourceNotFoundException("Một số booking không tồn tại.");
    }
    
    bookings.forEach(booking -> {
      if (booking.getStatus() != BookingStatus.CONFIRMED && booking.getStatus() != BookingStatus.COMPLETED) {
        booking.setStatus(BookingStatus.CONFIRMED);
      }
    });
    
    List<Booking> saved = bookingRepository.saveAll(bookings);
    
    // Gửi thông báo
    saved.forEach(booking -> {
      try {
        if (booking.getUser() != null) {
          notificationService.createNotificationAsync(
              booking.getUser().getId(),
              "BOOKING_CONFIRMED",
              "Đơn đặt bàn đã được xác nhận",
              String.format("Đơn đặt bàn của bạn tại %s đã được Admin xác nhận.", 
                  booking.getOutlet().getName()),
              booking.getId()
          );
        }
      } catch (Exception e) {
        log.warn("Failed to send notification for confirmed booking {}: {}", booking.getId(), e.getMessage());
      }
    });
    
    SuccessMessageContext.setMessage(String.format("Đã xác nhận %d đơn thành công.", saved.size()));
    
    return saved.stream()
        .map(bookingMapper::toResponse)
        .collect(java.util.stream.Collectors.toList());
  }

  /**
   * Xử lý hoàn tiền (Refund) khi booking bị hủy hoặc từ chối
   */
  private void processRefund(Booking booking) {
    try {
      Optional<com.foodgo.backend.module.payment.entity.Payment> paymentOpt = paymentRepository.findLatestByRelatedIdAndType(
          booking.getId().toString(),
          com.foodgo.backend.common.constant.PaymentType.BOOKING
      );

      if (paymentOpt.isPresent()) {
        com.foodgo.backend.module.payment.entity.Payment payment = paymentOpt.get();
        
        if (payment.getPaymentStatus() == com.foodgo.backend.common.constant.PaymentStatus.COMPLETED) {
          payment.setPaymentStatus(com.foodgo.backend.common.constant.PaymentStatus.REFUNDED);
          paymentRepository.save(payment);
          
          log.info("Refunded payment {} for booking {}", payment.getId(), booking.getId());
        }
      }
    } catch (Exception e) {
      log.error("Failed to process refund for booking {}: {}", booking.getId(), e.getMessage(), e);
    }
  }
}
