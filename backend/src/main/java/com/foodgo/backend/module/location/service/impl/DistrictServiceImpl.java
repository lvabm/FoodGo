package com.foodgo.backend.module.location.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.location.criteria.DistrictSpecification;
import com.foodgo.backend.module.location.dto.DistrictFilterRequest;
import com.foodgo.backend.module.location.dto.DistrictResponse;
import com.foodgo.backend.module.location.entity.District;
import com.foodgo.backend.module.location.mapper.DistrictMapper;
import com.foodgo.backend.module.location.repository.DistrictRepository;
import com.foodgo.backend.module.location.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl
    extends ReadOnlyServiceImpl<District, DistrictResponse, Integer, DistrictFilterRequest>
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
  protected ReadableMapper<District, DistrictResponse> getMapper() {
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
}
