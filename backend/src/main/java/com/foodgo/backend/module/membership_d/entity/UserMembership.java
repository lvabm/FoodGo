package com.foodgo.backend.module.membership_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_membership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseEntity {
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "plan_id", nullable = false)
  private Long planId;

  @Column(name = "start_date", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", nullable = false)
  private Instant endDate;
}
