package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.outlet.dto.request.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;

import java.util.UUID;

// Kế thừa BaseService cho Full CRUD
public interface OutletService
    extends BaseService<
        OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse, UUID> {}
