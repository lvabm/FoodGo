package com.foodgo.backend.common.base;

import lombok.Data;

/**
 * Lớp **BaseFilterRequest** trừu tượng, dùng làm cơ sở cho mọi **DTO Yêu cầu Lọc** và phân trang
 * trong hệ thống. Cung cấp các trường lọc phổ biến áp dụng cho hầu hết các Thực thể.
 */
@Data
public abstract class BaseFilterRequest {

  /** Từ khóa tìm kiếm chung (áp dụng cho các trường văn bản như tên, mô tả, mã). */
  private String searchKeyword;

  /**
   * Cờ (flag) cho biết có nên bao gồm các thực thể đã bị xóa mềm (soft deleted) hay không. Mặc định
   * là `false` (chỉ lấy các thực thể đang hoạt động).
   */
  private Boolean includeDeleted = false;
}
