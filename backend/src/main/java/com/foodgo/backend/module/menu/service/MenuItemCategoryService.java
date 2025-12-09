package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.menu.dto.request.MenuItemCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemCategoryResponse;

public interface MenuItemCategoryService
    extends ReadOnlyService<MenuItemCategoryResponse, Integer, MenuItemCategoryFilterRequest> {}
