package com.foodgo.backend.module.admin.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Response DTO cho thống kê admin
 */
public record AdminStatisticsResponse(
    // Tổng quan
    OverviewStats overview,
    
    // Thống kê theo thời gian
    TimeStats timeStats,
    
    // Top items
    TopItems topItems,
    
    // Growth metrics
    GrowthMetrics growth
) {
  
  public record OverviewStats(
      Long totalUsers,
      Long totalOutlets,
      Long totalBookings,
      Long totalReviews,
      Long totalOrders,
      BigDecimal totalRevenue,
      BigDecimal monthlyRevenue,
      BigDecimal todayRevenue
  ) {}
  
  public record TimeStats(
      List<DailyStat> last7Days,
      List<MonthlyStat> last6Months,
      TodayStats today
  ) {}
  
  public record DailyStat(
      LocalDate date,
      String dayName,
      Long bookings,
      Long users,
      BigDecimal revenue
  ) {}
  
  public record MonthlyStat(
      Integer year,
      Integer month,
      String monthName,
      Long bookings,
      Long users,
      BigDecimal revenue
  ) {}
  
  public record TodayStats(
      Long bookings,
      Long newUsers,
      Long newOutlets,
      BigDecimal revenue
  ) {}
  
  public record TopItems(
      List<TopOutlet> topOutlets,
      List<TopCategory> topCategories,
      List<TopUser> topUsers
  ) {}
  
  public record TopOutlet(
      String id,
      String name,
      Long bookings,
      BigDecimal revenue,
      Double rating,
      Long reviews
  ) {}
  
  public record TopCategory(
      String id,
      String name,
      Long outletCount,
      Long bookings
  ) {}
  
  public record TopUser(
      String id,
      String name,
      Long bookings,
      Long reviews
  ) {}
  
  public record GrowthMetrics(
      Double userGrowthRate,
      Double outletGrowthRate,
      Double bookingGrowthRate,
      Double revenueGrowthRate
  ) {}
}


