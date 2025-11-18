package com.foodgo.backend.module.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

  @Operation(summary = "Cấu hình kênh thông báo")
  @GetMapping("/users/{userId}")
  private void getNotification(@PathVariable Integer userId) {}

  @Operation(summary = "Cấu hình kênh thông báo")
  @PutMapping("/users/{userId}")
  private void puNotification(@PathVariable Integer userId) {}
}
