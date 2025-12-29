<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Dashboard</h1>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Stats Cards - 100% Real Data -->
    <div
      v-if="!isLoading && !error && statistics"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8"
    >
      <div class="card-premium p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
              Tổng người dùng
            </p>
            <h3 class="text-3xl font-black text-gradient-primary mb-1">
              {{ formatNumber(statistics.overview?.totalUsers || 0) }}
            </h3>
            <p v-if="statistics.growth" class="text-xs" :class="statistics.growth.userGrowthRate >= 0 ? 'text-green-600' : 'text-red-600'">
              <span class="material-symbols-outlined text-xs align-middle">{{ statistics.growth.userGrowthRate >= 0 ? 'trending_up' : 'trending_down' }}</span>
              {{ Math.abs(statistics.growth.userGrowthRate || 0).toFixed(1) }}%
            </p>
          </div>
          <div class="bg-primary/10 p-4 rounded-xl">
            <span class="material-symbols-outlined text-primary text-3xl">group</span>
          </div>
        </div>
      </div>

      <div class="card-premium p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
              Tổng địa điểm
            </p>
            <h3 class="text-3xl font-black text-gradient-primary mb-1">
              {{ formatNumber(statistics.overview?.totalOutlets || 0) }}
            </h3>
            <p v-if="statistics.growth" class="text-xs" :class="statistics.growth.outletGrowthRate >= 0 ? 'text-green-600' : 'text-red-600'">
              <span class="material-symbols-outlined text-xs align-middle">{{ statistics.growth.outletGrowthRate >= 0 ? 'trending_up' : 'trending_down' }}</span>
              {{ Math.abs(statistics.growth.outletGrowthRate || 0).toFixed(1) }}%
            </p>
          </div>
          <div class="bg-primary/10 p-4 rounded-xl">
            <span class="material-symbols-outlined text-primary text-3xl">storefront</span>
          </div>
        </div>
      </div>

      <div class="card-premium p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
              Đặt bàn hôm nay
            </p>
            <h3 class="text-3xl font-black text-gradient-primary mb-1">
              {{ formatNumber(statistics.timeStats?.today?.bookings || 0) }}
            </h3>
            <p class="text-xs text-subtext-light dark:text-subtext-dark">
              {{ statistics.timeStats?.today?.newUsers || 0 }} người dùng mới
            </p>
          </div>
          <div class="bg-primary/10 p-4 rounded-xl">
            <span class="material-symbols-outlined text-primary text-3xl">event</span>
          </div>
        </div>
      </div>

      <div class="card-premium p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
              Doanh thu tháng này
            </p>
            <h3 class="text-2xl font-black text-gradient-primary mb-1">
              {{ formatCurrency(statistics.overview?.monthlyRevenue || 0) }}
            </h3>
            <p v-if="statistics.growth" class="text-xs" :class="statistics.growth.revenueGrowthRate >= 0 ? 'text-green-600' : 'text-red-600'">
              <span class="material-symbols-outlined text-xs align-middle">{{ statistics.growth.revenueGrowthRate >= 0 ? 'trending_up' : 'trending_down' }}</span>
              {{ Math.abs(statistics.growth.revenueGrowthRate || 0).toFixed(1) }}%
            </p>
          </div>
          <div class="bg-primary/10 p-4 rounded-xl">
            <span class="material-symbols-outlined text-primary text-3xl">payments</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Additional Stats -->
    <div
      v-if="!isLoading && !error && statistics"
      class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8"
    >
      <div class="card-premium p-6">
        <p class="text-sm text-subtext-light dark:text-subtext-dark mb-2">Tổng đặt bàn</p>
        <h3 class="text-2xl font-bold text-text-light dark:text-text-dark">
          {{ formatNumber(statistics.overview?.totalBookings || 0) }}
        </h3>
      </div>
      <div class="card-premium p-6">
        <p class="text-sm text-subtext-light dark:text-subtext-dark mb-2">Tổng đánh giá</p>
        <h3 class="text-2xl font-bold text-text-light dark:text-text-dark">
          {{ formatNumber(statistics.overview?.totalReviews || 0) }}
        </h3>
      </div>
      <div class="card-premium p-6">
        <p class="text-sm text-subtext-light dark:text-subtext-dark mb-2">Doanh thu hôm nay</p>
        <h3 class="text-2xl font-bold text-gradient-primary">
          {{ formatCurrency(statistics.overview?.todayRevenue || 0) }}
        </h3>
      </div>
    </div>

    <!-- Charts Section - Real Data -->
    <div
      v-if="!isLoading && !error && statistics"
      class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8"
    >
      <!-- Weekly Bookings Chart -->
      <div class="card-premium p-6">
        <h3 class="text-lg font-bold mb-4 text-text-light dark:text-text-dark">
          Đặt bàn 7 ngày qua
        </h3>
        <div class="h-64 flex items-end justify-between gap-2">
          <div
            v-for="day in statistics.timeStats?.last7Days || []"
            :key="day.date"
            class="flex flex-col items-center flex-1"
          >
            <div class="w-full relative group">
              <div
                class="w-full bg-primary/20 rounded-lg hover:bg-primary/40 transition-colors relative cursor-pointer"
                :style="{height: getBarHeight(day.bookings) + '%'}"
              >
                <div
                  class="absolute bottom-0 left-0 w-full bg-gradient-to-t from-primary to-primary/70 rounded-lg transition-all duration-300"
                  :style="{height: '100%'}"
                ></div>
              </div>
              <div class="absolute -top-8 left-1/2 -translate-x-1/2 opacity-0 group-hover:opacity-100 transition-opacity bg-black/80 text-white text-xs px-2 py-1 rounded whitespace-nowrap">
                {{ day.bookings }} đặt bàn
              </div>
            </div>
            <span class="text-xs mt-2 text-subtext-light dark:text-subtext-dark font-medium">
              {{ day.dayName }}
            </span>
            <span class="text-xs text-subtext-light dark:text-subtext-dark">
              {{ formatDate(day.date) }}
            </span>
          </div>
        </div>
      </div>

      <!-- Top Outlets -->
      <div class="card-premium p-6">
        <h3 class="text-lg font-bold mb-4 text-text-light dark:text-text-dark">
          Top địa điểm phổ biến
        </h3>
        <div class="space-y-3 max-h-64 overflow-y-auto">
          <div
            v-for="(outlet, index) in statistics.topItems?.topOutlets?.slice(0, 5) || []"
            :key="outlet.id"
            class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800/50 transition-colors"
          >
            <div class="flex items-center gap-3 flex-1 min-w-0">
              <div class="flex-shrink-0 w-8 h-8 bg-primary/10 rounded-lg flex items-center justify-center">
                <span class="text-primary font-bold text-sm">#{{ index + 1 }}</span>
              </div>
              <div class="flex-1 min-w-0">
                <p class="font-semibold text-text-light dark:text-text-dark truncate">
                  {{ outlet.name }}
                </p>
                <div class="flex items-center gap-3 text-xs text-subtext-light dark:text-subtext-dark">
                  <span>{{ formatNumber(outlet.bookings) }} đặt bàn</span>
                  <span>•</span>
                  <span class="flex items-center gap-1">
                    <span class="material-symbols-outlined text-yellow-500 text-xs fill">star</span>
                    {{ outlet.rating?.toFixed(1) || 'N/A' }}
                  </span>
                </div>
              </div>
            </div>
            <div class="text-right flex-shrink-0">
              <p class="text-sm font-bold text-primary">
                {{ formatCurrency(outlet.revenue) }}
              </p>
            </div>
          </div>
          <div v-if="!statistics.topItems?.topOutlets || statistics.topItems.topOutlets.length === 0" class="text-center py-8 text-subtext-light dark:text-subtext-dark">
            <span class="material-symbols-outlined text-4xl mb-2 block">storefront</span>
            <p class="text-sm">Chưa có dữ liệu</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Activities -->
    <div
      v-if="!isLoading && !error"
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
    >
      <h3 class="text-lg font-bold mb-4">Hoạt động gần đây</h3>
      <div class="space-y-3">
        <div
          v-for="activity in recentActivities"
          :key="activity.id"
          class="flex items-center gap-4 p-3 rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5"
        >
          <span class="material-symbols-outlined text-primary">{{
            activity.icon
          }}</span>
          <div class="flex-1">
            <p class="font-medium">{{ activity.title }}</p>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              {{ activity.time }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {adminApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const isLoading = ref(false);
const error = ref(null);
const statistics = ref(null);

// Legacy stats for backward compatibility
const stats = computed(() => ({
  totalUsers: statistics.value?.overview?.totalUsers || 0,
  totalOutlets: statistics.value?.overview?.totalOutlets || 0,
  todayBookings: statistics.value?.timeStats?.today?.bookings || 0,
  monthlyRevenue: statistics.value?.overview?.monthlyRevenue || 0,
}));

const formatCurrency = (amount) => {
  if (!amount) return "0 ₫";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

const formatNumber = (num) => {
  if (!num) return "0";
  return new Intl.NumberFormat("vi-VN").format(num);
};

const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN", { day: "2-digit", month: "2-digit" });
};

const getBarHeight = (value) => {
  if (!value || value === 0) return 5;
  const maxValue = Math.max(...(statistics.value?.timeStats?.last7Days?.map(d => d.bookings) || [1]));
  if (maxValue === 0) return 5;
  return Math.max(10, (value / maxValue) * 100);
};

const getMonthlyBarHeight = (revenue) => {
  if (!revenue || revenue === 0) return 5;
  const maxValue = Math.max(...(statistics.value?.timeStats?.last6Months?.map(m => Number(m.revenue) || 0) || [1]));
  if (maxValue === 0) return 5;
  return Math.max(10, (Number(revenue) / maxValue) * 100);
};

const fetchDashboardStats = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    // Fetch real statistics from new API (100% real data)
    const response = await adminApi.getStatistics();
    statistics.value = response.data?.data || response.data || response;
    console.log("✅ Admin Statistics loaded (100% real data):", statistics.value);
  } catch (err) {
    console.error("❌ Error fetching admin statistics:", err);
    error.value = "Không thể tải dữ liệu thống kê. Vui lòng thử lại.";
    
    // Fallback: try old endpoint
    try {
      const fallbackResponse = await adminApi.getDashboardStats();
      const fallbackData = fallbackResponse.data || fallbackResponse;
      statistics.value = {
        overview: {
          totalUsers: fallbackData.totalUsers || 0,
          totalOutlets: fallbackData.totalOutlets || 0,
          totalBookings: fallbackData.todayBookings || 0,
          totalReviews: 0,
          totalOrders: 0,
          totalRevenue: fallbackData.monthlyRevenue || 0,
          monthlyRevenue: fallbackData.monthlyRevenue || 0,
          todayRevenue: 0,
        },
        timeStats: {
          last7Days: [],
          last6Months: [],
          today: {
            bookings: fallbackData.todayBookings || 0,
            newUsers: 0,
            newOutlets: 0,
            revenue: 0,
          },
        },
        topItems: {
          topOutlets: [],
          topCategories: [],
          topUsers: [],
        },
        growth: {
          userGrowthRate: 0,
          outletGrowthRate: 0,
          bookingGrowthRate: 0,
          revenueGrowthRate: 0,
        },
      };
    } catch (fallbackErr) {
      console.error("Fallback also failed:", fallbackErr);
    }
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchDashboardStats();
});
</script>

