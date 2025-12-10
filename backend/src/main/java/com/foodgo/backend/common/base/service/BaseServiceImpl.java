package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.context.SuccessMessageContext;
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

  // ================= I. ABSTRACT =================

  protected abstract JpaRepository<Entity, Id> getRepository();

  protected abstract JpaSpecificationExecutor<Entity> getSpecRepository();

  protected abstract BaseMapper<Entity, CreateRequest, UpdateRequest, Response> getMapper();

  /** Tên hiển thị của Entity (VD: "Cửa hàng", "Loại cửa hàng",...) */
  protected abstract String getEntityName();

  // ================= II. HOOK – CHO CUSTOM LOGIC =================

  protected void validateBeforeCreate(CreateRequest request) {}

  protected void validateBeforeUpdate(Id id, UpdateRequest request) {}

  protected void afterCreate(Entity entity) {}

  protected void afterUpdate(Entity entity) {}

  /**
   * * 🔑 HOOK QUAN TRỌNG: Override trong service con để kiểm tra quyền truy cập/sở hữu (VD: Outlet,
   * Booking...)
   */
  protected void ensurePermission(Entity entity) {
    // Mặc định: Không làm gì. Logic kiểm tra quyền Admin/Owner sẽ được thêm ở lớp con.
  }

  protected Specification<Entity> buildSpecification(FilterRequest filterRequest) {
    // Mặc định trả về Specification rỗng
    return (root, query, cb) -> cb.conjunction();
  }

  // ================= III. SOFT DELETE =================

  /** HARD RULE: Specification lọc isDeleted = false */
  private Specification<Entity> notDeletedSpec() {
    return (root, query, cb) -> {
      Class<?> type = root.getJavaType();
      // Dùng isAssignableFrom để kiểm tra xem Entity có kế thừa Base Entity không
      boolean supportSoftDelete =
          BaseUUIDEntity.class.isAssignableFrom(type)
              || BaseIntegerEntity.class.isAssignableFrom(type);

      return supportSoftDelete ? cb.isFalse(root.get("isDeleted")) : cb.conjunction();
    };
  }

  // ================= IV. CRUD CORE =================

  @Override
  @Transactional
  public Response create(CreateRequest request) {
    validateBeforeCreate(request);

    Entity entity = getMapper().toEntity(request);
    Entity saved = getRepository().save(entity);

    afterCreate(saved);

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), getId(saved)));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional
  public Response update(Id id, UpdateRequest request) {
    validateBeforeUpdate(id, request);

    Entity entity = findByIdOrThrow(id);
    // 🔑 HOOK: Kiểm tra quyền truy cập/sở hữu
    ensurePermission(entity);

    getMapper().updateEntity(request, entity);
    Entity saved = getRepository().save(entity);

    afterUpdate(saved);

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public Response getDetail(Id id) {
    // 1. Kết hợp Soft Delete Filter và Filter theo ID
    Specification<Entity> specById = (root, query, cb) -> cb.equal(root.get("id"), id);
    // 🔑 KHẮC PHỤC: Dùng .and() để kết hợp Specification
    Specification<Entity> finalSpec = notDeletedSpec().and(specById);

    // 2. Tìm kiếm tường minh để có thể gán message
    Entity entity =
        getSpecRepository().findAll(finalSpec).stream()
            .findFirst()
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        getEntityName() + " không tìm thấy với ID: " + id));

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Response> getAll() {
    List<Entity> entities =
        getSpecRepository().findAll(notDeletedSpec()); // Áp dụng Soft Delete Filter

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, getEntityName()));

    return entities.stream().map(getMapper()::toResponse).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Response> getPage(FilterRequest filterRequest, Pageable pageable) {
    Specification<Entity> customSpec = buildSpecification(filterRequest);

    // Kết hợp Soft Delete Filter với Custom Filter
    Specification<Entity> finalSpec = notDeletedSpec().and(customSpec);

    Page<Entity> page = getSpecRepository().findAll(finalSpec, pageable);

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.FETCH_SUCCESS_PAGE,
            getEntityName(),
            page.getNumber() + 1,
            page.getTotalPages()));

    return page.map(getMapper()::toResponse);
  }

  @Override
  @Transactional
  public Response softDelete(Id id) {
    Entity entity = findByIdOrThrow(id);
    // 🔑 HOOK: Kiểm tra quyền truy cập/sở hữu
    ensurePermission(entity);

    if (entity instanceof BaseUUIDEntity e) e.setIsDeleted(true);
    else if (entity instanceof BaseIntegerEntity<?> e) e.setIsDeleted(true);
    else throw new UnsupportedOperationException(getEntityName() + " không hỗ trợ Soft Delete");

    Entity saved = getRepository().save(entity);

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.SOFT_DELETE_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional
  public void hardDelete(Id id) {
    Entity entity = findByIdOrThrow(id);
    // 🔑 HOOK: Kiểm tra quyền truy cập/sở hữu
    ensurePermission(entity);

    getRepository().deleteById(id);

    // 🔑 HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.HARD_DELETE_SUCCESS, getEntityName(), id));
  }

  @Override
  public boolean existsById(Id id) {
    return getRepository().existsById(id);
  }

  // ================= V. HELPER =================

  protected Entity findByIdOrThrow(Id id) {
    // NOTE: FindByIdOrThrow không áp dụng Soft Delete Filter, nó chỉ kiểm tra sự tồn tại trong DB.
    // Lớp con phải gọi ensurePermission ngay sau khi gọi findByIdOrThrow.
    return getRepository()
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(getEntityName() + " không tìm thấy với ID: " + id));
  }

  private String getId(Entity entity) {
    if (entity instanceof BaseUUIDEntity u) return u.getId().toString();
    if (entity instanceof BaseIntegerEntity<?> i) return i.getId().toString();
    return "N/A";
  }
}
