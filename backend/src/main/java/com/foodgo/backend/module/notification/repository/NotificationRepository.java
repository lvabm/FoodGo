package com.foodgo.backend.module.notification.repository;

import com.foodgo.backend.module.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  
  Page<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
  
  long countByUserIdAndIsReadFalse(UUID userId);
  
  @Modifying
  @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId AND n.isRead = false")
  int markAllAsReadByUserId(@Param("userId") UUID userId);
  
  @Modifying
  @Query("UPDATE Notification n SET n.isRead = true WHERE n.id = :id")
  int markAsRead(@Param("id") Long id);
}


