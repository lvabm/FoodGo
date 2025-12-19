package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemCategoryResponse;
import com.foodgo.backend.module.menu.service.MenuItemCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Menu Item Category Management (Read Only)",
    description = "Quản lý dữ liệu Danh mục Món ăn (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/menu-item-categories")
@RequiredArgsConstructor
public class MenuItemCategoryController {

  private final MenuItemCategoryService menuItemCategoryService;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Danh mục Món ăn (Không phân trang)")
  public List<MenuItemCategoryResponse> getAll() {
    return menuItemCategoryService.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Danh mục Món ăn theo ID")
  public MenuItemCategoryResponse getDetail(@PathVariable Integer id) {
    return menuItemCategoryService.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Danh mục Món ăn")
  public Page<MenuItemCategoryResponse> search(
      @ModelAttribute MenuItemCategoryFilterRequest filter, Pageable pageable) {
    return menuItemCategoryService.getPage(filter, pageable);
  }
}
