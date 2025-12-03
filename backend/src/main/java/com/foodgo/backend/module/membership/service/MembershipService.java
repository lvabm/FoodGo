package com.foodgo.backend.module.membership.service;

import com.foodgo.backend.module.membership.dto.MembershipRequest;
import com.foodgo.backend.module.membership.dto.MembershipResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MembershipService {
  MembershipResponse createMembership(MembershipRequest request);

  List<MembershipResponse> getAllMemberships();

  MembershipResponse getMembershipById(Long id);
}
