<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Lịch sử giao dịch</h1>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <input
          v-model="filters.search"
          type="text"
          placeholder="Tìm kiếm theo ID, email..."
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          @keyup.enter="handleSearch"
        />
        <select
          v-model="filters.status"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả trạng thái</option>
          <option value="SUCCESS">Thành công</option>
          <option value="FAILED">Thất bại</option>
          <option value="PENDING">Đang xử lý</option>
        </select>
        <input
          v-model="filters.startDate"
          type="date"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        />
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

    <!-- Transactions Table -->
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
              ID
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Người dùng
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Số tiền
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Loại
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Trạng thái
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Ngày giao dịch
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-border-light dark:divide-border-dark">
          <tr
            v-for="transaction in transactions"
            :key="transaction.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ transaction.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">
                {{ transaction.user?.email || transaction.userId || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">
                {{ formatCurrency(transaction.amount || 0) }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ transaction.type || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="getStatusClass(transaction.status)"
                class="px-2 py-1 text-xs font-medium rounded-full"
              >
                {{ formatStatus(transaction.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light">
              {{ formatDate(transaction.createdAt || transaction.createdDate) }}
            </td>
          </tr>
          <tr v-if="transactions.length === 0">
            <td colspan="6" class="px-6 py-8 text-center text-subtext-light">
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
import {ref, onMounted} from "vue";
import {adminApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const isLoading = ref(false);
const error = ref(null);
const transactions = ref([]);
const filters = ref({
  search: "",
  status: "",
  startDate: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 10,
  totalElements: 0,
  totalPages: 0,
});

const formatCurrency = (amount) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

const formatStatus = (status) => {
  const statusMap = {
    SUCCESS: "Thành công",
    FAILED: "Thất bại",
    PENDING: "Đang xử lý",
  };
  return statusMap[status] || status || "N/A";
};

const getStatusClass = (status) => {
  const classMap = {
    SUCCESS: "bg-positive/10 text-positive",
    FAILED: "bg-red-100 text-red-600",
    PENDING: "bg-yellow-100 text-yellow-600",
  };
  return classMap[status] || "bg-gray-100 text-gray-600";
};

const formatDate = (date) => {
  if (!date) return "N/A";
  try {
    return new Date(date).toLocaleDateString("vi-VN");
  } catch {
    return date;
  }
};

const fetchTransactions = async () => {
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
    if (filters.value.startDate) {
      params.startDate = filters.value.startDate;
    }

    const response = await adminApi.getTransactions(params);
    const data = response.data || response;
    transactions.value = data.content || data.data || [];
    if (data.totalElements !== undefined) {
      pagination.value.totalElements = data.totalElements;
      pagination.value.totalPages = data.totalPages || Math.ceil(data.totalElements / pagination.value.pageSize);
    }
  } catch (err) {
    console.error("Error fetching transactions:", err);
    error.value = err.response?.data?.message || "Không thể tải lịch sử giao dịch";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchTransactions();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    status: "",
    startDate: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchTransactions();
  }
};

onMounted(() => {
  fetchTransactions();
});
</script>
