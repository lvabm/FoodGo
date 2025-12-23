package com.foodgo.backend.module.admin.dto.user;

import com.foodgo.backend.common.constant.RoleType;

import java.util.UUID;

public record UserAdminResponse(
    UUID id,
    String username,
    String email,
    String fullName,
    String address,
    String phoneNumber,
    boolean isActive,
    RoleType roleType) {}
