package com.foodgo.backend.common.constant;

public enum PaymentStatus {
  PENDING, // Đợi thanh toán
  COMPLETED, // Thanh toán thành công
  FAILED, // Thanh toán thất bại
  REFUNDED // Đã hoàn tiền
}
