package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.location.dto.request.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.response.ProvinceResponse;

public interface ProvinceService
    extends ReadableService<Integer, ProvinceFilterRequest, ProvinceResponse> {}
