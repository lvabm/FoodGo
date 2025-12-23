package com.foodgo.backend.module.location.repository;

import com.foodgo.backend.module.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository
    extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByCodeIgnoreCase(String code);
}
