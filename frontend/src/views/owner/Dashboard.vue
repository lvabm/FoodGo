<template>
  <div class="px-10 py-8 font-inter">
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
              24
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
              18
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
              4.8 ⭐
            </h3>
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
              ₫25.8M
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
        <h3 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
          Đặt bàn theo tuần
        </h3>
        <div class="h-64 flex items-end justify-between gap-2">
          <div
            v-for="day in weekData"
            :key="day.name"
            class="flex flex-col items-center flex-1"
          >
            <div
              class="w-full bg-primary/20 rounded-lg hover:bg-primary/30 transition-colors relative"
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
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark shadow-sm"
      >
        <h3 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
          Đặt bàn sắp tới
        </h3>
        <div class="space-y-3">
          <div
            v-for="booking in upcomingBookings"
            :key="booking.id"
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
                  {{ booking.name }}
                </p>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  {{ booking.time }} • {{ booking.guests }} người
                </p>
              </div>
            </div>
            <span
              class="px-2 py-1 text-xs font-medium rounded-full bg-primary/10 text-primary"
            >
              {{ booking.status }}
            </span>
          </div>
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
      <div class="space-y-4">
        <div
          v-for="review in recentReviews"
          :key="review.id"
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
                  {{ review.customer }}
                </p>
                <div class="flex items-center gap-1">
                  <span
                    class="material-symbols-outlined fill text-yellow-500 text-sm"
                    >star</span
                  >
                  <span class="text-sm font-medium">{{ review.rating }}</span>
                </div>
              </div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                {{ review.comment }}
              </p>
              <p class="text-xs text-subtext-light dark:text-subtext-dark">
                {{ review.time }}
              </p>
            </div>
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

const upcomingBookings = ref([
  {
    id: 1,
    customer: "Nguyễn Văn A",
    time: "19:00",
    guests: 4,
    status: "Đã xác nhận",
  },
  {
    id: 2,
    customer: "Trần Thị B",
    time: "19:30",
    guests: 2,
    status: "Chờ xác nhận",
  },
  {
    id: 3,
    customer: "Lê Văn C",
    time: "20:00",
    guests: 6,
    status: "Đã xác nhận",
  },
]);

const recentReviews = ref([
  {
    id: 1,
    customer: "Nguyễn Minh",
    rating: 5,
    comment: "Món ăn rất ngon, phục vụ nhiệt tình!",
    time: "2 giờ trước",
  },
  {
    id: 2,
    customer: "Phạm Hà",
    rating: 4,
    comment: "Không gian đẹp, giá hơi cao",
    time: "5 giờ trước",
  },
  {
    id: 3,
    customer: "Trần Tuấn",
    rating: 5,
    comment: "Sẽ quay lại lần sau",
    time: "1 ngày trước",
  },
]);
</script>
