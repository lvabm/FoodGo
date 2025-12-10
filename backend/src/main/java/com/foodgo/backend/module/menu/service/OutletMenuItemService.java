package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletMenuItemFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletMenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemResponse;

public interface OutletMenuItemService
    extends BaseService<
        OutletMenuItemCreateRequest,
        OutletMenuItemUpdateRequest,
        OutletMenuItemFilterRequest,
        OutletMenuItemResponse,
        Integer> {
  OutletMenuItemResponse toggleAvailability(Integer itemId);
}
