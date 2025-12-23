<template>
  <div class="px-10 py-8 font-inter">
    <h1 class="text-2xl font-bold mb-6 text-text-light dark:text-text-dark">
      Thống kê & Báo cáo
    </h1>

    <!-- Outlet Selector -->
    <div class="mb-6">
      <label
        class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
      >
        Chọn quán
      </label>
      <select
        v-model="selectedOutletId"
        @change="fetchOutletStats"
        class="w-full md:w-96 px-4 py-2 border border-border-light dark:border-border-dark rounded-lg bg-white dark:bg-surface-dark text-text-light dark:text-text-dark focus:outline-none focus:ring-2 focus:ring-primary pr-8 truncate appearance-none"
      >
        <option value="">Tất cả quán</option>
        <option v-for="outlet in outlets" :key="outlet.id" :value="outlet.id">
          {{ outlet.name }}
        </option>
      </select>
    </div>

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
      <!-- Summary Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <div class="flex items-center justify-between mb-2">
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Tổng đặt bàn hôm nay
            </p>
            <span class="material-symbols-outlined text-primary">event</span>
          </div>
          <h3 class="text-3xl font-bold text-text-light dark:text-text-dark">
            {{ stats.todayBookings || 0 }}
          </h3>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <div class="flex items-center justify-between mb-2">
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Check-in hôm nay
            </p>
            <span class="material-symbols-outlined text-primary"
              >check_circle</span
            >
          </div>
          <h3 class="text-3xl font-bold text-text-light dark:text-text-dark">
            {{ stats.todayCheckins || 0 }}
          </h3>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <div class="flex items-center justify-between mb-2">
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Đánh giá trung bình
            </p>
            <span class="material-symbols-outlined text-primary">grade</span>
          </div>
          <h3 class="text-2xl font-bold text-text-light dark:text-text-dark">
            <template v-if="stats.averageRating">
              {{ Number(stats.averageRating).toFixed(1) }} ⭐
            </template>
            <template v-else> Chưa có </template>
          </h3>
          <p class="text-xs text-subtext-light dark:text-subtext-dark mt-1">
            {{ stats.totalReviews || 0 }} đánh giá
          </p>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <div class="flex items-center justify-between mb-2">
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Doanh thu tháng
            </p>
            <span class="material-symbols-outlined text-primary"
              >trending_up</span
            >
          </div>
          <h3 class="text-2xl font-bold text-text-light dark:text-text-dark">
            <template v-if="stats.monthRevenue">
              ₫{{ formatCurrency(stats.monthRevenue) }}
            </template>
            <template v-else> Chưa có </template>
          </h3>
        </div>
      </div>

      <!-- Weekly Bookings Chart -->
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm mb-8"
      >
        <h3 class="text-lg font-bold text-text-light dark:text-text-dark mb-6">
          Đặt bàn 7 ngày qua
        </h3>
        <div v-if="stats.weekBookings && stats.weekBookings.length > 0" class="h-80">
          <BarChart
            :labels="stats.weekBookings.map(d => d.dayName)"
            :values="stats.weekBookings.map(d => d.count)"
            height="320"
            color="#F97316"
          />
        </div>
        <div v-else class="h-80 flex items-center justify-center">
          <p class="text-subtext-light dark:text-subtext-dark">
            Chưa có dữ liệu
          </p>
        </div>
      </div>

      <!-- Two Columns: Upcoming Bookings & Recent Reviews -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Upcoming Bookings -->
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
              class="p-4 bg-background-light dark:bg-background-dark rounded-lg hover:bg-primary/5 transition-colors border border-border-light dark:border-border-dark"
            >
              <div class="flex items-center justify-between mb-2">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-primary text-lg"
                    >person</span
                  >
                  <p class="font-medium text-text-light dark:text-text-dark">
                    {{ booking.customerName }}
                  </p>
                </div>
                <span
                  :class="[
                    'px-2 py-1 text-xs font-medium rounded-full',
                    booking.status === 'Đã xác nhận'
                      ? 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400'
                      : 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400',
                  ]"
                >
                  {{ booking.status }}
                </span>
              </div>
              <div
                class="flex items-center gap-4 text-sm text-subtext-light dark:text-subtext-dark"
              >
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-xs"
                    >schedule</span
                  >
                  {{ formatBookingTime(booking.bookingDate) }}
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-xs">group</span>
                  {{ booking.numberOfGuests }} người
                </span>
              </div>
            </div>
          </div>
          <div v-else class="h-48 flex items-center justify-center">
            <p class="text-subtext-light dark:text-subtext-dark">
              Chưa có đặt bàn sắp tới
            </p>
          </div>
        </div>

        <!-- Recent Reviews -->
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
        >
          <h3
            class="text-lg font-bold text-text-light dark:text-text-dark mb-4"
          >
            Đánh giá gần đây
          </h3>
          <div
            v-if="stats.recentReviews && stats.recentReviews.length > 0"
            class="space-y-4"
          >
            <div
              v-for="review in stats.recentReviews"
              :key="review.reviewId"
              class="p-4 bg-background-light dark:bg-background-dark rounded-lg border border-border-light dark:border-border-dark"
            >
              <div class="flex items-center justify-between mb-2">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-primary text-lg"
                    >person</span
                  >
                  <p class="font-medium text-text-light dark:text-text-dark">
                    {{ review.customerName }}
                  </p>
                </div>
                <div class="flex items-center gap-1">
                  <span
                    class="material-symbols-outlined fill text-yellow-500 text-sm"
                    >star</span
                  >
                  <span class="text-sm font-medium">{{ review.rating }}</span>
                </div>
              </div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-2">
                {{ review.comment }}
              </p>
              <p class="text-xs text-subtext-light dark:text-subtext-dark">
                {{ review.timeAgo }}
              </p>
            </div>
          </div>
          <div v-else class="h-48 flex items-center justify-center">
            <p class="text-subtext-light dark:text-subtext-dark">
              Chưa có đánh giá
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {ownerApi, outletApi} from "@/api";
import BarChart from "@/components/charts/BarChart.vue";

const loading = ref(true);
const error = ref(null);
const outlets = ref([]);
const selectedOutletId = ref("");

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

const fetchOutlets = async () => {
  try {
    const response = await outletApi.getMyOutlets();
    outlets.value = response || [];
  } catch (err) {
    console.error("Failed to fetch outlets:", err);
  }
};

const fetchStats = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await ownerApi.getDashboardStats();
    console.log("📊 Statistics (all outlets) response:", response);
    stats.value = response || stats.value;
  } catch (err) {
    console.error("Failed to fetch stats:", err);
    error.value =
      err.message || "Không thể tải dữ liệu thống kê. Vui lòng thử lại.";
  } finally {
    loading.value = false;
  }
};

const fetchOutletStats = async () => {
  if (!selectedOutletId.value) {
    fetchStats();
    return;
  }

  loading.value = true;
  error.value = null;
  try {
    const response = await ownerApi.getOutletStats(selectedOutletId.value);
    console.log("📊 Statistics (outlet) response:", response);
    stats.value = response || stats.value;
  } catch (err) {
    console.error("Failed to fetch outlet stats:", err);
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
  const day = date.getDate().toString().padStart(2, "0");
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");
  return `${day}/${month} ${hours}:${minutes}`;
};

onMounted(async () => {
  await fetchOutlets();
  await fetchStats();
});
</script>
