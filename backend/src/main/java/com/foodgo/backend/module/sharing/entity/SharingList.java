package com.foodgo.backend.module.sharing.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sharing_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharingList extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: Group <--> UserGroup
  // UserGroup sở hữu quan hệ (fk_group_id_user_group)
  @OneToMany(mappedBy = "sharingList", fetch = FetchType.LAZY)
  private List<SharingListMember> sharingListMembers;
}
