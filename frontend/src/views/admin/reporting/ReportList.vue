<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Danh sách báo cáo</h1>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <select
          v-model="filters.status"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả trạng thái</option>
          <option value="PENDING">Chờ xử lý</option>
          <option value="RESOLVED">Đã xử lý</option>
          <option value="REJECTED">Từ chối</option>
        </select>
        <button
          @click="handleSearch"
          class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
        >
          Lọc
        </button>
        <button
          @click="resetFilters"
          class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
        >
          Reset
        </button>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Reports Table -->
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
              Người báo cáo
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Lý do
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Trạng thái
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Ngày tạo
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
            v-for="report in reports"
            :key="report.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ report.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">
                {{ report.reporter?.email || report.reporterId || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-subtext-light dark:text-subtext-dark max-w-md truncate">
                {{ report.reason || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="getStatusClass(report.status)"
                class="px-2 py-1 text-xs font-medium rounded-full"
              >
                {{ formatStatus(report.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light">
              {{ formatDate(report.createdAt || report.createdDate) }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <button
                @click="viewReport(report)"
                class="text-primary hover:text-primary/80"
              >
                Xem chi tiết
              </button>
            </td>
          </tr>
          <tr v-if="reports.length === 0">
            <td colspan="6" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
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
const reports = ref([]);
const filters = ref({
  status: "",
});

const formatStatus = (status) => {
  const statusMap = {
    PENDING: "Chờ xử lý",
    RESOLVED: "Đã xử lý",
    REJECTED: "Từ chối",
  };
  return statusMap[status] || status || "N/A";
};

const getStatusClass = (status) => {
  const classMap = {
    PENDING: "bg-yellow-100 text-yellow-600",
    RESOLVED: "bg-positive/10 text-positive",
    REJECTED: "bg-red-100 text-red-600",
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

const fetchReports = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {};
    if (filters.value.status) {
      params.status = filters.value.status;
    }
    const response = await adminApi.getReports(params);
    const data = response.data || response;
    reports.value = data.content || data.data || [];
  } catch (err) {
    console.error("Error fetching reports:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách báo cáo";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  fetchReports();
};

const resetFilters = () => {
  filters.value = {
    status: "",
  };
  handleSearch();
};

const viewReport = (report) => {
  // Navigate to report detail or show modal
  alert(`Chi tiết báo cáo #${report.id}\nLý do: ${report.reason || "N/A"}`);
};

onMounted(() => {
  fetchReports();
});
</script>
