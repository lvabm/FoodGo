package com.foodgo.backend.module.membership.dto;

import java.math.BigDecimal;

public record MembershipResponse(
    String name,
    String description,
    BigDecimal price,
    Integer durationMonths,
    Integer dishLimit,
    String features) {}
