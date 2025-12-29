package com.foodgo.backend.module.notification.service.impl;

import com.foodgo.backend.common.constant.NotificationType;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.notification.dto.mapper.NotificationMapper;
import com.foodgo.backend.module.notification.dto.response.NotificationResponse;
import com.foodgo.backend.module.notification.entity.Notification;
import com.foodgo.backend.module.notification.repository.NotificationRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;
  private final UserAccountRepository userAccountRepository;

  @Override
  @Transactional
  public NotificationResponse createNotification(
      UUID userId, 
      String type, 
      String title, 
      String content, 
      UUID relatedId) {
    
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    
    // Validate notification type
    if (type != null && !NotificationType.isValid(type)) {
      log.warn("Invalid notification type: {}", type);
      // Không throw exception, chỉ log warning để không fail operation
    }
    
    // Validate required fields
    if (title == null || title.trim().isEmpty()) {
      throw new BadRequestException("Tiêu đề thông báo không được để trống.");
    }
    
    if (content == null || content.trim().isEmpty()) {
      throw new BadRequestException("Nội dung thông báo không được để trống.");
    }
    
    Notification notification = Notification.builder()
        .user(userAccountRepository.getReferenceById(userId))
        .type(type != null ? type : NotificationType.SYSTEM_ANNOUNCEMENT.getCode())
        .title(title.trim())
        .content(content.trim())
        .relatedId(relatedId)
        .isRead(false)
        .build();
    
    Notification saved = notificationRepository.save(notification);
    log.info("Created notification {} (type: {}) for user {}", saved.getId(), type, userId);
    
    return notificationMapper.toResponse(saved);
  }

  @Override
  @Async("notificationExecutor")
  @Transactional
  public void createNotificationAsync(
      UUID userId, 
      String type, 
      String title, 
      String content, 
      UUID relatedId) {
    try {
      createNotification(userId, type, title, content, relatedId);
      log.debug("Async notification created successfully for user {}", userId);
    } catch (Exception e) {
      log.error("Failed to create async notification for user {}: {}", userId, e.getMessage(), e);
      // Không throw exception để không ảnh hưởng đến main transaction
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<NotificationResponse> getMyNotifications(Pageable pageable) {
    UUID userId = SecurityContext.getCurrentUserId();
    return notificationRepository
        .findByUserIdOrderByCreatedAtDesc(userId, pageable)
        .map(notificationMapper::toResponse);
  }

  @Override
  @Transactional
  public void markAsRead(Long notificationId) {
    if (notificationId == null) {
      throw new IllegalArgumentException("Notification ID cannot be null");
    }
    
    UUID userId = SecurityContext.getCurrentUserId();
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new ResourceNotFoundException("Thông báo không tồn tại."));
    
    if (notification.getUser() == null || !notification.getUser().getId().equals(userId)) {
      throw new ForbiddenException("Bạn không có quyền đánh dấu thông báo này là đã đọc.");
    }
    
    notificationRepository.markAsRead(notificationId);
    log.debug("Marked notification {} as read", notificationId);
  }

  @Override
  @Transactional
  public void markAllAsRead() {
    UUID userId = SecurityContext.getCurrentUserId();
    int count = notificationRepository.markAllAsReadByUserId(userId);
    log.info("Marked {} notifications as read for user {}", count, userId);
  }

  @Override
  @Transactional(readOnly = true)
  public long getUnreadCount() {
    UUID userId = SecurityContext.getCurrentUserId();
    return notificationRepository.countByUserIdAndIsReadFalse(userId);
  }
}

