package com.foodgo.backend.module.booking.service;

import com.foodgo.backend.common.constant.*;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.booking.service.impl.BookingServiceImpl;
import com.foodgo.backend.module.membership.repository.UserMembershipRepository;
import com.foodgo.backend.module.outlet.entity.OperatingHours;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OperatingHoursRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookingService Unit Tests")
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private UserMembershipRepository userMembershipRepository;

    @Mock
    private OutletRepository outletRepository;

    @Mock
    private OperatingHoursRepository operatingHoursRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private UUID userId;
    private UUID outletId;
    private UUID bookingId;
    private UserAccount user;
    private Outlet outlet;
    private Booking booking;
    private BookingCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        outletId = UUID.randomUUID();
        bookingId = UUID.randomUUID();

        user = new UserAccount();
        user.setId(userId);

        outlet = new Outlet();
        outlet.setId(outletId);
        outlet.setActive(true);
        outlet.setCapacity(50);
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

        createRequest = new BookingCreateRequest(
            outletId,
            LocalDate.now().plusDays(1),
            LocalTime.of(18, 0),
            2,
            "Test notes"
        );
    }

    @Test
    @DisplayName("Should create booking successfully with valid request")
    void testCreateBooking_Success() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);
            securityContext.when(() -> SecurityContext.hasRole(anyString())).thenReturn(false);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(bookingRepository.countByUser_IdAndStatus(userId, BookingStatus.PENDING)).thenReturn(0L);
            
            OperatingHours operatingHours = new OperatingHours();
            operatingHours.setOpenTime(LocalTime.of(9, 0));
            operatingHours.setCloseTime(LocalTime.of(22, 0));
            operatingHours.setIsClosed(false);
            when(operatingHoursRepository.findByOutletIdAndDayOfWeek(
                outletId, createRequest.bookingDate().getDayOfWeek().getValue() - 1))
                .thenReturn(Optional.of(operatingHours));
            
            when(bookingRepository.sumGuestsByOutletIdAndBookingDateAndBookingTimeAndStatusIn(
                any(), any(), any(), any())).thenReturn(0);
            when(bookingRepository.existsByOutletIdAndBookingDateAndBookingTimeAndStatusIn(
                any(), any(), any(), any())).thenReturn(false);
            
            when(bookingMapper.toEntity(createRequest)).thenReturn(booking);
            when(userAccountRepository.getReferenceById(userId)).thenReturn(user);
            when(outletRepository.getReferenceById(outletId)).thenReturn(outlet);
            when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
            when(paymentRepository.save(any(Payment.class))).thenReturn(new Payment());
            when(bookingMapper.toResponse(booking)).thenReturn(new BookingResponse(
                bookingId, outletId, "Test Outlet", "Test Address", userId, "Test User", null,
                LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2, 
                BookingStatus.PENDING.name(), new BigDecimal("40000"),
                "Test notes", null, null, null, false
            ));

            // Act
            BookingResponse response = bookingService.create(createRequest);

            // Assert
            assertNotNull(response);
            assertEquals(bookingId, response.id());
            verify(bookingRepository, times(1)).save(any(Booking.class));
            verify(paymentRepository, times(1)).save(any(Payment.class));
        }
    }

    @Test
    @DisplayName("Should throw exception when user has no membership")
    void testCreateBooking_NoMembership() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(false);
            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.OWNER)).thenReturn(false);

            // Act & Assert
            ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
                bookingService.create(createRequest);
            });

            assertTrue(exception.getMessage().contains("Hội viên"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when outlet not found")
    void testCreateBooking_OutletNotFound() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                bookingService.create(createRequest);
            });

            assertTrue(exception.getMessage().contains("Cửa hàng không tồn tại"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when outlet is inactive")
    void testCreateBooking_InactiveOutlet() {
        // Arrange
        outlet.setActive(false);
        
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(createRequest);
            });

            assertTrue(exception.getMessage().contains("không hoạt động"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when number of guests is less than minimum")
    void testCreateBooking_TooFewGuests() {
        // Arrange
        BookingCreateRequest invalidRequest = new BookingCreateRequest(
            outletId,
            LocalDate.now().plusDays(1),
            LocalTime.of(18, 0),
            0, // Invalid: less than MIN_NUMBER_OF_GUESTS (1)
            "Test notes"
        );

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(invalidRequest);
            });

            assertTrue(exception.getMessage().contains("tối thiểu"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when number of guests exceeds maximum")
    void testCreateBooking_TooManyGuests() {
        // Arrange
        BookingCreateRequest invalidRequest = new BookingCreateRequest(
            outletId,
            LocalDate.now().plusDays(1),
            LocalTime.of(18, 0),
            51, // Invalid: more than MAX_NUMBER_OF_GUESTS (50)
            "Test notes"
        );

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(invalidRequest);
            });

            assertTrue(exception.getMessage().contains("tối đa"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when booking date is in the past")
    void testCreateBooking_PastDate() {
        // Arrange
        BookingCreateRequest invalidRequest = new BookingCreateRequest(
            outletId,
            LocalDate.now().minusDays(1), // Invalid: past date
            LocalTime.of(18, 0),
            2,
            "Test notes"
        );

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(invalidRequest);
            });

            assertTrue(exception.getMessage().contains("quá khứ"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when user has too many pending bookings")
    void testCreateBooking_TooManyPendingBookings() {
        // Arrange
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(bookingRepository.countByUser_IdAndStatus(userId, BookingStatus.PENDING))
                .thenReturn((long) AppConstants.MAX_PENDING_BOOKINGS_PER_USER);

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(createRequest);
            });

            assertTrue(exception.getMessage().contains("đang chờ duyệt"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should throw exception when capacity is exceeded")
    void testCreateBooking_CapacityExceeded() {
        // Arrange
        outlet.setCapacity(10);
        
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
                userId, PlanType.USER)).thenReturn(true);
            when(outletRepository.findById(outletId)).thenReturn(Optional.of(outlet));
            when(bookingRepository.countByUser_IdAndStatus(userId, BookingStatus.PENDING)).thenReturn(0L);
            
            OperatingHours operatingHours = new OperatingHours();
            operatingHours.setOpenTime(LocalTime.of(9, 0));
            operatingHours.setCloseTime(LocalTime.of(22, 0));
            operatingHours.setIsClosed(false);
            when(operatingHoursRepository.findByOutletIdAndDayOfWeek(
                outletId, createRequest.bookingDate().getDayOfWeek().getValue() - 1))
                .thenReturn(Optional.of(operatingHours));
            
            // Capacity already full: 10 guests already booked
            when(bookingRepository.sumGuestsByOutletIdAndBookingDateAndBookingTimeAndStatusIn(
                any(), any(), any(), any())).thenReturn(10);

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.create(createRequest);
            });

            assertTrue(exception.getMessage().contains("chỗ trống"));
            verify(bookingRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("Should cancel pending booking successfully")
    void testCancelBooking_Pending_Success() {
        // Arrange
        booking.setStatus(BookingStatus.PENDING);
        
        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
            when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
            when(bookingMapper.toResponse(any(Booking.class))).thenReturn(new BookingResponse(
                bookingId, outletId, "Test Outlet", "Test Address", userId, "Test User", null,
                LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2,
                BookingStatus.CANCELLED.name(), new BigDecimal("40000"),
                "Test notes", null, null, null, false
            ));

            // Act
            BookingResponse response = bookingService.cancelBooking(bookingId, "Test cancellation");

            // Assert
            assertNotNull(response);
            assertEquals(BookingStatus.CANCELLED, booking.getStatus());
            verify(bookingRepository, times(1)).save(booking);
        }
    }

    @Test
    @DisplayName("Should throw exception when cancelling confirmed booking too late")
    void testCancelBooking_Confirmed_TooLate() {
        // Arrange
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDate.now());
        booking.setBookingTime(LocalTime.now().plusHours(1)); // Less than 2 hours ahead

        try (MockedStatic<SecurityContext> securityContext = mockStatic(SecurityContext.class)) {
            securityContext.when(SecurityContext::isAdmin).thenReturn(false);
            securityContext.when(SecurityContext::getCurrentUserId).thenReturn(userId);

            when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, () -> {
                bookingService.cancelBooking(bookingId, "Test reason");
            });

            assertTrue(exception.getMessage().contains("ít nhất"));
            verify(bookingRepository, never()).save(any());
        }
    }
}

