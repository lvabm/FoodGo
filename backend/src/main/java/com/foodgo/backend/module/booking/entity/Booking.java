package com.foodgo.backend.module.booking.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseUUIDEntity {

  @Column(name = "booking_time", nullable = false)
  private Instant bookingTime;

  @Column(name = "number_of_people", nullable = false)
  private int numberOfPeople;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private BookingStatus status = BookingStatus.PENDING;

  @Column(name = "note", length = 500)
  private String note;

  // 1. QUAN HỆ ONE - TO - MANY: Booking <--> Payment
  // Payment sở hữu quan hệ (fk_booking_id_payment)
  @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Payment> payments;

  // 2. QUAN HỆ ONE - TO - MANY: Booking <--> Review
  // Review sở hữu quan hệ (fk_booking_id_review)
  @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Review> reviews;

  // 3. QUAN HỆ MANY - TO - ONE: UserAccount <--> Booking
  // Booking sở hữu quan hệ (fk_user_account_id_booking)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 4. QUAN HỆ MANY - TO - ONE: Outlet <--> Booking
  // Booking sở hữu quan hệ (fk_outlet_id_booking)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
