package com.foodgo.backend.module.sharing.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sharing_list_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharingListMember extends BaseIntegerEntity<Long> {

  // 1. QUAN HỆ MANY - TO - ONE: UserAccount <--> SharingListMember
  // SharingListMember sở hữu quan hệ (fk_user_account_id_sharing_list_member)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount userAccount;

  // 2. QUAN HỆ MANY - TO - ONE: SharingList <--> SharingListMember
  // SharingListMember sở hữu quan hệ (fk_sharing_list_id_sharing_list_member)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "sharing_list_id", nullable = false)
  private SharingList sharingList;
}
