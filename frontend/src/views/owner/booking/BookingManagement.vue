<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Quản lý đặt bàn</h1>

    <!-- Tabs -->
    <div
      class="flex gap-4 mb-6 border-b border-border-light dark:border-border-dark"
    >
      <button
        v-for="tab in tabs"
        :key="tab"
        :class="[
          'pb-3 px-4 font-medium transition-colors',
          activeTab === tab
            ? 'border-b-2 border-owner-primary text-owner-primary'
            : 'text-gray-500',
        ]"
        @click="activeTab = tab"
      >
        {{ tab }}
      </button>
    </div>

    <!-- Booking List -->
    <div class="space-y-4">
      <div
        v-for="booking in filteredBookings"
        :key="booking.id"
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-start justify-between">
          <div class="flex items-start gap-4">
            <div class="w-12 h-12 bg-gray-300 rounded-full"></div>
            <div>
              <h3 class="font-bold text-lg">{{ booking.customer }}</h3>
              <div
                class="flex items-center gap-4 mt-2 text-sm text-gray-600 dark:text-gray-400"
              >
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-lg"
                    >calendar_today</span
                  >
                  {{ booking.date }}
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-lg"
                    >schedule</span
                  >
                  {{ booking.time }}
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-lg">group</span>
                  {{ booking.guests }} người
                </span>
              </div>
              <p class="mt-2 text-sm text-gray-600 dark:text-gray-400">
                {{ booking.note }}
              </p>
            </div>
          </div>
          <div class="flex flex-col items-end gap-2">
            <span
              :class="getStatusClass(booking.status)"
              class="px-3 py-1 text-sm font-medium rounded-full"
            >
              {{ booking.status }}
            </span>
            <div class="flex gap-2 mt-2">
              <button
                v-if="booking.status === 'Chờ xác nhận'"
                class="px-4 py-2 bg-owner-primary text-white rounded-lg hover:bg-opacity-90"
              >
                Xác nhận
              </button>
              <button
                class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800"
              >
                Chi tiết
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed} from "vue";

const activeTab = ref("Tất cả");
const tabs = ref([
  "Tất cả",
  "Chờ xác nhận",
  "Đã xác nhận",
  "Hoàn thành",
  "Đã hủy",
]);

const bookings = ref([
  {
    id: 1,
    customer: "Nguyễn Văn A",
    date: "22/12/2024",
    time: "19:00",
    guests: 4,
    status: "Chờ xác nhận",
    note: "Muốn bàn gần cửa sổ",
  },
  {
    id: 2,
    customer: "Trần Thị B",
    date: "22/12/2024",
    time: "20:00",
    guests: 2,
    status: "Đã xác nhận",
    note: "Có trẻ em",
  },
  {
    id: 3,
    customer: "Lê Văn C",
    date: "23/12/2024",
    time: "18:30",
    guests: 6,
    status: "Đã xác nhận",
    note: "Tiệc sinh nhật",
  },
]);

const filteredBookings = computed(() => {
  if (activeTab.value === "Tất cả") return bookings.value;
  return bookings.value.filter((b) => b.status === activeTab.value);
});

const getStatusClass = (status) => {
  switch (status) {
    case "Chờ xác nhận":
      return "bg-yellow-100 text-yellow-600";
    case "Đã xác nhận":
      return "bg-owner-primary/10 text-owner-primary";
    case "Hoàn thành":
      return "bg-positive/10 text-positive";
    case "Đã hủy":
      return "bg-red-100 text-red-600";
    default:
      return "bg-gray-100 text-gray-600";
  }
};
</script>
