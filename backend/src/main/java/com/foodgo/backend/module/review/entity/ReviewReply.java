package com.foodgo.backend.module.review.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
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

  @Column(name = "reply_text", nullable = false, columnDefinition = "TEXT")
  private String replyText;

  // 1. QUAN HỆ ONE-TO-ONE: ReviewReply <--> Review
  // ReviewReply sở hữu quan hệ (fk_review_id_review_reply)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id", nullable = false, unique = true)
  private Review review;

  // 2. QUAN HỆ MANY - TO - ONE: ReviewReply <--> UserAccount
  // ReviewReply sở hữu quan hệ (fk_user_account_id_review_reply)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserAccount owner;
}
