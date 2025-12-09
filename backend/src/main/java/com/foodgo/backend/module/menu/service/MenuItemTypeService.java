package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.menu.dto.request.MenuItemTypeFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemTypeResponse;

public interface MenuItemTypeService
    extends ReadOnlyService<MenuItemTypeResponse, Integer, MenuItemTypeFilterRequest> {}
