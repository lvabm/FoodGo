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

  // Owner: Tạo menu item
  createMenuItem(data) {
    return apiClient.post("/menu-items", data);
  },

  // Owner: Cập nhật menu item
  updateMenuItem(id, data) {
    return apiClient.patch(`/menu-items/${id}`, data);
  },

  // Owner: Xóa menu item
  deleteMenuItem(id) {
    return apiClient.delete(`/menu-items/${id}`);
  },

  // Lấy menu categories
  getMenuCategories() {
    return apiClient.get("/menu-item-categories");
  },

  // Lấy menu types
  getMenuTypes() {
    return apiClient.get("/menu-item-types");
  },
};
