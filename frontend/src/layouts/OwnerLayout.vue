<template>
  <div
    class="relative flex h-auto min-h-screen w-full flex-col bg-background-light dark:bg-background-dark"
  >
    <div class="flex min-h-screen">
      <!-- Sidebar -->
      <aside
        class="flex flex-col w-64 bg-card-light dark:bg-card-dark border-r border-border-light dark:border-border-dark"
      >
        <div class="flex flex-col flex-1 p-4">
          <div class="flex flex-col gap-4">
            <!-- Logo -->
            <div class="flex items-center gap-3 p-2">
              <div
                class="bg-center bg-no-repeat aspect-square bg-cover rounded-full size-10 bg-owner-primary flex items-center justify-center"
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
                <h1
                  class="text-gray-900 dark:text-gray-100 text-base font-bold"
                >
                  FoodGo
                </h1>
                <p class="text-gray-500 dark:text-gray-400 text-sm font-normal">
                  Quản lý Quán
                </p>
              </div>
            </div>

            <!-- Navigation -->
            <div class="flex flex-col gap-2 mt-4">
              <router-link
                to="/owner"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">dashboard</span>
                <p class="text-sm font-medium">Tổng quan</p>
              </router-link>

              <router-link
                to="/owner/bookings"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">calendar_month</span>
                <p class="text-sm font-medium">Đặt bàn</p>
              </router-link>

              <router-link
                to="/owner/menu"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">restaurant_menu</span>
                <p class="text-sm font-medium">Menu</p>
              </router-link>

              <router-link
                to="/owner/feedback"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">grade</span>
                <p class="text-sm font-medium">Đánh giá</p>
              </router-link>

              <router-link
                to="/owner/outlet"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">storefront</span>
                <p class="text-sm font-medium">Thông tin quán</p>
              </router-link>

              <router-link
                to="/owner/statistics"
                class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-owner-primary/10"
                active-class="bg-owner-primary/20 text-owner-primary"
              >
                <span class="material-symbols-outlined">analytics</span>
                <p class="text-sm font-medium">Thống kê</p>
              </router-link>
            </div>
          </div>

          <!-- Bottom Actions -->
          <div class="mt-auto flex flex-col gap-1">
            <button
              class="flex items-center gap-3 px-3 py-2 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-owner-primary/10"
            >
              <span class="material-symbols-outlined">settings</span>
              <p class="text-sm font-medium">Cài đặt</p>
            </button>
            <button
              class="flex items-center gap-3 px-3 py-2 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-owner-primary/10"
            >
              <span class="material-symbols-outlined">logout</span>
              <p class="text-sm font-medium">Đăng xuất</p>
            </button>
          </div>
        </div>
      </aside>

      <!-- Main Content -->
      <div class="flex-1 flex flex-col">
        <!-- TopNavBar -->
        <header
          class="flex items-center justify-between border-b border-border-light dark:border-border-dark px-10 py-3 bg-background-light dark:bg-background-dark"
        >
          <div class="flex items-center gap-4">
            <h2 class="text-gray-900 dark:text-gray-100 text-lg font-bold">
              Dashboard Quản lý
            </h2>
          </div>
          <div class="flex items-center gap-4">
            <button
              class="flex items-center justify-center rounded-full h-10 bg-gray-100 dark:bg-gray-800 hover:bg-gray-200 dark:hover:bg-gray-700 px-4"
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
  </div>
</template>

<script setup>
// Owner Layout Component
</script>
