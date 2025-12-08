package com.foodgo.backend.module.location.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.menu.entity.MenuItem;
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
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  // 1. QUAN HỆ MANY - TO - ONE: Country <--> Province
  // Province sở hữu quan hệ (fk_country_id_province)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;

  // 2. QUAN HỆ ONE - TO - MANY: Province <--> District
  // District sở hữu quan hệ (fk_province_id_district)
  @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
  private List<District> districts;

  // 3. QUAN HỆ ONE - TO - MANY: Province <--> MenuItem
  // MenuItem sở hữu quan hệ (fk_province_id_menu_item)
  @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
  private List<MenuItem> menuItems;
}
