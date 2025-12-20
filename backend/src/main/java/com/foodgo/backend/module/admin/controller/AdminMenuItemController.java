package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;
import com.foodgo.backend.module.menu.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
    name = "Admin | Menu Item",
    description = "API Quản lý CRUD các món ăn gốc (Global Menu) - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/menu-items")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMenuItemController {

  private final MenuItemService service;

  // 1. CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Tạo mới món ăn gốc")
  public MenuItemResponse createMenuItem(@Valid @RequestBody MenuItemCreateRequest request) {
    return service.create(request);
  }

  // 2. UPDATE
  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật món ăn gốc (Partial Update)")
  public MenuItemResponse updateMenuItem(
      @PathVariable UUID id, @Valid @RequestBody MenuItemUpdateRequest request) {
    return service.update(id, request);
  }

  // 3. SOFT DELETE
  @DeleteMapping("/{id}")
  @Operation(summary = "Xóa mềm (Soft Delete) món ăn gốc")
  public MenuItemResponse softDeleteMenuItem(@PathVariable UUID id) {
    return service.softDelete(id);
  }

  // 4. HARD DELETE
  @DeleteMapping("/hard-delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Xóa cứng (Hard Delete) món ăn gốc")
  public void hardDeleteMenuItem(@PathVariable UUID id) {
    service.hardDelete(id);
  }
}
