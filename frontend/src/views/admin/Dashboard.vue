<template>
  <div class="px-10 py-8">
    <!-- Stats Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Tổng người dùng
            </p>
            <h3 class="text-2xl font-bold mt-2">12,458</h3>
          </div>
          <div class="bg-primary/10 p-3 rounded-lg">
            <span class="material-symbols-outlined text-primary text-2xl"
              >group</span
            >
          </div>
        </div>
      </div>

      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Tổng địa điểm
            </p>
            <h3 class="text-2xl font-bold mt-2">3,845</h3>
          </div>
          <div class="bg-primary/10 p-3 rounded-lg">
            <span class="material-symbols-outlined text-primary text-2xl"
              >storefront</span
            >
          </div>
        </div>
      </div>

      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Đặt bàn hôm nay
            </p>
            <h3 class="text-2xl font-bold mt-2">1,284</h3>
          </div>
          <div class="bg-primary/10 p-3 rounded-lg">
            <span class="material-symbols-outlined text-primary text-2xl"
              >event</span
            >
          </div>
        </div>
      </div>

      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              Doanh thu tháng này
            </p>
            <h3 class="text-2xl font-bold mt-2">₫42.5M</h3>
          </div>
          <div class="bg-primary/10 p-3 rounded-lg">
            <span class="material-symbols-outlined text-primary text-2xl"
              >payments</span
            >
          </div>
        </div>
      </div>
    </div>

    <!-- Charts Section -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <h3 class="text-lg font-bold mb-4">Đặt bàn theo tuần</h3>
        <div class="h-64 flex items-end justify-between gap-2">
          <div
            v-for="day in weekData"
            :key="day.name"
            class="flex flex-col items-center flex-1"
          >
            <div
              class="w-full bg-primary/20 rounded-lg hover:bg-primary/40 transition-colors relative"
              :style="{height: day.height + '%'}"
            >
              <div
                class="absolute bottom-0 left-0 w-full bg-primary rounded-lg"
                :style="{height: '100%'}"
              ></div>
            </div>
            <span
              class="text-xs mt-2 text-subtext-light dark:text-subtext-dark"
              >{{ day.name }}</span
            >
          </div>
        </div>
      </div>

      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <h3 class="text-lg font-bold mb-4">Địa điểm phổ biến</h3>
        <div class="space-y-4">
          <div
            v-for="outlet in topOutlets"
            :key="outlet.id"
            class="flex items-center justify-between"
          >
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 bg-gray-300 rounded-lg"></div>
              <div>
                <p class="font-medium">{{ outlet.name }}</p>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  {{ outlet.bookings }} đặt bàn
                </p>
              </div>
            </div>
            <span class="text-sm font-medium text-positive"
              >+{{ outlet.growth }}%</span
            >
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Activities -->
    <div
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
import {ref} from "vue";

const weekData = ref([
  {name: "T2", height: 60},
  {name: "T3", height: 75},
  {name: "T4", height: 85},
  {name: "T5", height: 70},
  {name: "T6", height: 90},
  {name: "T7", height: 95},
  {name: "CN", height: 80},
]);

const topOutlets = ref([
  {id: 1, name: "Nhà hàng Hương Sen", bookings: 245, growth: 12},
  {id: 2, name: "Quán Café Phố Cổ", bookings: 198, growth: 8},
  {id: 3, name: "BBQ Garden", bookings: 167, growth: 15},
  {id: 4, name: "Lẩu Thái Bangkok", bookings: 142, growth: 5},
]);

const recentActivities = ref([
  {
    id: 1,
    icon: "person_add",
    title: "Người dùng mới đăng ký",
    time: "5 phút trước",
  },
  {
    id: 2,
    icon: "storefront",
    title: "Địa điểm mới được thêm",
    time: "15 phút trước",
  },
  {
    id: 3,
    icon: "event",
    title: "Đặt bàn mới tại Nhà hàng Hương Sen",
    time: "23 phút trước",
  },
  {
    id: 4,
    icon: "report",
    title: "Báo cáo mới cần xem xét",
    time: "1 giờ trước",
  },
]);
</script>
