package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.service.OutletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho các tính năng tìm kiếm nâng cao
 */
@Tag(name = "Search", description = "API Tìm kiếm nâng cao")
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

  private final OutletService outletService;

  @PermitAll
  @GetMapping("/suggestions")
  @Operation(
      summary = "Lấy gợi ý tìm kiếm",
      description = "Trả về danh sách gợi ý dựa trên query (outlets, menu items)")
  public List<SearchSuggestionResponse> getSuggestions(
      @RequestParam String q,
      @RequestParam(required = false, defaultValue = "5") Integer limit) {
    return outletService.getSearchSuggestions(q, limit);
  }

  @PermitAll
  @GetMapping("/autocomplete")
  @Operation(
      summary = "Autocomplete search",
      description = "Tìm kiếm nhanh với autocomplete cho outlets và menu items")
  public AutocompleteResponse autocomplete(
      @RequestParam String q,
      @RequestParam(required = false, defaultValue = "5") Integer limit) {
    return outletService.getAutocompleteResults(q, limit);
  }

  // DTOs
  public record SearchSuggestionResponse(
      String text,
      String type, // "outlet", "menu", "category"
      String id,
      String subtitle,
      String icon
  ) {}

  public record AutocompleteResponse(
      List<SearchSuggestionResponse> outlets,
      List<SearchSuggestionResponse> menuItems,
      List<SearchSuggestionResponse> categories
  ) {}
}

