package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReply extends BaseIntegerEntity<Integer> {

  @Column(name = "content", length = 1000)
  private String content;

  // 1. QUAN HỆ MANY - TO - ONE: ReviewReply <--> Review
  // ReviewReply sở hữu quan hệ (fk_review_id_review_reply)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;

  // 2. QUAN HỆ MANY - TO - ONE: ReviewReply <--> UserAccount
  // ReviewReply sở hữu quan hệ (fk_user_account_id_review_reply)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserAccount owner;
}
