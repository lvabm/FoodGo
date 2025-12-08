package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.OutletTypeResponse;
import com.foodgo.backend.module.outlet.entity.OutletType;
import com.foodgo.backend.module.outlet.mapper.OutletTypeMapper;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
import com.foodgo.backend.module.outlet.service.OutletTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutletTypeServiceImpl
    extends ReadOnlyServiceImpl<OutletType, OutletTypeResponse, Integer, OutletTypeFilterRequest>
    implements OutletTypeService {

  private final String outletTypeEntityName = EntityName.OUTLET_TYPE.getFriendlyName();
  private final OutletTypeRepository repository;
  private final OutletTypeMapper mapper;

  // ==================== I. BASE METHODS IMPLEMENTATION ====================

  @Override
  protected JpaRepository<OutletType, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletType> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<OutletType, OutletTypeResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return outletTypeEntityName;
  }

  @Override
  protected Specification<OutletType> buildSpecification(OutletTypeFilterRequest filter) {

    Specification<OutletType> spec = (root, q, cb) -> cb.conjunction();

    if (filter == null) return spec;

    if (filter.name() != null && !filter.name().isBlank()) {
      String pattern = "%" + filter.name().toLowerCase() + "%";
      spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("name")), pattern));
    }

    if (filter.searchKeyword() != null && !filter.searchKeyword().isBlank()) {
      String pattern = "%" + filter.searchKeyword().toLowerCase() + "%";
      spec =
          spec.and(
              (root, q, cb) ->
                  cb.or(
                      cb.like(cb.lower(root.get("name")), pattern),
                      cb.like(cb.lower(root.get("description")), pattern)));
    }

    return spec;
  }

  // ==================== II. GHI ĐÈ BASE SERVICE (TỐI ƯU HÓA & PHÂN TRANG) ====================

  @Override
  public List<OutletTypeResponse> getAll() {
    log.debug("Đang lấy tất cả {} với Projection và Count.", getEntityName());
    var projections = repository.findAllWithCountProjection();
    return mapper.toResponseListFromProjection(projections);
  }

  @Override
  public OutletTypeResponse getDetail(Integer id) {
    log.debug("Đang lấy {} với Projection và Count.", getEntityName());
    var projection =
        repository
            .findByIdWithCountProjection(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        getEntityName() + " không tồn tại với id = " + id));
    return mapper.toResponseFromProjection(projection);
  }

  @Override
  public Page<OutletTypeResponse> getPage(
      OutletTypeFilterRequest filterRequest, Pageable pageable) {
    log.info("Đang tìm kiếm có Phân trang/Lọc cho {}: {}", getEntityName(), filterRequest);

    return super.getPage(filterRequest, pageable);
  }
}
