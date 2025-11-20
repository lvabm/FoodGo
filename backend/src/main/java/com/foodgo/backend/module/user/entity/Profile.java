package com.foodgo.backend.module.user.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.location.entity.Country;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseIntegerEntity<Long> {

  @Column(name = "full_name", length = 100)
  private String fullName;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "address", columnDefinition = "TEXT")
  private String address;

  @Column(name = "avatar_url", length = 255)
  @Builder.Default
  private String avatarUrl = "https://default-avatar.png";

  // 1. QUAN HỆ ONE - TO - ONE: Profile <--> UserAccount
  // UserAccount sở hữu quan hệ (fk_user_account_id_profile)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
  private UserAccount userAccount;

  // 2. QUAN HỆ MANY - TO - ONE: Country <--> Profile
  // Profile sở hữu quan hệ (fk_country_id_profile)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  private Country country;
}
