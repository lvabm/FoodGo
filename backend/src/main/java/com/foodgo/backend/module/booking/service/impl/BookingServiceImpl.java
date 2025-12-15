package com.foodgo.backend.module.booking.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.PlanType;
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
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    boolean isMember =
        userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
            userId, PlanType.USER);
    if (!isMember) {
      throw new ForbiddenException("Chức năng Đặt bàn chỉ dành cho Hội viên. Vui lòng nâng cấp!");
    }
    if (!outletRepository.existsById(request.outletId())) {
      throw new ResourceNotFoundException("Cửa hàng không tồn tại.");
    }
  }

  @Override
  @Transactional
  public BookingResponse create(BookingCreateRequest request) {
    validateBeforeCreate(request);

    Booking entity = bookingMapper.toEntity(request);
    entity.setUser(userAccountRepository.getReferenceById(SecurityContext.getCurrentUserId()));
    entity.setOutlet(outletRepository.getReferenceById(request.outletId()));
    entity.setStatus(BookingStatus.PENDING);

    Booking saved = bookingRepository.save(entity);
    SuccessMessageContext.setMessage("Đặt bàn thành công! Mã đơn: " + saved.getId());
    return bookingMapper.toResponse(saved);
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

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã hủy đơn đặt bàn thành công.");
    return bookingMapper.toResponse(saved);
  }
}
