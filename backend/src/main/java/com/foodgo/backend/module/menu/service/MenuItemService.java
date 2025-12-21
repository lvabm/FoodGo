package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;

import java.util.UUID;

public interface MenuItemService
    extends ReadableService<UUID, MenuItemFilterRequest, MenuItemResponse> {}
