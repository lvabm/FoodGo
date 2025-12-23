package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.CreatableService;
import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.UpdatableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;

import java.util.UUID;

public interface AdminMenuItemService
    extends CreatableService<MenuItemCreateRequest, MenuItemResponse>,
        UpdatableService<UUID, MenuItemUpdateRequest, MenuItemResponse>,
        DeletableService<UUID, MenuItemResponse> {}
