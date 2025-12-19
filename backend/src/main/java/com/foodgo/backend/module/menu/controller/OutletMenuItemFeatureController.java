package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemFeatureResponse;
import com.foodgo.backend.module.menu.service.OutletMenuItemFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Outlet Menu Item Feature Mapping",
    description = "API Quản lý Tùy chọn (Features/Options) cho Món ăn Tùy chỉnh của Outlet.")
@RestController
@RequestMapping("/api/v1/outlets/menu-items/{outletMenuItemId}/features")
@RequiredArgsConstructor
public class OutletMenuItemFeatureController {

  private final OutletMenuItemFeatureService service;

  // 1. ADD FEATURE (CREATE) - Chỉ Owner/Admin
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Thêm Tùy chọn cho Món ăn",
      description =
          "Owner/Admin gán tùy chọn (ví dụ: Kích cỡ) với giá trị cụ thể. Service tự động kiểm tra quyền.")
  public OutletMenuItemFeatureResponse addFeature(
      @PathVariable Integer outletMenuItemId,
      @Valid @RequestBody OutletMenuItemFeatureCreateRequest request) {
    return service.addFeature(outletMenuItemId, request);
  }

  // 2. REMOVE FEATURE (HARD DELETE) - Chỉ Owner/Admin
  @DeleteMapping("/{featureId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa Tùy chọn khỏi Món ăn",
      description = "Owner/Admin loại bỏ một tùy chọn khỏi món ăn. Service tự động kiểm tra quyền.")
  public void removeFeature(
      @PathVariable Integer outletMenuItemId, @PathVariable Integer featureId) {
    service.removeFeature(outletMenuItemId, featureId);
  }

  // 3. LIST FEATURES (READ-ONLY) - Public
  @PermitAll
  @GetMapping
  @Operation(
      summary = "Lấy danh sách Tùy chọn của một Món ăn",
      description = "Public API: Xem tất cả các tùy chọn đã được gán cho món ăn tùy chỉnh này.")
  public List<OutletMenuItemFeatureResponse> listFeatures(@PathVariable Integer outletMenuItemId) {
    return service.listFeatures(outletMenuItemId);
  }
}
