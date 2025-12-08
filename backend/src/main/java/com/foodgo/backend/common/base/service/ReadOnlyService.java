package com.foodgo.backend.common.base.service;

import java.util.List;

/**
 * Interface **ReadOnlyService** (Dịch vụ chỉ Đọc) dùng cho các bảng tra cứu/tham chiếu. Chỉ cung
 * cấp các hoạt động đọc dữ liệu cơ bản.
 *
 * @param <Response> Kiểu dữ liệu của **DTO Phản hồi** (Response DTO).
 * @param <Id> Kiểu dữ liệu của **ID Thực thể** (Entity ID type).
 */
public interface ReadOnlyService<Response, Id> {

  /**
   * Lấy chi tiết Thực thể theo ID.
   *
   * @param id ID của Thực thể cần lấy.
   * @return DTO Phản hồi chi tiết.
   */
  Response getDetail(Id id);

  /**
   * Lấy tất cả các Thực thể.
   *
   * @return Danh sách DTO Phản hồi.
   */
  List<Response> getAll();

  /**
   * Kiểm tra xem Thực thể có tồn tại theo ID hay không.
   *
   * @param id ID cần kiểm tra.
   * @return `true` nếu tồn tại, `false` nếu không.
   */
  boolean existsById(Id id);
}
