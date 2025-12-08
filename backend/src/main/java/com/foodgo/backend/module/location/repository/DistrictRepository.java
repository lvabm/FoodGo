package com.foodgo.backend.module.location.repository;

import com.foodgo.backend.module.location.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DistrictRepository
    extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {}
