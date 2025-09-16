package com.foodgo.backend.module.outlet_d.service;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.outlet_d.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface OutletService {

  // Tạo quán ăn mới (owner)
  OutletResponse createOutlet(CreateOutletRequest request);

  // Cập nhật thông tin quán (owner or admin)
  OutletResponse updateOutlet(Long id, UpdateOutletRequest request);

  // Xem chi tiết quán (public)
  OutletDetailResponse getOutletDetail(Long id);

  // Danh sách quán (public) với filter + sort + geo
  PageResponse<OutletSummaryDto> searchOutlets(Object outletSearchDto, Pageable pageable);

  // Cập nhật giờ hoạt động (owner/admin)
  OutletResponse updateOperatingHours(Long id, OperatingHoursRequest request);

  // Upload images cho quán (owner/admin)
  OutletImageResponse uploadOutletImage(Long id, MultipartFile file);

  // Đặt ảnh đại diện chính (owner/admin)
  OutletImageResponse setPrimaryImage(Long id, Long imageId);

  // Bật / tắt trạng thái hoạt động (owner/admin)
  OutletResponse changeOutletStatus(Long id, Object changeOutletStatusRequest);

  // Danh sách quán của chủ sở hữu
  PageResponse<OutletSummaryDto> getOutletsByOwner(Long ownerId, Pageable pageable);
}
