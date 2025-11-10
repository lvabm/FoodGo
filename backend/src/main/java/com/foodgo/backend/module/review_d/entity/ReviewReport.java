package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReport extends BaseEntity<Integer> {

  @Column(name = "reason", length = 255)
  private String reason;

  //1. QUAN HỆ MANY - TO - ONE: ReviewReport <--> Review
  // ReviewReport sở hữu quan hệ (fk_review_id_review_report)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;

  //2. QUAN HỆ MANY - TO - ONE: ReviewReport <--> UserAccount
  // ReviewReport sở hữu quan hệ (fk_user_account_id_review_report)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "reporter_id", nullable = false)
  private UserAccount reporter;
}
