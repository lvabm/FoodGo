<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý địa điểm</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm địa điểm
      </button>
    </div>

    <!-- Stats -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Tổng địa điểm
        </p>
        <h3 class="text-2xl font-bold mt-1">{{ stats.total || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Đang chờ duyệt
        </p>
        <h3 class="text-2xl font-bold mt-1 text-yellow-600">{{ stats.pending || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">
          Hoạt động
        </p>
        <h3 class="text-2xl font-bold mt-1 text-positive">{{ stats.active || 0 }}</h3>
      </div>
      <div
        class="bg-white dark:bg-surface-dark rounded-lg p-4 border border-border-light dark:border-border-dark"
      >
        <p class="text-sm text-subtext-light dark:text-subtext-dark">Bị khóa</p>
        <h3 class="text-2xl font-bold mt-1 text-red-600">{{ stats.locked || 0 }}</h3>
      </div>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <input
          v-model="filters.search"
          type="text"
          placeholder="Tìm kiếm địa điểm..."
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
          @keyup.enter="handleSearch"
        />
        <select
          v-model="filters.category"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả danh mục</option>
          <option
            v-for="cat in categories"
            :key="cat.id"
            :value="cat.id"
          >
            {{ cat.name }}
          </option>
        </select>
        <select
          v-model="filters.status"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
        >
          <option value="">Tất cả trạng thái</option>
          <option value="active">Hoạt động</option>
          <option value="locked">Vô hiệu hóa</option>
        </select>
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
    <div v-if="error" class="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-xl p-4 mb-6">
      <div class="flex items-center gap-2">
        <span class="material-symbols-outlined text-red-600">error</span>
        <p class="text-red-600 dark:text-red-400 font-medium">{{ error }}</p>
      </div>
      <p class="text-sm text-red-500 dark:text-red-400 mt-2">Vui lòng kiểm tra console để xem chi tiết lỗi.</p>
    </div>

    <!-- Outlets Grid -->
    <div
      v-if="!isLoading && !error"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
    >
      <div
        v-for="outlet in outlets"
        :key="outlet.id"
        class="bg-white dark:bg-surface-dark rounded-xl overflow-hidden border border-border-light dark:border-border-dark hover:shadow-lg transition-shadow"
      >
        <div class="h-48 relative">
          <ImageDisplay
            :image-url="getOutletImageUrl(outlet)"
            :alt="outlet.name"
            placeholder-icon="storefront"
            container-class="h-full w-full"
            image-class="w-full h-full"
          />
        </div>
        <div class="p-4">
          <div class="flex items-start justify-between mb-2">
            <h3 class="font-bold text-lg">{{ outlet.name }}</h3>
            <span
              :class="getStatusClass(outlet.isActive)"
              class="px-2 py-1 text-xs font-medium rounded-full"
            >
              {{ getStatusText(outlet.isActive) }}
            </span>
          </div>
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-3">
            {{ outlet.address }}
          </p>
          <div class="flex items-center justify-between mb-3">
            <div class="flex items-center gap-1">
              <span
                class="material-symbols-outlined fill text-yellow-500 text-lg"
                >star</span
              >
              <span class="font-medium">{{ outlet.averageRating || 0 }}</span>
              <span class="text-sm text-subtext-light dark:text-subtext-dark"
                >({{ outlet.totalReviews || 0 }})</span
              >
            </div>
          </div>
          <div class="flex gap-2">
            <router-link
              :to="`/admin/outlets/${outlet.id}`"
              class="flex-1 text-center px-3 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Chi tiết
            </router-link>
            <button
              @click="editOutlet(outlet)"
              class="flex-1 px-3 py-2 bg-blue-600 text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Sửa
            </button>
            <button
              v-if="outlet.isActive === false"
              @click="approveOutlet(outlet)"
              class="flex-1 px-3 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Kích hoạt
            </button>
            <button
              v-else
              @click="lockOutlet(outlet)"
              class="flex-1 px-3 py-2 bg-yellow-600 text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Vô hiệu hóa
            </button>
            <button
              @click="deleteOutlet(outlet)"
              class="px-3 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90 text-sm"
            >
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Empty State -->
    <div
      v-if="!isLoading && !error && outlets.length === 0"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark text-center py-12"
    >
      <span class="material-symbols-outlined text-6xl text-gray-400 mb-4">storefront</span>
      <p class="text-lg font-medium text-subtext-light dark:text-subtext-dark mb-2">
        Không có địa điểm nào
      </p>
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        {{ filters.search || filters.category || filters.status ? "Thử thay đổi bộ lọc" : "Hãy thêm địa điểm đầu tiên" }}
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
      v-if="showCreateModal || editingOutlet"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-4xl border border-border-light dark:border-border-dark max-h-[90vh] overflow-y-auto"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingOutlet ? "Sửa địa điểm" : "Thêm địa điểm mới" }}
        </h2>
        <form @submit.prevent="saveOutlet" class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Tên địa điểm *</label>
              <input
                v-model="form.name"
                type="text"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Nhập tên địa điểm"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Loại địa điểm *</label>
              <select
                v-model="form.typeId"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn loại địa điểm</option>
                <option
                  v-for="type in outletTypes"
                  :key="type.id"
                  :value="type.id"
                >
                  {{ type.name }}
                </option>
              </select>
            </div>
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
            <label class="block text-sm font-medium mb-2">Địa chỉ *</label>
            <input
              v-model="form.address"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập địa chỉ"
            />
          </div>
          <div class="grid grid-cols-3 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Tỉnh/Thành phố</label>
              <select
                v-model="form.provinceId"
                @change="onProvinceChange"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn tỉnh/thành phố</option>
                <option
                  v-for="province in provinces"
                  :key="province.id"
                  :value="province.id"
                >
                  {{ province.name }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Quận/Huyện *</label>
              <select
                v-model="form.districtId"
                required
                :disabled="!form.provinceId"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg disabled:opacity-50"
              >
                <option value="">Chọn quận/huyện</option>
                <option
                  v-for="district in districts"
                  :key="district.id"
                  :value="district.id"
                >
                  {{ district.name }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Sức chứa</label>
              <input
                v-model.number="form.capacity"
                type="number"
                min="0"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Số người"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Email</label>
              <input
                v-model="form.email"
                type="email"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="email@example.com"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Số điện thoại</label>
              <input
                v-model="form.phoneNumber"
                type="tel"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="0123456789"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Website</label>
              <input
                v-model="form.website"
                type="url"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="https://example.com"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Khoảng giá</label>
              <input
                v-model="form.priceRange"
                type="text"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Ví dụ: 100k-500k"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Vĩ độ (Latitude)</label>
              <input
                v-model.number="form.latitude"
                type="number"
                step="any"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="10.762622"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Kinh độ (Longitude)</label>
              <input
                v-model.number="form.longitude"
                type="number"
                step="any"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="106.660172"
              />
            </div>
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
              {{ editingOutlet ? "Cập nhật" : "Tạo mới" }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted, watch} from "vue";
import {adminApi, outletApi, locationApi} from "@/api";
import {useToast} from "@/composables/useToast";
import {useConfirm} from "@/composables/useConfirm";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const {success, error: showError} = useToast();
const {confirm} = useConfirm();

const isLoading = ref(false);
const error = ref(null);
const outlets = ref([]);
const categories = ref([]);
const outletTypes = ref([]);
const provinces = ref([]);
const districts = ref([]);
const stats = ref({
  total: 0,
  pending: 0,
  active: 0,
  locked: 0,
});
const filters = ref({
  search: "",
  category: "",
  status: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 12,
  totalElements: 0,
  totalPages: 0,
});
const showCreateModal = ref(false);
const editingOutlet = ref(null);
const form = ref({
  name: "",
  description: "",
  address: "",
  email: "",
  phoneNumber: "",
  website: "",
  latitude: null,
  longitude: null,
  priceRange: "",
  capacity: null,
  typeId: "",
  provinceId: "",
  districtId: "",
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

const getStatusClass = (isActive) => {
  if (isActive === false) {
    return "bg-red-100 text-red-600";
  }
  return "bg-positive/10 text-positive";
};

const getStatusText = (isActive) => {
  if (isActive === false) {
    return "Bị khóa";
  }
  return "Hoạt động";
};

const getOutletImageUrl = (outlet) => {
  if (!outlet.images || outlet.images.length === 0) {
    return null;
  }
  const firstImage = outlet.images[0];
  // Handle both string URLs and object with imageUrl property
  return typeof firstImage === "string" ? firstImage : firstImage.imageUrl || firstImage.url || null;
};

const fetchCategories = async () => {
  try {
    const response = await outletApi.getCategories();
    categories.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch categories:", err);
  }
};

const fetchOutletTypes = async () => {
  try {
    const response = await outletApi.getTypes();
    outletTypes.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch outlet types:", err);
  }
};

const fetchProvinces = async () => {
  try {
    const response = await locationApi.getProvinces();
    provinces.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch provinces:", err);
  }
};

const fetchDistricts = async (provinceId) => {
  if (!provinceId) {
    districts.value = [];
    return;
  }
  try {
    const response = await locationApi.getDistricts(provinceId);
    districts.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch districts:", err);
    districts.value = [];
  }
};

const onProvinceChange = () => {
  form.value.districtId = "";
  fetchDistricts(form.value.provinceId);
};

const fetchOutlets = async () => {
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
    if (filters.value.category) {
      params.categoryId = filters.value.category;
    }
    // Note: Backend OutletFilterRequest doesn't have isActive field
    // Backend filter defaults to isActive = true only
    // Filtering by status will be done on frontend after fetching
    // TODO: Add isActive to OutletFilterRequest in backend

    const response = await adminApi.getOutlets(params);
    
    // Backend PageResponse structure: 
    // { success, message, data: Array, pageNumber, pageSize, totalElements, totalPages, ... }
    // Spring Data Page structure: { content: Array, totalElements, totalPages, ... }
    
    let pageData = response;
    let allOutlets = [];
    
    // Check if response has data array (PageResponse format)
    if (response && typeof response === 'object' && !Array.isArray(response)) {
      if (response.data && Array.isArray(response.data)) {
        allOutlets = response.data;
        pageData = response;
      }
      // Check if response has content array (Spring Data Page format)
      else if (response.content && Array.isArray(response.content)) {
        allOutlets = response.content;
        pageData = response;
      }
    }
    // Fallback: if response is already an array (shouldn't happen)
    else if (Array.isArray(response)) {
      allOutlets = response;
      pageData = { totalElements: response.length, totalPages: 1 };
    }
    
    // Filter by status on frontend (since backend doesn't support isActive filter)
    let fetchedOutlets = allOutlets.length > 0 ? [...allOutlets] : []; // Create a copy
    
    const currentStatusFilter = filters.value.status;
    
    if (currentStatusFilter === "active") {
      // Show only active outlets (isActive === true or null/undefined which means active by default)
      fetchedOutlets = fetchedOutlets.filter((o) => {
        // Consider null/undefined as active (default state)
        return o.isActive === true || o.isActive === null || o.isActive === undefined;
      });
    } else if (currentStatusFilter === "locked") {
      // Show only locked outlets (isActive === false)
      fetchedOutlets = fetchedOutlets.filter((o) => o.isActive === false);
    }
    // else: No filter - show all outlets regardless of status
    
    // Ensure we're assigning a proper array (even if empty)
    outlets.value = Array.isArray(fetchedOutlets) ? fetchedOutlets : [];
    
    // Update pagination
    if (pageData.totalElements !== undefined) {
      pagination.value.totalElements = pageData.totalElements;
      pagination.value.totalPages = pageData.totalPages || Math.ceil(pageData.totalElements / pagination.value.pageSize);
    } else if (pageData.totalPages !== undefined) {
      pagination.value.totalPages = pageData.totalPages;
      pagination.value.totalElements = pageData.totalElements || (pageData.totalPages * pagination.value.pageSize);
    } else {
      // Fallback: use array length
      pagination.value.totalElements = allOutlets.length;
      pagination.value.totalPages = Math.ceil(allOutlets.length / pagination.value.pageSize);
    }

    // Calculate stats - Note: Backend may not have isLocked field
    // Using isActive to determine status
    // Stats are calculated from all outlets, not just filtered ones
    const activeOutlets = allOutlets.filter((o) => o.isActive !== false && o.isActive !== null);
    const inactiveOutlets = allOutlets.filter((o) => o.isActive === false);
    
    stats.value = {
      total: pageData.totalElements || allOutlets.length || 0,
      pending: 0, // Backend doesn't have pending status, using isActive instead
      active: activeOutlets.length,
      locked: inactiveOutlets.length,
    };
    
    console.log("📊 Stats:", stats.value);
  } catch (err) {
    console.error("❌ Error fetching outlets:", err);
    console.error("❌ Error details:", err.response);
    error.value = err.response?.data?.message || err.message || "Không thể tải danh sách địa điểm";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchOutlets();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    category: "",
    status: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchOutlets();
  }
};

const approveOutlet = async (outlet) => {
  const confirmed = await confirm(`Bạn có chắc muốn kích hoạt địa điểm "${outlet.name}"?`);
  if (!confirmed) return;

  try {
    await adminApi.approveOutlet(outlet.id);
    success("Kích hoạt địa điểm thành công");
    await fetchOutlets();
  } catch (err) {
    const errorMsg = err.response?.data?.message || err.message || "Có lỗi xảy ra. Backend chưa hỗ trợ chức năng này.";
    showError(errorMsg);
    console.error("Error approving outlet:", err);
  }
};

const lockOutlet = async (outlet) => {
  const confirmed = await confirm(`Bạn có chắc muốn vô hiệu hóa địa điểm "${outlet.name}"?`);
  if (!confirmed) return;

  try {
    await adminApi.lockOutlet(outlet.id);
    success("Vô hiệu hóa địa điểm thành công");
    await fetchOutlets();
  } catch (err) {
    const errorMsg = err.response?.data?.message || err.message || "Có lỗi xảy ra. Backend chưa hỗ trợ chức năng này.";
    showError(errorMsg);
    console.error("Error locking outlet:", err);
  }
};

const editOutlet = async (outlet) => {
  editingOutlet.value = outlet;
  form.value = {
    name: outlet.name || "",
    description: outlet.description || "",
    address: outlet.address || "",
    email: outlet.email || "",
    phoneNumber: outlet.phoneNumber || "",
    website: outlet.website || "",
    latitude: outlet.latitude || null,
    longitude: outlet.longitude || null,
    priceRange: outlet.priceRange || "",
    capacity: outlet.capacity || null,
    typeId: outlet.type?.id || outlet.typeId || "",
    provinceId: outlet.district?.provinceId || "",
    districtId: outlet.district?.id || outlet.districtId || "",
  };
  
  // Fetch districts if province is available
  if (form.value.provinceId) {
    await fetchDistricts(form.value.provinceId);
  }
};

const deleteOutlet = async (outlet) => {
  const confirmed = await confirm(`Bạn có chắc muốn xóa địa điểm "${outlet.name}"?`);
  if (!confirmed) return;

  try {
    await outletApi.deleteOutlet(outlet.id);
    success("Xóa địa điểm thành công");
    await fetchOutlets();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveOutlet = async () => {
  try {
    const data = {
      name: form.value.name,
      description: form.value.description || null,
      address: form.value.address,
      email: form.value.email || null,
      phoneNumber: form.value.phoneNumber || null,
      website: form.value.website || null,
      latitude: form.value.latitude || null,
      longitude: form.value.longitude || null,
      priceRange: form.value.priceRange || null,
      capacity: form.value.capacity || null,
      typeId: parseInt(form.value.typeId),
      districtId: parseInt(form.value.districtId),
    };

    if (editingOutlet.value) {
      await outletApi.updateOutlet(editingOutlet.value.id, data);
      success("Cập nhật địa điểm thành công");
    } else {
      await outletApi.createOutlet(data);
      success("Tạo địa điểm mới thành công");
    }
    closeModal();
    await fetchOutlets();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingOutlet.value = null;
  form.value = {
    name: "",
    description: "",
    address: "",
    email: "",
    phoneNumber: "",
    website: "",
    latitude: null,
    longitude: null,
    priceRange: "",
    capacity: null,
    typeId: "",
    provinceId: "",
    districtId: "",
  };
  districts.value = [];
};

onMounted(() => {
  fetchCategories();
  fetchOutletTypes();
  fetchProvinces();
  fetchOutlets();
});
</script>
