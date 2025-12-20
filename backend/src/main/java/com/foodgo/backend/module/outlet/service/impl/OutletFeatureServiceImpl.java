package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.outlet.dto.criteria.OutletFeatureSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletFeatureMapper;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFeatureFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureResponse;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import com.foodgo.backend.module.outlet.repository.OutletFeatureRepository;
import com.foodgo.backend.module.outlet.service.OutletFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutletFeatureServiceImpl
    extends BaseServiceImpl<
        OutletFeature, Integer, Object, Object, OutletFeatureFilterRequest, OutletFeatureResponse>
    implements OutletFeatureService {

  private final String outletFeatureEntityName = EntityName.OUTLET_FEATURE.getFriendlyName();
  private final OutletFeatureRepository repository;
  private final OutletFeatureMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<OutletFeature, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletFeature> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<OutletFeature, Object, Object, OutletFeatureResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return outletFeatureEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<OutletFeature> buildSpecification(OutletFeatureFilterRequest filter) {
    return new OutletFeatureSearchSpecification(filter);
  }
}
