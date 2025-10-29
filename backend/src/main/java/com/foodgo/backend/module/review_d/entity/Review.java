package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "outlet_id", nullable = false)
  private UUID outletId;

  @Column(name = "booking_id")
  private Long bookingId;

  @Column(name = "rating", nullable = false)
  private int rating;

  @Column(name = "content", length = 1000)
  private String content;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();
}
