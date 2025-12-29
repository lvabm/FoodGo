package com.foodgo.backend.module.owner.dto.request.update;

import com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest;
import jakarta.validation.constraints.NotNull;

public record OwnerRegistrationRequestUpdateRequest(
    @NotNull(message = "Trạng thái không được để trống")
    OwnerRegistrationRequest.RequestStatus status,
    String adminNotes
) {}

