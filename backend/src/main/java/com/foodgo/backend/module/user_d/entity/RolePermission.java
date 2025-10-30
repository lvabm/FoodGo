package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission extends BaseEntity {
  //1. QUAN HỆ MANY - TO - ONE: RolePermission <--> Role
  // RolePermission sở hữu quan hệ (fk_role_id_role_permission)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  //2. QUAN HỆ MANY - TO - ONE: RolePermission <--> Permission
  // RolePermission sở hữu quan hệ (fk_permission_id_role_permission)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;
}
