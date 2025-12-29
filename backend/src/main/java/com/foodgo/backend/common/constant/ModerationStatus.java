package com.foodgo.backend.common.constant;

/**
 * Trạng thái kiểm duyệt nội dung
 */
public enum ModerationStatus {
  /**
   * Chờ kiểm duyệt - Nội dung mới tạo, chưa được admin duyệt
   */
  PENDING,

  /**
   * Đã duyệt - Nội dung đã được admin kiểm tra và chấp nhận, hiển thị công khai
   */
  APPROVED,

  /**
   * Từ chối - Nội dung vi phạm quy định, không được hiển thị
   */
  REJECTED,

  /**
   * Tạm ẩn - Nội dung bị ẩn do có nhiều báo cáo, chờ admin xem xét
   */
  HIDDEN,

  /**
   * Tự động duyệt - Nội dung đã được hệ thống tự động duyệt (không cần admin)
   */
  AUTO_APPROVED
}


