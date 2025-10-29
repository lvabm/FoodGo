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
  @Column(name = "review_id", nullable = false)
  private Long reviewId;

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;
}
