package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.location_d.entity.Province;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "fnb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fnb extends BaseEntity {
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "description", length = 1000)
  private String description;

  //1. QUAN HỆ ONE - TO - MANY: Fnb  <--> OutletHasFnb
  // OutletHasFnb sở hữu quan hệ (fk_fnb_id_outlet_has_fnb)
  @OneToMany(mappedBy = "fnb", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OutletHasFnb> outletHasFnbs;

  //2. QUAN HỆ MANY - TO - ONE: FnbSubCategory  <--> Fnb
  // Fnb sở hữu quan hệ (fk_fnb_sub_category_id_fnb)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "sub_category_id", nullable = false)
  private FnbSubCategory subCategory;

  //3. QUAN HỆ MANY - TO - ONE: Province <--> Fnb
  // Fnb sở hữu quan hệ (fk_province_id_fnb)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "province_id", nullable = false)
  private Province province;
}
