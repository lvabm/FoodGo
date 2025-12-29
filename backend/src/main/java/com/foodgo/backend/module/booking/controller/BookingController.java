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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller cho quản lý đặt bàn (Bookings).
 * 
 * <p>Endpoints:
 * <ul>
 *   <li>POST /api/v1/bookings - Tạo đơn đặt bàn mới (Member only)</li>
 *   <li>GET /api/v1/bookings/{id} - Xem chi tiết đơn đặt bàn</li>
 *   <li>GET /api/v1/bookings/me - Danh sách đơn đặt bàn của user/owner</li>
 *   <li>PATCH /api/v1/bookings/{id} - Cập nhật thông tin đơn</li>
 *   <li>DELETE /api/v1/bookings/{id}/cancel - Hủy đơn đặt bàn</li>
 *   <li>POST /api/v1/bookings/{id}/confirm - [Owner] Duyệt đơn</li>
 *   <li>DELETE /api/v1/bookings/{id}/reject - [Owner] Từ chối đơn</li>
 *   <li>POST /api/v1/bookings/{id}/user-checkin - User check-in</li>
 *   <li>POST /api/v1/bookings/{id}/owner-checkin - [Owner] Xác nhận khách đã tới</li>
 * </ul>
 * 
 * <p>Business Rules:
 * <ul>
 *   <li>Chỉ Member mới có thể tạo booking</li>
 *   <li>Phải đặt trước ít nhất 2 giờ</li>
 *   <li>Tối đa đặt trước 30 ngày</li>
 *   <li>Phải hủy trước ít nhất 2 giờ so với giờ đặt bàn</li>
 *   <li>Owner không thể đặt bàn tại quán của chính mình</li>
 * </ul>
 * 
 * @author FoodGo Team
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "API Đặt bàn cho User và Owner")
public class BookingController {

  private final BookingService bookingService;

  /**
   * Tạo đơn đặt bàn mới.
   * 
   * <p>Yêu cầu:
   * <ul>
   *   <li>User phải có membership (Member)</li>
   *   <li>Outlet phải active</li>
   *   <li>Số khách không vượt quá capacity</li>
   *   <li>Thời gian đặt bàn phải trong giờ hoạt động</li>
   *   <li>Phải đặt trước ít nhất 2 giờ</li>
   *   <li>Tối đa đặt trước 30 ngày</li>
   * </ul>
   * 
   * @param request Thông tin đặt bàn (outletId, bookingDate, bookingTime, numberOfGuests)
   * @return BookingResponse với thông tin đơn đặt bàn đã tạo
   * @throws com.foodgo.backend.common.exception.BadRequestException nếu validation thất bại
   * @throws com.foodgo.backend.common.exception.DataConflictException nếu có booking trùng
   * @since 1.0.0
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Tạo đơn đặt bàn mới (Chỉ Member)")
  public BookingResponse createBooking(@RequestBody @Valid BookingCreateRequest request) {
    return bookingService.create(request);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết đơn đặt bàn")
  public BookingResponse getDetail(@PathVariable UUID id) {
    return bookingService.getDetail(id);
  }

  @GetMapping("/me")
  @Operation(summary = "Danh sách đơn đặt bàn (Của tôi / Của quán tôi)")
  public Page<BookingResponse> getMyBookings(
      @ModelAttribute BookingFilterRequest filter, Pageable pageable) {
    return bookingService.getPage(filter, pageable);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật thông tin đơn (Patch)")
  public BookingResponse updateBooking(
      @PathVariable UUID id, @RequestBody BookingUpdateRequest request) {
    return bookingService.update(id, request);
  }

  @DeleteMapping("/{id}/cancel")
  @Operation(summary = "Hủy đơn đặt bàn")
  public BookingResponse cancelBooking(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Khách yêu cầu hủy") String reason) {
    return bookingService.cancelBooking(id, reason);
  }

  @Operation(summary = "[Owner] Duyệt đơn đặt bàn")
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/{id}/confirm")
  public BookingResponse confirm(@PathVariable UUID id) {
    return bookingService.confirmBooking(id);
  }

  @Operation(summary = "[Owner] Từ chối đơn đặt bàn")
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/{id}/reject")
  public BookingResponse reject(@PathVariable UUID id, @RequestParam String reason) {
    return bookingService.rejectBooking(id, reason);
  }

  @Operation(summary = "User check-in: Xác nhận đã tới quán")
  @PostMapping("/{id}/user-checkin")
  public BookingResponse userCheckIn(@PathVariable UUID id) {
    return bookingService.userCheckIn(id);
  }

  @Operation(summary = "[Owner] Xác nhận khách đã tới")
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/{id}/owner-checkin")
  public BookingResponse ownerCheckIn(@PathVariable UUID id) {
    return bookingService.ownerCheckIn(id);
  }
}
