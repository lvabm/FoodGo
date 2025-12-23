<template>
  <div class="px-10 py-8">
    <router-link
      to="/admin/outlets"
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

    <div v-if="!isLoading && !error && outlet" class="space-y-6">
      <!-- Header -->
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-start justify-between mb-4">
          <div>
            <h1 class="text-3xl font-bold mb-2">{{ outlet.name }}</h1>
            <div class="flex items-center gap-4 text-sm text-subtext-light">
              <span class="flex items-center gap-1">
                <span class="material-symbols-outlined fill text-yellow-500"
                  >star</span
                >
                <span class="font-medium">{{ outlet.averageRating || 0 }}</span>
                <span>({{ outlet.totalReviews || 0 }} đánh giá)</span>
              </span>
              <span
                :class="
                  outlet.isActive !== false
                    ? 'text-positive'
                    : 'text-red-500'
                "
                class="flex items-center gap-1"
              >
                <span
                  class="w-2 h-2 rounded-full"
                  :class="
                    outlet.isActive !== false
                      ? 'bg-positive'
                      : 'bg-red-500'
                  "
                ></span>
                {{ outlet.isActive !== false ? "Hoạt động" : "Bị khóa" }}
              </span>
            </div>
          </div>
          <div class="flex gap-2">
            <button
              v-if="outlet.isActive === false"
              @click="approveOutlet"
              class="px-4 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90"
            >
              Duyệt
            </button>
            <button
              v-else
              @click="lockOutlet"
              class="px-4 py-2 bg-yellow-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Khóa
            </button>
            <button
              @click="handleDelete"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Xóa
            </button>
          </div>
        </div>
      </div>

      <!-- Images -->
      <div
        v-if="outletImages.length > 0"
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">Hình ảnh</h2>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div
            v-for="image in outletImages"
            :key="image.id"
            class="relative aspect-square rounded-lg overflow-hidden"
          >
            <ImageDisplay
              :image-url="image.imageUrl"
              :alt="outlet.name"
              placeholder-icon="image"
              container-class="w-full h-full rounded-lg"
              image-class="w-full h-full"
            />
          </div>
        </div>
      </div>

      <!-- Main Info -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Description -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Mô tả</h2>
            <p class="text-subtext-light dark:text-subtext-dark">
              {{ outlet.description || "Chưa có mô tả" }}
            </p>
          </div>

          <!-- Contact Info -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Thông tin liên hệ</h2>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Địa chỉ
                </p>
                <p class="font-medium">{{ outlet.address }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Quận/Huyện
                </p>
                <p class="font-medium">{{ outlet.districtName || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Email
                </p>
                <p class="font-medium">{{ outlet.email || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Số điện thoại
                </p>
                <p class="font-medium">{{ outlet.phoneNumber || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Website
                </p>
                <p class="font-medium">
                  <a
                    v-if="outlet.website"
                    :href="outlet.website"
                    target="_blank"
                    class="text-primary hover:underline"
                  >
                    {{ outlet.website }}
                  </a>
                  <span v-else>N/A</span>
                </p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Loại địa điểm
                </p>
                <p class="font-medium">{{ outlet.outletTypeName || "N/A" }}</p>
              </div>
            </div>
          </div>

          <!-- Operating Hours -->
          <div
            v-if="operatingHours.length > 0"
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Giờ hoạt động</h2>
            <div class="space-y-2">
              <div
                v-for="hours in operatingHours"
                :key="hours.id"
                class="flex items-center justify-between p-3 border border-border-light dark:border-border-dark rounded-lg"
              >
                <span class="font-medium">{{ getDayName(hours.dayOfWeek) }}</span>
                <span v-if="hours.isClosed" class="text-red-500">Đóng cửa</span>
                <span v-else class="text-subtext-light">
                  {{ formatTime(hours.openTime) }} - {{ formatTime(hours.closeTime) }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Column -->
        <div class="space-y-6">
          <!-- Quick Stats -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Thống kê</h2>
            <div class="space-y-4">
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Sức chứa
                </p>
                <p class="text-2xl font-bold">{{ outlet.capacity || 0 }} người</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Khoảng giá
                </p>
                <p class="text-2xl font-bold">
                  {{ outlet.priceRange || "N/A" }}
                </p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Tổng đánh giá
                </p>
                <p class="text-2xl font-bold">
                  {{ outlet.totalReviews || 0 }}
                </p>
              </div>
            </div>
          </div>

          <!-- Owner Info -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Chủ sở hữu</h2>
            <router-link
              :to="`/admin/users/${outlet.ownerId}`"
              class="text-primary hover:underline"
            >
              Xem thông tin chủ sở hữu
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import {adminApi, outletApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const route = useRoute();
const router = useRouter();

const isLoading = ref(false);
const error = ref(null);
const outlet = ref(null);
const outletImages = ref([]);
const operatingHours = ref([]);

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

const getDayName = (dayOfWeek) => {
  const days = [
    "Chủ nhật",
    "Thứ hai",
    "Thứ ba",
    "Thứ tư",
    "Thứ năm",
    "Thứ sáu",
    "Thứ bảy",
  ];
  return days[dayOfWeek] || `Thứ ${dayOfWeek + 1}`;
};

const fetchOutletDetail = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const outletId = route.params.id;
    const response = await adminApi.getOutletDetail(outletId);
    outlet.value = response.data || response;

    // Fetch images
    try {
      const imagesResponse = await outletApi.getOutletImages(outletId);
      outletImages.value = imagesResponse.data || imagesResponse || [];
    } catch (err) {
      console.warn("Could not fetch images:", err);
      outletImages.value = [];
    }

    // Fetch operating hours
    try {
      const hoursResponse = await outletApi.getOperatingHours(outletId);
      operatingHours.value = hoursResponse.data || hoursResponse || [];
    } catch (err) {
      console.warn("Could not fetch operating hours:", err);
      operatingHours.value = [];
    }
  } catch (err) {
    console.error("Error fetching outlet detail:", err);
    error.value = err.response?.data?.message || "Không thể tải thông tin địa điểm";
  } finally {
    isLoading.value = false;
  }
};

const approveOutlet = async () => {
  if (!confirm(`Bạn có chắc muốn duyệt địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await adminApi.approveOutlet(outlet.value.id);
    await fetchOutletDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const lockOutlet = async () => {
  if (!confirm(`Bạn có chắc muốn khóa địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await adminApi.lockOutlet(outlet.value.id);
    await fetchOutletDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const handleDelete = async () => {
  if (!confirm(`Bạn có chắc muốn xóa địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await outletApi.deleteOutlet(outlet.value.id);
    router.push("/admin/outlets");
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

onMounted(() => {
  fetchOutletDetail();
});
</script>
