package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseMappingService;
import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;

public interface OutletFeatureMappingService
    extends BaseMappingService<
        Outlet,
        OutletFeature,
        OutletFeatureMapping,
        OutletFeatureMappingCreateRequest,
        OutletFeatureMappingResponse> {}
