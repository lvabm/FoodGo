package com.foodgo.backend.common.constant;

public enum BookingStatus {
  PENDING, // Chờ xác nhận
  CONFIRMED, // Đã xác nhận
  CANCELLED, // Hủy đơn
  REJECTED, // Outlet hủy đơn
  COMPLETED, // Đơn thành công
  NO_SHOW // Ẩn đơn
}
