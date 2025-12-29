package com.foodgo.backend.module.notification.service;

import com.foodgo.backend.module.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {
  
  /**
   * Tạo notification mới (synchronous)
   */
  NotificationResponse createNotification(
      UUID userId, 
      String type, 
      String title, 
      String content, 
      UUID relatedId);
  
  /**
   * Tạo notification mới (asynchronous - không block transaction)
   */
  void createNotificationAsync(
      UUID userId, 
      String type, 
      String title, 
      String content, 
      UUID relatedId);
  
  /**
   * Lấy danh sách notifications của user
   */
  Page<NotificationResponse> getMyNotifications(Pageable pageable);
  
  /**
   * Đánh dấu notification là đã đọc
   */
  void markAsRead(Long notificationId);
  
  /**
   * Đánh dấu tất cả notifications là đã đọc
   */
  void markAllAsRead();
  
  /**
   * Đếm số notifications chưa đọc
   */
  long getUnreadCount();
}

