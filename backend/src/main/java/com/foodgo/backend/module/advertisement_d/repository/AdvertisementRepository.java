package com.foodgo.backend.module.advertisement_d.repository;

import com.foodgo.backend.module.advertisement_d.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {}
