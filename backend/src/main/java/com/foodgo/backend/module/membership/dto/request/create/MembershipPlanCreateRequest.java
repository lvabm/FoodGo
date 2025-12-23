package com.foodgo.backend.module.membership.dto.request.create;

import com.foodgo.backend.common.constant.PlanType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record MembershipPlanCreateRequest(
    @NotBlank(message = "Tên gói không được để trống") String name,
    String description,
    @NotNull(message = "Giá không được để trống")
        @DecimalMin(value = "0.0", message = "Giá không được âm")
        BigDecimal price,
    @NotNull(message = "Thời hạn không được để trống")
        @Min(value = 1, message = "Thời hạn tối thiểu 1 tháng")
        Integer durationMonths,
    @Min(value = 0, message = "Giới hạn món ăn không hợp lệ") Integer dishLimit,
    String features,
    @NotNull(message = "Loại gói không được để trống") PlanType type) {}
