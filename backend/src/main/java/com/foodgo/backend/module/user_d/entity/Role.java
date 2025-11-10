package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseIntegerEntity<Integer> {

  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - ONE: Role <--> UserRole
  // UserAccount sở hữu quan hệ (fk_role_id_user_role)
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserRole> userRoles;

  // 2. QUAN HỆ ONE - TO - MANY: Role <--> RolePermission
  // UserAccount sở hữu quan hệ (fk_role_id_role_permission)
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<RolePermission> rolePermissions;
}
