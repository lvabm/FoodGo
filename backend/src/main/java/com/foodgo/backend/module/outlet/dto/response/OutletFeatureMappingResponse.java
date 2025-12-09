package com.foodgo.backend.module.outlet.dto.response;

import java.util.UUID;

public record OutletFeatureMappingResponse(
    Integer id, UUID outletId, Integer featureId, String featureName) {}
