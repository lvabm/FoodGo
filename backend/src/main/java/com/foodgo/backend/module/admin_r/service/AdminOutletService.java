package com.foodgo.backend.module.admin_r.service;

import com.foodgo.backend.common.dto.PageResponse;
import com.foodgo.backend.module.admin_r.dto.outlet.OutletAdminDto;
import com.foodgo.backend.module.outlet_d.dto.OutletResponse;
import org.springframework.data.domain.Pageable;

public interface AdminOutletService {
  // Admin: danh sách outlets (paged + filter)
  PageResponse<OutletAdminDto> getOutlets(Object filterDto, Pageable pageable);

  // Admin: duyệt / quyết định outlet (approve / reject)
  OutletResponse approveOutlet(Long id, Object approveOutletRequest);

  // Admin: admin view of outlet detail (if needed)
  OutletAdminDto getOutletAdminDetail(Long id);
}
