package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;

import java.util.UUID;

public interface MenuItemService
    extends BaseService<
        UUID,
        MenuItemCreateRequest,
        MenuItemUpdateRequest,
        MenuItemFilterRequest,
        MenuItemResponse> {}
