package com.foodgo.backend.module.location.service;

import com.foodgo.backend.common.base.service.ReadOnlyService;
import com.foodgo.backend.module.location.dto.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.CountryResponse;

public interface CountryService
    extends ReadOnlyService<CountryResponse, Integer, CountryFilterRequest> {}
