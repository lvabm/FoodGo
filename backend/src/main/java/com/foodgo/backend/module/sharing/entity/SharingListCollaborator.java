package com.foodgo.backend.module.sharing.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "sharing_list_collaborator",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "sharing_list_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharingListCollaborator extends BaseIntegerEntity<Long> {

  // 1. QUAN HỆ MANY - TO - ONE: UserAccount <--> SharingListCollaborator
  // SharingListCollaborator sở hữu quan hệ (fk_user_id_sharing_list_collaborator)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 2. QUAN HỆ MANY - TO - ONE: SharingList <--> SharingListCollaborator
  // SharingListCollaborator sở hữu quan hệ
  // (fk_sharing_list_id_sharing_list_collaborator)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sharing_list_id", nullable = false)
  private SharingList sharingList;
}
