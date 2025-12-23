package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletFeatureFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureResponse;
import com.foodgo.backend.module.outlet.service.OutletFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Outlet Feature Management (Read Only)",
    description = "Quản lý dữ liệu Đặc điểm nổi bật của Outlet (Ví dụ: Có Wifi)")
@RestController
@RequestMapping("/api/v1/outlet-features")
@RequiredArgsConstructor
public class OutletFeatureController {

  private final OutletFeatureService outletFeatureService;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Đặc điểm Outlet (Không phân trang)")
  public List<OutletFeatureResponse> getAll() {
    return outletFeatureService.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Đặc điểm Outlet theo ID")
  public OutletFeatureResponse getDetail(@PathVariable Integer id) {
    return outletFeatureService.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Đặc điểm Outlet")
  public Page<OutletFeatureResponse> search(
      @ModelAttribute OutletFeatureFilterRequest filter, Pageable pageable) {
    return outletFeatureService.getPage(filter, pageable);
  }
}
