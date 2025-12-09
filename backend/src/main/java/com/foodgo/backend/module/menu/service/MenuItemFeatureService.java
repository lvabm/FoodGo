package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;

public interface MenuItemFeatureService
    extends ReadOnlyService<MenuItemFeatureResponse, Integer, MenuItemFeatureFilterRequest> {}
