<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý người dùng</h1>
      <button
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm người dùng
      </button>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium mb-2">Tìm kiếm</label>
          <input
            type="text"
            placeholder="Tên, email, số điện thoại..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Vai trò</label>
          <select
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option>Tất cả</option>
            <option>User</option>
            <option>Owner</option>
            <option>Admin</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Trạng thái</label>
          <select
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option>Tất cả</option>
            <option>Hoạt động</option>
            <option>Bị khóa</option>
          </select>
        </div>
        <div class="flex items-end">
          <button
            class="w-full px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            Lọc
          </button>
        </div>
      </div>
    </div>

    <!-- Users Table -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden"
    >
      <table class="w-full">
        <thead class="bg-gray-50 dark:bg-surface-light/5">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Người dùng
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Email
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Vai trò
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Trạng thái
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Ngày tham gia
            </th>
            <th
              class="px-6 py-3 text-right text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Thao tác
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-border-light dark:divide-border-dark">
          <tr
            v-for="user in users"
            :key="user.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="flex items-center">
                <div class="h-10 w-10 flex-shrink-0">
                  <div class="h-10 w-10 rounded-full bg-gray-300"></div>
                </div>
                <div class="ml-4">
                  <div class="text-sm font-medium">{{ user.name }}</div>
                  <div
                    class="text-sm text-subtext-light dark:text-subtext-dark"
                  >
                    {{ user.phone }}
                  </div>
                </div>
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ user.email }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                class="px-2 py-1 text-xs font-medium rounded-full bg-primary/10 text-primary"
              >
                {{ user.role }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="
                  user.status === 'active' ? 'text-positive' : 'text-red-500'
                "
                class="flex items-center gap-1 text-sm"
              >
                <span
                  class="w-2 h-2 rounded-full"
                  :class="
                    user.status === 'active' ? 'bg-positive' : 'bg-red-500'
                  "
                ></span>
                {{ user.status === "active" ? "Hoạt động" : "Bị khóa" }}
              </span>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light dark:text-subtext-dark"
            >
              {{ user.joinDate }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <button class="text-primary hover:text-primary/80 mr-3">
                Chi tiết
              </button>
              <button class="text-red-600 hover:text-red-500">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <div class="flex items-center justify-between mt-6">
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        Hiển thị 1-10 trong 157 kết quả
      </p>
      <div class="flex gap-2">
        <button
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5"
        >
          Trước
        </button>
        <button class="px-4 py-2 bg-primary text-white rounded-lg">1</button>
        <button
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5"
        >
          2
        </button>
        <button
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5"
        >
          3
        </button>
        <button
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5"
        >
          Sau
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";

const users = ref([
  {
    id: 1,
    name: "Nguyễn Văn A",
    email: "nguyenvana@email.com",
    phone: "0901234567",
    role: "User",
    status: "active",
    joinDate: "15/01/2025",
  },
  {
    id: 2,
    name: "Trần Thị B",
    email: "tranthib@email.com",
    phone: "0907654321",
    role: "Owner",
    status: "active",
    joinDate: "10/01/2025",
  },
  {
    id: 3,
    name: "Lê Văn C",
    email: "levanc@email.com",
    phone: "0912345678",
    role: "User",
    status: "locked",
    joinDate: "05/01/2025",
  },
]);
</script>
