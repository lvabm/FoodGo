package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.menu.dto.response.MenuItemCategoryResponse;
import com.foodgo.backend.module.menu.entity.MenuItemCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemCategoryMapper
    extends BaseMapper<MenuItemCategory, Object, Object, MenuItemCategoryResponse> {

  @Override
  @Mapping(target = "typeId", source = "type.id")
  @Mapping(target = "typeName", source = "type.name") // Mapping từ quan hệ MenuItemType
  MenuItemCategoryResponse toResponse(MenuItemCategory entity);
}
