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

  // Lấy ảnh cho menu item từ outlet menu items
  getMenuItemImage(menuItemId) {
    return apiClient.get(`/menu-items/${menuItemId}/image`);
  },
};
