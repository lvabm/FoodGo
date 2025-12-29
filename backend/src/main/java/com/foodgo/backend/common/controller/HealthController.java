package com.foodgo.backend.common.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health Check", description = "API kiểm tra trạng thái hệ thống")
public class HealthController {

  @Autowired(required = false)
  private DataSource dataSource;

  @GetMapping
  @Operation(summary = "Kiểm tra trạng thái hệ thống")
  public ResponseEntity<BaseResponse<Map<String, Object>>> health() {
    Map<String, Object> healthData = new HashMap<>();
    healthData.put("status", "UP");
    healthData.put("timestamp", Instant.now());
    healthData.put("service", "FoodGo Backend API");
    healthData.put("version", "1.0.0");
    
    // Check database connection
    Map<String, String> database = checkDatabase();
    healthData.put("database", database);
    
    // Overall status
    String overallStatus = "UP".equals(database.get("status")) ? "UP" : "DOWN";
    healthData.put("status", overallStatus);

    log.debug("Health check requested - System status: {}", overallStatus);
    
    return ResponseEntity.ok(
        BaseResponse.<Map<String, Object>>builder()
            .success(true)
            .message("Hệ thống đang hoạt động bình thường")
            .data(healthData)
            .build()
    );
  }

  @GetMapping("/liveness")
  @Operation(summary = "Liveness probe - Kiểm tra ứng dụng còn sống")
  public ResponseEntity<Map<String, String>> liveness() {
    return ResponseEntity.ok(Map.of(
        "status", "UP",
        "timestamp", Instant.now().toString()
    ));
  }

  @GetMapping("/readiness")
  @Operation(summary = "Readiness probe - Kiểm tra ứng dụng sẵn sàng nhận requests")
  public ResponseEntity<Map<String, Object>> readiness() {
    Map<String, Object> readiness = new HashMap<>();
    readiness.put("status", "UP");
    readiness.put("timestamp", Instant.now());
    
    Map<String, String> database = checkDatabase();
    readiness.put("database", database);
    
    String status = "UP".equals(database.get("status")) ? "UP" : "DOWN";
    readiness.put("status", status);
    
    return ResponseEntity.ok(readiness);
  }

  private Map<String, String> checkDatabase() {
    Map<String, String> dbStatus = new HashMap<>();
    
    if (dataSource == null) {
      dbStatus.put("status", "UNKNOWN");
      dbStatus.put("message", "DataSource not configured");
      return dbStatus;
    }

    try (Connection connection = dataSource.getConnection()) {
      if (connection.isValid(2)) { // 2 seconds timeout
        dbStatus.put("status", "UP");
        dbStatus.put("message", "Database connection successful");
        dbStatus.put("database", connection.getMetaData().getDatabaseProductName());
        dbStatus.put("version", connection.getMetaData().getDatabaseProductVersion());
      } else {
        dbStatus.put("status", "DOWN");
        dbStatus.put("message", "Database connection invalid");
      }
    } catch (Exception e) {
      log.error("Database health check failed", e);
      dbStatus.put("status", "DOWN");
      dbStatus.put("message", "Database connection failed: " + e.getMessage());
    }
    
    return dbStatus;
  }
}

