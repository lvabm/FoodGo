package com.foodgo.backend.module.outlet_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.fnb_d.entity.FnbCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "outlet_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletCategory extends BaseEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  //1. QUAN HỆ ONE - TO - MANY: OutletCategory <--> FnbCategory
  // FnbCategory sở hữu quan hệ (fk_outlet_category_id_fnb_category)
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<FnbCategory> categories;

  //2. QUAN HỆ MANY - TO - ONE: OutletType  <--> OutletCategory
  // OutletCategory sở hữu quan hệ (fk_outlet_type_id_outlet_category)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "type_id", nullable = false)
  private OutletType type;
}
