package com.foodgo.backend.module.search.controller;

import com.foodgo.backend.module.search.dto.request.AdvancedSearchRequest;
import com.foodgo.backend.module.search.dto.response.SearchResultResponse;
import com.foodgo.backend.module.search.service.AdvancedSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho tìm kiếm nâng cao
 */
@Tag(name = "Advanced Search", description = "API Tìm kiếm nâng cao với relevance scoring")
@RestController
@RequestMapping("/api/v1/search/advanced")
@RequiredArgsConstructor
public class AdvancedSearchController {

  private final AdvancedSearchService advancedSearchService;

  @PermitAll
  @PostMapping
  @Operation(
      summary = "Tìm kiếm nâng cao",
      description = "Tìm kiếm với full-text search, relevance scoring, và filters nâng cao")
  public SearchResultResponse advancedSearch(
      @RequestBody @Valid AdvancedSearchRequest request,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size) {
    
    Pageable pageable = PageRequest.of(page, size);
    return advancedSearchService.advancedSearch(request, pageable);
  }

  @PermitAll
  @GetMapping("/quick")
  @Operation(
      summary = "Tìm kiếm nhanh",
      description = "Tìm kiếm nhanh với autocomplete và suggestions")
  public SearchResultResponse quickSearch(
      @RequestParam String q,
      @RequestParam(defaultValue = "10") Integer limit) {
    return advancedSearchService.quickSearch(q, limit);
  }

  @PermitAll
  @GetMapping("/popular")
  @Operation(
      summary = "Lấy popular searches",
      description = "Lấy danh sách các từ khóa tìm kiếm phổ biến")
  public List<String> getPopularSearches(
      @RequestParam(defaultValue = "10") Integer limit) {
    return advancedSearchService.getPopularSearches(limit);
  }
}


