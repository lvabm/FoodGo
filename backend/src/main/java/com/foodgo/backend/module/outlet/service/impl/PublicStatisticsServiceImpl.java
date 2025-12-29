package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.module.outlet.dto.response.PublicStatisticsResponse;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.PublicStatisticsService;
import com.foodgo.backend.module.menu.repository.MenuItemRepository;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicStatisticsServiceImpl implements PublicStatisticsService {

  private final OutletRepository outletRepository;
  private final MenuItemRepository menuItemRepository;
  private final ReviewRepository reviewRepository;
  private final UserAccountRepository userAccountRepository;

  @Override
  @Transactional(readOnly = true)
  public PublicStatisticsResponse getPublicStatistics() {
    try {
      // 1. Tổng số quán ăn (chỉ đếm quán active)
      long totalOutlets = outletRepository.countByIsActiveTrue();
      
      // 2. Tổng số món ăn (master menu items) - sử dụng count() từ JpaRepository
      long totalMenuItems = menuItemRepository.count();
      
      // 3. Tổng số đánh giá - sử dụng count() từ JpaRepository
      long totalReviews = reviewRepository.count();
      
      // 4. Tổng số người dùng - sử dụng count() từ JpaRepository
      long totalUsers = userAccountRepository.count();
      
      log.info("📊 Public Statistics - Outlets: {}, MenuItems: {}, Reviews: {}, Users: {}", 
          totalOutlets, totalMenuItems, totalReviews, totalUsers);
      
      return new PublicStatisticsResponse(
          totalOutlets,
          totalMenuItems,
          totalReviews,
          totalUsers
      );
    } catch (Exception e) {
      log.error("❌ Error getting public statistics:", e);
      // Return zeros on error to prevent frontend crash
      return new PublicStatisticsResponse(0L, 0L, 0L, 0L);
    }
  }
}

