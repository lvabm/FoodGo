<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý tính năng</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm tính năng
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Features Table -->
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
              Tên tính năng
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Icon
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
            v-for="feature in features"
            :key="feature.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ feature.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ feature.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span class="material-symbols-outlined">{{ feature.icon || "star" }}</span>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editFeature(feature)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteFeature(feature)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="features.length === 0">
            <td colspan="4" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingFeature"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingFeature ? "Sửa tính năng" : "Thêm tính năng mới" }}
        </h2>
        <form @submit.prevent="saveFeature" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên tính năng</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên tính năng"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Icon (Material Symbol)</label>
            <input
              v-model="form.icon"
              type="text"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="wifi, parking, etc."
            />
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
              {{ editingFeature ? "Cập nhật" : "Tạo mới" }}
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
const features = ref([]);
const showCreateModal = ref(false);
const editingFeature = ref(null);
const form = ref({
  name: "",
  icon: "",
});

const fetchFeatures = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await adminApi.getFeatures();
    features.value = response.data || response || [];
  } catch (err) {
    console.error("Error fetching features:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách tính năng";
  } finally {
    isLoading.value = false;
  }
};

const editFeature = (feature) => {
  editingFeature.value = feature;
  form.value = {
    name: feature.name || "",
    icon: feature.icon || "",
  };
};

const deleteFeature = async (feature) => {
  if (!confirm(`Bạn có chắc muốn xóa tính năng "${feature.name}"?`)) {
    return;
  }

  try {
    await adminApi.deleteFeature(feature.id);
    await fetchFeatures();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveFeature = async () => {
  try {
    if (editingFeature.value) {
      await adminApi.updateFeature(editingFeature.value.id, form.value);
    } else {
      await adminApi.createFeature(form.value);
    }
    closeModal();
    await fetchFeatures();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingFeature.value = null;
  form.value = {
    name: "",
    icon: "",
  };
};

onMounted(() => {
  fetchFeatures();
});
</script>
