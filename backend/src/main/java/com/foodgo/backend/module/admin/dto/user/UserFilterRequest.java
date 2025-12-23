package com.foodgo.backend.module.admin.dto.user;

public record UserFilterRequest(String searchTerm, Boolean isActive, String roleName) {}
