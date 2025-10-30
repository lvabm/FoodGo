package com.foodgo.backend.module.outlet_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "outlet_has_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletHasFeature extends BaseEntity {

  //1. QUAN HỆ MANY - TO - ONE: OutletHasFeature <--> Outlet
  // OutletHasFeature sở hữu quan hệ (fk_outlet_id_outlet_has_feature)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

//  //2. QUAN HỆ MANY - TO - ONE: OutletHasFeature <--> Feature
//  // OutletHasFeature sở hữu quan hệ (fk_feature_of_outlet_id_outlet_has_feature)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  @JoinColumn(name = "feature_id", nullable = false)
//  private Feature feature;
}
