package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.admin.dto.response.AdminStatisticsResponse;

/**
 * Service cho thống kê admin
 */
public interface AdminStatisticsService {
  
  /**
   * Lấy thống kê tổng quan cho admin dashboard
   */
  AdminStatisticsResponse getStatistics();
}


