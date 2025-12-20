package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.location.dto.request.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;

public interface CountryService
    extends ReadableService<Integer, CountryFilterRequest, CountryResponse> {}
