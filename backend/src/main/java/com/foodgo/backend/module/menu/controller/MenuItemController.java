package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;
import com.foodgo.backend.module.menu.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Menu Item (Public)", description = "API Đọc dữ liệu các món ăn gốc (Global Menu).")
@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

  private final MenuItemService menuItemService;

  // 1. GET DETAIL
  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết món ăn gốc theo ID")
  public MenuItemResponse getDetail(@PathVariable UUID id) {
    return menuItemService.getDetail(id);
  }

  // 2. GET SEARCH
  @PermitAll
  @GetMapping
  @Operation(
      summary = "Tìm kiếm và Phân trang món ăn gốc",
      description = "Tìm kiếm theo tên, phân loại, và tỉnh thành áp dụng.")
  public Page<MenuItemResponse> searchMenuItems(
      @ModelAttribute MenuItemFilterRequest filter, Pageable pageable) {
    return menuItemService.getPage(filter, pageable);
  }
}
