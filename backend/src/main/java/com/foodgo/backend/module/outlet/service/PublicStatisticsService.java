package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.module.outlet.dto.response.PublicStatisticsResponse;

/**
 * Service cho thống kê công khai
 * Cung cấp số liệu tổng quan cho trang chủ
 */
public interface PublicStatisticsService {
  
  /**
   * Lấy thống kê công khai (tổng số quán, món ăn, đánh giá, người dùng)
   */
  PublicStatisticsResponse getPublicStatistics();
}

