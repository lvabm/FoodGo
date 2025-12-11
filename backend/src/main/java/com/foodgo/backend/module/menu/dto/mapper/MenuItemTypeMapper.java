package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.menu.dto.response.MenuItemTypeResponse;
import com.foodgo.backend.module.menu.entity.MenuItemType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemTypeMapper
    extends BaseMapper<MenuItemType, Object, Object, MenuItemTypeResponse> {}
