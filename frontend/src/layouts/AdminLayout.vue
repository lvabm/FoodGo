<template>
  <div class="flex min-h-screen bg-background-light dark:bg-background-dark">
    <!-- Sidebar -->
    <aside
      class="flex w-64 flex-col border-r border-gray-200 dark:border-gray-700 bg-background-light dark:bg-background-dark p-4"
    >
      <div class="flex flex-col gap-4">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-3">
          <div
            class="bg-center bg-no-repeat aspect-square bg-cover rounded-full size-10 bg-primary flex items-center justify-center"
          >
            <svg
              class="h-6 w-6 text-white"
              fill="none"
              viewBox="0 0 48 48"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M44 4H30.6666V17.3334H17.3334V30.6666H4V44H44V4Z"
                fill="currentColor"
              ></path>
            </svg>
          </div>
          <div class="flex flex-col">
            <h1 class="text-gray-900 dark:text-gray-100 text-base font-bold">
              FoodGo
            </h1>
            <p class="text-gray-500 dark:text-gray-400 text-sm font-normal">
              Admin Panel
            </p>
          </div>
        </router-link>

        <!-- Navigation -->
        <nav class="flex flex-col gap-2 mt-4">
          <router-link
            to="/admin"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">dashboard</span>
            <p class="text-sm font-medium">Dashboard</p>
          </router-link>

          <router-link
            to="/admin/users"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">group</span>
            <p class="text-sm font-medium">Quản lý người dùng</p>
          </router-link>

          <router-link
            to="/admin/outlets"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">storefront</span>
            <p class="text-sm font-medium">Quản lý địa điểm</p>
          </router-link>

          <router-link
            to="/admin/categories"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">category</span>
            <p class="text-sm font-medium">Quản lý danh mục</p>
          </router-link>

          <router-link
            to="/admin/countries"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">public</span>
            <p class="text-sm font-medium">Quản lý địa lý</p>
          </router-link>

          <router-link
            to="/admin/memberships"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl"
              >card_membership</span
            >
            <p class="text-sm font-medium">Quản lý thành viên</p>
          </router-link>

          <router-link
            to="/admin/reports"
            class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-primary/10"
            active-class="bg-primary/20 text-primary dark:text-white dark:bg-primary/30"
          >
            <span class="material-symbols-outlined text-xl">assessment</span>
            <p class="text-sm font-medium">Báo cáo</p>
          </router-link>
        </nav>
      </div>

      <!-- Bottom actions -->
      <div class="mt-auto flex flex-col gap-1">
        <button
          @click="handleLogout"
          class="flex items-center gap-3 px-3 py-2 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-primary/10"
        >
          <span class="material-symbols-outlined text-xl">logout</span>
          <p class="text-sm font-medium">Đăng xuất</p>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col">
      <!-- TopNavBar -->
      <header
        class="flex items-center justify-between border-b border-gray-200 dark:border-gray-700 px-10 py-3 bg-background-light dark:bg-background-dark"
      >
        <div class="flex items-center gap-4">
          <h2
            class="text-gray-900 dark:text-gray-100 text-lg font-bold leading-tight tracking-[-0.015em]"
          >
            Admin Dashboard
          </h2>
        </div>
        <div class="flex items-center gap-4">
          <button
            class="flex items-center justify-center rounded-full h-10 bg-gray-100 dark:bg-gray-800 hover:bg-gray-200 dark:hover:bg-gray-700 px-4 transition-colors"
          >
            <span class="material-symbols-outlined">notifications</span>
          </button>
          <div class="flex items-center gap-3">
            <div
              class="bg-center bg-no-repeat aspect-square bg-cover rounded-full size-10 bg-gray-300"
            ></div>
          </div>
        </div>
      </header>

      <!-- Page Content -->
      <main class="flex-1 overflow-y-auto">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();

const handleLogout = () => {
  if (confirm("Bạn có chắc muốn đăng xuất?")) {
    authStore.logout();
    router.push("/auth/login");
  }
};
</script>
