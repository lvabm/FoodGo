package com.foodgo.backend.module.advertisement.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/ads")
public class AdvertisementController {

  @Operation(summary = "Quản lý vị trí/thời hạn quảng cáo")
  @PutMapping("/NA")
  private void updateMarketingPosition() {}
}
