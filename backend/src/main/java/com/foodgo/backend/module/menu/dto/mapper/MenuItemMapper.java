package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;
import com.foodgo.backend.module.menu.entity.MenuItem;
import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import com.foodgo.backend.module.location.entity.Province;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
  public MenuItemResponse toResponseDTO(MenuItem entity) {
    if (entity == null) return null;
    MenuItemResponse dto = new MenuItemResponse();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    dto.setIsPopular(entity.getIsPopular());
    if (entity.getSubCategory() != null) {
      dto.setSubCategoryId(entity.getSubCategory().getId());
    }
    if (entity.getProvince() != null) {
      dto.setProvinceId(entity.getProvince().getId());
    }
    return dto;
  }

  public MenuItem toEntity(
      MenuItemCreateRequest dto, MenuItemSubCategory subCategory, Province province) {
    if (dto == null) return null;
    MenuItem entity = new MenuItem();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setIsPopular(dto.getIsPopular());
    entity.setSubCategory(subCategory);
    entity.setProvince(province);
    return entity;
  }

  public void updateEntityFromDTO(
      MenuItem entity,
      MenuItemUpdateRequest dto,
      MenuItemSubCategory subCategory,
      Province province) {
    if (dto == null) return;
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setIsPopular(dto.getIsPopular());
    entity.setSubCategory(subCategory);
    entity.setProvince(province);
  }
}
