package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.outlet.dto.request.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;

import java.util.UUID;

// Kế thừa BaseService cho Full CRUD
public interface OutletService
    extends BaseService<
        OutletRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse, UUID> {

  // Phương thức tạo/cập nhật tùy chỉnh để nhận Owner ID từ Security Context
  OutletResponse createOutlet(OutletRequest request, UUID ownerId);

  OutletResponse updateOutlet(UUID id, OutletUpdateRequest request, UUID ownerId);

  // Phương thức tìm kiếm chung sẽ dùng getPage từ BaseService
}
