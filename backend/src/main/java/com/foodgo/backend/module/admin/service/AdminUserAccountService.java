package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.user.dto.response.UserAccountResponse;

import java.util.UUID;

import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.ReadableService;

import com.foodgo.backend.module.user.dto.request.filter.UserAccountFilterRequest;

public interface AdminUserAccountService
    extends ReadableService<UUID, UserAccountFilterRequest, UserAccountResponse>,
        DeletableService<UUID, UserAccountResponse> {

  // Custom Actions
  UserAccountResponse changeUserStatus(UUID id, ChangeUserStatusRequest request);

  UserAccountResponse assignRoles(UUID id, AssignRolesRequest request);
}
