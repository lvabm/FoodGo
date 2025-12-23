<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý đơn đặt bàn</h1>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium mb-2">Tìm kiếm</label>
          <input
            v-model="filters.search"
            type="text"
            placeholder="Mã đơn, tên khách hàng..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
            @keyup.enter="handleSearch"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Trạng thái</label>
          <select
            v-model="filters.status"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả</option>
            <option value="PENDING">Chờ xử lý</option>
            <option value="CONFIRMED">Đã xác nhận</option>
            <option value="REJECTED">Đã từ chối</option>
            <option value="CANCELLED">Đã hủy</option>
            <option value="COMPLETED">Hoàn thành</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Outlet</label>
          <input
            v-model="filters.outletId"
            type="text"
            placeholder="ID outlet..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            @keyup.enter="handleSearch"
          />
        </div>
        <div class="flex items-end gap-2">
          <button
            @click="handleSearch"
            class="w-full px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
          >
            Lọc
          </button>
          <button
            @click="resetFilters"
            class="w-full px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
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

    <!-- Orders Table -->
    <div
      v-if="!isLoading && !error"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden"
    >
      <table class="w-full">
        <thead class="bg-gray-50 dark:bg-surface-light/5">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Mã đơn
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Khách hàng
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Outlet
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Ngày đặt
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Số khách
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Trạng thái
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Tiền cọc
            </th>
            <th
              class="px-6 py-3 text-right text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Thao tác
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-border-light dark:divide-border-dark">
          <tr
            v-for="order in orders"
            :key="order.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm font-mono">
              {{ order.id || "N/A" }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">
                {{ order.customerName || "N/A" }}
              </div>
              <div
                v-if="order.customerPhone"
                class="text-sm text-subtext-light dark:text-subtext-dark"
              >
                {{ order.customerPhone }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="font-medium">{{ order.outletName || "N/A" }}</div>
              <div
                v-if="order.outletAddress"
                class="text-xs text-subtext-light dark:text-subtext-dark"
              >
                {{ order.outletAddress }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div>{{ formatDate(order.bookingDate) }}</div>
              <div class="text-subtext-light dark:text-subtext-dark">
                {{ formatTime(order.bookingTime) }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ order.numberOfGuests || "N/A" }} người
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                class="px-2 py-1 text-xs font-medium rounded-full"
                :class="getStatusClass(order.status)"
              >
                {{ formatStatus(order.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              {{ formatPrice(order.depositAmount) }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  v-if="order.status !== 'CANCELLED' && order.status !== 'COMPLETED'"
                  @click="handleForceCancel(order)"
                  class="text-red-600 hover:text-red-500"
                >
                  Hủy đơn
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="orders.length === 0">
            <td colspan="8" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
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
import {adminApi} from "@/api";
import {useToast} from "@/composables/useToast";
import {useConfirm} from "@/composables/useConfirm";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const {success, error: showError} = useToast();
const {confirm} = useConfirm();

const isLoading = ref(false);
const error = ref(null);
const orders = ref([]);
const filters = ref({
  search: "",
  status: "",
  outletId: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 10,
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

const formatStatus = (status) => {
  const statusMap = {
    PENDING: "Chờ xử lý",
    CONFIRMED: "Đã xác nhận",
    REJECTED: "Đã từ chối",
    CANCELLED: "Đã hủy",
    COMPLETED: "Hoàn thành",
  };
  return statusMap[status] || status || "N/A";
};

const getStatusClass = (status) => {
  const classMap = {
    PENDING: "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400",
    CONFIRMED: "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400",
    REJECTED: "bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400",
    CANCELLED: "bg-gray-100 text-gray-800 dark:bg-gray-800 dark:text-gray-400",
    COMPLETED: "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400",
  };
  return classMap[status] || "bg-gray-100 text-gray-800";
};

const formatDate = (date) => {
  if (!date) return "N/A";
  try {
    return new Date(date).toLocaleDateString("vi-VN");
  } catch {
    return date;
  }
};

const formatTime = (time) => {
  if (!time) return "N/A";
  try {
    if (typeof time === "string") {
      return time.substring(0, 5); // HH:mm format
    }
    return time;
  } catch {
    return time;
  }
};

const formatPrice = (price) => {
  if (!price && price !== 0) return "N/A";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const fetchOrders = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.pageSize,
    };

    if (filters.value.search) {
      params.search = filters.value.search;
    }
    if (filters.value.status) {
      params.status = filters.value.status;
    }
    if (filters.value.outletId) {
      params.outletId = filters.value.outletId;
    }

    const response = await adminApi.getOrders(params);
    
    // Handle PageResponse structure: { data: Array, totalElements, totalPages, ... }
    let pageData = response;
    let allOrders = [];
    
    if (response && typeof response === 'object' && !Array.isArray(response)) {
      if (response.data && Array.isArray(response.data)) {
        allOrders = response.data;
        pageData = response;
      } else if (response.content && Array.isArray(response.content)) {
        allOrders = response.content;
        pageData = response;
      }
    } else if (Array.isArray(response)) {
      allOrders = response;
      pageData = { totalElements: response.length, totalPages: 1 };
    }

    // Ensure orders is always an array
    orders.value = Array.isArray(allOrders) ? allOrders : [];
    
    // Update pagination
    if (pageData && typeof pageData === 'object' && !Array.isArray(pageData)) {
      if (pageData.totalElements !== undefined) {
        pagination.value.totalElements = pageData.totalElements;
        pagination.value.totalPages = pageData.totalPages || Math.ceil(pageData.totalElements / pagination.value.pageSize);
      }
    }
  } catch (err) {
    console.error("Error fetching orders:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách đơn đặt bàn";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchOrders();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    status: "",
    outletId: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchOrders();
  }
};

const handleForceCancel = async (order) => {
  const confirmed = await confirm(`Bạn có chắc muốn hủy đơn đặt bàn này?`);
  if (!confirmed) return;

  try {
    await adminApi.forceCancelOrder(order.id);
    success("Hủy đơn đặt bàn thành công");
    await fetchOrders();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

onMounted(() => {
  fetchOrders();
});
</script>
