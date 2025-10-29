package com.foodgo.backend.module.outlet_d.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/outlets")
public class OutletController {

  @Operation(summary = "Đăng ký Hồ sơ Quán ăn")
  @PostMapping()
  private void createOutlet() {}

  @Operation(summary = "Hiển thị hình ảnh quán ăn")
  @GetMapping("/{outletId}/images")
  private void getOutletImages(@PathVariable Integer outletId) {}

  @Operation(summary = "Hiển thị menu của quán (Người Dùng)")
  @GetMapping("/{outletId}/menu")
  private void getOutletMenu(@PathVariable Integer outletId) {}

  @Operation(summary = "Hiển thị trạng thái quán")
  @GetMapping("/{outletId}/status")
  private void getOutletStatus(@PathVariable Integer outletId) {}

  @Operation(summary = "Xem chi tiết quán ăn (Người Dùng)")
  @GetMapping("/{outletId}")
  private void getOutletDetail(@PathVariable Integer outletId) {}
}
