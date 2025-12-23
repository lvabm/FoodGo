package com.foodgo.backend.module.booking.service;

import com.foodgo.backend.common.base.service.CreatableService;
import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.common.base.service.UpdatableService;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;

import java.util.UUID;

public interface BookingService
    extends ReadableService<UUID, BookingFilterRequest, BookingResponse>,
        CreatableService<BookingCreateRequest, BookingResponse>,
        UpdatableService<UUID, BookingUpdateRequest, BookingResponse> {
  // API dành riêng cho User: Hủy đơn của chính mình
  BookingResponse cancelBooking(UUID bookingId, String reason);

  // Owner/Admin duyệt đơn
  BookingResponse confirmBooking(UUID bookingId);

  // Owner/Admin từ chối đơn
  BookingResponse rejectBooking(UUID bookingId, String reason);

  // User check-in (user confirms they've arrived)
  BookingResponse userCheckIn(UUID bookingId);

  // Owner check-in (owner confirms customer arrived)
  BookingResponse ownerCheckIn(UUID bookingId);
}
