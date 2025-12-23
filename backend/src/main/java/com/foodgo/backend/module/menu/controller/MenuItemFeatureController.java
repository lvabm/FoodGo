package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;
import com.foodgo.backend.module.menu.service.MenuItemFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Menu Item Feature Management (Read Only)",
    description = "Quản lý dữ liệu Đặc điểm Món ăn (Ví dụ: Kích cỡ, độ cay)")
@RestController
@RequestMapping("/api/v1/menu-item-features")
@RequiredArgsConstructor
public class MenuItemFeatureController {

  private final MenuItemFeatureService service;

  @GetMapping
  @Operation(summary = "Lấy tất cả Đặc điểm Món ăn (Không phân trang)")
  public List<MenuItemFeatureResponse> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Đặc điểm Món ăn theo ID")
  public MenuItemFeatureResponse getDetail(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Đặc điểm Món ăn")
  public Page<MenuItemFeatureResponse> search(
      @ModelAttribute MenuItemFeatureFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }
}
