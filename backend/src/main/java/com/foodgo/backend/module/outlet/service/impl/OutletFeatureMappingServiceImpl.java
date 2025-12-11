package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.service.impl.BaseMappingServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.mapper.OutletFeatureMappingMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import com.foodgo.backend.module.outlet.repository.OutletFeatureMappingRepository;
import com.foodgo.backend.module.outlet.repository.OutletFeatureRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletFeatureMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OutletFeatureMappingServiceImpl
    extends BaseMappingServiceImpl<
        Outlet,
        OutletFeature,
        OutletFeatureMapping,
        OutletFeatureMappingCreateRequest,
        OutletFeatureMappingResponse>
    implements OutletFeatureMappingService {

  private final OutletFeatureMappingRepository mappingRepository;
  private final OutletRepository outletRepository;
  private final OutletFeatureRepository featureRepository;
  private final OutletFeatureMappingMapper mappingMapper;

  private final String outletFeatureMappingEntityName =
      EntityName.OUTLET_FEATURE_MAPPING.getFriendlyName();

  // --- Triển khai Abstract Methods từ BaseMappingServiceImpl ---

  @Override
  protected Outlet findMainEntityAndEnsurePermission(Object mainEntityId) {
    UUID outletId = (UUID) mainEntityId;
    Outlet outlet =
        outletRepository
            .findById(outletId)
            .orElseThrow(() -> new ResourceNotFoundException("Outlet" + " id: " + outletId));

    UUID currentUserId = SecurityContext.getCurrentUserId();

    if (SecurityContext.isAdmin()) return outlet;
    if (!outlet.getOwner().getId().equals(currentUserId)) {
      throw new ResourceNotFoundException("Outlet" + " id: " + outletId);
    }
    return outlet;
  }

  @Override
  protected OutletFeature findExtendEntityOrThrow(Integer extendEntityId) {
    return featureRepository
        .findById(extendEntityId)
        .orElseThrow(
            () -> new ResourceNotFoundException("OutletFeature" + " id " + extendEntityId));
  }

  @Override
  protected boolean existsMapping(Object mainEntityId, Integer extendEntityId) {
    return mappingRepository.existsByOutletIdAndFeatureId((UUID) mainEntityId, extendEntityId);
  }

  @Override
  protected OutletFeatureMapping createMappingEntity(OutletFeatureMappingCreateRequest request) {
    // MapStruct cho việc tạo Entity Mapping
    return mappingMapper.toEntity(request);
  }

  @Override
  protected void setEntities(
      OutletFeatureMapping mapping, Outlet mainEntity, OutletFeature extendEntity) {
    mapping.setOutlet(mainEntity);
    mapping.setFeature(extendEntity);
  }

  @Override
  protected OutletFeatureMapping saveMapping(OutletFeatureMapping mapping) {
    return mappingRepository.save(mapping);
  }

  @Override
  protected OutletFeatureMappingResponse toResponse(OutletFeatureMapping mapping) {
    return mappingMapper.toResponse(mapping);
  }

  @Override
  protected OutletFeatureMapping findMapping(Object mainEntityId, Integer extendEntityId) {
    return mappingRepository
        .findByOutletIdAndFeatureId((UUID) mainEntityId, extendEntityId)
        .orElseThrow(
            () -> new ResourceNotFoundException(getEntityName() + " featureId " + extendEntityId));
  }

  @Override
  protected void deleteMapping(OutletFeatureMapping mapping) {
    mappingRepository.delete(mapping);
  }

  @Override
  protected String getEntityName() {
    return outletFeatureMappingEntityName;
  }

  @Override
  protected Object getId(OutletFeatureMapping mapping) {
    return mapping.getId();
  }

  @Override
  protected Integer getExtendEntityId(OutletFeatureMappingCreateRequest request) {
    return request.featureId();
  }

  @Override
  protected List<OutletFeatureMapping> findAllMappingsByMainEntityId(Object mainEntityId) {
    return mappingRepository.findAllByOutletId((UUID) mainEntityId);
  }
}
