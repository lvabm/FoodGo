package com.foodgo.backend.module.membership.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserMembershipResponse(
    Long id,
    String planName,
    BigDecimal pricePaid,
    LocalDate startDate,
    LocalDate endDate,
    Boolean isActive) {}
