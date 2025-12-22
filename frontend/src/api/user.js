import apiClient from "./axios";

export const userApi = {
  // Lấy profile của tôi
  getMyProfile() {
    return apiClient.get("/profile/me");
  },

  // Cập nhật profile của tôi
  updateMyProfile(data) {
    return apiClient.patch("/profile/me", data);
  },

  // Admin: Lấy danh sách users
  getUsers(params) {
    return apiClient.get("/admin/users", {params});
  },

  // Admin: Lấy chi tiết user
  getUserDetail(id) {
    return apiClient.get(`/admin/users/${id}`);
  },

  // Admin: Cập nhật user
  updateUser(id, data) {
    return apiClient.patch(`/admin/users/${id}`, data);
  },

  // Admin: Xóa user
  deleteUser(id) {
    return apiClient.delete(`/admin/users/${id}`);
  },

  // Admin: Khóa/mở khóa user
  toggleUserStatus(id) {
    return apiClient.patch(`/admin/users/${id}/toggle-status`);
  },
};
