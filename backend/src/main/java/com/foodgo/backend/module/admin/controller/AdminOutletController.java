package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminOutletService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Admin | Outlet Management", description = "API Quản lý Outlet cho Admin")
@RestController
@RequestMapping("/api/v1/admin/outlets")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOutletController {

  private final AdminOutletService adminOutletService;

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang outlets (Admin)")
  public Page<OutletResponse> searchOutlets(
      @ParameterObject OutletFilterRequest filter,
      @ParameterObject Pageable pageable) {
    return adminOutletService.getPage(filter, pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết outlet")
  public OutletResponse getDetail(@PathVariable UUID id) {
    return adminOutletService.getDetail(id);
  }

  @PostMapping("/{id}/approve")
  @Operation(summary = "Duyệt outlet (approve)")
  public OutletResponse approveOutlet(@PathVariable UUID id) {
    return adminOutletService.approveOutlet(id);
  }

  @PostMapping("/{id}/lock")
  @Operation(summary = "Khóa outlet (lock/disable)")
  public OutletResponse lockOutlet(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminOutletService.lockOutlet(id, reason);
  }

  @PostMapping("/{id}/unlock")
  @Operation(summary = "Mở khóa outlet (unlock/enable)")
  public OutletResponse unlockOutlet(@PathVariable UUID id) {
    return adminOutletService.unlockOutlet(id);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Xóa outlet (soft delete)")
  public OutletResponse deleteOutlet(
      @PathVariable UUID id,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminOutletService.deleteOutlet(id, reason);
  }

  @PostMapping("/{id}/restore")
  @Operation(summary = "Khôi phục outlet đã xóa")
  public OutletResponse restoreOutlet(@PathVariable UUID id) {
    return adminOutletService.restoreOutlet(id);
  }

  @PostMapping("/bulk/approve")
  @Operation(summary = "Duyệt nhiều outlets (bulk approve)")
  public List<OutletResponse> bulkApprove(@RequestBody @Valid BulkOperationRequest request) {
    return adminOutletService.bulkApprove(request.ids());
  }

  @PostMapping("/bulk/lock")
  @Operation(summary = "Khóa nhiều outlets (bulk lock)")
  public List<OutletResponse> bulkLock(
      @RequestBody @Valid BulkOperationRequest request,
      @RequestParam(required = false, defaultValue = "Vi phạm quy định") String reason) {
    return adminOutletService.bulkLock(request.ids(), reason);
  }

  @PostMapping("/bulk/unlock")
  @Operation(summary = "Mở khóa nhiều outlets (bulk unlock)")
  public List<OutletResponse> bulkUnlock(@RequestBody @Valid BulkOperationRequest request) {
    return adminOutletService.bulkUnlock(request.ids());
  }

  // DTO for bulk operations
  public record BulkOperationRequest(
      @NotEmpty(message = "Danh sách ID không được để trống")
      List<UUID> ids
  ) {}
}

