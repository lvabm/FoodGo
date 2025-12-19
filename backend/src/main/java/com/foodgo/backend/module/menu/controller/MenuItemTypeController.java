package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemTypeFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemTypeResponse;
import com.foodgo.backend.module.menu.service.MenuItemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Menu Item Type Management (Read Only)",
    description = "Quản lý dữ liệu Loại hình Món ăn (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/menu-item-types")
@RequiredArgsConstructor
public class MenuItemTypeController {

  private final MenuItemTypeService service;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Loại hình Món ăn (Không phân trang)")
  public List<MenuItemTypeResponse> getAll() {
    return service.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Loại hình Món ăn theo ID")
  public MenuItemTypeResponse getDetail(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Loại hình Món ăn")
  public Page<MenuItemTypeResponse> search(
      @ModelAttribute MenuItemTypeFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }
}
