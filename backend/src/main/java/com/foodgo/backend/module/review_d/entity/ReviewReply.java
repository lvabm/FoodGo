package com.foodgo.backend.module.review_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "review_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReply extends BaseEntity {
  @Column(name = "review_id", nullable = false)
  private Long reviewId;

  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;

  @Column(name = "content", length = 1000)
  private String content;
}
