package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.admin.dto.user.UserAdminResponse;
import com.foodgo.backend.module.admin.dto.user.UserFilterRequest;
import com.foodgo.backend.module.admin.service.AdminUserService;
import com.foodgo.backend.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

  private final AdminUserService adminUserService;

  @Operation(summary = "Danh sách người dùng (admin)")
  @GetMapping
  public ResponseEntity<BaseResponse<PageResponse<UserAdminResponse>>> getUsers(
      @Valid UserFilterRequest filter, Pageable pageable) {
    PageResponse<UserAdminResponse> data = adminUserService.getUsers(filter, pageable);
    return ResponseEntity.ok(
        ApiResponseBuilder.success("Lấy danh sách người dùng thành công", data));
  }

  @Operation(summary = "Xem thông tin người dùng theo ID (admin)")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<UserAdminResponse>> getUserById(@PathVariable UUID id) {
    UserAdminResponse data = adminUserService.getUserById(id);
    return ResponseEntity.ok(
        ApiResponseBuilder.success("Lấy thông tin người dùng thành công", data));
  }

  @Operation(summary = "Thay đổi trạng thái hoạt động (admin)")
  @PatchMapping("/{id}/status")
  public ResponseEntity<BaseResponse<UserAdminResponse>> changeStatus(
      @PathVariable UUID id, @RequestBody @Valid ChangeUserStatusRequest request) {
    UserAdminResponse data = adminUserService.changeUserStatus(id, request);
    return ResponseEntity.ok(ApiResponseBuilder.success("Thay đổi trạng thái thành công", data));
  }

  @Operation(summary = "Gán quyền cho người dùng (admin)")
  @PatchMapping("/{id}/roles")
  public ResponseEntity<BaseResponse<UserAdminResponse>> assignRoles(
      @PathVariable UUID id, @RequestBody @Valid AssignRolesRequest request) {
    UserAdminResponse data = adminUserService.assignRoles(id, request);
    return ResponseEntity.ok(ApiResponseBuilder.success("Cập nhật quyền thành công", data));
  }

  // Hiện tại để đơn giản dùng chung thay đổi trạng thái, không thêm trường isDelete (boolean)
  @Operation(summary = "Xóa người dùng (soft delete) (admin)")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<UserAdminResponse>> softDelete(@PathVariable UUID id) {
    UserAdminResponse data = adminUserService.softDeleteUser(id);
    return ResponseEntity.ok(ApiResponseBuilder.success("Xóa người dùng thành công (soft)", data));
  }
}
