package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.service.OutletCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Outlet Category Management (Read Only)",
    description = "Quản lý dữ liệu Danh mục Outlet (Ví dụ: Ẩm thực Ý)")
@RestController
@RequestMapping("/api/v1/outlet-categories")
@RequiredArgsConstructor
public class OutletCategoryController {

  private final OutletCategoryService outletCategoryService;

  @GetMapping
  @Operation(summary = "Lấy tất cả Danh mục Outlet (Không phân trang)")
  public List<OutletCategoryResponse> getAll() {
    return outletCategoryService.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Danh mục Outlet theo ID")
  public OutletCategoryResponse getDetail(@PathVariable Integer id) {
    return outletCategoryService.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Danh mục Outlet")
  public Page<OutletCategoryResponse> search(
      @ModelAttribute OutletCategoryFilterRequest filter, Pageable pageable) {
    return outletCategoryService.getPage(filter, pageable);
  }
}
