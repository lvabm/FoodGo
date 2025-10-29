package com.foodgo.backend.module.group_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "user_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup extends BaseEntity {
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "group_id", nullable = false)
  private Long groupId;
}
