package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletImage;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
// Kế thừa BaseMapper cho CRUD
public interface OutletMapper
    extends BaseMapper<Outlet, OutletCreateRequest, OutletUpdateRequest, OutletResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  default OutletResponse toResponse(Outlet entity) {
    if (entity == null) return null;

    // Tính averageRating và totalReviews từ reviews collection
    BigDecimal avgRating = null;
    Integer totalReviews = 0;

    if (entity.getReviews() != null && !entity.getReviews().isEmpty()) {
      var reviews = entity.getReviews();
      totalReviews = reviews.size();
      
      double avg = reviews.stream()
          .mapToInt(r -> r.getOverallRating() != null ? r.getOverallRating() : 0)
          .average()
          .orElse(0.0);
      
      if (avg > 0) {
        avgRating = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
      }
    }

    // Map images from outletImages collection
    List<String> imageUrls = null;
    if (entity.getOutletImages() != null && !entity.getOutletImages().isEmpty()) {
      imageUrls = entity.getOutletImages().stream()
          .filter(img -> {
            // Filter out null, deleted, and images without URL
            if (img == null || img.getImageUrl() == null || img.getImageUrl().isBlank()) {
              return false;
            }
            // Filter out deleted images
            return !Boolean.TRUE.equals(img.getIsDeleted());
          })
          .sorted((a, b) -> {
            // Sort by displayOrder, then by isPrimary
            int orderCompare = Integer.compare(
                a.getDisplayOrder() != null ? a.getDisplayOrder() : 0,
                b.getDisplayOrder() != null ? b.getDisplayOrder() : 0
            );
            if (orderCompare != 0) return orderCompare;
            // Primary images first
            if (Boolean.TRUE.equals(a.getIsPrimary()) && !Boolean.TRUE.equals(b.getIsPrimary())) return -1;
            if (!Boolean.TRUE.equals(a.getIsPrimary()) && Boolean.TRUE.equals(b.getIsPrimary())) return 1;
            return 0;
          })
          .map(OutletImage::getImageUrl)
          .collect(Collectors.toList());
    }

    return new OutletResponse(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getAddress(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        entity.getLatitude(),
        entity.getLongitude(),
        entity.getPriceRange(),
        entity.getCapacity(),
        entity.isActive(),
        avgRating,
        totalReviews,
        entity.getOwner() != null ? entity.getOwner().getId() : null,
        entity.getDistrict() != null ? entity.getDistrict().getName() : null,
        entity.getType() != null ? entity.getType().getName() : null,
        imageUrls != null && !imageUrls.isEmpty() ? imageUrls : List.of()
    );
  }
}
