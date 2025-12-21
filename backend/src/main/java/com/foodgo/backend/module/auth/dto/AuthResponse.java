package com.foodgo.backend.module.auth.dto;

import java.util.List;

public record AuthResponse(String accessToken, String refreshToken, List<String> roles) {}
