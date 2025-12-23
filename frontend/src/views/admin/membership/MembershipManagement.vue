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
            :class="plan.isActive ? 'bg-positive/10 text-positive' : 'bg-gray-100 text-gray-600'"
          >
            {{ plan.isActive ? "Hoạt động" : "Tạm dừng" }}
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
        <div class="mb-4 space-y-2">
          <div
            v-for="benefit in plan.benefits"
            :key="benefit"
            class="flex items-center gap-2 text-sm"
          >
            <span class="material-symbols-outlined text-positive text-lg"
              >check_circle</span
            >
            <span>{{ benefit }}</span>
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
            <label class="flex items-center gap-2">
              <input
                v-model="form.isActive"
                type="checkbox"
                class="rounded"
              />
              <span class="text-sm">Hoạt động</span>
            </label>
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
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

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
  isActive: true,
  benefits: [],
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
    const data = response.data || response;
    membershipPlans.value = data.content || data.data || [];
  } catch (err) {
    console.error("Error fetching membership plans:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách gói thành viên";
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
    isActive: plan.isActive !== false,
    benefits: plan.benefits || [],
  };
};

const deletePlan = async (plan) => {
  if (!confirm(`Bạn có chắc muốn xóa gói thành viên "${plan.name}"?`)) {
    return;
  }

  try {
    await adminApi.deleteMembershipPlan(plan.id);
    await fetchMembershipPlans();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const savePlan = async () => {
  try {
    if (editingPlan.value) {
      await adminApi.updateMembershipPlan(editingPlan.value.id, form.value);
    } else {
      await adminApi.createMembershipPlan(form.value);
    }
    closeModal();
    await fetchMembershipPlans();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
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
    isActive: true,
    benefits: [],
  };
};

onMounted(() => {
  fetchMembershipPlans();
});
</script>
