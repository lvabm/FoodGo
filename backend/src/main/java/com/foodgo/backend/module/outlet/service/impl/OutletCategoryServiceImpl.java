package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;

import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.outlet.dto.criteria.OutletCategorySpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletCategoryMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import com.foodgo.backend.module.outlet.repository.OutletCategoryRepository;
import com.foodgo.backend.module.outlet.service.OutletCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutletCategoryServiceImpl
    extends ReadOnlyServiceImpl<
        OutletCategory, OutletCategoryResponse, Integer, OutletCategoryFilterRequest>
    implements OutletCategoryService {

  private final String outletCategoryEntityName = EntityName.OUTLET_CATEGORY.getFriendlyName();
  private final OutletCategoryRepository repository;
  private final OutletCategoryMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<OutletCategory, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletCategory> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<OutletCategory, OutletCategoryResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return outletCategoryEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<OutletCategory> buildSpecification(OutletCategoryFilterRequest filter) {
    return new OutletCategorySpecification(filter);
  }
}
