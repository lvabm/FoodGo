package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, Integer> {
}
