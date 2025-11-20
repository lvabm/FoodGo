package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "operating_hours",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"outlet_id", "day_of_week"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours extends BaseIntegerEntity<Integer> {

  @Column(name = "day_of_week", nullable = false)
  private Integer dayOfWeek;

  @Column(name = "open_time", nullable = false)
  private LocalTime openTime;

  @Column(name = "close_time", nullable = false)
  private LocalTime closeTime;

  @Column(name = "is_closed")
  @Builder.Default
  private Boolean isClosed = false;

  // 1. QUAN HỆ MANY - TO - ONE: OperatingHours <--> Outlet
  // OperatingHours sở hữu quan hệ (fk_outlet_id_operating_hours)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
