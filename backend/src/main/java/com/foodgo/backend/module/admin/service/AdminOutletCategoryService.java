package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.CreatableService;
import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.UpdatableService;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCategoryCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletCategoryUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;

public interface AdminOutletCategoryService
    extends CreatableService<OutletCategoryCreateRequest, OutletCategoryResponse>,
        UpdatableService<Integer, OutletCategoryUpdateRequest, OutletCategoryResponse>,
        DeletableService<Integer, OutletCategoryResponse> {}

