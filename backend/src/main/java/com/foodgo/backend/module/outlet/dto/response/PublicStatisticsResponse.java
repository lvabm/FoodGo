package com.foodgo.backend.module.outlet.dto.response;

/**
 * Response DTO cho thống kê công khai (Public Statistics)
 * Hiển thị trên trang chủ cho tất cả người dùng (kể cả khách vãng lai)
 */
public record PublicStatisticsResponse(
    Long totalOutlets,      // Tổng số quán ăn
    Long totalMenuItems,    // Tổng số món ăn
    Long totalReviews,      // Tổng số đánh giá
    Long totalUsers         // Tổng số người dùng (ước tính hoặc chính xác)
) {}

