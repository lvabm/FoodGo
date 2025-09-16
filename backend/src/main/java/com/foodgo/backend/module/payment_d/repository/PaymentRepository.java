package com.foodgo.backend.module.payment_d.repository;

import com.foodgo.backend.module.payment_d.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
