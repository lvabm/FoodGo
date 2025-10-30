package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
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

  @Column(name = "country_id")
  private Integer countryId;

  //1. QUAN HỆ ONE - TO - ONE: Profile <--> UserAccount
  // UserAccount sở hữu quan hệ (fk_user_id_profile)
  @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @Column(name = "user_id", nullable = false, unique = true)
  private UserAccount userAccount;
}
