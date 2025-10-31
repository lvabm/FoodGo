package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.location_d.entity.Country;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseEntity {

  @Column(name = "full_name", length = 100)
  private String fullName;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "address")
  private String address;

  @Column(name = "avatar_url", length = 255)
  private String avatarUrl;

  //1. QUAN HỆ ONE - TO - ONE: Profile <--> UserAccount
  // UserAccount sở hữu quan hệ (fk_user_account_id_profile)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserAccount userAccount;

  //2. QUAN HỆ MANY - TO - ONE: Country <--> Profile
  // Profile sở hữu quan hệ (fk_country_id_profile)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;


}
