package com.foodgo.backend.module.menu.dto.response;

public record MenuItemFeatureResponse(
    Integer id,
    String name,
    String featureType,
    String valueType,
    String possibleValues,
    String description) {}
