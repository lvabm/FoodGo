package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperatingHoursRepository
    extends JpaRepository<OperatingHours, Integer>, JpaSpecificationExecutor<OperatingHours> {
  
  /**
   * Tìm operating hours theo outlet ID và day of week
   * Tối ưu hơn findAll().stream().filter()
   */
  @Query("SELECT oh FROM OperatingHours oh WHERE oh.outlet.id = :outletId AND oh.dayOfWeek = :dayOfWeek")
  Optional<OperatingHours> findByOutletIdAndDayOfWeek(
      @Param("outletId") UUID outletId, 
      @Param("dayOfWeek") Integer dayOfWeek);
}
