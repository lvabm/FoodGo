package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.location.dto.request.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.response.ProvinceResponse;

public interface ProvinceService
    extends ReadOnlyService<ProvinceResponse, Integer, ProvinceFilterRequest> {}
