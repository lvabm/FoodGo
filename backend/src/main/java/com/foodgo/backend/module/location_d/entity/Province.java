package com.foodgo.backend.module.location_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.fnb_d.entity.Fnb;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  // 1. QUAN HỆ ONE - TO - MANY: Province <--> District
  // District sở hữu quan hệ (fk_province_id_district)
  @OneToMany(mappedBy = "province", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<District> districts;

  // 2. QUAN HỆ ONE - TO - MANY: Province <--> Fnb
  // Fnb sở hữu quan hệ (fk_province_id_fnb)
  @OneToMany(mappedBy = "province", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Fnb> fnbs;

  // 3. QUAN HỆ MANY - TO - ONE: Country <--> Province
  // Province sở hữu quan hệ (fk_country_id_province)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;
}
