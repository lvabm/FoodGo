package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.outlet.dto.request.OutletFeatureFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureResponse;

public interface OutletFeatureService
    extends ReadOnlyService<OutletFeatureResponse, Integer, OutletFeatureFilterRequest> {}
