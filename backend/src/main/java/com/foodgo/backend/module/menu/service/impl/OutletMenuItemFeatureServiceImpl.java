package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.service.impl.BaseMappingServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.menu.dto.mapper.OutletMenuItemFeatureMapper;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;
import com.foodgo.backend.module.menu.repository.MenuItemFeatureRepository;
import com.foodgo.backend.module.menu.repository.OutletMenuItemFeatureRepository;
import com.foodgo.backend.module.menu.repository.OutletMenuItemRepository;
import com.foodgo.backend.module.menu.service.OutletMenuItemFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutletMenuItemFeatureServiceImpl
    extends BaseMappingServiceImpl<
        OutletMenuItem,
        MenuItemFeature,
        OutletMenuItemFeature,
        OutletMenuItemFeatureCreateRequest,
        OutletMenuItemFeatureResponse>
    implements OutletMenuItemFeatureService {

  private final OutletMenuItemFeatureRepository mappingRepository;
  private final OutletMenuItemRepository outletMenuItemRepository;
  private final MenuItemFeatureRepository featureRepository;
  private final OutletMenuItemFeatureMapper mappingMapper;

  private final String outletMenuItemFeatureEntityName =
      EntityName.OUTLET_MENU_ITEM_FEATURE.getFriendlyName();

  @Override
  protected OutletMenuItem findMainEntityAndEnsurePermission(Object mainEntityId) {
    Integer id = (Integer) mainEntityId;
    OutletMenuItem item =
        outletMenuItemRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("OutletMenuItem id: " + id));

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!SecurityContext.isAdmin() && !item.getOutlet().getOwner().getId().equals(currentUserId)) {
      throw new ResourceNotFoundException(
          outletMenuItemFeatureEntityName + " không tìm thấy với ID: " + id);
    }
    return item;
  }

  @Override
  protected MenuItemFeature findExtendEntityOrThrow(Integer extendEntityId) {
    return featureRepository
        .findById(extendEntityId)
        .orElseThrow(() -> new ResourceNotFoundException("MenuItemFeature id: " + extendEntityId));
  }

  @Override
  protected boolean existsMapping(Object mainEntityId, Integer extendEntityId) {
    return mappingRepository.existsByOutletMenuItemIdAndFeatureId(
        (Integer) mainEntityId, extendEntityId);
  }

  @Override
  protected OutletMenuItemFeature createMappingEntity(OutletMenuItemFeatureCreateRequest request) {
    return mappingMapper.toEntity(request);
  }

  @Override
  protected void setEntities(
      OutletMenuItemFeature mapping, OutletMenuItem mainEntity, MenuItemFeature extendEntity) {
    mapping.setOutletMenuItem(mainEntity);
    mapping.setFeature(extendEntity);
  }

  @Override
  protected OutletMenuItemFeature saveMapping(OutletMenuItemFeature mapping) {
    return mappingRepository.save(mapping);
  }

  @Override
  protected OutletMenuItemFeatureResponse toResponse(OutletMenuItemFeature mapping) {
    return mappingMapper.toResponse(mapping);
  }

  @Override
  protected OutletMenuItemFeature findMapping(Object mainEntityId, Integer extendEntityId) {
    return mappingRepository
        .findByOutletMenuItemIdAndFeatureId((Integer) mainEntityId, extendEntityId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    outletMenuItemFeatureEntityName + " featureId: " + extendEntityId));
  }

  @Override
  protected void deleteMapping(OutletMenuItemFeature mapping) {
    mappingRepository.delete(mapping);
  }

  @Override
  protected String getEntityName() {
    return outletMenuItemFeatureEntityName;
  }

  @Override
  protected Object getId(OutletMenuItemFeature mapping) {
    return mapping.getId();
  }

  @Override
  protected Integer getExtendEntityId(OutletMenuItemFeatureCreateRequest request) {
    return request.featureId();
  }

  @Override
  protected List<OutletMenuItemFeature> findAllMappingsByMainEntityId(Object mainEntityId) {
    return mappingRepository.findAllByOutletMenuItemId((Integer) mainEntityId);
  }
}
