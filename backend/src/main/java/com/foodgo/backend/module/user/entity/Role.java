package com.foodgo.backend.module.user.entity;

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

  // 1. QUAN HỆ ONE - TO - MANY: Role <--> UserAccount
  // Role sở hữu quan hệ (fk_role_id_user_account)
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private List<UserAccount> users;

  // 2. QUAN HỆ ONE - TO - MANY: Role <--> RolePermission
  // RolePermission sở hữu quan hệ (fk_role_id_role_permission)
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private List<RolePermission> rolePermissions;
}
