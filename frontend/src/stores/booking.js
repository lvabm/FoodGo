import {defineStore} from "pinia";
import {ref} from "vue";
import {bookingApi} from "@/api";

export const useBookingStore = defineStore("booking", () => {
  // State
  const bookings = ref([]);
  const currentBooking = ref(null);
  const isLoading = ref(false);
  const error = ref(null);
  const pagination = ref({
    page: 0,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });

  // Actions
  async function fetchMyBookings(params = {}) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.getMyBookings(params);
      bookings.value = response.content || response;

      if (response.pageable) {
        pagination.value = {
          page: response.pageable.pageNumber,
          size: response.pageable.pageSize,
          totalElements: response.totalElements,
          totalPages: response.totalPages,
        };
      }

      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải danh sách đặt bàn";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function fetchBookingDetail(id) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.getBookingDetail(id);
      currentBooking.value = response;
      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải chi tiết đặt bàn";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function createBooking(data) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.createBooking(data);
      bookings.value.unshift(response);
      return response;
    } catch (err) {
      error.value = err.message || "Đặt bàn thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function updateBooking(id, data) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.updateBooking(id, data);
      const index = bookings.value.findIndex((b) => b.id === id);
      if (index !== -1) {
        bookings.value[index] = response;
      }
      if (currentBooking.value?.id === id) {
        currentBooking.value = response;
      }
      return response;
    } catch (err) {
      error.value = err.message || "Cập nhật đặt bàn thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function cancelBooking(id) {
    isLoading.value = true;
    error.value = null;
    try {
      await bookingApi.cancelBooking(id);
      const index = bookings.value.findIndex((b) => b.id === id);
      if (index !== -1) {
        bookings.value[index].status = "CANCELLED";
      }
    } catch (err) {
      error.value = err.message || "Hủy đặt bàn thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function confirmBooking(id) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.confirmBooking(id);
      const index = bookings.value.findIndex((b) => b.id === id);
      if (index !== -1) {
        bookings.value[index] = response;
      }
      return response;
    } catch (err) {
      error.value = err.message || "Xác nhận đặt bàn thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function rejectBooking(id, reason) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await bookingApi.rejectBooking(id, reason);
      const index = bookings.value.findIndex((b) => b.id === id);
      if (index !== -1) {
        bookings.value[index] = response;
      }
      return response;
    } catch (err) {
      error.value = err.message || "Từ chối đặt bàn thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  return {
    bookings,
    currentBooking,
    isLoading,
    error,
    pagination,
    fetchMyBookings,
    fetchBookingDetail,
    createBooking,
    updateBooking,
    cancelBooking,
    confirmBooking,
    rejectBooking,
  };
});
