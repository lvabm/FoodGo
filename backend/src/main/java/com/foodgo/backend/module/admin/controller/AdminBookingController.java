package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminBookingService;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/bookings")
@RequiredArgsConstructor
@Tag(name = "Admin | Booking Management", description = "Quản lý Booking cho Admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM_ADMIN')")
public class AdminBookingController {

  private final AdminBookingService service;

  @GetMapping("search")
  @Operation(summary = "Xem toàn bộ Booking trên hệ thống")
  public Page<BookingResponse> getAllBookings(
      @ModelAttribute BookingFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết bất kỳ đơn nào")
  public BookingResponse getDetail(@PathVariable UUID id) {
    return service.getDetail(id);
  }

  @DeleteMapping("/{id}/force-cancel")
  @Operation(summary = "Hủy nóng đơn đặt bàn (Force Cancel)")
  public void forceCancel(@PathVariable UUID id) {
    service.forceCancel(id);
  }
}
