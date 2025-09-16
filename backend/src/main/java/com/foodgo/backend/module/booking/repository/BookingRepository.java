package com.foodgo.backend.module.booking.repository;

import com.foodgo.backend.module.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

  List<Booking> findByUserId(UUID userId);

  List<Booking> findByOutletId(UUID outletId);

  List<Booking> findByStatus(String status);
}
