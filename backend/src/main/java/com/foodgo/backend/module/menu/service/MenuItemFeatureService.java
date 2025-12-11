package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;

public interface MenuItemFeatureService
    extends ReadableService<Integer, MenuItemFeatureFilterRequest, MenuItemFeatureResponse> {}
