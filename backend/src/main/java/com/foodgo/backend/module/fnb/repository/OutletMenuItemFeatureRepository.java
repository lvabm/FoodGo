package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.OutletMenuItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletMenuItemFeatureRepository extends JpaRepository<OutletMenuItemFeature, Integer> {
}
