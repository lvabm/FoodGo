package com.foodgo.backend.module.location_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.user_d.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

  // 1. QUAN HỆ ONE - TO - MANY: Country <--> Profile
  // Profile sở hữu quan hệ (fk_country_id_profile)
  @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Profile> Profile;

  // 1. QUAN HỆ ONE - TO - MANY: Country <--> Province
  // Profile sở hữu quan hệ (fk_country_id_province)
  @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Province> provinces;
}
