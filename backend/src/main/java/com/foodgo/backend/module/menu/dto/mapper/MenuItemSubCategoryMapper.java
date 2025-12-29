package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.menu.dto.response.MenuItemSubCategoryResponse;
import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemSubCategoryMapper
    extends BaseMapper<MenuItemSubCategory, Object, Object, MenuItemSubCategoryResponse> {

  @Override
  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "categoryName", source = "category.name") // Mapping từ quan hệ MenuItemCategory
  MenuItemSubCategoryResponse toResponse(MenuItemSubCategory entity);
}
