package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken extends BaseEntity {

  @Column(name = "token", nullable = false, unique = true, length = 500)
  private String token;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "is_used", nullable = false)
  private boolean isUsed = false;

  //1. QUAN HỆ MANY - TO - ONE: PasswordResetToken <--> UserAccount
  // PasswordResetToken sở hữu quan hệ (fk_user_id_password_reset_token)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;
}
