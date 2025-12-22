<template>
  <div class="w-full">
    <!-- Loading State -->
    <div v-if="isLoading" class="w-full max-w-7xl mx-auto px-4 py-12">
      <div class="flex items-center justify-center">
        <div
          class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
        ></div>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="w-full max-w-7xl mx-auto px-4 py-12">
      <div
        class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg"
      >
        {{ error }}
      </div>
    </div>

    <!-- Outlet Details -->
    <div v-else-if="outlet" class="w-full">
      <!-- Hero Image Gallery -->
      <div class="relative w-full h-[400px] bg-gray-200 dark:bg-gray-800">
        <img
          v-if="outlet.images && outlet.images.length > 0"
          :src="outlet.images[0]"
          :alt="outlet.name"
          class="w-full h-full object-cover"
        />
        <div v-else class="flex items-center justify-center h-full">
          <span class="material-symbols-outlined text-6xl text-gray-400"
            >restaurant</span
          >
        </div>

        <!-- Image Gallery Thumbnails -->
        <div
          v-if="outlet.images && outlet.images.length > 1"
          class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex gap-2"
        >
          <button
            v-for="(img, index) in outlet.images.slice(0, 5)"
            :key="index"
            class="w-16 h-16 rounded-lg overflow-hidden border-2 border-white shadow-lg hover:scale-110 transition-transform"
          >
            <img
              :src="img"
              :alt="`Image ${index + 1}`"
              class="w-full h-full object-cover"
            />
          </button>
        </div>
      </div>

      <!-- Main Content -->
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <!-- Left Column - Main Info -->
          <div class="lg:col-span-2 space-y-6">
            <!-- Header -->
            <div>
              <div class="flex items-start justify-between mb-2">
                <h1
                  class="text-3xl font-bold text-text-light dark:text-text-dark"
                >
                  {{ outlet.name }}
                </h1>
                <button
                  class="p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-full transition-colors"
                >
                  <span class="material-symbols-outlined text-2xl text-red-500"
                    >favorite_border</span
                  >
                </button>
              </div>

              <div
                class="flex flex-wrap items-center gap-4 text-sm text-subtext-light dark:text-subtext-dark"
              >
                <!-- Rating -->
                <div class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-yellow-500"
                    >star</span
                  >
                  <span class="font-medium">{{ outlet.rating || "N/A" }}</span>
                  <span>({{ outlet.totalReviews || 0 }} đánh giá)</span>
                </div>

                <!-- Category -->
                <div class="flex items-center gap-1">
                  <span class="material-symbols-outlined">restaurant</span>
                  <span>{{ outlet.outletCategory?.name || "N/A" }}</span>
                </div>

                <!-- Price Range -->
                <div v-if="outlet.priceRange" class="flex items-center gap-1">
                  <span class="material-symbols-outlined">payments</span>
                  <span>{{ outlet.priceRange }}</span>
                </div>
              </div>
            </div>

            <!-- Tabs -->
            <div class="border-b border-border-light dark:border-border-dark">
              <nav class="flex gap-6">
                <button
                  v-for="tab in tabs"
                  :key="tab.id"
                  @click="activeTab = tab.id"
                  :class="[
                    'pb-3 px-1 text-sm font-medium border-b-2 transition-colors',
                    activeTab === tab.id
                      ? 'border-primary text-primary'
                      : 'border-transparent text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark',
                  ]"
                >
                  {{ tab.label }}
                </button>
              </nav>
            </div>

            <!-- Tab Content -->
            <div class="py-4">
              <!-- Overview Tab -->
              <div v-if="activeTab === 'overview'" class="space-y-6">
                <!-- Description -->
                <div>
                  <h3 class="text-lg font-semibold mb-3">Giới thiệu</h3>
                  <p
                    class="text-subtext-light dark:text-subtext-dark leading-relaxed"
                  >
                    {{ outlet.description || "Chưa có mô tả" }}
                  </p>
                </div>

                <!-- Address & Contact -->
                <div>
                  <h3 class="text-lg font-semibold mb-3">Thông tin liên hệ</h3>
                  <div class="space-y-3">
                    <div class="flex items-start gap-3">
                      <span class="material-symbols-outlined text-primary"
                        >location_on</span
                      >
                      <div>
                        <p class="font-medium">Địa chỉ</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.address }}, {{ outlet.district?.name }},
                          {{ outlet.province?.name }}
                        </p>
                      </div>
                    </div>

                    <div
                      v-if="outlet.phoneNumber"
                      class="flex items-start gap-3"
                    >
                      <span class="material-symbols-outlined text-primary"
                        >call</span
                      >
                      <div>
                        <p class="font-medium">Số điện thoại</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.phoneNumber }}
                        </p>
                      </div>
                    </div>

                    <div v-if="outlet.email" class="flex items-start gap-3">
                      <span class="material-symbols-outlined text-primary"
                        >mail</span
                      >
                      <div>
                        <p class="font-medium">Email</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.email }}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Operating Hours -->
                <div
                  v-if="
                    outlet.operatingHours && outlet.operatingHours.length > 0
                  "
                >
                  <h3 class="text-lg font-semibold mb-3">Giờ mở cửa</h3>
                  <div class="space-y-2">
                    <div
                      v-for="hours in outlet.operatingHours"
                      :key="hours.dayOfWeek"
                      class="flex justify-between items-center py-2 border-b border-border-light dark:border-border-dark last:border-0"
                    >
                      <span class="font-medium">{{
                        getDayName(hours.dayOfWeek)
                      }}</span>
                      <span class="text-subtext-light dark:text-subtext-dark">
                        {{ hours.openTime }} - {{ hours.closeTime }}
                      </span>
                    </div>
                  </div>
                </div>

                <!-- Features -->
                <div v-if="outlet.features && outlet.features.length > 0">
                  <h3 class="text-lg font-semibold mb-3">Tiện ích</h3>
                  <div class="flex flex-wrap gap-2">
                    <span
                      v-for="feature in outlet.features"
                      :key="feature.id"
                      class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm font-medium"
                    >
                      {{ feature.name }}
                    </span>
                  </div>
                </div>
              </div>

              <!-- Menu Tab -->
              <div v-else-if="activeTab === 'menu'" class="space-y-4">
                <!-- Loading state for menu -->
                <div v-if="isLoadingMenu" class="flex justify-center py-12">
                  <div
                    class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary"
                  ></div>
                </div>

                <!-- Menu items -->
                <div
                  v-else-if="menuItems && menuItems.length > 0"
                  class="grid grid-cols-1 md:grid-cols-2 gap-4"
                >
                  <div
                    v-for="item in menuItems"
                    :key="item.id"
                    class="flex gap-4 p-4 border border-border-light dark:border-border-dark rounded-lg hover:shadow-lg transition-shadow"
                    :class="{
                      'opacity-50':
                        item.isAvailable === false ||
                        item.is_available === false,
                    }"
                  >
                    <img
                      v-if="item.image || item.imageUrl || item.image_url"
                      :src="item.image || item.imageUrl || item.image_url"
                      :alt="item.name"
                      class="w-24 h-24 object-cover rounded-lg"
                      @error="$event.target.style.display = 'none'"
                    />
                    <div
                      v-else
                      class="w-24 h-24 bg-gray-200 dark:bg-gray-700 rounded-lg flex items-center justify-center flex-shrink-0"
                    >
                      <span class="material-symbols-outlined text-gray-400"
                        >restaurant</span
                      >
                    </div>
                    <div class="flex-1">
                      <div class="flex items-start justify-between mb-1">
                        <h4 class="font-semibold">{{ item.name }}</h4>
                        <span
                          v-if="
                            item.isAvailable === false ||
                            item.is_available === false
                          "
                          class="text-xs text-red-500 ml-2"
                        >
                          Hết món
                        </span>
                      </div>
                      <p
                        class="text-sm text-subtext-light dark:text-subtext-dark mb-2 line-clamp-2"
                      >
                        {{ item.description || "Chưa có mô tả" }}
                      </p>
                      <p class="text-primary font-bold">
                        {{ formatPrice(item.price) }}
                      </p>
                    </div>
                  </div>
                </div>

                <!-- Empty state -->
                <div
                  v-else
                  class="text-center py-12 text-subtext-light dark:text-subtext-dark"
                >
                  <span
                    class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4"
                    >restaurant_menu</span
                  >
                  <p>Chưa có thực đơn</p>
                </div>
              </div>

              <!-- Reviews Tab -->
              <div v-else-if="activeTab === 'reviews'" class="space-y-4">
                <!-- Loading state for reviews -->
                <div v-if="isLoadingReviews" class="flex justify-center py-12">
                  <div
                    class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary"
                  ></div>
                </div>

                <!-- Reviews list -->
                <div
                  v-else-if="reviews && reviews.length > 0"
                  class="space-y-6"
                >
                  <div
                    v-for="review in reviews"
                    :key="review.id"
                    class="p-4 border border-border-light dark:border-border-dark rounded-lg"
                  >
                    <div class="flex items-start gap-4">
                      <div
                        class="w-10 h-10 rounded-full bg-primary/20 flex items-center justify-center"
                      >
                        <span class="material-symbols-outlined text-primary"
                          >person</span
                        >
                      </div>
                      <div class="flex-1">
                        <div class="flex items-center justify-between mb-2">
                          <h4 class="font-semibold">
                            {{
                              review.userFullName ||
                              review.userName ||
                              "Anonymous"
                            }}
                          </h4>
                          <div class="flex items-center gap-1">
                            <span
                              class="material-symbols-outlined text-yellow-500 text-sm"
                              >star</span
                            >
                            <span class="text-sm font-medium">{{
                              review.rating
                            }}</span>
                          </div>
                        </div>
                        <p
                          class="text-subtext-light dark:text-subtext-dark mb-2"
                        >
                          {{ review.comment || review.reviewText }}
                        </p>
                        <p
                          class="text-xs text-subtext-light dark:text-subtext-dark"
                        >
                          {{ formatDate(review.createdAt) }}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Empty state -->
                <div
                  v-else
                  class="text-center py-12 text-subtext-light dark:text-subtext-dark"
                >
                  <span
                    class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4"
                    >rate_review</span
                  >
                  <p>Chưa có đánh giá</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column - Booking Card -->
          <div class="lg:col-span-1">
            <div
              class="sticky top-20 bg-white dark:bg-surface-dark border border-border-light dark:border-border-dark rounded-xl p-6 shadow-lg"
            >
              <div class="mb-6">
                <div class="text-3xl font-bold text-primary mb-2">
                  {{ formatPrice(outlet.averagePrice || 0) }}
                </div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  Giá trung bình / người
                </p>
              </div>

              <router-link
                :to="`/booking/${outlet.id}`"
                class="block w-full bg-primary text-white text-center font-bold py-3 rounded-lg hover:bg-opacity-90 transition-colors mb-4"
              >
                Đặt bàn ngay
              </router-link>

              <div
                class="space-y-3 pt-4 border-t border-border-light dark:border-border-dark"
              >
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >schedule</span
                  >
                  <span>Đặt bàn trực tuyến</span>
                </div>
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >verified</span
                  >
                  <span>Xác nhận ngay lập tức</span>
                </div>
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >cancel</span
                  >
                  <span>Hủy miễn phí</span>
                </div>
              </div>

              <!-- Share -->
              <div
                class="mt-6 pt-6 border-t border-border-light dark:border-border-dark"
              >
                <p class="text-sm font-medium mb-3">Chia sẻ</p>
                <div class="flex gap-2">
                  <button
                    class="p-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                  >
                    <span class="material-symbols-outlined">share</span>
                  </button>
                  <button
                    class="p-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                  >
                    <span class="material-symbols-outlined">link</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed, watch} from "vue";
import {useRoute} from "vue-router";
import {outletApi} from "@/api";
import {menuApi} from "@/api/menu";
import {reviewApi} from "@/api/review";

const route = useRoute();

// State
const outlet = ref(null);
const menuItems = ref([]);
const reviews = ref([]);
const isLoading = ref(false);
const isLoadingMenu = ref(false);
const isLoadingReviews = ref(false);
const error = ref(null);
const activeTab = ref("overview");

// Tabs configuration
const tabs = [
  {id: "overview", label: "Tổng quan"},
  {id: "menu", label: "Thực đơn"},
  {id: "reviews", label: "Đánh giá"},
];

// Fetch outlet details
const fetchOutletDetail = async () => {
  isLoading.value = true;
  error.value = null;

  try {
    console.log("🔍 Fetching outlet detail for ID:", route.params.id);
    const data = await outletApi.getOutletDetail(route.params.id);
    console.log("✅ Outlet data received:", data);
    console.log(
      "📊 Full outlet object keys:",
      data ? Object.keys(data) : "null"
    );
    outlet.value = data;

    // Check if menu items or reviews are already included in outlet response
    if (data?.menuItems && Array.isArray(data.menuItems)) {
      console.log("📋 Menu items found in outlet data:", data.menuItems.length);
      console.log("📋 First menu item:", data.menuItems[0]);
      menuItems.value = data.menuItems;
    } else {
      console.log(
        "⚠️ No menuItems in outlet data. Available fields:",
        data ? Object.keys(data) : "null"
      );
    }

    if (data?.reviews && Array.isArray(data.reviews)) {
      console.log("⭐ Reviews found in outlet data:", data.reviews.length);
      console.log("⭐ First review:", data.reviews[0]);
      reviews.value = data.reviews;
    } else {
      console.log(
        "⚠️ No reviews in outlet data. Available fields:",
        data ? Object.keys(data) : "null"
      );
      console.log("🔍 Checking alternative field names...");
      // Check for alternative field names
      if (data?.reviewList) {
        console.log("✅ Found reviewList instead!", data.reviewList.length);
        reviews.value = data.reviewList;
      }
    }

    console.log(
      "📊 Final state - menuItems count:",
      menuItems.value.length,
      "reviews count:",
      reviews.value.length
    );
  } catch (err) {
    console.error("❌ Error fetching outlet:", err);
    error.value = err.message || "Không thể tải thông tin địa điểm";
  } finally {
    isLoading.value = false;
  }
};

// Fetch menu items
const fetchMenuItems = async () => {
  if (menuItems.value.length > 0) return; // Already loaded

  // Check if already in outlet data
  if (outlet.value?.menuItems && Array.isArray(outlet.value.menuItems)) {
    console.log("✅ Using menu items from outlet data");
    menuItems.value = outlet.value.menuItems;
    return;
  }

  isLoadingMenu.value = true;
  try {
    console.log("🍽️ Fetching menu items for outlet:", route.params.id);
    const data = await menuApi.getOutletMenuItems(route.params.id, {
      page: 0,
      size: 20,
    });
    console.log("✅ Menu items API response:", data);
    console.log("📊 Response type:", typeof data);
    console.log("📊 Is array?", Array.isArray(data));
    console.log("📊 Has data property?", data?.data);
    console.log("📊 Response keys:", data ? Object.keys(data) : null);

    // Check if response is PageResponse
    const items = data?.data || data || [];
    console.log("📦 Extracted items:", items);
    console.log(
      "📦 Items count:",
      Array.isArray(items) ? items.length : "not array"
    );

    if (items.length > 0) {
      console.log("🔍 First menu item structure:", items[0]);
      console.log("🔍 First menu item keys:", Object.keys(items[0]));
    }

    menuItems.value = items;
  } catch (err) {
    console.warn("⚠️ Could not fetch menu items:", err.message);
    console.error("⚠️ Full error:", err);
    console.log("💡 Menu endpoint may not be available yet or returning 500");
    // Don't throw, just leave empty
    menuItems.value = [];
  } finally {
    isLoadingMenu.value = false;
    console.log("🏁 Final menuItems.value:", menuItems.value);
  }
};

// Fetch reviews
const fetchReviews = async () => {
  if (reviews.value.length > 0) return; // Already loaded

  // Check if already in outlet data
  if (outlet.value?.reviews && Array.isArray(outlet.value.reviews)) {
    console.log("✅ Using reviews from outlet data");
    reviews.value = outlet.value.reviews;
    return;
  }

  isLoadingReviews.value = true;
  try {
    console.log("⭐ Fetching reviews for outlet:", route.params.id);
    const data = await reviewApi.getOutletReviews(route.params.id, {
      page: 0,
      size: 10,
    });
    console.log("✅ Reviews received:", data);

    // Check if response is PageResponse
    reviews.value = data?.data || data || [];
  } catch (err) {
    console.warn("⚠️ Could not fetch reviews:", err.message);
    console.log("💡 Reviews endpoint may not be available yet");
    // Don't throw, just leave empty
    reviews.value = [];
  } finally {
    isLoadingReviews.value = false;
  }
};

// Watch tab changes to load data
watch(activeTab, (newTab) => {
  console.log("📑 Tab changed to:", newTab);

  // Re-enabled API calls with graceful error handling
  // Will show empty state if backend endpoints return 500
  if (newTab === "menu" && menuItems.value.length === 0) {
    fetchMenuItems();
  } else if (newTab === "reviews" && reviews.value.length === 0) {
    fetchReviews();
  }
});

// Helper functions
const getDayName = (dayOfWeek) => {
  const days = {
    MONDAY: "Thứ Hai",
    TUESDAY: "Thứ Ba",
    WEDNESDAY: "Thứ Tư",
    THURSDAY: "Thứ Năm",
    FRIDAY: "Thứ Sáu",
    SATURDAY: "Thứ Bảy",
    SUNDAY: "Chủ Nhật",
  };
  return days[dayOfWeek] || dayOfWeek;
};

const formatPrice = (price) => {
  if (!price) return "N/A";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("vi-VN", {
    year: "numeric",
    month: "long",
    day: "numeric",
  }).format(date);
};

// Lifecycle
onMounted(() => {
  fetchOutletDetail();
});
</script>
