package com.foodgo.backend.module.location.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "district")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  // 1. QUAN HỆ MANY - TO - ONE: Province <--> District
  // District sở hữu quan hệ (fk_province_id_district)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id", nullable = false)
  private Province province;

  // 2. QUAN HỆ ONE - TO - MANY: District <--> Outlet
  // Outlet sở hữu quan hệ (fk_district_id_outlet)
  @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
  private Set<Outlet> outlets;
}
