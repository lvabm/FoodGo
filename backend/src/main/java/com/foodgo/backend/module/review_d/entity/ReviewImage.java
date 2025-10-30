package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImage extends BaseEntity {

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;

  //1. QUAN HỆ MANY - TO - ONE: ReviewImage <--> Review
  // ReviewImage sở hữu quan hệ (fk_review_id_review_image)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "review_id", nullable = false)
  private Review review;
}
