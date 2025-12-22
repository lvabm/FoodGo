import apiClient from "./axios";

export const bookingApi = {
  // Tạo đặt bàn mới
  createBooking(data) {
    return apiClient.post("/bookings", data);
  },

  // Lấy chi tiết đặt bàn
  getBookingDetail(id) {
    return apiClient.get(`/bookings/${id}`);
  },

  // Lấy danh sách đặt bàn của tôi
  getMyBookings(params) {
    return apiClient.get("/bookings/me", {params});
  },

  // Cập nhật trạng thái đặt bàn
  updateBooking(id, data) {
    return apiClient.patch(`/bookings/${id}`, data);
  },

  // Hủy đặt bàn
  cancelBooking(id) {
    return apiClient.delete(`/bookings/${id}`);
  },

  // Owner: Xác nhận đặt bàn
  confirmBooking(id) {
    return apiClient.patch(`/bookings/${id}/confirm`);
  },

  // Owner: Từ chối đặt bàn
  rejectBooking(id, reason) {
    return apiClient.patch(`/bookings/${id}/reject`, {reason});
  },

  // Owner: Hoàn thành đặt bàn
  completeBooking(id) {
    return apiClient.patch(`/bookings/${id}/complete`);
  },
};
