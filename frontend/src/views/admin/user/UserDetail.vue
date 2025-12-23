<template>
  <div class="px-10 py-8">
    <router-link
      to="/admin/users"
      class="flex items-center gap-2 text-primary mb-6 hover:underline"
    >
      <span class="material-symbols-outlined">arrow_back</span>
      Quay lại
    </router-link>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <div v-if="!isLoading && !error && user" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- User Info Card -->
      <div
        class="lg:col-span-1 bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex flex-col items-center">
          <div class="h-24 w-24 mb-4">
            <ImageDisplay
              :image-url="user.profile?.avatarUrl || user.avatarUrl"
              :alt="user.profile?.fullName || user.email"
              placeholder-icon="person"
              container-class="h-24 w-24 rounded-full"
              image-class="h-24 w-24 rounded-full"
              icon-size="48px"
            />
          </div>
          <h2 class="text-xl font-bold mb-2">
            {{ user.profile?.fullName || user.email || "N/A" }}
          </h2>
          <span
            class="px-3 py-1 text-sm font-medium rounded-full mb-4"
            :class="getRoleClass(user.roleName || user.role)"
          >
            {{ formatRole(user.roleName || user.role) }}
          </span>
          <div class="w-full space-y-3">
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >mail</span
              >
              <span>{{ user.email }}</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >phone</span
              >
              <span>{{ user.profile?.phoneNumber || "N/A" }}</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >calendar_today</span
              >
              <span>Tham gia: {{ formatDate(user.createdAt || user.createdDate) }}</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <span
                class="material-symbols-outlined text-subtext-light dark:text-subtext-dark"
                >toggle_on</span
              >
              <span
                :class="
                  user.isActive !== false
                    ? 'text-positive'
                    : 'text-red-500'
                "
              >
                {{ user.isActive !== false ? "Hoạt động" : "Bị khóa" }}
              </span>
            </div>
          </div>
          <div class="w-full mt-4 space-y-2">
            <button
              @click="toggleUserStatus"
              :class="
                user.isActive !== false
                  ? 'w-full px-4 py-2 bg-yellow-100 text-yellow-800 rounded-lg hover:bg-yellow-200'
                  : 'w-full px-4 py-2 bg-positive/10 text-positive rounded-lg hover:bg-positive/20'
              "
            >
              {{ user.isActive !== false ? "Khóa tài khoản" : "Mở khóa tài khoản" }}
            </button>
            <button
              @click="handleDeleteUser"
              class="w-full px-4 py-2 bg-red-100 text-red-800 rounded-lg hover:bg-red-200"
            >
              Xóa tài khoản
            </button>
          </div>
        </div>
      </div>

      <!-- Details -->
      <div class="lg:col-span-2 space-y-6">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
        >
          <h3 class="text-lg font-bold mb-4">Thông tin chi tiết</h3>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Họ và tên
              </p>
              <p class="font-medium">{{ user.profile?.fullName || "N/A" }}</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Giới tính
              </p>
              <p class="font-medium">{{ formatGender(user.profile?.gender) }}</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Ngày sinh
              </p>
              <p class="font-medium">{{ formatDate(user.profile?.dateOfBirth) }}</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Quốc gia
              </p>
              <p class="font-medium">{{ user.profile?.country?.name || "N/A" }}</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Email
              </p>
              <p class="font-medium">{{ user.email }}</p>
            </div>
            <div>
              <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                Số điện thoại
              </p>
              <p class="font-medium">{{ user.profile?.phoneNumber || "N/A" }}</p>
            </div>
          </div>
        </div>

        <div
          class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
        >
          <h3 class="text-lg font-bold mb-4">Lịch sử đặt bàn</h3>
          <div v-if="bookings.length === 0" class="text-center py-8 text-subtext-light">
            Chưa có lịch sử đặt bàn
          </div>
          <div v-else class="space-y-3">
            <div
              v-for="booking in bookings"
              :key="booking.id"
              class="flex items-center justify-between p-3 border border-border-light dark:border-border-dark rounded-lg"
            >
              <div>
                <p class="font-medium">{{ booking.outlet?.name || "N/A" }}</p>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  {{ formatDate(booking.bookingDate) }} - {{ formatTime(booking.bookingTime) }}
                </p>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  Số khách: {{ booking.numberOfGuests }}
                </p>
              </div>
              <span
                :class="getBookingStatusClass(booking.status)"
                class="px-2 py-1 text-xs font-medium rounded-full"
              >
                {{ formatBookingStatus(booking.status) }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import {adminApi, bookingApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const route = useRoute();
const router = useRouter();

const isLoading = ref(false);
const error = ref(null);
const user = ref(null);
const bookings = ref([]);

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

const formatTime = (time) => {
  if (!time) return "N/A";
  try {
    if (typeof time === "string") {
      return time.substring(0, 5); // HH:mm format
    }
    return time;
  } catch {
    return time;
  }
};

const formatGender = (gender) => {
  const genderMap = {
    MALE: "Nam",
    FEMALE: "Nữ",
    OTHER: "Khác",
  };
  return genderMap[gender] || "N/A";
};

const formatBookingStatus = (status) => {
  const statusMap = {
    PENDING: "Chờ xác nhận",
    CONFIRMED: "Đã xác nhận",
    CANCELLED: "Đã hủy",
    REJECTED: "Bị từ chối",
    COMPLETED: "Hoàn thành",
    NO_SHOW: "Không đến",
  };
  return statusMap[status] || status || "N/A";
};

const getBookingStatusClass = (status) => {
  const classMap = {
    PENDING: "bg-yellow-100 text-yellow-600",
    CONFIRMED: "bg-blue-100 text-blue-600",
    CANCELLED: "bg-red-100 text-red-600",
    REJECTED: "bg-red-100 text-red-600",
    COMPLETED: "bg-positive/10 text-positive",
    NO_SHOW: "bg-gray-100 text-gray-600",
  };
  return classMap[status] || "bg-gray-100 text-gray-600";
};

const fetchUserDetail = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const userId = route.params.id;
    const response = await adminApi.getUserDetail(userId);
    user.value = response.data || response;

    // Fetch user bookings
    try {
      const bookingsResponse = await bookingApi.getMyBookings({
        userId: userId,
        page: 0,
        size: 10,
      });
      const bookingsData = bookingsResponse.data || bookingsResponse;
      bookings.value = bookingsData.content || bookingsData.data || [];
    } catch (err) {
      console.warn("Could not fetch bookings:", err);
      bookings.value = [];
    }
  } catch (err) {
    console.error("Error fetching user detail:", err);
    error.value = err.response?.data?.message || "Không thể tải thông tin người dùng";
  } finally {
    isLoading.value = false;
  }
};

const toggleUserStatus = async () => {
  if (!confirm(`Bạn có chắc muốn ${user.value.isActive !== false ? "khóa" : "mở khóa"} người dùng này?`)) {
    return;
  }

  try {
    await adminApi.changeUserStatus(user.value.id, {
      isActive: user.value.isActive === false,
    });
    await fetchUserDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const handleDeleteUser = async () => {
  if (!confirm(`Bạn có chắc muốn xóa người dùng ${user.value.email}?`)) {
    return;
  }

  try {
    await adminApi.deleteUser(user.value.id);
    router.push("/admin/users");
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

onMounted(() => {
  fetchUserDetail();
});
</script>
