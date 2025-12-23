<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-text-light dark:text-text-dark">
          Phản hồi & Đánh giá
        </h1>
        <p class="text-subtext-light dark:text-subtext-dark">
          Quản lý đánh giá từ khách hàng
        </p>
      </div>
      <div class="flex items-center gap-3">
        <select
          v-model="selectedOutletId"
          @change="loadReviews"
          class="px-3 py-2 border rounded-lg pr-8 truncate appearance-none"
        >
          <option disabled value="">Chọn quán</option>
          <option v-for="o in outlets" :key="o.id" :value="o.id">
            {{ o.name }}
          </option>
        </select>
        <button
          @click="refresh"
          class="px-4 py-2 bg-primary text-white rounded-lg"
        >
          Làm mới
        </button>
      </div>
    </div>

    <div class="mb-4 flex items-center gap-3 flex-wrap">
      <input
        v-model="q"
        @keyup.enter="refresh"
        placeholder="Tìm theo tên hoặc nội dung"
        class="px-3 py-2 border rounded-lg w-96 focus:outline-none focus:ring-2 focus:ring-primary"
      />
      <select 
        v-model="minRating" 
        @change="refresh"
        class="px-3 py-2 border rounded-lg pr-8 truncate appearance-none focus:outline-none focus:ring-2 focus:ring-primary"
      >
        <option :value="0">Tất cả</option>
        <option :value="5">5⭐</option>
        <option :value="4">4⭐+</option>
        <option :value="3">3⭐+</option>
      </select>
      <label class="flex items-center gap-2 text-sm text-subtext-light cursor-pointer">
        <input 
          type="checkbox" 
          v-model="onlyUnreplied"
          @change="refresh"
          class="cursor-pointer"
        /> 
        Chưa trả lời
      </label>
    </div>

    <div v-if="loading" class="py-10 flex justify-center">
      <div
        class="animate-spin rounded-full h-10 w-10 border-b-2 border-primary"
      ></div>
    </div>

    <div v-if="error" class="mb-4 p-3 bg-red-50 text-red-700 rounded">
      {{ error }}
    </div>

    <div v-else>
      <div
        v-if="reviews.length === 0"
        class="text-center py-20 text-subtext-light"
      >
        Không có đánh giá
      </div>

      <div v-else class="space-y-4">
        <div
          v-for="rev in reviews"
          :key="rev.id"
          class="bg-white dark:bg-surface-dark rounded-xl p-4 border border-border-light"
        >
          <div class="flex items-start gap-4">
            <div
              class="w-12 h-12 rounded-full bg-gray-100 flex items-center justify-center overflow-hidden"
            >
              <img
                v-if="rev.userAvatar"
                :src="rev.userAvatar"
                class="w-full h-full object-cover"
              />
              <span v-else class="material-symbols-outlined text-primary"
                >person</span
              >
            </div>
            <div class="flex-1">
              <div class="flex items-center justify-between">
                <div>
                  <div class="font-medium text-text-light">
                    {{ rev.userName }}
                  </div>
                  <div class="text-xs text-subtext-light">
                    {{ formatDate(rev.createdAt) }}
                  </div>
                </div>
                <div class="flex items-center gap-2">
                  <div class="flex items-center">
                    <template v-for="i in 5" :key="i">
                      <span
                        class="material-symbols-outlined text-sm"
                        :class="i <= (rev.overallRating || 0) ? 'text-yellow-500' : 'text-gray-300'">
                        {{ i <= (rev.overallRating || 0) ? 'star' : 'star_border' }}
                      </span>
                    </template>
                  </div>
                  <span class="text-sm font-medium ml-2">{{ rev.overallRating || 0 }}/5</span>
                </div>
              </div>

              <p class="mt-3 text-subtext-light">{{ rev.comment }}</p>

              <!-- Per-aspect ratings -->
              <div
                class="mt-3 grid grid-cols-2 gap-2 text-sm text-subtext-light"
              >
                <div class="flex items-center gap-2">
                  <span class="font-medium">Đồ ăn:</span>
                  <div class="flex items-center">
                    <template v-for="i in 5" :key="`food-${i}-${rev.id}`">
                      <span class="material-symbols-outlined text-yellow-400">
                        {{
                          i <= (rev.foodRating || 0) ? "star" : "star_border"
                        }}
                      </span>
                    </template>
                    <span class="ml-2">{{ rev.foodRating || 0 }}/5</span>
                  </div>
                </div>

                <div class="flex items-center gap-2">
                  <span class="font-medium">Phục vụ:</span>
                  <div class="flex items-center">
                    <template v-for="i in 5" :key="`service-${i}-${rev.id}`">
                      <span class="material-symbols-outlined text-yellow-400">
                        {{
                          i <= (rev.serviceRating || 0) ? "star" : "star_border"
                        }}
                      </span>
                    </template>
                    <span class="ml-2">{{ rev.serviceRating || 0 }}/5</span>
                  </div>
                </div>

                <div class="flex items-center gap-2">
                  <span class="font-medium">Không gian:</span>
                  <div class="flex items-center">
                    <template v-for="i in 5" :key="`amb-${i}-${rev.id}`">
                      <span class="material-symbols-outlined text-yellow-400">
                        {{
                          i <= (rev.ambianceRating || 0)
                            ? "star"
                            : "star_border"
                        }}
                      </span>
                    </template>
                    <span class="ml-2">{{ rev.ambianceRating || 0 }}/5</span>
                  </div>
                </div>

                <div class="flex items-center gap-2">
                  <span class="font-medium">Giá cả:</span>
                  <div class="flex items-center">
                    <template v-for="i in 5" :key="`price-${i}-${rev.id}`">
                      <span class="material-symbols-outlined text-yellow-400">
                        {{
                          i <= (rev.priceRating || 0) ? "star" : "star_border"
                        }}
                      </span>
                    </template>
                    <span class="ml-2">{{ rev.priceRating || 0 }}/5</span>
                  </div>
                </div>
              </div>

              <div v-if="rev.images?.length" class="mt-2 flex gap-2">
                <img
                  v-for="img in rev.images"
                  :key="img"
                  :src="img"
                  class="w-20 h-20 object-cover rounded"
                />
              </div>

              <div class="mt-3">
                <div v-if="rev.reply" class="p-3 bg-primary/5 rounded-lg border border-primary/20">
                  <div class="flex items-center gap-2 mb-2">
                    <span class="material-symbols-outlined text-primary text-sm">reply</span>
                    <div class="text-sm font-medium text-text-light">Phản hồi của chủ quán</div>
                  </div>
                  <div class="text-sm text-subtext-light whitespace-pre-wrap">
                    {{ rev.reply.replyText }}
                  </div>
                  <div class="text-xs text-subtext-light mt-2">
                    {{ formatDate(rev.reply.createdAt) }}
                  </div>
                </div>

                <div v-else class="mt-2">
                  <textarea
                    v-model="replyMap[rev.id]"
                    class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
                    rows="3"
                    placeholder="Viết phản hồi..."
                    :maxlength="1000"
                  />
                  <div class="flex items-center justify-between mt-2">
                    <span class="text-xs text-subtext-light">
                      {{ (replyMap[rev.id] || '').length }}/1000 ký tự
                    </span>
                    <div class="flex gap-2">
                      <button
                        @click="submitReply(rev.id)"
                        :disabled="replyLoading[rev.id] || !replyMap[rev.id]?.trim()"
                        class="px-4 py-2 bg-primary text-white rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-primary/90"
                      >
                        <span v-if="replyLoading[rev.id]">Đang gửi...</span>
                        <span v-else>Gửi phản hồi</span>
                      </button>
                      <button
                        @click="replyMap[rev.id] = ''"
                        :disabled="replyLoading[rev.id]"
                        class="px-4 py-2 border rounded-lg hover:bg-gray-50 disabled:opacity-50"
                      >
                        Huỷ
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div
        v-if="totalPages > 1"
        class="flex justify-center items-center gap-2 mt-6"
      >
        <button
          @click="changePage(currentPage - 1)"
          :disabled="currentPage === 0"
          class="px-3 py-2 rounded-lg border border-border-light disabled:opacity-50 hover:bg-primary/5"
        >
          <span class="material-symbols-outlined">chevron_left</span>
        </button>
        <button
          v-for="p in visiblePages"
          :key="p"
          @click="changePage(p)"
          :class="[
            'px-4 py-2 rounded-lg border transition-colors',
            currentPage === p
              ? 'bg-primary text-white border-primary'
              : 'border-border-light hover:bg-primary/5',
          ]"
        >
          {{ p + 1 }}
        </button>
        <button
          @click="changePage(currentPage + 1)"
          :disabled="currentPage >= totalPages - 1"
          class="px-3 py-2 rounded-lg border border-border-light disabled:opacity-50 hover:bg-primary/5"
        >
          <span class="material-symbols-outlined">chevron_right</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted, watch} from "vue";
import {reviewApi, outletApi} from "@/api";
import {useToast} from "@/composables/useToast";

const outlets = ref([]);
const selectedOutletId = ref("");
const reviews = ref([]);
const loading = ref(false);
const error = ref("");
const q = ref("");
const minRating = ref(0);
const onlyUnreplied = ref(false);

// pagination
const currentPage = ref(0);
const pageSize = ref(10);
const totalPages = ref(0);

const replyMap = ref({});
const replyLoading = ref({});

const {success, error: showError} = useToast();

const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5;
  let start = Math.max(0, currentPage.value - Math.floor(maxVisible / 2));
  let end = Math.min(totalPages.value, start + maxVisible);
  if (end - start < maxVisible) start = Math.max(0, end - maxVisible);
  for (let i = start; i < end; i++) pages.push(i);
  return pages;
});

const formatDate = (iso) => {
  if (!iso) return "";
  const d = new Date(iso);
  return d.toLocaleString("vi-VN");
};

const extractPageData = (resp) => {
  if (!resp) return {items: [], totalPages: 0};
  const items = resp.data?.data || resp.data || [];
  return {items, totalPages: resp.totalPages ?? resp.data?.totalPages ?? 0};
};

const loadOutlets = async () => {
  try {
    const resp = await outletApi.getMyOutlets();
    outlets.value = resp.data || resp || [];
    if (outlets.value.length) selectedOutletId.value = outlets.value[0].id;
  } catch (err) {
    console.error(err);
    outlets.value = [];
  }
};

const buildParams = () => {
  const params = {page: currentPage.value, size: pageSize.value};
  if (q.value) params.q = q.value;
  if (minRating.value) params.minRating = minRating.value;
  // Map onlyUnreplied to hasReply=false for backend
  if (onlyUnreplied.value) params.hasReply = false;
  if (selectedOutletId.value) params.outletId = selectedOutletId.value;
  return params;
};

const loadReviews = async () => {
  if (!selectedOutletId.value) return;
  loading.value = true;
  error.value = "";
  try {
    const params = buildParams();
    const resp = await reviewApi.getOutletReviews(
      selectedOutletId.value,
      params
    );
    const {items, totalPages: tp} = extractPageData(resp);
    reviews.value = items;
    totalPages.value = tp;
    // init replyMap
    reviews.value.forEach((r) => {
      if (!replyMap.value[r.id]) replyMap.value[r.id] = "";
      if (replyLoading.value[r.id] === undefined) replyLoading.value[r.id] = false;
    });
  } catch (err) {
    console.error(err);
    const errorMsg = err.response?.data?.message || err.message || "Không thể tải đánh giá";
    error.value = errorMsg;
    showError(errorMsg);
  } finally {
    loading.value = false;
  }
};

const submitReply = async (reviewId) => {
  const replyText = replyMap.value[reviewId]?.trim();
  if (!replyText) {
    showError("Vui lòng nhập nội dung phản hồi");
    return;
  }
  
  // Validate length
  if (replyText.length < 5) {
    showError("Phản hồi phải có ít nhất 5 ký tự");
    return;
  }
  
  if (replyText.length > 1000) {
    showError("Phản hồi không được vượt quá 1000 ký tự");
    return;
  }
  
  replyLoading.value[reviewId] = true;
  try {
    await reviewApi.replyReview(reviewId, replyText);
    replyMap.value[reviewId] = "";
    success("Đã gửi phản hồi thành công");
    // reload reviews to show reply
    await loadReviews();
  } catch (err) {
    console.error(err);
    const errorMsg = err.response?.data?.message || err.message || "Gửi phản hồi thất bại";
    showError(errorMsg);
  } finally {
    replyLoading.value[reviewId] = false;
  }
};

const changePage = (p) => {
  if (p >= 0 && p < totalPages.value) {
    currentPage.value = p;
    loadReviews();
  }
};

const refresh = () => {
  currentPage.value = 0;
  loadReviews();
};

// Watch filter changes to auto-reload
watch([onlyUnreplied, minRating], () => {
  currentPage.value = 0;
  loadReviews();
});

onMounted(async () => {
  await loadOutlets();
  await loadReviews();
});
</script>
