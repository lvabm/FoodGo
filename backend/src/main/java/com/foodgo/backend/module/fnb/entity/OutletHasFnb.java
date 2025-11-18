package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "outlet_has_fnb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletHasFnb extends BaseIntegerEntity<Integer> {

  @Column(name = "price")
  private BigDecimal price;

  // 1. QUAN HỆ ONE - TO - MANY: OutletHasFnb <--> OutletFnbHasFeature
  // OutletFnbHasFeature sở hữu quan hệ (fk_outlet_has_fnb_id_outlet_fnb_has_feature)
  @OneToMany(mappedBy = "outletFnb", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OutletFnbHasFeature> outletFnbHasFeatures;

  // 2. QUAN HỆ MANY - TO - ONE: Fnb  <--> OutletHasFnb
  // OutletHasFnb sở hữu quan hệ (fk_fnb_id_outlet_has_fnb)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fnb_id", nullable = false)
  private Fnb fnb;

  // 3. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletHasFnb
  // OutletHasFnb sở hữu quan hệ (fk_outlet_id_outlet_has_fnb)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
