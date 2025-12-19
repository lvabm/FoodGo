package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.service.OutletCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
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

  private final OutletCategoryService service;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Danh mục Outlet (Không phân trang)")
  public List<OutletCategoryResponse> getAll() {
    return service.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Danh mục Outlet theo ID")
  public OutletCategoryResponse getDetail(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Danh mục Outlet")
  public Page<OutletCategoryResponse> search(
      @ModelAttribute OutletCategoryFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }
}
