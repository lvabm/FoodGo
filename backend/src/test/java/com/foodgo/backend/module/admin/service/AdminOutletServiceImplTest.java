package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.impl.AdminOutletServiceImpl;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminOutletService Unit Tests")
class AdminOutletServiceImplTest {

    @Mock
    private OutletRepository outletRepository;

    @Mock
    private OutletMapper outletMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AdminOutletServiceImpl adminOutletService;

    private UUID outletId;
    private Outlet outlet;
    private UserAccount owner;

    @BeforeEach
    void setUp() {
        outletId = UUID.randomUUID();

        owner = new UserAccount();
        owner.setId(UUID.randomUUID());
        owner.setEmail("owner@test.com");

        outlet = new Outlet();
        outlet.setId(outletId);
        outlet.setName("Test Outlet");
        outlet.setOwner(owner);
        // Outlet doesn't have status field, only isActive
        outlet.setActive(false);
    }

    @Test
    @DisplayName("Should approve outlet successfully")
    void testApproveOutlet_Success() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(outletRepository.save(any(Outlet.class))).thenReturn(outlet);
            when(outletMapper.toResponse(any(Outlet.class))).thenReturn(createMockOutletResponse());

            // Act
            OutletResponse response = adminOutletService.approveOutlet(outletId);

            // Assert
            assertNotNull(response);
            assertTrue(outlet.isActive());
            verify(outletRepository, times(1)).save(outlet);
            verify(notificationService, times(1)).createNotificationAsync(
                eq(owner.getId()), anyString(), anyString(), anyString(), eq(outletId));
        }
    }

    @Test
    @DisplayName("Should throw exception when approving already active outlet")
    void testApproveOutlet_AlreadyActive() {
        // Arrange
        outlet.setActive(true);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                adminOutletService.approveOutlet(outletId);
            });

            assertTrue(exception.getMessage().contains("đã được duyệt"));
            verify(outletRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when outlet not found")
    void testApproveOutlet_NotFound() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findById(outletId)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                adminOutletService.approveOutlet(outletId);
            });

            assertNotNull(exception);
            verify(outletRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should lock outlet successfully")
    void testLockOutlet_Success() {
        // Arrange
        outlet.setActive(true);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(outletRepository.save(any(Outlet.class))).thenReturn(outlet);
            when(outletMapper.toResponse(any(Outlet.class))).thenReturn(createMockOutletResponse());

            // Act
            OutletResponse response = adminOutletService.lockOutlet(outletId, "Vi phạm quy định");

            // Assert
            assertNotNull(response);
            assertFalse(outlet.isActive());
            assertTrue(outlet.getDescription().contains("Bị khóa bởi Admin"));
            verify(outletRepository, times(1)).save(outlet);
            verify(notificationService, times(1)).createNotificationAsync(
                eq(owner.getId()), anyString(), anyString(), anyString(), eq(outletId));
        }
    }

    @Test
    @DisplayName("Should unlock outlet successfully")
    void testUnlockOutlet_Success() {
        // Arrange
        outlet.setActive(false);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(outletRepository.save(any(Outlet.class))).thenReturn(outlet);
            when(outletMapper.toResponse(any(Outlet.class))).thenReturn(createMockOutletResponse());

            // Act
            OutletResponse response = adminOutletService.unlockOutlet(outletId);

            // Assert
            assertNotNull(response);
            assertTrue(outlet.isActive());
            verify(outletRepository, times(1)).save(outlet);
            verify(notificationService, times(1)).createNotificationAsync(
                eq(owner.getId()), anyString(), anyString(), anyString(), eq(outletId));
        }
    }

    @Test
    @DisplayName("Should bulk approve outlets successfully")
    void testBulkApprove_Success() {
        // Arrange
        UUID outletId2 = UUID.randomUUID();
        Outlet outlet2 = new Outlet();
        outlet2.setId(outletId2);
        outlet2.setName("Test Outlet 2");
        outlet2.setOwner(owner);
        outlet2.setActive(false);

        List<UUID> outletIds = List.of(outletId, outletId2);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(outletRepository.findAllById(outletIds)).thenReturn(List.of(outlet, outlet2));
            when(outletRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
            when(outletMapper.toResponse(any(Outlet.class))).thenAnswer(invocation -> {
                Outlet o = invocation.getArgument(0);
                return new OutletResponse(
                    o.getId(),
                    o.getName(),
                    null, // description
                    null, // address
                    null, // email
                    null, // phoneNumber
                    null, // latitude
                    null, // longitude
                    null, // priceRange
                    null, // capacity
                    o.isActive(), // isActive
                    null, // averageRating
                    null, // totalReviews
                    o.getOwner().getId(), // ownerId
                    null, // districtName
                    null, // outletTypeName
                    null // images
                );
            });

            // Act
            List<OutletResponse> responses = adminOutletService.bulkApprove(outletIds);

            // Assert
            assertNotNull(responses);
            assertEquals(2, responses.size());
            verify(outletRepository, times(1)).saveAll(anyList());
        }
    }

    private OutletResponse createMockOutletResponse() {
        return new OutletResponse(
            outletId,
            "Test Outlet",
            null, // description
            null, // address
            null, // email
            null, // phoneNumber
            null, // latitude
            null, // longitude
            null, // priceRange
            null, // capacity
            false, // isActive
            null, // averageRating
            null, // totalReviews
            owner.getId(), // ownerId
            null, // districtName
            null, // outletTypeName
            null // images
        );
    }
}
