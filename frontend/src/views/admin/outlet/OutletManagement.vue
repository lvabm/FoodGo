<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý địa điểm</h1>
      <button
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm địa điểm
      </button>
    </div>

    <!-- Stats -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Tổng địa điểm
        </p>
        <h3 class="text-2xl font-bold mt-1">3,845</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Đang chờ duyệt
        </p>
        <h3 class="text-2xl font-bold mt-1 text-yellow-600">24</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Hoạt động
        </p>
        <h3 class="text-2xl font-bold mt-1 text-positive">3,621</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">Bị khóa</p>
        <h3 class="text-2xl font-bold mt-1 text-red-600">200</h3>
      </div>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <input
          type="text"
          placeholder="Tìm kiếm địa điểm..."
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        />
        <select
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option>Tất cả danh mục</option>
          <option>Nhà hàng</option>
          <option>Café</option>
          <option>Bar</option>
        </select>
        <select
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option>Tất cả trạng thái</option>
          <option>Hoạt động</option>
          <option>Chờ duyệt</option>
          <option>Bị khóa</option>
        </select>
        <button class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg">
          Lọc
        </button>
      </div>
    </div>

    <!-- Outlets Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
        v-for="outlet in outlets"
        :key="outlet.id"
        class="bg-white dark:bg-surface-dark rounded-xl overflow-hidden border border-border-light dark:border-border-dark hover:shadow-lg transition-shadow"
      >
        <div class="h-48 bg-gray-300"></div>
        <div class="p-4">
          <div class="flex items-start justify-between mb-2">
            <h3 class="font-bold text-lg">{{ outlet.name }}</h3>
            <span
              :class="getStatusClass(outlet.status)"
              class="px-2 py-1 text-xs font-medium rounded-full"
            >
              {{ outlet.status }}
            </span>
          </div>
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-3">
            {{ outlet.address }}
          </p>
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-1">
              <span
                class="material-symbols-outlined fill text-yellow-500 text-lg"
                >star</span
              >
              <span class="font-medium">{{ outlet.rating }}</span>
              <span class="text-sm text-subtext-light dark:text-subtext-dark"
                >({{ outlet.reviews }})</span
              >
            </div>
            <button class="text-primary hover:underline text-sm">
              Chi tiết
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";

const outlets = ref([
  {
    id: 1,
    name: "Nhà hàng Hương Sen",
    address: "123 Nguyễn Huệ, Q1, TPHCM",
    rating: 4.5,
    reviews: 234,
    status: "Hoạt động",
  },
  {
    id: 2,
    name: "Quán Café Phố Cổ",
    address: "45 Lê Lợi, Q1, TPHCM",
    rating: 4.8,
    reviews: 189,
    status: "Hoạt động",
  },
  {
    id: 3,
    name: "BBQ Garden",
    address: "78 Trần Hưng Đạo, Q5, TPHCM",
    rating: 4.3,
    reviews: 156,
    status: "Chờ duyệt",
  },
]);

const getStatusClass = (status) => {
  switch (status) {
    case "Hoạt động":
      return "bg-positive/10 text-positive";
    case "Chờ duyệt":
      return "bg-yellow-100 text-yellow-600";
    case "Bị khóa":
      return "bg-red-100 text-red-600";
    default:
      return "bg-gray-100 text-gray-600";
  }
};
</script>
