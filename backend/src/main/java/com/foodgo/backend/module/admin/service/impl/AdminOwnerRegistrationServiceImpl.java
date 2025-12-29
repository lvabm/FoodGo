package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminOwnerRegistrationService;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.owner.dto.response.OwnerRegistrationRequestResponse;
import com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest;
import com.foodgo.backend.module.owner.repository.OwnerRegistrationRequestRepository;
import com.foodgo.backend.module.user.entity.Role;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOwnerRegistrationServiceImpl implements AdminOwnerRegistrationService {

  private final OwnerRegistrationRequestRepository requestRepository;
  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;
  private final NotificationService notificationService;
  private final com.foodgo.backend.module.user.dto.mapper.UserAccountMapper userAccountMapper;
  private final com.foodgo.backend.module.outlet.dto.mapper.OutletMapper outletMapper;

  @Override
  @Transactional(readOnly = true)
  public List<OwnerRegistrationRequestResponse> getAllPendingRequests() {
    List<OwnerRegistrationRequest> requests = requestRepository.findByStatus(
        OwnerRegistrationRequest.RequestStatus.PENDING);
    return requests.stream()
        .map(this::mapToResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public OwnerRegistrationRequestResponse getRequestDetail(UUID id) {
    OwnerRegistrationRequest request = requestRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu đăng ký owner với ID: " + id));
    return mapToResponse(request);
  }

  @Override
  @Transactional
  public OwnerRegistrationRequestResponse approveRequest(UUID id, String adminNotes) {
    OwnerRegistrationRequest request = requestRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu đăng ký owner với ID: " + id));

    if (request.getStatus() != OwnerRegistrationRequest.RequestStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể duyệt các yêu cầu đang chờ (PENDING).");
    }

    // 1. Thăng cấp user lên ROLE_OWNER
    UserAccount user = request.getUser();
    Role ownerRole = roleRepository.findByName(RoleType.ROLE_OWNER.getName())
        .orElseThrow(() -> new ResourceNotFoundException("Role OWNER không tồn tại trong hệ thống."));
    
    user.setRole(ownerRole);
    userAccountRepository.save(user);

    // 2. Kích hoạt outlet (nếu chưa active)
    if (request.getOutlet() != null && !request.getOutlet().isActive()) {
      request.getOutlet().setActive(true);
    }

    // 3. Cập nhật request status
    UUID adminId = SecurityContext.getCurrentUserId();
    UserAccount admin = userAccountRepository.findById(adminId)
        .orElseThrow(() -> new ResourceNotFoundException("Admin không tồn tại."));

    request.setStatus(OwnerRegistrationRequest.RequestStatus.APPROVED);
    request.setAdminNotes(adminNotes);
    request.setReviewedAt(LocalDateTime.now());
    request.setReviewedBy(admin);
    requestRepository.save(request);

    // 4. Gửi thông báo cho user
    try {
      notificationService.createNotificationAsync(
          user.getId(),
          "OWNER_REGISTRATION_APPROVED",
          "Yêu cầu đăng ký owner đã được duyệt",
          "Yêu cầu đăng ký làm chủ quán của bạn đã được Admin duyệt. Bạn có thể bắt đầu quản lý quán của mình.",
          request.getId()
      );
    } catch (Exception e) {
      log.warn("Failed to send notification for approved owner registration {}: {}", id, e.getMessage());
    }

    log.info("Owner registration request {} approved by admin {}", id, adminId);
    return mapToResponse(request);
  }

  @Override
  @Transactional
  public OwnerRegistrationRequestResponse rejectRequest(UUID id, String adminNotes) {
    OwnerRegistrationRequest request = requestRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu đăng ký owner với ID: " + id));

    if (request.getStatus() != OwnerRegistrationRequest.RequestStatus.PENDING) {
      throw new BadRequestException("Chỉ có thể từ chối các yêu cầu đang chờ (PENDING).");
    }

    if (adminNotes == null || adminNotes.trim().isEmpty()) {
      throw new BadRequestException("Lý do từ chối không được để trống.");
    }

    // Cập nhật request status
    UUID adminId = SecurityContext.getCurrentUserId();
    UserAccount admin = userAccountRepository.findById(adminId)
        .orElseThrow(() -> new ResourceNotFoundException("Admin không tồn tại."));

    request.setStatus(OwnerRegistrationRequest.RequestStatus.REJECTED);
    request.setAdminNotes(adminNotes);
    request.setReviewedAt(LocalDateTime.now());
    request.setReviewedBy(admin);
    requestRepository.save(request);

    // Gửi thông báo cho user
    try {
      notificationService.createNotificationAsync(
          request.getUser().getId(),
          "OWNER_REGISTRATION_REJECTED",
          "Yêu cầu đăng ký owner đã bị từ chối",
          String.format("Yêu cầu đăng ký làm chủ quán của bạn đã bị từ chối. Lý do: %s", adminNotes),
          request.getId()
      );
    } catch (Exception e) {
      log.warn("Failed to send notification for rejected owner registration {}: {}", id, e.getMessage());
    }

    log.info("Owner registration request {} rejected by admin {}", id, adminId);
    return mapToResponse(request);
  }

  @Override
  @Transactional(readOnly = true)
  public List<OwnerRegistrationRequestResponse> getAllRequests() {
    List<OwnerRegistrationRequest> requests = requestRepository.findAll();
    return requests.stream()
        .map(this::mapToResponse)
        .toList();
  }

  private OwnerRegistrationRequestResponse mapToResponse(OwnerRegistrationRequest request) {
    return new OwnerRegistrationRequestResponse(
        request.getId(),
        request.getStatus(),
        request.getAdminNotes(),
        request.getReviewedAt(),
        request.getUser() != null ? userAccountMapper.toResponse(request.getUser()) : null,
        request.getOutlet() != null ? outletMapper.toResponse(request.getOutlet()) : null,
        request.getReviewedBy() != null ? userAccountMapper.toResponse(request.getReviewedBy()) : null,
        null // createdAt - can be added if BaseUUIDEntity has it
    );
  }
}

