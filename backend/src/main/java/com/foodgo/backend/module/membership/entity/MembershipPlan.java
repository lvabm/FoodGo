package com.foodgo.backend.module.membership.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.common.constant.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "membership_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan extends BaseIntegerEntity<Integer> {

  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "duration_months", nullable = false)
  private Integer durationMonths;

  // Dành cho Owner (Số món được đăng) - Nullable
  @Column(name = "dish_limit")
  private Integer dishLimit;

  @Column(name = "features", columnDefinition = "TEXT")
  private String features;

  // Phân loại: USER hoặc OWNER
  @Column(name = "type", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private PlanType type = PlanType.USER;

  // 1. QUAN HỆ ONE - TO - MANY: MembershipPlan <--> UserMembership
  // UserMembership sở hữu quan hệ (fk_plan_id_user_membership)
  @OneToMany(mappedBy = "membershipPlan", fetch = FetchType.LAZY)
  private Set<UserMembership> userMemberships;
}
