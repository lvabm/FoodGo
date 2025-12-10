package com.foodgo.backend.common.base.service;

import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public abstract class ReadOnlyServiceImpl<Entity, Response, Id extends Serializable, FilterRequest>
    implements ReadOnlyService<Response, Id, FilterRequest> {

  protected abstract JpaRepository<Entity, Id> getRepository();

  protected abstract JpaSpecificationExecutor<Entity> getSpecRepository();

  protected abstract ReadableMapper<Entity, Response> getMapper();

  protected abstract String getEntityName();

  protected Specification<Entity> buildSpecification(FilterRequest filter) {
    return (root, query, cb) -> cb.conjunction();
  }

  @Override
  @Transactional(readOnly = true)
  public Response getDetail(Id id) {
    return getRepository()
        .findById(id)
        .map(getMapper()::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException(getEntityName() + " not found: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Response> getAll() {
    return getRepository().findAll().stream()
        .map(getMapper()::toResponse)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Response> getPage(FilterRequest filter, Pageable pageable) {
    Specification<Entity> spec = buildSpecification(filter);
    return getSpecRepository().findAll(spec, pageable).map(getMapper()::toResponse);
  }
}
