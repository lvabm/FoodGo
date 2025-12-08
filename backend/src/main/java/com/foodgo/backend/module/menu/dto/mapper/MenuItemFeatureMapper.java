package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import org.springframework.stereotype.Component;

@Component
public class MenuItemFeatureMapper {
  public MenuItemFeatureResponse toResponse(MenuItemFeature entity) {
    if (entity == null) return null;
    MenuItemFeatureResponse dto = new MenuItemFeatureResponse();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setFeatureType(entity.getFeatureType());
    dto.setValueType(entity.getValueType());
    dto.setPossibleValues(entity.getPossibleValues());
    dto.setDescription(entity.getDescription());
    return dto;
  }

  public MenuItemFeature toEntity(MenuItemFeatureCreateRequest dto) {
    if (dto == null) return null;
    MenuItemFeature entity = new MenuItemFeature();
    entity.setName(dto.getName());
    entity.setFeatureType(dto.getFeatureType());
    entity.setValueType(dto.getValueType());
    entity.setPossibleValues(dto.getPossibleValues());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  public void updateEntity(MenuItemFeature entity, MenuItemFeatureUpdateRequest dto) {
    if (dto == null) return;
    entity.setName(dto.getName());
    entity.setFeatureType(dto.getFeatureType());
    entity.setValueType(dto.getValueType());
    entity.setPossibleValues(dto.getPossibleValues());
    entity.setDescription(dto.getDescription());
  }
}
