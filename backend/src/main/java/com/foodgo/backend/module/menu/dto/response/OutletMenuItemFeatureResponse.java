package com.foodgo.backend.module.menu.dto.response;

public record OutletMenuItemFeatureResponse(
    Integer id, Integer outletMenuItemId, Integer featureId, String featureName, String value) {}
