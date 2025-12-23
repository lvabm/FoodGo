package com.foodgo.backend.module.auth.dto;

import java.util.List;

public record AuthResponse(String accessToken, List<String> roles) {}
