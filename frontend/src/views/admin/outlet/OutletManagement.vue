<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý địa điểm</h1>
    </div>

    <!-- Stats -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Tổng địa điểm
        </p>
        <h3 class="text-2xl font-bold mt-1">{{ stats.total || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Đang chờ duyệt
        </p>
        <h3 class="text-2xl font-bold mt-1 text-yellow-600">{{ stats.pending || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Hoạt động
        </p>
        <h3 class="text-2xl font-bold mt-1 text-positive">{{ stats.active || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">Bị khóa</p>
        <h3 class="text-2xl font-bold mt-1 text-red-600">{{ stats.locked || 0 }}</h3>
      </div>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <input
          v-model="filters.search"
          type="text"
          placeholder="Tìm kiếm địa điểm..."
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
          @keyup.enter="handleSearch"
        />
        <select
          v-model="filters.category"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả danh mục</option>
          <option
            v-for="cat in categories"
            :key="cat.id"
            :value="cat.id"
          >
            {{ cat.name }}
          </option>
        </select>
        <select
          v-model="filters.status"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả trạng thái</option>
          <option value="active">Hoạt động</option>
          <option value="locked">Vô hiệu hóa</option>
        </select>
        <div class="flex gap-2">
          <button
            @click="handleSearch"
            class="flex-1 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
          >
            Lọc
          </button>
          <button
            @click="resetFilters"
            class="flex-1 px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            Reset
          </button>
        </div>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Outlets Grid -->
    <div
      v-if="!isLoading && !error"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
    >
      <div
        v-for="outlet in outlets"
        :key="outlet.id"
        class="bg-white dark:bg-surface-dark rounded-xl overflow-hidden border border-border-light dark:border-border-dark hover:shadow-lg transition-shadow"
      >
        <div class="h-48 relative">
          <ImageDisplay
            :image-url="getOutletImageUrl(outlet)"
            :alt="outlet.name"
            placeholder-icon="storefront"
            container-class="h-full w-full"
            image-class="w-full h-full"
          />
        </div>
        <div class="p-4">
          <div class="flex items-start justify-between mb-2">
            <h3 class="font-bold text-lg">{{ outlet.name }}</h3>
            <span
              :class="getStatusClass(outlet.isActive)"
              class="px-2 py-1 text-xs font-medium rounded-full"
            >
              {{ getStatusText(outlet.isActive) }}
            </span>
          </div>
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-3">
            {{ outlet.address }}
          </p>
          <div class="flex items-center justify-between mb-3">
            <div class="flex items-center gap-1">
              <span
                class="material-symbols-outlined fill text-yellow-500 text-lg"
                >star</span
              >
              <span class="font-medium">{{ outlet.averageRating || 0 }}</span>
              <span class="text-sm text-subtext-light dark:text-subtext-dark"
                >({{ outlet.totalReviews || 0 }})</span
              >
            </div>
          </div>
          <div class="flex gap-2">
            <router-link
              :to="`/admin/outlets/${outlet.id}`"
              class="flex-1 text-center px-3 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Chi tiết
            </router-link>
            <button
              v-if="outlet.isActive === false"
              @click="approveOutlet(outlet)"
              class="flex-1 px-3 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Kích hoạt
            </button>
            <button
              v-else
              @click="lockOutlet(outlet)"
              class="flex-1 px-3 py-2 bg-yellow-600 text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Vô hiệu hóa
            </button>
          </div>
        </div>
      </div>
      <div v-if="outlets.length === 0" class="col-span-full text-center py-8 text-subtext-light">
        Không có dữ liệu
      </div>
    </div>

    <!-- Pagination -->
    <div
      v-if="!isLoading && !error && pagination.totalElements > 0"
      class="flex items-center justify-between mt-6"
    >
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        Hiển thị {{ (pagination.currentPage * pagination.pageSize) + 1 }}-{{ Math.min((pagination.currentPage + 1) * pagination.pageSize, pagination.totalElements) }} trong
        {{ pagination.totalElements }} kết quả
      </p>
      <div class="flex gap-2">
        <button
          @click="goToPage(pagination.currentPage - 1)"
          :disabled="pagination.currentPage === 0"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Trước
        </button>
        <button
          v-for="page in visiblePages"
          :key="page"
          @click="goToPage(page)"
          :class="
            page === pagination.currentPage
              ? 'px-4 py-2 bg-primary text-white rounded-lg'
              : 'px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5'
          "
        >
          {{ page + 1 }}
        </button>
        <button
          @click="goToPage(pagination.currentPage + 1)"
          :disabled="pagination.currentPage >= pagination.totalPages - 1"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Sau
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {adminApi, outletApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const isLoading = ref(false);
const error = ref(null);
const outlets = ref([]);
const categories = ref([]);
const stats = ref({
  total: 0,
  pending: 0,
  active: 0,
  locked: 0,
});
const filters = ref({
  search: "",
  category: "",
  status: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 12,
  totalElements: 0,
  totalPages: 0,
});

const visiblePages = computed(() => {
  const total = pagination.value.totalPages;
  const current = pagination.value.currentPage;
  const pages = [];
  const maxVisible = 5;

  if (total <= maxVisible) {
    for (let i = 0; i < total; i++) {
      pages.push(i);
    }
  } else {
    if (current < 3) {
      for (let i = 0; i < maxVisible; i++) {
        pages.push(i);
      }
    } else if (current > total - 4) {
      for (let i = total - maxVisible; i < total; i++) {
        pages.push(i);
      }
    } else {
      for (let i = current - 2; i <= current + 2; i++) {
        pages.push(i);
      }
    }
  }
  return pages;
});

const getStatusClass = (isActive) => {
  if (isActive === false) {
    return "bg-red-100 text-red-600";
  }
  return "bg-positive/10 text-positive";
};

const getStatusText = (isActive) => {
  if (isActive === false) {
    return "Bị khóa";
  }
  return "Hoạt động";
};

const getOutletImageUrl = (outlet) => {
  if (!outlet.images || outlet.images.length === 0) {
    return null;
  }
  const firstImage = outlet.images[0];
  // Handle both string URLs and object with imageUrl property
  return typeof firstImage === "string" ? firstImage : firstImage.imageUrl || firstImage.url || null;
};

const fetchCategories = async () => {
  try {
    const response = await outletApi.getCategories();
    categories.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch categories:", err);
  }
};

const fetchOutlets = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.pageSize,
    };

    if (filters.value.search) {
      params.name = filters.value.search;
    }
    if (filters.value.category) {
      params.categoryId = filters.value.category;
    }
    // Note: Backend OutletFilterRequest doesn't have isActive field
    // Backend filter defaults to isActive = true only
    // Filtering by status will be done on frontend after fetching
    // TODO: Add isActive to OutletFilterRequest in backend

    const response = await adminApi.getOutlets(params);
    const data = response.data || response;

    let fetchedOutlets = data.content || data.data || [];
    
    // Filter by status on frontend (since backend doesn't support isActive filter)
    if (filters.value.status === "active") {
      fetchedOutlets = fetchedOutlets.filter((o) => o.isActive !== false);
    } else if (filters.value.status === "locked") {
      fetchedOutlets = fetchedOutlets.filter((o) => o.isActive === false);
    }
    
    outlets.value = fetchedOutlets;
    
    if (data.totalElements !== undefined) {
      pagination.value.totalElements = data.totalElements;
      pagination.value.totalPages = data.totalPages || Math.ceil(data.totalElements / pagination.value.pageSize);
    }

    // Calculate stats - Note: Backend may not have isLocked field
    // Using isActive to determine status
    // Stats are calculated from all outlets, not just filtered ones
    const allOutlets = data.content || data.data || [];
    const activeOutlets = allOutlets.filter((o) => o.isActive !== false);
    const inactiveOutlets = allOutlets.filter((o) => o.isActive === false);
    
    stats.value = {
      total: data.totalElements || 0,
      pending: 0, // Backend doesn't have pending status, using isActive instead
      active: activeOutlets.length,
      locked: inactiveOutlets.length,
    };
  } catch (err) {
    console.error("Error fetching outlets:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách địa điểm";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchOutlets();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    category: "",
    status: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchOutlets();
  }
};

const approveOutlet = async (outlet) => {
  if (!confirm(`Bạn có chắc muốn kích hoạt địa điểm "${outlet.name}"?`)) {
    return;
  }

  try {
    // Note: Backend endpoint not implemented yet
    // This will fail until backend adds PATCH /outlets/{id}/approve
    // or adds isActive field to OutletUpdateRequest
    await adminApi.approveOutlet(outlet.id);
    alert("Đã kích hoạt địa điểm thành công");
    await fetchOutlets();
  } catch (err) {
    const errorMsg = err.response?.data?.message || err.message || "Có lỗi xảy ra. Backend chưa hỗ trợ chức năng này.";
    alert(errorMsg);
    console.error("Error approving outlet:", err);
  }
};

const lockOutlet = async (outlet) => {
  if (!confirm(`Bạn có chắc muốn vô hiệu hóa địa điểm "${outlet.name}"?`)) {
    return;
  }

  try {
    // Note: Backend endpoint not implemented yet
    // This will fail until backend adds PATCH /outlets/{id}/lock
    // or adds isActive field to OutletUpdateRequest
    await adminApi.lockOutlet(outlet.id);
    alert("Đã vô hiệu hóa địa điểm thành công");
    await fetchOutlets();
  } catch (err) {
    const errorMsg = err.response?.data?.message || err.message || "Có lỗi xảy ra. Backend chưa hỗ trợ chức năng này.";
    alert(errorMsg);
    console.error("Error locking outlet:", err);
  }
};

onMounted(() => {
  fetchCategories();
  fetchOutlets();
});
</script>
