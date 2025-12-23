package com.foodgo.backend.module.booking.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.*;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.booking.dto.criteria.BookingSpecification;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.booking.service.BookingService;
import com.foodgo.backend.module.membership.repository.UserMembershipRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl
    extends BaseServiceImpl<
        Booking,
        UUID,
        BookingCreateRequest,
        BookingUpdateRequest,
        BookingFilterRequest,
        BookingResponse>
    implements BookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;
  private final UserMembershipRepository userMembershipRepository;
  private final OutletRepository outletRepository;
  private final UserAccountRepository userAccountRepository;
  private final PaymentRepository paymentRepository;

  private final String bookingEntityName = EntityName.BOOKING.getFriendlyName();

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
    if (SecurityContext.isAdmin()) return;

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (entity.getUser().getId().equals(currentUserId)) return;
    if (entity.getOutlet().getOwner().getId().equals(currentUserId)) return;

    throw new ForbiddenException("Bạn không có quyền truy cập vào đơn đặt bàn này.");
  }

  @Override
  protected Specification<Booking> buildSpecification(BookingFilterRequest filter) {
    return new BookingSpecification(filter);
  }

  @Override
  protected void validateBeforeCreate(BookingCreateRequest request) {
    if (SecurityContext.isAdmin()) return;

    UUID userId = SecurityContext.getCurrentUserId();

    // Check Membership: Require an active membership of type USER or OWNER (check package, not role)
    boolean hasUserMembership =
        userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
            userId, PlanType.USER);
    boolean hasOwnerMembership =
        userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
            userId, PlanType.OWNER);

    if (!hasUserMembership && !hasOwnerMembership) {
      throw new ForbiddenException("Chức năng Đặt bàn chỉ dành cho Hội viên. Vui lòng nâng cấp!");
    }

    if (!outletRepository.existsById(request.outletId())) {
      throw new ResourceNotFoundException("Cửa hàng không tồn tại.");
    }

    // Additional business rule: If user has OWNER role, prevent booking at their own outlet
    boolean isOwnerRole = SecurityContext.hasRole(com.foodgo.backend.common.constant.RoleType.ROLE_OWNER.getName());
    if (isOwnerRole) {
      var outletOpt = outletRepository.findById(request.outletId());
      if (outletOpt.isPresent()) {
        var outlet = outletOpt.get();
        if (outlet.getOwner() != null && outlet.getOwner().getId().equals(userId)) {
          throw new ForbiddenException("Chủ quán không thể đặt bàn tại chính quán của mình. Vui lòng chọn quán khác.");
        }
      }
    }
  }

  @Override
  @Transactional
  public BookingResponse create(BookingCreateRequest request) {
    validateBeforeCreate(request);

    // 1. Tạo Booking Entity
    Booking entity = bookingMapper.toEntity(request);
    entity.setUser(userAccountRepository.getReferenceById(SecurityContext.getCurrentUserId()));
    entity.setOutlet(outletRepository.getReferenceById(request.outletId()));
    entity.setStatus(BookingStatus.PENDING); // Đặt bàn luôn bắt đầu là Pending chờ duyệt

    // 2. [PAYMENT MANUAL LOGIC] - Tự động tính và tạo Payment
    // Logic: Tiền cọc = Số khách * 20
    BigDecimal depositRate = new BigDecimal("20000"); // Định mức cọc: 20 (đơn vị tiền tệ)
    BigDecimal depositAmount = depositRate.multiply(BigDecimal.valueOf(request.numberOfGuests()));
    entity.setDepositAmount(depositAmount);

    Booking savedBooking = bookingRepository.save(entity);

    // Luôn tạo Payment nếu số tiền > 0 (Khách đặt ít nhất 1 người -> deposit >= 20)
    if (depositAmount.compareTo(BigDecimal.ZERO) > 0) {
      Payment payment =
          Payment.builder()
              .amount(depositAmount)
              .paymentMethod(PaymentMethod.BANK_TRANSFER) // Giả lập chuyển khoản/tiền mặt
              .paymentStatus(PaymentStatus.COMPLETED) // Ghi nhận đã thu tiền
              .transactionId("BOOKING_" + System.currentTimeMillis())
              .type(PaymentType.BOOKING)
              .relatedId(savedBooking.getId().toString()) // Link tới Booking vừa tạo
              .build();

      paymentRepository.save(payment);
    }

    SuccessMessageContext.setMessage("Đặt bàn thành công! Mã đơn: " + savedBooking.getId());
    return bookingMapper.toResponse(savedBooking);
  }

  @Override
  protected void validateBeforeUpdate(UUID id, BookingUpdateRequest request) {
    Booking booking = findByIdOrThrow(id);
    ensurePermission(booking);
    if (booking.getStatus() == BookingStatus.COMPLETED
        || booking.getStatus() == BookingStatus.CANCELLED) {
      throw new BadRequestException("Không thể chỉnh sửa đơn đã hoàn thành hoặc đã hủy.");
    }
  }

  @Override
  @Transactional
  public BookingResponse cancelBooking(UUID bookingId, String reason) {
    Booking booking = findByIdOrThrow(bookingId);
    ensurePermission(booking);

    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new BadRequestException("Không thể hủy đơn đã hoàn tất.");
    }

    booking.setStatus(BookingStatus.CANCELLED);
    booking.setUserNotes(booking.getUserNotes() + " [Lý do hủy: " + reason + "]");

    // TODO: Nếu cần hoàn tiền (Refund), có thể update trạng thái Payment tại đây.

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã hủy đơn đặt bàn thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse confirmBooking(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);
    ensurePermission(booking); // Chỉ Owner của quán hoặc Admin mới được duyệt

    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể duyệt các đơn đang chờ (PENDING).");
    }

    booking.setStatus(BookingStatus.CONFIRMED);
    Booking saved = bookingRepository.save(booking);

    // TODO: Gửi thông báo (Notification) cho User là đơn đã được duyệt
    SuccessMessageContext.setMessage("Duyệt đơn đặt bàn thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse rejectBooking(UUID bookingId, String reason) {
    Booking booking = findByIdOrThrow(bookingId);
    ensurePermission(booking);

    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể từ chối các đơn đang chờ (PENDING).");
    }

    booking.setStatus(BookingStatus.REJECTED);
    booking.setOwnerNotes(reason);

    // Logic hoàn tiền (Refund) nếu cần thiết (Manual Payment -> Có thể ghi chú là cần hoàn tiền)

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã từ chối đơn đặt bàn.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse userCheckIn(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!booking.getUser().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Bạn không có quyền xác nhận tới quán cho đơn này.");
    }

    if (booking.getStatus() != BookingStatus.CONFIRMED) {
      throw new BadRequestException("Chỉ có thể check-in cho đơn đã được xác nhận.");
    }

    if (!booking.getBookingDate().equals(LocalDate.now())) {
      throw new BadRequestException("Chỉ có thể check-in trong ngày đặt bàn.");
    }

    // Idempotent: nếu đã check-in rồi thì trả về success
    if (booking.getUserCheckedInAt() != null) {
      return bookingMapper.toResponse(booking);
    }

    booking.setUserCheckedInAt(Instant.now());

    if (booking.getOwnerCheckedInAt() != null && booking.getStatus() == BookingStatus.CONFIRMED) {
      booking.setStatus(BookingStatus.COMPLETED);
    }

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Bạn đã check-in thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse ownerCheckIn(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!booking.getOutlet().getOwner().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Bạn không có quyền xác nhận khách tới cho đơn này.");
    }

    if (booking.getStatus() != BookingStatus.CONFIRMED) {
      throw new BadRequestException("Chỉ có thể xác nhận khách tới cho đơn đã được xác nhận.");
    }

    if (!booking.getBookingDate().equals(LocalDate.now())) {
      throw new BadRequestException("Chỉ có thể xác nhận khách trong ngày đặt bàn.");
    }

    // Idempotent
    if (booking.getOwnerCheckedInAt() != null) {
      return bookingMapper.toResponse(booking);
    }

    booking.setOwnerCheckedInAt(Instant.now());

    if (booking.getUserCheckedInAt() != null && booking.getStatus() == BookingStatus.CONFIRMED) {
      booking.setStatus(BookingStatus.COMPLETED);
    }

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã xác nhận khách đã tới.");
    return bookingMapper.toResponse(saved);
  }
}
