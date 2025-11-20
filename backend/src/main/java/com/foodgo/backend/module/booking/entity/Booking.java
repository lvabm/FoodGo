package com.foodgo.backend.module.booking.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseUUIDEntity {

  @Column(name = "booking_date", nullable = false)
  private LocalDate bookingDate;

  @Column(name = "booking_time", nullable = false)
  private LocalTime bookingTime;

  @Column(name = "number_of_guests", nullable = false)
  private Integer numberOfGuests;

  @Column(name = "status", nullable = false, length = 20)
  @Builder.Default
  private String status = "pending";

  @Column(name = "deposit_amount", nullable = false)
  @Builder.Default
  private BigDecimal depositAmount = BigDecimal.ZERO;

  @Column(name = "user_notes", columnDefinition = "TEXT")
  private String userNotes;

  @Column(name = "owner_notes", columnDefinition = "TEXT")
  private String ownerNotes;

  // 1. QUAN HỆ ONE - TO - MANY: Booking <--> Payment
  // Payment sở hữu quan hệ (fk_booking_id_payment)
  @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Payment> payments;

  // 2. QUAN HỆ ONE - TO-ONE: Booking <--> Review
  // Review sở hữu quan hệ (fk_booking_id_review)
  @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Review review;

  // 3. QUAN HỆ MANY - TO - ONE: UserAccount <--> Booking
  // Booking sở hữu quan hệ (fk_user_account_id_booking)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount user;

  // 4. QUAN HỆ MANY - TO - ONE: Outlet <--> Booking
  // Booking sở hữu quan hệ (fk_outlet_id_booking)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
