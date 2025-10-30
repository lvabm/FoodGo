package com.foodgo.backend.module.notification_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

  @Column(name = "content", nullable = false, length = 500)
  private String content;

  @Column(name = "is_read", nullable = false)
  private boolean isRead = false;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  //1. QUAN HỆ MANY - TO - ONE: UserAccount <--> Notification
  // UserAccount sở hữu quan hệ (fk_user_account_id_notification)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;
}
