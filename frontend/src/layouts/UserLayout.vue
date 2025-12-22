<template>
  <div
    class="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark"
  >
    <!-- TopNavBar -->
    <header
      class="sticky top-0 z-50 flex items-center justify-center border-b border-border-light dark:border-border-dark bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-sm"
    >
      <div
        class="flex h-16 w-full max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8"
      >
        <div class="flex items-center gap-4">
          <svg
            class="h-6 w-6 text-primary"
            fill="none"
            viewBox="0 0 48 48"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M44 4H30.6666V17.3334H17.3334V30.6666H4V44H44V4Z"
              fill="currentColor"
            ></path>
          </svg>
          <h2
            class="text-xl font-bold leading-tight tracking-[-0.015em] text-text-light dark:text-text-dark"
          >
            FoodGo
          </h2>
        </div>

        <nav class="hidden items-center gap-9 md:flex">
          <router-link
            to="/"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Trang chủ
          </router-link>
          <router-link
            to="/search"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Khám phá
          </router-link>

          <!-- Show Login if not authenticated -->
          <router-link
            v-if="!authStore.isAuthenticated"
            to="/auth/login"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Đăng nhập
          </router-link>

          <!-- Show Profile dropdown if authenticated -->
          <div v-else class="relative group">
            <button
              class="flex items-center gap-2 text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
            >
              <span class="material-symbols-outlined text-xl"
                >account_circle</span
              >
              <span>{{
                authStore.user?.fullName || authStore.user?.email
              }}</span>
            </button>

            <!-- Dropdown menu -->
            <div
              class="absolute right-0 mt-2 w-48 bg-white dark:bg-surface-dark rounded-lg shadow-lg border border-border-light dark:border-border-dark opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all"
            >
              <router-link
                to="/profile"
                class="block px-4 py-2 text-sm text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800 rounded-t-lg"
              >
                Hồ sơ cá nhân
              </router-link>
              <router-link
                to="/booking-history"
                class="block px-4 py-2 text-sm text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800"
              >
                Lịch sử đặt bàn
              </router-link>
              <button
                @click="handleLogout"
                class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-b-lg"
              >
                Đăng xuất
              </button>
            </div>
          </div>
        </nav>

        <router-link
          to="/owner"
          class="flex min-w-[84px] cursor-pointer items-center justify-center overflow-hidden rounded-full h-10 px-4 bg-primary text-white text-sm font-bold leading-normal tracking-[0.015em] hover:bg-opacity-90 transition-colors"
        >
          <span class="truncate">Dành cho chủ quán</span>
        </router-link>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex flex-1 flex-col items-center">
      <router-view />
    </main>

    <!-- Footer -->
    <footer
      class="w-full bg-surface-light dark:bg-surface-dark border-t border-border-light dark:border-border-dark"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="text-center text-text-light dark:text-text-dark">
          <p class="text-sm">&copy; 2025 FoodGo. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import {useAuthStore} from "@/stores/auth";
import {useRouter} from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const handleLogout = () => {
  authStore.logout();
  router.push("/auth/login");
};
</script>
