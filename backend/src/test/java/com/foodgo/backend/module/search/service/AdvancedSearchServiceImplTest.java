package com.foodgo.backend.module.search.service;

import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletService;
import com.foodgo.backend.module.search.dto.request.AdvancedSearchRequest;
import com.foodgo.backend.module.search.dto.response.SearchResultResponse;
import com.foodgo.backend.module.search.service.impl.AdvancedSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdvancedSearchService Unit Tests")
class AdvancedSearchServiceImplTest {

    @Mock
    private OutletRepository outletRepository;

    @Mock
    private OutletService outletService;

    @Mock
    private com.foodgo.backend.module.menu.repository.OutletMenuItemRepository outletMenuItemRepository;

    @Mock
    private com.foodgo.backend.module.booking.repository.BookingRepository bookingRepository;

    @Mock
    private com.foodgo.backend.module.outlet.repository.OperatingHoursRepository operatingHoursRepository;

    @InjectMocks
    private AdvancedSearchServiceImpl advancedSearchService;

    private Outlet outlet1;
    private Outlet outlet2;
    private List<Outlet> outlets;

    @BeforeEach
    void setUp() {
        outlet1 = new Outlet();
        outlet1.setId(UUID.randomUUID());
        outlet1.setName("Nhà hàng Phở Bò");
        outlet1.setDescription("Phở bò ngon nhất Sài Gòn");
        outlet1.setAddress("123 Nguyễn Huệ, Quận 1");
        outlet1.setActive(true);
        outlet1.setAverageRating(new BigDecimal("4.5"));
        outlet1.setTotalReviews(100);

        outlet2 = new Outlet();
        outlet2.setId(UUID.randomUUID());
        outlet2.setName("Quán Cơm Gà");
        outlet2.setDescription("Cơm gà Hội An");
        outlet2.setAddress("456 Lê Lợi, Quận 1");
        outlet2.setActive(true);
        outlet2.setAverageRating(new BigDecimal("4.0"));
        outlet2.setTotalReviews(50);

        outlets = List.of(outlet1, outlet2);
    }

    @Test
    @DisplayName("Should perform advanced search with query successfully")
    void testAdvancedSearch_WithQuery_Success() {
        // Arrange
        AdvancedSearchRequest request = new AdvancedSearchRequest(
            "phở", // query
            null, null, null, // location
            null, null, null, null, // category & type
            null, null, null, // price
            null, // rating
            null, // distance
            null, null, null, null, null, null, // other filters
            null, null, null, // search options
            null, null // sorting
        );
        Pageable pageable = PageRequest.of(0, 10);

        when(outletRepository.findAll(any(Specification.class))).thenReturn(outlets);

        // Act
        SearchResultResponse response = advancedSearchService.advancedSearch(request, pageable);

        // Assert
        assertNotNull(response);
        assertNotNull(response.results());
        assertTrue(response.totalElements() >= 0);
        assertEquals(0, response.currentPage());
        assertEquals(10, response.pageSize());
        verify(outletRepository, atLeastOnce()).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should perform quick search successfully")
    void testQuickSearch_Success() {
        // Arrange
        String query = "phở";

        when(outletRepository.findAll(any(Specification.class))).thenReturn(outlets);

        // Act
        SearchResultResponse response = advancedSearchService.quickSearch(query, 10);

        // Assert
        assertNotNull(response);
        assertNotNull(response.results());
        verify(outletRepository, atLeastOnce()).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return empty results when no outlets match")
    void testAdvancedSearch_NoMatches() {
        // Arrange
        AdvancedSearchRequest request = new AdvancedSearchRequest(
            "xyz123nonexistent", // query
            null, null, null, // location
            null, null, null, null, // category & type
            null, null, null, // price
            null, // rating
            null, // distance
            null, null, null, null, null, null, // other filters
            null, null, null, // search options
            null, null // sorting
        );
        Pageable pageable = PageRequest.of(0, 10);

        when(outletRepository.findAll(any(Specification.class))).thenReturn(List.of());

        // Act
        SearchResultResponse response = advancedSearchService.advancedSearch(request, pageable);

        // Assert
        assertNotNull(response);
        // Results should be empty or filtered out if relevance score is 0
        assertTrue(response.results().isEmpty() || 
                   response.results().stream().allMatch(r -> r.relevanceScore() == 0.0));
    }

    @Test
    @DisplayName("Should handle null query gracefully")
    void testAdvancedSearch_NullQuery() {
        // Arrange
        AdvancedSearchRequest request = new AdvancedSearchRequest(
            null, // null query
            null, null, null, // location
            null, null, null, null, // category & type
            null, null, null, // price
            null, // rating
            null, // distance
            null, null, null, null, null, null, // other filters
            null, null, null, // search options
            null, null // sorting
        );
        Pageable pageable = PageRequest.of(0, 10);

        when(outletRepository.findAll(any(Specification.class))).thenReturn(outlets);

        // Act
        SearchResultResponse response = advancedSearchService.advancedSearch(request, pageable);

        // Assert
        assertNotNull(response);
        assertNotNull(response.results());
    }

    @Test
    @DisplayName("Should get popular searches successfully")
    void testGetPopularSearches_Success() {
        // Act
        List<String> popularSearches = advancedSearchService.getPopularSearches(10);

        // Assert
        assertNotNull(popularSearches);
        // Popular searches should be a list of strings
        assertTrue(popularSearches instanceof List);
    }

    @Test
    @DisplayName("Should record search successfully")
    void testRecordSearch_Success() {
        // Arrange
        String query = "phở bò";

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            advancedSearchService.recordSearch(query, UUID.randomUUID());
        });
    }
}

