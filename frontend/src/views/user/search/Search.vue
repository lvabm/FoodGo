<template>
  <div class="w-full">
    <!-- Search Header -->
    <div class="bg-primary/5 dark:bg-primary/10 py-8">
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-3xl font-bold text-text-light dark:text-text-dark mb-6">
          Tìm kiếm nhà hàng
        </h1>

        <!-- Search Form -->
        <div class="bg-white dark:bg-surface-dark rounded-xl p-6 shadow-lg">
          <!-- Main Search Bar -->
          <div class="mb-4">
            <div class="relative">
              <input
                v-model="searchQuery"
                @keyup.enter="handleSearch"
                type="text"
                placeholder="Tìm kiếm tên nhà hàng, món ăn, địa điểm..."
                class="w-full pl-12 pr-4 py-4 rounded-lg border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary text-base"
              />
              <span
                class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-subtext-light dark:text-subtext-dark text-xl"
              >
                search
              </span>
              <button
                v-if="searchQuery"
                @click="searchQuery = ''; handleSearch()"
                class="absolute right-3 top-1/2 -translate-y-1/2 text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark"
              >
                <span class="material-symbols-outlined">close</span>
              </button>
            </div>
          </div>

          <!-- Quick Filters Row -->
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-4">
            <!-- Category Filter -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Danh mục
              </label>
              <select
                v-model="selectedCategory"
                @change="handleSearch"
                class="w-full px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
              >
                <option value="">Tất cả</option>
                <option
                  v-for="category in categories"
                  :key="category.id"
                  :value="category.id"
                >
                  {{ category.name }}
                </option>
              </select>
            </div>

            <!-- District Filter -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Quận/Huyện
              </label>
              <select
                v-model="selectedDistrict"
                @change="handleSearch"
                class="w-full px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
              >
                <option value="">Tất cả</option>
                <option
                  v-for="district in districts"
                  :key="district.id"
                  :value="district.id"
                >
                  {{ district.name }}
                </option>
              </select>
            </div>

            <!-- Outlet Type Filter -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Loại quán
              </label>
              <select
                v-model="selectedOutletType"
                @change="handleSearch"
                class="w-full px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
              >
                <option value="">Tất cả</option>
                <option
                  v-for="type in outletTypes"
                  :key="type.id"
                  :value="type.id"
                >
                  {{ type.name }}
                </option>
              </select>
            </div>

            <!-- Price Range Filter -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Mức giá
              </label>
              <select
                v-model="selectedPriceRange"
                @change="handleSearch"
                class="w-full px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
              >
                <option value="">Tất cả</option>
                <option value="Dưới 100k">Dưới 100k</option>
                <option value="100k - 200k">100k - 200k</option>
                <option value="200k - 500k">200k - 500k</option>
                <option value="Trên 500k">Trên 500k</option>
              </select>
            </div>
          </div>

          <!-- Advanced Filters Toggle -->
          <div class="flex items-center justify-between">
            <button
              @click="showAdvancedFilters = !showAdvancedFilters"
              class="text-sm text-primary hover:underline font-medium flex items-center gap-1"
            >
              <span class="material-symbols-outlined text-base">
                {{ showAdvancedFilters ? 'expand_less' : 'expand_more' }}
              </span>
              {{ showAdvancedFilters ? 'Ẩn bộ lọc nâng cao' : 'Bộ lọc nâng cao' }}
            </button>
            <div class="flex gap-2">
              <button
                v-if="hasActiveFilters"
                @click="clearAllFilters"
                class="text-sm text-red-500 hover:underline font-medium"
              >
                Xóa tất cả bộ lọc
              </button>
              <button
                @click="handleSearch"
                :disabled="isSearching"
                class="px-6 py-2 rounded-lg bg-primary text-white font-bold hover:bg-opacity-90 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span v-if="!isSearching">Tìm kiếm</span>
                <span v-else class="flex items-center gap-2">
                  <span class="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></span>
                  Đang tìm...
                </span>
              </button>
            </div>
          </div>

          <!-- Advanced Filters Panel -->
          <div
            v-show="showAdvancedFilters"
            class="mt-4 pt-4 border-t border-border-light dark:border-border-dark"
          >
            <div class="mb-4">
              <label class="block text-sm font-medium text-text-light dark:text-text-dark mb-2">
                Tiện ích
              </label>
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="feature in features"
                  :key="feature.id"
                  @click="toggleFeature(feature.id)"
                  :class="[
                    'px-3 py-1.5 rounded-full text-sm font-medium transition-colors',
                    selectedFeatures.includes(feature.id)
                      ? 'bg-primary text-white'
                      : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
                  ]"
                >
                  {{ feature.name }}
                </button>
              </div>
            </div>
          </div>

          <!-- Active Filters Chips -->
          <div v-if="hasActiveFilters" class="mt-4 pt-4 border-t border-border-light dark:border-border-dark">
            <div class="flex flex-wrap gap-2 items-center">
              <span class="text-sm text-subtext-light dark:text-subtext-dark">Bộ lọc đang áp dụng:</span>
              <span
                v-if="selectedCategory"
                class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm flex items-center gap-2"
              >
                Danh mục: {{ getCategoryName(selectedCategory) }}
                <button @click="selectedCategory = ''; handleSearch()" class="hover:text-primary/70">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
              <span
                v-if="selectedDistrict"
                class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm flex items-center gap-2"
              >
                Quận: {{ getDistrictName(selectedDistrict) }}
                <button @click="selectedDistrict = ''; handleSearch()" class="hover:text-primary/70">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
              <span
                v-if="selectedOutletType"
                class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm flex items-center gap-2"
              >
                Loại: {{ getOutletTypeName(selectedOutletType) }}
                <button @click="selectedOutletType = ''; handleSearch()" class="hover:text-primary/70">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
              <span
                v-if="selectedPriceRange"
                class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm flex items-center gap-2"
              >
                Giá: {{ selectedPriceRange }}
                <button @click="selectedPriceRange = ''; handleSearch()" class="hover:text-primary/70">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
              <span
                v-for="featureId in selectedFeatures"
                :key="featureId"
                class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm flex items-center gap-2"
              >
                {{ getFeatureName(featureId) }}
                <button @click="removeFeature(featureId)" class="hover:text-primary/70">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Search Results -->
    <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Menu Items Section (when menuCategoryId is present) -->
      <div v-if="menuCategoryId" class="mb-8">
        <h2 class="text-2xl font-bold text-text-light dark:text-text-dark mb-4">
          Món ăn liên quan
        </h2>
        
        <!-- Loading State for Menu Items -->
        <div v-if="isLoadingMenuItems" class="flex justify-center py-12">
          <div
            class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
          ></div>
        </div>
        
        <!-- Menu Items Grid -->
        <div v-else-if="menuItems.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
            v-for="item in menuItems"
            :key="item.id"
            class="bg-white dark:bg-surface-dark border border-border-light dark:border-border-dark rounded-xl overflow-hidden hover:shadow-lg transition-all cursor-pointer"
          >
            <!-- Image Section -->
            <div class="w-full h-48 bg-gradient-to-br from-primary/10 to-primary/20 dark:from-primary/20 dark:to-primary/30">
              <ImageDisplay
                :image-url="item.imageUrl || null"
                :alt="item.name"
                :lazy="true"
                placeholder-icon="restaurant_menu"
                :icon-size="'64px'"
                container-class="w-full h-full"
                image-class="w-full h-full object-cover"
              />
            </div>
            
            <!-- Content Section -->
            <div class="p-4">
              <div class="flex items-start justify-between mb-2">
                <h3 class="text-lg font-semibold text-text-light dark:text-text-dark line-clamp-1">
                  {{ item.name }}
                </h3>
                <span
                  v-if="item.isPopular"
                  class="px-2 py-1 bg-yellow-100 dark:bg-yellow-900 text-yellow-800 dark:text-yellow-200 rounded text-xs font-medium whitespace-nowrap ml-2 flex-shrink-0"
                >
                  ⭐ Phổ biến
                </span>
              </div>
              <p
                v-if="item.description"
                class="text-sm text-subtext-light dark:text-subtext-dark mb-3 line-clamp-2"
              >
                {{ item.description }}
              </p>
              <div class="flex items-center gap-2 flex-wrap">
                <span
                  v-if="item.subCategoryName"
                  class="px-2 py-1 bg-primary/10 dark:bg-primary/20 text-primary rounded text-xs font-medium"
                >
                  {{ item.subCategoryName }}
                </span>
                <span
                  v-if="item.provinceName"
                  class="px-2 py-1 bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded text-xs"
                >
                  📍 {{ item.provinceName }}
                </span>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Empty State for Menu Items -->
        <div v-else class="text-center py-12 bg-gray-50 dark:bg-gray-800 rounded-xl">
          <span
            class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4 block"
          >
            restaurant_menu
          </span>
          <p class="text-subtext-light dark:text-subtext-dark">
            Chưa có món ăn nào trong danh mục này
          </p>
        </div>
      </div>

      <!-- Loading State -->
      <div v-if="isSearching" class="flex justify-center py-12">
        <div
          class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
        ></div>
      </div>

      <!-- Error State -->
      <div
        v-else-if="errorMessage"
        class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg"
      >
        {{ errorMessage }}
      </div>

      <!-- Results -->
      <div v-else>
        <!-- Results Header -->
        <div v-if="hasSearched" class="flex items-center justify-between mb-6">
          <h2 class="text-xl font-semibold">
            <span v-if="outlets.length > 0">
              Tìm thấy {{ totalResults }} kết quả
            </span>
            <span v-else> Không tìm thấy kết quả </span>
          </h2>

          <!-- Sort Options -->
          <div v-if="outlets.length > 0" class="flex items-center gap-2 flex-wrap">
            <label class="text-sm font-medium text-text-light dark:text-text-dark">
              Sắp xếp:
            </label>
            <select
              v-model="sortBy"
              @change="handleSearch"
              class="px-4 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary font-medium"
            >
              <option value="name,asc">Tên A-Z</option>
              <option value="name,desc">Tên Z-A</option>
              <option value="averageRating,desc">Đánh giá cao nhất</option>
              <option value="averageRating,asc">Đánh giá thấp nhất</option>
              <option value="totalReviews,desc">Nhiều đánh giá nhất</option>
            </select>
          </div>
        </div>

        <!-- Outlets Grid -->
        <div
          v-if="outlets.length > 0"
          class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8"
        >
          <router-link
            v-for="outlet in outlets"
            :key="outlet.id"
            :to="`/outlet/${outlet.id}`"
            class="group bg-white dark:bg-surface-dark border border-border-light dark:border-border-dark rounded-xl overflow-hidden hover:shadow-xl transition-all duration-300"
          >
            <!-- Image -->
            <div
              class="relative h-48 bg-gray-200 dark:bg-gray-800 overflow-hidden"
            >
              <img
                v-if="outlet.images && outlet.images.length > 0"
                :src="outlet.images[0]"
                :alt="outlet.name"
                class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
              />
              <div v-else class="flex items-center justify-center h-full">
                <span class="material-symbols-outlined text-6xl text-gray-400">
                  restaurant
                </span>
              </div>
            </div>

            <!-- Content -->
            <div class="p-4">
              <h3
                class="text-lg font-semibold text-text-light dark:text-text-dark mb-2 line-clamp-1"
              >
                {{ outlet.name }}
              </h3>

              <div class="space-y-2 mb-3">
                <!-- Rating -->
                <div class="flex items-center gap-1 text-sm">
                  <span
                    class="material-symbols-outlined text-yellow-500 text-base"
                  >
                    star
                  </span>
                  <span class="font-medium">{{ getRating(outlet) }}</span>
                  <span class="text-subtext-light dark:text-subtext-dark">
                    ({{ outlet.totalReviews || 0 }})
                  </span>
                </div>

                <!-- Category -->
                <div
                  class="flex items-center gap-1 text-sm text-subtext-light dark:text-subtext-dark"
                >
                  <span class="material-symbols-outlined text-base"
                    >restaurant</span
                  >
                  <span>{{ outlet.outletCategory?.name || outlet.outletTypeName || "Nhà hàng" }}</span>
                </div>

                <!-- Location -->
                <div
                  class="flex items-center gap-1 text-sm text-subtext-light dark:text-subtext-dark"
                >
                  <span class="material-symbols-outlined text-base"
                    >location_on</span
                  >
                  <span class="line-clamp-1">
                    {{ outlet.districtName || outlet.district?.name || "TPHCM" }}
                  </span>
                </div>

                <!-- Price -->
                <div class="flex items-center gap-1 text-sm">
                  <span class="material-symbols-outlined text-base text-primary"
                    >payments</span
                  >
                  <span class="font-semibold text-primary">
                    {{ getDisplayPrice(outlet) }}
                  </span>
                  <span class="text-subtext-light dark:text-subtext-dark"
                    >/ người</span
                  >
                </div>
              </div>

              <!-- Features Tags -->
              <div
                v-if="outlet.features && outlet.features.length > 0"
                class="flex flex-wrap gap-1"
              >
                <span
                  v-for="feature in outlet.features.slice(0, 3)"
                  :key="feature.id"
                  class="px-2 py-1 bg-primary/10 text-primary rounded text-xs"
                >
                  {{ feature.name }}
                </span>
              </div>
            </div>
          </router-link>
        </div>

        <!-- Empty State -->
        <div v-else-if="hasSearched" class="text-center py-16">
          <span
            class="material-symbols-outlined text-8xl text-gray-300 dark:text-gray-600 mb-4 block"
          >
            search_off
          </span>
          <h3
            class="text-xl font-semibold text-text-light dark:text-text-dark mb-2"
          >
            Không tìm thấy kết quả
          </h3>
          <p class="text-subtext-light dark:text-subtext-dark mb-6">
            Hãy thử tìm kiếm với từ khóa khác hoặc thay đổi bộ lọc
          </p>
          <button
            @click="resetSearch"
            class="px-6 py-2 rounded-lg bg-primary text-white font-medium hover:bg-opacity-90 transition-colors"
          >
            Đặt lại tìm kiếm
          </button>
        </div>

        <!-- Initial State -->
        <div v-else class="text-center py-16">
          <span
            class="material-symbols-outlined text-8xl text-gray-300 dark:text-gray-600 mb-4 block"
          >
            search
          </span>
          <h3
            class="text-xl font-semibold text-text-light dark:text-text-dark mb-2"
          >
            Bắt đầu tìm kiếm
          </h3>
          <p class="text-subtext-light dark:text-subtext-dark">
            Nhập từ khóa để tìm kiếm nhà hàng yêu thích của bạn
          </p>
        </div>

        <!-- Pagination -->
        <div
          v-if="outlets.length > 0 && totalPages > 1"
          class="flex justify-center items-center gap-2"
        >
          <button
            @click="goToPage(currentPage - 1)"
            :disabled="currentPage === 0"
            class="px-4 py-2 rounded-lg border border-border-light dark:border-border-dark hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span class="material-symbols-outlined">chevron_left</span>
          </button>

          <div class="flex gap-2">
            <button
              v-for="page in visiblePages"
              :key="page"
              @click="goToPage(page)"
              :class="[
                'px-4 py-2 rounded-lg border transition-colors',
                currentPage === page
                  ? 'bg-primary text-white border-primary'
                  : 'border-border-light dark:border-border-dark hover:bg-gray-100 dark:hover:bg-gray-800',
              ]"
            >
              {{ page + 1 }}
            </button>
          </div>

          <button
            @click="goToPage(currentPage + 1)"
            :disabled="currentPage >= totalPages - 1"
            class="px-4 py-2 rounded-lg border border-border-light dark:border-border-dark hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span class="material-symbols-outlined">chevron_right</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import {outletApi, menuApi, locationApi} from "@/api";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const route = useRoute();
const router = useRouter();

// State
const searchQuery = ref("");
const selectedCategory = ref("");
const selectedDistrict = ref("");
const selectedOutletType = ref("");
const selectedPriceRange = ref("");
const selectedFeatures = ref([]);
const sortBy = ref("name,asc");
const outlets = ref([]);
const categories = ref([]);
const districts = ref([]);
const outletTypes = ref([]);
const features = ref([]);
const isSearching = ref(false);
const errorMessage = ref("");
const hasSearched = ref(false);
const showAdvancedFilters = ref(false);

// Menu items state (for category search)
const menuCategoryId = ref(null);
const menuItems = ref([]);
const isLoadingMenuItems = ref(false);

// Pagination
const currentPage = ref(0);
const pageSize = ref(12);
const totalResults = ref(0);
const totalPages = ref(0);

// Computed
const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5;
  let start = Math.max(0, currentPage.value - Math.floor(maxVisible / 2));
  let end = Math.min(totalPages.value - 1, start + maxVisible - 1);

  if (end - start < maxVisible - 1) {
    start = Math.max(0, end - maxVisible + 1);
  }

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

const hasActiveFilters = computed(() => {
  return !!(
    selectedCategory.value ||
    selectedDistrict.value ||
    selectedOutletType.value ||
    selectedPriceRange.value ||
    selectedFeatures.value.length > 0
  );
});

// Fetch filter options
const fetchCategories = async () => {
  try {
    const data = await outletApi.getCategories();
    categories.value = Array.isArray(data) ? data : [];
  } catch (err) {
    console.error("Error fetching categories:", err);
  }
};

const fetchDistricts = async () => {
  try {
    // Get HCM province first (assuming province ID 1 or get from API)
    const provinces = await locationApi.getProvinces();
    const hcmProvince = Array.isArray(provinces) 
      ? provinces.find(p => p.name === 'Hồ Chí Minh' || p.name === 'TPHCM')
      : null;
    
    if (hcmProvince) {
      const data = await locationApi.getDistricts(hcmProvince.id);
      districts.value = Array.isArray(data) ? data : [];
    }
  } catch (err) {
    console.error("Error fetching districts:", err);
  }
};

const fetchOutletTypes = async () => {
  try {
    const data = await outletApi.getTypes();
    outletTypes.value = Array.isArray(data) ? data : [];
  } catch (err) {
    console.error("Error fetching outlet types:", err);
  }
};

const fetchFeatures = async () => {
  try {
    const data = await outletApi.getFeatures();
    features.value = Array.isArray(data) ? data : [];
  } catch (err) {
    console.error("Error fetching features:", err);
  }
};

// Search outlets
const handleSearch = async () => {
  isSearching.value = true;
  errorMessage.value = "";
  hasSearched.value = true;
  currentPage.value = 0; // Reset to first page on new search

  try {
    const [sortField, sortDirection] = sortBy.value.split(",");

    const params = {
      page: currentPage.value,
      size: pageSize.value,
      sort: `${sortField},${sortDirection}`,
    };

    if (searchQuery.value.trim()) {
      params.name = searchQuery.value.trim();
    }

    if (selectedCategory.value) {
      params.categoryId = selectedCategory.value;
    }

    if (selectedDistrict.value) {
      params.districtId = parseInt(selectedDistrict.value);
    }

    if (selectedOutletType.value) {
      params.outletTypeId = parseInt(selectedOutletType.value);
    }

    if (selectedPriceRange.value) {
      params.priceRange = selectedPriceRange.value;
    }

    if (selectedFeatures.value.length > 0) {
      params.featureIds = selectedFeatures.value;
    }

    const response = await outletApi.searchOutlets(params);

    // Handle PageResponse structure
    if (response.data && Array.isArray(response.data)) {
      outlets.value = response.data;
      totalResults.value = response.totalElements || 0;
      totalPages.value = response.totalPages || 0;
    } else if (Array.isArray(response)) {
      outlets.value = response;
      totalResults.value = response.length;
      totalPages.value = 1;
    } else {
      outlets.value = [];
      totalResults.value = 0;
      totalPages.value = 0;
    }

    // Update URL with search params
    updateURL();
  } catch (err) {
    console.error("❌ Search error:", err);
    errorMessage.value = err.message || "Lỗi khi tìm kiếm. Vui lòng thử lại.";
    outlets.value = [];
  } finally {
    isSearching.value = false;
  }
};

// Update URL with current search params
const updateURL = () => {
  const query = {};
  if (searchQuery.value.trim()) query.q = searchQuery.value.trim();
  if (selectedCategory.value) query.category = selectedCategory.value;
  if (selectedDistrict.value) query.district = selectedDistrict.value;
  if (selectedOutletType.value) query.type = selectedOutletType.value;
  if (selectedPriceRange.value) query.price = selectedPriceRange.value;
  if (selectedFeatures.value.length > 0) query.features = selectedFeatures.value.join(',');
  if (sortBy.value !== "name,asc") query.sort = sortBy.value;
  if (currentPage.value > 0) query.page = currentPage.value;
  if (menuCategoryId.value) query.menuCategoryId = menuCategoryId.value;

  router.replace({query});
};

// Pagination
const goToPage = async (page) => {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page;
    
    // Don't reset page in handleSearch when paginating
    isSearching.value = true;
    errorMessage.value = "";
    
    try {
      const [sortField, sortDirection] = sortBy.value.split(",");
      const params = {
        page: currentPage.value,
        size: pageSize.value,
        sort: `${sortField},${sortDirection}`,
      };

      if (searchQuery.value.trim()) {
        params.name = searchQuery.value.trim();
      }
      if (selectedCategory.value) {
        params.categoryId = selectedCategory.value;
      }
      if (selectedDistrict.value) {
        params.districtId = parseInt(selectedDistrict.value);
      }
      if (selectedOutletType.value) {
        params.outletTypeId = parseInt(selectedOutletType.value);
      }
      if (selectedPriceRange.value) {
        params.priceRange = selectedPriceRange.value;
      }
      if (selectedFeatures.value.length > 0) {
        params.featureIds = selectedFeatures.value;
      }

      const response = await outletApi.searchOutlets(params);

      if (response.data && Array.isArray(response.data)) {
        outlets.value = response.data;
        totalResults.value = response.totalElements || 0;
        totalPages.value = response.totalPages || 0;
      }

      updateURL();
      window.scrollTo({top: 0, behavior: "smooth"});
    } catch (err) {
      console.error("❌ Pagination error:", err);
      errorMessage.value = err.message || "Lỗi khi tải trang.";
    } finally {
      isSearching.value = false;
    }
  }
};

// Filter helpers
const toggleFeature = (featureId) => {
  const index = selectedFeatures.value.indexOf(featureId);
  if (index > -1) {
    selectedFeatures.value.splice(index, 1);
  } else {
    selectedFeatures.value.push(featureId);
  }
  handleSearch();
};

const removeFeature = (featureId) => {
  const index = selectedFeatures.value.indexOf(featureId);
  if (index > -1) {
    selectedFeatures.value.splice(index, 1);
    handleSearch();
  }
};

const clearAllFilters = () => {
  searchQuery.value = "";
  selectedCategory.value = "";
  selectedDistrict.value = "";
  selectedOutletType.value = "";
  selectedPriceRange.value = "";
  selectedFeatures.value = [];
  sortBy.value = "name,asc";
  handleSearch();
};

// Helper functions for filter names
const getCategoryName = (id) => {
  const category = categories.value.find(c => c.id === parseInt(id));
  return category?.name || "";
};

const getDistrictName = (id) => {
  const district = districts.value.find(d => d.id === parseInt(id));
  return district?.name || "";
};

const getOutletTypeName = (id) => {
  const type = outletTypes.value.find(t => t.id === parseInt(id));
  return type?.name || "";
};

const getFeatureName = (id) => {
  const feature = features.value.find(f => f.id === id);
  return feature?.name || "";
};

// Reset search
const resetSearch = () => {
  clearAllFilters();
  hasSearched.value = false;
  errorMessage.value = "";
  router.replace({query: {}});
};

// Helpers for rating and price display
const getRating = (o) => {
  const r = o?.averageRating ?? o?.rating;
  if (r === undefined || r === null) return "N/A";
  const num = Number(r);
  if (Number.isNaN(num)) return "N/A";
  return num.toFixed(1);
};

const getDisplayPrice = (o) => {
  if (o?.priceRange) return o.priceRange;
  if (o?.averagePrice) return formatPrice(o.averagePrice);
  const prices = (o?.menuItems || []).map((i) => Number(i?.price || i?.priceAmount || 0)).filter((p) => p > 0);
  if (prices.length > 0) {
    const avg = prices.reduce((s, v) => s + v, 0) / prices.length;
    return formatPrice(avg);
  }
  return "N/A";
};

// Format price
const formatPrice = (price) => {
  if (!price) return "N/A";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

// Fetch menu items by category
const fetchMenuItemsByCategory = async (categoryId) => {
  isLoadingMenuItems.value = true;
  try {
    // Get sub categories for this category using search API with filter
    const subCategoriesResponse = await menuApi.getMenuSubCategories();
    const allSubCategories = Array.isArray(subCategoriesResponse) ? subCategoriesResponse : [];
    const subCategories = allSubCategories.filter((sc) => sc.categoryId === categoryId);

    if (subCategories.length === 0) {
      menuItems.value = [];
      return;
    }

    // Get menu items for each sub category
    const allMenuItems = [];
    for (const subCat of subCategories) {
      try {
        const itemsResponse = await menuApi.searchMasterMenuItems({
          subCategoryId: subCat.id,
          page: 0,
          size: 50,
        });
        
        // Handle PageResponse (Spring Data Page) or BaseResponse
        let items = [];
        if (itemsResponse?.content && Array.isArray(itemsResponse.content)) {
          // Spring Data Page format
          items = itemsResponse.content;
        } else if (itemsResponse?.data && Array.isArray(itemsResponse.data)) {
          // BaseResponse with data array
          items = itemsResponse.data;
        } else if (Array.isArray(itemsResponse)) {
          // Direct array
          items = itemsResponse;
        }
        
        if (items.length > 0) {
          allMenuItems.push(...items);
        }
      } catch (err) {
        console.error(`Error fetching menu items for sub category ${subCat.id}:`, err);
      }
    }

    menuItems.value = allMenuItems;
  } catch (err) {
    console.error("Error fetching menu items by category:", err);
    menuItems.value = [];
  } finally {
    isLoadingMenuItems.value = false;
  }
};

// Lifecycle
onMounted(async () => {
  // Fetch all filter options in parallel
  await Promise.all([
    fetchCategories(),
    fetchDistricts(),
    fetchOutletTypes(),
    fetchFeatures(),
  ]);

  // Load search params from URL
  if (route.query.q) {
    searchQuery.value = route.query.q;
  }
  if (route.query.category) {
    selectedCategory.value = route.query.category;
  }
  if (route.query.district) {
    selectedDistrict.value = route.query.district;
  }
  if (route.query.type) {
    selectedOutletType.value = route.query.type;
  }
  if (route.query.price) {
    selectedPriceRange.value = route.query.price;
  }
  if (route.query.features) {
    selectedFeatures.value = route.query.features.split(',').map(id => parseInt(id));
  }
  if (route.query.sort) {
    sortBy.value = route.query.sort;
  }
  if (route.query.page) {
    currentPage.value = parseInt(route.query.page);
  }
  if (route.query.menuCategoryId) {
    menuCategoryId.value = parseInt(route.query.menuCategoryId);
    fetchMenuItemsByCategory(menuCategoryId.value);
  }

  // Auto search if has query params
  if (route.query.q || route.query.category || route.query.district || route.query.type || route.query.price || route.query.features) {
    handleSearch();
  }
});
</script>
