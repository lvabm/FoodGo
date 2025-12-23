package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminOutletCategoryService;
import com.foodgo.backend.module.outlet.dto.criteria.OutletCategorySearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletCategoryMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCategoryCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletCategoryUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import com.foodgo.backend.module.outlet.repository.OutletCategoryRepository;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
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
public class AdminOutletCategoryServiceImpl
    extends BaseServiceImpl<
        OutletCategory,
        Integer,
        OutletCategoryCreateRequest,
        OutletCategoryUpdateRequest,
        OutletCategoryFilterRequest,
        OutletCategoryResponse>
    implements AdminOutletCategoryService {

  private final OutletCategoryRepository outletCategoryRepository;
  private final OutletCategoryMapper outletCategoryMapper;
  private final OutletTypeRepository outletTypeRepository;

  private final String entityName = EntityName.OUTLET_CATEGORY.getFriendlyName();

  // --- BASE IMPLEMENTATION ---

  @Override
  protected JpaRepository<OutletCategory, Integer> getRepository() {
    return outletCategoryRepository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletCategory> getSpecRepository() {
    return outletCategoryRepository;
  }

  @Override
  protected BaseMapper<
          OutletCategory,
          OutletCategoryCreateRequest,
          OutletCategoryUpdateRequest,
          OutletCategoryResponse>
      getMapper() {
    return outletCategoryMapper;
  }

  @Override
  protected String getEntityName() {
    return entityName;
  }

  @Override
  protected Specification<OutletCategory> buildSpecification(
      OutletCategoryFilterRequest filterRequest) {
    return new OutletCategorySearchSpecification(filterRequest);
  }

  // --- SECURITY HOOK ---

  @Override
  protected void ensurePermission(OutletCategory entity) {
    // Chỉ Admin mới có quyền CRUD OutletCategory
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException(
          "Chỉ Admin mới có quyền thao tác với " + getEntityName() + ".");
    }
  }

  // --- CUSTOM CREATE/UPDATE ---

  @Override
  @Transactional
  public OutletCategoryResponse create(OutletCategoryCreateRequest request) {
    ensurePermission(null); // Kiểm tra quyền Admin trước khi tạo

    // 1. Validate FK tồn tại
    if (!outletTypeRepository.existsById(request.typeId())) {
      throw new ResourceNotFoundException("OutletType" + " id: " + request.typeId());
    }

    // 2. Mapping và gán Entity
    OutletCategory entity = outletCategoryMapper.toEntity(request);
    entity.setType(outletTypeRepository.getReferenceById(request.typeId()));

    // 3. Lưu và hoàn tất
    OutletCategory savedEntity = outletCategoryRepository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));

    return outletCategoryMapper.toResponse(savedEntity);
  }

  @Override
  @Transactional
  public OutletCategoryResponse update(Integer id, OutletCategoryUpdateRequest request) {
    OutletCategory entity = findByIdOrThrow(id);
    ensurePermission(entity); // Check quyền trước khi update

    // Update typeId nếu có
    request
        .optionalTypeId()
        .ifPresent(
            typeId -> {
              if (!outletTypeRepository.existsById(typeId)) {
                throw new ResourceNotFoundException("OutletType" + " id: " + typeId);
              }
              entity.setType(outletTypeRepository.getReferenceById(typeId));
            });

    // Update các field khác
    outletCategoryMapper.updateEntity(request, entity);
    OutletCategory updatedEntity = outletCategoryRepository.save(entity);
    afterUpdate(updatedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return outletCategoryMapper.toResponse(updatedEntity);
  }
}

