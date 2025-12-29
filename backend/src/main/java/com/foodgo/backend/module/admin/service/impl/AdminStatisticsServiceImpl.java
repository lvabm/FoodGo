package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.module.admin.dto.response.AdminStatisticsResponse;
import com.foodgo.backend.module.admin.service.AdminStatisticsService;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

  private final UserAccountRepository userAccountRepository;
  private final OutletRepository outletRepository;
  private final BookingRepository bookingRepository;
  private final ReviewRepository reviewRepository;

  @Override
  @Transactional(readOnly = true)
  public AdminStatisticsResponse getStatistics() {
    log.info("Calculating admin statistics...");
    
    // Cache outlet IDs to avoid multiple queries
    List<java.util.UUID> allOutletIds = getAllOutletIds();
    
    // Overview Stats
    AdminStatisticsResponse.OverviewStats overview = calculateOverviewStats(allOutletIds);
    
    // Time Stats
    AdminStatisticsResponse.TimeStats timeStats = calculateTimeStats(allOutletIds);
    
    // Top Items
    AdminStatisticsResponse.TopItems topItems = calculateTopItems();
    
    // Growth Metrics
    AdminStatisticsResponse.GrowthMetrics growth = calculateGrowthMetrics(allOutletIds);
    
    log.info("Admin statistics calculated successfully");
    return new AdminStatisticsResponse(overview, timeStats, topItems, growth);
  }

  private AdminStatisticsResponse.OverviewStats calculateOverviewStats(List<java.util.UUID> allOutletIds) {
    // Total counts
    long totalUsers = userAccountRepository.count();
    long totalOutlets = outletRepository.count();
    long totalBookings = bookingRepository.count();
    long totalReviews = reviewRepository.count();
    
    // Revenue calculations
    LocalDate today = LocalDate.now();
    LocalDate monthStart = today.withDayOfMonth(1);
    LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());
    
    // Total revenue (all completed bookings)
    BigDecimal totalRevenue = calculateTotalRevenue(allOutletIds);
    
    // Monthly revenue (completed bookings this month)
    BigDecimal monthlyRevenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            monthStart,
            monthEnd,
            List.of(BookingStatus.COMPLETED)
        );
    if (monthlyRevenue == null) monthlyRevenue = BigDecimal.ZERO;
    
    // Today revenue
    BigDecimal todayRevenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            today,
            today,
            List.of(BookingStatus.COMPLETED)
        );
    if (todayRevenue == null) todayRevenue = BigDecimal.ZERO;
    
    // Total orders (same as bookings for now)
    long totalOrders = totalBookings;
    
    return new AdminStatisticsResponse.OverviewStats(
        totalUsers,
        totalOutlets,
        totalBookings,
        totalReviews,
        totalOrders,
        totalRevenue,
        monthlyRevenue,
        todayRevenue
    );
  }

  private AdminStatisticsResponse.TimeStats calculateTimeStats(List<java.util.UUID> allOutletIds) {
    // Last 7 days
    List<AdminStatisticsResponse.DailyStat> last7Days = calculateLast7Days(allOutletIds);
    
    // Last 6 months
    List<AdminStatisticsResponse.MonthlyStat> last6Months = calculateLast6Months(allOutletIds);
    
    // Today stats
    AdminStatisticsResponse.TodayStats today = calculateTodayStats(allOutletIds);
    
    return new AdminStatisticsResponse.TimeStats(last7Days, last6Months, today);
  }

  private List<AdminStatisticsResponse.DailyStat> calculateLast7Days(List<java.util.UUID> allOutletIds) {
    List<AdminStatisticsResponse.DailyStat> stats = new ArrayList<>();
    LocalDate today = LocalDate.now();
    String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
    
    for (int i = 6; i >= 0; i--) {
      LocalDate date = today.minusDays(i);
      String dayName = dayNames[date.getDayOfWeek().getValue() % 7];
      
      // Count bookings for this day
      long bookings = bookingRepository.countByOutletIdInAndBookingDateBetween(
          allOutletIds, date, date
      );
      
      // Count new users created on this day
      // Note: BaseUUIDEntity doesn't have createdAt, so we'll use total count for now
      // TODO: Add createdAt field to BaseUUIDEntity or use JPA auditing
      long users = 0; // Will be calculated from total if needed
      
      // Calculate revenue for this day
      BigDecimal revenue = bookingRepository
          .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
              allOutletIds,
              date,
              date,
              List.of(BookingStatus.COMPLETED)
          );
      if (revenue == null) revenue = BigDecimal.ZERO;
      
      stats.add(new AdminStatisticsResponse.DailyStat(date, dayName, bookings, users, revenue));
    }
    
    return stats;
  }

  private List<AdminStatisticsResponse.MonthlyStat> calculateLast6Months(List<java.util.UUID> allOutletIds) {
    List<AdminStatisticsResponse.MonthlyStat> stats = new ArrayList<>();
    LocalDate today = LocalDate.now();
    
    for (int i = 5; i >= 0; i--) {
      LocalDate monthDate = today.minusMonths(i);
      int year = monthDate.getYear();
      int month = monthDate.getMonthValue();
      String monthName = monthDate.getMonth().getDisplayName(TextStyle.SHORT, new Locale("vi"));
      
      LocalDate monthStart = monthDate.withDayOfMonth(1);
      LocalDate monthEnd = monthDate.withDayOfMonth(monthDate.lengthOfMonth());
      
      // Count bookings in this month
      long bookings = bookingRepository.countByOutletIdInAndBookingDateBetween(
          allOutletIds, monthStart, monthEnd
      );
      
      // Count new users in this month
      // Note: BaseUUIDEntity doesn't have createdAt, using 0 for now
      // TODO: Add createdAt field or use JPA auditing
      long users = 0;
      
      // Calculate revenue for this month
      BigDecimal revenue = bookingRepository
          .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
              allOutletIds,
              monthStart,
              monthEnd,
              List.of(BookingStatus.COMPLETED)
          );
      if (revenue == null) revenue = BigDecimal.ZERO;
      
      stats.add(new AdminStatisticsResponse.MonthlyStat(year, month, monthName, bookings, users, revenue));
    }
    
    return stats;
  }

  private AdminStatisticsResponse.TodayStats calculateTodayStats(List<java.util.UUID> allOutletIds) {
    LocalDate today = LocalDate.now();
    
    // Today bookings
    long bookings = bookingRepository.countByOutletIdInAndBookingDateBetween(
        allOutletIds, today, today
    );
    
    // New users today - Note: BaseUUIDEntity doesn't have createdAt
    // Using total count as approximation (can be improved with createdAt field)
    long newUsers = 0; // TODO: Add createdAt to BaseUUIDEntity
    
    // New outlets today - Note: BaseUUIDEntity doesn't have createdAt
    long newOutlets = 0; // TODO: Add createdAt to BaseUUIDEntity
    
    // Today revenue
    BigDecimal revenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            today,
            today,
            List.of(BookingStatus.COMPLETED)
        );
    if (revenue == null) revenue = BigDecimal.ZERO;
    
    return new AdminStatisticsResponse.TodayStats(bookings, newUsers, newOutlets, revenue);
  }

  private AdminStatisticsResponse.TopItems calculateTopItems() {
    // Top outlets by bookings
    List<AdminStatisticsResponse.TopOutlet> topOutlets = calculateTopOutlets();
    
    // Top categories (simplified - would need category repository)
    List<AdminStatisticsResponse.TopCategory> topCategories = new ArrayList<>();
    
    // Top users by bookings
    List<AdminStatisticsResponse.TopUser> topUsers = calculateTopUsers();
    
    return new AdminStatisticsResponse.TopItems(topOutlets, topCategories, topUsers);
  }

  private List<AdminStatisticsResponse.TopOutlet> calculateTopOutlets() {
    List<Outlet> allOutlets = outletRepository.findAll();
    
    return allOutlets.stream()
        .map(outlet -> {
          List<java.util.UUID> outletIds = List.of(outlet.getId());
          
          // Count bookings
          long bookings = bookingRepository.countByOutletIdInAndBookingDateBetween(
              outletIds, LocalDate.of(2000, 1, 1), LocalDate.now()
          );
          
          // Calculate revenue
          BigDecimal revenue = bookingRepository
              .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
                  outletIds,
                  LocalDate.of(2000, 1, 1),
                  LocalDate.now(),
                  List.of(BookingStatus.COMPLETED)
              );
          if (revenue == null) revenue = BigDecimal.ZERO;
          
          // Get rating and reviews
          List<Review> reviews = reviewRepository.findByOutletIdIn(outletIds);
          double rating = 0.0;
          if (!reviews.isEmpty()) {
            rating = reviews.stream()
                .mapToInt(r -> r.getOverallRating() != null ? r.getOverallRating() : 0)
                .average()
                .orElse(0.0);
          }
          long reviewCount = reviews.size();
          
          return new AdminStatisticsResponse.TopOutlet(
              outlet.getId().toString(),
              outlet.getName(),
              bookings,
              revenue,
              rating,
              reviewCount
          );
        })
        .sorted((a, b) -> Long.compare(b.bookings(), a.bookings()))
        .limit(10)
        .collect(Collectors.toList());
  }

  private List<AdminStatisticsResponse.TopUser> calculateTopUsers() {
    List<UserAccount> allUsers = userAccountRepository.findAll();
    
    return allUsers.stream()
        .map(user -> {
          // Count bookings (would need user bookings relationship)
          long bookings = 0;
          if (user.getBookings() != null) {
            bookings = user.getBookings().size();
          }
          
          // Count reviews
          long reviews = 0;
          if (user.getReviews() != null) {
            reviews = user.getReviews().size();
          }
          
          String name = user.getProfile() != null && user.getProfile().getFullName() != null
              ? user.getProfile().getFullName()
              : user.getUsername();
          
          return new AdminStatisticsResponse.TopUser(
              user.getId().toString(),
              name,
              bookings,
              reviews
          );
        })
        .sorted((a, b) -> Long.compare(b.bookings(), a.bookings()))
        .limit(10)
        .collect(Collectors.toList());
  }

  private AdminStatisticsResponse.GrowthMetrics calculateGrowthMetrics(List<java.util.UUID> allOutletIds) {
    LocalDate today = LocalDate.now();
    LocalDate lastMonth = today.minusMonths(1);
    LocalDate twoMonthsAgo = today.minusMonths(2);
    
    // User growth
    double userGrowthRate = calculateGrowthRate(
        countUsersInPeriod(twoMonthsAgo, lastMonth),
        countUsersInPeriod(lastMonth, today)
    );
    
    // Outlet growth
    double outletGrowthRate = calculateGrowthRate(
        countOutletsInPeriod(twoMonthsAgo, lastMonth),
        countOutletsInPeriod(lastMonth, today)
    );
    
    // Booking growth
    double bookingGrowthRate = calculateGrowthRate(
        bookingRepository.countByOutletIdInAndBookingDateBetween(
            allOutletIds, twoMonthsAgo, lastMonth.minusDays(1)
        ),
        bookingRepository.countByOutletIdInAndBookingDateBetween(
            allOutletIds, lastMonth, today
        )
    );
    
    // Revenue growth
    BigDecimal previousRevenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            twoMonthsAgo,
            lastMonth.minusDays(1),
            List.of(BookingStatus.COMPLETED)
        );
    if (previousRevenue == null) previousRevenue = BigDecimal.ZERO;
    
    BigDecimal currentRevenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            lastMonth,
            today,
            List.of(BookingStatus.COMPLETED)
        );
    if (currentRevenue == null) currentRevenue = BigDecimal.ZERO;
    
    double revenueGrowthRate = calculateGrowthRate(
        previousRevenue.doubleValue(),
        currentRevenue.doubleValue()
    );
    
    return new AdminStatisticsResponse.GrowthMetrics(
        userGrowthRate,
        outletGrowthRate,
        bookingGrowthRate,
        revenueGrowthRate
    );
  }

  private double calculateGrowthRate(double previous, double current) {
    if (previous == 0) {
      return current > 0 ? 100.0 : 0.0;
    }
    return ((current - previous) / previous) * 100.0;
  }

  private long countUsersInPeriod(LocalDate start, LocalDate end) {
    // Note: BaseUUIDEntity doesn't have createdAt field
    // For now, return 0. In production, add createdAt field or use JPA auditing
    // TODO: Add createdAt to BaseUUIDEntity or enable JPA auditing
    return 0;
  }

  private long countOutletsInPeriod(LocalDate start, LocalDate end) {
    // Note: BaseUUIDEntity doesn't have createdAt field
    // For now, return 0. In production, add createdAt field or use JPA auditing
    // TODO: Add createdAt to BaseUUIDEntity or enable JPA auditing
    return 0;
  }

  private BigDecimal calculateTotalRevenue(List<java.util.UUID> allOutletIds) {
    BigDecimal revenue = bookingRepository
        .sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            allOutletIds,
            LocalDate.of(2000, 1, 1),
            LocalDate.now(),
            List.of(BookingStatus.COMPLETED)
        );
    return revenue != null ? revenue : BigDecimal.ZERO;
  }

  private List<java.util.UUID> getAllOutletIds() {
    return outletRepository.findAll().stream()
        .map(Outlet::getId)
        .collect(Collectors.toList());
  }
}

