<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý người dùng</h1>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium mb-2">Tìm kiếm</label>
          <input
            v-model="filters.search"
            type="text"
            placeholder="Tên, email, số điện thoại..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
            @keyup.enter="handleSearch"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Vai trò</label>
          <select
            v-model="filters.role"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả</option>
            <option value="USER">User</option>
            <option value="OWNER">Owner</option>
            <option value="ADMIN">Admin</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Trạng thái</label>
          <select
            v-model="filters.status"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả</option>
            <option value="true">Hoạt động</option>
            <option value="false">Bị khóa</option>
          </select>
        </div>
        <div class="flex items-end gap-2">
          <button
            @click="handleSearch"
            class="w-full px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
          >
            Lọc
          </button>
          <button
            @click="resetFilters"
            class="w-full px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            Reset
          </button>
        </div>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Users Table -->
    <div
      v-if="!isLoading && !error"
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
                  <ImageDisplay
                    :image-url="user.profile?.avatarUrl || user.avatarUrl"
                    :alt="user.profile?.fullName || user.email"
                    placeholder-icon="person"
                    container-class="h-10 w-10 rounded-full"
                    image-class="h-10 w-10 rounded-full"
                    icon-size="24px"
                  />
                </div>
                <div class="ml-4">
                  <div class="text-sm font-medium">
                    {{ user.profile?.fullName || user.email || "N/A" }}
                  </div>
                  <div
                    class="text-sm text-subtext-light dark:text-subtext-dark"
                  >
                    {{ user.profile?.phoneNumber || "N/A" }}
                  </div>
                </div>
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              {{ user.email }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                class="px-2 py-1 text-xs font-medium rounded-full"
                :class="getRoleClass(user.roleName || user.role)"
              >
                {{ formatRole(user.roleName || user.role) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="
                  user.isActive !== false
                    ? 'text-positive'
                    : 'text-red-500'
                "
                class="flex items-center gap-1 text-sm"
              >
                <span
                  class="w-2 h-2 rounded-full"
                  :class="
                    user.isActive !== false
                      ? 'bg-positive'
                      : 'bg-red-500'
                  "
                ></span>
                {{
                  user.isActive !== false ? "Hoạt động" : "Bị khóa"
                }}
              </span>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light dark:text-subtext-dark"
            >
              {{ formatDate(user.createdAt || user.createdDate) }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <router-link
                  :to="`/admin/users/${user.id}`"
                  class="text-primary hover:text-primary/80"
                >
                  Chi tiết
                </router-link>
                <button
                  @click="toggleUserStatus(user)"
                  :class="
                    user.isActive !== false
                      ? 'text-yellow-600 hover:text-yellow-500'
                      : 'text-positive hover:text-green-600'
                  "
                >
                  {{ user.isActive !== false ? "Khóa" : "Mở khóa" }}
                </button>
                <button
                  @click="handleDeleteUser(user)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="users.length === 0">
            <td colspan="6" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <div
      v-if="!isLoading && !error && pagination.totalElements > 0"
      class="flex items-center justify-between mt-6"
    >
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        Hiển thị {{ (pagination.currentPage * pagination.pageSize) + 1 }}-{{ Math.min((pagination.currentPage + 1) * pagination.pageSize, pagination.totalElements) }} trong
        {{ pagination.totalElements }} kết quả
      </p>
      <div class="flex gap-2">
        <button
          @click="goToPage(pagination.currentPage - 1)"
          :disabled="pagination.currentPage === 0"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Trước
        </button>
        <button
          v-for="page in visiblePages"
          :key="page"
          @click="goToPage(page)"
          :class="
            page === pagination.currentPage
              ? 'px-4 py-2 bg-primary text-white rounded-lg'
              : 'px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5'
          "
        >
          {{ page + 1 }}
        </button>
        <button
          @click="goToPage(pagination.currentPage + 1)"
          :disabled="pagination.currentPage >= pagination.totalPages - 1"
          class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-50 dark:hover:bg-surface-light/5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Sau
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {useRouter} from "vue-router";
import {adminApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const router = useRouter();

const isLoading = ref(false);
const error = ref(null);
const users = ref([]);
const filters = ref({
  search: "",
  role: "",
  status: "",
});
const pagination = ref({
  currentPage: 0,
  pageSize: 10,
  totalElements: 0,
  totalPages: 0,
});


const visiblePages = computed(() => {
  const total = pagination.value.totalPages;
  const current = pagination.value.currentPage;
  const pages = [];
  const maxVisible = 5;

  if (total <= maxVisible) {
    for (let i = 0; i < total; i++) {
      pages.push(i);
    }
  } else {
    if (current < 3) {
      for (let i = 0; i < maxVisible; i++) {
        pages.push(i);
      }
    } else if (current > total - 4) {
      for (let i = total - maxVisible; i < total; i++) {
        pages.push(i);
      }
    } else {
      for (let i = current - 2; i <= current + 2; i++) {
        pages.push(i);
      }
    }
  }
  return pages;
});

const formatRole = (role) => {
  const roleMap = {
    ROLE_USER: "User",
    ROLE_OWNER: "Owner",
    ROLE_ADMIN: "Admin",
    USER: "User",
    OWNER: "Owner",
    ADMIN: "Admin",
  };
  return roleMap[role] || role || "N/A";
};

const getRoleClass = (role) => {
  const classMap = {
    ROLE_USER: "bg-blue-100 text-blue-800",
    ROLE_OWNER: "bg-purple-100 text-purple-800",
    ROLE_ADMIN: "bg-red-100 text-red-800",
    USER: "bg-blue-100 text-blue-800",
    OWNER: "bg-purple-100 text-purple-800",
    ADMIN: "bg-red-100 text-red-800",
  };
  return classMap[role] || "bg-gray-100 text-gray-800";
};

const formatDate = (date) => {
  if (!date) return "N/A";
  try {
    return new Date(date).toLocaleDateString("vi-VN");
  } catch {
    return date;
  }
};

const fetchUsers = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.pageSize,
    };

    if (filters.value.search) {
      params.search = filters.value.search;
    }
    if (filters.value.role) {
      params.role = filters.value.role;
    }
    if (filters.value.status !== "") {
      params.isActive = filters.value.status === "true";
    }

    const response = await adminApi.getUsers(params);
    const data = response.data || response;

    users.value = data.content || data.data || [];
    if (data.totalElements !== undefined) {
      pagination.value.totalElements = data.totalElements;
      pagination.value.totalPages = data.totalPages || Math.ceil(data.totalElements / pagination.value.pageSize);
    }
  } catch (err) {
    console.error("Error fetching users:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách người dùng";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchUsers();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    role: "",
    status: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchUsers();
  }
};

const toggleUserStatus = async (user) => {
  if (!confirm(`Bạn có chắc muốn ${user.isActive !== false ? "khóa" : "mở khóa"} người dùng này?`)) {
    return;
  }

  try {
    await adminApi.changeUserStatus(user.id, {
      isActive: user.isActive === false,
    });
    await fetchUsers();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const handleDeleteUser = async (user) => {
  if (!confirm(`Bạn có chắc muốn xóa người dùng ${user.email}?`)) {
    return;
  }

  try {
    await adminApi.deleteUser(user.id);
    await fetchUsers();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

onMounted(() => {
  fetchUsers();
});
</script>
