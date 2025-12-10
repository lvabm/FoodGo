package com.foodgo.backend.module.user.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "role_permission",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission extends BaseIntegerEntity<Long> {
  // 1. QUAN HỆ MANY - TO - ONE: RolePermission <--> Role
  // RolePermission sở hữu quan hệ (fk_role_id_role_permission)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  // 2. QUAN HỆ MANY - TO - ONE: RolePermission <--> Permission
  // RolePermission sở hữu quan hệ (fk_permission_id_role_permission)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;
}
