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

          <div class="grid grid-cols-2 md:grid-cols-3 gap-4 mb-4">
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

            <!-- User check-in button -->
            <button
              v-if="canUserCheckIn(booking)"
              @click="userCheckIn(booking)"
              :disabled="isChecking[booking.id]"
              class="px-4 py-2 bg-green-500 text-white rounded-lg text-sm font-medium hover:bg-green-600 transition-colors disabled:opacity-50"
            >
              <span v-if="isChecking[booking.id]">Đang gửi...</span>
              <span v-else>✓ Đã tới quán</span>
            </button>

            <!-- Check-in indicator -->
            <div
              v-else-if="booking.userCheckedInAt"
              class="flex items-center gap-2"
            >
              <span class="text-sm text-green-700 font-medium"
                >✓ Bạn đã check-in</span
              >
              <span class="text-xs text-subtext-light">{{
                formatDateTime(booking.userCheckedInAt)
              }}</span>
            </div>

            <button
              v-if="canCancel(booking.status)"
              @click="openCancelDialog(booking)"
              class="px-4 py-2 bg-red-500 text-white rounded-lg text-sm font-medium hover:bg-red-600 transition-colors"
            >
              Hủy đặt bàn
            </button>

            <!-- Review states: only show for COMPLETED bookings -->
            <template v-if="hasReviewed(booking)">
              <div
                class="px-4 py-2 rounded-lg text-sm font-medium text-green-700 flex items-center gap-2"
              >
                <span class="material-symbols-outlined">check_circle</span>
                <span>Cảm ơn bạn đã đánh giá</span>
              </div>
            </template>

            <template v-else-if="isBookingReviewable(booking)">
              <button
                @click="
                  () => {
                    console.log(
                      'Open review for booking',
                      booking.id,
                      'hasReviewed=',
                      hasReviewed(booking)
                    );
                    openReviewForBooking(booking);
                  }
                "
                class="px-4 py-2 bg-primary text-white rounded-lg text-sm font-medium hover:bg-opacity-90 transition-colors"
              >
                Hãy đánh giá
              </button>
            </template>
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

  <!-- Review Dialog -->
  <div
    v-if="showReviewDialog"
    class="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4"
  >
    <div class="bg-white dark:bg-surface-dark rounded-xl max-w-md w-full p-6">
      <h3 class="text-xl font-bold mb-4">Đánh giá đơn đặt bàn</h3>

      <div class="grid grid-cols-2 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium mb-2"
            >Đồ ăn <span class="text-red-500">*</span></label
          >
          <div class="flex gap-2">
            <button
              v-for="s in 5"
              :key="s"
              @click="reviewForm.foodRating = s"
              class="text-3xl"
              :class="
                s <= reviewForm.foodRating ? 'text-yellow-500' : 'text-gray-300'
              "
            >
              <span class="material-symbols-outlined">{{
                s <= reviewForm.foodRating ? "star" : "star_border"
              }}</span>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2"
            >Phục vụ <span class="text-red-500">*</span></label
          >
          <div class="flex gap-2">
            <button
              v-for="s in 5"
              :key="'sv-' + s"
              @click="reviewForm.serviceRating = s"
              class="text-3xl"
              :class="
                s <= reviewForm.serviceRating
                  ? 'text-yellow-500'
                  : 'text-gray-300'
              "
            >
              <span class="material-symbols-outlined">{{
                s <= reviewForm.serviceRating ? "star" : "star_border"
              }}</span>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2"
            >Không gian <span class="text-red-500">*</span></label
          >
          <div class="flex gap-2">
            <button
              v-for="s in 5"
              :key="'amb-' + s"
              @click="reviewForm.ambianceRating = s"
              class="text-3xl"
              :class="
                s <= reviewForm.ambianceRating
                  ? 'text-yellow-500'
                  : 'text-gray-300'
              "
            >
              <span class="material-symbols-outlined">{{
                s <= reviewForm.ambianceRating ? "star" : "star_border"
              }}</span>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2"
            >Giá cả <span class="text-red-500">*</span></label
          >
          <div class="flex gap-2">
            <button
              v-for="s in 5"
              :key="'pr-' + s"
              @click="reviewForm.priceRating = s"
              class="text-3xl"
              :class="
                s <= reviewForm.priceRating
                  ? 'text-yellow-500'
                  : 'text-gray-300'
              "
            >
              <span class="material-symbols-outlined">{{
                s <= reviewForm.priceRating ? "star" : "star_border"
              }}</span>
            </button>
          </div>
        </div>
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium mb-2"
          >Đánh giá của bạn <span class="text-red-500">*</span></label
        >
        <textarea
          v-model="reviewForm.comment"
          rows="4"
          class="w-full px-4 py-3 rounded-lg border"
          placeholder="Chia sẻ trải nghiệm của bạn..."
        ></textarea>
      </div>

      <div v-if="reviewError" class="mb-4 text-red-600">{{ reviewError }}</div>
      <div v-if="reviewSuccess" class="mb-4 text-green-600">
        {{ reviewSuccess }}
      </div>

      <div class="flex gap-3">
        <button
          @click="showReviewDialog = false"
          class="flex-1 px-4 py-2 border rounded-lg"
        >
          Huỷ
        </button>
        <button
          @click="submitReviewForBooking"
          :disabled="
            isSubmittingReview ||
            !reviewForm.foodRating ||
            !reviewForm.serviceRating ||
            !reviewForm.ambianceRating ||
            !reviewForm.priceRating ||
            !reviewForm.comment.trim()
          "
          class="flex-1 px-4 py-2 bg-primary text-white rounded-lg"
        >
          {{ isSubmittingReview ? "Đang gửi..." : "Gửi đánh giá" }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {bookingApi} from "@/api";
import {reviewApi} from "@/api/review";
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

// Check-in state
const isChecking = ref({});

// Review state
const showReviewDialog = ref(false);
const reviewForm = ref({
  foodRating: 0,
  serviceRating: 0,
  ambianceRating: 0,
  priceRating: 0,
  comment: "",
});
const isSubmittingReview = ref(false);
const reviewError = ref("");
const reviewSuccess = ref("");
const selectedBookingForReview = ref(null);
const reviewedBookingIds = ref(new Set()); // set of booking id strings

// Can user check-in? (status=CONFIRMED, booking date is today, not checked-in yet)
const canUserCheckIn = (booking) => {
  if (!booking) return false;
  if (booking.status !== "CONFIRMED") return false;
  const bookingDate = new Date(booking.bookingDate).toDateString();
  const today = new Date().toDateString();
  if (bookingDate !== today) return false;
  return !booking.userCheckedInAt;
};

// User check-in action
const userCheckIn = async (booking) => {
  if (!booking) return;
  isChecking.value[booking.id] = true;
  try {
    await bookingApi.userCheckIn(booking.id);
    // Refresh list
    await fetchBookings(currentPage.value);
  } catch (err) {
    console.error("❌ Error during user check-in:", err);
    alert(
      err.response?.data?.message || "Check-in thất bại. Vui lòng thử lại."
    );
  } finally {
    isChecking.value[booking.id] = false;
  }
};

// --- Reviews: fetch user's reviews so we can know which bookings already reviewed ---
const loadMyReviews = async () => {
  try {
    const resp = await reviewApi.getMyReviews({page: 0, size: 200});
    const reviews = resp.data || resp || [];
    const s = new Set();
    reviews.forEach((r) => {
      if (r.bookingId) s.add(String(r.bookingId));
    });

    // Merge persisted local reviews (optimistic) so the UI stays consistent across refresh
    try {
      const persisted = JSON.parse(
        localStorage.getItem("localReviewedBookingIds") || "[]"
      );
      if (Array.isArray(persisted) && persisted.length) {
        persisted.forEach((id) => s.add(String(id)));
        console.log("💾 Merged persisted local reviewed IDs:", persisted);
      }
    } catch (e) {
      console.warn(
        "⚠️ Could not read persisted reviewed ids from localStorage",
        e
      );
    }

    reviewedBookingIds.value = s; // Update bookings flags if bookings are loaded

    // Remove any persisted IDs that are now confirmed on server (cleanup)
    try {
      const persisted = JSON.parse(
        localStorage.getItem("localReviewedBookingIds") || "[]"
      );
      const remaining = Array.isArray(persisted)
        ? persisted.filter((id) => !s.has(String(id)))
        : [];
      if (!remaining.length) {
        localStorage.removeItem("localReviewedBookingIds");
      } else {
        localStorage.setItem(
          "localReviewedBookingIds",
          JSON.stringify(remaining)
        );
      }
    } catch (e) {
      console.warn("⚠️ Could not cleanup persisted reviewed ids", e);
    }

    console.log(
      "✅ Loaded my reviews, bookingIds:",
      Array.from(reviewedBookingIds.value)
    );
    if (bookings.value && bookings.value.length) {
      bookings.value = bookings.value.map((b) => ({
        ...b,
        _reviewed: reviewedBookingIds.value.has(String(b.id)),
      }));
    }
  } catch (err) {
    console.error("❌ Error loading my reviews", err);
    // Fallback: load persisted reviewed IDs from localStorage so UI remains consistent
    try {
      const persisted = JSON.parse(
        localStorage.getItem("localReviewedBookingIds") || "[]"
      );
      if (Array.isArray(persisted) && persisted.length) {
        const s2 = new Set(persisted.map((id) => String(id)));
        reviewedBookingIds.value = s2;
        console.log(
          "💾 Fallback loaded persisted reviewed IDs:",
          Array.from(s2)
        );
      } else {
        reviewedBookingIds.value = new Set();
      }
    } catch (e) {
      console.warn("⚠️ Failed to read persisted reviewed IDs in fallback", e);
      reviewedBookingIds.value = new Set();
    }
  }
};

// Helper: booking is reviewable only when status is COMPLETED
const isBookingReviewable = (booking) => {
  if (!booking) return false;
  const status = String(booking.status || "").toUpperCase();
  return status === "COMPLETED";
};

const hasReviewed = (booking) => {
  if (!booking) return false;
  const res =
    Boolean(booking._reviewed) ||
    reviewedBookingIds.value.has(String(booking.id));
  console.log(
    "🔍 hasReviewed check for booking",
    booking.id,
    "=>",
    res,
    "(_reviewed=",
    booking._reviewed,
    ", reviewedBookingIds=",
    Array.from(reviewedBookingIds.value)
  );
  return res;
};

// Open review dialog for a booking
const openReviewForBooking = (booking) => {
  selectedBookingForReview.value = booking;
  reviewForm.value = {
    foodRating: 0,
    serviceRating: 0,
    ambianceRating: 0,
    priceRating: 0,
    comment: "",
  };
  reviewError.value = "";
  reviewSuccess.value = "";
  showReviewDialog.value = true;
};

// Submit review for selected booking
const submitReviewForBooking = async () => {
  if (!selectedBookingForReview.value) return;
  if (
    !reviewForm.value.foodRating ||
    !reviewForm.value.serviceRating ||
    !reviewForm.value.ambianceRating ||
    !reviewForm.value.priceRating ||
    !reviewForm.value.comment.trim()
  ) {
    reviewError.value = "Vui lòng nhập đầy đủ thông tin";
    return;
  }

  isSubmittingReview.value = true;
  reviewError.value = "";
  reviewSuccess.value = "";

  try {
    const payload = {
      bookingId: selectedBookingForReview.value.id,
      foodRating: reviewForm.value.foodRating,
      serviceRating: reviewForm.value.serviceRating,
      ambianceRating: reviewForm.value.ambianceRating,
      priceRating: reviewForm.value.priceRating,
      comment: reviewForm.value.comment.trim(),
    };
    console.log("🧾 Submit review payload", payload);
    const created = await reviewApi.createReview(payload);
    reviewSuccess.value = "Đã gửi đánh giá thành công!";

    // mark booking as reviewed - update reviewedBookingIds Set
    const reviewedId = String(selectedBookingForReview.value.id);
    reviewedBookingIds.value.add(reviewedId);

    // Force Vue reactivity by creating a new Set
    reviewedBookingIds.value = new Set(reviewedBookingIds.value);

    // Persist to localStorage as fallback so refresh keeps the optimistic state
    try {
      const persisted = JSON.parse(
        localStorage.getItem("localReviewedBookingIds") || "[]"
      );
      if (!persisted.includes(reviewedId)) {
        persisted.push(reviewedId);
        localStorage.setItem(
          "localReviewedBookingIds",
          JSON.stringify(persisted)
        );
        console.log("💾 Persisted reviewedId to localStorage:", reviewedId);
      }
    } catch (e) {
      console.warn("⚠️ Could not persist reviewed id to localStorage", e);
    }

    console.log(
      "✅ Review submitted — updated reviewedBookingIds:",
      Array.from(reviewedBookingIds.value)
    );

    // Update the booking in the list - create completely new array to trigger reactivity
    bookings.value = bookings.value.map((b) => {
      if (String(b.id) === reviewedId) {
        return {...b, _reviewed: true};
      }
      return b;
    });

    console.log(
      "🔎 Local bookings state after marking reviewed:",
      bookings.value.map((b) => ({id: b.id, _reviewed: b._reviewed}))
    );

    // Wait a moment to show success message
    await new Promise((resolve) => setTimeout(resolve, 500));

    // close dialog
    showReviewDialog.value = false;
  } catch (err) {
    console.error("❌ Error submitting booking review", err);
    reviewError.value = err.message || "Gửi đánh giá thất bại";
  } finally {
    isSubmittingReview.value = false;
  }
};

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
      viewType: "MY_BOOKINGS", // Owner/User xem booking cá nhân của mình
    });

    console.log("✅ Bookings response:", response);

    // Handle PageResponse structure
    if (response.data) {
      bookings.value = response.data;
      console.log(
        "🔁 After fetch: loaded bookings count =",
        bookings.value.length
      );
      // Mark bookings that the user has already reviewed for immediate UI update
      if (bookings.value && bookings.value.length) {
        bookings.value = bookings.value.map((b) => ({
          ...b,
          _reviewed: reviewedBookingIds.value.has(String(b.id)),
        }));
        console.log(
          "🔎 After fetch mapping _reviewed:",
          bookings.value.map((b) => ({id: b.id, _reviewed: b._reviewed}))
        );
        console.log(
          "🔁 Current reviewedBookingIds:",
          Array.from(reviewedBookingIds.value)
        );
      }
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
onMounted(async () => {
  await loadMyReviews();
  await fetchBookings();
});
</script>
