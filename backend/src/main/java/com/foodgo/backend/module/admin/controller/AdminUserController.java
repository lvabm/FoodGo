package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.admin.dto.user.UserAdminResponse;
import com.foodgo.backend.module.admin.dto.user.UserFilterRequest;
import com.foodgo.backend.module.admin.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

  private final AdminUserService adminUserService;

  @Operation(summary = "Danh sách người dùng (admin)")
  @GetMapping
  public Page<UserAdminResponse> getUsers(@Valid UserFilterRequest filter, Pageable pageable) {
    return adminUserService.getUsers(filter, pageable);
  }

  @Operation(summary = "Xem thông tin người dùng theo ID (admin)")
  @GetMapping("/{id}")
  public UserAdminResponse getUserById(@PathVariable UUID id) {
    return adminUserService.getUserById(id);
  }

  @Operation(summary = "Thay đổi trạng thái hoạt động (admin)")
  @PatchMapping("/{id}/status")
  public UserAdminResponse changeStatus(
      @PathVariable UUID id, @RequestBody @Valid ChangeUserStatusRequest request) {
    return adminUserService.changeUserStatus(id, request);
  }

  @Operation(summary = "Gán quyền cho người dùng (admin)")
  @PatchMapping("/{id}/roles")
  public UserAdminResponse assignRoles(
      @PathVariable UUID id, @RequestBody @Valid AssignRolesRequest request) {
    return adminUserService.assignRoles(id, request);
  }

  // Hiện tại để đơn giản dùng chung thay đổi trạng thái, không thêm trường isDelete (boolean)
  @Operation(summary = "Xóa người dùng (soft delete) (admin)")
  @DeleteMapping("/{id}")
  public UserAdminResponse softDelete(@PathVariable UUID id) {
    return adminUserService.softDeleteUser(id);
  }
}
