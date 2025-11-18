package com.foodgo.backend.module.membership.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "membership_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "feature_limit", nullable = false)
  private int featureLimit;

  // 1. QUAN HỆ ONE - TO - MANY: MembershipPlan <--> UserMembership
  // UserMembership sở hữu quan hệ (fk_membership_plan_id_user_membership)
  @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserMembership> userMemberships;
}
