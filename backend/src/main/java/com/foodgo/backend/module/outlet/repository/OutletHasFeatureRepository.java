package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OutletHasFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletHasFeatureRepository extends JpaRepository<OutletHasFeature, Long> {
}
