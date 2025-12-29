<template>
  <div
    v-if="selectedOutlets.length > 0"
    class="fixed bottom-0 left-0 right-0 glass-premium border-t border-border-light dark:border-border-dark shadow-2xl z-50 animate-slide-up backdrop-blur-xl"
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-5">
      <div class="flex items-center justify-between mb-5">
        <h3 class="text-xl font-black text-text-light dark:text-text-dark flex items-center gap-3">
          <span class="material-symbols-outlined text-primary text-2xl">compare_arrows</span>
          <span>So sánh quán</span>
          <span class="px-3 py-1 bg-primary/20 text-primary rounded-full text-sm font-bold">
            {{ selectedOutlets.length }}/3
          </span>
        </h3>
        <div class="flex items-center gap-3">
          <button
            @click="clearComparison"
            class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary font-medium transition-colors"
          >
            Xóa tất cả
          </button>
          <button
            @click="$emit('close')"
            class="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
          >
            <span class="material-symbols-outlined text-subtext-light dark:text-subtext-dark">close</span>
          </button>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 max-h-[32rem] overflow-y-auto custom-scrollbar">
        <div
          v-for="outlet in selectedOutlets"
          :key="outlet.id"
          class="card-premium rounded-2xl p-5 border border-border-light dark:border-border-dark hover:shadow-lg transition-all duration-300"
        >
          <!-- Image -->
          <div class="relative h-32 mb-4 rounded-xl overflow-hidden bg-gradient-to-br from-gray-100 to-gray-200 dark:from-gray-800 dark:to-gray-900">
            <ImageDisplay
              :image-url="getOutletImageUrl(outlet)"
              :alt="outlet.name"
              container-class="w-full h-full"
              image-class="w-full h-full object-cover"
            />
          </div>

          <div class="flex items-start justify-between mb-3">
            <h4 class="font-bold text-lg text-text-light dark:text-text-dark line-clamp-2 flex-1">
              {{ outlet.name }}
            </h4>
            <button
              @click="removeFromComparison(outlet.id)"
              class="p-1.5 rounded-lg hover:bg-red-100 dark:hover:bg-red-900/20 text-subtext-light dark:text-subtext-dark hover:text-red-500 transition-colors ml-2 flex-shrink-0"
              title="Xóa khỏi so sánh"
            >
              <span class="material-symbols-outlined text-sm">close</span>
            </button>
          </div>

          <div class="space-y-3 text-sm mb-4">
            <div class="flex items-center gap-2 bg-yellow-50 dark:bg-yellow-900/20 px-3 py-1.5 rounded-lg">
              <span class="material-symbols-outlined text-yellow-500 text-base fill">star</span>
              <span class="font-bold text-text-light dark:text-text-dark">{{ getRating(outlet) }}</span>
              <span class="text-subtext-light dark:text-subtext-dark">
                ({{ outlet.totalReviews || 0 }} đánh giá)
              </span>
            </div>
            <div class="flex items-center gap-2 text-subtext-light dark:text-subtext-dark">
              <span class="material-symbols-outlined text-base text-primary">location_on</span>
              <span class="line-clamp-1 font-medium">{{ outlet.districtName || outlet.district?.name || 'TPHCM' }}</span>
            </div>
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-base text-primary">payments</span>
              <span class="font-bold text-lg text-primary">{{ getDisplayPrice(outlet) }}</span>
              <span class="text-subtext-light dark:text-subtext-dark text-xs">/ người</span>
            </div>
            <div v-if="outlet.distanceText" class="flex items-center gap-2 text-subtext-light dark:text-subtext-dark">
              <span class="material-symbols-outlined text-base text-primary">near_me</span>
              <span class="font-medium">{{ outlet.distanceText }}</span>
            </div>
            <div v-if="outlet.capacity" class="flex items-center gap-2 text-subtext-light dark:text-subtext-dark">
              <span class="material-symbols-outlined text-base text-primary">groups</span>
              <span class="font-medium">Sức chứa: {{ outlet.capacity }} người</span>
            </div>
          </div>

          <!-- Features (if any) -->
          <div v-if="outlet.features && outlet.features.length > 0" class="mb-4">
            <div class="flex flex-wrap gap-1.5">
              <Badge
                v-for="feature in outlet.features.slice(0, 3)"
                :key="feature.id"
                variant="secondary"
                size="sm"
              >
                {{ feature.name }}
              </Badge>
              <Badge
                v-if="outlet.features.length > 3"
                variant="outline"
                size="sm"
              >
                +{{ outlet.features.length - 3 }}
              </Badge>
            </div>
          </div>

          <div class="flex gap-2 pt-4 border-t border-border-light dark:border-border-dark">
            <button
              @click="viewOutlet(outlet.id)"
              class="flex-1 px-4 py-2.5 bg-gradient-to-r from-primary to-primary/80 text-white rounded-xl text-sm font-bold hover:shadow-lg hover:-translate-y-0.5 transition-all duration-300 active:scale-98"
            >
              Xem chi tiết
            </button>
            <button
              @click="bookOutlet(outlet.id)"
              class="px-4 py-2.5 border-2 border-primary text-primary rounded-xl text-sm font-bold hover:bg-primary/10 transition-all duration-300 hover:shadow-lg hover:-translate-y-0.5 active:scale-98"
            >
              Đặt bàn
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import ImageDisplay from './ImageDisplay.vue';
import Badge from './Badge.vue';

const props = defineProps({
  selectedOutlets: {
    type: Array,
    default: () => []
  }
});

const emit = defineEmits(['close', 'remove', 'clear', 'view', 'book']);

const getOutletImageUrl = (outlet) => {
  if (!outlet?.images || !Array.isArray(outlet.images) || outlet.images.length === 0) {
    return null;
  }
  return outlet.images[0] || null;
};

const getRating = (o) => {
  const r = o?.averageRating ?? o?.rating;
  if (r === undefined || r === null) return "N/A";
  const num = Number(r);
  if (Number.isNaN(num)) return "N/A";
  return num.toFixed(1);
};

const getDisplayPrice = (o) => {
  if (o?.priceRange) return o.priceRange;
  if (o?.averagePrice) return formatPrice(o.averagePrice);
  return "N/A";
};

const formatPrice = (price) => {
  if (!price) return "N/A";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const removeFromComparison = (outletId) => {
  emit('remove', outletId);
};

const clearComparison = () => {
  emit('clear');
};

const viewOutlet = (outletId) => {
  emit('view', outletId);
};

const bookOutlet = (outletId) => {
  emit('book', outletId);
};
</script>


