package com.foodgo.backend.module.booking.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.*;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.booking.dto.criteria.BookingSpecification;
import com.foodgo.backend.module.booking.dto.mapper.BookingMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.booking.service.BookingService;
import com.foodgo.backend.module.membership.repository.UserMembershipRepository;
import com.foodgo.backend.module.outlet.entity.OperatingHours;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OperatingHoursRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl
    extends BaseServiceImpl<
        Booking,
        UUID,
        BookingCreateRequest,
        BookingUpdateRequest,
        BookingFilterRequest,
        BookingResponse>
    implements BookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;
  private final UserMembershipRepository userMembershipRepository;
  private final OutletRepository outletRepository;
  private final OperatingHoursRepository operatingHoursRepository;
  private final UserAccountRepository userAccountRepository;
  private final PaymentRepository paymentRepository;
  private final NotificationService notificationService;

  private final String bookingEntityName = EntityName.BOOKING.getFriendlyName();

  @Override
  protected JpaRepository<Booking, UUID> getRepository() {
    return bookingRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Booking> getSpecRepository() {
    return bookingRepository;
  }

  @Override
  protected BaseMapper<Booking, BookingCreateRequest, BookingUpdateRequest, BookingResponse>
      getMapper() {
    return bookingMapper;
  }

  @Override
  protected String getEntityName() {
    return bookingEntityName;
  }

  @Override
  protected void ensurePermission(Booking entity) {
    if (SecurityContext.isAdmin()) return;

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (entity.getUser().getId().equals(currentUserId)) return;
    if (entity.getOutlet().getOwner().getId().equals(currentUserId)) return;

    throw new ForbiddenException("Bạn không có quyền truy cập vào đơn đặt bàn này.");
  }

  @Override
  protected Specification<Booking> buildSpecification(BookingFilterRequest filter) {
    return new BookingSpecification(filter);
  }

  @Override
  protected void validateBeforeCreate(BookingCreateRequest request) {
    if (SecurityContext.isAdmin()) return;

    // Check authentication first - khách vãng lai không thể đặt bàn
    UUID userId;
    try {
      userId = SecurityContext.getCurrentUserId();
    } catch (org.springframework.security.access.AccessDeniedException e) {
      throw new com.foodgo.backend.common.exception.UnauthorizedException(
          "Vui lòng đăng nhập hoặc đăng ký tài khoản để đặt bàn. Chức năng đặt bàn chỉ dành cho thành viên đã đăng ký.");
    }

    // 1. Check Membership: Require an active membership of type USER or OWNER
    boolean hasUserMembership =
        userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
            userId, PlanType.USER);
    boolean hasOwnerMembership =
        userMembershipRepository.existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(
            userId, PlanType.OWNER);

    if (!hasUserMembership && !hasOwnerMembership) {
      throw new ForbiddenException("Chức năng Đặt bàn chỉ dành cho Hội viên. Vui lòng nâng cấp!");
    }

    // 2. Check Outlet exists and is active
    Outlet outlet = outletRepository.findById(request.outletId())
        .orElseThrow(() -> new ResourceNotFoundException("Cửa hàng không tồn tại."));
    
    if (!outlet.isActive()) {
      throw new BadRequestException("Cửa hàng hiện không hoạt động. Vui lòng chọn cửa hàng khác.");
    }

    // 3. Check number of guests: tối thiểu và tối đa
    if (request.numberOfGuests() < AppConstants.MIN_NUMBER_OF_GUESTS) {
      throw new BadRequestException(
          String.format("Số lượng khách tối thiểu là %d người.", AppConstants.MIN_NUMBER_OF_GUESTS));
    }
    
    if (request.numberOfGuests() > AppConstants.MAX_NUMBER_OF_GUESTS) {
      throw new BadRequestException(
          String.format("Số lượng khách tối đa là %d người.", AppConstants.MAX_NUMBER_OF_GUESTS));
    }

    // 4. Check Capacity: số khách không vượt quá capacity của outlet
    if (outlet.getCapacity() != null && request.numberOfGuests() > outlet.getCapacity()) {
      throw new BadRequestException(
          String.format("Số lượng khách (%d) vượt quá sức chứa của cửa hàng (%d người).", 
              request.numberOfGuests(), outlet.getCapacity()));
    }

    // 5. Check booking date: không được là quá khứ và không quá xa trong tương lai
    LocalDate today = LocalDate.now();
    if (request.bookingDate().isBefore(today)) {
      throw new BadRequestException("Không thể đặt bàn trong quá khứ. Vui lòng chọn ngày từ hôm nay trở đi.");
    }
    
    if (request.bookingDate().isAfter(today.plusDays(AppConstants.MAX_BOOKING_DAYS_AHEAD))) {
      throw new BadRequestException(
          String.format("Chỉ có thể đặt bàn tối đa %d ngày trước.", AppConstants.MAX_BOOKING_DAYS_AHEAD));
    }

    // 6. Check booking time: nếu đặt hôm nay, phải đặt trước ít nhất 2 giờ
    if (request.bookingDate().equals(today)) {
      LocalTime now = LocalTime.now();
      LocalTime bookingTime = request.bookingTime();
      
      // Kiểm tra không được đặt trong quá khứ
      if (bookingTime.isBefore(now)) {
        throw new BadRequestException("Không thể đặt bàn trong quá khứ. Vui lòng chọn giờ sau thời điểm hiện tại.");
      }
      
      // Kiểm tra phải đặt trước ít nhất 2 giờ
      if (bookingTime.isBefore(now.plusHours(AppConstants.MIN_BOOKING_HOURS_AHEAD))) {
        throw new BadRequestException(
            String.format("Vui lòng đặt bàn trước ít nhất %d giờ.", AppConstants.MIN_BOOKING_HOURS_AHEAD));
      }
    }

    // 7. Check số booking pending của user: giới hạn số booking đang chờ
    long pendingBookingsCount = bookingRepository.countByUser_IdAndStatus(userId, BookingStatus.PENDING);
    if (pendingBookingsCount >= AppConstants.MAX_PENDING_BOOKINGS_PER_USER) {
      throw new BadRequestException(
          String.format("Bạn đã có %d đơn đặt bàn đang chờ duyệt. Vui lòng đợi các đơn này được xử lý trước khi đặt thêm.", 
              AppConstants.MAX_PENDING_BOOKINGS_PER_USER));
    }

    // 8. Check Operating Hours: thời gian đặt bàn phải trong giờ hoạt động
    DayOfWeek dayOfWeek = request.bookingDate().getDayOfWeek();
    int dayOfWeekValue = dayOfWeek.getValue() - 1; // 0 = Monday, 6 = Sunday
    
    // Optimize: Query by outlet and day instead of loading all
    Optional<OperatingHours> operatingHoursOpt = 
        operatingHoursRepository.findByOutletIdAndDayOfWeek(request.outletId(), dayOfWeekValue);
    
    if (operatingHoursOpt.isEmpty()) {
      throw new BadRequestException("Cửa hàng chưa cấu hình giờ hoạt động cho ngày này.");
    }
    
    OperatingHours operatingHours = operatingHoursOpt.get();
    if (Boolean.TRUE.equals(operatingHours.getIsClosed())) {
      throw new BadRequestException("Cửa hàng đóng cửa vào ngày này.");
    }
    
    LocalTime openTime = operatingHours.getOpenTime();
    LocalTime closeTime = operatingHours.getCloseTime();
    LocalTime bookingTime = request.bookingTime();
    
    if (bookingTime.isBefore(openTime) || bookingTime.isAfter(closeTime)) {
      throw new BadRequestException(
          String.format("Thời gian đặt bàn phải trong khoảng %s - %s.", 
              openTime.toString(), closeTime.toString()));
    }

    // 9. Check capacity thực tế: kiểm tra số chỗ còn lại tại thời điểm đặt bàn
    if (outlet.getCapacity() != null) {
      Integer totalGuestsAtTime = bookingRepository.sumGuestsByOutletIdAndBookingDateAndBookingTimeAndStatusIn(
          request.outletId(),
          request.bookingDate(),
          request.bookingTime(),
          List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
      );
      
      int availableCapacity = outlet.getCapacity() - (totalGuestsAtTime != null ? totalGuestsAtTime : 0);
      
      if (request.numberOfGuests() > availableCapacity) {
        throw new BadRequestException(
            String.format("Không đủ chỗ trống. Hiện tại còn %d chỗ trống tại thời điểm này.", availableCapacity));
      }
    }

    // 10. Check duplicate booking: không cho đặt trùng thời gian (backup check)
    boolean hasConflictingBooking = bookingRepository.existsByOutletIdAndBookingDateAndBookingTimeAndStatusIn(
        request.outletId(),
        request.bookingDate(),
        request.bookingTime(),
        List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
    );
    
    if (hasConflictingBooking) {
      throw new BadRequestException(
          "Thời gian này đã có người đặt. Vui lòng chọn thời gian khác.");
    }

    // 11. Additional business rule: If user has OWNER role, prevent booking at their own outlet
    boolean isOwnerRole = SecurityContext.hasRole(com.foodgo.backend.common.constant.RoleType.ROLE_OWNER.getName());
    if (isOwnerRole && outlet.getOwner() != null && outlet.getOwner().getId().equals(userId)) {
      throw new ForbiddenException("Chủ quán không thể đặt bàn tại chính quán của mình. Vui lòng chọn quán khác.");
    }
  }

  @Override
  @Transactional
  public BookingResponse create(BookingCreateRequest request) {
    validateBeforeCreate(request);

    // 1. Tạo Booking Entity
    Booking entity = bookingMapper.toEntity(request);
    entity.setUser(userAccountRepository.getReferenceById(SecurityContext.getCurrentUserId()));
    entity.setOutlet(outletRepository.getReferenceById(request.outletId()));
    entity.setStatus(BookingStatus.PENDING); // Đặt bàn luôn bắt đầu là Pending chờ duyệt

    // 2. [PAYMENT MANUAL LOGIC] - Tự động tính và tạo Payment
    // Logic: Tiền cọc = Số khách * 20
    BigDecimal depositRate = new BigDecimal("20000"); // Định mức cọc: 20 (đơn vị tiền tệ)
    BigDecimal depositAmount = depositRate.multiply(BigDecimal.valueOf(request.numberOfGuests()));
    entity.setDepositAmount(depositAmount);

    Booking savedBooking = bookingRepository.save(entity);

    // Luôn tạo Payment nếu số tiền > 0 (Khách đặt ít nhất 1 người -> deposit >= 20)
    if (depositAmount.compareTo(BigDecimal.ZERO) > 0) {
      Payment payment =
          Payment.builder()
              .amount(depositAmount)
              .paymentMethod(PaymentMethod.BANK_TRANSFER) // Giả lập chuyển khoản/tiền mặt
              .paymentStatus(PaymentStatus.COMPLETED) // Ghi nhận đã thu tiền
              .transactionId("BOOKING_" + System.currentTimeMillis())
              .type(PaymentType.BOOKING)
              .relatedId(savedBooking.getId().toString()) // Link tới Booking vừa tạo
              .build();

      paymentRepository.save(payment);
    }

    SuccessMessageContext.setMessage("Đặt bàn thành công! Mã đơn: " + savedBooking.getId());
    return bookingMapper.toResponse(savedBooking);
  }

  @Override
  protected void validateBeforeUpdate(UUID id, BookingUpdateRequest request) {
    Booking booking = findByIdOrThrow(id);
    ensurePermission(booking);
    if (booking.getStatus() == BookingStatus.COMPLETED
        || booking.getStatus() == BookingStatus.CANCELLED) {
      throw new BadRequestException("Không thể chỉnh sửa đơn đã hoàn thành hoặc đã hủy.");
    }
  }

  @Override
  @Transactional
  public BookingResponse cancelBooking(UUID bookingId, String reason) {
    Booking booking = findByIdOrThrow(bookingId);
    ensurePermission(booking);

    // Business rule: Không thể hủy đơn đã hoàn tất hoặc đã hủy
    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new BadRequestException("Không thể hủy đơn đã hoàn tất.");
    }
    
    if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.REJECTED) {
      throw new BadRequestException("Đơn này đã bị hủy trước đó.");
    }

    // Business rule: 
    // - PENDING bookings: có thể hủy bất cứ lúc nào
    // - CONFIRMED bookings: phải hủy trước ít nhất 2 giờ so với giờ đặt bàn
    if (booking.getStatus() == BookingStatus.CONFIRMED) {
      LocalDate bookingDate = booking.getBookingDate();
      LocalTime bookingTime = booking.getBookingTime();
      LocalDate today = LocalDate.now();
      
      // Chỉ kiểm tra nếu booking trong hôm nay hoặc tương lai
      if (bookingDate.equals(today) || bookingDate.isAfter(today)) {
        LocalTime now = LocalTime.now();
        java.time.LocalDateTime bookingDateTime = java.time.LocalDateTime.of(bookingDate, bookingTime);
        java.time.LocalDateTime nowDateTime = java.time.LocalDateTime.of(today, now);
        
        long hoursUntilBooking = java.time.Duration.between(nowDateTime, bookingDateTime).toHours();
        
        if (hoursUntilBooking < AppConstants.MIN_CANCELLATION_HOURS_BEFORE) {
          throw new BadRequestException(
              String.format("Bạn chỉ có thể hủy đơn trước ít nhất %d giờ so với giờ đặt bàn.", 
                  AppConstants.MIN_CANCELLATION_HOURS_BEFORE));
        }
      }
      
      // Không cho hủy booking đã qua (quá khứ)
      if (bookingDate.isBefore(today)) {
        throw new BadRequestException("Không thể hủy đơn đặt bàn đã qua.");
      }
    }
    // PENDING bookings có thể hủy bất cứ lúc nào (không cần kiểm tra thời gian)

    booking.setStatus(BookingStatus.CANCELLED);
    String updatedNotes = booking.getUserNotes() != null 
        ? booking.getUserNotes() + " [Lý do hủy: " + reason + "]" 
        : "[Lý do hủy: " + reason + "]";
    booking.setUserNotes(updatedNotes);

    // Refund logic: Hoàn tiền nếu đã thanh toán
    processRefund(booking);

    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho owner
    try {
      if (booking.getOutlet().getOwner() != null) {
        notificationService.createNotification(
            booking.getOutlet().getOwner().getId(),
            "BOOKING_CANCELLED",
            "Đơn đặt bàn đã bị hủy",
            String.format("Đơn đặt bàn #%s đã bị hủy bởi khách hàng. Lý do: %s", 
                booking.getId().toString().substring(0, 8), reason),
            booking.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for cancelled booking {}: {}", bookingId, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã hủy đơn đặt bàn thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse confirmBooking(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);
    
    UUID currentUserId = SecurityContext.getCurrentUserId();
    
    // Business rule: Chỉ owner của quán mới có thể confirm booking
    if (!booking.getOutlet().getOwner().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Chỉ chủ quán mới có thể xác nhận đơn đặt bàn tại quán của mình.");
    }
    
    // Business rule: Owner không thể confirm booking của chính mình
    if (booking.getUser().getId().equals(currentUserId)) {
      throw new ForbiddenException("Bạn không thể xác nhận đơn đặt bàn của chính mình.");
    }

    // Business rule: Chỉ có thể duyệt đơn PENDING
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể duyệt các đơn đang chờ (PENDING).");
    }

    booking.setStatus(BookingStatus.CONFIRMED);
    Booking saved = bookingRepository.save(booking);

    // Gửi thông báo cho User (async để không block transaction)
    notificationService.createNotificationAsync(
        booking.getUser().getId(),
        "BOOKING_CONFIRMED",
        "Đơn đặt bàn đã được xác nhận",
        String.format("Đơn đặt bàn của bạn tại %s đã được xác nhận. Thời gian: %s %s", 
            booking.getOutlet().getName(),
            booking.getBookingDate(),
            booking.getBookingTime()),
        booking.getId()
    );
    
    SuccessMessageContext.setMessage("Duyệt đơn đặt bàn thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse rejectBooking(UUID bookingId, String reason) {
    Booking booking = findByIdOrThrow(bookingId);
    
    UUID currentUserId = SecurityContext.getCurrentUserId();
    
    // Business rule: Chỉ owner của quán mới có thể reject booking
    if (!booking.getOutlet().getOwner().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Chỉ chủ quán mới có thể từ chối đơn đặt bàn tại quán của mình.");
    }
    
    // Business rule: Owner không thể reject booking của chính mình
    if (booking.getUser().getId().equals(currentUserId)) {
      throw new ForbiddenException("Bạn không thể từ chối đơn đặt bàn của chính mình.");
    }
    
    // Business rule: Lý do từ chối không được để trống
    if (reason == null || reason.trim().isEmpty()) {
      throw new BadRequestException("Vui lòng nhập lý do từ chối đơn đặt bàn.");
    }

    // Business rule: Chỉ có thể từ chối đơn PENDING
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể từ chối các đơn đang chờ (PENDING).");
    }

    booking.setStatus(BookingStatus.REJECTED);
    booking.setOwnerNotes(reason);

    // Refund logic: Hoàn tiền khi từ chối đơn
    processRefund(booking);

    Booking saved = bookingRepository.save(booking);
    
    // Gửi thông báo cho User (async để không block transaction)
    notificationService.createNotificationAsync(
        booking.getUser().getId(),
        "BOOKING_REJECTED",
        "Đơn đặt bàn đã bị từ chối",
        String.format("Đơn đặt bàn của bạn tại %s đã bị từ chối. Lý do: %s", 
            booking.getOutlet().getName(), reason),
        booking.getId()
    );
    
    SuccessMessageContext.setMessage("Đã từ chối đơn đặt bàn.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse userCheckIn(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!booking.getUser().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Bạn không có quyền xác nhận tới quán cho đơn này.");
    }

    if (booking.getStatus() != BookingStatus.CONFIRMED) {
      throw new BadRequestException("Chỉ có thể check-in cho đơn đã được xác nhận.");
    }

    if (!booking.getBookingDate().equals(LocalDate.now())) {
      throw new BadRequestException("Chỉ có thể check-in trong ngày đặt bàn.");
    }

    // Idempotent: nếu đã check-in rồi thì trả về success
    if (booking.getUserCheckedInAt() != null) {
      return bookingMapper.toResponse(booking);
    }

    booking.setUserCheckedInAt(Instant.now());

    if (booking.getOwnerCheckedInAt() != null && booking.getStatus() == BookingStatus.CONFIRMED) {
      booking.setStatus(BookingStatus.COMPLETED);
    }

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Bạn đã check-in thành công.");
    return bookingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookingResponse ownerCheckIn(UUID bookingId) {
    Booking booking = findByIdOrThrow(bookingId);

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!booking.getOutlet().getOwner().getId().equals(currentUserId) && !SecurityContext.isAdmin()) {
      throw new ForbiddenException("Bạn không có quyền xác nhận khách tới cho đơn này.");
    }

    if (booking.getStatus() != BookingStatus.CONFIRMED) {
      throw new BadRequestException("Chỉ có thể xác nhận khách tới cho đơn đã được xác nhận.");
    }

    if (!booking.getBookingDate().equals(LocalDate.now())) {
      throw new BadRequestException("Chỉ có thể xác nhận khách trong ngày đặt bàn.");
    }

    // Idempotent
    if (booking.getOwnerCheckedInAt() != null) {
      return bookingMapper.toResponse(booking);
    }

    booking.setOwnerCheckedInAt(Instant.now());

    if (booking.getUserCheckedInAt() != null && booking.getStatus() == BookingStatus.CONFIRMED) {
      booking.setStatus(BookingStatus.COMPLETED);
    }

    Booking saved = bookingRepository.save(booking);
    SuccessMessageContext.setMessage("Đã xác nhận khách đã tới.");
    return bookingMapper.toResponse(saved);
  }

  /**
   * Xử lý hoàn tiền (Refund) khi booking bị hủy hoặc từ chối
   */
  private void processRefund(Booking booking) {
    try {
      // Tìm payment liên quan đến booking này
      Optional<Payment> paymentOpt = paymentRepository.findLatestByRelatedIdAndType(
          booking.getId().toString(),
          PaymentType.BOOKING
      );

      if (paymentOpt.isPresent()) {
        Payment payment = paymentOpt.get();
        
        // Chỉ hoàn tiền nếu payment đã completed và chưa được refund
        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED 
            && payment.getPaymentStatus() != PaymentStatus.REFUNDED) {
          
          payment.setPaymentStatus(PaymentStatus.REFUNDED);
          paymentRepository.save(payment);
          
          log.info("Refunded payment {} for booking {}", payment.getId(), booking.getId());
        }
      }
    } catch (Exception e) {
      // Không fail booking cancellation nếu refund thất bại
      log.error("Failed to process refund for booking {}: {}", booking.getId(), e.getMessage(), e);
    }
  }
}
