package com.foodgo.backend.module.booking.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.common.constant.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "outlet_id", nullable = false)
    private UUID outletId;

    @Column(name = "booking_time", nullable = false)
    private Instant bookingTime;

    @Column(name = "number_of_people", nullable = false)
    private int numberOfPeople;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "note", length = 500)
    private String note;
}
