package com.foodgo.backend.module.owner.entity;

import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "owner_registration_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerRegistrationRequest extends BaseUUIDEntity {

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private RequestStatus status = RequestStatus.PENDING;

  @Column(name = "admin_notes", columnDefinition = "TEXT")
  private String adminNotes;

  @Column(name = "reviewed_at")
  private LocalDateTime reviewedAt;

  // 1. QUAN HỆ MANY - TO - ONE: OwnerRegistrationRequest <--> UserAccount
  // OwnerRegistrationRequest sở hữu quan hệ (fk_user_account_id_owner_registration_request)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 2. QUAN HỆ MANY - TO - ONE: OwnerRegistrationRequest <--> Outlet
  // OwnerRegistrationRequest sở hữu quan hệ (fk_outlet_id_owner_registration_request)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  // 3. QUAN HỆ MANY - TO - ONE: OwnerRegistrationRequest <--> UserAccount (Admin)
  // OwnerRegistrationRequest sở hữu quan hệ (fk_reviewed_by_id_owner_registration_request)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reviewed_by_id")
  private UserAccount reviewedBy;

  public enum RequestStatus {
    PENDING,
    APPROVED,
    REJECTED
  }
}

