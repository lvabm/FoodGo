import apiClient from "./axios";

export const reviewApi = {
  // Lấy danh sách reviews của outlet
  getOutletReviews(outletId, params) {
    return apiClient.get(`/reviews/search`, {params: {outletId, ...params}});
  },

  // Tạo review mới
  createReview(data) {
    return apiClient.post("/reviews", data);
  },

  // Cập nhật review
  updateReview(id, data) {
    return apiClient.patch(`/reviews/${id}`, data);
  },

  // Xóa review
  deleteReview(id) {
    return apiClient.delete(`/reviews/${id}`);
  },

  // Owner: Reply review
  replyReview(id, replyText) {
    return apiClient.post(`/reviews/${id}/reply`, {text: replyText});
  },

  // Lấy reviews của tôi
  getMyReviews(params) {
    return apiClient.get("/reviews/me", {params});
  },
};
