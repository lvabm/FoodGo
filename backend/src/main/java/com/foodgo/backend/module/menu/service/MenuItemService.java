package com.foodgo.backend.module.menu.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;

import java.util.UUID;

public interface MenuItemService
    extends ReadableService<UUID, MenuItemFilterRequest, MenuItemResponse> {
  
  /**
   * Lấy ảnh cho menu item từ outlet menu items
   * @param menuItemId ID của master menu item
   * @return URL của ảnh hoặc null nếu không tìm thấy
   */
  String getMenuItemImageUrl(UUID menuItemId);
}
