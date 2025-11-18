package com.foodgo.backend.module.location.repository;

import com.foodgo.backend.module.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {}
