package com.foodgo.backend.module.membership.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.*;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.exception.DataConflictException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminMembershipService;
import com.foodgo.backend.module.membership.dto.criteria.MembershipPlanSpecification;
import com.foodgo.backend.module.membership.dto.mapper.MembershipPlanMapper;
import com.foodgo.backend.module.membership.dto.mapper.UserMembershipMapper;
import com.foodgo.backend.module.membership.dto.request.create.MembershipPlanCreateRequest;
import com.foodgo.backend.module.membership.dto.request.filter.MembershipPlanFilterRequest;
import com.foodgo.backend.module.membership.dto.request.update.MembershipPlanUpdateRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import com.foodgo.backend.module.membership.dto.response.UserMembershipResponse;
import com.foodgo.backend.module.membership.entity.MembershipPlan;
import com.foodgo.backend.module.membership.entity.UserMembership;
import com.foodgo.backend.module.membership.repository.MembershipPlanRepository;
import com.foodgo.backend.module.membership.repository.UserMembershipRepository;
import com.foodgo.backend.module.membership.service.MembershipService;
import com.foodgo.backend.module.payment.entity.Payment;
import com.foodgo.backend.module.payment.repository.PaymentRepository;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MembershipServiceImpl
    extends BaseServiceImpl<
        MembershipPlan,
        Integer,
        MembershipPlanCreateRequest,
        MembershipPlanUpdateRequest,
        MembershipPlanFilterRequest,
        MembershipPlanResponse>
    implements MembershipService, AdminMembershipService {

  private final String membershipPlanEntityName = EntityName.MEMBERSHIP_PLAN.getFriendlyName();

  // Dependencies
  private final MembershipPlanRepository repository;
  private final MembershipPlanMapper mapper;

  // Dependencies for Subscribe Logic
  private final UserMembershipRepository userMembershipRepository;
  private final UserAccountRepository userAccountRepository;
  private final PaymentRepository paymentRepository;
  private final UserMembershipMapper userMembershipMapper;

  // --- TRIỂN KHAI PHƯƠNG THỨC TRỪU TƯỢNG ---

  @Override
  protected JpaRepository<MembershipPlan, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<MembershipPlan> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<
          MembershipPlan,
          MembershipPlanCreateRequest,
          MembershipPlanUpdateRequest,
          MembershipPlanResponse>
      getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return membershipPlanEntityName;
  }

  @Override
  protected Specification<MembershipPlan> buildSpecification(MembershipPlanFilterRequest filter) {
    return new MembershipPlanSpecification(filter);
  }

  // --- NGHIỆP VỤ RIÊNG: SUBSCRIBE (Đã Fix) ---

  @Override
  public UserMembershipResponse subscribe(Integer planId) {
    // 1. Get Current User ID
    UUID userId = SecurityContext.getCurrentUserId();

    // 2. Check Duplicate Subscription (User chỉ được active 1 gói)
    if (userMembershipRepository.existsByUserAccount_IdAndIsActiveTrue(userId)) {
      throw new DataConflictException(
          "Bạn đang có một gói hội viên đang hoạt động. Vui lòng hủy gói cũ trước khi đăng ký mới.");
    }

    UserAccount user =
        userAccountRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // 3. Get Plan
    MembershipPlan plan =
        repository
            .findById(planId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        membershipPlanEntityName + " không tìm thấy với id: " + planId));

    // 4. [FIX BUG ROLE] Validate User Role matches Plan Type
    validatePlanSuitability(user, plan);

    // 5. Create & Save Membership
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusMonths(plan.getDurationMonths());

    UserMembership subscription =
        UserMembership.builder()
            .userAccount(user)
            .membershipPlan(plan)
            .startDate(startDate)
            .endDate(endDate)
            .isActive(true)
            .build();

    UserMembership savedSubscription = userMembershipRepository.save(subscription);

    // 6. Create Payment
    Payment payment =
        Payment.builder()
            .amount(plan.getPrice()) // Lấy giá từ Plan
            .paymentMethod(PaymentMethod.BANK_TRANSFER)
            .paymentStatus(PaymentStatus.COMPLETED)
            .transactionId("MANUAL_" + System.currentTimeMillis())
            .type(PaymentType.MEMBERSHIP)
            .relatedId(savedSubscription.getId().toString())
            .build();

    paymentRepository.save(payment);

    // 7. Return Response
    return userMembershipMapper.toResponse(savedSubscription);
  }

  @Override
  public void cancelCurrentSubscription() {
    UUID userId = SecurityContext.getCurrentUserId();

    UserMembership activeMembership =
        userMembershipRepository
            .findByUserAccount_IdAndIsActiveTrue(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Bạn không có gói hội viên nào đang hoạt động."));

    activeMembership.setIsActive(false);
    userMembershipRepository.save(activeMembership);
  }

  /**
   * Helper method: Kiểm tra xem User có Role phù hợp với PlanType không. PlanType.OWNER -> Yêu cầu
   * RoleType.OWNER PlanType.USER -> Yêu cầu RoleType.USER
   */
  private void validatePlanSuitability(UserAccount user, MembershipPlan plan) {
    RoleType requiredRoleType;

    // Map PlanType sang RoleType tương ứng
    if (plan.getType() == PlanType.OWNER) {
      requiredRoleType = RoleType.ROLE_OWNER;
    } else {
      requiredRoleType = RoleType.ROLE_USER;
    }

    // Kiểm tra User có sở hữu Role đó không
    boolean hasRequiredRole = user.getRole().getName().equalsIgnoreCase(requiredRoleType.getName());

    if (!hasRequiredRole) {
      throw new ForbiddenException(
          String.format(
              "Bạn không thể đăng ký gói này. Gói '%s' chỉ dành cho tài khoản %s.",
              plan.getName(), requiredRoleType.name()));
    }
  }
}
