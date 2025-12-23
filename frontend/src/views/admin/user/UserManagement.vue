<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý người dùng</h1>
      <button
        @click="showCreateModal = true"
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
    <div v-if="error" class="mb-6">
      <ErrorMessage :message="error" />
      <div class="mt-2 text-sm text-red-600 dark:text-red-400">
        <p class="font-medium mb-2">Vui lòng kiểm tra:</p>
        <ul class="list-disc list-inside space-y-1">
          <li>Backend đang chạy và kết nối database thành công</li>
          <li>Token JWT còn hợp lệ</li>
          <li>Bạn có quyền ADMIN</li>
        </ul>
        <button
          @click="fetchUsers"
          class="mt-3 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90 text-sm"
        >
          Thử lại
        </button>
      </div>
    </div>

    <!-- Users Table -->
    <div
      v-if="!isLoading && !error && users.length > 0"
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
                  v-if="!isCurrentUser(user)"
                  @click="toggleUserStatus(user)"
                  :class="
                    user.isActive !== false
                      ? 'text-yellow-600 hover:text-yellow-500'
                      : 'text-positive hover:text-green-600'
                  "
                >
                  {{ user.isActive !== false ? "Khóa" : "Mở khóa" }}
                </button>
                <span
                  v-else
                  class="text-gray-400 text-sm"
                  title="Bạn không thể khóa chính mình"
                >
                  Không thể khóa
                </span>
                <button
                  @click="handleDeleteUser(user)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Empty State -->
    <div
      v-if="!isLoading && !error && users.length === 0"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark text-center py-12"
    >
      <span class="material-symbols-outlined text-6xl text-gray-400 mb-4">group</span>
      <p class="text-lg font-medium text-subtext-light dark:text-subtext-dark mb-2">
        Không có người dùng nào
      </p>
      <p class="text-sm text-subtext-light dark:text-subtext-dark">
        {{ filters.search || filters.role || filters.status ? "Thử thay đổi bộ lọc" : "Hãy thêm người dùng đầu tiên" }}
      </p>
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

    <!-- Create User Modal -->
    <div
      v-if="showCreateModal"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeCreateModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">Thêm người dùng mới</h2>
        <form @submit.prevent="createUser" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Họ và tên *</label>
            <input
              v-model="createForm.fullName"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập họ và tên"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Email *</label>
            <input
              v-model="createForm.email"
              type="email"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="email@example.com"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Mật khẩu *</label>
            <input
              v-model="createForm.password"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Tối thiểu 6 ký tự"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Xác nhận mật khẩu *</label>
            <input
              v-model="createForm.passwordConfirmation"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập lại mật khẩu"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Vai trò *</label>
            <select
              v-model="createForm.role"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            >
              <option value="">Chọn vai trò</option>
              <option value="USER">User</option>
              <option value="OWNER">Owner</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>
          <div class="flex gap-2 justify-end">
            <button
              type="button"
              @click="closeCreateModal"
              class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
            >
              Hủy
            </button>
            <button
              type="submit"
              :disabled="isCreating"
              class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90 disabled:opacity-50"
            >
              {{ isCreating ? "Đang tạo..." : "Tạo mới" }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth";
import {adminApi, authApi} from "@/api";
import {useToast} from "@/composables/useToast";
import {useConfirm} from "@/composables/useConfirm";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const router = useRouter();
const authStore = useAuthStore();
const {success, error: showError} = useToast();
const {confirm} = useConfirm();

const isLoading = ref(false);
const error = ref(null);
const users = ref([]);
const showCreateModal = ref(false);
const isCreating = ref(false);
const filters = ref({
  search: "",
  role: "",
  status: "",
});
const createForm = ref({
  fullName: "",
  email: "",
  password: "",
  passwordConfirmation: "",
  role: "",
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

const isCurrentUser = (user) => {
  const currentUser = authStore.user;
  if (!currentUser || !user) return false;
  return currentUser.id === user.id || currentUser.email === user.email;
};

const fetchUsers = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = {
      page: pagination.value.currentPage,
      size: pagination.value.pageSize,
    };

    // Backend expects: searchTerm, roleName (enum), isActive
    if (filters.value.search) {
      params.searchTerm = filters.value.search;
    }
    if (filters.value.role) {
      // Backend expects roleName as enum: ROLE_USER, ROLE_OWNER, ROLE_ADMIN
      const roleName = filters.value.role.startsWith("ROLE_") 
        ? filters.value.role 
        : `ROLE_${filters.value.role}`;
      params.roleName = roleName;
    }
    if (filters.value.status !== "") {
      params.isActive = filters.value.status === "true";
    }

    console.log("🔍 [UserManagement] Fetching users with params:", params);
    const response = await adminApi.getUsers(params);
    console.log("✅ [UserManagement] Raw response:", response);
    console.log("✅ [UserManagement] Response type:", typeof response);
    console.log("✅ [UserManagement] Is array:", Array.isArray(response));
    
    // Handle Spring Data Page structure: { content: Array, totalElements, totalPages, ... }
    // Or PageResponse structure: { data: Array, totalElements, totalPages, ... }
    let pageData = response;
    let allUsers = [];
    
    if (response && typeof response === 'object' && !Array.isArray(response)) {
      // Check for Spring Data Page format (content array)
      if (response.content && Array.isArray(response.content)) {
        allUsers = response.content;
        pageData = response;
        console.log("✅ [UserManagement] Detected Spring Data Page format, users count:", allUsers.length);
      }
      // Check for PageResponse format (data array)
      else if (response.data && Array.isArray(response.data)) {
        allUsers = response.data;
        pageData = response;
        console.log("✅ [UserManagement] Detected PageResponse format, users count:", allUsers.length);
      } else {
        console.warn("⚠️ [UserManagement] Response object but no content/data array found:", Object.keys(response));
      }
    } else if (Array.isArray(response)) {
      allUsers = response;
      pageData = { totalElements: response.length, totalPages: 1 };
      console.log("✅ [UserManagement] Response is array directly, users count:", allUsers.length);
    } else {
      console.error("❌ [UserManagement] Unexpected response format:", typeof response, response);
    }

    // Ensure users is always an array
    users.value = Array.isArray(allUsers) ? allUsers : [];
    console.log("✅ [UserManagement] Final users.value:", users.value);
    console.log("✅ [UserManagement] Final users.value.length:", users.value.length);
    
    // Update pagination
    if (pageData && typeof pageData === 'object' && !Array.isArray(pageData)) {
      if (pageData.totalElements !== undefined) {
        pagination.value.totalElements = pageData.totalElements;
        pagination.value.totalPages = pageData.totalPages || Math.ceil(pageData.totalElements / pagination.value.pageSize);
        console.log("✅ [UserManagement] Pagination updated:", {
          totalElements: pagination.value.totalElements,
          totalPages: pagination.value.totalPages,
          currentPage: pagination.value.currentPage
        });
      } else {
        console.warn("⚠️ [UserManagement] PageData has no totalElements:", pageData);
      }
    }
  } catch (err) {
    console.error("❌ [UserManagement] Error fetching users:", err);
    console.error("❌ [UserManagement] Error response:", err.response);
    console.error("❌ [UserManagement] Error details:", err.response?.data?.details);
    
    // Show detailed error message
    const errorDetails = err.response?.data?.details;
    let errorMessage = err.response?.data?.message || err.message || "Không thể tải danh sách người dùng";
    
    if (errorDetails && Array.isArray(errorDetails) && errorDetails.length > 0) {
      errorMessage += `\nChi tiết: ${errorDetails.map(d => d.toString()).join(", ")}`;
    }
    
    error.value = errorMessage;
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
  // Không cho admin tự khóa chính mình
  if (isCurrentUser(user)) {
    showError("Bạn không thể khóa chính mình");
    return;
  }

  const confirmed = await confirm(
    `Bạn có chắc muốn ${user.isActive !== false ? "khóa" : "mở khóa"} người dùng này?`
  );
  if (!confirmed) return;

  try {
    await adminApi.changeUserStatus(user.id, {
      isActive: user.isActive === false,
    });
    success(`${user.isActive !== false ? "Khóa" : "Mở khóa"} người dùng thành công`);
    await fetchUsers();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const handleDeleteUser = async (user) => {
  if (isCurrentUser(user)) {
    showError("Bạn không thể xóa chính mình");
    return;
  }

  const confirmed = await confirm(`Bạn có chắc muốn xóa người dùng ${user.email}?`);
  if (!confirmed) return;

  try {
    await adminApi.deleteUser(user.id);
    success("Xóa người dùng thành công");
    await fetchUsers();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const createUser = async () => {
  if (createForm.value.password !== createForm.value.passwordConfirmation) {
    showError("Mật khẩu xác nhận không khớp");
    return;
  }

  isCreating.value = true;
  try {
    // Step 1: Register user
    const registerData = {
      fullName: createForm.value.fullName,
      email: createForm.value.email,
      password: createForm.value.password,
      confirmPassword: createForm.value.passwordConfirmation,
    };
    
    await authApi.register(registerData);
    
    // Step 2: Find user by email to get ID
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const searchResponse = await adminApi.getUsers({
      searchTerm: createForm.value.email,
      page: 0,
      size: 1,
    });
    
    let userId = null;
    const userList = searchResponse?.data || searchResponse?.content || searchResponse || [];
    const foundUser = Array.isArray(userList) ? userList.find(u => u.email === createForm.value.email) : null;
    
    if (foundUser?.id) {
      userId = foundUser.id;
    } else {
      throw new Error("Không thể tìm thấy người dùng vừa tạo. Vui lòng kiểm tra lại.");
    }

    // Step 3: Assign role if not USER
    if (createForm.value.role && createForm.value.role !== "USER") {
      try {
        const roleType = createForm.value.role.startsWith("ROLE_") 
          ? createForm.value.role 
          : `ROLE_${createForm.value.role}`;
        
        await adminApi.assignUserRoles(userId, {
          roleType: roleType,
        });
      } catch (roleErr) {
        console.warn("Could not assign role:", roleErr);
        showError(`Đã tạo người dùng nhưng không thể gán vai trò: ${roleErr.response?.data?.message || roleErr.message}`);
        await fetchUsers();
        return;
      }
    }

    success("Tạo người dùng thành công");
    closeCreateModal();
    await fetchUsers();
  } catch (err) {
    console.error("Error creating user:", err);
    showError(err.response?.data?.message || err.message || "Có lỗi xảy ra khi tạo người dùng");
  } finally {
    isCreating.value = false;
  }
};

const closeCreateModal = () => {
  showCreateModal.value = false;
  createForm.value = {
    fullName: "",
    email: "",
    password: "",
    passwordConfirmation: "",
    role: "",
  };
};

onMounted(() => {
  fetchUsers();
});
</script>
