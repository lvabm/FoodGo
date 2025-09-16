package com.foodgo.backend.module.booking_d.repository;

import com.foodgo.backend.module.booking_d.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
