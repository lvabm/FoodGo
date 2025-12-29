<template>
  <div class="w-full">
    <!-- Loading State -->
    <div v-if="isLoadingOutlet" class="w-full max-w-3xl mx-auto px-4 py-12">
      <div class="flex items-center justify-center">
        <div
          class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
        ></div>
      </div>
    </div>

    <!-- Booking Form -->
    <div
      v-else-if="outlet"
      class="w-full max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-8"
    >
      <!-- Header with Premium Style -->
      <div class="mb-8 animate-fade-in-scale">
        <h1 class="text-4xl sm:text-5xl font-black text-text-light dark:text-text-dark mb-2">
          <span class="text-gradient-primary">Đặt bàn</span>
        </h1>
        <div class="flex items-center gap-2 mb-2">
          <span class="material-symbols-outlined text-primary">restaurant</span>
          <p class="text-lg font-semibold text-text-light dark:text-text-dark">
            {{ outlet.name }}
          </p>
        </div>
        <div class="flex items-center gap-2">
          <span class="material-symbols-outlined text-subtext-light dark:text-subtext-dark text-sm">location_on</span>
          <p class="text-sm text-subtext-light dark:text-subtext-dark">
            {{ outlet.address }}
          </p>
        </div>
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

      <!-- Form with Premium Style -->
      <form
        @submit.prevent="handleSubmit"
        class="card-premium p-8 space-y-6 animate-fade-in-scale stagger-1"
      >
        <!-- Date Selection -->
        <div>
          <label
            class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
          >
            Ngày đặt bàn <span class="text-red-500">*</span>
          </label>
          <input
            v-model="formData.bookingDate"
            type="date"
            :min="minDate"
            required
            class="w-full px-4 py-3 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-4 focus:ring-primary/20 focus:border-primary transition-all duration-300 hover:border-primary/50"
          />
        </div>

        <!-- Time Selection -->
        <div>
          <label
            class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
          >
            Giờ đặt bàn <span class="text-red-500">*</span>
          </label>
          <input
            v-model="formData.bookingTime"
            type="time"
            required
            class="w-full px-4 py-3 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-4 focus:ring-primary/20 focus:border-primary transition-all duration-300 hover:border-primary/50"
          />
        </div>

        <!-- Number of Guests -->
        <div>
          <label
            class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
          >
            Số lượng khách <span class="text-red-500">*</span>
          </label>
          <div class="flex items-center gap-4">
            <button
              type="button"
              @click="decreaseGuests"
              class="w-12 h-12 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark hover:bg-primary/10 dark:hover:bg-primary/20 hover:border-primary transition-all duration-300 flex items-center justify-center hover:scale-110"
            >
              <span class="material-symbols-outlined text-primary">remove</span>
            </button>
            <input
              v-model.number="formData.numberOfGuests"
              type="number"
              min="1"
              :max="outlet?.capacity || 50"
              required
              class="flex-1 px-4 py-3 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-center font-bold text-lg focus:ring-4 focus:ring-primary/20 focus:border-primary transition-all duration-300 hover:border-primary/50"
            />
            <button
              type="button"
              @click="increaseGuests"
              class="w-12 h-12 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark hover:bg-primary/10 dark:hover:bg-primary/20 hover:border-primary transition-all duration-300 flex items-center justify-center hover:scale-110"
            >
              <span class="material-symbols-outlined text-primary">add</span>
            </button>
          </div>
        </div>

        <!-- User Notes -->
        <div>
          <label
            class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
          >
            Ghi chú (Tùy chọn)
          </label>
          <textarea
            v-model="formData.userNotes"
            rows="4"
            placeholder="Ví dụ: Yêu cầu vị trí ngồi, dị ứng thực phẩm, ..."
            class="w-full px-4 py-3 rounded-xl border-2 border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-4 focus:ring-primary/20 focus:border-primary resize-none transition-all duration-300 hover:border-primary/50"
          ></textarea>
        </div>

        <!-- Summary -->
        <div class="border-t border-border-light dark:border-border-dark pt-6">
          <h3 class="font-semibold mb-4">Thông tin đặt bàn</h3>
          <div class="space-y-2 text-sm">
            <div class="flex justify-between">
              <span class="text-subtext-light dark:text-subtext-dark"
                >Nhà hàng:</span
              >
              <span class="font-medium">{{ outlet.name }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-subtext-light dark:text-subtext-dark"
                >Ngày:</span
              >
              <span class="font-medium">{{
                formatDate(formData.bookingDate)
              }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-subtext-light dark:text-subtext-dark"
                >Giờ:</span
              >
              <span class="font-medium">{{
                formData.bookingTime || "--:--"
              }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-subtext-light dark:text-subtext-dark"
                >Số khách:</span
              >
              <span class="font-medium"
                >{{ formData.numberOfGuests }} người</span
              >
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="flex gap-4">
          <button
            type="button"
            @click="$router.back()"
            class="flex-1 px-6 py-3 rounded-lg border border-border-light dark:border-border-dark text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors font-medium"
          >
            Hủy
          </button>
          <button
            type="submit"
            :disabled="isSubmitting"
            class="btn-premium flex-1 px-6 py-3 rounded-xl text-white font-bold disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:transform-none"
          >
            <span v-if="!isSubmitting" class="flex items-center justify-center gap-2">
              <span class="material-symbols-outlined">restaurant</span>
              Xác nhận đặt bàn
            </span>
            <span v-else class="flex items-center justify-center gap-2">
              <LoadingSpinner size="sm" color="white" />
              Đang xử lý...
            </span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {useRoute, useRouter} from "vue-router";
import {outletApi} from "@/api";
import {bookingApi} from "@/api/booking";
import {useAuthStore} from "@/stores/auth";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

// State
const outlet = ref(null);
const isLoadingOutlet = ref(false);
const isSubmitting = ref(false);
const errorMessage = ref("");
const successMessage = ref("");

// Form Data
const formData = ref({
  bookingDate: "",
  bookingTime: "",
  numberOfGuests: 2,
  userNotes: "",
});

// Computed
const minDate = computed(() => {
  const today = new Date();
  return today.toISOString().split("T")[0];
});

// Fetch outlet details
const fetchOutlet = async () => {
  isLoadingOutlet.value = true;
  try {
    const outletId = route.params.outletId;
    console.log("🔍 Fetching outlet for booking:", outletId);
    const data = await outletApi.getOutletDetail(outletId);
    outlet.value = data;

    // Set default date to today
    formData.value.bookingDate = minDate.value;
  } catch (err) {
    console.error("❌ Error fetching outlet:", err);
    errorMessage.value = "Không thể tải thông tin nhà hàng";
  } finally {
    isLoadingOutlet.value = false;
  }
};

// Guest controls
const increaseGuests = () => {
  if (formData.value.numberOfGuests < 50) {
    formData.value.numberOfGuests++;
  }
};

const decreaseGuests = () => {
  if (formData.value.numberOfGuests > 1) {
    formData.value.numberOfGuests--;
  }
};

// Format date for display
const formatDate = (dateString) => {
  if (!dateString) return "--/--/----";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("vi-VN", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  }).format(date);
};

// Submit booking
const handleSubmit = async () => {
  // Check authentication - khách vãng lai không thể đặt bàn
  if (!authStore.isAuthenticated) {
    errorMessage.value = "Vui lòng đăng nhập hoặc đăng ký tài khoản để đặt bàn. Chức năng đặt bàn chỉ dành cho thành viên đã đăng ký.";
    setTimeout(() => {
      router.push("/auth/login");
    }, 2500);
    return;
  }

  // Check membership requirement: require active membership (package), regardless of role
  if (!authStore.user?.membershipIsActive) {
    errorMessage.value = "Bạn cần đăng ký gói membership để đặt bàn";
    setTimeout(() => {
      router.push("/membership");
    }, 2000);
    return;
  }

  // Owners cannot book at their own outlet
  if (authStore.isOwner && outlet.value?.owner?.id === authStore.user?.id) {
    errorMessage.value =
      "Bạn không thể đặt bàn tại chính quán của mình. Vui lòng chọn quán khác.";
    return;
  }

  errorMessage.value = "";
  successMessage.value = "";
  isSubmitting.value = true;

  try {
    console.log("📝 Submitting booking:", formData.value);

    const bookingData = {
      outletId: route.params.outletId,
      bookingDate: formData.value.bookingDate,
      bookingTime: formData.value.bookingTime,
      numberOfGuests: formData.value.numberOfGuests,
      userNotes: formData.value.userNotes || null,
    };

    const response = await bookingApi.createBooking(bookingData);
    console.log("✅ Booking created:", response);

    successMessage.value = "Đặt bàn thành công! Đang chuyển hướng...";

    setTimeout(() => {
      router.push("/booking-history");
    }, 2000);
  } catch (err) {
    console.error("❌ Error creating booking:", err);
    errorMessage.value = err.message || "Đặt bàn thất bại. Vui lòng thử lại.";
  } finally {
    isSubmitting.value = false;
  }
};

// Lifecycle
onMounted(() => {
  // Check authentication first
  if (!authStore.isAuthenticated) {
    errorMessage.value = "Vui lòng đăng nhập để đặt bàn. Đang chuyển hướng...";
    setTimeout(() => {
      router.push("/auth/login");
    }, 2000);
    return;
  }

  // Check membership
  if (!authStore.user?.membershipIsActive) {
    errorMessage.value = "Bạn cần đăng ký gói membership để đặt bàn. Đang chuyển hướng...";
    setTimeout(() => {
      router.push("/membership");
    }, 2000);
    return;
  }

  fetchOutlet();
});
</script>
