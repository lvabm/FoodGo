package com.foodgo.backend.module.location_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.membership_d.entity.UserMembership;
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
public class Country extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "code", nullable = false, unique = true, length = 10)
  private String code;

  //1. QUAN HỆ ONE - TO - MANY: Country <--> Profile
  // Profile sở hữu quan hệ (fk_country_id_profile)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserMembership> userMemberships;

  //1. QUAN HỆ ONE - TO - MANY: Country <--> Province
  // Profile sở hữu quan hệ (fk_country_id_province)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Province> provinces;
}
