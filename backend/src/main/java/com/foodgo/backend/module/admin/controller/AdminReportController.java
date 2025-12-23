package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminReportService;
import com.foodgo.backend.module.review.dto.request.filter.ReviewReportFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewReportStatusUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin | Report Management", description = "API Quản lý báo cáo - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

  private final AdminReportService adminReportService;

  @GetMapping("/search")
  @Operation(summary = "Lấy danh sách báo cáo (có filter và phân trang)")
  public Page<ReviewReportResponse> getReports(
      @ParameterObject ReviewReportFilterRequest filterRequest,
      @ParameterObject Pageable pageable) {
    return adminReportService.getPage(filterRequest, pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết báo cáo")
  public ReviewReportResponse getReportDetail(@PathVariable Integer id) {
    return adminReportService.getDetail(id);
  }

  @PatchMapping("/{id}/status")
  @Operation(summary = "Cập nhật trạng thái báo cáo")
  public ReviewReportResponse updateStatus(
      @PathVariable Integer id,
      @RequestBody @Valid ReviewReportStatusUpdateRequest request) {
    return adminReportService.updateStatus(id, request);
  }
}

