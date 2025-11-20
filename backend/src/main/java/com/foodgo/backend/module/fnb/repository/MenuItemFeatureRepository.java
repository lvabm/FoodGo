package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.MenuItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemFeatureRepository extends JpaRepository<MenuItemFeature, Integer> {
}
