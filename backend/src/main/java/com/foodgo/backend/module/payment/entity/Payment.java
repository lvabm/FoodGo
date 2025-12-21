package com.foodgo.backend.module.payment.entity;

import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.common.constant.PaymentMethod;
import com.foodgo.backend.common.constant.PaymentStatus;
import com.foodgo.backend.common.constant.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

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
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @Column(name = "payment_status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private PaymentStatus paymentStatus = PaymentStatus.PENDING;

  @Column(name = "transaction_id", length = 255, unique = true)
  private String transactionId;

  // ID của đối tượng liên quan (BookingID hoặc UserMembershipID)
  @Column(name = "related_id", nullable = false)
  private String relatedId;

  // Loại thanh toán: BOOKING hoặc MEMBERSHIP
  @Column(name = "type", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private PaymentType type;
}
