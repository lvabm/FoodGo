package com.foodgo.backend.module.payment.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.module.booking.entity.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseUUIDEntity {

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "payment_method", nullable = false, length = 20)
  private String paymentMethod;

  @Column(name = "payment_status", nullable = false, length = 20)
  @Builder.Default
  private String paymentStatus = "pending";

  @Column(name = "transaction_id", length = 255, unique = true)
  private String transactionId;

  // 1. QUAN HỆ MANY - TO - ONE: Payment <--> Booking
  // Payment sở hữu quan hệ (fk_booking_id_payment)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;
}
