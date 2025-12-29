package com.foodgo.backend.module.search.service.impl;

import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletService;
import static com.foodgo.backend.module.outlet.util.DistanceCalculator.*;
import com.foodgo.backend.module.search.dto.request.AdvancedSearchRequest;
import com.foodgo.backend.module.search.dto.response.SearchResultResponse;
import com.foodgo.backend.module.search.service.AdvancedSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation của AdvancedSearchService với relevance scoring và full-text search
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdvancedSearchServiceImpl implements AdvancedSearchService {

  private final OutletRepository outletRepository;
  private final OutletService outletService;
  private final com.foodgo.backend.module.menu.repository.OutletMenuItemRepository outletMenuItemRepository;
  private final com.foodgo.backend.module.booking.repository.BookingRepository bookingRepository;
  private final com.foodgo.backend.module.outlet.repository.OperatingHoursRepository operatingHoursRepository;
  
  @PersistenceContext
  private EntityManager entityManager;

  // Weights cho relevance scoring
  private static final double NAME_WEIGHT = 0.35;
  private static final double DESCRIPTION_WEIGHT = 0.15;
  private static final double ADDRESS_WEIGHT = 0.15;
  private static final double MENU_ITEM_WEIGHT = 0.25; // Tăng weight cho menu items
  private static final double RATING_WEIGHT = 0.05;
  private static final double REVIEWS_WEIGHT = 0.05;

  @Override
  @Transactional(readOnly = true)
  public SearchResultResponse advancedSearch(AdvancedSearchRequest request, Pageable pageable) {
    long startTime = System.currentTimeMillis();
    
    log.debug("Advanced search: query={}, filters={}", request.query(), request);

    // 1. Build base query với filters
    List<Outlet> outlets = findOutletsWithFilters(request);
    
    // 2. Apply text search và calculate relevance scores
    List<SearchResultResponse.ScoredOutletResponse> scoredResults = new ArrayList<>();
    
    if (request.query() != null && !request.query().trim().isEmpty()) {
      String query = request.query().trim().toLowerCase();
      scoredResults = outlets.stream()
          .map(outlet -> scoreOutlet(outlet, query, request))
          .filter(result -> result.relevanceScore() > 0.0) // Chỉ lấy kết quả có score > 0 khi có query
          .sorted((a, b) -> Double.compare(b.relevanceScore(), a.relevanceScore())) // Sort by relevance desc
          .collect(Collectors.toList());
    } else {
      // Không có query, chỉ apply filters và sort (vẫn có rating/reviews boost)
      scoredResults = outlets.stream()
          .map(outlet -> scoreOutlet(outlet, null, request))
          .collect(Collectors.toList());
    }

    // 3. Apply sorting
    scoredResults = applySorting(scoredResults, request);

    // 4. Apply pagination
    int totalElements = scoredResults.size();
    int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), totalElements);
    
    List<SearchResultResponse.ScoredOutletResponse> paginatedResults = 
        start < totalElements ? scoredResults.subList(start, end) : Collections.emptyList();

    // 5. Results đã được convert trong scoreOutlet method
    List<SearchResultResponse.ScoredOutletResponse> finalResults = paginatedResults;

    long searchTime = System.currentTimeMillis() - startTime;

    // 6. Generate suggestions và highlights
    List<String> suggestions = generateSuggestions(request.query());
    Map<String, List<String>> highlights = extractHighlights(finalResults, request.query());

    return new SearchResultResponse(
        finalResults,
        totalElements,
        totalPages,
        pageable.getPageNumber(),
        pageable.getPageSize(),
        request.query(),
        searchTime,
        generateFacets(outlets),
        suggestions,
        generateRelatedSearches(request.query()),
        highlights
    );
  }

  @Override
  @Transactional(readOnly = true)
  public SearchResultResponse quickSearch(String query, Integer limit) {
    if (query == null || query.trim().isEmpty()) {
      return new SearchResultResponse(
          Collections.emptyList(),
          0, 0, 0, limit != null ? limit : 10,
          query, 0L,
          Collections.emptyMap(),
          Collections.emptyList(),
          Collections.emptyList(),
          Collections.emptyMap()
      );
    }

    AdvancedSearchRequest request = new AdvancedSearchRequest(
        query,
        null, null, null, // location (latitude, longitude, radiusKm)
        null, // categoryId
        null, // districtId
        null, // outletTypeId
        null, // featureIds
        null, null, null, // price (priceRange, minPrice, maxPrice)
        null, // minRating
        false, // sortByDistance
        null, // isOpenNow
        null, // hasParking
        null, // hasWifi
        null, // hasDelivery
        null, // hasAvailableCapacity
        null, // menuItemName
        true, false, null, // search options (fuzzySearch, exactMatch, searchFields)
        "relevance", "desc" // sorting (sortBy, sortDirection)
    );

    Pageable pageable = org.springframework.data.domain.PageRequest.of(0, limit != null ? limit : 10);
    return advancedSearch(request, pageable);
  }

  @Override
  public List<String> getPopularSearches(Integer limit) {
    // TODO: Implement với search analytics table
    // Tạm thời return empty list
    return Collections.emptyList();
  }

  @Override
  public void recordSearch(String query, java.util.UUID userId) {
    // TODO: Implement search analytics
    log.debug("Recording search: query={}, userId={}", query, userId);
  }

  // ==================== PRIVATE HELPERS ====================

  /**
   * Tìm outlets với filters
   */
  private List<Outlet> findOutletsWithFilters(AdvancedSearchRequest request) {
    // districtId và outletTypeId đã là Integer trong AdvancedSearchRequest
    Integer districtId = request.districtId();
    Integer outletTypeId = request.outletTypeId();
    List<Integer> featureIds = request.featureIds();

    // Map hasParking, hasWifi, hasDelivery to featureIds
    List<Integer> allFeatureIds = new ArrayList<>(featureIds != null ? featureIds : new ArrayList<>());
    
    if (Boolean.TRUE.equals(request.hasParking())) {
      // Tìm feature "Chỗ Đậu Xe" (id = 2)
      allFeatureIds.add(2);
    }
    if (Boolean.TRUE.equals(request.hasWifi())) {
      // Tìm feature "Wifi Miễn Phí" (id = 1)
      allFeatureIds.add(1);
    }
    if (Boolean.TRUE.equals(request.hasDelivery())) {
      // Tìm feature "Dịch Vụ Giao Hàng" (id = 6)
      allFeatureIds.add(6);
    }
    
    // Remove duplicates
    allFeatureIds = allFeatureIds.stream().distinct().collect(Collectors.toList());
    if (allFeatureIds.isEmpty()) {
      allFeatureIds = null;
    }

    com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest filterRequest =
        new com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest(
            request.query(),
            districtId,
            outletTypeId,
            request.priceRange(),
            allFeatureIds,
            request.latitude(),
            request.longitude(),
            request.radiusKm(),
            null, // distanceMode
            0, // page
            1000 // size - lấy tất cả để score
        );

    // Sử dụng OutletService để tìm với filters
    // Sử dụng OutletSearchSpecification để filter
    com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification spec =
        new com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification(filterRequest);
    
    List<Outlet> outlets = outletRepository.findAll(spec);
    
    // Apply business logic filters
    
    // 1. Filter by categoryId (nếu có)
    if (request.categoryId() != null) {
      outlets = filterByCategoryId(outlets, request.categoryId());
    }
    
    // 2. Filter by minRating (nếu có)
    if (request.minRating() != null && request.minRating() > 0) {
      outlets = filterByMinRating(outlets, request.minRating());
    }
    
    // 3. Filter by menu item name (nếu có)
    if (request.menuItemName() != null && !request.menuItemName().trim().isEmpty()) {
      outlets = filterByMenuItemName(outlets, request.menuItemName().trim());
    }
    
    // 4. Filter by isOpenNow (nếu có)
    if (Boolean.TRUE.equals(request.isOpenNow())) {
      outlets = filterByOpenNow(outlets);
    }
    
    // 5. Filter by available capacity (nếu có)
    if (Boolean.TRUE.equals(request.hasAvailableCapacity())) {
      outlets = filterByAvailableCapacity(outlets);
    }
    
    return outlets;
  }
  
  /**
   * Filter outlets by categoryId
   * Uses native query to check outlet_category_mapping table
   */
  @SuppressWarnings("unchecked")
  private List<Outlet> filterByCategoryId(List<Outlet> outlets, Integer categoryId) {
    if (outlets.isEmpty() || categoryId == null) return outlets;
    
    List<UUID> outletIds = outlets.stream().map(Outlet::getId).toList();
    if (outletIds.isEmpty()) return outlets;
    
    // Query outlet_category_mapping table using native query
    // SELECT outlet_id FROM outlet_category_mapping WHERE category_id = ? AND outlet_id IN (...)
    String sql = "SELECT outlet_id FROM outlet_category_mapping WHERE category_id = :categoryId AND outlet_id IN :outletIds";
    
    // Use JPA native query to find matching outlet IDs
    List<Object> resultList = entityManager.createNativeQuery(sql)
        .setParameter("categoryId", categoryId)
        .setParameter("outletIds", outletIds)
        .getResultList();
    
    // Convert Object results to UUID
    Set<UUID> matchingIdsSet = resultList.stream()
        .map(obj -> {
          if (obj instanceof UUID) return (UUID) obj;
          if (obj instanceof String) return UUID.fromString((String) obj);
          if (obj instanceof java.util.UUID) return (java.util.UUID) obj;
          return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
    
    return outlets.stream()
        .filter(outlet -> matchingIdsSet.contains(outlet.getId()))
        .collect(Collectors.toList());
  }
  
  /**
   * Filter outlets by minimum rating
   */
  private List<Outlet> filterByMinRating(List<Outlet> outlets, Double minRating) {
    if (outlets.isEmpty() || minRating == null || minRating <= 0) return outlets;
    
    return outlets.stream()
        .filter(outlet -> {
          if (outlet.getAverageRating() == null) return false;
          return outlet.getAverageRating().doubleValue() >= minRating;
        })
        .collect(Collectors.toList());
  }
  
  /**
   * Filter outlets by menu item name
   */
  private List<Outlet> filterByMenuItemName(List<Outlet> outlets, String menuItemName) {
    if (outlets.isEmpty()) return outlets;
    
    List<UUID> outletIds = outlets.stream().map(Outlet::getId).toList();
    
    // Tìm các outlet có menu item khớp với tên
    List<com.foodgo.backend.module.menu.entity.OutletMenuItem> menuItems = 
        outletMenuItemRepository.findAll().stream()
            .filter(item -> item.getOutlet() != null && outletIds.contains(item.getOutlet().getId()))
            .filter(item -> Boolean.TRUE.equals(item.getIsAvailable())) // Chỉ lấy món available
            .filter(item -> {
              String name = item.getName() != null ? item.getName().toLowerCase() : "";
              String description = item.getDescription() != null ? item.getDescription().toLowerCase() : "";
              String searchTerm = menuItemName.toLowerCase();
              return name.contains(searchTerm) || description.contains(searchTerm);
            })
            .toList();
    
    Set<UUID> matchingOutletIds = menuItems.stream()
        .map(item -> item.getOutlet().getId())
        .collect(java.util.stream.Collectors.toSet());
    
    return outlets.stream()
        .filter(outlet -> matchingOutletIds.contains(outlet.getId()))
        .toList();
  }
  
  /**
   * Filter outlets that are open now
   */
  private List<Outlet> filterByOpenNow(List<Outlet> outlets) {
    if (outlets.isEmpty()) return outlets;
    
    java.time.LocalTime now = java.time.LocalTime.now();
    int dayOfWeek = java.time.LocalDate.now().getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
    
    List<UUID> outletIds = outlets.stream().map(Outlet::getId).toList();
    
    // Lấy operating hours cho các outlets
    List<com.foodgo.backend.module.outlet.entity.OperatingHours> operatingHours = 
        operatingHoursRepository.findAll().stream()
            .filter(oh -> outletIds.contains(oh.getOutlet().getId()))
            .filter(oh -> oh.getDayOfWeek().equals(dayOfWeek))
            .filter(oh -> !Boolean.TRUE.equals(oh.getIsClosed()))
            .filter(oh -> {
              java.time.LocalTime openTime = oh.getOpenTime();
              java.time.LocalTime closeTime = oh.getCloseTime();
              // Handle case where close time is next day (e.g., 22:00 - 02:00)
              if (closeTime.isBefore(openTime)) {
                return now.isAfter(openTime) || now.isBefore(closeTime);
              } else {
                return now.isAfter(openTime) && now.isBefore(closeTime);
              }
            })
            .toList();
    
    Set<UUID> openOutletIds = operatingHours.stream()
        .map(oh -> oh.getOutlet().getId())
        .collect(java.util.stream.Collectors.toSet());
    
    return outlets.stream()
        .filter(outlet -> openOutletIds.contains(outlet.getId()))
        .toList();
  }
  
  /**
   * Filter outlets with available capacity
   */
  private List<Outlet> filterByAvailableCapacity(List<Outlet> outlets) {
    if (outlets.isEmpty()) return outlets;
    
    java.time.LocalDate today = java.time.LocalDate.now();
    java.time.LocalDate tomorrow = today.plusDays(1);
    
    List<UUID> outletIds = outlets.stream().map(Outlet::getId).toList();
    
    // Đếm số bookings confirmed/pending cho hôm nay và ngày mai
    List<com.foodgo.backend.module.booking.entity.Booking> activeBookings = 
        bookingRepository.findAll().stream()
            .filter(b -> outletIds.contains(b.getOutlet().getId()))
            .filter(b -> {
              java.time.LocalDate bookingDate = b.getBookingDate();
              return (bookingDate.equals(today) || bookingDate.equals(tomorrow));
            })
            .filter(b -> {
              com.foodgo.backend.common.constant.BookingStatus status = b.getStatus();
              return status == com.foodgo.backend.common.constant.BookingStatus.PENDING 
                  || status == com.foodgo.backend.common.constant.BookingStatus.CONFIRMED;
            })
            .toList();
    
    // Group by outlet và tính tổng số người đã đặt
    Map<UUID, Integer> bookedCapacityByOutlet = activeBookings.stream()
        .collect(java.util.stream.Collectors.groupingBy(
            b -> b.getOutlet().getId(),
            java.util.stream.Collectors.summingInt(b -> b.getNumberOfGuests() != null ? b.getNumberOfGuests() : 0)
        ));
    
    // Filter outlets có capacity còn trống
    return outlets.stream()
        .filter(outlet -> {
          Integer capacity = outlet.getCapacity();
          if (capacity == null || capacity <= 0) {
            return true; // Không có capacity info, giả định còn chỗ
          }
          
          Integer bookedCapacity = bookedCapacityByOutlet.getOrDefault(outlet.getId(), 0);
          return bookedCapacity < capacity; // Còn chỗ nếu booked < capacity
        })
        .toList();
  }

  /**
   * Tính relevance score cho một outlet
   */
  private SearchResultResponse.ScoredOutletResponse scoreOutlet(
      Outlet outlet, String query, AdvancedSearchRequest request) {
    
    double score = 0.0;
    List<String> matchedFields = new ArrayList<>();
    Map<String, List<String>> highlights = new HashMap<>();

    if (query != null && !query.isEmpty()) {
      // 1. Name matching (highest weight)
      double nameScore = calculateFieldScore(outlet.getName(), query);
      if (nameScore > 0) {
        score += nameScore * NAME_WEIGHT;
        matchedFields.add("name");
        highlights.put("name", highlightTerms(outlet.getName(), query));
      }

      // 2. Description matching
      if (outlet.getDescription() != null) {
        double descScore = calculateFieldScore(outlet.getDescription(), query);
        if (descScore > 0) {
          score += descScore * DESCRIPTION_WEIGHT;
          matchedFields.add("description");
          highlights.put("description", highlightTerms(outlet.getDescription(), query));
        }
      }

      // 3. Address matching
      if (outlet.getAddress() != null) {
        double addrScore = calculateFieldScore(outlet.getAddress(), query);
        if (addrScore > 0) {
          score += addrScore * ADDRESS_WEIGHT;
          matchedFields.add("address");
          highlights.put("address", highlightTerms(outlet.getAddress(), query));
        }
      }

      // 4. Menu items matching (business logic: tìm kiếm món ăn)
      double menuScore = calculateMenuItemsScore(outlet, query);
      if (menuScore > 0) {
        score += menuScore * MENU_ITEM_WEIGHT;
        matchedFields.add("menu");
        highlights.put("menu", getMenuItemsHighlights(outlet, query));
      }
    }

    // 4. Rating boost
    if (outlet.getAverageRating() != null) {
      double ratingScore = outlet.getAverageRating().doubleValue() / 5.0; // Normalize to 0-1
      score += ratingScore * RATING_WEIGHT;
    }

    // 5. Reviews count boost
    if (outlet.getTotalReviews() != null && outlet.getTotalReviews() > 0) {
      double reviewsScore = Math.min(1.0, Math.log10(outlet.getTotalReviews() + 1) / 3.0);
      score += reviewsScore * REVIEWS_WEIGHT;
    }

    // 6. Distance calculation (nếu có location)
    Double distanceKm = null;
    String distanceText = null;
    if (request.latitude() != null && request.longitude() != null 
        && outlet.getLatitude() != null && outlet.getLongitude() != null) {
      distanceKm = calculateDistanceKm(
          request.latitude(),
          request.longitude(),
          outlet.getLatitude(),
          outlet.getLongitude()
      );
      distanceText = formatDistance(distanceKm);
    }

    // Convert to OutletResponse
    OutletResponse outletResponse = outletService.getDetail(outlet.getId());

    return new SearchResultResponse.ScoredOutletResponse(
        outletResponse,
        Math.min(1.0, score), // Cap at 1.0
        distanceKm,
        distanceText,
        matchedFields,
        highlights
    );
  }

  /**
   * Tính score cho một field
   */
  private double calculateFieldScore(String fieldValue, String query) {
    if (fieldValue == null || query == null) return 0.0;
    
    String field = fieldValue.toLowerCase();
    String[] queryTerms = query.toLowerCase().split("\\s+");
    
    double score = 0.0;
    for (String term : queryTerms) {
      if (field.contains(term)) {
        // Exact match gets higher score
        if (field.equals(term)) {
          score += 1.0;
        } else if (field.startsWith(term)) {
          score += 0.8;
        } else if (field.contains(term)) {
          score += 0.5;
        }
      }
    }
    
    return Math.min(1.0, score / queryTerms.length);
  }
  
  /**
   * Tính score cho menu items của outlet
   */
  private double calculateMenuItemsScore(Outlet outlet, String query) {
    if (outlet == null || query == null || query.trim().isEmpty()) return 0.0;
    
    try {
      // Lấy menu items của outlet
      List<com.foodgo.backend.module.menu.entity.OutletMenuItem> menuItems = 
          outletMenuItemRepository.findAll().stream()
              .filter(item -> item.getOutlet() != null && item.getOutlet().getId().equals(outlet.getId()))
              .filter(item -> Boolean.TRUE.equals(item.getIsAvailable())) // Chỉ lấy món còn available
              .toList();
      
      if (menuItems.isEmpty()) return 0.0;
      
      String queryLower = query.toLowerCase();
      double totalScore = 0.0;
      int matchCount = 0;
      
      for (com.foodgo.backend.module.menu.entity.OutletMenuItem item : menuItems) {
        double itemScore = 0.0;
        
        // Check name match
        if (item.getName() != null) {
          String name = item.getName().toLowerCase();
          if (name.equals(queryLower)) {
            itemScore += 1.0;
          } else if (name.startsWith(queryLower)) {
            itemScore += 0.8;
          } else if (name.contains(queryLower)) {
            itemScore += 0.6;
          }
        }
        
        // Check description match
        if (item.getDescription() != null) {
          String desc = item.getDescription().toLowerCase();
          if (desc.contains(queryLower)) {
            itemScore += 0.3;
          }
        }
        
        if (itemScore > 0) {
          totalScore += itemScore;
          matchCount++;
        }
      }
      
      // Normalize: càng nhiều món khớp thì score càng cao, nhưng cap ở 1.0
      if (matchCount > 0) {
        double avgScore = totalScore / matchCount;
        double matchBonus = Math.min(0.3, matchCount * 0.05); // Bonus cho nhiều món khớp
        return Math.min(1.0, avgScore + matchBonus);
      }
      
      return 0.0;
    } catch (Exception e) {
      log.warn("Error calculating menu items score for outlet {}: {}", outlet.getId(), e.getMessage());
      return 0.0;
    }
  }
  
  /**
   * Lấy highlights cho menu items
   */
  private List<String> getMenuItemsHighlights(Outlet outlet, String query) {
    if (outlet == null || query == null || query.trim().isEmpty()) {
      return Collections.emptyList();
    }
    
    try {
      String queryLower = query.toLowerCase();
      return outletMenuItemRepository.findAll().stream()
          .filter(item -> item.getOutlet() != null && item.getOutlet().getId().equals(outlet.getId()))
          .filter(item -> Boolean.TRUE.equals(item.getIsAvailable()))
          .filter(item -> {
            String name = item.getName() != null ? item.getName().toLowerCase() : "";
            return name.contains(queryLower);
          })
          .map(com.foodgo.backend.module.menu.entity.OutletMenuItem::getName)
          .filter(Objects::nonNull)
          .limit(3) // Chỉ lấy 3 món đầu tiên
          .toList();
    } catch (Exception e) {
      log.warn("Error getting menu items highlights for outlet {}: {}", outlet.getId(), e.getMessage());
      return Collections.emptyList();
    }
  }

  /**
   * Highlight terms trong text
   */
  private List<String> highlightTerms(String text, String query) {
    if (text == null || query == null) return Collections.emptyList();
    
    String[] terms = query.toLowerCase().split("\\s+");
    List<String> highlights = new ArrayList<>();
    
    String lowerText = text.toLowerCase();
    for (String term : terms) {
      if (lowerText.contains(term)) {
        highlights.add(term);
      }
    }
    
    return highlights;
  }

  /**
   * Apply sorting
   */
  private List<SearchResultResponse.ScoredOutletResponse> applySorting(
      List<SearchResultResponse.ScoredOutletResponse> results, AdvancedSearchRequest request) {
    
    String sortBy = request.sortBy() != null ? request.sortBy() : "relevance";
    String sortDirection = request.sortDirection() != null ? request.sortDirection() : "desc";
    
    Comparator<SearchResultResponse.ScoredOutletResponse> comparator;
    
    switch (sortBy.toLowerCase()) {
      case "rating":
        comparator = Comparator.<SearchResultResponse.ScoredOutletResponse, Double>comparing(r -> 
            r.outlet().averageRating() != null ? r.outlet().averageRating().doubleValue() : 0.0);
        break;
      case "distance":
        comparator = Comparator.<SearchResultResponse.ScoredOutletResponse, Double>comparing(r -> 
            r.distanceKm() != null ? r.distanceKm() : Double.MAX_VALUE);
        break;
      case "reviews":
        comparator = Comparator.<SearchResultResponse.ScoredOutletResponse, Integer>comparing(r -> 
            r.outlet().totalReviews() != null ? r.outlet().totalReviews() : 0);
        break;
      case "price":
        // TODO: Implement price sorting
        comparator = Comparator.comparing(SearchResultResponse.ScoredOutletResponse::relevanceScore);
        break;
      default: // relevance
        comparator = Comparator.comparing(SearchResultResponse.ScoredOutletResponse::relevanceScore);
    }
    
    if ("asc".equalsIgnoreCase(sortDirection)) {
      comparator = comparator.reversed();
    }
    
    return results.stream()
        .sorted(comparator)
        .collect(Collectors.toList());
  }

  /**
   * Generate search suggestions
   */
  private List<String> generateSuggestions(String query) {
    if (query == null || query.trim().isEmpty()) {
      return Collections.emptyList();
    }
    
    // TODO: Implement với search history và popular searches
    return Collections.emptyList();
  }

  /**
   * Generate related searches
   */
  private List<String> generateRelatedSearches(String query) {
    if (query == null || query.trim().isEmpty()) {
      return Collections.emptyList();
    }
    
    // TODO: Implement với search analytics
    return Collections.emptyList();
  }

  /**
   * Extract highlights từ results
   */
  private Map<String, List<String>> extractHighlights(
      List<SearchResultResponse.ScoredOutletResponse> results, String query) {
    Map<String, List<String>> allHighlights = new HashMap<>();
    
    for (SearchResultResponse.ScoredOutletResponse result : results) {
      if (result.highlights() != null) {
        allHighlights.putAll(result.highlights());
      }
    }
    
    return allHighlights;
  }

  /**
   * Generate facets từ results
   */
  private Map<String, Object> generateFacets(List<Outlet> outlets) {
    Map<String, Object> facets = new HashMap<>();
    
    // TODO: Implement facets (categories, price ranges, features, etc.)
    
    return facets;
  }
}

