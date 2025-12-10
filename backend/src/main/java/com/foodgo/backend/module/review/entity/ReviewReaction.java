package com.foodgo.backend.module.review.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "review_reaction",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"review_id", "user_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReaction extends BaseIntegerEntity<Long> {

  @Column(name = "reaction_type", nullable = false, length = 10)
  private String reactionType;

  // 1. QUAN HỆ MANY - TO - ONE: ReviewReaction <--> UserAccount
  // ReviewReaction sở hữu quan hệ (fk_user_account_id_review_reaction)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 2. QUAN HỆ MANY - TO - ONE: ReviewReaction <--> Review
  // ReviewReaction sở hữu quan hệ (fk_review_id_review_reaction)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;
}
