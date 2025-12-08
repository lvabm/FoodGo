package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;
import org.springframework.stereotype.Component;

@Component
public class OutletMenuItemFeatureMapper {
  public OutletMenuItemFeatureResponse toResponse(OutletMenuItemFeature entity) {
    if (entity == null) return null;
    OutletMenuItemFeatureResponse dto = new OutletMenuItemFeatureResponse();
    dto.setId(entity.getId());
    dto.setValue(entity.getValue());
    if (entity.getFeature() != null) {
      dto.setFeatureId(entity.getFeature().getId());
    }
    if (entity.getOutletMenuItem() != null) {
      dto.setOutletMenuItemId(entity.getOutletMenuItem().getId());
    }
    return dto;
  }

  public OutletMenuItemFeature toEntity(
      OutletMenuItemFeatureCreateRequest dto,
      MenuItemFeature feature,
      OutletMenuItem outletMenuItem) {
    if (dto == null) return null;
    OutletMenuItemFeature entity = new OutletMenuItemFeature();
    entity.setValue(dto.getValue());
    entity.setFeature(feature);
    entity.setOutletMenuItem(outletMenuItem);
    return entity;
  }

  public void updateEntity(
      OutletMenuItemFeature entity,
      OutletMenuItemFeatureUpdateRequest dto,
      MenuItemFeature feature,
      OutletMenuItem outletMenuItem) {
    if (dto == null) return;
    entity.setValue(dto.getValue());
    entity.setFeature(feature);
    entity.setOutletMenuItem(outletMenuItem);
  }
}
