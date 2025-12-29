<template>
  <div class="w-full max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-6 sm:py-8 animate-ios-slide-up">
    <!-- Header with iOS Style -->
    <div class="mb-6 sm:mb-8 animate-ios-scale">
      <h1 class="text-3xl sm:text-4xl font-bold text-text-light dark:text-text-dark mb-2">
        Hồ sơ cá nhân
      </h1>
      <p class="text-base text-subtext-light dark:text-subtext-dark">
        Quản lý thông tin cá nhân của bạn
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex items-center justify-center py-20">
      <div class="flex flex-col items-center gap-4">
        <div class="animate-spin rounded-full h-12 w-12 border-[3px] border-primary/20 border-t-primary"></div>
        <p class="text-sm text-subtext-light dark:text-subtext-dark">Đang tải...</p>
      </div>
    </div>

    <!-- Error Message - iOS Style -->
    <div
      v-if="errorMessage"
      class="mb-6 card-premium p-4 bg-red-50 dark:bg-red-900/20 border-red-200 dark:border-red-800"
    >
      <div class="flex items-center gap-3">
        <span class="material-symbols-outlined text-red-600 dark:text-red-400">error</span>
        <p class="text-sm text-red-700 dark:text-red-300">{{ errorMessage }}</p>
      </div>
    </div>

    <!-- Success Message - iOS Style -->
    <div
      v-if="successMessage"
      class="mb-6 card-premium p-4 bg-green-50 dark:bg-green-900/20 border-green-200 dark:border-green-800"
    >
      <div class="flex items-center gap-3">
        <span class="material-symbols-outlined text-green-600 dark:text-green-400">check_circle</span>
        <p class="text-sm text-green-700 dark:text-green-300">{{ successMessage }}</p>
      </div>
    </div>

    <!-- Profile Content -->
    <div
      v-if="!isLoading && profile"
      class="grid grid-cols-1 lg:grid-cols-3 gap-4 sm:gap-6"
    >
      <!-- Left Sidebar - Avatar & Basic Info -->
      <div class="lg:col-span-1">
        <div class="card-premium p-6">
          <!-- Avatar - iOS Style -->
          <div class="flex flex-col items-center">
            <div class="relative mb-4">
              <div
                class="w-28 h-28 sm:w-32 sm:h-32 rounded-full overflow-hidden bg-gradient-to-br from-gray-100 to-gray-200 dark:from-gray-700 dark:to-gray-800 flex items-center justify-center shadow-[0_4px_12px_rgba(0,0,0,0.1)] dark:shadow-[0_4px_12px_rgba(0,0,0,0.3)]"
              >
                <img
                  v-if="profile.avatarUrl"
                  :src="profile.avatarUrl"
                  :alt="profile.fullName"
                  class="w-full h-full object-cover"
                  @error="handleImageError"
                />
                <span
                  v-else
                  class="material-symbols-outlined text-5xl sm:text-6xl text-gray-400 dark:text-gray-500"
                  >account_circle</span
                >
              </div>
              <button
                v-if="isEditing"
                @click="handleAvatarClick"
                class="absolute bottom-0 right-0 w-10 h-10 sm:w-12 sm:h-12 bg-primary text-white rounded-full flex items-center justify-center shadow-[0_4px_12px_rgba(245,110,61,0.4)] hover:scale-105 active:scale-95 transition-all duration-200 z-10"
              >
                <span class="material-symbols-outlined text-lg sm:text-xl">photo_camera</span>
              </button>
            </div>
            <input
              ref="avatarInput"
              type="file"
              accept="image/*"
              class="hidden"
              @change="handleAvatarChange"
            />
            <h2
              class="mt-2 text-lg sm:text-xl font-semibold text-text-light dark:text-text-dark text-center"
            >
              {{ profile.fullName || "Chưa cập nhật" }}
            </h2>
            <p class="mt-1 text-sm text-subtext-light dark:text-subtext-dark text-center">
              {{ authStore.user?.email }}
            </p>
          </div>

          <!-- Quick Stats - iOS Style -->
          <div
            class="mt-6 pt-6 border-t border-[rgba(0,0,0,0.08)] dark:border-[rgba(255,255,255,0.1)] space-y-4"
          >
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center flex-shrink-0">
                <span class="material-symbols-outlined text-primary text-lg">badge</span>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-xs text-subtext-light dark:text-subtext-dark mb-0.5">
                  Vai trò
                </p>
                <p class="font-semibold text-sm text-text-light dark:text-text-dark truncate">
                  {{ getRoleText(authStore.user?.roleType) }}
                </p>
              </div>
            </div>
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center flex-shrink-0">
                <span class="material-symbols-outlined text-primary text-lg">location_on</span>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-xs text-subtext-light dark:text-subtext-dark mb-0.5">
                  Quốc gia
                </p>
                <p class="font-semibold text-sm text-text-light dark:text-text-dark truncate">
                  {{ profile.countryName || "Chưa cập nhật" }}
                </p>
              </div>
            </div>
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center flex-shrink-0">
                <span class="material-symbols-outlined text-primary text-lg">card_membership</span>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-xs text-subtext-light dark:text-subtext-dark mb-0.5">
                  Membership
                </p>
                <p class="font-semibold text-sm text-text-light dark:text-text-dark truncate">
                  {{
                    profile.membershipName ||
                    authStore.user?.membershipName ||
                    "Chưa có"
                  }}
                </p>
                <p
                  v-if="
                    profile.membershipStartDate && profile.membershipEndDate
                  "
                  class="text-xs text-subtext-light dark:text-subtext-dark mt-0.5"
                >
                  {{
                    formatMembershipPeriod(
                      profile.membershipStartDate,
                      profile.membershipEndDate
                    )
                  }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Content - Detailed Info -->
      <div class="lg:col-span-2 space-y-4 sm:space-y-6">
        <div class="card-premium p-5 sm:p-6">
          <!-- Edit/Save Buttons - iOS Style -->
          <div class="flex justify-between items-center mb-5 sm:mb-6">
            <h3 class="text-lg sm:text-xl font-semibold text-text-light dark:text-text-dark">Thông tin chi tiết</h3>
            <button
              v-if="!isEditing"
              @click="startEditing"
              class="px-4 py-2.5 bg-primary text-white rounded-[14px] font-medium hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 flex items-center gap-2 shadow-[0_4px_14px_rgba(245,110,61,0.3)]"
            >
              <span class="material-symbols-outlined text-sm">edit</span>
              <span class="text-sm">Chỉnh sửa</span>
            </button>
            <div v-else class="flex gap-2">
              <button
                @click="cancelEditing"
                class="px-4 py-2.5 bg-[rgba(142,142,147,0.12)] dark:bg-[rgba(142,142,147,0.2)] backdrop-blur-[20px] text-text-light dark:text-text-dark rounded-[14px] font-medium hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)]"
              >
                <span class="text-sm">Hủy</span>
              </button>
              <button
                @click="saveProfile"
                :disabled="isSaving"
                class="px-4 py-2.5 bg-primary text-white rounded-[14px] font-medium hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2 shadow-[0_4px_14px_rgba(245,110,61,0.3)]"
              >
                <span v-if="isSaving" class="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent"></span>
                <span v-else class="material-symbols-outlined text-sm">save</span>
                <span class="text-sm">{{ isSaving ? "Đang lưu..." : "Lưu" }}</span>
              </button>
            </div>
          </div>

          <!-- Form Fields - iOS Style -->
          <form @submit.prevent="saveProfile" class="space-y-5">
            <!-- Full Name -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2.5"
              >
                Họ và tên
              </label>
              <input
                v-model="formData.fullName"
                type="text"
                :disabled="!isEditing"
                placeholder="Nhập họ và tên"
                class="w-full px-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark placeholder:text-subtext-light dark:placeholder:text-subtext-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:border-primary/30"
              />
            </div>

            <!-- Date of Birth -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2.5"
              >
                Ngày sinh
              </label>
              <input
                v-model="formData.dateOfBirth"
                type="date"
                :disabled="!isEditing"
                class="w-full px-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:border-primary/30"
              />
            </div>

            <!-- Address -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2.5"
              >
                Địa chỉ
              </label>
              <textarea
                v-model="formData.address"
                :disabled="!isEditing"
                rows="3"
                placeholder="Nhập địa chỉ"
                class="w-full px-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark placeholder:text-subtext-light dark:placeholder:text-subtext-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary resize-none transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:border-primary/30"
              ></textarea>
            </div>

            <!-- Country -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2.5"
              >
                Quốc gia
              </label>
              <select
                v-model="formData.countryId"
                :disabled="!isEditing || isLoadingCountries"
                class="w-full px-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:border-primary/30 appearance-none bg-[url('data:image/svg+xml;charset=UTF-8,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%2212%22%20height%3D%2212%22%20viewBox%3D%220%200%2012%2012%22%3E%3Cpath%20fill%3D%22%23666%22%20d%3D%22M6%209L1%204h10z%22%2F%3E%3C%2Fsvg%3E')] bg-no-repeat bg-right-3 bg-[length:12px] pr-10"
              >
                <option :value="null">Chọn quốc gia</option>
                <option
                  v-for="country in countries"
                  :key="country.id"
                  :value="country.id"
                >
                  {{ country.name }}
                </option>
              </select>
            </div>

            <!-- Avatar URL (Optional - for advanced users) -->
            <div v-if="isEditing">
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2.5"
              >
                URL Avatar (Tùy chọn)
              </label>
              <input
                v-model="formData.avatarUrl"
                type="url"
                placeholder="https://example.com/avatar.jpg"
                class="w-full px-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark placeholder:text-subtext-light dark:placeholder:text-subtext-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary transition-all duration-200 hover:border-primary/30"
              />
            </div>
          </form>
        </div>

        <!-- Booking History Link - iOS Style -->
        <div class="card-premium p-5 sm:p-6">
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <h3 class="text-base sm:text-lg font-semibold mb-1 text-text-light dark:text-text-dark">Lịch sử đặt bàn</h3>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Xem các đơn đặt bàn của bạn
              </p>
            </div>
            <router-link
              to="/booking-history"
              class="px-4 py-2.5 bg-primary text-white rounded-[14px] font-medium hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 flex items-center gap-2 shadow-[0_4px_14px_rgba(245,110,61,0.3)] ml-4"
            >
              <span class="text-sm">Xem lịch sử</span>
              <span class="material-symbols-outlined text-sm">arrow_forward</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useAuthStore} from "@/stores/auth";
import {userApi, locationApi} from "@/api";

const authStore = useAuthStore();

// State
const profile = ref(null);
const isLoading = ref(false);
const isEditing = ref(false);
const isSaving = ref(false);
const errorMessage = ref("");
const successMessage = ref("");
const countries = ref([]);
const isLoadingCountries = ref(false);
const avatarInput = ref(null);

// Form Data
const formData = ref({
  fullName: "",
  dateOfBirth: "",
  address: "",
  avatarUrl: "",
  countryId: null,
});

// Fetch profile
const fetchProfile = async () => {
  isLoading.value = true;
  errorMessage.value = "";
  try {
    console.log("👤 Fetching user profile");
    const data = await userApi.getMyProfile();
    profile.value = data;

    // Populate form data
    formData.value = {
      fullName: data.fullName || "",
      dateOfBirth: data.dateOfBirth || "",
      address: data.address || "",
      avatarUrl: data.avatarUrl || "",
      countryId: data.countryId || null,
    };

    console.log("✅ Profile loaded:", data);
  } catch (err) {
    console.error("❌ Error fetching profile:", err);
    errorMessage.value = "Không thể tải thông tin cá nhân";
  } finally {
    isLoading.value = false;
  }
};

// Fetch countries
const fetchCountries = async () => {
  isLoadingCountries.value = true;
  try {
    const data = await locationApi.getCountries();
    countries.value = Array.isArray(data) ? data : [];
    console.log("🌍 Countries loaded:", countries.value.length);
  } catch (err) {
    console.error("❌ Error fetching countries:", err);
    countries.value = [];
  } finally {
    isLoadingCountries.value = false;
  }
};

// Start editing
const startEditing = () => {
  isEditing.value = true;
  // Reset form data from current profile
  formData.value = {
    fullName: profile.value.fullName || "",
    dateOfBirth: profile.value.dateOfBirth || "",
    address: profile.value.address || "",
    avatarUrl: profile.value.avatarUrl || "",
    countryId: profile.value.countryId || null,
  };
};

// Cancel editing
const cancelEditing = () => {
  isEditing.value = false;
  errorMessage.value = "";
  successMessage.value = "";
};

// Save profile
const saveProfile = async () => {
  errorMessage.value = "";
  successMessage.value = "";
  isSaving.value = true;

  try {
    console.log("💾 Saving profile:", formData.value);

    const updateData = {
      fullName: formData.value.fullName || null,
      dateOfBirth: formData.value.dateOfBirth || null,
      address: formData.value.address || null,
      avatarUrl: formData.value.avatarUrl || null,
      countryId: formData.value.countryId || null,
    };

    const updatedProfile = await userApi.updateMyProfile(updateData);
    profile.value = updatedProfile;

    // Update auth store with new profile info
    await authStore.fetchUserProfile();

    successMessage.value = "Cập nhật thông tin thành công!";
    isEditing.value = false;

    setTimeout(() => {
      successMessage.value = "";
    }, 3000);

    console.log("✅ Profile updated:", updatedProfile);
  } catch (err) {
    console.error("❌ Error updating profile:", err);
    errorMessage.value = err.message || "Cập nhật thất bại. Vui lòng thử lại.";
  } finally {
    isSaving.value = false;
  }
};

// Handle avatar click
const handleAvatarClick = () => {
  avatarInput.value?.click();
};

// Handle avatar change
const handleAvatarChange = (event) => {
  const file = event.target.files?.[0];
  if (file) {
    // TODO: Upload to server and get URL
    // For now, just show a message
    errorMessage.value =
      "Tính năng tải ảnh đang được phát triển. Vui lòng sử dụng URL Avatar.";
  }
};

// Handle image error
const handleImageError = (event) => {
  event.target.style.display = "none";
};

// Get role text
const getRoleText = (role) => {
  const roleMap = {
    ADMIN: "Quản trị viên",
    OWNER: "Chủ cửa hàng",
    MEMBER: "Thành viên",
  };
  return roleMap[role] || role;
};

// Format membership period
const formatMembershipPeriod = (start, end) => {
  try {
    const s = new Date(start);
    const e = new Date(end);
    return `${new Intl.DateTimeFormat("vi-VN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    }).format(s)} - ${new Intl.DateTimeFormat("vi-VN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    }).format(e)}`;
  } catch (err) {
    return "";
  }
};

// Lifecycle
onMounted(() => {
  fetchProfile();
  fetchCountries();
});
</script>
