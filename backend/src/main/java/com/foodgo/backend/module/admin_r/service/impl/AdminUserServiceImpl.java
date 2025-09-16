package com.foodgo.backend.module.admin_r.service.impl;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.user.UserAdminDto;
import com.foodgo.backend.module.admin_r.dto.user.UserFilterDto;
import com.foodgo.backend.module.admin_r.service.AdminUserService;
import com.foodgo.backend.module.user_d.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
  private final UserAccountRepository userAccountRepository;

  @Override
  public PageResponse<UserAdminDto> getUsers(UserFilterDto filter, Pageable pageable) {
    // TODO: query repository with role/status/q filters, map to UserAdminDto and build PageResponse
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public UserAdminDto changeUserStatus(Long id, Object changeStatusRequest) {
    // TODO: update status (active/deactivate/ban), return updated admin dto
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public UserAdminDto assignRoles(Long id, Object assignRolesRequest) {
    // TODO: assign/revoke roles, create audit record
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public void softDeleteUser(Long id) {
    // TODO: set soft-delete flag, preserve data
    throw new UnsupportedOperationException("Not implemented");
  }
}
