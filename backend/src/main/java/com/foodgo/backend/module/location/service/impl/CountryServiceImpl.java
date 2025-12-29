package com.foodgo.backend.module.location.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.location.dto.criteria.CountrySpecification;
import com.foodgo.backend.module.location.dto.request.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import com.foodgo.backend.module.location.entity.Country;
import com.foodgo.backend.module.location.dto.mapper.CountryMapper;
import com.foodgo.backend.module.location.repository.CountryRepository;
import com.foodgo.backend.module.location.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl
    extends BaseServiceImpl<Country, Integer, Object, Object, CountryFilterRequest, CountryResponse>
    implements CountryService {

  private final String countryEntityName = EntityName.COUNTRY.getFriendlyName();
  private final CountryRepository repository;
  private final CountryMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<Country, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<Country> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<Country, Object, Object, CountryResponse> getMapper() {
    // Wrapper để adapt CountryMapper (với CreateRequest/UpdateRequest)
    // thành BaseMapper với Object, Object cho ReadableService
    return new BaseMapper<Country, Object, Object, CountryResponse>() {
      @Override
      public CountryResponse toResponse(Country entity) {
        return mapper.toResponse(entity);
      }

      @Override
      public java.util.List<CountryResponse> toResponseList(
          java.util.List<Country> entities) {
        return mapper.toResponseList(entities);
      }

      @Override
      public Country toEntity(Object createRequest) {
        throw new UnsupportedOperationException(
            "CountryServiceImpl chỉ hỗ trợ Read operations");
      }

      @Override
      public void updateEntity(Object updateRequest, Country entity) {
        throw new UnsupportedOperationException(
            "CountryServiceImpl chỉ hỗ trợ Read operations");
      }
    };
  }

  @Override
  protected String getEntityName() {
    return countryEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<Country> buildSpecification(CountryFilterRequest filter) {
    return new CountrySpecification(filter);
  }

  // Override getAll() để thêm cache
  @Override
  @Cacheable(value = "countries", unless = "#result == null || #result.isEmpty()")
  public java.util.List<CountryResponse> getAll() {
    return super.getAll();
  }
}
