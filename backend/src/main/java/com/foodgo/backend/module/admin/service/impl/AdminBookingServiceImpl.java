package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.module.admin.service.AdminBookingService;
import com.foodgo.backend.module.booking.dto.criteria.BookingSpecification;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    // Admin has full access
  }

  @Override
  protected Specification<Booking> buildSpecification(BookingFilterRequest filter) {
    return new BookingSpecification(filter);
  }

  @Override
  @Transactional
  public void forceCancel(UUID id) {
    Booking booking = findByIdOrThrow(id);
    booking.setStatus(BookingStatus.CANCELLED);
    booking.setOwnerNotes("Đã bị hủy bởi Quản trị viên.");
    bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã hủy đơn thành công (Quyền Admin).");
  }
}
