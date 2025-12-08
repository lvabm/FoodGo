package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.io.Serializable;

/**
 * Lớp triển khai **ReadOnlyService** trừu tượng (Abstract Read-Only Service Implementation). Cung
 * cấp logic mặc định cho các hoạt động **Chỉ Đọc** (Read, Find All, Exists) cho các bảng tra
 * cứu/tham chiếu.
 *
 * @param <Entity> Kiểu dữ liệu của **Thực thể** (Entity), nên kế thừa một Base Entity có ID.
 * @param <Response> Kiểu dữ liệu của **DTO Phản hồi**.
 * @param <Id> Kiểu dữ liệu của **ID Thực thể** (phải là kiểu có thể Serialize).
 */
@Slf4j
@Transactional(readOnly = true)
public abstract class ReadOnlyServiceImpl<Entity, Response, Id extends Serializable>
    implements ReadOnlyService<Response, Id> {

  // ==================== I. PHƯƠNG THỨC TRỪU TƯỢNG CẦN TRIỂN KHAI ====================

  /**
   * Lấy instance của **Repository** - Phải được triển khai bởi các lớp con.
   *
   * @return JpaRepository tương ứng với Thực thể.
   */
  protected abstract JpaRepository<Entity, Id> getRepository();

  /**
   * Lấy instance của **Mapper** - Phải được triển khai bởi các lớp con. Lưu ý: Vì đây là dịch vụ
   * chỉ đọc, chúng ta không cần tham số cho Create/Update Request DTO.
   *
   * @return BaseMapper với các tham số Create/Update là `Void`.
   */
  protected abstract BaseMapper<Entity, Void, Void, Response> getMapper();

  /**
   * Lấy tên của Thực thể (dùng cho thông báo lỗi và log).
   *
   * @return Tên Thực thể (ví dụ: "Country").
   */
  protected abstract String getEntityName();

  // ==================== II. TRIỂN KHAI CÁC HOẠT ĐỘNG CHỈ ĐỌC ====================

  @Override
  public Response getDetail(Id id) {
    log.debug("Đang lấy chi tiết {} với ID: {}", getEntityName(), id);
    Entity entity = findByIdOrThrow(id);
    return getMapper().toResponse(entity);
  }

  @Override
  public List<Response> getAll() {
    log.debug("Đang lấy tất cả {}", getEntityName());
    List<Entity> entities = getRepository().findAll();
    log.debug("Tìm thấy {} thực thể {}", entities.size(), getEntityName());
    return getMapper().toResponseList(entities);
  }

  @Override
  public boolean existsById(Id id) {
    return getRepository().existsById(id);
  }

  // ==================== III. PHƯƠNG THỨC HỖ TRỢ (HELPER METHOD) ====================

  /**
   * Tìm Thực thể theo ID. Nếu không tìm thấy, ném ra ngoại lệ **ResourceNotFoundException**.
   *
   * @param id ID của Thực thể cần tìm.
   * @return Thực thể tìm thấy.
   * @throws ResourceNotFoundException nếu không tìm thấy Thực thể.
   */
  protected Entity findByIdOrThrow(Id id) {
    return getRepository()
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(getEntityName() + " không tìm thấy với ID: " + id));
  }
}
