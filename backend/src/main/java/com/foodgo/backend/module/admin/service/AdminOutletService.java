package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service cho Admin quản lý Outlets
 */
public interface AdminOutletService
    extends ReadableService<UUID, OutletFilterRequest, OutletResponse> {
  
  /**
   * Admin duyệt outlet (approve)
   */
  OutletResponse approveOutlet(UUID id);
  
  /**
   * Admin khóa outlet (lock/disable)
   */
  OutletResponse lockOutlet(UUID id, String reason);
  
  /**
   * Admin mở khóa outlet (unlock/enable)
   */
  OutletResponse unlockOutlet(UUID id);
  
  /**
   * Admin xóa outlet (soft delete)
   */
  OutletResponse deleteOutlet(UUID id, String reason);
  
  /**
   * Admin khôi phục outlet đã xóa
   */
  OutletResponse restoreOutlet(UUID id);
  
  /**
   * Bulk operations: approve nhiều outlets
   */
  List<OutletResponse> bulkApprove(List<UUID> ids);
  
  /**
   * Bulk operations: lock nhiều outlets
   */
  List<OutletResponse> bulkLock(List<UUID> ids, String reason);
  
  /**
   * Bulk operations: unlock nhiều outlets
   */
  List<OutletResponse> bulkUnlock(List<UUID> ids);
}


