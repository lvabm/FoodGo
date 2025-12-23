package com.foodgo.backend.module.owner.dto;

import java.math.BigDecimal;
import java.util.List;

public record OwnerDashboardStats(
    Integer todayBookings,
    Integer todayCheckins,
    BigDecimal averageRating,
    Integer totalReviews,
    BigDecimal monthRevenue,
    List<WeekBooking> weekBookings,
    List<UpcomingBooking> upcomingBookings,
    List<RecentReview> recentReviews
) {

  public record WeekBooking(String dayName, Integer count) {}

  public record UpcomingBooking(
      String bookingId,
      String customerName,
      String bookingDate,
      Integer numberOfGuests,
      String status
  ) {}

  public record RecentReview(
      String reviewId,
      String customerName,
      Integer rating,
      String comment,
      String timeAgo
  ) {}
}
