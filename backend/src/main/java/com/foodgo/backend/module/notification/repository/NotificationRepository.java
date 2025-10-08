package com.foodgo.backend.module.notification.repository;

import com.foodgo.backend.module.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
