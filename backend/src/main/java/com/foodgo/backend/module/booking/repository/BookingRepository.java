package com.foodgo.backend.module.booking.repository;

import com.foodgo.backend.module.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
