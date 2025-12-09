package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.module.outlet.dto.request.OutletFeatureMappingRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;

import java.util.UUID;

public interface OutletFeatureMappingService {

  // Thêm một Feature vào Outlet
  OutletFeatureMappingResponse addFeatureToOutlet(
      UUID outletId, OutletFeatureMappingRequest request, UUID ownerId);

  // Xóa một Feature khỏi Outlet
  void removeFeatureFromOutlet(UUID outletId, Integer featureId, UUID ownerId);
}
