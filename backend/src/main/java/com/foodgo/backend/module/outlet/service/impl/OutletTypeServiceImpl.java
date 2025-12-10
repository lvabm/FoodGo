package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.outlet.dto.criteria.OutletTypeSearchSpecification;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletTypeResponse;
import com.foodgo.backend.module.outlet.entity.OutletType;
import com.foodgo.backend.module.outlet.dto.mapper.OutletTypeMapper;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
import com.foodgo.backend.module.outlet.service.OutletTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutletTypeServiceImpl
    extends ReadOnlyServiceImpl<OutletType, OutletTypeResponse, Integer, OutletTypeFilterRequest>
    implements OutletTypeService {

  private final String outletTypeEntityName = EntityName.OUTLET_TYPE.getFriendlyName();
  private final OutletTypeRepository repository;
  private final OutletTypeMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

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

  // Triển khai logic Specification
  @Override
  protected Specification<OutletType> buildSpecification(OutletTypeFilterRequest filter) {
    return new OutletTypeSearchSpecification(filter);
  }
}
