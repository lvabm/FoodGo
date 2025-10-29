package com.foodgo.backend.module.outlet_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "outlet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outlet extends BaseEntity {
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "address", length = 500)
  private String address;

  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;

  @Column(name = "type_id")
  private Integer typeId;

  @Column(name = "district_id")
  private Integer districtId;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;
}
