package com.foodgo.backend.common.constant;

public enum NotificationType {
  BOOKING_CONFIRMED("BOOKING_CONFIRMED", "Đơn đặt bàn đã được xác nhận"),
  BOOKING_REJECTED("BOOKING_REJECTED", "Đơn đặt bàn đã bị từ chối"),
  BOOKING_CANCELLED("BOOKING_CANCELLED", "Đơn đặt bàn đã bị hủy"),
  BOOKING_REMINDER("BOOKING_REMINDER", "Nhắc nhở đơn đặt bàn"),
  REVIEW_RECEIVED("REVIEW_RECEIVED", "Nhận được đánh giá mới"),
  MEMBERSHIP_EXPIRING("MEMBERSHIP_EXPIRING", "Gói hội viên sắp hết hạn"),
  SYSTEM_ANNOUNCEMENT("SYSTEM_ANNOUNCEMENT", "Thông báo hệ thống");

  private final String code;
  private final String description;

  NotificationType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static boolean isValid(String type) {
    if (type == null) {
      return false;
    }
    for (NotificationType notificationType : values()) {
      if (notificationType.code.equals(type)) {
        return true;
      }
    }
    return false;
  }
}


