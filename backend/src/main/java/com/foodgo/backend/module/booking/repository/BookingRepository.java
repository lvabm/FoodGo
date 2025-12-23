package com.foodgo.backend.module.booking.repository;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.module.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  // Sum of deposit amounts for bookings (used for revenue calculation)
  @Query("SELECT COALESCE(SUM(b.depositAmount), 0) FROM Booking b WHERE b.outlet.id IN :outletIds AND b.bookingDate BETWEEN :start AND :end AND b.status IN :statuses")
  java.math.BigDecimal sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
      @Param("outletIds") List<UUID> outletIds, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("statuses") List<BookingStatus> statuses);
} 

