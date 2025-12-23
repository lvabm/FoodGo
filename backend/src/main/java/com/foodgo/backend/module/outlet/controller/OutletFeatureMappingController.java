package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.service.OutletFeatureMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
    name = "Outlet Feature Mapping",
    description = "API Quản lý các Feature/Tiện ích (Wifi, AC...) của Outlet.")
@RestController
@RequestMapping("/api/v1/outlets/{outletId}/features")
@RequiredArgsConstructor
public class OutletFeatureMappingController {

  private final OutletFeatureMappingService outletFeatureMappingService;

  // 1. ADD FEATURE (CREATE) - Chỉ Owner/Admin
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Thêm Feature/Tiện ích cho Outlet",
      description =
          "Owner/Admin gán một tiện ích (ví dụ: Wi-Fi) cho Outlet. Service tự động kiểm tra quyền.")
  public OutletFeatureMappingResponse addFeature(
      @PathVariable UUID outletId, @Valid @RequestBody OutletFeatureMappingCreateRequest request) {
    return outletFeatureMappingService.addFeature(outletId, request);
  }

  // 2. REMOVE FEATURE (HARD DELETE) - Chỉ Owner/Admin
  @DeleteMapping("/{featureId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa Feature/Tiện ích khỏi Outlet",
      description = "Owner/Admin loại bỏ một tiện ích khỏi Outlet. Service tự động kiểm tra quyền.")
  public void removeFeature(@PathVariable UUID outletId, @PathVariable Integer featureId) {
    outletFeatureMappingService.removeFeature(outletId, featureId);
  }

  // 3. LIST FEATURES (READ-ONLY) - Public
  @PermitAll
  @GetMapping
  @Operation(
      summary = "Lấy danh sách Feature của một Outlet",
      description = "Public API: Xem tất cả các tiện ích đã được gán cho Outlet này.")
  public List<OutletFeatureMappingResponse> listFeatures(@PathVariable UUID outletId) {
    return outletFeatureMappingService.listFeatures(outletId);
  }
}
