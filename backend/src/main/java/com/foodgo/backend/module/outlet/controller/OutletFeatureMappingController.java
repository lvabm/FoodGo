package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.service.OutletFeatureMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
    name = "Outlet Feature Mapping",
    description = "API Quản lý quan hệ Đặc điểm (Feature) của Outlet.")
@RestController
@RequestMapping("/api/v1/outlets/{outletId}/features")
@RequiredArgsConstructor
public class OutletFeatureMappingController {

  private final OutletFeatureMappingService service;

  // 1. ADD FEATURE (CREATE)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Thêm đặc điểm (Feature) cho Outlet",
      description = "Owner/Admin có thể gán một đặc điểm mới (ví dụ: 'Có Wifi') cho Outlet.")
  public OutletFeatureMappingResponse addFeature(
      @PathVariable UUID outletId, @Valid @RequestBody OutletFeatureMappingCreateRequest request) {
    return service.addFeatureToOutlet(outletId, request);
  }

  // 2. REMOVE FEATURE (HARD DELETE - Dùng cho bảng mapping)
  @DeleteMapping("/{featureId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa đặc điểm (Feature) khỏi Outlet",
      description = "Owner/Admin có thể loại bỏ một đặc điểm khỏi Outlet.")
  public void removeFeature(@PathVariable UUID outletId, @PathVariable Integer featureId) {
    service.removeFeatureFromOutlet(outletId, featureId);
  }
}
