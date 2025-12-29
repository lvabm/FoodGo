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
                {{ report.reporterName || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-subtext-light dark:text-subtext-dark max-w-md truncate">
                {{ formatReason(report.reason) || "N/A" }}
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
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2"
            >
              <button
                @click="viewReport(report)"
                class="text-primary hover:text-primary/80 mr-2"
              >
                Chi tiết
              </button>
              <button
                v-if="report.status === 'PENDING'"
                @click="handleUpdateStatus(report, 'RESOLVED')"
                class="text-positive hover:text-positive/80"
              >
                Duyệt
              </button>
              <button
                v-if="report.status === 'PENDING'"
                @click="handleUpdateStatus(report, 'REJECTED')"
                class="text-red-600 hover:text-red-700"
              >
                Từ chối
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

      <!-- Pagination -->
      <div
        v-if="pagination.totalPages > 1"
        class="px-6 py-4 flex items-center justify-between border-t border-border-light dark:border-border-dark"
      >
        <div class="text-sm text-subtext-light dark:text-subtext-dark">
          Hiển thị {{ reports.length }} / {{ pagination.totalElements }} báo cáo
        </div>
        <div class="flex gap-2">
          <button
            @click="goToPage(pagination.currentPage - 1)"
            :disabled="pagination.currentPage === 0"
            class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            Trước
          </button>
          <span class="px-4 py-2 text-sm">
            Trang {{ pagination.currentPage + 1 }} / {{ pagination.totalPages }}
          </span>
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

    <!-- Detail Modal -->
    <div
      v-if="showDetailModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @click.self="closeDetailModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 max-w-2xl w-full mx-4 max-h-[90vh] overflow-y-auto"
      >
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-xl font-bold">Chi tiết báo cáo #{{ selectedReport?.id }}</h2>
          <button
            @click="closeDetailModal"
            class="text-subtext-light hover:text-text-light"
          >
            ✕
          </button>
        </div>

        <div v-if="selectedReport" class="space-y-4">
          <div>
            <label class="text-sm font-medium text-subtext-light">Người báo cáo</label>
            <p class="text-base">{{ selectedReport.reporterName || "N/A" }}</p>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Lý do báo cáo</label>
            <p class="text-base">{{ formatReason(selectedReport.reason) || "N/A" }}</p>
          </div>

          <div v-if="selectedReport.description">
            <label class="text-sm font-medium text-subtext-light">Mô tả</label>
            <p class="text-base">{{ selectedReport.description }}</p>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Trạng thái</label>
            <span
              :class="getStatusClass(selectedReport.status)"
              class="px-2 py-1 text-xs font-medium rounded-full"
            >
              {{ formatStatus(selectedReport.status) }}
            </span>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Review ID</label>
            <p class="text-base">{{ selectedReport.reviewId || "N/A" }}</p>
          </div>

          <div v-if="selectedReport.reviewSummary">
            <label class="text-sm font-medium text-subtext-light">Nội dung review</label>
            <p class="text-base">{{ selectedReport.reviewSummary }}</p>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Ngày tạo</label>
            <p class="text-base">
              {{ formatDate(selectedReport.createdAt || selectedReport.createdDate) }}
            </p>
          </div>

          <div
            v-if="selectedReport.status === 'PENDING'"
            class="flex gap-2 pt-4 border-t border-border-light dark:border-border-dark"
          >
            <button
              @click="handleUpdateStatus(selectedReport, 'RESOLVED')"
              class="px-4 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90"
            >
              Duyệt báo cáo
            </button>
            <button
              @click="handleUpdateStatus(selectedReport, 'REJECTED')"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Từ chối báo cáo
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {adminApi} from "@/api";
import {useToast} from "@/composables/useToast";
import {useConfirm} from "@/composables/useConfirm";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const {success, error: showError} = useToast();
const {confirm} = useConfirm();

const isLoading = ref(false);
const error = ref(null);
const reports = ref([]);
const pagination = ref({
  currentPage: 0,
  totalPages: 0,
  totalElements: 0,
  size: 10,
});
const filters = ref({
  status: "",
});

const showDetailModal = ref(false);
const selectedReport = ref(null);

const formatStatus = (status) => {
  const statusMap = {
    PENDING: "Chờ xử lý",
    RESOLVED: "Đã xử lý",
    REJECTED: "Từ chối",
  };
  return statusMap[status] || status || "N/A";
};

const formatReason = (reason) => {
  const reasonMap = {
    SPAM: "Spam, quảng cáo",
    FAKE_CONTENT: "Nội dung giả mạo",
    INAPPROPRIATE: "Ngôn từ không phù hợp",
    OTHER: "Khác",
  };
  return reasonMap[reason] || reason || "N/A";
};

const getStatusClass = (status) => {
  const classMap = {
    PENDING: "bg-yellow-100 text-yellow-600 dark:bg-yellow-900/20 dark:text-yellow-400",
    RESOLVED: "bg-positive/10 text-positive",
    REJECTED: "bg-red-100 text-red-600 dark:bg-red-900/20 dark:text-red-400",
  };
  return classMap[status] || "bg-gray-100 text-gray-600";
};

const formatDate = (date) => {
  if (!date) return "N/A";
  try {
    return new Date(date).toLocaleDateString("vi-VN", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    });
  } catch {
    return date;
  }
};

const fetchReports = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.size,
    };
    if (filters.value.status) {
      params.status = filters.value.status;
    }
    const response = await adminApi.getReports(params);
    // Axios interceptor đã xử lý response, trả về Spring Data Page object
    let allReports = [];
    if (response?.content && Array.isArray(response.content)) {
      // Spring Data Page format: { content: [...], totalElements, totalPages, number, size, ... }
      allReports = response.content;
      pagination.value = {
        currentPage: response.number || 0,
        totalPages: response.totalPages || 0,
        totalElements: response.totalElements || 0,
        size: response.size || 10,
      };
    } else if (Array.isArray(response)) {
      // Direct array (fallback)
      allReports = response;
      pagination.value = {
        currentPage: 0,
        totalPages: 1,
        totalElements: response.length,
        size: response.length,
      };
    } else if (response?.data && Array.isArray(response.data)) {
      // Wrapped in data field (fallback)
      allReports = response.data;
      pagination.value = {
        currentPage: response.pageNumber || 0,
        totalPages: response.totalPages || 1,
        totalElements: response.totalElements || response.data.length,
        size: response.size || 10,
      };
    }
    reports.value = allReports || [];
  } catch (err) {
    console.error("Error fetching reports:", err);
    error.value = err.message || err.response?.data?.message || "Không thể tải danh sách báo cáo";
    showError(error.value);
    reports.value = [];
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchReports();
};

const resetFilters = () => {
  filters.value = {
    status: "",
  };
  handleSearch();
};

const viewReport = async (report) => {
  try {
    const detail = await adminApi.getReportDetail(report.id);
    selectedReport.value = detail;
    showDetailModal.value = true;
  } catch (err) {
    const errorMsg = err.response?.data?.message || "Không thể tải chi tiết báo cáo";
    showError(errorMsg);
  }
};

const closeDetailModal = () => {
  showDetailModal.value = false;
  selectedReport.value = null;
};

const handleUpdateStatus = async (report, newStatus) => {
  const statusText = formatStatus(newStatus);
  const confirmed = await confirm(
    `Xác nhận cập nhật trạng thái`,
    `Bạn có chắc chắn muốn ${statusText.toLowerCase()} báo cáo #${report.id}?`
  );
  if (!confirmed) return;

  try {
    await adminApi.updateReportStatus(report.id, newStatus);
    success(`Đã cập nhật trạng thái báo cáo thành ${statusText.toLowerCase()}`);
    closeDetailModal();
    fetchReports();
  } catch (err) {
    const errorMsg = err.response?.data?.message || "Không thể cập nhật trạng thái báo cáo";
    showError(errorMsg);
  }
};

const goToPage = (page) => {
  pagination.value.currentPage = page;
  fetchReports();
};

onMounted(() => {
  fetchReports();
});
</script>
