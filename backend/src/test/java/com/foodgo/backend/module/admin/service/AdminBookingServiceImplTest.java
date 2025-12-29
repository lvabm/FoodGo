package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.impl.AdminBookingServiceImpl;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.common.constant.PaymentStatus;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.outlet.entity.Outlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminBookingService Unit Tests")
class AdminBookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AdminBookingServiceImpl adminBookingService;

    private UUID bookingId;
    private Booking booking;
    private UserAccount user;
    private Outlet outlet;

    @BeforeEach
    void setUp() {
        bookingId = UUID.randomUUID();

        user = new UserAccount();
        user.setId(UUID.randomUUID());

        outlet = new Outlet();
        outlet.setId(UUID.randomUUID());
        outlet.setOwner(user);

        booking = new Booking();
        booking.setId(bookingId);
        booking.setUser(user);
        booking.setOutlet(outlet);
        booking.setStatus(BookingStatus.PENDING);
        booking.setNumberOfGuests(2);
        booking.setBookingDate(LocalDate.now().plusDays(1));
        booking.setBookingTime(LocalTime.of(18, 0));
        booking.setDepositAmount(new BigDecimal("40000"));
    }

    @Test
    @DisplayName("Should force cancel booking successfully")
    void testForceCancel_Success() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
            when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
            
            Payment payment = new Payment();
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            when(paymentRepository.findLatestByRelatedIdAndType(anyString(), any()))
                .thenReturn(Optional.of(payment));
            when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
            
            when(bookingMapper.toResponse(any(Booking.class))).thenReturn(new BookingResponse(
                bookingId, outlet.getId(), "Test Outlet", "Test Address", user.getId(), "Test User", null,
                LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2,
                BookingStatus.CANCELLED.name(), new BigDecimal("40000"),
                null, null, null, null, false
            ));

            // Act
            BookingResponse response = adminBookingService.forceCancel(bookingId, "Vi phạm quy định");

            // Assert
            assertNotNull(response);
            assertEquals(BookingStatus.CANCELLED, booking.getStatus());
            assertTrue(booking.getOwnerNotes().contains("Quản trị viên"));
            verify(bookingRepository, times(1)).save(booking);
            // Notification được gửi cho cả user và owner
            verify(notificationService, times(2)).createNotificationAsync(
                any(), anyString(), anyString(), anyString(), any());
        }
    }

    @Test
    @DisplayName("Should throw exception when force cancelling already cancelled booking")
    void testForceCancel_AlreadyCancelled() {
        // Arrange
        booking.setStatus(BookingStatus.CANCELLED);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                adminBookingService.forceCancel(bookingId, "Test reason");
            });

            assertTrue(exception.getMessage().contains("đã bị hủy"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when booking not found")
    void testForceCancel_NotFound() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                adminBookingService.forceCancel(bookingId, "Test reason");
            });

            assertNotNull(exception);
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should force confirm booking successfully")
    void testForceConfirm_Success() {
        // Arrange
        booking.setStatus(BookingStatus.PENDING);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
            when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
            when(bookingMapper.toResponse(any(Booking.class))).thenReturn(new BookingResponse(
                bookingId, outlet.getId(), "Test Outlet", "Test Address", user.getId(), "Test User", null,
                LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2,
                BookingStatus.CONFIRMED.name(), new BigDecimal("40000"),
                null, null, null, null, false
            ));

            // Act
            BookingResponse response = adminBookingService.forceConfirm(bookingId);

            // Assert
            assertNotNull(response);
            assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
            verify(bookingRepository, times(1)).save(booking);
            verify(notificationService, times(1)).createNotificationAsync(
                any(), anyString(), anyString(), anyString(), any());
        }
    }

    @Test
    @DisplayName("Should force reject booking successfully")
    void testForceReject_Success() {
        // Arrange
        booking.setStatus(BookingStatus.PENDING);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
            when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
            
            Payment payment = new Payment();
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            when(paymentRepository.findLatestByRelatedIdAndType(anyString(), any()))
                .thenReturn(Optional.of(payment));
            when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
            
            when(bookingMapper.toResponse(any(Booking.class))).thenReturn(new BookingResponse(
                bookingId, outlet.getId(), "Test Outlet", "Test Address", user.getId(), "Test User", null,
                LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2,
                BookingStatus.REJECTED.name(), new BigDecimal("40000"),
                null, null, null, null, false
            ));

            // Act
            BookingResponse response = adminBookingService.forceReject(bookingId, "Không phù hợp");

            // Assert
            assertNotNull(response);
            assertEquals(BookingStatus.REJECTED, booking.getStatus());
            assertTrue(booking.getOwnerNotes().contains("Quản trị viên"));
            verify(bookingRepository, times(1)).save(booking);
            verify(notificationService, times(1)).createNotificationAsync(
                any(), anyString(), anyString(), anyString(), any());
        }
    }

    @Test
    @DisplayName("Should bulk cancel bookings successfully")
    void testBulkCancel_Success() {
        // Arrange
        UUID bookingId2 = UUID.randomUUID();
        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUser(user);
        booking2.setOutlet(outlet);
        booking2.setStatus(BookingStatus.PENDING);

        List<UUID> bookingIds = List.of(bookingId, bookingId2);

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(true);

            when(bookingRepository.findAllById(bookingIds)).thenReturn(List.of(booking, booking2));
            when(bookingRepository.saveAll(anyList())).thenAnswer(invocation -> {
                List<Booking> bookings = invocation.getArgument(0);
                return bookings;
            });
            
            Payment payment = new Payment();
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            when(paymentRepository.findLatestByRelatedIdAndType(anyString(), any()))
                .thenReturn(Optional.of(payment));
            when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
            
            when(bookingMapper.toResponse(any(Booking.class))).thenAnswer(invocation -> {
                Booking b = invocation.getArgument(0);
                return new BookingResponse(
                    b.getId(), outlet.getId(), "Test Outlet", "Test Address", user.getId(), "Test User", null,
                    b.getBookingDate(), b.getBookingTime(), b.getNumberOfGuests(),
                    b.getStatus().name(), b.getDepositAmount(),
                    null, null, null, null, false
                );
            });

            // Act
            List<BookingResponse> responses = adminBookingService.bulkCancel(bookingIds, "Bulk cancel");

            // Assert
            assertNotNull(responses);
            assertEquals(2, responses.size());
            verify(bookingRepository, times(1)).saveAll(anyList());
        }
    }
}

