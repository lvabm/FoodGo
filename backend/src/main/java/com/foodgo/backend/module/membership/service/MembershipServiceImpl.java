package com.foodgo.backend.module.membership.service;

import com.foodgo.backend.exception.ResourceNotFoundException;
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
  private final MembershipPlanRepository membershipPlanRepository;
  private final MembershipPlanMapper membershipPlanMapper;

  @Override
  public MembershipResponse createMembership(MembershipRequest request) {
    MembershipPlan plan = membershipPlanMapper.toEntity(request);
    MembershipPlan savedPlan = membershipPlanRepository.save(plan);
    return membershipPlanMapper.toResponse(savedPlan);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MembershipResponse> getAllMemberships() {
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
                () -> new ResourceNotFoundException("Membership not found with id: " + id));

    return membershipPlanMapper.toResponse(plan);
  }
}
