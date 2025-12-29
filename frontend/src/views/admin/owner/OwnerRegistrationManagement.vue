<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Quản lý yêu cầu đăng ký Owner</h1>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="flex gap-4">
        <button
          @click="filterStatus = 'PENDING'"
          :class="filterStatus === 'PENDING' ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800'"
          class="px-4 py-2 rounded-lg hover:bg-opacity-90"
        >
          Chờ duyệt
        </button>
        <button
          @click="filterStatus = 'APPROVED'"
          :class="filterStatus === 'APPROVED' ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800'"
          class="px-4 py-2 rounded-lg hover:bg-opacity-90"
        >
          Đã duyệt
        </button>
        <button
          @click="filterStatus = 'REJECTED'"
          :class="filterStatus === 'REJECTED' ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800'"
          class="px-4 py-2 rounded-lg hover:bg-opacity-90"
        >
          Đã từ chối
        </button>
        <button
          @click="filterStatus = null"
          :class="!filterStatus ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800'"
          class="px-4 py-2 rounded-lg hover:bg-opacity-90"
        >
          Tất cả
        </button>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Requests Table -->
    <div
      v-if="!isLoading && !error"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden"
    >
      <table class="w-full">
        <thead class="bg-gray-50 dark:bg-surface-light/5">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase">
              User
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase">
              Outlet
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase">
              Trạng thái
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase">
              Ngày tạo
            </th>
            <th class="px-6 py-3 text-right text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase">
              Thao tác
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-border-light dark:divide-border-dark">
          <tr
            v-for="request in filteredRequests"
            :key="request.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">
                {{ request.user?.fullName || request.user?.email || "N/A" }}
              </div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm font-medium">{{ request.outlet?.name || "N/A" }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="getStatusClass(request.status)"
                class="px-2 py-1 text-xs font-medium rounded-full"
              >
                {{ formatStatus(request.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light">
              {{ formatDate(request.createdAt) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
              <button
                @click="viewRequest(request)"
                class="text-primary hover:text-primary/80 mr-2"
              >
                Chi tiết
              </button>
              <button
                v-if="request.status === 'PENDING'"
                @click="handleApprove(request)"
                class="text-positive hover:text-positive/80"
              >
                Duyệt
              </button>
              <button
                v-if="request.status === 'PENDING'"
                @click="handleReject(request)"
                class="text-red-600 hover:text-red-700"
              >
                Từ chối
              </button>
            </td>
          </tr>
          <tr v-if="filteredRequests.length === 0">
            <td colspan="5" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
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
          <h2 class="text-xl font-bold">Chi tiết yêu cầu #{{ selectedRequest?.id?.substring(0, 8) }}</h2>
          <button @click="closeDetailModal" class="text-subtext-light hover:text-text-light">✕</button>
        </div>

        <div v-if="selectedRequest" class="space-y-4">
          <div>
            <label class="text-sm font-medium text-subtext-light">User</label>
            <p class="text-base">{{ selectedRequest.user?.fullName || selectedRequest.user?.email || "N/A" }}</p>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Outlet</label>
            <p class="text-base">{{ selectedRequest.outlet?.name || "N/A" }}</p>
            <p class="text-sm text-subtext-light">{{ selectedRequest.outlet?.address || "" }}</p>
          </div>

          <div>
            <label class="text-sm font-medium text-subtext-light">Trạng thái</label>
            <span
              :class="getStatusClass(selectedRequest.status)"
              class="px-2 py-1 text-xs font-medium rounded-full"
            >
              {{ formatStatus(selectedRequest.status) }}
            </span>
          </div>

          <div v-if="selectedRequest.adminNotes">
            <label class="text-sm font-medium text-subtext-light">Ghi chú Admin</label>
            <p class="text-base">{{ selectedRequest.adminNotes }}</p>
          </div>

          <div v-if="selectedRequest.reviewedBy">
            <label class="text-sm font-medium text-subtext-light">Người duyệt</label>
            <p class="text-base">{{ selectedRequest.reviewedBy?.fullName || selectedRequest.reviewedBy?.email || "N/A" }}</p>
          </div>

          <div v-if="selectedRequest.reviewedAt">
            <label class="text-sm font-medium text-subtext-light">Ngày duyệt</label>
            <p class="text-base">{{ formatDate(selectedRequest.reviewedAt) }}</p>
          </div>

          <div
            v-if="selectedRequest.status === 'PENDING'"
            class="flex gap-2 pt-4 border-t border-border-light dark:border-border-dark"
          >
            <button
              @click="handleApprove(selectedRequest)"
              class="px-4 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90"
            >
              Duyệt yêu cầu
            </button>
            <button
              @click="handleReject(selectedRequest)"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Từ chối yêu cầu
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Reject Modal -->
    <div
      v-if="showRejectModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @click.self="showRejectModal = false"
    >
      <div class="bg-white dark:bg-surface-dark rounded-xl p-6 max-w-md w-full mx-4">
        <h3 class="text-lg font-bold mb-4">Từ chối yêu cầu</h3>
        <div class="mb-4">
          <label class="block text-sm font-medium mb-2">Lý do từ chối *</label>
          <textarea
            v-model="rejectReason"
            rows="4"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            placeholder="Nhập lý do từ chối..."
          ></textarea>
        </div>
        <div class="flex gap-2 justify-end">
          <button
            @click="showRejectModal = false"
            class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            Hủy
          </button>
          <button
            @click="confirmReject"
            :disabled="!rejectReason || rejectReason.trim() === ''"
            class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90 disabled:opacity-50"
          >
            Xác nhận từ chối
          </button>
        </div>
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
const requests = ref([]);
const filterStatus = ref("PENDING");
const showDetailModal = ref(false);
const showRejectModal = ref(false);
const selectedRequest = ref(null);
const rejectReason = ref("");

const filteredRequests = computed(() => {
  if (!filterStatus.value) return requests.value;
  return requests.value.filter((r) => r.status === filterStatus.value);
});

const formatStatus = (status) => {
  const statusMap = {
    PENDING: "Chờ duyệt",
    APPROVED: "Đã duyệt",
    REJECTED: "Đã từ chối",
  };
  return statusMap[status] || status || "N/A";
};

const getStatusClass = (status) => {
  const classMap = {
    PENDING: "bg-yellow-100 text-yellow-600 dark:bg-yellow-900/20 dark:text-yellow-400",
    APPROVED: "bg-positive/10 text-positive",
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

const fetchRequests = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await adminApi.getOwnerRegistrationRequests();
    requests.value = Array.isArray(response) ? response : [];
  } catch (err) {
    console.error("Error fetching owner registration requests:", err);
    error.value = err.message || "Không thể tải danh sách yêu cầu";
    showError(error.value);
    requests.value = [];
  } finally {
    isLoading.value = false;
  }
};

const viewRequest = (request) => {
  selectedRequest.value = request;
  showDetailModal.value = true;
};

const closeDetailModal = () => {
  showDetailModal.value = false;
  selectedRequest.value = null;
};

const handleApprove = async (request) => {
  const confirmed = await confirm(
    "Xác nhận duyệt",
    `Bạn có chắc muốn duyệt yêu cầu đăng ký owner của ${request.user?.fullName || request.user?.email || "user"}?`
  );
  if (!confirmed) return;

  try {
    await adminApi.approveOwnerRegistration(request.id);
    success("Đã duyệt yêu cầu thành công");
    closeDetailModal();
    await fetchRequests();
  } catch (err) {
    const errorMsg = err.message || "Không thể duyệt yêu cầu";
    showError(errorMsg);
  }
};

const handleReject = (request) => {
  selectedRequest.value = request;
  rejectReason.value = "";
  showRejectModal.value = true;
};

const confirmReject = async () => {
  if (!rejectReason.value || rejectReason.value.trim() === "") {
    showError("Vui lòng nhập lý do từ chối");
    return;
  }

  try {
    await adminApi.rejectOwnerRegistration(selectedRequest.value.id, rejectReason.value);
    success("Đã từ chối yêu cầu thành công");
    showRejectModal.value = false;
    closeDetailModal();
    await fetchRequests();
  } catch (err) {
    const errorMsg = err.message || "Không thể từ chối yêu cầu";
    showError(errorMsg);
  }
};

onMounted(() => {
  fetchRequests();
});
</script>

