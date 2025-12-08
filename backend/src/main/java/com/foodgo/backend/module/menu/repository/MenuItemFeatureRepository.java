package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemFeatureRepository extends JpaRepository<MenuItemFeature, Integer> {}
