package com.foodgo.backend.module.membership.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_membership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseIntegerEntity<Long> {
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "is_active")
  @Builder.Default
  private Boolean isActive = true;

  // 1. QUAN HỆ MANY - TO - ONE: UserMembership <--> UserAccount
  // UserMembership sở hữu quan hệ (fk_user_id_user_membership)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount userAccount;

  // 2. QUAN HỆ MANY - TO - ONE: UserMembership <--> MembershipPlan
  // UserMembership sở hữu quan hệ (fk_plan_id_user_membership)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id", nullable = false)
  private MembershipPlan membershipPlan;
}
