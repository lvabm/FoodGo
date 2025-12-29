package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.module.outlet.service.PublicStatisticsService;
import com.foodgo.backend.module.outlet.dto.response.PublicStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller cho thống kê công khai (Public Statistics)
 * Cho phép khách vãng lai xem số liệu tổng quan
 */
@Tag(name = "Public Statistics", description = "API Thống kê công khai cho trang chủ")
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class PublicStatisticsController {

  private final PublicStatisticsService publicStatisticsService;

  @PermitAll
  @GetMapping("/public")
  @Operation(
      summary = "Lấy thống kê công khai",
      description = "Lấy số liệu tổng quan: tổng số quán, món ăn, đánh giá, người dùng (cho phép khách vãng lai)"
  )
  public ResponseEntity<BaseResponse<PublicStatisticsResponse>> getPublicStatistics() {
    PublicStatisticsResponse statistics = publicStatisticsService.getPublicStatistics();
    
    return ResponseEntity.ok(
        BaseResponse.<PublicStatisticsResponse>builder()
            .success(true)
            .message("Lấy thống kê thành công")
            .data(statistics)
            .build()
    );
  }
}

