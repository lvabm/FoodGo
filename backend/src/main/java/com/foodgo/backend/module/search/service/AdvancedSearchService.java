package com.foodgo.backend.module.search.service;

import com.foodgo.backend.module.search.dto.request.AdvancedSearchRequest;
import com.foodgo.backend.module.search.dto.response.SearchResultResponse;
import org.springframework.data.domain.Pageable;

/**
 * Service cho tìm kiếm nâng cao với full-text search, relevance scoring và filters
 */
public interface AdvancedSearchService {
  
  /**
   * Tìm kiếm nâng cao với relevance scoring
   */
  SearchResultResponse advancedSearch(AdvancedSearchRequest request, Pageable pageable);
  
  /**
   * Tìm kiếm nhanh với autocomplete và suggestions
   */
  SearchResultResponse quickSearch(String query, Integer limit);
  
  /**
   * Lấy popular searches
   */
  java.util.List<String> getPopularSearches(Integer limit);
  
  /**
   * Lưu search query để analytics
   */
  void recordSearch(String query, java.util.UUID userId);
}

