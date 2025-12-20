package com.foodgo.backend.module.sharing.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
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
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_public", nullable = false)
  @Builder.Default
  private Boolean isPublic = false;

  @Column(name = "is_collaborative", nullable = false)
  @Builder.Default
  private Boolean isCollaborative = false;

  // 1. QUAN HỆ MANY - TO - ONE: SharingList <--> UserAccount (Owner)
  // SharingList sở hữu quan hệ (fk_owner_id_sharing_list)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserAccount owner;

  // 2. QUAN HỆ ONE - TO - MANY: SharingList <--> SharingListCollaborator
  // SharingListCollaborator sở hữu quan hệ
  // (fk_sharing_list_id_sharing_list_collaborator)
  @OneToMany(mappedBy = "sharingList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<SharingListCollaborator> collaborators;
}
