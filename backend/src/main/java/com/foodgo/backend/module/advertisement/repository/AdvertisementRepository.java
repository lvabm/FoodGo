package com.foodgo.backend.module.advertisement.repository;

import com.foodgo.backend.module.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {}
