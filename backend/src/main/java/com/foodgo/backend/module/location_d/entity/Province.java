package com.foodgo.backend.module.location_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "country_id", nullable = false)
  private Long countryId;
}
