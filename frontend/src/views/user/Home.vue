<template>
  <div class="w-full">
    <!-- Hero Section -->
    <section class="w-full py-16 sm:py-20">
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
              <div class="flex flex-col gap-2">
                <h1
                  class="text-white text-4xl font-black leading-tight tracking-[-0.033em] @[480px]:text-5xl"
                >
                  Khám phá Ẩm thực Sài Gòn
                </h1>
                <h2
                  class="text-white text-sm font-normal leading-normal @[480px]:text-base"
                >
                  Tìm kiếm hàng ngàn quán ăn, đồ uống tuyệt vời tại TPHCM.
                </h2>
              </div>

              <!-- Search Bar -->
              <div class="flex w-full max-w-[560px]">
                <div
                  class="flex w-full flex-1 items-stretch rounded-xl h-14 shadow-lg"
                >
                  <div
                    class="flex border border-border-light bg-background-light items-center justify-center pl-4 rounded-l-xl border-r-0"
                  >
                    <span class="material-symbols-outlined text-subtext-light"
                      >search</span
                    >
                  </div>
                  <input
                    v-model="searchQuery"
                    type="text"
                    placeholder="Tìm món ăn, nhà hàng, địa điểm..."
                    class="flex-1 border-y border-border-light bg-background-light px-2 text-sm focus:outline-none"
                  />
                  <div
                    class="flex items-center justify-center rounded-r-xl border border-border-light bg-background-light pr-2"
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
                  :key="filter"
                  class="h-8 px-4 rounded-full bg-white/20 text-white hover:bg-white/30 transition-colors text-sm font-medium"
                >
                  {{ filter }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Category Section -->
    <section class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <h2
        class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight mb-4"
      >
        Bạn muốn ăn gì hôm nay?
      </h2>
      <div class="grid grid-cols-2 gap-4 md:grid-cols-4">
        <div
          v-for="category in categories"
          :key="category.name"
          class="group relative cursor-pointer overflow-hidden rounded-xl"
        >
          <div
            class="bg-cover bg-center flex flex-col justify-end p-4 aspect-[4/3] transition-transform duration-300 group-hover:scale-105"
            :style="`background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 60%), url(${category.image});`"
          >
            <p class="text-white text-base font-bold leading-tight">
              {{ category.name }}
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- Featured Restaurants Section -->
    <section class="w-full py-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <h2
        class="text-text-light dark:text-text-dark text-[22px] font-bold leading-tight mb-4"
      >
        Quán ngon không thể bỏ lỡ
      </h2>
      <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <div
          v-for="restaurant in featuredRestaurants"
          :key="restaurant.id"
          @click="viewOutletDetail(restaurant.id)"
          class="group flex cursor-pointer flex-col overflow-hidden rounded-xl bg-white dark:bg-surface-dark shadow-md transition-shadow hover:shadow-xl"
        >
          <div class="relative overflow-hidden">
            <div
              class="h-48 w-full bg-gray-300 transition-transform duration-300 group-hover:scale-105"
            ></div>
            <div
              class="absolute top-3 left-3 rounded-full bg-primary/90 px-3 py-1 text-xs font-bold text-white"
            >
              {{ restaurant.badge }}
            </div>
          </div>
          <div class="flex flex-col gap-2 p-4">
            <h3 class="text-lg font-bold text-text-light dark:text-text-dark">
              {{ restaurant.name }}
            </h3>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              {{ restaurant.category }} • {{ restaurant.district }}
            </p>
            <div class="flex items-center gap-1">
              <span
                class="material-symbols-outlined fill text-yellow-500 text-base"
                >star</span
              >
              <span class="text-sm font-bold">{{ restaurant.rating }}</span>
              <span class="text-sm text-subtext-light dark:text-subtext-dark"
                >({{ restaurant.reviews }}+ đánh giá)</span
              >
            </div>
            <div class="flex items-center gap-2 mt-2">
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
    </section>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {useOutletStore} from "@/stores/outlet";

const router = useRouter();
const outletStore = useOutletStore();

const searchQuery = ref("");
const quickFilters = ref(["Gần tôi", "Mới nhất", "Đang khuyến mãi", "Đặt bàn"]);

const categories = ref([
  {
    name: "Cà phê",
    image: "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400",
  },
  {
    name: "Bún/Phở",
    image: "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400",
  },
  {
    name: "Trà sữa",
    image: "https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=400",
  },
  {
    name: "Ăn vặt",
    image: "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=400",
  },
]);

const featuredRestaurants = ref([]);

onMounted(async () => {
  try {
    // Fetch featured outlets from API - remove sort for now to test
    await outletStore.fetchOutlets({page: 0, size: 6});

    // Map outlets to featured restaurants format
    if (outletStore.outlets && outletStore.outlets.length > 0) {
      featuredRestaurants.value = outletStore.outlets.map((outlet) => ({
        id: outlet.id,
        name: outlet.name,
        category: outlet.outletCategory?.name || "Nhà hàng",
        district: outlet.district?.name || "TPHCM",
        rating: outlet.averageRating || 4.5,
        reviews: outlet.totalReviews || 0,
        badge: outlet.featured ? "Nổi bật" : "Mới",
        hours: outlet.openingHours || "8:00 - 22:00",
      }));
    } else {
      // No data from API, use fallback
      throw new Error("No outlets data");
    }
  } catch (error) {
    console.error("Failed to load outlets:", error);
    // Fallback to mock data if API fails
    featuredRestaurants.value = [
      {
        id: 1,
        name: "The Vintage Eatery",
        category: "Món Âu",
        district: "Quận 1",
        rating: 4.8,
        reviews: 120,
        badge: "Nổi bật",
        hours: "8:00 - 22:00",
      },
      {
        id: 2,
        name: "Phố Cổ Café",
        category: "Café",
        district: "Quận 3",
        rating: 4.5,
        reviews: 89,
        badge: "Mới",
        hours: "7:00 - 23:00",
      },
      {
        id: 3,
        name: "BBQ House",
        category: "Nướng",
        district: "Quận 7",
        rating: 4.7,
        reviews: 156,
        badge: "Hot",
        hours: "10:00 - 23:00",
      },
    ];
  }
});

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({path: "/search", query: {q: searchQuery.value}});
  }
};

const viewOutletDetail = (id) => {
  router.push(`/outlet/${id}`);
};
</script>
