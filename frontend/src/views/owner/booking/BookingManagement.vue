<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold text-text-light dark:text-text-dark">
        Quản lý đặt bàn
      </h1>
      <button
        @click="loadBookings"
        class="px-4 py-2 bg-primary text-white rounded-lg hover:opacity-90 transition-opacity flex items-center gap-2"
      >
        <span class="material-symbols-outlined text-lg">refresh</span>
        Làm mới
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center py-20">
      <div
        class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
      ></div>
    </div>

    <!-- Error State -->
    <div
      v-else-if="error"
      class="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-lg p-4 mb-6"
    >
      <p class="text-red-600 dark:text-red-400">{{ error }}</p>
    </div>

    <!-- Content -->
    <div v-else>
      <!-- Tabs -->
      <div
        class="flex gap-4 mb-6 border-b border-border-light dark:border-border-dark"
      >
        <button
          v-for="tab in tabs"
          :key="tab.value"
          :class="[
            'pb-3 px-4 font-medium transition-colors',
            activeTab === tab.value
              ? 'border-b-2 border-primary text-primary'
              : 'text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark',
          ]"
          @click="changeTab(tab.value)"
        >
          {{ tab.label }}
          <span
            v-if="tab.count !== undefined"
            class="ml-2 px-2 py-0.5 text-xs rounded-full bg-primary/10 text-primary"
          >
            {{ tab.count }}
          </span>
        </button>
      </div>

      <!-- Empty State -->
      <div
        v-if="filteredBookings.length === 0"
        class="text-center py-20 text-subtext-light dark:text-subtext-dark"
      >
        <span class="material-symbols-outlined text-6xl mb-4 opacity-50"
          >event_busy</span
        >
        <p class="text-lg">Không có đặt bàn nào</p>
      </div>

      <!-- Booking List -->
      <div v-else class="space-y-4">
        <div
          v-for="booking in filteredBookings"
          :key="booking.id"
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm hover:shadow-md transition-shadow"
        >
          <div class="flex items-start justify-between">
            <div class="flex items-start gap-4 flex-1">
              <div
                class="w-12 h-12 bg-primary/20 rounded-full flex items-center justify-center flex-shrink-0"
              >
                <span class="material-symbols-outlined text-primary text-xl"
                  >person</span
                >
              </div>
              <div class="flex-1">
                <div class="flex items-start justify-between mb-2">
                  <div>
                    <h3
                      class="font-bold text-lg text-text-light dark:text-text-dark"
                    >
                      {{ booking.customerName }}
                    </h3>
                    <p
                      class="text-sm text-subtext-light dark:text-subtext-dark"
                    >
                      {{ booking.customerPhone }}
                    </p>
                  </div>
                  <span
                    :class="getStatusClass(booking.status)"
                    class="px-3 py-1 text-sm font-medium rounded-full"
                  >
                    {{ getStatusText(booking.status) }}
                  </span>
                </div>

                <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mt-3 text-sm">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-lg text-primary"
                      >calendar_today</span
                    >
                    <span class="text-subtext-light dark:text-subtext-dark">
                      {{ formatDate(booking.bookingDate) }}
                    </span>
                  </div>
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-lg text-primary"
                      >schedule</span
                    >
                    <span class="text-subtext-light dark:text-subtext-dark">
                      {{ booking.bookingTime }}
                    </span>
                  </div>
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-lg text-primary"
                      >group</span
                    >
                    <span class="text-subtext-light dark:text-subtext-dark">
                      {{ booking.numberOfGuests }} người
                    </span>
                  </div>
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-lg text-primary"
                      >payments</span
                    >
                    <span class="text-subtext-light dark:text-subtext-dark">
                      {{ formatCurrency(booking.depositAmount) }}
                    </span>
                  </div>
                </div>

                <div
                  v-if="booking.userNotes"
                  class="mt-3 p-3 bg-background-light dark:bg-background-dark rounded-lg"
                >
                  <p
                    class="text-sm font-medium text-text-light dark:text-text-dark mb-1"
                  >
                    Ghi chú từ khách:
                  </p>
                  <p class="text-sm text-subtext-light dark:text-subtext-dark">
                    {{ booking.userNotes }}
                  </p>
                </div>

                <div
                  v-if="booking.ownerNotes"
                  class="mt-2 p-3 bg-primary/5 rounded-lg"
                >
                  <p
                    class="text-sm font-medium text-text-light dark:text-text-dark mb-1"
                  >
                    Ghi chú của bạn:
                  </p>
                  <p class="text-sm text-subtext-light dark:text-subtext-dark">
                    {{ booking.ownerNotes }}
                  </p>
                </div>
              </div>
            </div>

            <div class="flex flex-col gap-2 ml-4">
              <!-- Xác nhận button -->
              <button
                v-if="booking.status === 'PENDING'"
                @click="confirmBooking(booking.id)"
                :disabled="actionLoading[booking.id]"
                class="px-4 py-2 bg-primary text-white rounded-lg hover:opacity-90 transition-opacity disabled:opacity-50 whitespace-nowrap"
              >
                <span v-if="actionLoading[booking.id]">Đang xử lý...</span>
                <span v-else>Xác nhận</span>
              </button>

              <!-- Từ chối button -->
              <button
                v-if="booking.status === 'PENDING'"
                @click="openRejectDialog(booking)"
                class="px-4 py-2 border border-red-500 text-red-500 rounded-lg hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors whitespace-nowrap"
              >
                Từ chối
              </button>

              <!-- Owner check-in button (confirm customer arrived) -->
              <button
                v-if="
                  booking.status === 'CONFIRMED' &&
                  !booking.ownerCheckedInAt &&
                  new Date(booking.bookingDate).toDateString() ===
                    new Date().toDateString()
                "
                @click="ownerCheckIn(booking.id)"
                :disabled="actionLoading[booking.id]"
                class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50 whitespace-nowrap"
              >
                <span v-if="actionLoading[booking.id]">Đang xử lý...</span>
                <span v-else>Khách đã tới</span>
              </button>

              <div
                v-else-if="booking.ownerCheckedInAt"
                class="text-sm text-green-700 font-medium whitespace-nowrap"
              >
                ✓ Đã xác nhận: {{ formatDateTime(booking.ownerCheckedInAt) }}
              </div>

              <!-- Chi tiết button -->
              <button
                @click="viewDetail(booking.id)"
                class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-primary/5 transition-colors text-text-light dark:text-text-dark whitespace-nowrap"
              >
                Chi tiết
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div
        v-if="totalPages > 1"
        class="flex justify-center items-center gap-2 mt-8"
      >
        <button
          @click="changePage(currentPage - 1)"
          :disabled="currentPage === 0"
          class="px-3 py-2 rounded-lg border border-border-light dark:border-border-dark disabled:opacity-50 hover:bg-primary/5 transition-colors"
        >
          <span class="material-symbols-outlined">chevron_left</span>
        </button>

        <button
          v-for="page in visiblePages"
          :key="page"
          @click="changePage(page)"
          :class="[
            'px-4 py-2 rounded-lg border transition-colors',
            currentPage === page
              ? 'bg-primary text-white border-primary'
              : 'border-border-light dark:border-border-dark hover:bg-primary/5',
          ]"
        >
          {{ page + 1 }}
        </button>

        <button
          @click="changePage(currentPage + 1)"
          :disabled="currentPage >= totalPages - 1"
          class="px-3 py-2 rounded-lg border border-border-light dark:border-border-dark disabled:opacity-50 hover:bg-primary/5 transition-colors"
        >
          <span class="material-symbols-outlined">chevron_right</span>
        </button>
      </div>
    </div>

    <!-- Reject Dialog -->
    <div
      v-if="showRejectDialog"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeRejectDialog"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 max-w-md w-full mx-4 border border-border-light dark:border-border-dark"
      >
        <h3 class="text-xl font-bold text-text-light dark:text-text-dark mb-4">
          Từ chối đặt bàn
        </h3>
        <p class="text-subtext-light dark:text-subtext-dark mb-4">
          Vui lòng nhập lý do từ chối đặt bàn này:
        </p>
        <textarea
          v-model="rejectReason"
          class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:outline-none focus:ring-2 focus:ring-primary resize-none"
          rows="4"
          placeholder="Nhập lý do từ chối..."
        ></textarea>
        <div class="flex gap-3 mt-4">
          <button
            @click="closeRejectDialog"
            class="flex-1 px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-primary/5 transition-colors"
          >
            Hủy
          </button>
          <button
            @click="submitReject"
            :disabled="!rejectReason.trim() || rejectLoading"
            class="flex-1 px-4 py-2 bg-red-500 text-white rounded-lg hover:opacity-90 transition-opacity disabled:opacity-50"
          >
            <span v-if="rejectLoading">Đang xử lý...</span>
            <span v-else>Xác nhận từ chối</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {bookingApi} from "@/api";
import {useRouter} from "vue-router";

const router = useRouter();

// State
const bookings = ref([]);
const loading = ref(false);
const error = ref("");
const activeTab = ref("ALL");
const currentPage = ref(0);
const pageSize = ref(10);
const totalElements = ref(0);
const totalPages = ref(0);

// Action loading states
const actionLoading = ref({});

// Reject dialog
const showRejectDialog = ref(false);
const rejectReason = ref("");
const rejectLoading = ref(false);
const selectedBooking = ref(null);

// Tabs configuration and counts
const counts = ref({
  ALL: 0,
  PENDING: 0,
  CONFIRMED: 0,
  COMPLETED: 0,
  CANCELLED: 0,
});
const countsLimited = ref(false); // true when we couldn't fetch full counts due to size
const MAX_COUNT_FETCH = 1000; // safety limit to avoid huge fetches

async function computeCounts(response) {
  countsLimited.value = false;

  // Try to resolve totalElements from multiple possible response shapes
  const totalFromResp =
    response?.totalElements ??
    response?.data?.totalElements ??
    bookings.value.length;
  counts.value.ALL = totalFromResp || bookings.value.length || 0;

  // If total is reasonably small, fetch all items to compute accurate counts
  if (counts.value.ALL > 0 && counts.value.ALL <= MAX_COUNT_FETCH) {
    try {
      const resAll = await bookingApi.getMyBookings({
        page: 0,
        size: counts.value.ALL,
      });
      const items = resAll.data || resAll || [];
      const map = {PENDING: 0, CONFIRMED: 0, COMPLETED: 0, CANCELLED: 0};
      items.forEach((b) => {
        if (b && b.status) {
          map[b.status] = (map[b.status] || 0) + 1;
        }
      });
      counts.value = {...counts.value, ...map};
    } catch (err) {
      console.error("❌ Error fetching all bookings for counts:", err);
      // Fallback to page-level counts
      counts.value.PENDING = bookings.value.filter(
        (b) => b.status === "PENDING"
      ).length;
      counts.value.CONFIRMED = bookings.value.filter(
        (b) => b.status === "CONFIRMED"
      ).length;
      counts.value.COMPLETED = bookings.value.filter(
        (b) => b.status === "COMPLETED"
      ).length;
      counts.value.CANCELLED = bookings.value.filter(
        (b) => b.status === "CANCELLED"
      ).length;
      countsLimited.value = true;
    }
  } else {
    // Total too large or zero - use page counts as fallback
    counts.value.PENDING = bookings.value.filter(
      (b) => b.status === "PENDING"
    ).length;
    counts.value.CONFIRMED = bookings.value.filter(
      (b) => b.status === "CONFIRMED"
    ).length;
    counts.value.COMPLETED = bookings.value.filter(
      (b) => b.status === "COMPLETED"
    ).length;
    counts.value.CANCELLED = bookings.value.filter(
      (b) => b.status === "CANCELLED"
    ).length;
    if (counts.value.ALL > MAX_COUNT_FETCH) countsLimited.value = true;
  }
}

const tabs = computed(() => [
  {
    label: "Tất cả",
    value: "ALL",
    count: counts.value.ALL ?? bookings.value.length,
  },
  {
    label: "Chờ xác nhận",
    value: "PENDING",
    count: counts.value.PENDING ?? 0,
  },
  {
    label: "Đã xác nhận",
    value: "CONFIRMED",
    count: counts.value.CONFIRMED ?? 0,
  },
  {
    label: "Hoàn thành",
    value: "COMPLETED",
    count: counts.value.COMPLETED ?? 0,
  },
  {
    label: "Đã hủy",
    value: "CANCELLED",
    count: counts.value.CANCELLED ?? 0,
  },
]);

// Filtered bookings based on active tab
const filteredBookings = computed(() => {
  if (activeTab.value === "ALL") return bookings.value;
  return bookings.value.filter((b) => b.status === activeTab.value);
});

// Visible pages for pagination
const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5;
  let start = Math.max(0, currentPage.value - Math.floor(maxVisible / 2));
  let end = Math.min(totalPages.value, start + maxVisible);

  if (end - start < maxVisible) {
    start = Math.max(0, end - maxVisible);
  }

  for (let i = start; i < end; i++) {
    pages.push(i);
  }
  return pages;
});

// Load bookings from API
const loadBookings = async () => {
  loading.value = true;
  error.value = "";

  try {
    const params = {
      page: currentPage.value,
      size: pageSize.value,
    };

    // Add status filter if not ALL
    if (activeTab.value !== "ALL") {
      params.status = activeTab.value;
    }

    const response = await bookingApi.getMyBookings(params);
    console.log("📋 Full response:", response);

    // response.data is already the array of bookings
    bookings.value = response.data || [];
    totalElements.value =
      (response.totalElements ??
        response.data?.totalElements ??
        bookings.value.length) ||
      0;
    totalPages.value = (response.totalPages ?? response.data?.totalPages) || 0;

    console.log("📋 Loaded bookings:", bookings.value.length, "items");

    // Recompute stable counts (may fetch all records if safe)
    await computeCounts(response);
  } catch (err) {
    console.error("❌ Error loading bookings:", err);
    error.value =
      err.response?.data?.message ||
      "Không thể tải danh sách đặt bàn. Vui lòng thử lại.";
  } finally {
    loading.value = false;
  }
};

// Change tab
const changeTab = (tab) => {
  activeTab.value = tab;
  currentPage.value = 0; // Reset to first page when changing tab
  loadBookings();
};

// Change page
const changePage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page;
    loadBookings();
  }
};

// Confirm booking
const confirmBooking = async (bookingId) => {
  actionLoading.value[bookingId] = true;

  try {
    await bookingApi.confirmBooking(bookingId);
    console.log("✅ Booking confirmed:", bookingId);

    // Reload bookings to get updated status
    await loadBookings();
  } catch (err) {
    console.error("❌ Error confirming booking:", err);
    alert(
      err.response?.data?.message ||
        "Không thể xác nhận đặt bàn. Vui lòng thử lại."
    );
  } finally {
    actionLoading.value[bookingId] = false;
  }
};

// Open reject dialog
const openRejectDialog = (booking) => {
  selectedBooking.value = booking;
  rejectReason.value = "";
  showRejectDialog.value = true;
};

// Close reject dialog
const closeRejectDialog = () => {
  showRejectDialog.value = false;
  selectedBooking.value = null;
  rejectReason.value = "";
};

// Submit reject
const submitReject = async () => {
  if (!rejectReason.value.trim() || !selectedBooking.value) return;

  rejectLoading.value = true;

  try {
    await bookingApi.rejectBooking(
      selectedBooking.value.id,
      rejectReason.value
    );
    console.log("❌ Booking rejected:", selectedBooking.value.id);

    closeRejectDialog();
    await loadBookings();
  } catch (err) {
    console.error("❌ Error rejecting booking:", err);
    alert(
      err.response?.data?.message ||
        "Không thể từ chối đặt bàn. Vui lòng thử lại."
    );
  } finally {
    rejectLoading.value = false;
  }
};

// View booking detail
const viewDetail = (bookingId) => {
  router.push(`/owner/bookings/${bookingId}`);
};

// Owner check-in handler
const ownerCheckIn = async (bookingId) => {
  actionLoading.value[bookingId] = true;
  try {
    await bookingApi.ownerCheckIn(bookingId);
    await loadBookings();
  } catch (err) {
    console.error("❌ Error during owner check-in:", err);
    alert(
      err.response?.data?.message ||
        "Xác nhận khách thất bại. Vui lòng thử lại."
    );
  } finally {
    actionLoading.value[bookingId] = false;
  }
};

// Format date time helper
const formatDateTime = (dateTimeString) => {
  if (!dateTimeString) return "";
  const date = new Date(dateTimeString);
  return new Intl.DateTimeFormat("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }).format(date);
};
// Status badge classes
const getStatusClass = (status) => {
  const statusMap = {
    PENDING:
      "bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400",
    CONFIRMED: "bg-primary/10 text-primary",
    COMPLETED:
      "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400",
    CANCELLED: "bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400",
  };
  return statusMap[status] || "bg-gray-100 text-gray-600";
};

// Status text in Vietnamese
const getStatusText = (status) => {
  const statusMap = {
    PENDING: "Chờ xác nhận",
    CONFIRMED: "Đã xác nhận",
    COMPLETED: "Hoàn thành",
    CANCELLED: "Đã hủy",
  };
  return statusMap[status] || status;
};

// Format date
const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
};

// Format currency
const formatCurrency = (amount) => {
  if (amount === null || amount === undefined) return "₫0";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

// Load bookings on mount
onMounted(() => {
  loadBookings();
});
</script>
