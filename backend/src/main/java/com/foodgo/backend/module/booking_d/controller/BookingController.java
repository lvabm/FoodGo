package com.foodgo.backend.module.booking_d.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

  @Operation(summary = "Đặt bàn")
  @PostMapping("/outlets/{outletId}")
  private void createBooking(@PathVariable Integer outletId) {}

  @Operation(summary = "Hủy đặt bàn")
  @DeleteMapping("/{bookingId}")
  private void cancelBooking(@PathVariable Integer bookingId) {}

  @Operation(summary = "Thay đổi thông tin đặt bàn")
  @PutMapping("/{bookingId}")
  private void updateBooking(@PathVariable Integer bookingId) {}

  @Operation(summary = "Xem chi tiết đơn đặt bàn (Người dùng)")
  @GetMapping("/{bookingId}")
  private void getBookingDetail(@PathVariable Integer bookingId) {}

  // URL ở đây có vẽ chưa đúng -> nếu giữ URL cũ /bookings -> truyền input userDetail từ header vào
  // ở dưới đang là URL đã thay đổi
  @Operation(summary = "Xem lịch sử đặt bàn")
  @GetMapping("/users/{userId}")
  private void getAllBooking() {}
}
