package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.location.dto.request.DistrictFilterRequest;
import com.foodgo.backend.module.location.dto.response.DistrictResponse;

public interface DistrictService
    extends ReadableService<Integer, DistrictFilterRequest, DistrictResponse> {}
