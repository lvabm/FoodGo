package com.foodgo.backend.module.outlet_d.service;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.outlet_d.dto.*;
import com.foodgo.backend.module.outlet_d.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OutletServiceImpl implements OutletService {
  private final OutletRepository outletRepository;

  @Override
  @Transactional
  public OutletResponse createOutlet(CreateOutletRequest request) {
    // TODO: soft-create until verified, optional geocode
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletResponse updateOutlet(Long id, UpdateOutletRequest request) {
    // TODO: validate owner or admin, update fields
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public OutletDetailResponse getOutletDetail(Long id) {
    // TODO: include avg rating, hours
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public PageResponse<OutletSummaryDto> searchOutlets(Object outletSearchDto, Pageable pageable) {
    // TODO: full search with geo filter + sort
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletResponse updateOperatingHours(Long id, OperatingHoursRequest request) {
    // TODO: validate overlapping slots
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletImageResponse uploadOutletImage(Long id, MultipartFile file) {
    // TODO: generate thumbnails, persist image, return response
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletImageResponse setPrimaryImage(Long id, Long imageId) {
    // TODO: ensure ownership and set primary
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public OutletResponse changeOutletStatus(Long id, Object changeOutletStatusRequest) {
    // TODO: soft-enable/disable and visibility to search
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public PageResponse<OutletSummaryDto> getOutletsByOwner(Long ownerId, Pageable pageable) {
    // TODO: return outlets belonging to owner
    throw new UnsupportedOperationException("Not implemented");
  }
}
