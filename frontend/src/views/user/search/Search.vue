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
          <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
            <!-- Search Input -->
            <div class="md:col-span-2">
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Tìm kiếm
              </label>
              <div class="relative">
                <input
                  v-model="searchQuery"
                  @keyup.enter="handleSearch"
                  type="text"
                  placeholder="Tên nhà hàng, món ăn, địa điểm..."
                  class="w-full pl-10 pr-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary"
                />
                <span
                  class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-subtext-light dark:text-subtext-dark"
                >
                  search
                </span>
              </div>
            </div>

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
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary"
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

            <!-- Search Button -->
            <div class="flex items-end">
              <button
                @click="handleSearch"
                :disabled="isSearching"
                class="w-full px-6 py-3 rounded-lg bg-primary text-white font-bold hover:bg-opacity-90 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span v-if="!isSearching">Tìm kiếm</span>
                <span v-else>Đang tìm...</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Search Results -->
    <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
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
          <div v-if="outlets.length > 0" class="flex items-center gap-2">
            <label class="text-sm text-subtext-light dark:text-subtext-dark">
              Sắp xếp:
            </label>
            <select
              v-model="sortBy"
              @change="handleSearch"
              class="px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50"
            >
              <option value="name,asc">Tên A-Z</option>
              <option value="name,desc">Tên Z-A</option>
              <option value="rating,desc">Đánh giá cao nhất</option>
              <option value="averagePrice,asc">Giá thấp đến cao</option>
              <option value="averagePrice,desc">Giá cao đến thấp</option>
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
                  <span>{{ outlet.outletCategory?.name || "N/A" }}</span>
                </div>

                <!-- Location -->
                <div
                  class="flex items-center gap-1 text-sm text-subtext-light dark:text-subtext-dark"
                >
                  <span class="material-symbols-outlined text-base"
                    >location_on</span
                  >
                  <span class="line-clamp-1">
                    {{ outlet.district?.name }}, {{ outlet.province?.name }}
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
import {outletApi} from "@/api";

const route = useRoute();
const router = useRouter();

// State
const searchQuery = ref("");
const selectedCategory = ref("");
const sortBy = ref("name,asc");
const outlets = ref([]);
const categories = ref([]);
const isSearching = ref(false);
const errorMessage = ref("");
const hasSearched = ref(false);

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

// Fetch categories
const fetchCategories = async () => {
  try {
    const data = await outletApi.getCategories();
    categories.value = data;
  } catch (err) {
    console.error("Error fetching categories:", err);
  }
};

// Search outlets
const handleSearch = async () => {
  isSearching.value = true;
  errorMessage.value = "";
  hasSearched.value = true;

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

    console.log("🔍 Searching with params:", params);
    const response = await outletApi.searchOutlets(params);
    console.log("✅ Search response:", response);

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
    const query = {};
    if (searchQuery.value.trim()) query.q = searchQuery.value.trim();
    if (selectedCategory.value) query.category = selectedCategory.value;
    if (sortBy.value !== "name,asc") query.sort = sortBy.value;
    if (currentPage.value > 0) query.page = currentPage.value;

    router.replace({query});
  } catch (err) {
    console.error("❌ Search error:", err);
    errorMessage.value = err.message || "Lỗi khi tìm kiếm. Vui lòng thử lại.";
    outlets.value = [];
  } finally {
    isSearching.value = false;
  }
};

// Pagination
const goToPage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page;
    handleSearch();
    window.scrollTo({top: 0, behavior: "smooth"});
  }
};

// Reset search
const resetSearch = () => {
  searchQuery.value = "";
  selectedCategory.value = "";
  sortBy.value = "name,asc";
  currentPage.value = 0;
  outlets.value = [];
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

// Lifecycle
onMounted(() => {
  fetchCategories();

  // Load search params from URL
  if (route.query.q) {
    searchQuery.value = route.query.q;
  }
  if (route.query.category) {
    selectedCategory.value = route.query.category;
  }
  if (route.query.sort) {
    sortBy.value = route.query.sort;
  }
  if (route.query.page) {
    currentPage.value = parseInt(route.query.page);
  }

  // Auto search if has query params
  if (route.query.q || route.query.category) {
    handleSearch();
  }
});
</script>
