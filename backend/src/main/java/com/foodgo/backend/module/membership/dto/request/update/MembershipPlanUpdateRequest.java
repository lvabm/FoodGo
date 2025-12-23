package com.foodgo.backend.module.membership.dto.request.update;

import com.foodgo.backend.common.constant.PlanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record MembershipPlanUpdateRequest(
    String name,
    String description,
    @DecimalMin(value = "0.0") BigDecimal price,
    @Min(value = 1) Integer durationMonths,
    Integer dishLimit,
    String features,
    PlanType type) {}
