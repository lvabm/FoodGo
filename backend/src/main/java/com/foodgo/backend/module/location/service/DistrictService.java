package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.location.dto.DistrictFilterRequest;
import com.foodgo.backend.module.location.dto.DistrictResponse;

public interface DistrictService
    extends ReadOnlyService<DistrictResponse, Integer, DistrictFilterRequest> {}
