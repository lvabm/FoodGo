package com.foodgo.backend.module.owner.dto.response;

import com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.user.dto.response.UserAccountResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record OwnerRegistrationRequestResponse(
    UUID id,
    OwnerRegistrationRequest.RequestStatus status,
    String adminNotes,
    LocalDateTime reviewedAt,
    UserAccountResponse user,
    OutletResponse outlet,
    UserAccountResponse reviewedBy,
    LocalDateTime createdAt
) {}

