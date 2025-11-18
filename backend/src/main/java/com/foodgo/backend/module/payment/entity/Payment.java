package com.foodgo.backend.module.payment.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.common.constant.PaymentStatus;
import com.foodgo.backend.common.constant.PaymentMethod;
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

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private PaymentStatus status = PaymentStatus.PENDING;

  @Enumerated(EnumType.STRING)
  @Column(name = "method", nullable = false)
  private PaymentMethod method;

  @Column(name = "transaction_id", length = 100)
  private String transactionId;

  // 1. QUAN HỆ ONE - TO - ONE: Payment <--> Booking
  // Payment sở hữu quan hệ (fk_booking_id_payment)
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id", nullable = false, unique = true)
  private Booking booking;
}
