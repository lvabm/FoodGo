package com.foodgo.backend.module.admin_r.service.impl;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.outlet.OutletAdminDto;
import com.foodgo.backend.module.admin_r.service.AdminOutletService;
import com.foodgo.backend.module.outlet_d.dto.OutletResponse;
import com.foodgo.backend.module.outlet_d.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminOutletServiceImpl implements AdminOutletService {
  private final OutletRepository outletRepository;

  @Override
  public PageResponse<OutletAdminDto> getOutlets(Object filterDto, Pageable pageable) {
    // TODO: filter by moderation status, owner, etc.
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletResponse approveOutlet(Long id, Object approveOutletRequest) {
    // TODO: set approved/rejected status, record audit reason
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public OutletAdminDto getOutletAdminDetail(Long id) {
    // TODO: return full admin view
    throw new UnsupportedOperationException("Not implemented");
  }
}
