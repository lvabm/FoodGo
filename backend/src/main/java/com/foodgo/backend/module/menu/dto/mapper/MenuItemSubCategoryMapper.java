package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemSubCategoryResponse;
import com.foodgo.backend.module.menu.entity.MenuItemCategory;
import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import org.springframework.stereotype.Component;

@Component
public class MenuItemSubCategoryMapper {
  public MenuItemSubCategoryResponse toResponseDTO(MenuItemSubCategory entity) {
    if (entity == null) return null;
    MenuItemSubCategoryResponse dto = new MenuItemSubCategoryResponse();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    if (entity.getCategory() != null) {
      dto.setCategoryId(entity.getCategory().getId());
    }
    return dto;
  }

  public MenuItemSubCategory toEntity(
      MenuItemSubCategoryCreateRequest dto, MenuItemCategory category) {
    if (dto == null) return null;
    MenuItemSubCategory entity = new MenuItemSubCategory();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setCategory(category);
    return entity;
  }

  public void updateEntityFromDTO(
      MenuItemSubCategory entity, MenuItemSubCategoryUpdateRequest dto, MenuItemCategory category) {
    if (dto == null) return;
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setCategory(category);
  }
}
