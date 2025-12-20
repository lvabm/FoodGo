package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.BaseMappingService;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;

public interface OutletMenuItemFeatureService
    extends BaseMappingService<
        OutletMenuItem,
        MenuItemFeature,
        OutletMenuItemFeature,
        OutletMenuItemFeatureCreateRequest,
        OutletMenuItemFeatureResponse> {}
