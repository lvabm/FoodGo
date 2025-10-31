package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "review_reaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReaction extends BaseEntity {

  @Column(name = "reaction_type", length = 50)
  private String reactionType;

  //1. QUAN HỆ MANY - TO - ONE: ReviewReaction <--> Review
  // ReviewReaction sở hữu quan hệ (fk_review_id_review_reaction)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;

  //2. QUAN HỆ MANY - TO - ONE: ReviewReaction <--> UserAccount
  // ReviewReaction sở hữu quan hệ (fk_user_account_id_review_reaction)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;
}
