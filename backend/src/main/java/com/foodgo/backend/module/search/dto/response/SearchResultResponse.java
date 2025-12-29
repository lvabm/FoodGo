package com.foodgo.backend.module.search.dto.response;

import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import java.util.List;
import java.util.Map;

/**
 * Response DTO cho kết quả tìm kiếm nâng cao
 */
public record SearchResultResponse(
    // Results
    List<ScoredOutletResponse> results,
    
    // Pagination
    Integer totalElements,
    Integer totalPages,
    Integer currentPage,
    Integer pageSize,
    
    // Search metadata
    String query,
    Long searchTimeMs, // Thời gian tìm kiếm (milliseconds)
    
    // Facets/Filters available
    Map<String, Object> facets, // Các filter có thể áp dụng
    
    // Suggestions
    List<String> suggestions, // Gợi ý tìm kiếm
    List<String> relatedSearches, // Tìm kiếm liên quan
    
    // Highlighting info
    Map<String, List<String>> highlights // Highlighted terms trong results
) {
  
  /**
   * Outlet response với relevance score
   */
  public record ScoredOutletResponse(
      OutletResponse outlet,
      Double relevanceScore, // Điểm relevance (0.0 - 1.0)
      Double distanceKm, // Khoảng cách (nếu có location)
      String distanceText, // Text hiển thị khoảng cách
      List<String> matchedFields, // Các fields match với query
      Map<String, List<String>> highlights // Highlighted terms trong từng field
  ) {}
}


