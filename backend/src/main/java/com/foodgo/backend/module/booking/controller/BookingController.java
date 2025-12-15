package com.foodgo.backend.module.booking.controller;

import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "API Đặt bàn cho User và Owner")
public class BookingController {

  private final BookingService service;

  @PostMapping
  @Operation(summary = "Tạo đơn đặt bàn mới (Chỉ Member)")
  public BookingResponse createBooking(@RequestBody @Valid BookingCreateRequest request) {
    return service.create(request);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết đơn đặt bàn")
  public BookingResponse getDetail(@PathVariable UUID id) {
    return service.getDetail(id);
  }

  @GetMapping("/me")
  @Operation(summary = "Danh sách đơn đặt bàn (Của tôi / Của quán tôi)")
  public Page<BookingResponse> getMyBookings(
      @ModelAttribute BookingFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật thông tin đơn (Patch)")
  public BookingResponse updateBooking(
      @PathVariable UUID id, @RequestBody BookingUpdateRequest request) {
    return service.update(id, request);
  }

  @DeleteMapping("/{id}/cancel")
  @Operation(summary = "Hủy đơn đặt bàn")
  public BookingResponse cancelBooking(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Khách yêu cầu hủy") String reason) {
    return service.cancelBooking(id, reason);
  }
}
