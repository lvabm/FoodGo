package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.outlet.dto.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.OutletTypeResponse;

public interface OutletTypeService
    extends ReadOnlyService<OutletTypeResponse, Integer, OutletTypeFilterRequest> {}
