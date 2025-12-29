package com.foodgo.backend.common.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.common.util.CacheUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Metrics", description = "API lấy metrics và thống kê hệ thống (Admin only)")
@PreAuthorize("hasRole('ADMIN')")
public class MetricsController {

  @Autowired(required = false)
  private CacheManager cacheManager;

  @GetMapping
  @Operation(summary = "Lấy metrics tổng quan của hệ thống")
  public ResponseEntity<BaseResponse<Map<String, Object>>> getMetrics() {
    Map<String, Object> metrics = new HashMap<>();
    
    // JVM Metrics
    metrics.put("jvm", getJvmMetrics());
    
    // Cache Metrics
    if (cacheManager != null) {
      metrics.put("cache", getCacheMetrics());
    }
    
    // System Metrics
    metrics.put("system", getSystemMetrics());
    
    metrics.put("timestamp", Instant.now());

    return ResponseEntity.ok(
        BaseResponse.<Map<String, Object>>builder()
            .success(true)
            .message("Lấy metrics thành công")
            .data(metrics)
            .build()
    );
  }

  @PostMapping("/cache/clear/{cacheName}")
  @Operation(summary = "Xóa cache cụ thể (Admin only)")
  public ResponseEntity<BaseResponse<String>> clearCache(@PathVariable String cacheName) {
    CacheUtils.clearCache(cacheManager, cacheName);
    log.info("Cache cleared: {}", cacheName);
    
    return ResponseEntity.ok(
        BaseResponse.<String>builder()
            .success(true)
            .message("Đã xóa cache: " + cacheName)
            .data("Cache cleared successfully")
            .build()
    );
  }

  @PostMapping("/cache/clear-all")
  @Operation(summary = "Xóa tất cả caches (Admin only)")
  public ResponseEntity<BaseResponse<String>> clearAllCaches() {
    CacheUtils.clearAllCaches(cacheManager);
    log.info("All caches cleared");
    
    return ResponseEntity.ok(
        BaseResponse.<String>builder()
            .success(true)
            .message("Đã xóa tất cả caches")
            .data("All caches cleared successfully")
            .build()
    );
  }

  private Map<String, Object> getJvmMetrics() {
    Map<String, Object> jvm = new HashMap<>();
    
    RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    
    // Memory
    long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
    long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
    long freeMemory = maxMemory - usedMemory;
    
    jvm.put("uptime", runtimeBean.getUptime());
    jvm.put("usedMemory", formatBytes(usedMemory));
    jvm.put("maxMemory", formatBytes(maxMemory));
    jvm.put("freeMemory", formatBytes(freeMemory));
    jvm.put("memoryUsagePercent", (usedMemory * 100.0 / maxMemory));
    jvm.put("jvmVersion", runtimeBean.getVmVersion());
    jvm.put("jvmName", runtimeBean.getVmName());
    
    return jvm;
  }

  private Map<String, Object> getCacheMetrics() {
    Map<String, Object> cache = new HashMap<>();
    
    if (cacheManager != null) {
      cache.put("cacheNames", cacheManager.getCacheNames());
      
      Map<String, String> stats = new HashMap<>();
      cacheManager.getCacheNames().forEach(cacheName -> {
        String stat = CacheUtils.getCacheStats(cacheManager, cacheName);
        stats.put(cacheName, stat);
      });
      cache.put("stats", stats);
    }
    
    return cache;
  }

  private Map<String, Object> getSystemMetrics() {
    Map<String, Object> system = new HashMap<>();
    
    Runtime runtime = Runtime.getRuntime();
    int processors = runtime.availableProcessors();
    long totalMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    long usedMemory = totalMemory - freeMemory;
    
    system.put("processors", processors);
    system.put("totalMemory", formatBytes(totalMemory));
    system.put("freeMemory", formatBytes(freeMemory));
    system.put("usedMemory", formatBytes(usedMemory));
    
    return system;
  }

  private String formatBytes(long bytes) {
    if (bytes < 1024) {
      return bytes + " B";
    } else if (bytes < 1024 * 1024) {
      return String.format("%.2f KB", bytes / 1024.0);
    } else if (bytes < 1024 * 1024 * 1024) {
      return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    } else {
      return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
  }
}


