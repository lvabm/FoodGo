package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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

  @Column(name = "sub_category_id")
  private Long subCategoryId;

  @Column(name = "province_id")
  private Integer provinceId;
}
