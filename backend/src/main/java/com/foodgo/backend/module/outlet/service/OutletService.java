package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.outlet.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface OutletService {

  // Tạo quán ăn mới (owner)
  OutletResponse createOutlet(CreateOutletRequest request);

  // Cập nhật thông tin quán (owner or admin)
  OutletResponse updateOutlet(UUID id, UpdateOutletRequest request);

  // Xem chi tiết quán (public)
  OutletDetailResponse getOutletDetail(UUID id);

  // Danh sách quán (public) với filter + sort + geo
  PageResponse<OutletSummaryDto> searchOutlets(Object outletSearchDto, Pageable pageable);

  // Cập nhật giờ hoạt động (owner/admin)
  OutletResponse updateOperatingHours(UUID id, OperatingHoursRequest request);

  // Upload images cho quán (owner/admin)
  OutletImageResponse uploadOutletImage(UUID id, MultipartFile file);

  // Đặt ảnh đại diện chính (owner/admin)
  OutletImageResponse setPrimaryImage(UUID id, Long imageId);

  // Bật / tắt trạng thái hoạt động (owner/admin)
  OutletResponse changeOutletStatus(UUID id, Object changeOutletStatusRequest);

  // Danh sách quán của chủ sở hữu
  PageResponse<OutletSummaryDto> getOutletsByOwner(UUID ownerId, Pageable pageable);
}
