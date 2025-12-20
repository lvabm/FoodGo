package com.foodgo.backend.module.advertisement.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "advertisement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertisement extends BaseIntegerEntity<Integer> {

  @Column(name = "position", nullable = false, length = 50)
  private String position;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "is_active")
  @Builder.Default
  private Boolean isActive = true;

  // 1. QUAN HỆ MANY - TO - ONE: Advertisement <--> Outlet
  // Advertisement sở hữu quan hệ (fk_outlet_id_advertisement)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
