package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.module.menu.dto.request.MenuItemTypeCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemTypeUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemTypeResponse;
import com.foodgo.backend.module.menu.entity.MenuItemType;
import org.springframework.stereotype.Component;

@Component
public class MenuItemTypeMapper {

  public MenuItemTypeResponse toResponseDTO(MenuItemType entity) {
    if (entity == null) {
      return null;
    }
    MenuItemTypeResponse dto = new MenuItemTypeResponse();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    return dto;
  }

  public MenuItemType toEntity(MenuItemTypeCreateRequest dto) {
    if (dto == null) {
      return null;
    }
    MenuItemType entity = new MenuItemType();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  public void updateEntity(MenuItemType entity, MenuItemTypeUpdateRequest dto) {
    if (dto == null) {
      return;
    }
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
  }
}
