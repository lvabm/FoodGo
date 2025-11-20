package com.foodgo.backend.module.user.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole extends BaseIntegerEntity<Long> {

  // 1. QUAN HỆ MANY - TO - ONE: UserRole <--> UserAccount
  // UserRole sở hữu quan hệ (fk_user_account_id_user_role)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount userAccount;

  // 2. QUAN HỆ MANY - TO - ONE: UserRole <--> Role
  // UserRole sở hữu quan hệ (fk_role_id_user_role)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
}
