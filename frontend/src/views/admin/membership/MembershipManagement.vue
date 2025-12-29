<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý gói thành viên</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm gói thành viên
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Membership Plans Grid -->
    <div
      v-if="!isLoading && !error"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
    >
      <div
        v-for="plan in membershipPlans"
        :key="plan.id"
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark hover:shadow-lg transition-shadow"
      >
        <div class="flex items-start justify-between mb-4">
          <h3 class="text-xl font-bold">{{ plan.name }}</h3>
          <span
            class="px-3 py-1 text-sm font-medium rounded-full"
            :class="plan.type === 'USER' ? 'bg-blue-100 text-blue-800' : 'bg-purple-100 text-purple-800'"
          >
            {{ plan.type === "USER" ? "Người dùng" : "Chủ quán" }}
          </span>
        </div>
        <div class="mb-4">
          <p class="text-3xl font-bold text-primary mb-1">
            {{ formatCurrency(plan.price) }}
          </p>
          <p class="text-sm text-subtext-light dark:text-subtext-dark">
            / {{ plan.durationMonths }} tháng
          </p>
        </div>
        <div class="mb-4">
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-2">
            {{ plan.description || "Không có mô tả" }}
          </p>
          <div class="flex items-center gap-2 text-xs">
            <span class="px-2 py-1 bg-gray-100 dark:bg-gray-800 rounded">
              {{ plan.type === "USER" ? "Người dùng" : "Chủ quán" }}
            </span>
            <span v-if="plan.dishLimit" class="px-2 py-1 bg-gray-100 dark:bg-gray-800 rounded">
              Giới hạn: {{ plan.dishLimit }} món
            </span>
          </div>
        </div>
        <div class="flex gap-2">
          <button
            @click="editPlan(plan)"
            class="flex-1 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
          >
            Sửa
          </button>
          <button
            @click="deletePlan(plan)"
            class="flex-1 px-4 py-2 bg-red-100 text-red-800 rounded-lg hover:bg-red-200"
          >
            Xóa
          </button>
        </div>
      </div>
      <div v-if="membershipPlans.length === 0" class="col-span-full text-center py-8 text-subtext-light">
        Không có dữ liệu
      </div>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingPlan"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark max-h-[90vh] overflow-y-auto"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingPlan ? "Sửa gói thành viên" : "Thêm gói thành viên mới" }}
        </h2>
        <form @submit.prevent="savePlan" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên gói</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên gói thành viên"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Giá (VND)</label>
            <input
              v-model.number="form.price"
              type="number"
              required
              min="0"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập giá"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Thời hạn (tháng)</label>
            <input
              v-model.number="form.durationMonths"
              type="number"
              required
              min="1"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập số tháng"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Mô tả</label>
            <textarea
              v-model="form.description"
              rows="3"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập mô tả"
            ></textarea>
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Loại gói</label>
            <select
              v-model="form.type"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            >
              <option value="USER">Người dùng</option>
              <option value="OWNER">Chủ quán</option>
            </select>
          </div>
          <div v-if="form.type === 'OWNER'">
            <label class="block text-sm font-medium mb-2">Giới hạn món ăn</label>
            <input
              v-model.number="form.dishLimit"
              type="number"
              min="0"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Để trống = không giới hạn"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Tính năng (JSON hoặc text)</label>
            <textarea
              v-model="form.features"
              rows="3"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder='VD: ["basic-listing", "priority-email"] hoặc text mô tả'
            ></textarea>
          </div>
          <div class="flex gap-2 justify-end">
            <button
              type="button"
              @click="closeModal"
              class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
            >
              Hủy
            </button>
            <button
              type="submit"
              class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
            >
              {{ editingPlan ? "Cập nhật" : "Tạo mới" }}
            </button>
          </div>
        </form>
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
const membershipPlans = ref([]);
const showCreateModal = ref(false);
const editingPlan = ref(null);
const form = ref({
  name: "",
  price: 0,
  durationMonths: 1,
  description: "",
  dishLimit: null,
  features: "",
  type: "USER",
});

const formatCurrency = (amount) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

const fetchMembershipPlans = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await adminApi.getMembershipPlans({page: 0, size: 100});
    // Axios interceptor đã xử lý response, trả về Spring Data Page object hoặc data
    let allPlans = [];
    if (response?.content && Array.isArray(response.content)) {
      // Spring Data Page format: { content: [...], totalElements, totalPages, ... }
      allPlans = response.content;
    } else if (Array.isArray(response)) {
      // Direct array
      allPlans = response;
    } else if (response?.data && Array.isArray(response.data)) {
      // Wrapped in data field
      allPlans = response.data;
    }
    membershipPlans.value = allPlans || [];
  } catch (err) {
    console.error("Error fetching membership plans:", err);
    error.value = err.message || err.response?.data?.message || "Không thể tải danh sách gói thành viên";
    showError(error.value);
  } finally {
    isLoading.value = false;
  }
};

const editPlan = (plan) => {
  editingPlan.value = plan;
  form.value = {
    name: plan.name || "",
    price: plan.price || 0,
    durationMonths: plan.durationMonths || 1,
    description: plan.description || "",
    dishLimit: plan.dishLimit || null,
    features: plan.features || "",
    type: plan.type || "USER",
  };
  showCreateModal.value = true;
};

const deletePlan = async (plan) => {
  const confirmed = await confirm(
    "Xác nhận xóa",
    `Bạn có chắc muốn xóa gói thành viên "${plan.name}"?`
  );
  if (!confirmed) return;

  try {
    await adminApi.deleteMembershipPlan(plan.id);
    success("Xóa gói thành viên thành công");
    await fetchMembershipPlans();
  } catch (err) {
    const errorMsg = err.response?.data?.message || "Có lỗi xảy ra khi xóa gói thành viên";
    showError(errorMsg);
  }
};

const savePlan = async () => {
  try {
    // Prepare data for backend
    const data = {
      name: form.value.name,
      description: form.value.description || null,
      price: form.value.price,
      durationMonths: form.value.durationMonths,
      dishLimit: form.value.type === "OWNER" ? (form.value.dishLimit || null) : null,
      features: form.value.features || null,
      type: form.value.type,
    };

    if (editingPlan.value) {
      await adminApi.updateMembershipPlan(editingPlan.value.id, data);
      success("Cập nhật gói thành viên thành công");
    } else {
      await adminApi.createMembershipPlan(data);
      success("Tạo gói thành viên thành công");
    }
    closeModal();
    await fetchMembershipPlans();
  } catch (err) {
    const errorMsg =
      err.response?.data?.message || err.message || "Có lỗi xảy ra khi lưu gói thành viên";
    showError(errorMsg);
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingPlan.value = null;
  form.value = {
    name: "",
    price: 0,
    durationMonths: 1,
    description: "",
    dishLimit: null,
    features: "",
    type: "USER",
  };
};

onMounted(() => {
  fetchMembershipPlans();
});
</script>
