package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.admin.dto.response.AdminStatisticsResponse;
import com.foodgo.backend.module.admin.service.impl.AdminStatisticsServiceImpl;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminStatisticsService Unit Tests")
class AdminStatisticsServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private OutletRepository outletRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private AdminStatisticsServiceImpl adminStatisticsService;

    @BeforeEach
    void setUp() {
        // Setup default mock returns
        when(userAccountRepository.count()).thenReturn(100L);
        when(outletRepository.count()).thenReturn(50L);
        when(bookingRepository.count()).thenReturn(200L);
        when(reviewRepository.count()).thenReturn(150L);
        
        // getAllOutletIds() is a private method that calls outletRepository.findAll()
        // and extracts IDs, so we mock findAll() instead
        when(outletRepository.findAll()).thenReturn(Collections.emptyList());
        when(bookingRepository.sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            anyList(), any(), any(), anyList())).thenReturn(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should get statistics successfully")
    void testGetStatistics_Success() {
        // Arrange
        // getAllOutletIds() internally calls outletRepository.findAll() and extracts IDs
        // We don't need to mock it directly as it's a private method

        // Act
        AdminStatisticsResponse response = adminStatisticsService.getStatistics();

        // Assert
        assertNotNull(response);
        assertNotNull(response.overview());
        assertNotNull(response.timeStats());
        assertNotNull(response.topItems());
        assertNotNull(response.growth());
        
        assertEquals(100L, response.overview().totalUsers());
        assertEquals(50L, response.overview().totalOutlets());
        assertEquals(200L, response.overview().totalBookings());
        assertEquals(150L, response.overview().totalReviews());
        
        verify(userAccountRepository, atLeastOnce()).count();
        verify(outletRepository, atLeastOnce()).count();
        verify(bookingRepository, atLeastOnce()).count();
        verify(reviewRepository, atLeastOnce()).count();
    }

    @Test
    @DisplayName("Should handle empty data gracefully")
    void testGetStatistics_EmptyData() {
        // Arrange
        when(userAccountRepository.count()).thenReturn(0L);
        when(outletRepository.count()).thenReturn(0L);
        when(bookingRepository.count()).thenReturn(0L);
        when(reviewRepository.count()).thenReturn(0L);
        // getAllOutletIds() is a private method that calls outletRepository.findAll()
        // and extracts IDs, so we mock findAll() instead
        when(outletRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        AdminStatisticsResponse response = adminStatisticsService.getStatistics();

        // Assert
        assertNotNull(response);
        assertEquals(0L, response.overview().totalUsers());
        assertEquals(0L, response.overview().totalOutlets());
        assertEquals(0L, response.overview().totalBookings());
        assertEquals(0L, response.overview().totalReviews());
    }

    @Test
    @DisplayName("Should calculate revenue correctly")
    void testGetStatistics_RevenueCalculation() {
        // Arrange
        // getAllOutletIds() internally calls outletRepository.findAll() and extracts IDs
        // Mock findAll() to return outlets with IDs
        com.foodgo.backend.module.outlet.entity.Outlet outlet = new com.foodgo.backend.module.outlet.entity.Outlet();
        outlet.setId(UUID.randomUUID());
        when(outletRepository.findAll()).thenReturn(List.of(outlet));

        BigDecimal expectedRevenue = new BigDecimal("1000000");
        when(bookingRepository.sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            anyList(), any(), any(), anyList())).thenReturn(expectedRevenue);

        // Act
        AdminStatisticsResponse response = adminStatisticsService.getStatistics();

        // Assert
        assertNotNull(response);
        assertNotNull(response.overview().totalRevenue());
        // Revenue should be calculated from completed bookings
        verify(bookingRepository, atLeastOnce()).sumDepositAmountByOutletIdInAndBookingDateBetweenAndStatusIn(
            anyList(), any(), any(), anyList());
    }
}

