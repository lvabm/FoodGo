package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.MenuItemCategoryCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.MenuItemCategoryUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.MenuItemCategoryResponse;
import com.foodgo.backend.module.fnb.entity.MenuItemCategory;
import com.foodgo.backend.module.fnb.entity.MenuItemType;
import org.springframework.stereotype.Component;

@Component
public class MenuItemCategoryMapper {

    public MenuItemCategoryResponse toResponseDTO(MenuItemCategory entity) {
        if (entity == null)
            return null;
        MenuItemCategoryResponse dto = new MenuItemCategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        if (entity.getType() != null) {
            dto.setTypeId(entity.getType().getId());
        }
        return dto;
    }

    public MenuItemCategory toEntity(MenuItemCategoryCreateRequest dto, MenuItemType type) {
        if (dto == null)
            return null;
        MenuItemCategory entity = new MenuItemCategory();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setType(type);
        return entity;
    }

    public void updateEntityFromDTO(MenuItemCategory entity, MenuItemCategoryUpdateRequest dto, MenuItemType type) {
        if (dto == null)
            return;
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setType(type);
    }
}
