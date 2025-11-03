package com.foodgo.backend.module.admin_r.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin_r.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.admin_r.dto.user.UserAdminDto;
import com.foodgo.backend.module.admin_r.dto.user.UserFilterDto;
import com.foodgo.backend.module.admin_r.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
  private final AdminUserService adminUserService;

  @Operation(summary = "Danh sách người dùng (admin)")
  @GetMapping
  public ResponseEntity<PageResponse<UserAdminDto>> getUsers(
      @Valid UserFilterDto filter, Pageable pageable) {
    PageResponse<UserAdminDto> page = adminUserService.getUsers(filter, pageable);
    return ResponseEntity.ok(page);
  }

  @Operation(summary = "Thay đổi trạng thái hoạt động")
  @PatchMapping("/{id}/status")
  public ResponseEntity<BaseResponse<UserAdminDto>> changeStatus(
      @PathVariable Long id, @RequestBody ChangeUserStatusRequest request) {
    UserAdminDto dto = adminUserService.changeUserStatus(id, request);
    BaseResponse<UserAdminDto> body =
        BaseResponse.<UserAdminDto>builder()
            .success(true)
            .message("Thay đổi trạng thái thành công")
            .data(dto)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Gán quyền cho người dùng")
  @PostMapping("/{id}/roles")
  public ResponseEntity<BaseResponse<UserAdminDto>> assignRoles(
      @PathVariable Long id, @RequestBody AssignRolesRequest request) {
    UserAdminDto dto = adminUserService.assignRoles(id, request);
    BaseResponse<UserAdminDto> body =
        BaseResponse.<UserAdminDto>builder()
            .success(true)
            .message("Cập nhật quyền thành công")
            .data(dto)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Xóa người dùng (soft delete)")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<Void>> softDelete(@PathVariable Long id) {
    adminUserService.softDeleteUser(id);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Xóa người dùng thành công (soft)")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }
}
