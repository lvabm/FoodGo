package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletTypeResponse;
import com.foodgo.backend.module.outlet.service.OutletTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Outlet Type Management (Read Only)",
    description = "Quản lý dữ liệu Loại hình Cơ sở (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/outlet-types")
@RequiredArgsConstructor
public class OutletTypeController {

  private final OutletTypeService outletTypeService;

  @GetMapping
  @Operation(summary = "Lấy tất cả Loại hình Cơ sở (Không phân trang)")
  public List<OutletTypeResponse> getAll() {
    return outletTypeService.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Loại hình Cơ sở theo ID")
  public OutletTypeResponse getDetail(@PathVariable Integer id) {
    return outletTypeService.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Loại hình Cơ sở")
  public Page<OutletTypeResponse> search(
      @ModelAttribute OutletTypeFilterRequest filter, Pageable pageable) {
    return outletTypeService.getPage(filter, pageable);
  }
}
