package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "operating_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours extends BaseIntegerEntity<Integer> {

  @Column(name = "day_of_week", nullable = false)
  private int dayOfWeek;

  @Column(name = "open_time", length = 10)
  private String openTime;

  @Column(name = "close_time", length = 10)
  private String closeTime;

  // 1. QUAN HỆ MANY - TO - ONE: OperatingHours <--> Outlet
  // OperatingHours sở hữu quan hệ (fk_outlet_id_operating_hours)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
