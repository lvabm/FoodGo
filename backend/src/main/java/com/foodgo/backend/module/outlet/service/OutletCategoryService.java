package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;

public interface OutletCategoryService
    extends ReadOnlyService<OutletCategoryResponse, Integer, OutletCategoryFilterRequest> {}
