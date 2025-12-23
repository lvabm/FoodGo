package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.location.dto.request.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.request.create.CountryCreateRequest;
import com.foodgo.backend.module.location.dto.request.update.CountryUpdateRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;

public interface AdminCountryService
    extends BaseService<Integer, CountryCreateRequest, CountryUpdateRequest, CountryFilterRequest, CountryResponse> {}

