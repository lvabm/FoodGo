<template>
  <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-text-light dark:text-text-dark mb-2">
        Gói Membership
      </h1>
      <p class="text-subtext-light dark:text-subtext-dark">
        Nâng cấp tài khoản để trải nghiệm thêm nhiều tính năng
      </p>
    </div>

    <!-- Current Membership -->
    <div
      v-if="currentMembership"
      class="mb-8 bg-gradient-to-r from-primary/10 to-primary/5 dark:from-primary/20 dark:to-primary/10 rounded-xl border border-primary/20 p-6"
    >
      <div class="flex items-start justify-between">
        <div>
          <div class="flex items-center gap-2 mb-2">
            <span class="material-symbols-outlined text-primary"
              >workspace_premium</span
            >
            <h3 class="text-lg font-bold">
              Gói hiện tại: {{ currentMembership }}
            </h3>
          </div>
          <p
            v-if="membershipEndDate"
            class="text-sm text-subtext-light dark:text-subtext-dark"
          >
            Hết hạn: {{ formatDate(membershipEndDate) }}
          </p>
        </div>
        <button
          v-if="currentMembership"
          @click="openCancelDialog"
          class="px-4 py-2 text-sm border border-red-500 text-red-500 rounded-lg hover:bg-red-500 hover:text-white transition-colors"
        >
          Hủy gói
        </button>
      </div>
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

    <!-- Membership Plans Grid -->
    <div
      v-if="!isLoading"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
    >
      <div
        v-for="plan in membershipPlans"
        :key="plan.id"
        class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark p-6 hover:shadow-lg transition-shadow"
        :class="{'ring-2 ring-primary': plan.name === currentMembership}"
      >
        <!-- Plan Header -->
        <div class="mb-6">
          <div class="flex items-center justify-between mb-2">
            <h3 class="text-xl font-bold text-text-light dark:text-text-dark">
              {{ plan.name }}
            </h3>
            <span
              v-if="plan.name === currentMembership"
              class="px-3 py-1 bg-primary text-white text-xs rounded-full"
            >
              Đang dùng
            </span>
          </div>
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-4">
            {{ plan.description }}
          </p>
          <div class="flex items-baseline gap-2">
            <span class="text-3xl font-bold text-primary">{{
              formatPrice(plan.price)
            }}</span>
            <span class="text-sm text-subtext-light dark:text-subtext-dark"
              >/{{ plan.durationMonths }} tháng</span
            >
          </div>
        </div>

        <!-- Features -->
        <div class="mb-6 space-y-2">
          <div
            v-for="feature in parseFeatures(plan.features)"
            :key="feature"
            class="flex items-center gap-2"
          >
            <span class="material-symbols-outlined text-primary text-sm"
              >check_circle</span
            >
            <span class="text-sm">{{ feature }}</span>
          </div>
          <div v-if="plan.dishLimit" class="flex items-center gap-2">
            <span class="material-symbols-outlined text-primary text-sm"
              >restaurant</span
            >
            <span class="text-sm">Tối đa {{ plan.dishLimit }} món ăn</span>
          </div>
        </div>

        <!-- Action Button -->
        <button
          @click="handleSubscribe(plan)"
          :disabled="isSubscribing || plan.name === currentMembership"
          class="w-full px-4 py-3 rounded-lg font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          :class="
            plan.name === currentMembership
              ? 'bg-gray-200 dark:bg-gray-700 text-gray-500'
              : 'bg-primary text-white hover:bg-opacity-90'
          "
        >
          <span v-if="plan.name === currentMembership">Đã đăng ký</span>
          <span v-else-if="currentMembership">Nâng cấp</span>
          <span v-else>Đăng ký</span>
        </button>
      </div>
    </div>

    <!-- Cancel Confirmation Dialog -->
    <div
      v-if="showCancelDialog"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
    >
      <div class="bg-white dark:bg-surface-dark rounded-xl max-w-md w-full p-6">
        <h3 class="text-xl font-bold mb-4">Xác nhận hủy gói</h3>
        <p class="text-subtext-light dark:text-subtext-dark mb-6">
          Bạn có chắc chắn muốn hủy gói membership hiện tại? Bạn sẽ mất quyền
          truy cập vào các tính năng đặc biệt.
        </p>
        <div class="flex gap-3">
          <button
            @click="showCancelDialog = false"
            class="flex-1 px-4 py-2 border border-border-light dark:border-border-dark rounded-lg font-medium hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
          >
            Đóng
          </button>
          <button
            @click="confirmCancel"
            :disabled="isCancelling"
            class="flex-1 px-4 py-2 bg-red-500 text-white rounded-lg font-medium hover:bg-red-600 transition-colors disabled:opacity-50"
          >
            {{ isCancelling ? "Đang hủy..." : "Xác nhận hủy" }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth";
import {membershipApi} from "@/api/membership";

const router = useRouter();
const authStore = useAuthStore();

// State
const membershipPlans = ref([]);
const isLoading = ref(false);
const isSubscribing = ref(false);
const isCancelling = ref(false);
const errorMessage = ref("");
const successMessage = ref("");
const showCancelDialog = ref(false);

// Computed
const currentMembership = computed(() => authStore.user?.membershipName);
const membershipEndDate = computed(() => authStore.user?.membershipEndDate);

// Fetch membership plans
const fetchPlans = async () => {
  isLoading.value = true;
  errorMessage.value = "";
  try {
    console.log("📋 Fetching membership plans");
    const response = await membershipApi.getMembershipPlans({
      type: "USER", // Only USER type memberships
      page: 0,
      size: 10,
    });

    membershipPlans.value = response.data || [];
    console.log("✅ Plans loaded:", membershipPlans.value.length);
  } catch (err) {
    console.error("❌ Error fetching plans:", err);
    errorMessage.value = "Không thể tải danh sách gói membership";
  } finally {
    isLoading.value = false;
  }
};

// Handle subscribe/upgrade
const handleSubscribe = async (plan) => {
  if (!authStore.isAuthenticated) {
    router.push("/auth/login");
    return;
  }

  errorMessage.value = "";
  successMessage.value = "";
  isSubscribing.value = true;

  try {
    console.log("💳 Subscribing to plan:", plan.id);
    const response = await membershipApi.subscribeToPlan(plan.id);
    console.log("✅ Subscribed successfully:", response);

    successMessage.value = currentMembership.value
      ? `Nâng cấp lên ${plan.name} thành công!`
      : `Đăng ký ${plan.name} thành công!`;

    // Refresh user profile
    await authStore.fetchUserProfile();

    setTimeout(() => {
      successMessage.value = "";
    }, 3000);
  } catch (err) {
    console.error("❌ Error subscribing:", err);
    errorMessage.value = err.message || "Đăng ký thất bại. Vui lòng thử lại.";
  } finally {
    isSubscribing.value = false;
  }
};

// Open cancel dialog
const openCancelDialog = () => {
  showCancelDialog.value = true;
};

// Confirm cancel
const confirmCancel = async () => {
  isCancelling.value = true;
  errorMessage.value = "";
  successMessage.value = "";

  try {
    console.log("🚫 Cancelling membership");
    await membershipApi.cancelCurrentSubscription();
    console.log("✅ Membership cancelled");

    successMessage.value = "Đã hủy gói membership thành công";
    showCancelDialog.value = false;

    // Refresh user profile
    await authStore.fetchUserProfile();

    setTimeout(() => {
      successMessage.value = "";
    }, 3000);
  } catch (err) {
    console.error("❌ Error cancelling:", err);
    errorMessage.value = err.message || "Hủy gói thất bại. Vui lòng thử lại.";
  } finally {
    isCancelling.value = false;
  }
};

// Format price
const formatPrice = (price) => {
  if (price === 0) return "Miễn phí";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

// Format date
const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }).format(date);
};

// Parse features JSON
const parseFeatures = (featuresJson) => {
  try {
    if (typeof featuresJson === "string") {
      return JSON.parse(featuresJson);
    }
    return Array.isArray(featuresJson) ? featuresJson : [];
  } catch {
    return [];
  }
};

// Lifecycle
onMounted(() => {
  fetchPlans();
});
</script>
