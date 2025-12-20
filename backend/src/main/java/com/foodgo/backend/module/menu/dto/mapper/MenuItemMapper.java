package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;
import com.foodgo.backend.module.menu.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemMapper
    extends BaseMapper<MenuItem, MenuItemCreateRequest, MenuItemUpdateRequest, MenuItemResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "subCategoryId", source = "subCategory.id")
  @Mapping(target = "subCategoryName", source = "subCategory.name")
  @Mapping(target = "provinceId", source = "province.id")
  @Mapping(target = "provinceName", source = "province.name")
  MenuItemResponse toResponse(MenuItem entity);
}
