package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OutletType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletTypeRepository extends JpaRepository<OutletType, Long> {
}
