package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemSubCategoryResponse;

public interface MenuItemSubCategoryService
    extends ReadableService<
        Integer, MenuItemSubCategoryFilterRequest, MenuItemSubCategoryResponse> {}
