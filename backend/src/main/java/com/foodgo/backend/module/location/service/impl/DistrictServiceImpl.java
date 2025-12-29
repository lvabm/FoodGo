package com.foodgo.backend.module.location.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.location.dto.criteria.DistrictSpecification;
import com.foodgo.backend.module.location.dto.request.DistrictFilterRequest;
import com.foodgo.backend.module.location.dto.response.DistrictResponse;
import com.foodgo.backend.module.location.entity.District;
import com.foodgo.backend.module.location.dto.mapper.DistrictMapper;
import com.foodgo.backend.module.location.repository.DistrictRepository;
import com.foodgo.backend.module.location.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl
    extends BaseServiceImpl<
        District, Integer, Object, Object, DistrictFilterRequest, DistrictResponse>
    implements DistrictService {

  private final String districEntityName = EntityName.DISTRICT.getFriendlyName();

  private final DistrictRepository repository;
  private final DistrictMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<District, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<District> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<District, Object, Object, DistrictResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return districEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<District> buildSpecification(DistrictFilterRequest filter) {
    return new DistrictSpecification(filter);
  }

  // Override getAll() để thêm cache
  @Override
  @Cacheable(value = "districts", unless = "#result == null || #result.isEmpty()")
  public java.util.List<DistrictResponse> getAll() {
    return super.getAll();
  }
}
