<template>
  <div class="w-full">
    <!-- Premium Hero Section -->
    <section class="w-full py-20 sm:py-28 relative overflow-hidden">
      <!-- Animated Background -->
      <div class="absolute inset-0 bg-gradient-to-br from-primary/20 via-primary/10 to-transparent"></div>
      <div 
        class="absolute inset-0 bg-cover bg-center bg-no-repeat opacity-20"
        style="background-image: url('https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=1200');"
      ></div>
      
      <!-- Floating decorative elements -->
      <div class="absolute top-20 left-10 w-72 h-72 bg-primary/10 rounded-full blur-3xl animate-float"></div>
      <div class="absolute bottom-20 right-10 w-96 h-96 bg-primary/5 rounded-full blur-3xl animate-float" style="animation-delay: 1.5s;"></div>
      
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div class="flex flex-col items-center justify-center min-h-[600px] text-center">
          <!-- Hero Content -->
          <div class="flex flex-col gap-4 mb-8 animate-fade-in-scale">
            <div class="inline-block">
              <span class="text-sm font-semibold text-primary bg-primary/10 px-4 py-2 rounded-full backdrop-blur-sm">
                🍜 Khám phá Ẩm thực Việt Nam
              </span>
            </div>
            <h1 class="text-5xl sm:text-6xl lg:text-7xl font-black leading-tight tracking-tight">
              <span class="text-gradient-primary">Khám phá</span>
              <br />
              <span class="text-text-light dark:text-text-dark">Ẩm thực Sài Gòn</span>
            </h1>
            <p class="text-lg sm:text-xl text-subtext-light dark:text-subtext-dark max-w-2xl mx-auto leading-relaxed">
              Tìm kiếm hàng ngàn quán ăn, đồ uống tuyệt vời tại TPHCM. 
              <span class="text-primary font-semibold">Đặt bàn ngay hôm nay!</span>
            </p>
          </div>

          <!-- Premium Search Bar -->
          <div class="w-full max-w-3xl mb-6 animate-fade-in-scale stagger-1">
            <div class="glass-premium rounded-2xl p-2 shadow-premium-lg">
              <SearchAutocomplete
                v-model="searchQuery"
                placeholder="Tìm món ăn, nhà hàng, địa điểm..."
                @search="handleSearch"
                @select="handleSearchSelect"
                class="w-full"
              >
                <template #input="{ value, onInput, onFocus, onBlur, onKeydown }">
                  <div class="flex w-full items-center rounded-xl bg-white dark:bg-surface-dark shadow-lg overflow-hidden">
                    <div class="flex items-center justify-center pl-5 pr-3">
                      <span class="material-symbols-outlined text-2xl text-primary">search</span>
                    </div>
                    <input
                      :value="value"
                      @input="onInput"
                      @focus="onFocus"
                      @blur="onBlur"
                      @keydown="onKeydown"
                      @keyup.enter="handleSearch"
                      type="text"
                      placeholder="Tìm món ăn, nhà hàng, địa điểm..."
                      class="flex-1 py-4 px-2 text-base text-text-light dark:text-text-dark bg-transparent focus:outline-none placeholder:text-subtext-light dark:placeholder:text-subtext-dark"
                    />
                    <div class="flex items-center pr-2">
                      <button
                        @click="handleSearch"
                        class="btn-premium text-white px-6 py-3 rounded-lg font-bold"
                      >
                        Tìm kiếm
                      </button>
                    </div>
                  </div>
                </template>
              </SearchAutocomplete>
            </div>
          </div>

          <!-- Quick filters with premium style -->
          <div class="flex gap-3 flex-wrap justify-center animate-fade-in-scale stagger-2">
            <button
              v-for="(filter, index) in quickFilters"
              :key="filter.key"
              @click="handleQuickFilter(filter.key)"
              class="group glass-premium px-6 py-3 rounded-full text-white hover:bg-white/30 transition-premium hover-lift backdrop-blur-md border border-white/20"
              :style="{ animationDelay: `${index * 0.1}s` }"
            >
              <span class="flex items-center gap-2 text-sm font-semibold">
                <span class="material-symbols-outlined text-base">{{ filter.icon || 'restaurant' }}</span>
                {{ filter.label }}
              </span>
            </button>
            <!-- Admin Management Button -->
            <button
              v-if="authStore.isAdmin"
              @click="router.push('/admin')"
              class="group glass-premium px-6 py-3 rounded-full text-white hover:bg-yellow-400/30 transition-premium hover-lift backdrop-blur-md border border-yellow-400/30 animate-fade-in-scale"
              style="animation-delay: 0.4s"
            >
              <span class="flex items-center gap-2 text-sm font-semibold">
                <span class="material-symbols-outlined text-base">admin_panel_settings</span>
                Quản lý Admin
              </span>
            </button>
            <!-- Owner Management Button -->
            <button
              v-if="authStore.isOwner"
              @click="router.push('/owner')"
              class="group glass-premium px-6 py-3 rounded-full text-white hover:bg-purple-400/30 transition-premium hover-lift backdrop-blur-md border border-purple-400/30 animate-fade-in-scale"
              style="animation-delay: 0.4s"
            >
              <span class="flex items-center gap-2 text-sm font-semibold">
                <span class="material-symbols-outlined text-base">storefront</span>
                Quản lý Quán
              </span>
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- Premium Statistics Section -->
    <section class="w-full py-20 bg-gradient-to-br from-primary/5 via-white to-primary/5 dark:from-primary/10 dark:via-surface-dark dark:to-primary/10 relative overflow-hidden">
      <!-- Decorative background elements -->
      <div class="absolute inset-0 opacity-30">
        <div class="absolute top-10 left-10 w-64 h-64 bg-primary/5 rounded-full blur-3xl"></div>
        <div class="absolute bottom-10 right-10 w-80 h-80 bg-primary/5 rounded-full blur-3xl"></div>
      </div>
      
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div class="text-center mb-12">
          <h2 class="text-3xl sm:text-4xl font-black text-text-light dark:text-text-dark mb-3">
            Số liệu thống kê
          </h2>
          <p class="text-subtext-light dark:text-subtext-dark text-lg">
            Hàng ngàn quán ăn và món ngon đang chờ bạn khám phá
          </p>
        </div>
        
        <div class="grid grid-cols-2 md:grid-cols-4 gap-6 lg:gap-8">
          <div
            v-for="(stat, index) in statistics"
            :key="stat.label"
            :style="{ animationDelay: `${index * 0.1}s` }"
            class="group card-premium text-center p-6 lg:p-8 hover-lift animate-fade-in-scale"
          >
            <div class="flex justify-center mb-4">
              <div class="p-4 bg-primary/10 dark:bg-primary/20 rounded-2xl group-hover:bg-primary/20 dark:group-hover:bg-primary/30 group-hover:scale-110 transition-premium">
                <span class="material-symbols-outlined text-4xl text-primary">
                  {{ stat.icon }}
                </span>
              </div>
            </div>
            <div v-if="stat.loading" class="text-4xl font-black text-primary mb-3 min-h-[48px] flex items-center justify-center">
              <div class="inline-block h-10 w-20 bg-gray-200 dark:bg-gray-700 rounded-lg animate-pulse"></div>
            </div>
            <div v-else class="text-4xl lg:text-5xl font-black text-gradient-primary mb-3 min-h-[48px] flex items-center justify-center">
              {{ stat.value }}
            </div>
            <div class="text-sm text-subtext-light dark:text-subtext-dark font-semibold uppercase tracking-wide">
              {{ stat.label }}
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Category Section -->
    <section class="w-full py-12 lg:py-16 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between mb-8 gap-4">
        <div>
          <h2 class="text-3xl sm:text-4xl font-black text-text-light dark:text-text-dark mb-2">
            Bạn muốn ăn gì hôm nay?
          </h2>
          <p class="text-subtext-light dark:text-subtext-dark text-base">
            Khám phá các danh mục ẩm thực phong phú
          </p>
        </div>
        <button
          @click="router.push('/search')"
          class="group flex items-center gap-2 px-6 py-3 bg-primary/10 dark:bg-primary/20 text-primary rounded-xl font-semibold hover:bg-primary hover:text-white transition-all duration-300 hover:scale-105"
        >
          <span>Xem tất cả</span>
          <span class="material-symbols-outlined text-lg group-hover:translate-x-1 transition-transform">arrow_forward</span>
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
      
      <!-- Premium Categories Grid -->
      <div v-else-if="categories.length > 0" class="grid grid-cols-2 gap-4 md:grid-cols-4">
        <div
          v-for="(category, index) in categories"
          :key="category.id"
          @click="handleCategoryClick(category.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group relative cursor-pointer overflow-hidden rounded-2xl shadow-premium hover:shadow-premium-lg transition-premium animate-fade-in-scale image-overlay"
        >
          <div
            class="bg-cover bg-center flex flex-col justify-end p-6 aspect-[4/3] transition-transform duration-500 group-hover:scale-110"
            :style="`background-image: linear-gradient(180deg, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.4) 50%, rgba(0, 0, 0, 0.8) 100%), url(${category.image});`"
          >
            <div class="relative z-10">
              <p class="text-white text-lg font-black leading-tight drop-shadow-lg mb-1">
                {{ category.name }}
              </p>
              <div class="flex items-center gap-1 text-white/90 text-sm">
                <span class="material-symbols-outlined text-base">arrow_forward</span>
                <span class="font-medium">Khám phá</span>
              </div>
            </div>
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
    <section class="w-full py-12 lg:py-16 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 bg-gray-50/50 dark:bg-gray-900/30 rounded-3xl my-8">
      <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between mb-8 gap-4">
        <div>
          <h2 class="text-3xl sm:text-4xl font-black text-text-light dark:text-text-dark mb-2">
            Quán ngon không thể bỏ lỡ
          </h2>
          <p class="text-subtext-light dark:text-subtext-dark text-base">
            Những địa điểm ẩm thực được yêu thích nhất
          </p>
        </div>
        <button
          @click="router.push('/search')"
          class="group flex items-center gap-2 px-6 py-3 bg-primary/10 dark:bg-primary/20 text-primary rounded-xl font-semibold hover:bg-primary hover:text-white transition-all duration-300 hover:scale-105"
        >
          <span>Xem tất cả</span>
          <span class="material-symbols-outlined text-lg group-hover:translate-x-1 transition-transform">arrow_forward</span>
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
      
      <!-- Premium Restaurants Grid -->
      <div v-else-if="featuredRestaurants.length > 0" class="grid grid-cols-1 gap-6 sm:gap-8 sm:grid-cols-2 lg:grid-cols-3">
        <div
          v-for="(restaurant, index) in featuredRestaurants"
          :key="restaurant.id"
          @click="viewOutletDetail(restaurant.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group card-premium cursor-pointer overflow-hidden animate-fade-in-scale hover-lift"
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
    <section v-if="popularMenuItems.length > 0" class="w-full py-12 lg:py-16 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between mb-8 gap-4">
        <div>
          <h2 class="text-3xl sm:text-4xl font-black text-text-light dark:text-text-dark mb-2">
            Món ăn phổ biến
          </h2>
          <p class="text-subtext-light dark:text-subtext-dark text-base">
            Những món ăn được yêu thích nhất
          </p>
        </div>
        <button
          @click="router.push('/search')"
          class="group flex items-center gap-2 px-6 py-3 bg-primary/10 dark:bg-primary/20 text-primary rounded-xl font-semibold hover:bg-primary hover:text-white transition-all duration-300 hover:scale-105"
        >
          <span>Xem tất cả</span>
          <span class="material-symbols-outlined text-lg group-hover:translate-x-1 transition-transform">arrow_forward</span>
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
              :image-url="item.imageUrl"
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
    <section v-if="topRatedRestaurants.length > 0" class="w-full py-12 lg:py-16 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 bg-gradient-to-br from-yellow-50/50 to-orange-50/50 dark:from-yellow-900/10 dark:to-orange-900/10 rounded-3xl my-8">
      <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between mb-8 gap-4">
        <div>
          <h2 class="text-3xl sm:text-4xl font-black text-text-light dark:text-text-dark mb-2 flex items-center gap-2">
            <span class="text-yellow-500">⭐</span>
            <span>Quán được đánh giá cao nhất</span>
          </h2>
          <p class="text-subtext-light dark:text-subtext-dark text-base">
            Những quán ăn có rating cao nhất từ cộng đồng
          </p>
        </div>
        <button
          @click="router.push({path: '/search', query: {sort: 'averageRating,desc'}})"
          class="group flex items-center gap-2 px-6 py-3 bg-primary/10 dark:bg-primary/20 text-primary rounded-xl font-semibold hover:bg-primary hover:text-white transition-all duration-300 hover:scale-105"
        >
          <span>Xem tất cả</span>
          <span class="material-symbols-outlined text-lg group-hover:translate-x-1 transition-transform">arrow_forward</span>
        </button>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="(restaurant, index) in topRatedRestaurants"
          :key="restaurant.id"
          @click="viewOutletDetail(restaurant.id)"
          :style="{ animationDelay: `${index * 0.1}s` }"
          class="group card-premium cursor-pointer overflow-hidden animate-fade-in-scale hover-lift"
        >
          <div class="relative overflow-hidden">
            <div class="h-48 w-full transition-transform duration-500 group-hover:scale-110">
              <ImageDisplay
                :image-url="restaurant.imageUrl || null"
                :alt="restaurant.name"
                :lazy="true"
                placeholder-icon="restaurant"
                :icon-size="'64px'"
                container-class="w-full h-full bg-gradient-to-br from-yellow-100 to-yellow-200 dark:from-yellow-900 dark:to-yellow-800 flex items-center justify-center"
                image-class="w-full h-full object-cover"
              />
            </div>
            <div
              class="absolute top-4 right-4 rounded-full bg-yellow-500/95 backdrop-blur-sm px-4 py-2 text-sm font-black text-white shadow-xl flex items-center gap-1 z-10"
            >
              <span class="material-symbols-outlined text-base fill">star</span>
              <span>{{ restaurant.rating }}</span>
            </div>
          </div>
          <div class="flex flex-col gap-2 p-5">
            <h3 class="text-lg font-black text-text-light dark:text-text-dark line-clamp-1">
              {{ restaurant.name }}
            </h3>
            <p class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-1">
              {{ restaurant.category }} • {{ restaurant.district }}
            </p>
            <div class="flex items-center gap-2 mt-1">
              <div class="flex items-center gap-1">
                <span class="material-symbols-outlined fill text-yellow-500 text-base">star</span>
                <span class="text-sm font-black text-yellow-600 dark:text-yellow-400">{{ restaurant.rating }}</span>
              </div>
              <span class="text-xs text-subtext-light dark:text-subtext-dark">
                ({{ restaurant.reviews }} đánh giá)
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Call to Action Section -->
    <section class="w-full py-20 lg:py-28 bg-gradient-to-br from-primary via-primary/90 to-primary/80 dark:from-primary/90 dark:via-primary/80 dark:to-primary/70 relative overflow-hidden">
      <!-- Animated background elements -->
      <div class="absolute inset-0 opacity-20">
        <div class="absolute top-10 left-10 w-72 h-72 bg-white/10 rounded-full blur-3xl animate-float"></div>
        <div class="absolute bottom-10 right-10 w-96 h-96 bg-white/10 rounded-full blur-3xl animate-float" style="animation-delay: 1.5s;"></div>
      </div>
      
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10">
        <div class="inline-block mb-4">
          <span class="text-sm font-semibold text-white/90 bg-white/20 px-4 py-2 rounded-full backdrop-blur-sm">
            🍜 Bắt đầu hành trình ẩm thực
          </span>
        </div>
        <h2 class="text-4xl sm:text-5xl lg:text-6xl font-black text-white mb-6 leading-tight">
          Sẵn sàng khám phá<br />
          <span class="text-yellow-200">ẩm thực Sài Gòn?</span>
        </h2>
        <p class="text-white/90 mb-10 text-lg sm:text-xl max-w-2xl mx-auto leading-relaxed">
          Tìm kiếm hàng ngàn quán ăn và món ngon tại Sài Gòn. 
          Đặt bàn ngay hôm nay để trải nghiệm ẩm thực tuyệt vời!
        </p>
        <div class="flex gap-4 justify-center flex-wrap">
          <button
            @click="router.push('/search')"
            class="group px-8 py-4 bg-white text-primary rounded-xl font-black hover:bg-gray-100 transition-all duration-300 shadow-xl hover:shadow-2xl hover:scale-105 flex items-center gap-2"
          >
            <span>Khám phá ngay</span>
            <span class="material-symbols-outlined group-hover:translate-x-1 transition-transform">arrow_forward</span>
          </button>
          <button
            v-if="authStore.isAuthenticated"
            @click="router.push('/booking-history')"
            class="px-8 py-4 bg-white/10 backdrop-blur-md text-white border-2 border-white/30 rounded-xl font-black hover:bg-white/20 hover:border-white/50 transition-all duration-300 shadow-lg hover:shadow-xl hover:scale-105"
          >
            Đặt bàn ngay
          </button>
          <button
            v-else
            @click="router.push('/auth/login')"
            class="px-8 py-4 bg-white/10 backdrop-blur-md text-white border-2 border-white/30 rounded-xl font-black hover:bg-white/20 hover:border-white/50 transition-all duration-300 shadow-lg hover:shadow-xl hover:scale-105"
          >
            Đăng nhập để đặt bàn
          </button>
          <!-- Admin Management Button -->
          <button
            v-if="authStore.isAdmin"
            @click="router.push('/admin')"
            class="group px-8 py-4 bg-yellow-400/90 text-gray-900 rounded-xl font-black hover:bg-yellow-300 transition-all duration-300 shadow-xl hover:shadow-2xl hover:scale-105 flex items-center gap-2"
          >
            <span class="material-symbols-outlined">admin_panel_settings</span>
            <span>Quản lý Admin</span>
          </button>
          <!-- Owner Management Button -->
          <button
            v-if="authStore.isOwner"
            @click="router.push('/owner')"
            class="group px-8 py-4 bg-purple-400/90 text-white rounded-xl font-black hover:bg-purple-300 transition-all duration-300 shadow-xl hover:shadow-2xl hover:scale-105 flex items-center gap-2"
          >
            <span class="material-symbols-outlined">storefront</span>
            <span>Quản lý Quán</span>
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
import {useAuthStore} from "@/stores/auth";
import {menuApi, outletApi, adminApi} from "@/api";
import apiClient from "@/api/axios";
import ImageDisplay from "@/components/common/ImageDisplay.vue";
import SearchAutocomplete from "@/components/common/SearchAutocomplete.vue";
import {processImageUrl} from "@/utils/imageUtils";

const router = useRouter();
const outletStore = useOutletStore();
const authStore = useAuthStore();

const searchQuery = ref("");
const searchFocused = ref(false);
const quickFilters = ref([
  { key: "nearby", label: "Gần tôi", icon: "near_me" },
  { key: "newest", label: "Mới nhất", icon: "new_releases" },
  { key: "promotion", label: "Đang khuyến mãi", icon: "local_offer" },
  { key: "booking", label: "Đặt bàn", icon: "event_available" },
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
  
  // Debug: Log user role info
  if (authStore.isAuthenticated) {
    console.log("🔍 [Home] User info:", {
      user: authStore.user,
      isAdmin: authStore.isAdmin,
      isOwner: authStore.isOwner,
      roleName: authStore.user?.roleName,
      roleType: authStore.user?.roleType,
      role: authStore.user?.role,
    });
  }
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
        // Get first image from outlet.images array
        const firstImage = outlet.images && Array.isArray(outlet.images) && outlet.images.length > 0 
          ? outlet.images[0] 
          : null;
        
        return {
          id: outlet.id,
          name: outlet.name,
          category: outlet.outletCategory?.name || outlet.outletTypeName || "Nhà hàng",
          district: outlet.districtName || outlet.district?.name || "TPHCM",
          rating: Number(rating).toFixed(1),
          reviews: outlet.totalReviews ?? 0,
          imageUrl: firstImage, // Add image URL for ImageDisplay
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
        // Try to get image from outlet menu items using the new API endpoint
        try {
          const imageResponse = await menuApi.getMenuItemImage(item.id);
          console.log(`📸 Image response for menu item ${item.id}:`, imageResponse);
          
          // Handle different response formats
          let imageUrl = null;
          if (typeof imageResponse === 'string') {
            // Direct string response
            imageUrl = imageResponse;
          } else if (imageResponse?.data) {
            // BaseResponse wrapper
            imageUrl = imageResponse.data;
          } else if (imageResponse) {
            // Try to extract from response object
            imageUrl = imageResponse;
          }
          
          // Only use non-empty strings
          if (imageUrl && typeof imageUrl === 'string' && imageUrl.trim().length > 0) {
            console.log(`✅ Found image for ${item.name}:`, imageUrl);
            return {
              ...item,
              imageUrl: imageUrl,
            };
          } else {
            console.log(`⚠️ No image found for ${item.name}`);
            return {
              ...item,
              imageUrl: null,
            };
          }
        } catch (err) {
          // If API fails, return item without image (ImageDisplay will show placeholder)
          console.warn(`❌ Failed to get image for menu item ${item.id}:`, err);
          return {
            ...item,
            imageUrl: item.imageUrl || null,
          };
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
    // Use new public statistics API endpoint
    try {
      const statsResponse = await outletApi.getPublicStatistics();
      console.log("📊 Public statistics response:", statsResponse);
      
      // Extract data from BaseResponse wrapper
      const stats = statsResponse?.data || statsResponse;
      
      if (stats) {
        statistics.value[0].value = formatNumber(stats.totalOutlets || 0);
        statistics.value[0].loading = false;
        
        statistics.value[1].value = formatNumber(stats.totalMenuItems || 0);
        statistics.value[1].loading = false;
        
        statistics.value[2].value = formatNumber(stats.totalReviews || 0);
        statistics.value[2].loading = false;
        
        statistics.value[3].value = formatNumber(stats.totalUsers || 0);
        statistics.value[3].loading = false;
        
        console.log("✅ Statistics loaded:", {
          outlets: statistics.value[0].value,
          menuItems: statistics.value[1].value,
          reviews: statistics.value[2].value,
          users: statistics.value[3].value,
        });
        return; // Success, exit early
      }
    } catch (statsError) {
      console.warn("⚠️ Public statistics API failed, falling back to individual APIs:", statsError);
    }
    
    // Fallback: Load all statistics in parallel using individual APIs
    const [outletsResponse, menuItemsResponse, reviewsResponse] = await Promise.allSettled([
      outletApi.searchOutlets({page: 0, size: 1}),
      menuApi.searchMasterMenuItems({page: 0, size: 1}),
      // Get all reviews by calling search without outletId filter
      apiClient.get("/reviews/search", {params: {page: 0, size: 1}}).catch(err => {
        console.warn("Failed to fetch reviews:", err);
        return null;
      }),
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
    if (reviewsResponse.status === 'fulfilled' && reviewsResponse.value) {
      const response = reviewsResponse.value;
      console.log("📊 Reviews response:", response);
      // Axios interceptor returns full PageResponse/Spring Data Page object
      // Check multiple possible response formats
      const total = response?.totalElements || 
                    response?.total || 
                    (response?.content ? response.content.length : 0) ||
                    (Array.isArray(response) ? response.length : 0) ||
                    0;
      console.log("📊 Reviews total:", total);
      statistics.value[2].value = formatNumber(total);
      statistics.value[2].loading = false;
    } else {
      console.warn("⚠️ Reviews response failed or empty:", reviewsResponse);
      statistics.value[2].value = "0";
      statistics.value[2].loading = false;
    }

    // Users count - try to get from admin API first (only if user is admin)
    // Skip if user is not authenticated or not admin
    let usersCountLoaded = false;
    if (authStore.isAuthenticated && authStore.isAdmin) {
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
    }
    
    // If admin API didn't work, try to estimate from reviews
    if (!usersCountLoaded && reviewsResponse.status === 'fulfilled' && reviewsResponse.value) {
      try {
        // Get reviews to count unique users
        const reviewsData = reviewsResponse.value;
        const totalReviews = reviewsData?.totalElements || 
                            reviewsData?.total || 
                            (reviewsData?.content ? reviewsData.content.length : 0) ||
                            0;
        
        console.log("📊 Total reviews for user estimation:", totalReviews);
        
        if (totalReviews > 0) {
          // Get a larger sample to estimate better (max 200 reviews)
          const sampleSize = Math.min(200, totalReviews);
          const sampleResponse = await apiClient.get("/reviews/search", {
            params: {page: 0, size: sampleSize}
          }).catch(err => {
            console.warn("Failed to fetch sample reviews:", err);
            return null;
          });
          
          if (sampleResponse) {
            const sampleReviews = sampleResponse?.content || 
                                 sampleResponse?.data || 
                                 (Array.isArray(sampleResponse) ? sampleResponse : []);
            
            console.log("📊 Sample reviews count:", sampleReviews.length);
            
            const uniqueUserIds = new Set();
            
            sampleReviews.forEach(review => {
              if (review?.userId) {
                uniqueUserIds.add(review.userId);
              }
            });
            
            console.log("📊 Unique users from reviews:", uniqueUserIds.size);
            
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

const handleQuickFilter = async (filterKey) => {
  switch (filterKey) {
    case "nearby":
      // Lấy vị trí người dùng và tìm outlets gần nhất
      try {
        if ("geolocation" in navigator) {
          navigator.geolocation.getCurrentPosition(
            async (position) => {
              const { latitude, longitude } = position.coords;
              // Redirect đến search page với location và sort by distance
              router.push({
                path: "/search",
                query: {
                  latitude: latitude.toString(),
                  longitude: longitude.toString(),
                  radius: "10",
                  sort: "distance,asc",
                  useAdvancedSearch: "true"
                }
              });
            },
            (error) => {
              console.error("Error getting location:", error);
              // Nếu không lấy được location, redirect đến search page bình thường
              alert("Không thể lấy vị trí của bạn. Vui lòng bật quyền truy cập vị trí.");
              router.push({ path: "/search" });
            }
          );
        } else {
          alert("Trình duyệt của bạn không hỗ trợ định vị.");
          router.push({ path: "/search" });
        }
      } catch (error) {
        console.error("Error in nearby filter:", error);
        router.push({ path: "/search" });
      }
      break;
    case "newest":
      // Redirect đến search page với newest outlets
      router.push({
        path: "/search",
        query: {
          filter: "newest"
        }
      });
      break;
    case "promotion":
      // Redirect đến search page với promoted outlets
      router.push({
        path: "/search",
        query: {
          filter: "promotion"
        }
      });
      break;
    case "booking":
      // Redirect đến booking page hoặc search với booking filter
      if (authStore.isAuthenticated) {
        router.push({ path: "/booking" });
      } else {
        // Nếu chưa đăng nhập, redirect đến login với return URL
        router.push({ 
          path: "/auth/login",
          query: { returnUrl: "/booking" }
        });
      }
      break;
    default:
      router.push("/search");
  }
};

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({path: "/search", query: {q: searchQuery.value}});
  } else {
    router.push({path: "/search"});
  }
};

const handleSearchSelect = (suggestion) => {
  if (suggestion && suggestion.name) {
    searchQuery.value = suggestion.name;
    handleSearch();
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
