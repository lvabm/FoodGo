package com.foodgo.backend.module.user.dto.response;

import com.foodgo.backend.common.constant.RoleType;

import java.util.UUID;

public record UserAccountResponse(
    UUID id,
    String username,
    String email,
    String fullName,
    String address,
    String phoneNumber,
    boolean isActive,
    RoleType roleType) {}
