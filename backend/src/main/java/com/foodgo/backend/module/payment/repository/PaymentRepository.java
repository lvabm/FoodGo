package com.foodgo.backend.module.payment.repository;

import com.foodgo.backend.module.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
