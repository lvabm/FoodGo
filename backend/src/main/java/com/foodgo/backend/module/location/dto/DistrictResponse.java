package com.foodgo.backend.module.location.dto;

public record DistrictResponse(
    Integer id, String name, Integer provinceId, String provinceName // Lấy từ Province Entity
    ) {}
