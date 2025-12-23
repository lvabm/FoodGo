package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;

import java.util.List;
import java.util.UUID;

// Kế thừa BaseService cho Full CRUD
public interface OutletService
    extends BaseService<
        UUID, OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse> {

  List<OutletResponse> getOwnerOutlets();
}
