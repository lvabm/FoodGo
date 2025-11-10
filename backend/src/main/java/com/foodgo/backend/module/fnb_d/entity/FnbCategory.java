package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.outlet_d.entity.OutletCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fnb_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FnbCategory extends BaseIntegerEntity<Integer> {

  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: FnbCategory <--> FnbSubCategory
  // FnbSubCategory sở hữu quan hệ (fk_fnb_category_id_fnb_sub_category)
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<FnbSubCategory> fnbSubCategories;

  // 2. QUAN HỆ MANY - TO - ONE: FnbType <--> FnbCategory
  // FnbCategory sở hữu quan hệ (fk_fnb_type_id_fnb_category)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "type_id", nullable = false)
  private FnbType type;

  // 2. QUAN HỆ MANY - TO - ONE: OutletCategory <--> FnbCategory
  // FnbCategory sở hữu quan hệ (fk_outlet_category_id_fnb_category)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", nullable = false)
  private OutletCategory category;
}
