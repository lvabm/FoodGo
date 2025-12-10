package com.foodgo.backend.module.outlet.dto.request;

import jakarta.validation.constraints.NotNull;

public record OutletFeatureMappingCreateRequest(@NotNull Integer featureId) {}
