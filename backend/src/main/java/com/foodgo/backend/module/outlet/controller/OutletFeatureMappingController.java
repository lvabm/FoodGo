package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.outlet.dto.request.OutletFeatureMappingRequest;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Thêm đặc điểm (Feature) cho Outlet",
      description = "Owner có thể gán một đặc điểm mới (ví dụ: 'Có Wifi') cho Outlet.")
  public OutletFeatureMappingResponse addFeature(
      @PathVariable UUID outletId, @Valid @RequestBody OutletFeatureMappingRequest request) {
    UUID ownerId = SecurityContext.getCurrentUserId();
    return service.addFeatureToOutlet(outletId, request, ownerId);
  }

  @DeleteMapping("/{featureId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa đặc điểm (Feature) khỏi Outlet",
      description = "Owner có thể loại bỏ một đặc điểm khỏi Outlet.")
  public void removeFeature(@PathVariable UUID outletId, @PathVariable Integer featureId) {
    UUID ownerId = SecurityContext.getCurrentUserId();
    service.removeFeatureFromOutlet(outletId, featureId, ownerId);
  }
}
