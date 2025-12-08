package com.foodgo.backend.common.base.service;

import java.util.List;

/**
 * Interface Base Mapper dùng để chuyển đổi giữa Entity và DTOs.
 *
 * @param <Entity> Kiểu dữ liệu của **Thực thể** (Entity).
 * @param <CreateRequest> Kiểu dữ liệu của **DTO Yêu cầu Tạo mới** (Create Request DTO).
 * @param <UpdateRequest> Kiểu dữ liệu của **DTO Yêu cầu Cập nhật** (Update Request DTO).
 * @param <Response> Kiểu dữ liệu của **DTO Phản hồi** (Response DTO).
 */
public interface BaseMapper<Entity, CreateRequest, UpdateRequest, Response> {

  /**
   * Chuyển đổi DTO Yêu cầu Tạo mới (**Create Request**) sang **Thực thể** (Entity).
   *
   * @param createRequest DTO chứa dữ liệu tạo mới.
   * @return Thực thể tương ứng.
   */
  Entity toEntity(CreateRequest createRequest);

  /**
   * Chuyển đổi **Thực thể** (Entity) sang DTO Phản hồi (**Response**).
   *
   * @param entity Thực thể cần chuyển đổi.
   * @return DTO Phản hồi tương ứng.
   */
  Response toResponse(Entity entity);

  /**
   * Chuyển đổi danh sách các Thực thể (**List<Entity>**) sang danh sách DTO Phản hồi
   * (**List<Response>**).
   *
   * @param entities Danh sách Thực thể.
   * @return Danh sách DTO Phản hồi.
   */
  List<Response> toResponseList(List<Entity> entities);

  /**
   * Cập nhật Thực thể đã tồn tại từ DTO Yêu cầu Cập nhật (**Update Request**). LƯU Ý QUAN TRỌNG:
   * Phương thức này **chỉ nên cập nhật** các trường có giá trị **khác null** (non-null fields) từ
   * Update Request.
   *
   * @param updateRequest DTO chứa dữ liệu cập nhật.
   * @param entity Thực thể đã tồn tại cần được cập nhật.
   */
  void updateEntity(UpdateRequest updateRequest, Entity entity);
}
