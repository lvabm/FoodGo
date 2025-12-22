<template>
  <div class="w-full max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-text-light dark:text-text-dark mb-2">
        Hồ sơ cá nhân
      </h1>
      <p class="text-subtext-light dark:text-subtext-dark">
        Quản lý thông tin cá nhân của bạn
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex items-center justify-center py-12">
      <div
        class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
      ></div>
    </div>

    <!-- Error Message -->
    <div
      v-if="errorMessage"
      class="mb-6 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg"
    >
      {{ errorMessage }}
    </div>

    <!-- Success Message -->
    <div
      v-if="successMessage"
      class="mb-6 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg"
    >
      {{ successMessage }}
    </div>

    <!-- Profile Content -->
    <div
      v-if="!isLoading && profile"
      class="grid grid-cols-1 lg:grid-cols-3 gap-6"
    >
      <!-- Left Sidebar - Avatar & Basic Info -->
      <div class="lg:col-span-1">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark p-6"
        >
          <!-- Avatar -->
          <div class="flex flex-col items-center">
            <div class="relative">
              <div
                class="w-32 h-32 rounded-full overflow-hidden bg-gray-200 dark:bg-gray-700 flex items-center justify-center"
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
                  class="material-symbols-outlined text-6xl text-gray-400"
                  >account_circle</span
                >
              </div>
              <button
                v-if="isEditing"
                @click="handleAvatarClick"
                class="absolute bottom-0 right-0 w-10 h-10 bg-primary text-white rounded-full flex items-center justify-center hover:bg-opacity-90 transition-colors"
              >
                <span class="material-symbols-outlined text-xl"
                  >photo_camera</span
                >
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
              class="mt-4 text-xl font-bold text-text-light dark:text-text-dark text-center"
            >
              {{ profile.fullName || "Chưa cập nhật" }}
            </h2>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              {{ authStore.user?.email }}
            </p>
          </div>

          <!-- Quick Stats -->
          <div
            class="mt-6 pt-6 border-t border-border-light dark:border-border-dark space-y-3"
          >
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary">badge</span>
              <div>
                <p class="text-xs text-subtext-light dark:text-subtext-dark">
                  Vai trò
                </p>
                <p class="font-medium">
                  {{ getRoleText(authStore.user?.roleType) }}
                </p>
              </div>
            </div>
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary"
                >location_on</span
              >
              <div>
                <p class="text-xs text-subtext-light dark:text-subtext-dark">
                  Quốc gia
                </p>
                <p class="font-medium">
                  {{ profile.countryName || "Chưa cập nhật" }}
                </p>
              </div>
            </div>
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary"
                >card_membership</span
              >
              <div>
                <p class="text-xs text-subtext-light dark:text-subtext-dark">
                  Membership
                </p>
                <p class="font-medium">
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
                  class="text-xs text-subtext-light dark:text-subtext-dark"
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
      <div class="lg:col-span-2">
        <div
          class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark p-6"
        >
          <!-- Edit/Save Buttons -->
          <div class="flex justify-between items-center mb-6">
            <h3 class="text-xl font-semibold">Thông tin chi tiết</h3>
            <button
              v-if="!isEditing"
              @click="startEditing"
              class="px-4 py-2 bg-primary text-white rounded-lg font-medium hover:bg-opacity-90 transition-colors flex items-center gap-2"
            >
              <span class="material-symbols-outlined text-sm">edit</span>
              Chỉnh sửa
            </button>
            <div v-else class="flex gap-3">
              <button
                @click="cancelEditing"
                class="px-4 py-2 border border-border-light dark:border-border-dark rounded-lg font-medium hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
              >
                Hủy
              </button>
              <button
                @click="saveProfile"
                :disabled="isSaving"
                class="px-4 py-2 bg-primary text-white rounded-lg font-medium hover:bg-opacity-90 transition-colors disabled:opacity-50 flex items-center gap-2"
              >
                <span class="material-symbols-outlined text-sm">save</span>
                {{ isSaving ? "Đang lưu..." : "Lưu" }}
              </button>
            </div>
          </div>

          <!-- Form Fields -->
          <form @submit.prevent="saveProfile" class="space-y-6">
            <!-- Full Name -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Họ và tên
              </label>
              <input
                v-model="formData.fullName"
                type="text"
                :disabled="!isEditing"
                placeholder="Nhập họ và tên"
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary disabled:opacity-50 disabled:cursor-not-allowed"
              />
            </div>

            <!-- Date of Birth -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Ngày sinh
              </label>
              <input
                v-model="formData.dateOfBirth"
                type="date"
                :disabled="!isEditing"
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary disabled:opacity-50 disabled:cursor-not-allowed"
              />
            </div>

            <!-- Address -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Địa chỉ
              </label>
              <textarea
                v-model="formData.address"
                :disabled="!isEditing"
                rows="3"
                placeholder="Nhập địa chỉ"
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary resize-none disabled:opacity-50 disabled:cursor-not-allowed"
              ></textarea>
            </div>

            <!-- Country -->
            <div>
              <label
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                Quốc gia
              </label>
              <select
                v-model="formData.countryId"
                :disabled="!isEditing || isLoadingCountries"
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary disabled:opacity-50 disabled:cursor-not-allowed"
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
                class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
              >
                URL Avatar (Tùy chọn)
              </label>
              <input
                v-model="formData.avatarUrl"
                type="url"
                placeholder="https://example.com/avatar.jpg"
                class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary"
              />
            </div>
          </form>
        </div>

        <!-- Booking History Link -->
        <div
          class="mt-6 bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark p-6"
        >
          <div class="flex items-center justify-between">
            <div>
              <h3 class="text-lg font-semibold mb-1">Lịch sử đặt bàn</h3>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                Xem các đơn đặt bàn của bạn
              </p>
            </div>
            <router-link
              to="/booking-history"
              class="px-4 py-2 bg-primary text-white rounded-lg font-medium hover:bg-opacity-90 transition-colors flex items-center gap-2"
            >
              Xem lịch sử
              <span class="material-symbols-outlined text-sm"
                >arrow_forward</span
              >
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
