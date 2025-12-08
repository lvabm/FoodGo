package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.common.context.SuccessMessageContext;
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

@Slf4j
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<
        Entity, CreateRequest, UpdateRequest, FilterRequest, Response, Id extends Serializable>
    implements BaseService<CreateRequest, UpdateRequest, FilterRequest, Response, Id> {

  // ==================== I. PHƯƠNG THỨC TRỪU TƯỢNG CẦN TRIỂN KHAI ====================

  protected abstract JpaRepository<Entity, Id> getRepository();

  protected abstract BaseMapper<Entity, CreateRequest, UpdateRequest, Response> getMapper();

  protected abstract String getEntityName();

  // ==================== II. HOOK METHODS (CÓ THỂ GHI ĐÈ) ====================

  protected Specification<Entity> buildSpecification(FilterRequest filterRequest) {
    return null;
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
    log.debug("Đang tạo mới {}: {}", getEntityName(), createRequest);

    validateBeforeCreate(createRequest);

    Entity entity = getMapper().toEntity(createRequest);
    Entity savedEntity = getRepository().save(entity);

    afterCreate(savedEntity);

    Object entityId = getIdFromEntity(savedEntity);
    log.info("Đã tạo {} thành công với ID: {}", getEntityName(), entityId);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), entityId));

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

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(updatedEntity);
  }

  @Override
  public Response getDetail(Id id) {
    log.debug("Đang lấy chi tiết {} với ID: {}", getEntityName(), id);
    Entity entity = findByIdOrThrow(id);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));
    return getMapper().toResponse(entity);
  }

  @Override
  public List<Response> getAll() {
    log.debug("Đang lấy tất cả {}", getEntityName());
    List<Entity> entities = getRepository().findAll();
    log.debug("Tìm thấy {} thực thể {}", entities.size(), getEntityName());

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, getEntityName()));

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

    // Kiểm tra và sử dụng Specification thông qua JpaSpecificationExecutor trong Repository
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

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.FETCH_SUCCESS_PAGE,
            getEntityName(),
            entityPage.getNumber() + 1,
            entityPage.getTotalPages()));

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

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.SOFT_DELETE_SUCCESS, getEntityName(), id));

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

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.HARD_DELETE_SUCCESS, getEntityName(), id));
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

  protected Entity findByIdOrNull(Id id) {
    return getRepository().findById(id).orElse(null);
  }

  private Object getIdFromEntity(Entity entity) {
    if (entity instanceof BaseUUIDEntity) {
      return ((BaseUUIDEntity) entity).getId();
    } else if (entity instanceof BaseIntegerEntity) {
      return ((BaseIntegerEntity<?>) entity).getId();
    }
    return "N/A";
  }
}
