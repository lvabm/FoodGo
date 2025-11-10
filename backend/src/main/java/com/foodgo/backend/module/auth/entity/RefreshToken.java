package com.foodgo.backend.module.auth.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseIntegerEntity<Long> {

  @Column(name = "token", nullable = false, unique = true, length = 500)
  private String token;

  @Column(name = "device_info", length = 255)
  private String deviceInfo;

  @Column(name = "ip_address", length = 50)
  private String ipAddress;

  @Column(name = "is_revoked", nullable = false)
  private boolean isRevoked = false;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  // 1. QUAN HỆ MANY - TO - ONE: UserAccount <--> RefreshToken
  // RefreshToken sở hữu quan hệ (fk_user_account_id_refresh_token)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;
}
