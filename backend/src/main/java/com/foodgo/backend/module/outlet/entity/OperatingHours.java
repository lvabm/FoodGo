package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "operating_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours extends BaseEntity {
  @Column(name = "outlet_id", nullable = false)
  private UUID outletId;

  @Column(name = "day_of_week", nullable = false)
  private int dayOfWeek;

  @Column(name = "open_time", length = 10)
  private String openTime;

  @Column(name = "close_time", length = 10)
  private String closeTime;
}
