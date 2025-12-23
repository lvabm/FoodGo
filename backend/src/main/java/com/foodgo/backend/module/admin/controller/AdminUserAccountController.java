package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.user.dto.response.UserAccountResponse;
import com.foodgo.backend.module.admin.service.AdminUserAccountService;
import com.foodgo.backend.module.user.dto.request.filter.UserAccountFilterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user-accounts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserAccountController {

  private final AdminUserAccountService adminUserAccountService;

  @Operation(summary = "Danh sách người dùng (admin)")
  @GetMapping("/search")
  public Page<UserAccountResponse> getUsers(
      @ParameterObject UserAccountFilterRequest filter, @ParameterObject Pageable pageable) {
    // Sử dụng getPage của BaseService
    return adminUserAccountService.getPage(filter, pageable);
  }

  @Operation(summary = "Xem thông tin người dùng theo ID (admin)")
  @GetMapping("/{id}")
  public UserAccountResponse getUserById(@PathVariable UUID id) {
    // Sử dụng getById của BaseService
    return adminUserAccountService.getDetail(id);
  }

  @Operation(summary = "Thay đổi trạng thái hoạt động (admin)")
  @PatchMapping("/{id}/status")
  public UserAccountResponse changeStatus(
      @PathVariable UUID id, @RequestBody @Valid ChangeUserStatusRequest request) {
    return adminUserAccountService.changeUserStatus(id, request);
  }

  @Operation(summary = "Gán quyền cho người dùng (admin)")
  @PatchMapping("/{id}/roles")
  public UserAccountResponse assignRoles(
      @PathVariable UUID id, @RequestBody @Valid AssignRolesRequest request) {
    return adminUserAccountService.assignRoles(id, request);
  }

  @Operation(summary = "Xóa người dùng / Đổi trạng thái active (soft delete)")
  @DeleteMapping("/{id}")
  public void softDelete(@PathVariable UUID id) {
    adminUserAccountService.softDelete(id);
  }
}
