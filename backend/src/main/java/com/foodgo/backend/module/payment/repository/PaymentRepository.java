package com.foodgo.backend.module.payment.repository;

import com.foodgo.backend.module.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
