package com.foodgo.backend.module.group_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup extends BaseEntity<Long> {

  //1. QUAN HỆ MANY - TO - ONE: UserAccount <--> UserGroup
  // UserGroup sở hữu quan hệ (fk_user_account_id_user_group)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount userAccount;

  //2. QUAN HỆ MANY - TO - ONE: Group <--> UserGroup
  // UserGroup sở hữu quan hệ (fk_group_id_user_group)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;
}
