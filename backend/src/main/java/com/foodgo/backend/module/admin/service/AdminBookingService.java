package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;

import java.util.List;
import java.util.UUID;

public interface AdminBookingService
    extends ReadableService<UUID, BookingFilterRequest, BookingResponse> {
  
  /**
   * Admin hủy đơn bất chấp trạng thái (Force Cancel)
   */
  BookingResponse forceCancel(UUID id, String reason);
  
  /**
   * Admin xác nhận đơn đặt bàn (Force Confirm)
   */
  BookingResponse forceConfirm(UUID id);
  
  /**
   * Admin từ chối đơn đặt bàn (Force Reject)
   */
  BookingResponse forceReject(UUID id, String reason);
  
  /**
   * Admin cập nhật thông tin booking
   */
  BookingResponse updateBooking(UUID id, com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest request);
  
  /**
   * Bulk cancel bookings
   */
  List<BookingResponse> bulkCancel(List<UUID> ids, String reason);
  
  /**
   * Bulk confirm bookings
   */
  List<BookingResponse> bulkConfirm(List<UUID> ids);
}
