<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý danh mục</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm danh mục
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Categories Table -->
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
              Tên danh mục
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Loại outlet
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
            v-for="category in categories"
            :key="category.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ category.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ category.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ category.typeName || "N/A" }}
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ category.description || "N/A" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editCategory(category)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteCategory(category)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="categories.length === 0">
            <td colspan="5" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingCategory"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingCategory ? "Sửa danh mục" : "Thêm danh mục mới" }}
        </h2>
        <form @submit.prevent="saveCategory" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên danh mục *</label>
            <input
              v-model="form.name"
              type="text"
              required
              maxlength="50"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên danh mục"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Loại outlet *</label>
            <select
              v-model="form.typeId"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            >
              <option value="">Chọn loại outlet</option>
              <option
                v-for="type in outletTypes"
                :key="type.id"
                :value="type.id"
              >
                {{ type.name }}
              </option>
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Mô tả</label>
            <textarea
              v-model="form.description"
              rows="3"
              maxlength="255"
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
              {{ editingCategory ? "Cập nhật" : "Tạo mới" }}
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
const categories = ref([]);
const outletTypes = ref([]);
const showCreateModal = ref(false);
const editingCategory = ref(null);
const form = ref({
  name: "",
  description: "",
  typeId: "",
});

const fetchOutletTypes = async () => {
  try {
    const response = await adminApi.getOutletTypes();
    const data = response?.data || response;
    outletTypes.value = Array.isArray(data) ? data : [];
  } catch (err) {
    console.warn("Could not fetch outlet types:", err);
  }
};

const fetchCategories = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await adminApi.getCategories();
    // Ensure categories is always an array
    const data = response?.data || response;
    categories.value = Array.isArray(data) ? data : [];
  } catch (err) {
    console.error("Error fetching categories:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách danh mục";
    categories.value = []; // Ensure empty array on error
  } finally {
    isLoading.value = false;
  }
};

const editCategory = (category) => {
  editingCategory.value = category;
  form.value = {
    name: category.name || "",
    description: category.description || "",
    typeId: category.typeId || "",
  };
};

const deleteCategory = async (category) => {
  const confirmed = await confirm(`Bạn có chắc muốn xóa danh mục "${category.name}"?`);
  if (!confirmed) return;

  try {
    await adminApi.deleteCategory(category.id);
    success("Xóa danh mục thành công");
    await fetchCategories();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveCategory = async () => {
  try {
    const data = {
      name: form.value.name,
      description: form.value.description || null,
      typeId: parseInt(form.value.typeId),
    };

    if (editingCategory.value) {
      await adminApi.updateCategory(editingCategory.value.id, data);
      success("Cập nhật danh mục thành công");
    } else {
      await adminApi.createCategory(data);
      success("Tạo danh mục mới thành công");
    }
    closeModal();
    await fetchCategories();
  } catch (err) {
    console.error("Error saving category:", err);
    showError(err.response?.data?.message || err.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingCategory.value = null;
  form.value = {
    name: "",
    description: "",
    typeId: "",
  };
};

onMounted(() => {
  fetchOutletTypes();
  fetchCategories();
});
</script>
