package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemFeatureMapper
    extends BaseMapper<MenuItemFeature, Object, Object, MenuItemFeatureResponse> {}
