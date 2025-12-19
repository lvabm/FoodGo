package com.foodgo.backend.module.user.dto.request.filter;

public record UserAccountFilterRequest(String searchTerm, Boolean isActive, String roleName) {}
