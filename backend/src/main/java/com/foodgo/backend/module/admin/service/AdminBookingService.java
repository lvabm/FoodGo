package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;

import java.util.UUID;

public interface AdminBookingService
    extends ReadableService<UUID, BookingFilterRequest, BookingResponse> {
  // Admin quyền lực: Force Cancel bất chấp trạng thái
  void forceCancel(UUID id);
}
