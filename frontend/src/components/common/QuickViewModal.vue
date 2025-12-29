<template>
  <Modal
    :show="show"
    @close="$emit('close')"
    title="Thông tin nhanh"
    size="lg"
  >
    <div v-if="outlet" class="space-y-4">
      <!-- Image -->
      <div class="relative h-48 bg-gray-200 dark:bg-gray-800 rounded-lg overflow-hidden">
        <ImageDisplay
          :image-url="getOutletImageUrl(outlet)"
          :alt="outlet.name"
          container-class="w-full h-full"
          image-class="w-full h-full object-cover"
        />
      </div>

      <!-- Basic Info -->
      <div>
        <h3 class="text-2xl font-black text-text-light dark:text-text-dark mb-3">
          {{ outlet.name }}
        </h3>
        <div class="flex items-center gap-4 text-sm mb-4">
          <div class="flex items-center gap-2 bg-yellow-50 dark:bg-yellow-900/20 px-3 py-1.5 rounded-lg">
            <span class="material-symbols-outlined text-yellow-500 text-base fill">star</span>
            <span class="font-bold text-text-light dark:text-text-dark">{{ getRating(outlet) }}</span>
            <span class="text-subtext-light dark:text-subtext-dark">({{ outlet.totalReviews || 0 }})</span>
          </div>
          <div class="flex items-center gap-2 text-subtext-light dark:text-subtext-dark">
            <span class="material-symbols-outlined text-base text-primary">location_on</span>
            <span class="font-medium">{{ outlet.districtName || outlet.district?.name || 'TPHCM' }}</span>
          </div>
          <div v-if="outlet.outletCategory || outlet.outletTypeName" class="flex items-center gap-2 text-subtext-light dark:text-subtext-dark">
            <span class="material-symbols-outlined text-base text-primary">restaurant</span>
            <span class="font-medium">{{ outlet.outletCategory?.name || outlet.outletTypeName }}</span>
          </div>
        </div>
        <p v-if="outlet.description" class="text-sm text-subtext-light dark:text-subtext-dark line-clamp-3 mb-4">
          {{ outlet.description }}
        </p>
      </div>

      <!-- Quick Stats -->
      <div class="grid grid-cols-3 gap-4">
        <div class="text-center p-4 bg-gradient-to-br from-primary/10 to-primary/5 dark:from-primary/20 dark:to-primary/10 rounded-xl border border-primary/20">
          <div class="text-xl font-black text-primary mb-1">
            {{ getDisplayPrice(outlet) }}
          </div>
          <div class="text-xs text-subtext-light dark:text-subtext-dark font-medium">Giá trung bình</div>
        </div>
        <div class="text-center p-4 bg-gradient-to-br from-primary/10 to-primary/5 dark:from-primary/20 dark:to-primary/10 rounded-xl border border-primary/20">
          <div class="text-xl font-black text-primary mb-1">
            {{ outlet.capacity || 'N/A' }}
          </div>
          <div class="text-xs text-subtext-light dark:text-subtext-dark font-medium">Sức chứa</div>
        </div>
        <div class="text-center p-4 bg-gradient-to-br from-primary/10 to-primary/5 dark:from-primary/20 dark:to-primary/10 rounded-xl border border-primary/20">
          <div class="text-xl font-black text-primary mb-1">
            {{ outlet.distanceText || 'N/A' }}
          </div>
          <div class="text-xs text-subtext-light dark:text-subtext-dark font-medium">Khoảng cách</div>
        </div>
      </div>

      <!-- Features -->
      <div v-if="outlet.features && outlet.features.length > 0">
        <h4 class="text-sm font-semibold text-text-light dark:text-text-dark mb-3">Tiện ích</h4>
        <div class="flex flex-wrap gap-2">
          <Badge
            v-for="feature in outlet.features.slice(0, 8)"
            :key="feature.id"
            variant="secondary"
            size="sm"
            class="hover:bg-primary/20 transition-colors"
          >
            {{ feature.name }}
          </Badge>
          <Badge
            v-if="outlet.features.length > 8"
            variant="outline"
            size="sm"
            class="hover:bg-primary/10 transition-colors"
          >
            +{{ outlet.features.length - 8 }} nữa
          </Badge>
        </div>
      </div>

      <!-- Actions -->
      <div class="flex gap-3 pt-4 border-t border-border-light dark:border-border-dark">
        <button
          @click="viewDetail"
          class="flex-1 px-6 py-3 bg-gradient-to-r from-primary to-primary/80 text-white rounded-xl font-bold hover:shadow-lg hover:-translate-y-0.5 transition-all duration-300 active:scale-98"
        >
          Xem chi tiết
        </button>
        <button
          @click="bookNow"
          class="flex-1 px-6 py-3 border-2 border-primary text-primary rounded-xl font-bold hover:bg-primary/10 transition-all duration-300 hover:shadow-lg hover:-translate-y-0.5 active:scale-98"
        >
          Đặt bàn ngay
        </button>
        <button
          @click="addToComparison"
          class="px-4 py-3 border-2 border-border-light dark:border-border-dark rounded-xl hover:bg-gray-100 dark:hover:bg-gray-800 transition-all duration-300 hover:shadow-lg hover:-translate-y-0.5 active:scale-98 disabled:opacity-50 disabled:cursor-not-allowed"
          :disabled="comparisonFull"
          :title="comparisonFull ? 'Đã đạt tối đa 3 quán để so sánh' : 'Thêm vào so sánh'"
        >
          <span class="material-symbols-outlined">compare_arrows</span>
        </button>
      </div>
    </div>
  </Modal>
</template>

<script setup>
import { computed } from 'vue';
import Modal from './Modal.vue';
import ImageDisplay from './ImageDisplay.vue';
import Badge from './Badge.vue';

const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  outlet: {
    type: Object,
    default: null
  },
  comparisonCount: {
    type: Number,
    default: 0
  }
});

const emit = defineEmits(['close', 'view', 'book', 'compare']);

const comparisonFull = computed(() => props.comparisonCount >= 3);

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

const viewDetail = () => {
  emit('view', props.outlet?.id);
};

const bookNow = () => {
  emit('book', props.outlet?.id);
};

const addToComparison = () => {
  if (!comparisonFull.value) {
    emit('compare', props.outlet);
  }
};

const handleClose = () => {
  emit('close');
  // Restore body scroll
  document.body.style.overflow = '';
};
</script>


