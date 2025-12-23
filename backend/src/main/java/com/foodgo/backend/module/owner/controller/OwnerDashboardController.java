package com.foodgo.backend.module.owner.controller;

import com.foodgo.backend.module.owner.dto.OwnerDashboardStats;
import com.foodgo.backend.module.owner.service.OwnerDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owner/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
@Tag(name = "Owner Dashboard", description = "API thống kê dành cho chủ quán")
public class OwnerDashboardController {

  private final OwnerDashboardService dashboardService;

  @GetMapping("/stats")
  @Operation(summary = "Lấy thống kê tổng quan cho owner (all outlets)")
  public OwnerDashboardStats getDashboardStats() {
    return dashboardService.getOwnerStats();
  }

  @GetMapping("/stats/{outletId}")
  @Operation(summary = "Lấy thống kê chi tiết cho 1 outlet")
  public OwnerDashboardStats getOutletStats(@PathVariable UUID outletId) {
    return dashboardService.getOutletStats(outletId);
  }
}
