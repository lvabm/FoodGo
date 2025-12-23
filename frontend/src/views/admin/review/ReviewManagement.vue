<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý đánh giá</h1>
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
            placeholder="Tên khách hàng, nội dung..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg focus:ring-2 focus:ring-primary/50"
            @keyup.enter="handleSearch"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Đánh giá</label>
          <select
            v-model="filters.rating"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả</option>
            <option value="5">5 sao</option>
            <option value="4">4 sao</option>
            <option value="3">3 sao</option>
            <option value="2">2 sao</option>
            <option value="1">1 sao</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-2">Outlet</label>
          <input
            v-model="filters.outletId"
            type="text"
            placeholder="ID outlet..."
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            @keyup.enter="handleSearch"
          />
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

    <!-- Reviews Table -->
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
              Khách hàng
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Outlet
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Đánh giá
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Nội dung
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Ngày đánh giá
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Trạng thái
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
            v-for="review in reviews"
            :key="review.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="flex items-center">
                <div class="h-10 w-10 flex-shrink-0">
                  <ImageDisplay
                    :image-url="review.userAvatar"
                    :alt="review.userName"
                    placeholder-icon="person"
                    container-class="h-10 w-10 rounded-full"
                    image-class="h-10 w-10 rounded-full"
                    icon-size="24px"
                  />
                </div>
                <div class="ml-4">
                  <div class="text-sm font-medium">
                    {{ review.userName || "N/A" }}
                  </div>
                </div>
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="font-medium">{{ review.outletName || "N/A" }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="flex items-center gap-1">
                <span class="text-yellow-500">★</span>
                <span class="text-sm font-medium">{{ review.overallRating || review.rating || "N/A" }}</span>
              </div>
            </td>
            <td class="px-6 py-4">
              <div
                class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-2 max-w-md"
              >
                {{ review.comment || "Không có bình luận" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-subtext-light dark:text-subtext-dark"
            >
              {{ formatDate(review.createdAt) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="
                  review.isDeleted
                    ? 'px-2 py-1 text-xs font-medium rounded-full bg-red-100 text-red-600 dark:bg-red-900/30 dark:text-red-400'
                    : 'px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-600 dark:bg-green-900/30 dark:text-green-400'
                "
              >
                {{ review.isDeleted ? "Ẩn" : "Hiển thị" }}
              </span>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="toggleReviewVisibility(review)"
                  :class="
                    review.isDeleted
                      ? 'text-green-600 hover:text-green-500'
                      : 'text-yellow-600 hover:text-yellow-500'
                  "
                >
                  {{ review.isDeleted ? "Hiện" : "Ẩn" }}
                </button>
                <button
                  @click="viewReviewDetail(review)"
                  class="text-primary hover:text-primary/80"
                >
                  Chi tiết
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="reviews.length === 0">
            <td colspan="7" class="px-6 py-8 text-center text-subtext-light">
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

    <!-- Review Detail Modal -->
    <div
      v-if="selectedReview"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeDetailModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-3xl border border-border-light dark:border-border-dark max-h-[90vh] overflow-y-auto"
      >
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-xl font-bold">Chi tiết đánh giá</h2>
          <button
            @click="closeDetailModal"
            class="text-gray-500 hover:text-gray-700 dark:hover:text-gray-300"
          >
            <span class="material-symbols-outlined">close</span>
          </button>
        </div>

        <div v-if="detailLoading" class="flex justify-center items-center py-8">
          <LoadingSpinner size="lg" />
        </div>

        <div v-else-if="reviewDetail" class="space-y-6">
          <!-- Customer Info -->
          <div class="flex items-start gap-4">
            <ImageDisplay
              :image-url="reviewDetail.userAvatar"
              :alt="reviewDetail.userName"
              placeholder-icon="person"
              container-class="h-16 w-16 rounded-full flex-shrink-0"
              image-class="h-16 w-16 rounded-full"
              icon-size="32px"
            />
            <div>
              <h3 class="text-lg font-semibold">{{ reviewDetail.userName || "N/A" }}</h3>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ reviewDetail.outletName || "N/A" }}
              </p>
              <p class="text-xs text-subtext-light dark:text-subtext-dark">
                {{ formatDate(reviewDetail.createdAt) }}
              </p>
            </div>
          </div>

          <!-- Ratings -->
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div>
              <label class="text-sm text-subtext-light dark:text-subtext-dark">Tổng thể</label>
              <div class="flex items-center gap-1 mt-1">
                <span class="text-yellow-500">★</span>
                <span class="font-medium">{{ reviewDetail.overallRating || "N/A" }}</span>
              </div>
            </div>
            <div>
              <label class="text-sm text-subtext-light dark:text-subtext-dark">Đồ ăn</label>
              <div class="flex items-center gap-1 mt-1">
                <span class="text-yellow-500">★</span>
                <span class="font-medium">{{ reviewDetail.foodRating || "N/A" }}</span>
              </div>
            </div>
            <div>
              <label class="text-sm text-subtext-light dark:text-subtext-dark">Phục vụ</label>
              <div class="flex items-center gap-1 mt-1">
                <span class="text-yellow-500">★</span>
                <span class="font-medium">{{ reviewDetail.serviceRating || "N/A" }}</span>
              </div>
            </div>
            <div>
              <label class="text-sm text-subtext-light dark:text-subtext-dark">Không gian</label>
              <div class="flex items-center gap-1 mt-1">
                <span class="text-yellow-500">★</span>
                <span class="font-medium">{{ reviewDetail.ambianceRating || "N/A" }}</span>
              </div>
            </div>
            <div>
              <label class="text-sm text-subtext-light dark:text-subtext-dark">Giá cả</label>
              <div class="flex items-center gap-1 mt-1">
                <span class="text-yellow-500">★</span>
                <span class="font-medium">{{ reviewDetail.priceRating || "N/A" }}</span>
              </div>
            </div>
          </div>

          <!-- Comment -->
          <div>
            <label class="block text-sm font-medium mb-2">Nội dung đánh giá</label>
            <p class="text-sm text-subtext-light dark:text-subtext-dark whitespace-pre-wrap">
              {{ reviewDetail.comment || "Không có bình luận" }}
            </p>
          </div>

          <!-- Images -->
          <div v-if="reviewDetail.images && reviewDetail.images.length > 0">
            <label class="block text-sm font-medium mb-2">Hình ảnh</label>
            <div class="grid grid-cols-3 gap-4">
              <div
                v-for="(image, index) in reviewDetail.images"
                :key="index"
                class="relative aspect-square rounded-lg overflow-hidden border border-border-light dark:border-border-dark"
              >
                <img
                  :src="image"
                  :alt="`Review image ${index + 1}`"
                  class="w-full h-full object-cover"
                  @error="handleImageError"
                />
              </div>
            </div>
          </div>

          <!-- Reply -->
          <div v-if="reviewDetail.reply" class="bg-gray-50 dark:bg-surface-light/5 rounded-lg p-4">
            <div class="flex items-center justify-between mb-2">
              <span class="text-sm font-medium">Phản hồi từ chủ quán</span>
              <span class="text-xs text-subtext-light dark:text-subtext-dark">
                {{ formatDate(reviewDetail.reply.createdAt) }}
              </span>
            </div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark">
              {{ reviewDetail.reply.replyText }}
            </p>
            <p class="text-xs text-subtext-light dark:text-subtext-dark mt-2">
              - {{ reviewDetail.reply.ownerName }}
            </p>
          </div>

          <!-- Stats -->
          <div class="flex items-center gap-6 text-sm">
            <div class="flex items-center gap-1">
              <span class="material-symbols-outlined text-red-500">favorite</span>
              <span>{{ reviewDetail.likesCount || 0 }}</span>
            </div>
            <div class="flex items-center gap-1">
              <span class="material-symbols-outlined text-gray-500">thumb_down</span>
              <span>{{ reviewDetail.dislikesCount || 0 }}</span>
            </div>
          </div>
        </div>

        <div class="flex gap-2 justify-end mt-6">
          <button
            @click="closeDetailModal"
            class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            Đóng
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {useRouter} from "vue-router";
import {adminApi} from "@/api";
import {useToast} from "@/composables/useToast";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const {success, error: showError} = useToast();

const router = useRouter();

const isLoading = ref(false);
const error = ref(null);
const reviews = ref([]);
const selectedReview = ref(null);
const reviewDetail = ref(null);
const detailLoading = ref(false);
const filters = ref({
  search: "",
  rating: "",
  outletId: "",
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

const formatDate = (date) => {
  if (!date) return "N/A";
  try {
    return new Date(date).toLocaleDateString("vi-VN");
  } catch {
    return date;
  }
};

const fetchReviews = async () => {
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
    if (filters.value.rating) {
      params.rating = filters.value.rating;
    }
    if (filters.value.outletId) {
      params.outletId = filters.value.outletId;
    }

    const response = await adminApi.getReviews(params);
    
    // Handle PageResponse structure: { data: Array, totalElements, totalPages, ... }
    let pageData = response;
    let allReviews = [];
    
    if (response && typeof response === 'object' && !Array.isArray(response)) {
      if (response.data && Array.isArray(response.data)) {
        allReviews = response.data;
        pageData = response;
      } else if (response.content && Array.isArray(response.content)) {
        allReviews = response.content;
        pageData = response;
      }
    } else if (Array.isArray(response)) {
      allReviews = response;
      pageData = { totalElements: response.length, totalPages: 1 };
    }

    // Ensure reviews is always an array
    reviews.value = Array.isArray(allReviews) ? allReviews : [];
    
    // Update pagination
    if (pageData && typeof pageData === 'object' && !Array.isArray(pageData)) {
      if (pageData.totalElements !== undefined) {
        pagination.value.totalElements = pageData.totalElements;
        pagination.value.totalPages = pageData.totalPages || Math.ceil(pageData.totalElements / pagination.value.pageSize);
      }
    }
  } catch (err) {
    console.error("Error fetching reviews:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách đánh giá";
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.currentPage = 0;
  fetchReviews();
};

const resetFilters = () => {
  filters.value = {
    search: "",
    rating: "",
    outletId: "",
  };
  handleSearch();
};

const goToPage = (page) => {
  if (page >= 0 && page < pagination.value.totalPages) {
    pagination.value.currentPage = page;
    fetchReviews();
  }
};

const toggleReviewVisibility = async (review) => {
  try {
    if (review.isDeleted) {
      // Show review (soft undelete)
      await adminApi.showReview(review.id);
      success("Hiển thị đánh giá thành công");
    } else {
      // Hide review (soft delete)
      await adminApi.hideReview(review.id);
      success("Ẩn đánh giá thành công");
    }
    await fetchReviews();
  } catch (err) {
    showError(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const viewReviewDetail = async (review) => {
  selectedReview.value = review;
  detailLoading.value = true;
  reviewDetail.value = null;
  
  try {
    const response = await adminApi.getReviewDetail(review.id);
    reviewDetail.value = response?.data || response;
  } catch (err) {
    console.error("Error fetching review detail:", err);
    showError(err.response?.data?.message || "Không thể tải chi tiết đánh giá");
    closeDetailModal();
  } finally {
    detailLoading.value = false;
  }
};

const closeDetailModal = () => {
  selectedReview.value = null;
  reviewDetail.value = null;
  detailLoading.value = false;
};

const handleImageError = (event) => {
  event.target.src = "/placeholder-image.png";
};

onMounted(() => {
  fetchReviews();
});
</script>
