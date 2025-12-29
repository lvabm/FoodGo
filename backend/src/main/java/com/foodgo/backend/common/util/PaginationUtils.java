package com.foodgo.backend.common.util;

import com.foodgo.backend.common.constant.AppConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility class for pagination operations
 */
public class PaginationUtils {

  private PaginationUtils() {
    // Utility class
  }

  /**
   * Create Pageable with default page size
   */
  public static Pageable createPageable(Integer page, Integer size) {
    return createPageable(page, size, null, null);
  }

  /**
   * Create Pageable with sorting
   */
  public static Pageable createPageable(Integer page, Integer size, String sortBy, String sortDirection) {
    // Validate and set defaults
    int pageNumber = (page != null && page >= 0) ? page : 0;
    int pageSize = (size != null && size > 0) ? size : AppConstants.DEFAULT_PAGE_SIZE;
    
    // Limit max page size
    if (pageSize > AppConstants.MAX_PAGE_SIZE) {
      pageSize = AppConstants.MAX_PAGE_SIZE;
    }

    // Handle sorting
    if (sortBy != null && !sortBy.trim().isEmpty()) {
      Sort.Direction direction = Sort.Direction.ASC;
      if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
        direction = Sort.Direction.DESC;
      }
      Sort sort = Sort.by(direction, sortBy.trim());
      return PageRequest.of(pageNumber, pageSize, sort);
    }

    return PageRequest.of(pageNumber, pageSize);
  }

  /**
   * Create Pageable from sort string (format: "field,direction")
   */
  public static Pageable createPageable(Integer page, Integer size, String sortString) {
    if (sortString == null || sortString.trim().isEmpty()) {
      return createPageable(page, size);
    }

    String[] parts = sortString.split(",");
    String sortBy = parts[0].trim();
    String sortDirection = parts.length > 1 ? parts[1].trim() : "asc";

    return createPageable(page, size, sortBy, sortDirection);
  }

  /**
   * Validate page number
   */
  public static int validatePage(int page) {
    return Math.max(0, page);
  }

  /**
   * Validate page size
   */
  public static int validatePageSize(int size) {
    if (size <= 0) {
      return AppConstants.DEFAULT_PAGE_SIZE;
    }
    return Math.min(size, AppConstants.MAX_PAGE_SIZE);
  }

  /**
   * Calculate total pages
   */
  public static int calculateTotalPages(long totalElements, int pageSize) {
    if (pageSize <= 0) {
      return 0;
    }
    return (int) Math.ceil((double) totalElements / pageSize);
  }

  /**
   * Check if page is valid
   */
  public static boolean isValidPage(int page, int totalPages) {
    return page >= 0 && page < totalPages;
  }
}


