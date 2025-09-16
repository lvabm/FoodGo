package com.foodgo.backend.module.admin_r.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.outlet.OutletAdminDto;
import com.foodgo.backend.module.admin_r.service.AdminOutletService;
import com.foodgo.backend.module.outlet_d.dto.OutletResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/outlets")
@RequiredArgsConstructor
public class AdminOutletController {
  private final AdminOutletService adminOutletService;

  @Operation(summary = "Danh sách quán ăn (admin)")
  @GetMapping
  public ResponseEntity<PageResponse<OutletAdminDto>> getOutlets(
      Object filterDto, Pageable pageable) {
    PageResponse<OutletAdminDto> page = adminOutletService.getOutlets(filterDto, pageable);
    return ResponseEntity.ok(page);
  }

  @Operation(summary = "Duyệt/quyết định quán (admin)")
  @PatchMapping("/{id}/approve")
  public ResponseEntity<BaseResponse<OutletResponse>> approveOutlet(
      @PathVariable Long id, @RequestBody Object approveOutletRequest) {

    OutletResponse data = adminOutletService.approveOutlet(id, approveOutletRequest);

    BaseResponse<OutletResponse> body =
        BaseResponse.<OutletResponse>builder()
            .success(true)
            .message("Duyệt/quyết định quán thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Chi tiết quán (admin)")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<OutletAdminDto>> getAdminDetail(@PathVariable Long id) {
    OutletAdminDto data = adminOutletService.getOutletAdminDetail(id);
    BaseResponse<OutletAdminDto> body =
        BaseResponse.<OutletAdminDto>builder()
            .success(true)
            .message("Chi tiết quán (admin)")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }
}
