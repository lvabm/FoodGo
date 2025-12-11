package com.foodgo.backend.module.user.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileResponse(
    Long id,
    String fullName,
    LocalDate dateOfBirth,
    String address,
    String avatarUrl,
    UUID userId,
    String countryName) {}
