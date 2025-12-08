package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Lớp triển khai **Base Service** trừu tượng (Abstract Base Service Implementation). Cung cấp logic
 * mặc định cho các hoạt động **CRUD** (Create, Read, Update, Delete), Phân trang, và Lọc.
 *
 * @param <Entity> Kiểu dữ liệu của **Thực thể**.
 * @param <CreateRequest> DTO Yêu cầu Tạo mới.
 * @param <UpdateRequest> DTO Yêu cầu Cập nhật.
 * @param <FilterRequest> DTO Yêu cầu Lọc.
 * @param <Response> DTO Phản hồi.
 * @param <Id> Kiểu dữ liệu của **ID Thực thể** (phải là kiểu có thể Serialize).
 */
@Slf4j
@Transactional(readOnly = true)
// Không ràng buộc Entity cụ thể để cho phép nó kế thừa cả BaseUUIDEntity hoặc BaseIntegerEntity
public abstract class BaseServiceImpl<
        Entity, CreateRequest, UpdateRequest, FilterRequest, Response, Id extends Serializable>
    implements BaseService<CreateRequest, UpdateRequest, FilterRequest, Response, Id> {

  // ==================== I. PHƯƠNG THỨC TRỪU TƯỢNG CẦN TRIỂN KHAI ====================

  /**
   * Lấy instance của **Repository**.
   *
   * @return JpaRepository tương ứng với Thực thể.
   */
  protected abstract JpaRepository<Entity, Id> getRepository();

  /**
   * Lấy instance của **Mapper**.
   *
   * @return BaseMapper tương ứng với Thực thể và DTOs.
   */
  protected abstract BaseMapper<Entity, CreateRequest, UpdateRequest, Response> getMapper();

  /**
   * Lấy tên của Thực thể (dùng cho thông báo lỗi và log).
   *
   * @return Tên Thực thể (ví dụ: "User", "Product").
   */
  protected abstract String getEntityName();

  // ==================== II. HOOK METHODS (CÓ THỂ GHI ĐÈ) ====================

  /**
   * Xây dựng **Specification** cho chức năng lọc nâng cao (tùy chọn).
   *
   * @param filterRequest DTO chứa các tiêu chí lọc.
   * @return Specification để áp dụng tiêu chí lọc, hoặc `null` (mặc định không lọc).
   */
  protected Specification<Entity> buildSpecification(FilterRequest filterRequest) {
    return null;
  }

  /** Logic kiểm tra/xác thực bổ sung **trước khi tạo**. */
  protected void validateBeforeCreate(CreateRequest createRequest) {
    // Ghi đè để thêm validation nghiệp vụ
  }

  /** Logic kiểm tra/xác thực bổ sung **trước khi cập nhật**. */
  protected void validateBeforeUpdate(Id id, UpdateRequest updateRequest) {
    // Ghi đè để thêm validation nghiệp vụ
  }

  /** Logic nghiệp vụ bổ sung **sau khi tạo**. */
  protected void afterCreate(Entity entity) {
    // Ghi đè để thực hiện các hành động tiếp theo (ví dụ: gửi sự kiện)
  }

  /** Logic nghiệp vụ bổ sung **sau khi cập nhật**. */
  protected void afterUpdate(Entity entity) {
    // Ghi đè để thực hiện các hành động tiếp theo
  }

  // ==================== III. TRIỂN KHAI CÁC HOẠT ĐỘNG CRUD ====================

  @Override
  @Transactional
  public Response create(CreateRequest createRequest) {
    log.debug("Đang tạo mới {}: {}", getEntityName(), createRequest);

    validateBeforeCreate(createRequest);

    Entity entity = getMapper().toEntity(createRequest);
    Entity savedEntity = getRepository().save(entity);

    afterCreate(savedEntity);

    Object entityId = getIdFromEntity(savedEntity);
    log.info("Đã tạo {} thành công với ID: {}", getEntityName(), entityId);
    return getMapper().toResponse(savedEntity);
  }

  @Override
  @Transactional
  public Response update(Id id, UpdateRequest updateRequest) {
    log.debug("Đang cập nhật {} với ID {}: {}", getEntityName(), id, updateRequest);

    validateBeforeUpdate(id, updateRequest);

    Entity entity = findByIdOrThrow(id);
    getMapper().updateEntity(updateRequest, entity);
    Entity updatedEntity = getRepository().save(entity);

    afterUpdate(updatedEntity);

    log.info("Đã cập nhật {} thành công với ID: {}", getEntityName(), id);
    return getMapper().toResponse(updatedEntity);
  }

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
  public Page<Response> getPage(FilterRequest filterRequest, Pageable pageable) {
    log.debug(
        "Đang lấy trang của {} với bộ lọc: {}, thông số phân trang: {}",
        getEntityName(),
        filterRequest,
        pageable);

    Page<Entity> entityPage;

    // Kiểm tra và sử dụng Specification nếu Repository hỗ trợ (thông qua JpaSpecificationExecutor)
    if (getRepository() instanceof JpaSpecificationExecutor) {
      var spec = buildSpecification(filterRequest);
      if (spec != null) {
        entityPage = ((JpaSpecificationExecutor<Entity>) getRepository()).findAll(spec, pageable);
      } else {
        entityPage = getRepository().findAll(pageable);
      }
    } else {
      entityPage = getRepository().findAll(pageable);
    }

    log.debug(
        "Tìm thấy {} thực thể {} (trang {} trên tổng {})",
        entityPage.getNumberOfElements(),
        getEntityName(),
        entityPage.getNumber() + 1,
        entityPage.getTotalPages());

    return entityPage.map(getMapper()::toResponse);
  }

  @Override
  @Transactional
  public Response softDelete(Id id) {
    log.debug("Đang xóa mềm {} với ID: {}", getEntityName(), id);

    Entity entity = findByIdOrThrow(id);

    // Logic Soft Delete: Sử dụng instanceof để truy cập setter isDeleted
    if (entity instanceof BaseUUIDEntity) {
      ((BaseUUIDEntity) entity).setIsDeleted(true);
    } else if (entity instanceof BaseIntegerEntity) {
      ((BaseIntegerEntity<?>) entity).setIsDeleted(true);
    } else {
      // Cảnh báo nếu Entity không có cơ chế Soft Delete
      log.error("{} không hỗ trợ Soft Delete vì không kế thừa Base Entity.", getEntityName());
      throw new UnsupportedOperationException(getEntityName() + " không hỗ trợ Soft Delete");
    }

    Entity deletedEntity = getRepository().save(entity);

    log.info("Đã xóa mềm {} thành công với ID: {}", getEntityName(), id);
    return getMapper().toResponse(deletedEntity);
  }

  @Override
  @Transactional
  public void hardDelete(Id id) {
    log.debug("Đang xóa cứng {} với ID: {}", getEntityName(), id);

    if (!existsById(id)) {
      throw new ResourceNotFoundException(getEntityName() + " không tìm thấy với ID: " + id);
    }

    getRepository().deleteById(id);
    log.warn("Đã xóa cứng {} với ID: {}", getEntityName(), id);
  }

  @Override
  public boolean existsById(Id id) {
    return getRepository().existsById(id);
  }

  // ==================== IV. PHƯƠNG THỨC HỖ TRỢ (HELPER METHODS) ====================

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

  /**
   * Tìm Thực thể theo ID. Trả về **null** nếu không tìm thấy.
   *
   * @param id ID của Thực thể cần tìm.
   * @return Thực thể tìm thấy, hoặc `null`.
   */
  protected Entity findByIdOrNull(Id id) {
    return getRepository().findById(id).orElse(null);
  }

  /**
   * Hỗ trợ lấy ID từ Entity mà không cần ràng buộc Generic cứng nhắc (dùng cho logging). Lưu ý:
   * Phương thức này dựa trên tính năng `getId()` của Lombok/JPA.
   */
  private Object getIdFromEntity(Entity entity) {
    if (entity instanceof BaseUUIDEntity) {
      return ((BaseUUIDEntity) entity).getId();
    } else if (entity instanceof BaseIntegerEntity) {
      return ((BaseIntegerEntity<?>) entity).getId();
    }
    return "N/A";
  }
}
