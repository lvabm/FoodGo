<template>
  <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-text-light dark:text-text-dark mb-2">
        Lịch sử đặt bàn
      </h1>
      <p class="text-subtext-light dark:text-subtext-dark">
        Quản lý các đơn đặt bàn của bạn
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex items-center justify-center py-12">
      <div
        class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
      ></div>
    </div>

    <!-- Empty State -->
    <div
      v-else-if="!bookings || bookings.length === 0"
      class="text-center py-12"
    >
      <span
        class="material-symbols-outlined text-6xl text-subtext-light dark:text-subtext-dark mb-4"
        >event_busy</span
      >
      <p class="text-subtext-light dark:text-subtext-dark text-lg mb-6">
        Chưa có lịch sử đặt bàn
      </p>
      <button
        @click="$router.push('/')"
        class="px-6 py-3 bg-primary text-white rounded-lg font-medium hover:bg-opacity-90 transition-colors"
      >
        Khám phá nhà hàng
      </button>
    </div>

    <!-- Bookings List -->
    <div v-else class="space-y-4">
      <div
        v-for="booking in bookings"
        :key="booking.id"
        class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden hover:shadow-lg transition-shadow"
      >
        <div class="p-6">
          <div class="flex items-start justify-between mb-4">
            <div class="flex-1">
              <h3
                class="text-lg font-semibold text-text-light dark:text-text-dark mb-1"
              >
                {{ booking.outlet?.name || booking.outletName || "Nhà hàng" }}
              </h3>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                {{
                  booking.outlet?.address ||
                  booking.outletAddress ||
                  "Địa chỉ không có"
                }}
              </p>
            </div>
            <div>
              <span
                class="px-3 py-1 rounded-full text-xs font-medium"
                :class="getStatusBadgeClass(booking.status)"
              >
                {{ getStatusText(booking.status) }}
              </span>
            </div>
          </div>

          <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-4">
            <div>
              <p class="text-xs text-subtext-light dark:text-subtext-dark mb-1">
                Ngày
              </p>
              <p class="font-medium">{{ formatDate(booking.bookingDate) }}</p>
            </div>
            <div>
              <p class="text-xs text-subtext-light dark:text-subtext-dark mb-1">
                Giờ
              </p>
              <p class="font-medium">{{ booking.bookingTime }}</p>
            </div>
            <div>
              <p class="text-xs text-subtext-light dark:text-subtext-dark mb-1">
                Số khách
              </p>
              <p class="font-medium">{{ booking.numberOfGuests }} người</p>
            </div>
            <div>
              <p class="text-xs text-subtext-light dark:text-subtext-dark mb-1">
                Đặt lúc
              </p>
              <p class="font-medium text-xs">
                {{ formatDateTime(booking.createdAt) }}
              </p>
            </div>
          </div>

          <div
            v-if="booking.userNotes"
            class="mb-4 p-3 bg-background-light dark:bg-background-dark rounded-lg"
          >
            <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
              Ghi chú:
            </p>
            <p class="text-sm">{{ booking.userNotes }}</p>
          </div>

          <div class="flex gap-3">
            <button
              @click="viewDetail(booking.id)"
              class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg text-sm font-medium hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
            >
              Xem chi tiết
            </button>
            <button
              v-if="canCancel(booking.status)"
              @click="openCancelDialog(booking)"
              class="px-4 py-2 bg-red-500 text-white rounded-lg text-sm font-medium hover:bg-red-600 transition-colors"
            >
              Hủy đặt bàn
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <div
      v-if="totalPages > 1"
      class="mt-8 flex items-center justify-center gap-2"
    >
      <button
        @click="changePage(currentPage - 1)"
        :disabled="currentPage === 0"
        class="px-4 py-2 rounded-lg border border-border-light dark:border-border-dark disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
      >
        Trước
      </button>
      <span class="px-4 py-2 text-sm">
        Trang {{ currentPage + 1 }} / {{ totalPages }}
      </span>
      <button
        @click="changePage(currentPage + 1)"
        :disabled="currentPage >= totalPages - 1"
        class="px-4 py-2 rounded-lg border border-border-light dark:border-border-dark disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
      >
        Sau
      </button>
    </div>

    <!-- Cancel Dialog -->
    <div
      v-if="showCancelDialog"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
    >
      <div class="bg-white dark:bg-surface-dark rounded-xl max-w-md w-full p-6">
        <h3 class="text-xl font-bold mb-4">Xác nhận hủy đặt bàn</h3>
        <p class="text-subtext-light dark:text-subtext-dark mb-4">
          Bạn có chắc chắn muốn hủy đặt bàn này?
        </p>
        <div class="mb-4">
          <label class="block text-sm font-medium mb-2"
            >Lý do hủy (Tùy chọn)</label
          >
          <textarea
            v-model="cancelReason"
            rows="3"
            placeholder="Nhập lý do hủy..."
            class="w-full px-4 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark resize-none"
          ></textarea>
        </div>
        <div class="flex gap-3">
          <button
            @click="showCancelDialog = false"
            class="flex-1 px-4 py-2 border border-border-light dark:border-border-dark rounded-lg font-medium hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
          >
            Đóng
          </button>
          <button
            @click="confirmCancel"
            :disabled="isCancelling"
            class="flex-1 px-4 py-2 bg-red-500 text-white rounded-lg font-medium hover:bg-red-600 transition-colors disabled:opacity-50"
          >
            {{ isCancelling ? "Đang hủy..." : "Xác nhận hủy" }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {bookingApi} from "@/api";

const router = useRouter();

// State
const bookings = ref([]);
const isLoading = ref(false);
const currentPage = ref(0);
const totalPages = ref(0);
const totalElements = ref(0);

// Cancel dialog
const showCancelDialog = ref(false);
const selectedBooking = ref(null);
const cancelReason = ref("");
const isCancelling = ref(false);

// Fetch bookings
const fetchBookings = async (page = 0) => {
  isLoading.value = true;
  try {
    console.log("📋 Fetching my bookings, page:", page);
    const response = await bookingApi.getMyBookings({
      page,
      size: 10,
      // backend Booking entity doesn't have 'createdAt', use bookingDate instead
      sort: "bookingDate,desc",
    });

    console.log("✅ Bookings response:", response);

    // Handle PageResponse structure
    if (response.data) {
      bookings.value = response.data;
      currentPage.value = response.pageNumber || 0;
      totalPages.value = response.totalPages || 0;
      totalElements.value = response.totalElements || 0;
    } else {
      bookings.value = [];
    }
  } catch (err) {
    console.error("❌ Error fetching bookings:", err);
    bookings.value = [];
  } finally {
    isLoading.value = false;
  }
};

// Change page
const changePage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    fetchBookings(page);
  }
};

// View detail
const viewDetail = (bookingId) => {
  router.push(`/booking/detail/${bookingId}`);
};

// Can cancel
const canCancel = (status) => {
  return status === "PENDING" || status === "CONFIRMED";
};

// Open cancel dialog
const openCancelDialog = (booking) => {
  selectedBooking.value = booking;
  cancelReason.value = "";
  showCancelDialog.value = true;
};

// Confirm cancel
const confirmCancel = async () => {
  if (!selectedBooking.value) return;

  isCancelling.value = true;
  try {
    console.log("🚫 Cancelling booking:", selectedBooking.value.id);
    await bookingApi.cancelBooking(
      selectedBooking.value.id,
      cancelReason.value
    );
    console.log("✅ Booking cancelled");

    showCancelDialog.value = false;
    selectedBooking.value = null;
    cancelReason.value = "";

    // Refresh list
    await fetchBookings(currentPage.value);
  } catch (err) {
    console.error("❌ Error cancelling booking:", err);
    alert("Hủy đặt bàn thất bại. Vui lòng thử lại.");
  } finally {
    isCancelling.value = false;
  }
};

// Format date
const formatDate = (dateString) => {
  if (!dateString) return "--/--/----";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }).format(date);
};

// Format date time
const formatDateTime = (dateTimeString) => {
  if (!dateTimeString) return "--/--/---- --:--";
  const date = new Date(dateTimeString);
  return new Intl.DateTimeFormat("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }).format(date);
};

// Get status text
const getStatusText = (status) => {
  const statusMap = {
    PENDING: "Chờ xác nhận",
    CONFIRMED: "Đã xác nhận",
    CANCELLED: "Đã hủy",
    REJECTED: "Bị từ chối",
    COMPLETED: "Hoàn thành",
    NO_SHOW: "Không đến",
  };
  return statusMap[status] || status;
};

// Get status badge class
const getStatusBadgeClass = (status) => {
  const classMap = {
    PENDING:
      "bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200",
    CONFIRMED:
      "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200",
    CANCELLED: "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200",
    REJECTED: "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200",
    COMPLETED: "bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200",
    NO_SHOW: "bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200",
  };
  return classMap[status] || "bg-gray-100 text-gray-800";
};

// Lifecycle
onMounted(() => {
  fetchBookings();
});
</script>
