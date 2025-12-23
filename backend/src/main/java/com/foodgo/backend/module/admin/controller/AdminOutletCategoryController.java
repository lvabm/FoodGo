package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminOutletCategoryService;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCategoryCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletCategoryUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Admin | Outlet Category",
    description = "API Quản lý CRUD danh mục Outlet - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/outlet-categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOutletCategoryController {

  private final AdminOutletCategoryService adminOutletCategoryService;

  // 1. CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Tạo mới danh mục Outlet")
  public OutletCategoryResponse createCategory(
      @Valid @RequestBody OutletCategoryCreateRequest request) {
    return adminOutletCategoryService.create(request);
  }

  // 2. UPDATE
  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật danh mục Outlet (Partial Update)")
  public OutletCategoryResponse updateCategory(
      @PathVariable Integer id, @Valid @RequestBody OutletCategoryUpdateRequest request) {
    return adminOutletCategoryService.update(id, request);
  }

  // 3. SOFT DELETE
  @DeleteMapping("/{id}")
  @Operation(summary = "Xóa mềm (Soft Delete) danh mục Outlet")
  public OutletCategoryResponse softDeleteCategory(@PathVariable Integer id) {
    return adminOutletCategoryService.softDelete(id);
  }

  // 4. HARD DELETE
  @DeleteMapping("/hard-delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Xóa cứng (Hard Delete) danh mục Outlet")
  public void hardDeleteCategory(@PathVariable Integer id) {
    adminOutletCategoryService.hardDelete(id);
  }
}

