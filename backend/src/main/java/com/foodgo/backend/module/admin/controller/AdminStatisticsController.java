package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.module.admin.dto.response.AdminStatisticsResponse;
import com.foodgo.backend.module.admin.service.AdminStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Statistics", description = "API Thống kê cho Admin")
@RestController
@RequestMapping("/api/v1/admin/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatisticsController {

  private final AdminStatisticsService adminStatisticsService;

  @GetMapping
  @Operation(
      summary = "Lấy thống kê tổng quan",
      description = "Lấy tất cả thống kê cho admin dashboard (100% dữ liệu thật từ database)"
  )
  public ResponseEntity<BaseResponse<AdminStatisticsResponse>> getStatistics() {
    AdminStatisticsResponse statistics = adminStatisticsService.getStatistics();
    
    return ResponseEntity.ok(
        BaseResponse.<AdminStatisticsResponse>builder()
            .success(true)
            .message("Lấy thống kê thành công")
            .data(statistics)
            .build()
    );
  }
}


