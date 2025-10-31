package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.booking_d.entity.Booking;
import com.foodgo.backend.module.outlet_d.entity.Outlet;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

  @Column(name = "rating", nullable = false)
  private int rating;

  @Column(name = "content", length = 1000)
  private String content;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  //1. QUAN HỆ MANY - TO - ONE: Review <--> Booking
  // Review sở hữu quan hệ (fk_booking_id_review)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  //2. QUAN HỆ MANY - TO - ONE: UserAccount <--> Review
  // Review sở hữu quan hệ (fk_user_account_id_review)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  //3. QUAN HỆ MANY - TO - ONE: Review <--> Outlet
  // Review sở hữu quan hệ (fk_outlet_id_review)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  //4. QUAN HỆ ONE - TO - MANY: Review <--> ReviewReport
  // ReviewReport sở hữu quan hệ (fk_review_id_review_report)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReport> reviewReports;

  //5. QUAN HỆ ONE - TO - MANY: Review <--> ReviewImage
  // ReviewImage sở hữu quan hệ (fk_review_id_review_image)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewImage> reviewImages;

  //6. QUAN HỆ ONE - TO - MANY: Review <--> ReviewReply
  // ReviewReply sở hữu quan hệ (fk_review_id_review_image)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReply> reviewReplies;

  //7. QUAN HỆ ONE - TO - MANY: Review <--> ReviewReaction
  // ReviewReaction sở hữu quan hệ (fk_review_id_review_reaction)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReaction> reviewReactions;

}
