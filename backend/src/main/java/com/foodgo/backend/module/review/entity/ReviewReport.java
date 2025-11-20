package com.foodgo.backend.module.review.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReport extends BaseIntegerEntity<Integer> {

  @Column(name = "reason", nullable = false, length = 255)
  private String reason;

  @Column(name = "status", length = 20)
  @Builder.Default
  private String status = "pending";

  // 1. QUAN HỆ MANY - TO - ONE: ReviewReport <--> UserAccount
  // ReviewReport sở hữu quan hệ (fk_user_account_id_review_report)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id", nullable = false)
  private UserAccount reporter;

  // 2. QUAN HỆ MANY - TO - ONE: ReviewReport <--> Review
  // ReviewReport sở hữu quan hệ (fk_review_id_review_report)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;
}
