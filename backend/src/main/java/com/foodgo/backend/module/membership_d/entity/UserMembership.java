package com.foodgo.backend.module.membership_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_membership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseEntity<Long> {

  @Column(name = "start_date", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", nullable = false)
  private Instant endDate;

  //1. QUAN HỆ MANY - TO - ONE: MembershipPlan <--> UserMembership
  // UserMembership sở hữu quan hệ (fk_membership_plan_id_user_membership)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "plan_id", nullable = false)
  private MembershipPlan plan;

  //2. QUAN HỆ MANY - TO - ONE: UserAccount <--> UserMembership
  // UserMembership sở hữu quan hệ (fk_user_account_id_user_membership)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount userAccount;
}
