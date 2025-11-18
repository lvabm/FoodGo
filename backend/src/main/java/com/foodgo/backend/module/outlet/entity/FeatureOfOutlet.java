package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "feature_of_outlet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureOfOutlet extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: FeatureOfOutlet <--> OutletHasFeature
  // FeatureOfOutlet sở hữu quan hệ (fk_feature_of_outlet_id_outlet_has_feature)
  @OneToMany(mappedBy = "featureOfOutlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OutletHasFeature> outletHasFeatures;
}
