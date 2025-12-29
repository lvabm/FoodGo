import apiClient from "./axios";

export const outletApi = {
  // Lấy danh sách outlets (có phân trang & filter) - Backend: GET /outlets/search
  getOutlets(params) {
    return apiClient.get("/outlets/search", {params});
  },

  // Lấy danh sách outlets của owner hiện tại - Backend: GET /outlets/my-outlets
  getMyOutlets() {
    return apiClient.get("/outlets/my-outlets");
  },

  // Lấy chi tiết outlet
  getOutletDetail(id) {
    return apiClient.get(`/outlets/${id}`);
  },

  // Tạo mới outlet (Owner)
  createOutlet(data) {
    return apiClient.post("/outlets", data);
  },

  // Cập nhật outlet (Owner)
  updateOutlet(id, data) {
    return apiClient.patch(`/outlets/${id}`, data);
  },

  // Xóa outlet (Owner)
  deleteOutlet(id) {
    return apiClient.delete(`/outlets/${id}`);
  },

  // Tìm kiếm outlets - Backend: GET /outlets/search (with filters)
  searchOutlets(params) {
    return apiClient.get("/outlets/search", {params});
  },

  // Lấy outlets gần đây
  getNearbyOutlets(latitude, longitude, radius) {
    return apiClient.get("/outlets/nearby", {
      params: {latitude, longitude, radius},
    });
  },

  // Outlet Categories
  getCategories() {
    return apiClient.get("/outlet-categories");
  },

  // Outlet Types
  getTypes() {
    return apiClient.get("/outlet-types");
  },

  // Outlet Features
  getFeatures() {
    return apiClient.get("/outlet-features");
  },

  // Get outlet images
  getOutletImages(outletId) {
    return apiClient.get(`/outlets/${outletId}/images`);
  },

  // Operating hours
  getOperatingHours(outletId) {
    return apiClient.get(`/outlets/${outletId}/operating-hours`);
  },

  // Lấy outlets mới nhất
  getNewestOutlets(limit = 20) {
    return apiClient.get("/outlets/newest", {
      params: { limit },
    });
  },

  // Lấy outlets đang khuyến mãi
  getPromotedOutlets(limit = 20) {
    return apiClient.get("/outlets/promotions", {
      params: { limit },
    });
  },

  // Lấy thống kê công khai (tổng số quán, món, đánh giá, người dùng)
  getPublicStatistics() {
    return apiClient.get("/statistics/public");
  },
};
