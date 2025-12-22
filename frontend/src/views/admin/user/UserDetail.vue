<template>
  <div class="px-10 py-8">
    <router-link
      to="/admin/users"
      class="flex items-center gap-2 text-primary mb-6 hover:underline"
    >
      <span class="material-symbols-outlined">arrow_back</span>
      Quay lại
    </router-link>

    <h1 class="text-2xl font-bold mb-6">Chi tiết người dùng</h1>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- User Info Card -->
      <div
        class="lg:col-span-1 bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex flex-col items-center">
          <div class="h-24 w-24 rounded-full bg-gray-300 mb-4"></div>
          <h2 class="text-xl font-bold mb-2">Nguyễn Văn A</h2>
          <span
            class="px-3 py-1 text-sm font-medium rounded-full bg-primary/10 text-primary mb-4"
            >User</span
          >
          <div class="w-full space-y-3">
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >mail</span
              >
              <span>nguyenvana@email.com</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >phone</span
              >
              <span>0901234567</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >calendar_today</span
              >
              <span>Tham gia: 15/01/2025</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Details -->
      <div class="lg:col-span-2 space-y-6">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
        >
          <h3 class="text-lg font-bold mb-4">Thông tin chi tiết</h3>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Họ và tên
              </p>
              <p class="font-medium">Nguyễn Văn A</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Giới tính
              </p>
              <p class="font-medium">Nam</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Ngày sinh
              </p>
              <p class="font-medium">15/05/1990</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Thành viên
              </p>
              <p class="font-medium">Premium</p>
            </div>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
        >
          <h3 class="text-lg font-bold mb-4">Lịch sử đặt bàn</h3>
          <div class="space-y-3">
            <div
              v-for="booking in bookings"
              :key="booking.id"
              class="flex items-center justify-between p-3 border border-border-light dark:border-border-dark rounded-lg"
            >
              <div>
                <p class="font-medium">{{ booking.outlet }}</p>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  {{ booking.date }}
                </p>
              </div>
              <span
                :class="getStatusClass(booking.status)"
                class="px-2 py-1 text-xs font-medium rounded-full"
              >
                {{ booking.status }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";

const bookings = ref([
  {
    id: 1,
    outlet: "Nhà hàng Hương Sen",
    date: "20/12/2024",
    status: "Hoàn thành",
  },
  {id: 2, outlet: "Quán Café Phố Cổ", date: "18/12/2024", status: "Đã hủy"},
  {id: 3, outlet: "BBQ Garden", date: "15/12/2024", status: "Hoàn thành"},
]);

const getStatusClass = (status) => {
  switch (status) {
    case "Hoàn thành":
      return "bg-positive/10 text-positive";
    case "Đã hủy":
      return "bg-red-100 text-red-600";
    default:
      return "bg-gray-100 text-gray-600";
  }
};
</script>
