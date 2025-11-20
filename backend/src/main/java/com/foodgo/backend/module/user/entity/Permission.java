package com.foodgo.backend.module.user.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: Permission <--> RolePermission
  // RolePermission sở hữu quan hệ (fk_permission_id_role_permission)
  @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
  private List<RolePermission> rolePermissions;
}
