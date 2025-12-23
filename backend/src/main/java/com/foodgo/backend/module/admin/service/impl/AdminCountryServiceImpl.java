package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.module.admin.service.AdminCountryService;
import com.foodgo.backend.module.location.dto.criteria.CountrySpecification;
import com.foodgo.backend.module.location.dto.mapper.CountryMapper;
import com.foodgo.backend.module.location.dto.request.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.request.create.CountryCreateRequest;
import com.foodgo.backend.module.location.dto.request.update.CountryUpdateRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import com.foodgo.backend.module.location.entity.Country;
import com.foodgo.backend.module.location.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCountryServiceImpl
    extends BaseServiceImpl<
        Country,
        Integer,
        CountryCreateRequest,
        CountryUpdateRequest,
        CountryFilterRequest,
        CountryResponse>
    implements AdminCountryService {

  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;

  private final String entityName = EntityName.COUNTRY.getFriendlyName();

  // --- BASE IMPLEMENTATION ---

  @Override
  protected JpaRepository<Country, Integer> getRepository() {
    return countryRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Country> getSpecRepository() {
    return countryRepository;
  }

  @Override
  protected BaseMapper<Country, CountryCreateRequest, CountryUpdateRequest, CountryResponse>
      getMapper() {
    return countryMapper;
  }

  @Override
  protected String getEntityName() {
    return entityName;
  }

  @Override
  protected Specification<Country> buildSpecification(CountryFilterRequest filterRequest) {
    return new CountrySpecification(filterRequest);
  }

  // --- SECURITY HOOK ---

  @Override
  protected void ensurePermission(Country entity) {
    // Chỉ Admin mới có quyền CRUD Country
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException(
          "Chỉ Admin mới có quyền thao tác với " + getEntityName() + ".");
    }
  }

  // --- CUSTOM CREATE/UPDATE ---

  @Override
  @Transactional
  public CountryResponse create(CountryCreateRequest request) {
    ensurePermission(null); // Kiểm tra quyền Admin trước khi tạo

    // Validate duplicate name
    if (countryRepository.existsByNameIgnoreCase(request.name())) {
      throw new BadRequestException("Tên quốc gia \"" + request.name() + "\" đã tồn tại.");
    }

    // Validate duplicate code
    if (countryRepository.existsByCodeIgnoreCase(request.code())) {
      throw new BadRequestException("Mã quốc gia \"" + request.code() + "\" đã tồn tại.");
    }

    // Mapping và lưu Entity
    Country entity = countryMapper.toEntity(request);
    Country savedEntity = countryRepository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));

    return countryMapper.toResponse(savedEntity);
  }

  @Override
  @Transactional
  public CountryResponse update(Integer id, CountryUpdateRequest request) {
    Country entity = findByIdOrThrow(id);
    ensurePermission(entity); // Check quyền trước khi update

    // Validate duplicate name (nếu có thay đổi)
    request
        .optionalName()
        .ifPresent(
            name -> {
              if (!name.equalsIgnoreCase(entity.getName())
                  && countryRepository.existsByNameIgnoreCase(name)) {
                throw new BadRequestException("Tên quốc gia \"" + name + "\" đã tồn tại.");
              }
            });

    // Validate duplicate code (nếu có thay đổi)
    request
        .optionalCode()
        .ifPresent(
            code -> {
              if (!code.equalsIgnoreCase(entity.getCode())
                  && countryRepository.existsByCodeIgnoreCase(code)) {
                throw new BadRequestException("Mã quốc gia \"" + code + "\" đã tồn tại.");
              }
            });

    // Update các field
    countryMapper.updateEntity(request, entity);
    Country updatedEntity = countryRepository.save(entity);
    afterUpdate(updatedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return countryMapper.toResponse(updatedEntity);
  }
}

