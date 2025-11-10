package com.foodgo.backend.module.group_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group extends BaseEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  //1. QUAN HỆ ONE - TO - MANY: Group <--> UserGroup
  // UserGroup sở hữu quan hệ (fk_group_id_user_group)
  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
  private List<UserGroup> userGroups;
}
