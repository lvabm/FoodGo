package com.foodgo.backend.module.notification.controller;

import com.foodgo.backend.module.notification.dto.response.NotificationResponse;
import com.foodgo.backend.module.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification", description = "API quản lý thông báo")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping("/me")
  @Operation(summary = "Lấy danh sách thông báo của tôi")
  public Page<NotificationResponse> getMyNotifications(Pageable pageable) {
    return notificationService.getMyNotifications(pageable);
  }

  @GetMapping("/unread-count")
  @Operation(summary = "Đếm số thông báo chưa đọc")
  public long getUnreadCount() {
    return notificationService.getUnreadCount();
  }

  @PatchMapping("/{id}/read")
  @Operation(summary = "Đánh dấu thông báo là đã đọc")
  public void markAsRead(@PathVariable Long id) {
    notificationService.markAsRead(id);
  }

  @PatchMapping("/mark-all-read")
  @Operation(summary = "Đánh dấu tất cả thông báo là đã đọc")
  public void markAllAsRead() {
    notificationService.markAllAsRead();
  }
}


