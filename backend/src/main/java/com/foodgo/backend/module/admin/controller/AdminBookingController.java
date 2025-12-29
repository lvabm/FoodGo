package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminBookingService;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/bookings")
@RequiredArgsConstructor
@Tag(name = "Admin | Booking Management", description = "Quản lý Booking cho Admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM_ADMIN')")
public class AdminBookingController {

  private final AdminBookingService adminBookingService;

  @GetMapping("search")
  @Operation(summary = "Xem toàn bộ Booking trên hệ thống")
  public Page<BookingResponse> getAllBookings(
      @ModelAttribute BookingFilterRequest filter, Pageable pageable) {
    return adminBookingService.getPage(filter, pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết bất kỳ đơn nào")
  public BookingResponse getDetail(@PathVariable UUID id) {
    return adminBookingService.getDetail(id);
  }

  @DeleteMapping("/{id}/force-cancel")
  @Operation(summary = "Hủy nóng đơn đặt bàn (Force Cancel)")
  public BookingResponse forceCancel(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminBookingService.forceCancel(id, reason);
  }

  @PostMapping("/{id}/force-confirm")
  @Operation(summary = "Xác nhận đơn đặt bàn (Force Confirm)")
  public BookingResponse forceConfirm(@PathVariable UUID id) {
    return adminBookingService.forceConfirm(id);
  }

  @DeleteMapping("/{id}/force-reject")
  @Operation(summary = "Từ chối đơn đặt bàn (Force Reject)")
  public BookingResponse forceReject(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminBookingService.forceReject(id, reason);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật thông tin booking (Admin)")
  public BookingResponse updateBooking(
      @PathVariable UUID id,
      @RequestBody @Valid com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest request) {
    return adminBookingService.updateBooking(id, request);
  }

  @PostMapping("/bulk/cancel")
  @Operation(summary = "Hủy nhiều bookings (Bulk Cancel)")
  public List<BookingResponse> bulkCancel(
      @RequestBody @Valid BulkOperationRequest request,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminBookingService.bulkCancel(request.ids(), reason);
  }

  @PostMapping("/bulk/confirm")
  @Operation(summary = "Xác nhận nhiều bookings (Bulk Confirm)")
  public List<BookingResponse> bulkConfirm(@RequestBody @Valid BulkOperationRequest request) {
    return adminBookingService.bulkConfirm(request.ids());
  }

  // DTO for bulk operations
  public record BulkOperationRequest(
      @jakarta.validation.constraints.NotEmpty(message = "Danh sách ID không được để trống")
      List<UUID> ids
  ) {}
}
