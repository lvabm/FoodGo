<template>
  <div class="px-10 py-8 font-inter">
    <!-- Loading State -->
    <div v-if="loading" class="flex items-center justify-center h-64">
      <div class="text-center">
        <div
          class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary mx-auto"
        ></div>
        <p class="mt-4 text-subtext-light dark:text-subtext-dark">
          Đang tải dữ liệu...
        </p>
      </div>
    </div>

    <!-- Error State -->
    <div
      v-else-if="error"
      class="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-xl p-6"
    >
      <p class="text-red-600 dark:text-red-400">{{ error }}</p>
      <button
        @click="fetchStats"
        class="mt-4 px-4 py-2 bg-primary text-white rounded-lg hover:bg-primary/90"
      >
        Thử lại
      </button>
    </div>

    <!-- Stats Content -->
    <div v-else>
      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm hover:shadow-md transition-shadow"
        >
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Đặt bàn hôm nay
              </p>
              <h3
                class="text-3xl font-bold mt-2 text-text-light dark:text-text-dark"
              >
                {{ stats.todayBookings || 0 }}
              </h3>
            </div>
            <div class="bg-primary/10 p-3 rounded-lg">
              <span class="material-symbols-outlined text-primary text-2xl"
                >event</span
              >
            </div>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm hover:shadow-md transition-shadow"
        >
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Check-in hôm nay
              </p>
              <h3
                class="text-3xl font-bold mt-2 text-text-light dark:text-text-dark"
              >
                {{ stats.todayCheckins || 0 }}
              </h3>
            </div>
            <div class="bg-primary/10 p-3 rounded-lg">
              <span class="material-symbols-outlined text-primary text-2xl"
                >check_circle</span
              >
            </div>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm hover:shadow-md transition-shadow"
        >
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Đánh giá trung bình
              </p>
              <h3
                class="text-3xl font-bold mt-2 text-text-light dark:text-text-dark"
              >
                <template v-if="stats.averageRating">
                  {{ Number(stats.averageRating).toFixed(1) }} ⭐
                </template>
                <template v-else>
                  <span class="text-xl">Chưa có</span>
                </template>
              </h3>
              <p class="text-xs text-subtext-light dark:text-subtext-dark mt-1">
                {{ stats.totalReviews || 0 }} đánh giá
              </p>
            </div>
            <div class="bg-primary/10 p-3 rounded-lg">
              <span class="material-symbols-outlined text-primary text-2xl"
                >grade</span
              >
            </div>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm hover:shadow-md transition-shadow"
        >
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Doanh thu tháng
              </p>
              <h3
                class="text-3xl font-bold mt-2 text-text-light dark:text-text-dark"
              >
                <template v-if="stats.monthRevenue">
                  ₫{{ formatCurrency(stats.monthRevenue) }}
                </template>
                <template v-else>
                  <span class="text-xl">Chưa có</span>
                </template>
              </h3>
            </div>
            <div class="bg-primary/10 p-3 rounded-lg">
              <span class="material-symbols-outlined text-primary text-2xl"
                >trending_up</span
              >
            </div>
          </div>
        </div>
      </div>

      <!-- Charts Section -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <h3
            class="text-lg font-bold text-text-light dark:text-text-dark mb-4"
          >
            Đặt bàn theo tuần
          </h3>
            <div v-if="stats.weekBookings && stats.weekBookings.length > 0" class="h-64">
            <BarChart
              :labels="stats.weekBookings.map(d => d.dayName)"
              :values="stats.weekBookings.map(d => d.count)"
              height="240"
              color="#F97316"
            />
          </div>
          <div v-else class="h-64 flex items-center justify-center">
            <p class="text-subtext-light dark:text-subtext-dark">Chưa có dữ liệu</p>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <h3
            class="text-lg font-bold text-text-light dark:text-text-dark mb-4"
          >
            Đặt bàn sắp tới
          </h3>
          <div
            v-if="stats.upcomingBookings && stats.upcomingBookings.length > 0"
            class="space-y-3"
          >
            <div
              v-for="booking in stats.upcomingBookings"
              :key="booking.bookingId"
              class="flex items-center justify-between p-3 bg-background-light dark:bg-background-dark rounded-lg hover:bg-primary/5 transition-colors"
            >
              <div class="flex items-center gap-3">
                <div
                  class="w-10 h-10 bg-primary/20 rounded-full flex items-center justify-center"
                >
                  <span class="material-symbols-outlined text-primary text-lg"
                    >person</span
                  >
                </div>
                <div>
                  <p class="font-medium text-text-light dark:text-text-dark">
                    {{ booking.customerName }}
                  </p>
                  <p class="text-sm text-subtext-light dark:text-subtext-dark">
                    {{ formatBookingTime(booking.bookingDate) }} •
                    {{ booking.numberOfGuests }} người
                  </p>
                </div>
              </div>
              <span
                :class="[
                  'px-2 py-1 text-xs font-medium rounded-full',
                  booking.status === 'Đã xác nhận'
                    ? 'bg-green-100 text-green-700'
                    : 'bg-yellow-100 text-yellow-700',
                ]"
              >
                {{ booking.status }}
              </span>
            </div>
          </div>
          <div v-else class="h-48 flex items-center justify-center">
            <p class="text-subtext-light dark:text-subtext-dark">
              Chưa có đặt bàn sắp tới
            </p>
          </div>
        </div>
      </div>

      <!-- Recent Reviews -->
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
      >
        <h3 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
          Đánh giá mới nhất
        </h3>
        <div
          v-if="stats.recentReviews && stats.recentReviews.length > 0"
          class="space-y-4"
        >
          <div
            v-for="review in stats.recentReviews"
            :key="review.reviewId"
            class="border-b border-border-light dark:border-border-dark last:border-0 pb-4 last:pb-0"
          >
            <div class="flex items-start gap-3">
              <div
                class="w-10 h-10 bg-primary/20 rounded-full flex-shrink-0 flex items-center justify-center"
              >
                <span class="material-symbols-outlined text-primary text-lg"
                  >person</span
                >
              </div>
              <div class="flex-1">
                <div class="flex items-center justify-between mb-1">
                  <p class="font-medium text-text-light dark:text-text-dark">
                    {{ review.customerName }}
                  </p>
                  <div class="flex items-center gap-1">
                    <span
                      class="material-symbols-outlined fill text-yellow-500 text-sm"
                      >star</span
                    >
                    <span class="text-sm font-medium">{{ review.rating }}</span>
                  </div>
                </div>
                <p
                  class="text-sm text-subtext-light dark:text-subtext-dark mb-1"
                >
                  {{ review.comment }}
                </p>
                <p class="text-xs text-subtext-light dark:text-subtext-dark">
                  {{ review.timeAgo }}
                </p>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="h-32 flex items-center justify-center">
          <p class="text-subtext-light dark:text-subtext-dark">
            Chưa có đánh giá
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {ownerApi} from "@/api";
import BarChart from "@/components/charts/BarChart.vue";

const loading = ref(true);
const error = ref(null);
const stats = ref({
  todayBookings: 0,
  todayCheckins: 0,
  averageRating: null,
  totalReviews: 0,
  monthRevenue: null,
  weekBookings: [],
  upcomingBookings: [],
  recentReviews: [],
});

const fetchStats = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await ownerApi.getDashboardStats();
    console.log("📊 Dashboard stats response:", response);
    // Interceptor already extracts data field, so response IS the data
    stats.value = response || stats.value;
  } catch (err) {
    console.error("Failed to fetch dashboard stats:", err);
    error.value =
      err.message || "Không thể tải dữ liệu thống kê. Vui lòng thử lại.";
  } finally {
    loading.value = false;
  }
};

const getBarHeight = (count) => {
  if (!stats.value.weekBookings || stats.value.weekBookings.length === 0)
    return 0;
  const maxCount = Math.max(...stats.value.weekBookings.map((d) => d.count));
  if (maxCount === 0) return 10;
  return Math.max((count / maxCount) * 100, 10);
};

const formatCurrency = (value) => {
  if (!value) return "0";
  return new Intl.NumberFormat("vi-VN").format(value);
};

const formatBookingTime = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");
  return `${hours}:${minutes}`;
};

onMounted(() => {
  fetchStats();
});
</script>
