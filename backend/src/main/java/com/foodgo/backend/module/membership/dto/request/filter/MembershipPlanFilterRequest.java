package com.foodgo.backend.module.membership.dto.request.filter;

import com.foodgo.backend.common.constant.PlanType;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

public record MembershipPlanFilterRequest(
    String keyword, BigDecimal minPrice, BigDecimal maxPrice, PlanType type) {
  // Helper Methods: Trả về Optional để Specification dùng trực tiếp
  public Optional<String> getKeywordOpt() {
    return Optional.ofNullable(keyword).filter(StringUtils::hasText).map(String::trim);
  }

  public Optional<BigDecimal> getMinPriceOpt() {
    return Optional.ofNullable(minPrice);
  }

  public Optional<BigDecimal> getMaxPriceOpt() {
    return Optional.ofNullable(maxPrice);
  }

  public Optional<PlanType> getTypeOpt() {
    return Optional.ofNullable(type);
  }
}
