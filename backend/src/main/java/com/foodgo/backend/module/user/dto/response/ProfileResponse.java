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
    String countryName,
    String roleName, // Role name from UserAccount
    // Membership fields (optional, may be null if user has no active membership)
    String membershipName,
    LocalDate membershipStartDate,
    LocalDate membershipEndDate,
    Boolean membershipIsActive) {}
