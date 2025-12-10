package com.foodgo.backend.module.location.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.user.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "code", nullable = false, unique = true, length = 10)
  private String code;

  // 1. QUAN HỆ ONE - TO - MANY: Country <--> Province
  // Province sở hữu quan hệ (fk_country_id_province)
  @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
  private Set<Province> provinces;

  // 2. QUAN HỆ ONE - TO - MANY: Country <--> Profile
  // Profile sở hữu quan hệ (fk_country_id_profile)
  @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
  private Set<Profile> profiles;
}
