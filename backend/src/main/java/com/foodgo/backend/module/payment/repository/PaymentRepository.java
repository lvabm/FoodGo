package com.foodgo.backend.module.payment.repository;

import com.foodgo.backend.module.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
  
  /**
   * Tìm payment theo relatedId và type (BOOKING hoặc MEMBERSHIP)
   */
  @Query("SELECT p FROM Payment p WHERE p.relatedId = :relatedId AND p.type = :type ORDER BY p.createdAt DESC")
  Optional<Payment> findLatestByRelatedIdAndType(
      @Param("relatedId") String relatedId,
      @Param("type") com.foodgo.backend.common.constant.PaymentType type);
}
