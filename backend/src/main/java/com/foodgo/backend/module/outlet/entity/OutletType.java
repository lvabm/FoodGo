package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "outlet_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletType extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: OutletType <--> Outlet
  // Outlet sở hữu quan hệ (fk_outlet_type_id_outlet)
  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  private Set<Outlet> outlets;

  // 2. QUAN HỆ ONE - TO - MANY: OutletType <--> OutletCategory
  // OutletCategory sở hữu quan hệ (fk_outlet_type_id_outlet_category)
  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  private Set<OutletCategory> outletCategories;
}
