package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.module.fnb.repository.MenuItemRepository;
import com.foodgo.backend.module.fnb.repository.OutletMenuItemRepository;
import com.foodgo.backend.module.outlet.dto.*;
import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OutletServiceImpl implements OutletService {
  private final OutletRepository outletRepository;
  private final OutletMenuItemRepository outletMenuItemRepository;
  private final MenuItemRepository menuItemRepository;

  @Override
  public OutletResponse createOutlet(CreateOutletRequest request) {
    return null;
  }

  @Override
  public OutletResponse updateOutlet(UUID id, UpdateOutletRequest request) {
    return null;
  }

  @Override
  public OutletDetailResponse getOutletDetail(UUID id) {
    return null;
  }

  @Override
  public PageResponse<OutletSummaryDto> searchOutlets(Object outletSearchDto, Pageable pageable) {
    return null;
  }

  @Override
  public OutletResponse updateOperatingHours(UUID id, OperatingHoursRequest request) {
    return null;
  }

  @Override
  public OutletImageResponse uploadOutletImage(UUID id, MultipartFile file) {
    return null;
  }

  @Override
  public OutletImageResponse setPrimaryImage(UUID id, Long imageId) {
    return null;
  }

  @Override
  public OutletResponse changeOutletStatus(UUID id, Object changeOutletStatusRequest) {
    return null;
  }

  @Override
  public PageResponse<OutletSummaryDto> getOutletsByOwner(UUID ownerId, Pageable pageable) {
    return null;
  }
}
