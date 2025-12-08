package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.location.dto.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.ProvinceResponse;

public interface ProvinceService
    extends ReadOnlyService<ProvinceResponse, Integer, ProvinceFilterRequest> {}
