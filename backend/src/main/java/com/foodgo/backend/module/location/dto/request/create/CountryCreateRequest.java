package com.foodgo.backend.module.location.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CountryCreateRequest(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Size(max = 10) String code) {}

