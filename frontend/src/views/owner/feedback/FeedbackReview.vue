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
          class="px-3 py-2 border rounded-lg"
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

    <div class="mb-4 flex items-center gap-3">
      <input
        v-model="q"
        @keyup.enter="loadReviews"
        placeholder="Tìm theo tên hoặc nội dung"
        class="px-3 py-2 border rounded-lg w-96"
      />
      <select v-model="minRating" class="px-3 py-2 border rounded-lg">
        <option :value="0">Tất cả</option>
        <option :value="5">5⭐</option>
        <option :value="4">4⭐+</option>
        <option :value="3">3⭐+</option>
      </select>
      <label class="flex items-center gap-2 text-sm text-subtext-light">
        <input type="checkbox" v-model="onlyUnreplied" /> Chưa trả lời
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
                        class="material-symbols-outlined text-yellow-400"
                        v-html="i <= rev.overallRating ? 'star' : 'star_border'"
                      ></span>
                    </template>
                  </div>
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
                <div v-if="rev.reply" class="p-3 bg-primary/5 rounded">
                  <div class="text-sm font-medium">Phản hồi của chủ quán</div>
                  <div class="text-sm text-subtext-light">
                    {{ rev.reply.replyText }}
                  </div>
                  <div class="text-xs text-subtext-light mt-1">
                    {{ formatDate(rev.reply.createdAt) }}
                  </div>
                </div>

                <div v-else class="mt-2">
                  <textarea
                    v-model="replyMap[rev.id]"
                    class="w-full p-3 border rounded-lg"
                    rows="3"
                    placeholder="Viết phản hồi..."
                  />
                  <div class="flex gap-2 mt-2">
                    <button
                      @click="submitReply(rev.id)"
                      :disabled="replyLoading[rev.id]"
                      class="px-4 py-2 bg-primary text-white rounded-lg"
                    >
                      <span v-if="replyLoading[rev.id]">Đang gửi...</span>
                      <span v-else>Gửi phản hồi</span>
                    </button>
                    <button
                      @click="replyMap[rev.id] = ''"
                      class="px-4 py-2 border rounded-lg"
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
import {ref, computed, onMounted} from "vue";
import {reviewApi, outletApi} from "@/api";

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
  if (onlyUnreplied.value) params.onlyUnreplied = true;
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
      replyMap.value[r.id] = "";
      replyLoading.value[r.id] = false;
    });
  } catch (err) {
    console.error(err);
    error.value = "Không thể tải đánh giá";
  } finally {
    loading.value = false;
  }
};

const submitReply = async (reviewId) => {
  if (!replyMap.value[reviewId]?.trim()) return;
  replyLoading.value[reviewId] = true;
  try {
    await reviewApi.replyReview(reviewId, replyMap.value[reviewId].trim());
    // reload reviews to show reply
    await loadReviews();
  } catch (err) {
    console.error(err);
    error.value = "Gửi phản hồi thất bại";
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

const refresh = () => loadReviews();

onMounted(async () => {
  await loadOutlets();
  await loadReviews();
});
</script>
