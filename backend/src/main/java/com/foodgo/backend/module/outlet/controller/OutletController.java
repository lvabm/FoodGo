package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.outlet.dto.*;
import com.foodgo.backend.module.outlet.service.OutletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outlets")
public class OutletController {
  private final OutletService outletService;

  @Operation(summary = "Tạo quán ăn mới")
  @PostMapping
  public ResponseEntity<BaseResponse<OutletResponse>> createOutlet(
      @Valid @RequestBody CreateOutletRequest request) {
    OutletResponse data = outletService.createOutlet(request);
    BaseResponse<OutletResponse> body =
        BaseResponse.<OutletResponse>builder()
            .success(true)
            .message("Tạo quán ăn thành công")
            .data(data)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

  @Operation(summary = "Cập nhật thông tin quán")
  @PutMapping("/{id}")
  public ResponseEntity<BaseResponse<OutletResponse>> updateOutlet(
      @PathVariable Long id, @Valid @RequestBody UpdateOutletRequest request) {
    OutletResponse data = outletService.updateOutlet(id, request);
    BaseResponse<OutletResponse> body =
        BaseResponse.<OutletResponse>builder()
            .success(true)
            .message("Cập nhật quán thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Xem chi tiết quán ăn")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<OutletDetailResponse>> getOutletDetail(@PathVariable Long id) {
    OutletDetailResponse data = outletService.getOutletDetail(id);
    BaseResponse<OutletDetailResponse> body =
        BaseResponse.<OutletDetailResponse>builder()
            .success(true)
            .message("Chi tiết quán")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Danh sách quán ăn (công khai)")
  @GetMapping
  public ResponseEntity<PageResponse<OutletSummaryDto>> searchOutlets(
      @Valid Object outletSearchDto, Pageable pageable) {
    PageResponse<OutletSummaryDto> page = outletService.searchOutlets(outletSearchDto, pageable);
    return ResponseEntity.ok(page);
  }

  @Operation(summary = "Cập nhật giờ hoạt động")
  @PutMapping("/{id}/hours")
  public ResponseEntity<BaseResponse<OutletResponse>> updateHours(
      @PathVariable Long id, @Valid @RequestBody OperatingHoursRequest request) {
    OutletResponse data = outletService.updateOperatingHours(id, request);
    BaseResponse<OutletResponse> body =
        BaseResponse.<OutletResponse>builder()
            .success(true)
            .message("Cập nhật giờ hoạt động thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Tải hình ảnh quán")
  @PostMapping("/{id}/images")
  public ResponseEntity<BaseResponse<OutletImageResponse>> uploadImage(
      @PathVariable Long id, @RequestPart("file") MultipartFile file) {
    OutletImageResponse data = outletService.uploadOutletImage(id, file);
    BaseResponse<OutletImageResponse> body =
        BaseResponse.<OutletImageResponse>builder()
            .success(true)
            .message("Upload ảnh thành công")
            .data(data)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

  @Operation(summary = "Đặt ảnh đại diện chính")
  @PatchMapping("/{id}/images/{imageId}/primary")
  public ResponseEntity<BaseResponse<OutletImageResponse>> setPrimaryImage(
      @PathVariable Long id, @PathVariable Long imageId) {
    OutletImageResponse data = outletService.setPrimaryImage(id, imageId);
    BaseResponse<OutletImageResponse> body =
        BaseResponse.<OutletImageResponse>builder()
            .success(true)
            .message("Đặt ảnh đại diện thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Bật / tắt trạng thái hoạt động")
  @PatchMapping("/{id}/status")
  public ResponseEntity<BaseResponse<OutletResponse>> changeStatus(
      @PathVariable Long id, @RequestBody Object changeOutletStatusRequest) {
    OutletResponse data = outletService.changeOutletStatus(id, changeOutletStatusRequest);
    BaseResponse<OutletResponse> body =
        BaseResponse.<OutletResponse>builder()
            .success(true)
            .message("Thay đổi trạng thái quán thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Danh sách quán của chủ sở hữu")
  @GetMapping("/owner/{ownerId}")
  public ResponseEntity<PageResponse<OutletSummaryDto>> getByOwner(
      @PathVariable Long ownerId, Pageable pageable) {
    PageResponse<OutletSummaryDto> page = outletService.getOutletsByOwner(ownerId, pageable);
    return ResponseEntity.ok(page);
  }
}
