package com.foodgo.backend.module.review.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseUUIDEntity {

  @Column(name = "food_rating", nullable = false)
  private Integer foodRating;

  @Column(name = "service_rating", nullable = false)
  private Integer serviceRating;

  @Column(name = "ambiance_rating", nullable = false)
  private Integer ambianceRating;

  @Column(name = "price_rating", nullable = false)
  private Integer priceRating;

  @Column(name = "overall_rating", nullable = false)
  private Integer overallRating;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  @Column(name = "likes_count")
  @Builder.Default
  private Integer likesCount = 0;

  @Column(name = "dislikes_count")
  @Builder.Default
  private Integer dislikesCount = 0;

  // 1. QUAN HỆ ONE-TO-ONE: Review <--> Booking
  // Review sở hữu quan hệ (fk_booking_id_review)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id")
  private Booking booking;

  // 2. QUAN HỆ MANY - TO - ONE: UserAccount <--> Review
  // Review sở hữu quan hệ (fk_user_account_id_review)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 3. QUAN HỆ MANY - TO - ONE: Review <--> Outlet
  // Review sở hữu quan hệ (fk_outlet_id_review)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  // 4. QUAN HỆ ONE - TO - MANY: Review <--> ReviewReport
  // ReviewReport sở hữu quan hệ (fk_review_id_review_report)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReport> reviewReports;

  // 5. QUAN HỆ ONE - TO - MANY: Review <--> ReviewImage
  // ReviewImage sở hữu quan hệ (fk_review_id_review_image)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewImage> reviewImages;

  // 6. QUAN HỆ ONE-TO-ONE: Review <--> ReviewReply
  // ReviewReply sở hữu quan hệ (fk_review_id_review_reply)
  @OneToOne(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private ReviewReply reviewReply;

  // 7. QUAN HỆ ONE - TO - MANY: Review <--> ReviewReaction
  // ReviewReaction sở hữu quan hệ (fk_review_id_review_reaction)
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReaction> reviewReactions;
}
