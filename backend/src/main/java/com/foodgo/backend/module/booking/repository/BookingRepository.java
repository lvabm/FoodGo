package com.foodgo.backend.module.booking.repository;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.module.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository
    extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {
  
  Integer countByOutletIdInAndBookingDateBetween(
      List<UUID> outletIds, LocalDate start, LocalDate end);

  Integer countByOutletIdInAndBookingDateBetweenAndUserCheckedInAtIsNotNullAndOwnerCheckedInAtIsNotNull(
      List<UUID> outletIds, LocalDate start, LocalDate end);

  List<Booking> findTop5ByOutletIdInAndBookingDateGreaterThanEqualAndStatusInOrderByBookingDateAsc(
      List<UUID> outletIds, LocalDate bookingDate, List<BookingStatus> statuses);
} 

