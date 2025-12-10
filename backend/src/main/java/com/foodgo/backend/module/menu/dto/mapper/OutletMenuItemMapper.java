package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletMenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletMenuItemMapper
    extends BaseMapper<
        OutletMenuItem,
        OutletMenuItemCreateRequest,
        OutletMenuItemUpdateRequest,
        OutletMenuItemResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "outletId", source = "outlet.id")
  @Mapping(target = "outletName", source = "outlet.name")
  @Mapping(target = "menuItemId", source = "menuItem.id")
  @Mapping(target = "menuItemName", source = "menuItem.name")
  OutletMenuItemResponse toResponse(OutletMenuItem entity);
}
