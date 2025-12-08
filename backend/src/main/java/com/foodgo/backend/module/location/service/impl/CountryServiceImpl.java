package com.foodgo.backend.module.location.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.location.criteria.CountrySpecification;
import com.foodgo.backend.module.location.dto.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.CountryResponse;
import com.foodgo.backend.module.location.entity.Country;
import com.foodgo.backend.module.location.mapper.CountryMapper;
import com.foodgo.backend.module.location.repository.CountryRepository;
import com.foodgo.backend.module.location.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryServiceImpl
    extends ReadOnlyServiceImpl<Country, CountryResponse, Integer, CountryFilterRequest>
    implements CountryService {

  private final String countryEntityName = EntityName.COUNTRY.getFriendlyName();
  private final CountryRepository repository;
  private final CountryMapper mapper;

  @Override
  protected JpaRepository<Country, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<Country> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<Country, CountryResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return countryEntityName;
  }

  @Override
  protected Specification<Country> buildSpecification(CountryFilterRequest filter) {
    return new CountrySpecification(filter);
  }
}
