import apiClient from "./axios";

export const menuApi = {
  // Lấy menu items của outlet
  getOutletMenuItems(outletId, params) {
    return apiClient.get(`/outlets/${outletId}/menu-items`, {params});
  },

  // Lấy chi tiết menu item
  getMenuItemDetail(id) {
    return apiClient.get(`/menu-items/${id}`);
  },

  // Owner/Admin: Tạo menu item (Admin global)
  createMenuItem(data) {
    return apiClient.post("/menu-items", data);
  },

  // Owner (Outlet specific): Tạo menu item cho outlet
  createOutletMenuItem(outletId, data) {
    return apiClient.post(`/outlets/${outletId}/menu-items`, data);
  },

  // Admin: Cập nhật menu item (global)
  updateMenuItem(id, data) {
    return apiClient.patch(`/menu-items/${id}`, data);
  },

  // Owner (Outlet specific): Cập nhật menu item (partial)
  updateOutletMenuItem(outletId, itemId, data) {
    return apiClient.patch(`/outlets/${outletId}/menu-items/${itemId}`, data);
  },

  // Owner: Xóa menu item (Admin global)
  deleteMenuItem(id) {
    return apiClient.delete(`/menu-items/${id}`);
  },

  // Owner (Outlet specific): Xóa menu item của outlet
  deleteOutletMenuItem(outletId, itemId) {
    return apiClient.delete(`/outlets/${outletId}/menu-items/${itemId}`);
  },

  // Search global master menu items
  searchMasterMenuItems(params) {
    return apiClient.get(`/menu-items`, {params});
  },

  // Lấy menu categories
  getMenuCategories() {
    return apiClient.get("/menu-item-categories");
  },

  // Lấy menu types
  getMenuTypes() {
    return apiClient.get("/menu-item-types");
  },

  // Lấy menu sub categories
  getMenuSubCategories() {
    return apiClient.get("/menu-item-sub-categories");
  },

  // Lấy provinces
  getProvinces() {
    return apiClient.get("/provinces");
  },

  // Tìm outlet menu item theo menuItemId để lấy ảnh
  async findOutletMenuItemImage(menuItemId) {
    if (!menuItemId) return null;
    try {
      // Lấy danh sách outlets (chỉ lấy một vài outlets để tối ưu)
      const outletsResponse = await apiClient.get("/outlets/search", {params: {page: 0, size: 5}});
      const outlets = outletsResponse?.data || outletsResponse?.content || [];
      
      // Tìm outlet menu item đầu tiên có menuItemId tương ứng
      for (const outlet of outlets) {
        try {
          const menuItemsResponse = await apiClient.get(`/outlets/${outlet.id}/menu-items`, {
            params: {menuItemId, page: 0, size: 1, isAvailable: true}
          });
          const items = menuItemsResponse?.data || menuItemsResponse?.content || [];
          if (items.length > 0 && items[0]?.imageUrl) {
            return items[0].imageUrl;
          }
        } catch (err) {
          // Continue to next outlet
          continue;
        }
      }
      return null;
    } catch (err) {
      console.error("Error finding outlet menu item image:", err);
      return null;
    }
  },
};
