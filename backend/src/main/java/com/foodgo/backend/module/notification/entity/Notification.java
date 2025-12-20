package com.foodgo.backend.module.notification.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseIntegerEntity<Long> {

  @Column(name = "type", nullable = false, length = 50)
  private String type;

  @Column(name = "title", nullable = false, length = 255)
  private String title;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "related_id")
  private UUID relatedId;

  @Column(name = "is_read")
  @Builder.Default
  private Boolean isRead = false;

  // 1. QUAN HỆ MANY - TO - ONE: Notification <--> UserAccount
  // Notification sở hữu quan hệ (fk_user_account_id_notification)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;
}
