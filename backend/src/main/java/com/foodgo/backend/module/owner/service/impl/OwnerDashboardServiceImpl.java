package com.foodgo.backend.module.owner.service.impl;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.owner.dto.OwnerDashboardStats;
import com.foodgo.backend.module.owner.service.OwnerDashboardService;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerDashboardServiceImpl implements OwnerDashboardService {

  private static final Logger log = LoggerFactory.getLogger(OwnerDashboardServiceImpl.class);

  private final OutletRepository outletRepository;
  private final BookingRepository bookingRepository;
  private final ReviewRepository reviewRepository;

  @Override
  @Transactional(readOnly = true)
  public OwnerDashboardStats getOwnerStats() {
    UUID ownerId = SecurityContext.getCurrentUserId();
    List<Outlet> outlets = outletRepository.findAllByOwnerId(ownerId);
    
    // Log outlets for debugging reasons - helps explain empty dashboard
    List<UUID> outletIds = outlets.stream().map(Outlet::getId).toList();
    log.info("Owner {} has {} outlets: {}", ownerId, outletIds.size(), outletIds);

    if (outlets.isEmpty()) {
      return buildEmptyStats();
    }
    
    return computeStats(outletIds);
  }

  @Override
  @Transactional(readOnly = true)
  public OwnerDashboardStats getOutletStats(UUID outletId) {
    // Verify owner permission
    UUID ownerId = SecurityContext.getCurrentUserId();
    Outlet outlet = outletRepository.findById(outletId)
        .orElseThrow(() -> new RuntimeException("Outlet not found"));
    
    if (!outlet.getOwner().getId().equals(ownerId)) {
      throw new RuntimeException("Unauthorized");
    }

    return computeStats(List.of(outletId));
  }

  private OwnerDashboardStats computeStats(List<UUID> outletIds) {
    LocalDate today = LocalDate.now();

    // 1. Today bookings
    Integer todayBookings = bookingRepository.countByOutletIdInAndBookingDateBetween(
        outletIds, today, today
    );

    // 2. Today checkins (both user and owner checked in)
    Integer todayCheckins = bookingRepository.countByOutletIdInAndBookingDateBetweenAndUserCheckedInAtIsNotNullAndOwnerCheckedInAtIsNotNull(
        outletIds, today, today
    );

    // 3. Average rating & total reviews
    List<Review> allReviews = reviewRepository.findByOutletIdIn(outletIds);
    BigDecimal avgRating = null;
    Integer totalReviews = allReviews.size();
    
    if (!allReviews.isEmpty()) {
      double avg = allReviews.stream()
          .mapToInt(r -> r.getOverallRating() != null ? r.getOverallRating() : 0)
          .average()
          .orElse(0.0);
      if (avg > 0) {
        avgRating = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
      }
    }

    // 4. Month revenue: sum deposit amounts of COMPLETED bookings in current month
    LocalDate nowDate = LocalDate.now();
    LocalDate monthStart = nowDate.withDayOfMonth(1);
    LocalDate monthEnd = nowDate.withDayOfMonth(nowDate.lengthOfMonth());

    BigDecimal monthRevenue = bookingRepository.sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
        outletIds,
        monthStart,
        monthEnd,
        List.of(com.foodgo.backend.common.constant.BookingStatus.COMPLETED)
    );
    if (monthRevenue == null) monthRevenue = BigDecimal.ZERO;
    log.info("Month revenue for outlets {} between {} and {} = {}", outletIds, monthStart, monthEnd, monthRevenue);

    // 5. Week bookings (last 7 days)
    List<OwnerDashboardStats.WeekBooking> weekBookings = computeWeekBookings(outletIds);

    // 6. Upcoming bookings (next 5 bookings with PENDING/CONFIRMED status, ordered by bookingDate)
    List<OwnerDashboardStats.UpcomingBooking> upcomingBookings = getUpcomingBookings(outletIds);

    // 7. Recent reviews (last 5 reviews)
    List<OwnerDashboardStats.RecentReview> recentReviews = getRecentReviews(allReviews);

    return new OwnerDashboardStats(
        todayBookings,
        todayCheckins,
        avgRating,
        totalReviews,
        monthRevenue,
        weekBookings,
        upcomingBookings,
        recentReviews
    );
  }

  private List<OwnerDashboardStats.WeekBooking> computeWeekBookings(List<UUID> outletIds) {
    LocalDate today = LocalDate.now();
    List<OwnerDashboardStats.WeekBooking> result = new ArrayList<>();

    String[] dayNames = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};

    for (int i = 6; i >= 0; i--) {
      LocalDate day = today.minusDays(i);

      Integer count = bookingRepository.countByOutletIdInAndBookingDateBetween(
          outletIds, day, day
      );

      String dayName = dayNames[(day.getDayOfWeek().getValue() - 1) % 7];
      result.add(new OwnerDashboardStats.WeekBooking(dayName, count));
    }

    return result;
  }

  private List<OwnerDashboardStats.UpcomingBooking> getUpcomingBookings(List<UUID> outletIds) {
    LocalDate now = LocalDate.now();

    List<Booking> bookings = bookingRepository.findTop5ByOutletIdInAndBookingDateGreaterThanEqualAndStatusInOrderByBookingDateAsc(
        outletIds,
        now,
        List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
    );

    return bookings.stream()
        .map(b -> {
          String bookingDateStr = "";
          if (b.getBookingDate() != null && b.getBookingTime() != null) {
            bookingDateStr = b.getBookingDate().atTime(b.getBookingTime()).toString();
          } else if (b.getBookingDate() != null) {
            bookingDateStr = b.getBookingDate().toString();
          }

          return new OwnerDashboardStats.UpcomingBooking(
              b.getId().toString(),
              b.getUser().getProfile() != null ? b.getUser().getProfile().getFullName() : "N/A",
              bookingDateStr,
              b.getNumberOfGuests(),
              mapStatus(b.getStatus())
          );
        })
        .collect(Collectors.toList());
  }

  private List<OwnerDashboardStats.RecentReview> getRecentReviews(List<Review> allReviews) {
    return allReviews.stream()
        // Sort by createAt DESC, but handle nulls safely by placing them last
        .sorted(Comparator.comparing(Review::getCreateAt, Comparator.nullsLast(Comparator.reverseOrder())))
        .limit(5)
        .map(r -> {
          String customerName = r.getUser().getProfile() != null 
              ? r.getUser().getProfile().getFullName() 
              : "Anonymous";
          
          String timeAgo = formatTimeAgo(r.getCreateAt());

          return new OwnerDashboardStats.RecentReview(
              r.getId().toString(),
              customerName,
              r.getOverallRating(),
              r.getComment(),
              timeAgo
          );
        })
        .collect(Collectors.toList());
  }

  private String mapStatus(BookingStatus status) {
    return switch (status) {
      case PENDING -> "Chờ xác nhận";
      case CONFIRMED -> "Đã xác nhận";
      case COMPLETED -> "Hoàn thành";
      case CANCELLED -> "Đã hủy";
      default -> status.name();
    };
  }

  private String formatTimeAgo(LocalDateTime time) {
    if (time == null) return "";
    
    Duration duration = Duration.between(time, LocalDateTime.now());
    
    long hours = duration.toHours();
    if (hours < 1) {
      return duration.toMinutes() + " phút trước";
    } else if (hours < 24) {
      return hours + " giờ trước";
    } else {
      long days = duration.toDays();
      return days + " ngày trước";
    }
  }

  private OwnerDashboardStats buildEmptyStats() {
    return new OwnerDashboardStats(
        0, 0, null, 0, BigDecimal.ZERO,
        List.of(),
        List.of(),
        List.of()
    );
  }
}
