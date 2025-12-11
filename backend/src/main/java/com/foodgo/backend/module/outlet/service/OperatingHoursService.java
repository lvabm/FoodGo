package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.outlet.dto.request.create.OperatingHoursCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OperatingHoursFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OperatingHoursUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OperatingHoursResponse;

public interface OperatingHoursService
    extends BaseService<
        OperatingHoursCreateRequest,
        OperatingHoursUpdateRequest,
        OperatingHoursFilterRequest,
        OperatingHoursResponse,
        Integer> {}
