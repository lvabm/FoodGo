package com.foodgo.backend.module.outlet.dto.response;

public record OutletImageResponse(
    Integer id, String imageUrl, Integer displayOrder, Boolean isPrimary) {}
