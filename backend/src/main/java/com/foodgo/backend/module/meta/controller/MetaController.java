package com.foodgo.backend.module.meta.controller;

import com.foodgo.backend.common.constant.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/meta")
public class MetaController {

  @Operation(summary = "Lấy danh sách các Enum định nghĩa trong hệ thống (Dùng cho Dropdown)")
  @GetMapping("/enums")
  public Map<String, List<?>> getAllEnums() {
    return Map.of(
        "bookingStatuses",
        Arrays.asList(BookingStatus.values()),
        "roles",
        Arrays.asList(RoleType.values()),
        "paymentMethods",
        Arrays.asList(PaymentMethod.values()),
        "paymentStatuses",
        Arrays.asList(PaymentStatus.values()),
        "reportReasons",
        Arrays.asList(ReportReason.values()),
        "planTypes",
        Arrays.asList(PlanType.values()),
        "reactionTypes",
        Arrays.asList(ReactionType.values()));
  }
}
