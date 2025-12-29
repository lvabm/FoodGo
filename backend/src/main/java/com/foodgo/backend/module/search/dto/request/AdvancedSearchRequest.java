package com.foodgo.backend.module.search.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO cho tìm kiếm nâng cao
 */
public record AdvancedSearchRequest(
    // Query string
    String query,
    
    // Location filters
    BigDecimal latitude,
    BigDecimal longitude,
    Double radiusKm, // Bán kính tìm kiếm (km)
    
    // Category & Type filters
    Integer categoryId, // Changed to Integer to match OutletFilterRequest
    Integer districtId, // District ID filter
    Integer outletTypeId, // Changed to Integer to match OutletFilterRequest
    List<Integer> featureIds, // Changed to List<Integer> to match OutletFilterRequest
    
    // Price range
    String priceRange, // "Dưới 100k", "100k - 200k", "200k - 500k", "Trên 500k"
    BigDecimal minPrice,
    BigDecimal maxPrice,
    
    // Rating filter
    @Min(0) @Max(5) Double minRating,
    
    // Distance filter
    Boolean sortByDistance, // Sắp xếp theo khoảng cách
    
    // Other filters
    Boolean isOpenNow, // Chỉ lấy quán đang mở
    Boolean hasParking, // Có chỗ đậu xe
    Boolean hasWifi, // Có WiFi
    Boolean hasDelivery, // Có giao hàng
    Boolean hasAvailableCapacity, // Còn chỗ (dựa trên capacity và bookings)
    String menuItemName, // Tìm kiếm theo tên món ăn
    
    // Search options
    Boolean fuzzySearch, // Tìm kiếm mờ (fuzzy matching)
    Boolean exactMatch, // Tìm kiếm chính xác
    List<String> searchFields, // Fields để tìm kiếm: ["name", "description", "address", "menu"]
    
    // Sorting
    String sortBy, // "relevance", "rating", "distance", "price", "reviews"
    String sortDirection // "asc", "desc"
) {
}

