package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "feature_of_fnb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureOfFnb extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  //1. QUAN HỆ ONE - TO - MANY: FeatureOfFnb <--> OutletFnbHasFeature
  // OutletFnbHasFeature sở hữu quan hệ (fk_feature_of_fnb_id_outlet_fnb_has_feature)
  @OneToMany(mappedBy = "featureOfFnb", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OutletFnbHasFeature> outletFnbHasFeatures;
}
