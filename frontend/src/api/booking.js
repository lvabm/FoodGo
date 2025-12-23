import apiClient from "./axios";

export const bookingApi = {
  // Tạo đặt bàn mới
  createBooking(data) {
    console.log("🎯 Creating booking with data:", data);
    return apiClient.post("/bookings", data);
  },

  // Lấy chi tiết đặt bàn
  getBookingDetail(id) {
    return apiClient.get(`/bookings/${id}`);
  },

  // Lấy danh sách đặt bàn của tôi (paginated)
  getMyBookings(params) {
    return apiClient.get("/bookings/me", {params});
  },

  // Cập nhật đặt bàn
  updateBooking(id, data) {
    return apiClient.patch(`/bookings/${id}`, data);
  },

  // Hủy đặt bàn (user) - with reason query param
  cancelBooking(id, reason = "") {
    return apiClient.delete(`/bookings/${id}/cancel`, {
      params: {reason},
    });
  },

  // Owner: Xác nhận đặt bàn (POST method)
  confirmBooking(id) {
    return apiClient.post(`/bookings/${id}/confirm`);
  },

  // Owner: Từ chối đặt bàn (DELETE method with reason)
  rejectBooking(id, reason = "") {
    return apiClient.delete(`/bookings/${id}/reject`, {
      params: {reason},
    });
  },

  // User: Check-in (confirm arrived at outlet)
  userCheckIn(id) {
    return apiClient.post(`/bookings/${id}/user-checkin`);
  },

  // Owner: Confirm customer arrived (check-in)
  ownerCheckIn(id) {
    return apiClient.post(`/bookings/${id}/owner-checkin`);
  },
};
