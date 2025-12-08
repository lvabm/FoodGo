package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletMenuItemFeatureRepository
    extends JpaRepository<OutletMenuItemFeature, Integer> {}
