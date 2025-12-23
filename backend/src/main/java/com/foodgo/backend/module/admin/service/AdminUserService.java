package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.admin.dto.user.UserAdminResponse;
import com.foodgo.backend.module.admin.dto.user.UserFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminUserService {

  // Admin: lấy danh sách user (filter + pageable)
  Page<UserAdminResponse> getUsers(UserFilterRequest filter, Pageable pageable);

  // Xem chi tiết 1 user theo ID
  UserAdminResponse getUserById(UUID id);

  // Admin: thay đổi trạng thái user
  UserAdminResponse changeUserStatus(UUID id, ChangeUserStatusRequest changeStatusRequest);

  // Admin: gán/thu hồi roles cho user
  UserAdminResponse assignRoles(UUID id, AssignRolesRequest assignRolesRequest);

  // Admin: soft-delete user
  UserAdminResponse softDeleteUser(UUID id);
}
