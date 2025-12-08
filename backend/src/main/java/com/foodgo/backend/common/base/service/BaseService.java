package com.foodgo.backend.common.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface Base Service cung cấp các hoạt động **CRUD** (Create, Read, Update, Delete) cơ bản.
 *
 * @param <CreateRequest> Kiểu dữ liệu của **DTO Yêu cầu Tạo mới** (Create Request DTO).
 * @param <UpdateRequest> Kiểu dữ liệu của **DTO Yêu cầu Cập nhật** (Update Request DTO).
 * @param <FilterRequest> Kiểu dữ liệu của **DTO Yêu cầu Lọc** (Filter Request DTO).
 * @param <Response> Kiểu dữ liệu của **DTO Phản hồi** (Response DTO).
 * @param <Id> Kiểu dữ liệu của **ID Thực thể** (Entity ID type).
 */
public interface BaseService<CreateRequest, UpdateRequest, FilterRequest, Response, Id> {

  /**
   * Tạo một Thực thể mới.
   *
   * @param createRequest DTO chứa dữ liệu tạo mới.
   * @return DTO Phản hồi của Thực thể vừa được tạo.
   */
  Response create(CreateRequest createRequest);

  /**
   * Cập nhật Thực thể đã tồn tại.
   *
   * @param id ID của Thực thể cần cập nhật.
   * @param updateRequest DTO chứa dữ liệu cập nhật.
   * @return DTO Phản hồi của Thực thể đã được cập nhật.
   */
  Response update(Id id, UpdateRequest updateRequest);

  /**
   * Lấy chi tiết Thực thể theo ID.
   *
   * @param id ID của Thực thể cần lấy.
   * @return DTO Phản hồi chi tiết.
   */
  Response getDetail(Id id);

  /**
   * Lấy tất cả các Thực thể (Nên **thận trọng** khi sử dụng - Ưu tiên dùng phân trang).
   *
   * @return Danh sách DTO Phản hồi.
   */
  List<Response> getAll();

  /**
   * Lấy danh sách Thực thể được phân trang và lọc.
   *
   * @param filterRequest DTO chứa các tiêu chí lọc.
   * @param pageable Tham số phân trang (ví dụ: số trang, kích thước trang, sắp xếp).
   * @return Đối tượng **Page** chứa danh sách DTO Phản hồi.
   */
  Page<Response> getPage(FilterRequest filterRequest, Pageable pageable);

  /**
   * Xóa mềm (Soft Delete) Thực thể (chỉ đánh dấu là đã xóa, không xóa khỏi DB).
   *
   * @param id ID của Thực thể cần xóa mềm.
   * @return DTO Phản hồi của Thực thể đã được xóa mềm.
   */
  Response softDelete(Id id);

  /**
   * Xóa cứng (Hard Delete) Thực thể (Xóa vĩnh viễn khỏi DB - Chỉ dành cho quản trị viên).
   *
   * @param id ID của Thực thể cần xóa cứng.
   */
  void hardDelete(Id id);

  /**
   * Kiểm tra xem Thực thể có tồn tại theo ID hay không.
   *
   * @param id ID cần kiểm tra.
   * @return `true` nếu tồn tại, `false` nếu không.
   */
  boolean existsById(Id id);
}
