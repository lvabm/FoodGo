package com.foodgo.backend.common.constant;

public enum ReportStatus {
  PENDING, // Chờ xử lý
  RESOLVED, // Đã xử lý (Chấp nhận báo cáo -> Xử lý review)
  REJECTED // Từ chối (Báo cáo sai)
}
