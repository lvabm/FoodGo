<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý menu</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm món ăn
      </button>
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
            placeholder="Tên món ăn..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
            @keyup.enter="handleSearch"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Danh mục con</label>
          <select
            v-model="filters.subCategoryId"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả danh mục</option>
            <option
              v-for="subCat in subCategories"
              :key="subCat.id"
              :value="subCat.id"
            >
              {{ subCat.name }}
            </option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Tỉnh/Thành</label>
          <select
            v-model="filters.provinceId"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả tỉnh/thành</option>
            <option
              v-for="province in provinces"
              :key="province.id"
              :value="province.id"
            >
              {{ province.name }}
            </option>
          </select>
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

    <!-- Menu Items Table -->
    <div
      v-if="!isLoading && !error && menuItems.length > 0"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden"
    >
      <table class="w-full">
        <thead class="bg-gray-50 dark:bg-surface-light/5">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Tên món
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Danh mục con
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Tỉnh/Thành
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Phổ biến
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
            v-for="item in menuItems"
            :key="item.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4">
              <div class="text-sm font-medium">{{ item.name }}</div>
              <div
                v-if="item.description"
                class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-2 mt-1"
              >
                {{ item.description }}
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ item.subCategoryName || "N/A" }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ item.provinceName || "N/A" }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <button
                @click="togglePopular(item)"
                :class="
                  item.isPopular
                    ? 'px-2 py-1 text-xs font-medium rounded-full bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-300'
                    : 'px-2 py-1 text-xs font-medium rounded-full bg-gray-100 text-gray-600 dark:bg-gray-800 dark:text-gray-400'
                "
              >
                {{ item.isPopular ? "Phổ biến" : "Bình thường" }}
              </button>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editMenuItem(item)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteMenuItem(item)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Empty State -->
    <div
      v-if="!isLoading && !error && menuItems.length === 0"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark text-center py-12"
    >
      <span class="material-symbols-outlined text-6xl text-gray-400 mb-4">restaurant</span>
      <p class="text-lg font-medium text-subtext-light dark:text-subtext-dark mb-2">
        Không có món ăn nào
      </p>
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        {{ filters.search || filters.subCategoryId || filters.provinceId ? "Thử thay đổi bộ lọc hoặc reset" : "Hãy thêm món ăn đầu tiên" }}
      </p>
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

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingItem"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-2xl border border-border-light dark:border-border-dark max-h-[90vh] overflow-y-auto"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingItem ? "Sửa món ăn" : "Thêm món ăn mới" }}
        </h2>
        <form @submit.prevent="saveMenuItem" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên món ăn *</label>
            <input
              v-model="form.name"
              type="text"
              required
              maxlength="255"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên món ăn"
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
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Danh mục con *</label>
              <select
                v-model="form.subCategoryId"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn danh mục con</option>
                <option
                  v-for="subCat in subCategories"
                  :key="subCat.id"
                  :value="subCat.id"
                >
                  {{ subCat.name }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Tỉnh/Thành *</label>
              <select
                v-model="form.provinceId"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn tỉnh/thành</option>
                <option
                  v-for="province in provinces"
                  :key="province.id"
                  :value="province.id"
                >
                  {{ province.name }}
                </option>
              </select>
            </div>
          </div>
          <div>
            <label class="flex items-center gap-2">
              <input
                v-model="form.isPopular"
                type="checkbox"
                class="rounded"
              />
              <span class="text-sm">Đánh dấu là món phổ biến</span>
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
              {{ editingItem ? "Cập nhật" : "Tạo mới" }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {adminApi, menuApi} from "@/api";
import {useToast} from "@/composables/useToast";
import {useConfirm} from "@/composables/useConfirm";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const {success, error: showError} = useToast();
const {confirm} = useConfirm();

const isLoading = ref(false);
const error = ref(null);
const menuItems = ref([]);
const subCategories = ref([]);
const provinces = ref([]);
const filters = ref({
  search: "",
  subCategoryId: "",
  provinceId: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 10,
  totalElements: 0,
  totalPages: 0,
});
const showCreateModal = ref(false);
const editingItem = ref(null);
const form = ref({
  name: "",
  description: "",
  subCategoryId: "",
  provinceId: "",
  isPopular: false,
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

const fetchSubCategories = async () => {
  try {
    const response = await menuApi.getMenuSubCategories();
    subCategories.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch sub categories:", err);
  }
};

const fetchProvinces = async () => {
  try {
    const response = await menuApi.getProvinces();
    provinces.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch provinces:", err);
  }
};

const fetchMenuItems = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.pageSize,
    };

    if (filters.value.search) {
      params.name = filters.value.search;
    }
    if (filters.value.subCategoryId) {
      params.subCategoryId = filters.value.subCategoryId;
    }
    if (filters.value.provinceId) {
      params.provinceId = filters.value.provinceId;
    }

    const response = await adminApi.getMenuItems(params);
    
    // Handle PageResponse structure: { data: Array, totalElements, totalPages, ... }
    let pageData = response;
    let allMenuItems = [];
    
    if (response && typeof response === 'object' && !Array.isArray(response)) {
      if (response.data && Array.isArray(response.data)) {
        allMenuItems = response.data;
        pageData = response;
      } else if (response.content && Array.isArray(response.content)) {
        allMenuItems = response.content;
        pageData = response;
      }
    } else if (Array.isArray(response)) {
      allMenuItems = response;
      pageData = { totalElements: response.length, totalPages: 1 };
    }

    // Ensure menuItems is always an array
    menuItems.value = Array.isArray(allMenuItems) ? allMenuItems : [];
    
    // Update pagination
    if (pageData && typeof pageData === 'object' && !Array.isArray(pageData)) {
      if (pageData.totalElements !== undefined) {
        pagination.value.totalElements = pageData.totalElements;
        pagination.value.totalPages = pageData.totalPages || Math.ceil(pageData.totalElements / pagination.value.pageSize);
        pagination.value.currentPage = pageData.pageNumber !== undefined ? pageData.pageNumber : pagination.value.currentPage;
      }
    }
  } catch (err) {
    console.error("Error fetching menu items:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách món ăn";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchMenuItems();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    subCategoryId: "",
    provinceId: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchMenuItems();
  }
};

const togglePopular = async (item) => {
  try {
    await adminApi.updateMenuItem(item.id, {
      isPopular: !item.isPopular,
    });
    success(`${!item.isPopular ? "Đánh dấu" : "Bỏ đánh dấu"} phổ biến thành công`);
    await fetchMenuItems();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const editMenuItem = (item) => {
  editingItem.value = item;
  form.value = {
    name: item.name || "",
    description: item.description || "",
    subCategoryId: item.subCategoryId || "",
    provinceId: item.provinceId || "",
    isPopular: item.isPopular || false,
  };
};

const deleteMenuItem = async (item) => {
  const confirmed = await confirm(`Bạn có chắc muốn xóa món ăn "${item.name}"?`);
  if (!confirmed) return;

  try {
    await adminApi.deleteMenuItem(item.id);
    success("Xóa món ăn thành công");
    await fetchMenuItems();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveMenuItem = async () => {
  try {
    const data = {
      name: form.value.name,
      description: form.value.description || null,
      subCategoryId: parseInt(form.value.subCategoryId),
      provinceId: parseInt(form.value.provinceId),
    };

    if (editingItem.value) {
      // Update: include isPopular if changed
      const updateData = {
        ...data,
        isPopular: form.value.isPopular,
      };
      await adminApi.updateMenuItem(editingItem.value.id, updateData);
      success("Cập nhật món ăn thành công");
    } else {
      // Create: isPopular is set to false by default in backend
      await adminApi.createMenuItem(data);
      success("Tạo món ăn mới thành công");
    }
    closeModal();
    await fetchMenuItems();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingItem.value = null;
  form.value = {
    name: "",
    description: "",
    subCategoryId: "",
    provinceId: "",
    isPopular: false,
  };
};

onMounted(() => {
  fetchSubCategories();
  fetchProvinces();
  fetchMenuItems();
});
</script>
