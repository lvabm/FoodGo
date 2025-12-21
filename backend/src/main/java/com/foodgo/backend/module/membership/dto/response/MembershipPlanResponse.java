package com.foodgo.backend.module.membership.dto.response;

import com.foodgo.backend.common.constant.PlanType;
import java.math.BigDecimal;

public record MembershipPlanResponse(
    Integer id,
    String name,
    String description,
    BigDecimal price,
    Integer durationMonths,
    Integer dishLimit,
    String features,
    PlanType type) {}
