package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletTypeResponse;

public interface OutletTypeService
    extends ReadableService<Integer, OutletTypeFilterRequest, OutletTypeResponse> {}
