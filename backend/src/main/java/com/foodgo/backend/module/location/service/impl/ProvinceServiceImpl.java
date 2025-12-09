package com.foodgo.backend.module.location.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;

import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.location.dto.criteria.ProvinceSpecification;
import com.foodgo.backend.module.location.dto.request.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.response.ProvinceResponse;
import com.foodgo.backend.module.location.entity.Province;
import com.foodgo.backend.module.location.dto.mapper.ProvinceMapper;
import com.foodgo.backend.module.location.repository.ProvinceRepository;
import com.foodgo.backend.module.location.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl
    extends ReadOnlyServiceImpl<Province, ProvinceResponse, Integer, ProvinceFilterRequest>
    implements ProvinceService {

  private final String provinceEntityName = EntityName.PROVINCE.getFriendlyName();
  private final ProvinceRepository repository;
  private final ProvinceMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<Province, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<Province> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<Province, ProvinceResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return provinceEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<Province> buildSpecification(ProvinceFilterRequest filter) {
    return new ProvinceSpecification(filter);
  }
}
