package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemCategoryResponse;

public interface MenuItemCategoryService
    extends ReadableService<Integer, MenuItemCategoryFilterRequest, MenuItemCategoryResponse> {}
