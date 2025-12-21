package com.foodgo.backend.common.base.service.impl;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.BaseService;
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
        Entity, Id extends Serializable, CreateRequest, UpdateRequest, FilterRequest, Response>
    implements BaseService<Id, CreateRequest, UpdateRequest, FilterRequest, Response> {

  // ================= I. ABSTRACT =================

  protected abstract JpaRepository<Entity, Id> getRepository();

  protected abstract JpaSpecificationExecutor<Entity> getSpecRepository();

  protected abstract BaseMapper<Entity, CreateRequest, UpdateRequest, Response> getMapper();

  protected abstract String getEntityName();

  // ================= II. HOOK – CHO CUSTOM LOGIC =================

  protected void validateBeforeCreate(CreateRequest request) {}

  protected void validateBeforeUpdate(Id id, UpdateRequest request) {}

  protected void afterCreate(Entity entity) {}

  protected void afterUpdate(Entity entity) {}

  protected void ensurePermission(Entity entity) {}

  protected Specification<Entity> buildSpecification(FilterRequest filterRequest) {
    return (root, query, cb) -> cb.conjunction();
  }

  // ================= III. SOFT DELETE =================

  private Specification<Entity> notDeletedSpec() {
    return (root, query, cb) -> {
      Class<?> type = root.getJavaType();
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

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), getId(saved)));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional
  public Response update(Id id, UpdateRequest request) {
    validateBeforeUpdate(id, request);

    Entity entity = findByIdOrThrow(id);
    ensurePermission(entity);

    getMapper().updateEntity(request, entity);
    Entity saved = getRepository().save(entity);

    afterUpdate(saved);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public Response getDetail(Id id) {
    Specification<Entity> specById = (root, query, cb) -> cb.equal(root.get("id"), id);
    Specification<Entity> finalSpec = notDeletedSpec().and(specById);

    Entity entity =
        getSpecRepository().findAll(finalSpec).stream()
            .findFirst()
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        getEntityName() + " không tìm thấy với ID: " + id));

    ensurePermission(entity); // Thêm check permission khi view detail

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Response> getAll() {
    List<Entity> entities = getSpecRepository().findAll(notDeletedSpec());

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, getEntityName()));

    return entities.stream().map(getMapper()::toResponse).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Response> getPage(FilterRequest filterRequest, Pageable pageable) {
    Specification<Entity> customSpec = buildSpecification(filterRequest);
    Specification<Entity> finalSpec = notDeletedSpec().and(customSpec);

    Page<Entity> page = getSpecRepository().findAll(finalSpec, pageable);

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
    ensurePermission(entity);

    if (entity instanceof BaseUUIDEntity e) e.setIsDeleted(true);
    else if (entity instanceof BaseIntegerEntity<?> e) e.setIsDeleted(true);
    else throw new UnsupportedOperationException(getEntityName() + " không hỗ trợ Soft Delete");

    Entity saved = getRepository().save(entity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.SOFT_DELETE_SUCCESS, getEntityName(), id));

    return getMapper().toResponse(saved);
  }

  @Override
  @Transactional
  public void hardDelete(Id id) {
    Entity entity = findByIdOrThrow(id);
    ensurePermission(entity);

    getRepository().deleteById(id);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.HARD_DELETE_SUCCESS, getEntityName(), id));
  }

  @Override
  public boolean existsById(Id id) {
    return getRepository().existsById(id);
  }

  // ================= V. HELPER =================

  protected Entity findByIdOrThrow(Id id) {
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
