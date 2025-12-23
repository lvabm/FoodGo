<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý loại địa điểm</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm loại địa điểm
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Outlet Types Table -->
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
              Tên loại
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Mô tả
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
            v-for="type in outletTypes"
            :key="type.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ type.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ type.name }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ type.description || "N/A" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editType(type)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteType(type)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="outletTypes.length === 0">
            <td colspan="4" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingType"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingType ? "Sửa loại địa điểm" : "Thêm loại địa điểm mới" }}
        </h2>
        <form @submit.prevent="saveType" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên loại</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên loại địa điểm"
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
              {{ editingType ? "Cập nhật" : "Tạo mới" }}
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
const outletTypes = ref([]);
const showCreateModal = ref(false);
const editingType = ref(null);
const form = ref({
  name: "",
  description: "",
});

const fetchOutletTypes = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await adminApi.getOutletTypes();
    outletTypes.value = response.data || response || [];
  } catch (err) {
    console.error("Error fetching outlet types:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách loại địa điểm";
  } finally {
    isLoading.value = false;
  }
};

const editType = (type) => {
  editingType.value = type;
  form.value = {
    name: type.name || "",
    description: type.description || "",
  };
};

const deleteType = async (type) => {
  if (!confirm(`Bạn có chắc muốn xóa loại địa điểm "${type.name}"?`)) {
    return;
  }

  try {
    await adminApi.deleteOutletType(type.id);
    await fetchOutletTypes();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveType = async () => {
  try {
    if (editingType.value) {
      await adminApi.updateOutletType(editingType.value.id, form.value);
    } else {
      await adminApi.createOutletType(form.value);
    }
    closeModal();
    await fetchOutletTypes();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingType.value = null;
  form.value = {
    name: "",
    description: "",
  };
};

onMounted(() => {
  fetchOutletTypes();
});
</script>
