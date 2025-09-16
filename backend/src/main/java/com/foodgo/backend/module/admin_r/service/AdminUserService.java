package com.foodgo.backend.module.admin_r.service;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.user.UserAdminDto;
import com.foodgo.backend.module.admin_r.dto.user.UserFilterDto;
import org.springframework.data.domain.Pageable;

public interface AdminUserService {

  // Admin: lấy danh sách user (filter + pageable)
  PageResponse<UserAdminDto> getUsers(UserFilterDto filter, Pageable pageable);

  // Admin: thay đổi trạng thái user
  UserAdminDto changeUserStatus(Long id, Object changeStatusRequest);

  // Admin: gán/thu hồi roles cho user
  UserAdminDto assignRoles(Long id, Object assignRolesRequest);

  // Admin: soft-delete user
  void softDeleteUser(Long id);
}
