package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;

import java.util.UUID;

public interface OutletFeatureMappingService {

  // Thêm một Feature vào Outlet
  OutletFeatureMappingResponse addFeatureToOutlet(
      UUID outletId, OutletFeatureMappingCreateRequest request);

  // Xóa một Feature khỏi Outlet (dùng hard delete cho bảng mapping)
  void removeFeatureFromOutlet(UUID outletId, Integer featureId);
}
