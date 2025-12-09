package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public abstract class BaseServiceImpl<
        Entity, CreateRequest, UpdateRequest, FilterRequest, Response, Id extends Serializable>
    implements BaseService<CreateRequest, UpdateRequest, FilterRequest, Response, Id> {

  // ==================== I. PHƯƠNG THỨC TRỪU TƯỢNG CẦN TRIỂN KHAI ====================

  protected abstract JpaRepository<Entity, Id> getRepository();

  // Giữ lại JpaSpecificationExecutor riêng để đảm bảo tính tường minh
  protected abstract JpaSpecificationExecutor<Entity> getSpecRepository();

  protected abstract BaseMapper<Entity, CreateRequest, UpdateRequest, Response> getMapper();

  protected abstract String getEntityName();

  // ==================== II. HOOK METHODS (CÓ THỂ GHI ĐÈ) ====================

  protected Specification<Entity> buildSpecification(FilterRequest filterRequest) {
    return (root, query, cb) -> cb.conjunction();
  }

  protected void validateBeforeCreate(CreateRequest createRequest) {
    // Ghi đè để thêm validation nghiệp vụ
  }

  protected void validateBeforeUpdate(Id id, UpdateRequest updateRequest) {
    // Ghi đè để thêm validation nghiệp vụ
  }

  protected void afterCreate(Entity entity) {
    // Ghi đè để thực hiện các hành động tiếp theo (ví dụ: gửi sự kiện)
  }

  protected void afterUpdate(Entity entity) {
    // Ghi đè để thực hiện các hành động tiếp theo
  }

  // ==================== III. TRIỂN KHAI CÁC HOẠT ĐỘNG CRUD ====================

  @Override
  @Transactional
  public Response create(CreateRequest createRequest) {
    validateBeforeCreate(createRequest);

    Entity entity = getMapper().toEntity(createRequest);
    Entity savedEntity = getRepository().save(entity);

    afterCreate(savedEntity);

    return getMapper().toResponse(savedEntity);
  }

  @Override
  @Transactional
  public Response update(Id id, UpdateRequest updateRequest) {
    validateBeforeUpdate(id, updateRequest);

    Entity entity = findByIdOrThrow(id);
    getMapper().updateEntity(updateRequest, entity);
    Entity updatedEntity = getRepository().save(entity);

    afterUpdate(updatedEntity);

    return getMapper().toResponse(updatedEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public Response getDetail(Id id) {
    return getRepository()
        .findById(id)
        .map(getMapper()::toResponse)
        .orElseThrow(
            () -> new ResourceNotFoundException(getEntityName() + " not found with ID: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Response> getAll() {
    return getRepository().findAll().stream()
        .map(getMapper()::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Response> getPage(FilterRequest filterRequest, Pageable pageable) {
    Specification<Entity> spec = buildSpecification(filterRequest);
    return getSpecRepository().findAll(spec, pageable).map(getMapper()::toResponse);
  }

  @Override
  @Transactional
  public Response softDelete(Id id) {
    Entity entity = findByIdOrThrow(id);

    // Logic Soft Delete: Sử dụng instanceof để truy cập setter isDeleted
    if (entity instanceof BaseUUIDEntity) {
      ((BaseUUIDEntity) entity).setIsDeleted(true);
    } else if (entity instanceof BaseIntegerEntity) {
      ((BaseIntegerEntity<?>) entity).setIsDeleted(true);
    } else {
      throw new UnsupportedOperationException(getEntityName() + " không hỗ trợ Soft Delete");
    }

    Entity deletedEntity = getRepository().save(entity);

    return getMapper().toResponse(deletedEntity);
  }

  @Override
  @Transactional
  public void hardDelete(Id id) {
    if (!existsById(id)) {
      throw new ResourceNotFoundException(getEntityName() + " không tìm thấy với ID: " + id);
    }
    getRepository().deleteById(id);
  }

  @Override
  public boolean existsById(Id id) {
    return getRepository().existsById(id);
  }

  // ==================== IV. PHƯƠNG THỨC HỖ TRỢ (HELPER METHODS) ====================

  protected Entity findByIdOrThrow(Id id) {
    return getRepository()
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(getEntityName() + " không tìm thấy với ID: " + id));
  }
}
