package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFeatureFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureResponse;

public interface OutletFeatureService
    extends ReadableService<Integer, OutletFeatureFilterRequest, OutletFeatureResponse> {}
