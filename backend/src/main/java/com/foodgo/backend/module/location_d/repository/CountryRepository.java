package com.foodgo.backend.module.location_d.repository;

import com.foodgo.backend.module.location_d.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {}
