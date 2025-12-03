package com.foodgo.backend.module.membership.service;

import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.membership.mapper.MembershipPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foodgo.backend.module.membership.dto.MembershipRequest;
import com.foodgo.backend.module.membership.dto.MembershipResponse;
import com.foodgo.backend.module.membership.entity.MembershipPlan;
import com.foodgo.backend.module.membership.repository.MembershipPlanRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MembershipServiceImpl implements MembershipService {
  private final String membershipPlanEntityName = EntityName.MEMBERSHIP_PLAN.getFriendlyName();

  private final MembershipPlanRepository membershipPlanRepository;
  private final MembershipPlanMapper membershipPlanMapper;

  @Override
  public MembershipResponse createMembership(MembershipRequest request) {
    MembershipPlan plan = membershipPlanMapper.toEntity(request);
    MembershipPlan savedPlan = membershipPlanRepository.save(plan);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS,
            membershipPlanEntityName,
            plan.getId().toString()));

    return membershipPlanMapper.toResponse(savedPlan);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MembershipResponse> getAllMemberships() {

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, membershipPlanEntityName));

    return membershipPlanRepository.findAll().stream()
        .map(membershipPlanMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public MembershipResponse getMembershipById(Long id) {
    MembershipPlan plan =
        membershipPlanRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Membership Plan không tìm thấy với id: " + id));

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.FETCH_DETAIL_SUCCESS,
            membershipPlanEntityName,
            plan.getId().toString()));

    return membershipPlanMapper.toResponse(plan);
  }
}
