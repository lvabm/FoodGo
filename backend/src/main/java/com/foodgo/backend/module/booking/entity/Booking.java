package com.foodgo.backend.module.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
  private UUID userId;

  @Column(name = "outlet_id", nullable = false, columnDefinition = "UUID")
  private UUID outletId;

  @Column(name = "booking_time", nullable = false)
  private LocalDateTime bookingTime;

  @Column(name = "number_of_people", nullable = false)
  private Integer numberOfPeople;

  @Column(nullable = false, length = 50)
  private String status = "pending";
}
