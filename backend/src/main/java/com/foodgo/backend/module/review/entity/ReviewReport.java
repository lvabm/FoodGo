package com.foodgo.backend.module.review.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.common.constant.ReportReason;
import com.foodgo.backend.common.constant.ReportStatus;
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

  @Column(name = "reason", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ReportReason reason;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private ReportStatus status = ReportStatus.PENDING;

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
