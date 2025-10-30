package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole extends BaseEntity {

  //1. QUAN HỆ MANY - TO - ONE: UserRole <--> UserAccount
  // UserRole sở hữu quan hệ (fk_user_id_user_role)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  //2. QUAN HỆ MANY - TO - ONE: UserRole <--> Role
  // UserRole sở hữu quan hệ (fk_role_id_user_role)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
}
