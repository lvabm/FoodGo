package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemSubCategoryResponse;
import com.foodgo.backend.module.menu.service.MenuItemSubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Menu Item Sub Category Management (Read Only)",
    description = "Quản lý dữ liệu Danh mục Con Món ăn (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/menu-item-sub-categories")
@RequiredArgsConstructor
public class MenuItemSubCategoryController {

  private final MenuItemSubCategoryService menuItemSubCategoryService;

  @GetMapping
  @Operation(summary = "Lấy tất cả Danh mục Con Món ăn (Không phân trang)")
  public List<MenuItemSubCategoryResponse> getAll() {
    return menuItemSubCategoryService.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Danh mục Con Món ăn theo ID")
  public MenuItemSubCategoryResponse getDetail(@PathVariable Integer id) {
    return menuItemSubCategoryService.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Danh mục Con Món ăn")
  public Page<MenuItemSubCategoryResponse> search(
      @ModelAttribute MenuItemSubCategoryFilterRequest filter, Pageable pageable) {
    return menuItemSubCategoryService.getPage(filter, pageable);
  }
}
