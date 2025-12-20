package com.foodgo.backend.module.location.repository;

import com.foodgo.backend.module.location.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProvinceRepository
    extends JpaRepository<Province, Integer>, JpaSpecificationExecutor<Province> {}
