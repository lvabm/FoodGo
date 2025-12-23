<template>
  <div class="w-full">
    <!-- Hero Section -->
    <section class="w-full py-16 sm:py-20 relative overflow-hidden">
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="@container">
          <div class="@[480px]:p-4">
            <div
              class="flex min-h-[480px] flex-col gap-6 rounded-xl bg-cover bg-center bg-no-repeat @[480px]:gap-8 items-center justify-center p-6 text-center"
              style="
                background-image: linear-gradient(
                    rgba(0, 0, 0, 0.2) 0%,
                    rgba(0, 0, 0, 0.5) 100%
                  ),
                  url('https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=1200');
              "
            >
              <div class="flex flex-col gap-2 animate-fade-in">
                <h1
                  class="text-white text-4xl font-black leading-tight tracking-[-0.033em] @[480px]:text-5xl drop-shadow-lg"
                >
                  Khám phá Ẩm thực Sài Gòn
                </h1>
                <h2
                  class="text-white text-sm font-normal leading-normal @[480px]:text-base drop-shadow-md"
                >
                  Tìm kiếm hàng ngàn quán ăn, đồ uống tuyệt vời tại TPHCM.
                </h2>
              </div>

              <!-- Search Bar -->
              <div class="flex w-full max-w-[560px]">
                <div
                  class="flex w-full flex-1 items-stretch rounded-xl h-14 shadow-lg bg-white dark:bg-surface-dark"
                >
                  <div
                    class="flex border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark items-center justify-center pl-4 rounded-l-xl border-r-0"
                  >
                    <span class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                      >search</span
                    >
                  </div>
                  <input
                    v-model="searchQuery"
                    @keyup.enter="handleSearch"
                    type="text"
                    placeholder="Tìm món ăn, nhà hàng, địa điểm..."
                    class="flex-1 border-y border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark px-2 text-sm text-text-light dark:text-text-dark focus:outline-none placeholder:text-subtext-light dark:placeholder:text-subtext-dark"
                  />
                  <div
                    class="flex items-center justify-center rounded-r-xl border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark pr-2"
                  >
                    <button
                      @click="handleSearch"
                      class="h-10 px-4 bg-primary text-white rounded-lg font-bold hover:bg-opacity-90 transition-colors"
                    >
                      Tìm kiếm
                    </button>
                  </div>
                </div>
              </div>

              <!-- Quick filters -->
              <div class="flex gap-3 flex-wrap justify-center">
                <button
                  v-for="filter in quickFilters"
                  :key="filter.key"
                  @click="handleQuickFilter(filter.key)"
                  class="h-8 px-4 rounded-full bg-white/20 text-white hover:bg-white/30 transition-colors text-sm font-medium backdrop-blur-sm"
                >
                  {{ filter.label }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Statistics Section -->
    <section class="w-full py-12 bg-gradient-to-r from-primary/5 to-primary/10 dark:from-primary/10 dark:to-primary/20">
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
          <div
            v-for="(stat, index) in statistics"
            :key="stat.label"
            :style="{ animationDelay: `${index * 0.1}s` }"
            class="text-center p-6 bg-white dark:bg-surface-dark rounded-xl shadow-md hover:shadow-lg transition-all duration-300 hover:-translate-y-1 animate-slide-up"
          >
            <div class="flex justify-center mb-3">
              <span class="material-symbols-outlined text-4xl text-primary">
                {{ stat.icon }}
              </span>
            </div>
            <div v-if="stat.loading" class="text-3xl font-bold text-primary mb-2">
              <div class="inline-block h-8 w-16 bg-gray-200 dark:bg-gray-700 rounded animate-pulse"></div>
            </div>
            <div v-else class="text-3xl font-bold text-primary mb-2">
              {{ stat.value }}
            </div>
            <div class="text-sm text-subtext-light dark:text-subtext-dark font-medium">
              {{ stat.label }}
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Category Section -->
    <section class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between mb-4">
        <h2
          class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight"
        >
          Bạn muốn ăn gì hôm nay?
        </h2>
        <button
          @click="router.push('/search')"
          class="text-sm text-primary hover:underline font-medium"
        >
          Xem tất cả →
        </button>
      </div>
      
      <!-- Loading State -->
      <div v-if="isLoadingCategories" class="grid grid-cols-2 gap-4 md:grid-cols-4">
        <div
          v-for="i in 4"
          :key="i"
          class="aspect-[4/3] rounded-xl bg-gray-200 dark:bg-gray-700 animate-pulse"
        ></div>
      </div>
      
      <!-- Categories Grid -->
      <div v-else-if="categories.length > 0" class="grid grid-cols-2 gap-4 md:grid-cols-4">
        <div
          v-for="(category, index) in categories"
          :key="category.id"
          @click="handleCategoryClick(category.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group relative cursor-pointer overflow-hidden rounded-xl shadow-md hover:shadow-xl transition-all duration-300 animate-slide-up"
        >
          <div
            class="bg-cover bg-center flex flex-col justify-end p-4 aspect-[4/3] transition-transform duration-300 group-hover:scale-105"
            :style="`background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.6) 0%, rgba(0, 0, 0, 0) 60%), url(${category.image});`"
          >
            <p class="text-white text-base font-bold leading-tight drop-shadow-lg">
              {{ category.name }}
            </p>
          </div>
        </div>
      </div>
      
      <!-- Empty State -->
      <div v-else class="text-center py-12">
        <p class="text-subtext-light dark:text-subtext-dark">
          Đang tải danh mục...
        </p>
      </div>
    </section>

    <!-- Featured Restaurants Section -->
    <section class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between mb-4">
        <h2
          class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight"
        >
          Quán ngon không thể bỏ lỡ
        </h2>
        <button
          @click="router.push('/search')"
          class="text-sm text-primary hover:underline font-medium"
        >
          Xem tất cả →
        </button>
      </div>
      
      <!-- Loading State -->
      <div v-if="isLoadingRestaurants" class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <div
          v-for="i in 6"
          :key="i"
          class="flex flex-col overflow-hidden rounded-xl bg-white dark:bg-surface-dark shadow-md animate-pulse"
        >
          <div class="h-48 w-full bg-gray-200 dark:bg-gray-700"></div>
          <div class="flex flex-col gap-2 p-4">
            <div class="h-5 w-3/4 bg-gray-200 dark:bg-gray-700 rounded"></div>
            <div class="h-4 w-1/2 bg-gray-200 dark:bg-gray-700 rounded"></div>
            <div class="h-4 w-2/3 bg-gray-200 dark:bg-gray-700 rounded"></div>
          </div>
        </div>
      </div>
      
      <!-- Restaurants Grid -->
      <div v-else-if="featuredRestaurants.length > 0" class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <div
          v-for="(restaurant, index) in featuredRestaurants"
          :key="restaurant.id"
          @click="viewOutletDetail(restaurant.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group flex cursor-pointer flex-col overflow-hidden rounded-xl bg-white dark:bg-surface-dark shadow-md transition-all duration-300 hover:shadow-xl hover:-translate-y-1 animate-slide-up"
        >
          <div class="relative overflow-hidden">
            <div class="h-48 w-full transition-transform duration-300 group-hover:scale-105">
              <ImageDisplay
                :image-url="restaurant.imageUrl"
                :alt="restaurant.name"
                :lazy="true"
                placeholder-icon="restaurant"
                :icon-size="'64px'"
                container-class="w-full h-full"
                image-class="w-full h-full object-cover"
              />
            </div>
            <div
              class="absolute top-3 left-3 rounded-full bg-primary/90 backdrop-blur-sm px-3 py-1 text-xs font-bold text-white shadow-lg z-10"
            >
              {{ restaurant.badge }}
            </div>
          </div>
          <div class="flex flex-col gap-2 p-4">
            <h3 class="text-lg font-bold text-text-light dark:text-text-dark line-clamp-1">
              {{ restaurant.name }}
            </h3>
            <p class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-1">
              {{ restaurant.category }} • {{ restaurant.district }}
            </p>
            <div class="flex items-center gap-1">
              <span
                class="material-symbols-outlined fill text-yellow-500 text-base"
                >star</span
              >
              <span class="text-sm font-bold text-text-light dark:text-text-dark">
                <template
                  v-if="
                    restaurant.rating !== null &&
                    restaurant.rating !== undefined
                  "
                  >{{ restaurant.rating }}</template
                >
                <template v-else>Chưa có</template>
              </span>
              <span class="text-sm text-subtext-light dark:text-subtext-dark">
                <template v-if="restaurant.reviews > 0"
                  >({{ restaurant.reviews }} đánh giá)</template
                >
                <template v-else>(Chưa có đánh giá)</template>
              </span>
            </div>
            <div class="flex items-center gap-2 mt-1">
              <span class="material-symbols-outlined text-primary text-lg"
                >schedule</span
              >
              <span class="text-sm text-subtext-light dark:text-subtext-dark">{{
                restaurant.hours
              }}</span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Empty State -->
      <div v-else class="text-center py-12 bg-gray-50 dark:bg-gray-800 rounded-xl">
        <span
          class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4 block"
        >
          restaurant_menu
        </span>
        <p class="text-subtext-light dark:text-subtext-dark">
          Chưa có quán ăn nào để hiển thị
        </p>
      </div>
    </section>

    <!-- Popular Menu Items Section -->
    <section v-if="popularMenuItems.length > 0" class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 bg-gray-50 dark:bg-gray-900/50">
      <div class="flex items-center justify-between mb-4">
        <h2
          class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight"
        >
          Món ăn phổ biến
        </h2>
        <button
          @click="router.push('/search')"
          class="text-sm text-primary hover:underline font-medium"
        >
          Xem tất cả →
        </button>
      </div>
      
      <div class="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
        <div
          v-for="(item, index) in popularMenuItems"
          :key="item.id"
          @click="router.push(`/search?menuCategoryId=${item.subCategoryId || ''}`)"
          :style="{ animationDelay: `${index * 0.05}s` }"
          class="group cursor-pointer bg-white dark:bg-surface-dark rounded-xl p-4 shadow-md hover:shadow-xl transition-all duration-300 hover:-translate-y-1 animate-slide-up"
        >
          <!-- Image Display with fallback -->
          <div class="aspect-square rounded-lg mb-3 overflow-hidden bg-gradient-to-br from-primary/10 to-primary/20 dark:from-primary/20 dark:to-primary/30 group-hover:scale-105 transition-transform">
            <ImageDisplay
              :image-url="item.imageUrl || getMenuImageUrl(item)"
              :alt="item.name"
              :lazy="true"
              placeholder-icon="restaurant_menu"
              :icon-size="'48px'"
              container-class="w-full h-full"
              image-class="w-full h-full object-cover"
            />
          </div>
          <h3 class="text-sm font-semibold text-text-light dark:text-text-dark line-clamp-2 text-center">
            {{ item.name }}
          </h3>
          <div
            v-if="item.isPopular"
            class="flex justify-center mt-2"
          >
            <span class="px-2 py-0.5 bg-yellow-100 dark:bg-yellow-900 text-yellow-800 dark:text-yellow-200 rounded text-xs font-medium">
              ⭐ Phổ biến
            </span>
          </div>
        </div>
      </div>
    </section>

    <!-- Top Rated Restaurants Section -->
    <section v-if="topRatedRestaurants.length > 0" class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between mb-4">
        <h2
          class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight"
        >
          ⭐ Quán được đánh giá cao nhất
        </h2>
        <button
          @click="router.push({path: '/search', query: {sort: 'averageRating,desc'}})"
          class="text-sm text-primary hover:underline font-medium"
        >
          Xem tất cả →
        </button>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="(restaurant, index) in topRatedRestaurants"
          :key="restaurant.id"
          @click="viewOutletDetail(restaurant.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group flex cursor-pointer flex-col overflow-hidden rounded-xl bg-white dark:bg-surface-dark shadow-md transition-all duration-300 hover:shadow-xl hover:-translate-y-1 animate-slide-up"
        >
          <div class="relative overflow-hidden">
            <div
              class="h-40 w-full bg-gradient-to-br from-yellow-100 to-yellow-200 dark:from-yellow-900 dark:to-yellow-800 transition-transform duration-300 group-hover:scale-105 flex items-center justify-center"
            >
              <span class="material-symbols-outlined text-5xl text-yellow-600 dark:text-yellow-400">
                star
              </span>
            </div>
            <div
              class="absolute top-3 right-3 rounded-full bg-yellow-500 px-3 py-1 text-xs font-bold text-white shadow-lg"
            >
              {{ restaurant.rating }} ⭐
            </div>
          </div>
          <div class="flex flex-col gap-2 p-4">
            <h3 class="text-lg font-bold text-text-light dark:text-text-dark line-clamp-1">
              {{ restaurant.name }}
            </h3>
            <p class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-1">
              {{ restaurant.category }} • {{ restaurant.district }}
            </p>
            <div class="flex items-center gap-1 text-sm">
              <span class="text-yellow-500 font-bold">{{ restaurant.rating }}</span>
              <span class="text-subtext-light dark:text-subtext-dark">
                ({{ restaurant.reviews }} đánh giá)
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Call to Action Section -->
    <section class="w-full py-16 bg-gradient-to-r from-primary to-primary/80 dark:from-primary/90 dark:to-primary/70">
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 class="text-3xl font-bold text-white mb-4">
          Sẵn sàng khám phá ẩm thực?
        </h2>
        <p class="text-white/90 mb-8 text-lg">
          Tìm kiếm hàng ngàn quán ăn và món ngon tại Sài Gòn
        </p>
        <div class="flex gap-4 justify-center flex-wrap">
          <button
            @click="router.push('/search')"
            class="px-8 py-3 bg-white text-primary rounded-lg font-bold hover:bg-gray-100 transition-colors shadow-lg"
          >
            Khám phá ngay
          </button>
          <button
            @click="router.push('/booking')"
            class="px-8 py-3 bg-white/10 backdrop-blur-sm text-white border-2 border-white rounded-lg font-bold hover:bg-white/20 transition-colors"
          >
            Đặt bàn ngay
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {useOutletStore} from "@/stores/outlet";
import {menuApi, outletApi, adminApi} from "@/api";
import apiClient from "@/api/axios";
import ImageDisplay from "@/components/common/ImageDisplay.vue";
import {processImageUrl} from "@/utils/imageUtils";

const router = useRouter();
const outletStore = useOutletStore();

const searchQuery = ref("");
const quickFilters = ref([
  { key: "nearby", label: "Gần tôi" },
  { key: "newest", label: "Mới nhất" },
  { key: "promotion", label: "Đang khuyến mãi" },
  { key: "booking", label: "Đặt bàn" },
]);

const isLoadingCategories = ref(false);
const isLoadingRestaurants = ref(false);

// Mapping images for categories
const categoryImageMap = {
  "Cà phê": "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400",
  "Cà Phê & Trà": "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400",
  "Bún/Phở": "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400",
  "Trà sữa": "https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=400",
  "Ăn vặt": "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=400",
  "Món Nước": "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400",
  "Món Khô": "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=400",
  "Nước Giải Khát": "https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=400",
  "Bánh Ngọt Âu Á": "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=400",
};

const categories = ref([]);
const featuredRestaurants = ref([]);
const topRatedRestaurants = ref([]);
const popularMenuItems = ref([]);
const statistics = ref([
  { label: "Quán ăn", value: "0", icon: "restaurant", loading: true },
  { label: "Món ăn", value: "0", icon: "restaurant_menu", loading: true },
  { label: "Đánh giá", value: "0", icon: "star", loading: true },
  { label: "Người dùng", value: "0", icon: "people", loading: true },
]);

onMounted(async () => {
  // Load all data in parallel
  await Promise.all([
    loadCategories(),
    loadRestaurants(),
    loadTopRatedRestaurants(),
    loadPopularMenuItems(),
    loadStatistics(),
  ]);
});

const loadCategories = async () => {
  isLoadingCategories.value = true;
  try {
    const categoriesResponse = await menuApi.getMenuCategories();
    
    if (categoriesResponse && Array.isArray(categoriesResponse) && categoriesResponse.length > 0) {
      const mainCategoryNames = ["Cà phê", "Bún/Phở", "Trà sữa", "Ăn vặt"];
      const filteredCategories = categoriesResponse
        .filter((cat) => cat && cat.name && mainCategoryNames.includes(cat.name))
        .map((cat) => ({
          id: cat.id,
          name: cat.name,
          description: cat.description || "",
          image: categoryImageMap[cat.name] || "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=400",
        }));
      
      // If we have filtered categories, use them; otherwise use all categories
      categories.value = filteredCategories.length > 0 
        ? filteredCategories 
        : categoriesResponse
            .filter((cat) => cat && cat.id && cat.name)
            .map((cat) => ({
              id: cat.id,
              name: cat.name,
              description: cat.description || "",
              image: categoryImageMap[cat.name] || "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=400",
            }));
    } else {
      categories.value = [];
    }
  } catch (error) {
    console.error("Failed to load categories:", error);
    categories.value = [];
  } finally {
    isLoadingCategories.value = false;
  }
};

const loadRestaurants = async () => {
  isLoadingRestaurants.value = true;
  try {
    await outletStore.fetchOutlets({page: 0, size: 6});

    if (outletStore.outlets && outletStore.outlets.length > 0) {
      featuredRestaurants.value = outletStore.outlets.map((outlet) => {
        // Get first image from outlet.images array (now List<String> from backend)
        const firstImage = outlet.images && Array.isArray(outlet.images) && outlet.images.length > 0 
          ? outlet.images[0] 
          : null;
        
        return {
          id: outlet.id,
          name: outlet.name || "Chưa có tên",
          category: outlet.outletCategory?.name || outlet.outletTypeName || "Nhà hàng",
          district: outlet.districtName || outlet.district?.name || "TPHCM",
          rating:
            outlet.averageRating !== undefined && outlet.averageRating !== null
              ? Number(outlet.averageRating).toFixed(1)
              : null,
          reviews: outlet.totalReviews ?? 0,
          badge: outlet.featured ? "Nổi bật" : "Mới",
          hours: outlet.openingHours || "8:00 - 22:00",
          imageUrl: firstImage, // Add image URL for ImageDisplay
        };
      });
    } else {
      featuredRestaurants.value = [];
    }
  } catch (error) {
    console.error("Failed to load restaurants:", error);
    featuredRestaurants.value = [];
  } finally {
    isLoadingRestaurants.value = false;
  }
};

const loadTopRatedRestaurants = async () => {
  try {
    // Request size 3 directly since we only need 3 items
    const response = await outletStore.searchOutlets({
      page: 0,
      size: 3,
      sort: "averageRating,desc",
    });

    // Handle response structure from outletStore.searchOutlets
    // It returns PageResponse with data array
    let outlets = [];
    if (response?.data && Array.isArray(response.data)) {
      outlets = response.data;
    } else if (Array.isArray(response)) {
      outlets = response;
    } else if (response?.content && Array.isArray(response.content)) {
      outlets = response.content;
    }

    // Filter outlets with rating >= 4.0 and map to display format
    topRatedRestaurants.value = outlets
      .filter((outlet) => {
        const rating = outlet.averageRating ?? outlet.rating;
        return rating && Number(rating) >= 4.0;
      })
      .map((outlet) => {
        const rating = outlet.averageRating ?? outlet.rating;
        return {
          id: outlet.id,
          name: outlet.name,
          category: outlet.outletCategory?.name || outlet.outletTypeName || "Nhà hàng",
          district: outlet.districtName || outlet.district?.name || "TPHCM",
          rating: Number(rating).toFixed(1),
          reviews: outlet.totalReviews ?? 0,
        };
      });
  } catch (error) {
    console.error("Failed to load top rated restaurants:", error);
    topRatedRestaurants.value = [];
  }
};

const loadPopularMenuItems = async () => {
  try {
    // Try to get popular items, if API doesn't support isPopular filter, get all and filter
    let response;
    try {
      response = await menuApi.searchMasterMenuItems({
        isPopular: true,
        page: 0,
        size: 20,
      });
    } catch (err) {
      // If isPopular filter not supported, get all items
      response = await menuApi.searchMasterMenuItems({
        page: 0,
        size: 20,
      });
    }

    let items = [];
    if (response?.content && Array.isArray(response.content)) {
      items = response.content;
    } else if (response?.data && Array.isArray(response.data)) {
      items = response.data;
    } else if (Array.isArray(response)) {
      items = response;
    }

    // Filter popular items if not already filtered by API
    const popular = items.filter(item => item.isPopular === true || item.isPopular === 'true');
    const selectedItems = (popular.length > 0 ? popular : items).slice(0, 6);
    
    // Try to enrich items with images from outlet menu items
    // For each menu item, try to get an image from an outlet that serves it
    const enrichedItems = await Promise.all(
      selectedItems.map(async (item) => {
        // Try to get image from outlet menu items
        try {
          if (item.id) {
            const imageUrl = await menuApi.findOutletMenuItemImage(item.id);
            return {
              ...item,
              imageUrl: imageUrl || null,
            };
          }
          return {
            ...item,
            imageUrl: item.imageUrl || null,
          };
        } catch (err) {
          console.warn(`Failed to get image for menu item ${item.id}:`, err);
          return item;
        }
      })
    );
    
    popularMenuItems.value = enrichedItems;
  } catch (error) {
    console.error("Failed to load popular menu items:", error);
    popularMenuItems.value = [];
  }
};

// Load statistics from real API data
const loadStatistics = async () => {
  try {
    // Load all statistics in parallel
    const [outletsResponse, menuItemsResponse, reviewsResponse] = await Promise.allSettled([
      outletApi.searchOutlets({page: 0, size: 1}),
      menuApi.searchMasterMenuItems({page: 0, size: 1}),
      // Get all reviews by calling search without outletId filter
      apiClient.get("/reviews/search", {params: {page: 0, size: 1}}),
    ]);

    // Update outlets count
    if (outletsResponse.status === 'fulfilled') {
      const response = outletsResponse.value;
      // Axios interceptor returns full PageResponse object for pages
      const total = response?.totalElements || response?.total || 0;
      statistics.value[0].value = formatNumber(total);
      statistics.value[0].loading = false;
    } else {
      statistics.value[0].value = "0";
      statistics.value[0].loading = false;
    }

    // Update menu items count
    if (menuItemsResponse.status === 'fulfilled') {
      const response = menuItemsResponse.value;
      // Axios interceptor returns full PageResponse/Spring Data Page object
      const total = response?.totalElements || response?.total || 0;
      statistics.value[1].value = formatNumber(total);
      statistics.value[1].loading = false;
    } else {
      statistics.value[1].value = "0";
      statistics.value[1].loading = false;
    }

    // Update reviews count
    if (reviewsResponse.status === 'fulfilled') {
      const response = reviewsResponse.value;
      // Axios interceptor returns full PageResponse/Spring Data Page object
      const total = response?.totalElements || response?.total || 0;
      statistics.value[2].value = formatNumber(total);
      statistics.value[2].loading = false;
    } else {
      statistics.value[2].value = "0";
      statistics.value[2].loading = false;
    }

    // Users count - try to get from admin API first (only if user is admin)
    let usersCountLoaded = false;
    try {
      const usersResponse = await adminApi.getUsers({page: 0, size: 1});
      const total = usersResponse?.totalElements || usersResponse?.total || 0;
      if (total > 0) {
        statistics.value[3].value = formatNumber(total);
        usersCountLoaded = true;
      }
    } catch (error) {
      // Admin API requires admin role - 403/500 is expected for non-admin users
      // Silently skip and use alternative method
      const status = error?.response?.status;
      if (status === 403 || status === 500) {
        // Expected behavior for non-admin users, don't log as error
      } else {
        // Other errors might be worth logging
        console.warn("Admin API error:", error?.response?.status, error?.message);
      }
    }
    
    // If admin API didn't work, try to estimate from reviews
    if (!usersCountLoaded && reviewsResponse.status === 'fulfilled') {
      try {
        // Get reviews to count unique users
        const reviewsData = reviewsResponse.value;
        const totalReviews = reviewsData?.totalElements || 0;
        
        if (totalReviews > 0) {
          // Get a larger sample to estimate better
          const sampleResponse = await apiClient.get("/reviews/search", {
            params: {page: 0, size: Math.min(200, totalReviews)}
          });
          
          const sampleReviews = sampleResponse?.data || sampleResponse?.content || [];
          const uniqueUserIds = new Set();
          
          sampleReviews.forEach(review => {
            if (review?.userId) {
              uniqueUserIds.add(review.userId);
            }
          });
          
          // Estimate: if we sampled all reviews, use unique count directly
          // Otherwise, estimate based on sample ratio
          if (sampleReviews.length >= totalReviews) {
            // We have all reviews, count unique users
            statistics.value[3].value = formatNumber(uniqueUserIds.size);
          } else if (uniqueUserIds.size > 0) {
            // Estimate: assume 40-60% of users write reviews
            // So multiply unique reviewers by 1.5-2.5
            const estimatedUsers = Math.ceil(uniqueUserIds.size * 2);
            statistics.value[3].value = formatNumber(estimatedUsers);
          } else {
            statistics.value[3].value = "0";
          }
        } else {
          statistics.value[3].value = "0";
        }
      } catch (err) {
        console.error("Failed to estimate users from reviews:", err);
        statistics.value[3].value = "0";
      }
    } else if (!usersCountLoaded) {
      statistics.value[3].value = "0";
    }
    
    statistics.value[3].loading = false;
  } catch (error) {
    console.error("Failed to load statistics:", error);
    // Set all to 0 on error
    statistics.value.forEach(stat => {
      stat.value = "0";
      stat.loading = false;
    });
  }
};

// Format number with K, M suffixes
const formatNumber = (num) => {
  const number = Number(num);
  if (!number || number === 0 || isNaN(number)) return "0";
  if (number >= 1000000) {
    return (number / 1000000).toFixed(1) + "M+";
  }
  if (number >= 1000) {
    return (number / 1000).toFixed(1) + "K+";
  }
  return number.toString();
};

const handleQuickFilter = (filterKey) => {
  switch (filterKey) {
    case "nearby":
      // Note: distance sort requires user location, redirect to search page
      // Backend doesn't support distance sort without location
      router.push({path: "/search", query: {sort: "name,asc"}});
      break;
    case "newest":
      // Backend supports sort via Pageable, but createdAt may not be available
      // Use default sort or name sort as fallback
      router.push({path: "/search"});
      break;
    case "promotion":
      // Backend doesn't support hasPromotion filter
      // Redirect to search page without filter
      router.push({path: "/search"});
      break;
    case "booking":
      // Backend doesn't support hasBooking filter
      // Redirect to search page without filter
      router.push({path: "/search"});
      break;
    default:
      router.push("/search");
  }
};

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({path: "/search", query: {q: searchQuery.value}});
  }
};

const handleCategoryClick = (categoryId) => {
  // Redirect đến trang search với filter menu category
  router.push({path: "/search", query: {menuCategoryId: categoryId}});
};

const viewOutletDetail = (id) => {
  router.push(`/outlet/${id}`);
};

// Helper function to get menu item image URL
// Since MenuItemResponse doesn't have imageUrl, we try to get from outlet menu items
// For now, return null to use placeholder (ImageDisplay component will handle it)
const getMenuImageUrl = (menuItem) => {
  // If item already has imageUrl (from outlet menu items), return processed URL
  if (menuItem?.imageUrl) {
    return processImageUrl(menuItem.imageUrl);
  }
  // Return null to use placeholder icon
  return null;
};
</script>
