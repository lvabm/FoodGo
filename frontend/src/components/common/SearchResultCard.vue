<template>
  <div
    :class="[
      'group relative card-premium overflow-hidden transition-all duration-500',
      showRelevanceScore ? 'hover:scale-[1.03] hover:shadow-premium-lg' : 'hover:scale-[1.02] hover:shadow-premium-lg'
    ]"
    @click="$emit('click', outlet)"
  >
    <!-- Image Section with Premium Overlay -->
    <div class="relative h-56 bg-gradient-to-br from-gray-100 to-gray-200 dark:from-gray-800 dark:to-gray-900 overflow-hidden rounded-t-2xl">
      <ImageDisplay
        :image-url="getOutletImageUrl(outlet)"
        :alt="outlet.name"
        :lazy="true"
        placeholder-icon="restaurant"
        :icon-size="'64px'"
        container-class="w-full h-full"
        image-class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
      />
      
      <!-- Gradient Overlay with Animation -->
      <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/20 to-black/0 opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
      
      <!-- Shine Effect on Hover -->
      <div class="absolute inset-0 bg-gradient-to-r from-transparent via-white/20 to-transparent -translate-x-full group-hover:translate-x-full transition-transform duration-1000"></div>
      
      <!-- Relevance Score Badge -->
      <RelevanceBadge
        v-if="showRelevanceScore && relevanceScore !== null"
        :score="relevanceScore"
        class="absolute top-4 right-4 z-10"
      />

      <!-- Distance Badge with Premium Style -->
      <div
        v-if="distanceText"
        class="absolute top-4 left-4 glass-premium text-white px-4 py-2 rounded-full text-xs font-bold backdrop-blur-md flex items-center gap-2 shadow-lg z-10"
      >
        <span class="material-symbols-outlined text-sm">near_me</span>
        <span>{{ distanceText }}</span>
      </div>

      <!-- Quick Actions on Hover -->
      <div class="absolute bottom-4 left-1/2 -translate-x-1/2 flex gap-2 opacity-0 group-hover:opacity-100 transition-all duration-300 transform translate-y-4 group-hover:translate-y-0 z-10">
        <button
          @click.stop="$emit('quick-view', outlet)"
          class="glass-premium p-3 rounded-full text-white hover:bg-white/30 transition-all backdrop-blur-md shadow-lg hover:scale-110"
          title="Xem nhanh"
        >
          <span class="material-symbols-outlined text-lg">visibility</span>
        </button>
        <button
          @click.stop="$emit('compare', outlet)"
          :disabled="comparisonDisabled"
          class="glass-premium p-3 rounded-full text-white hover:bg-white/30 transition-all backdrop-blur-md shadow-lg hover:scale-110 disabled:opacity-50 disabled:cursor-not-allowed"
          title="So sánh"
        >
          <span class="material-symbols-outlined text-lg">compare_arrows</span>
        </button>
      </div>
    </div>

    <!-- Content Section with Premium Padding -->
    <div class="p-6">
      <!-- Title with Highlighting -->
      <h3 class="text-xl font-black text-text-light dark:text-text-dark mb-3 line-clamp-1 group-hover:text-primary transition-colors">
        <span v-html="highlightText(outlet.name, highlights?.name || [])"></span>
      </h3>

      <!-- Rating and Reviews with Premium Style -->
      <div class="flex items-center gap-4 mb-4">
        <div class="flex items-center gap-2 bg-yellow-50 dark:bg-yellow-900/20 px-3 py-1.5 rounded-lg">
          <span class="material-symbols-outlined text-yellow-500 text-lg fill">star</span>
          <span class="font-bold text-base text-text-light dark:text-text-dark">{{ getRating(outlet) }}</span>
        </div>
        <span class="text-subtext-light dark:text-subtext-dark text-sm font-medium">
          ({{ outlet.totalReviews || 0 }} đánh giá)
        </span>
        <Badge
          v-if="matchedFields && matchedFields.length > 0"
          variant="primary"
          size="sm"
          class="ml-auto"
        >
          {{ matchedFields.length }} trường khớp
        </Badge>
      </div>

      <!-- Description with Highlighting -->
      <p
        v-if="outlet.description"
        class="text-sm text-subtext-light dark:text-subtext-dark mb-4 line-clamp-2 leading-relaxed"
      >
        <span v-html="highlightText(outlet.description, highlights?.description || [])"></span>
      </p>

      <!-- Category and Location with Premium Icons -->
      <div class="space-y-3 mb-4">
        <div class="flex items-center gap-2 text-sm">
          <div class="p-1.5 bg-primary/10 rounded-lg">
            <span class="material-symbols-outlined text-base text-primary">restaurant</span>
          </div>
          <span class="font-semibold text-text-light dark:text-text-dark">
            {{ outlet.outletCategory?.name || outlet.outletTypeName || "Nhà hàng" }}
          </span>
        </div>
        <div class="flex items-center gap-2 text-sm">
          <div class="p-1.5 bg-primary/10 rounded-lg">
            <span class="material-symbols-outlined text-base text-primary">location_on</span>
          </div>
          <span class="line-clamp-1 text-text-light dark:text-text-dark font-medium">
            <span v-html="highlightText(outlet.districtName || outlet.district?.name || 'TPHCM', highlights?.address || [])"></span>
          </span>
        </div>
        <div class="flex items-center gap-2 text-sm">
          <div class="p-1.5 bg-primary/10 rounded-lg">
            <span class="material-symbols-outlined text-base text-primary">payments</span>
          </div>
          <span class="font-bold text-lg text-gradient-primary">
            {{ getDisplayPrice(outlet) }}
          </span>
          <span class="text-subtext-light dark:text-subtext-dark text-sm">/ người</span>
        </div>
      </div>

      <!-- Features Tags with Premium Style -->
      <div
        v-if="outlet.features && outlet.features.length > 0"
        class="flex flex-wrap gap-2 pt-4 border-t border-border-light dark:border-border-dark"
      >
        <Badge
          v-for="feature in outlet.features.slice(0, 3)"
          :key="feature.id"
          variant="secondary"
          size="sm"
          class="hover:bg-primary/20 transition-colors"
        >
          {{ feature.name }}
        </Badge>
        <Badge
          v-if="outlet.features.length > 3"
          variant="outline"
          size="sm"
          class="hover:bg-primary/10 transition-colors"
        >
          +{{ outlet.features.length - 3 }} nữa
        </Badge>
      </div>
    </div>
  </div>
</template>

<script setup>
import ImageDisplay from './ImageDisplay.vue';
import Badge from './Badge.vue';
import RelevanceBadge from './RelevanceBadge.vue';

const props = defineProps({
  outlet: {
    type: Object,
    required: true
  },
  relevanceScore: {
    type: Number,
    default: null
  },
  distanceKm: {
    type: Number,
    default: null
  },
  distanceText: {
    type: String,
    default: null
  },
  matchedFields: {
    type: Array,
    default: () => []
  },
  highlights: {
    type: Object,
    default: () => ({})
  },
  showRelevanceScore: {
    type: Boolean,
    default: true
  },
  comparisonDisabled: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['click', 'quick-view', 'compare']);

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
  const prices = (o?.menuItems || []).map((i) => Number(i?.price || i?.priceAmount || 0)).filter((p) => p > 0);
  if (prices.length > 0) {
    const avg = prices.reduce((s, v) => s + v, 0) / prices.length;
    return formatPrice(avg);
  }
  return "N/A";
};

const formatPrice = (price) => {
  if (!price) return "N/A";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const highlightText = (text, terms) => {
  if (!text || !terms || terms.length === 0) {
    return escapeHtml(text || '');
  }
  
  let highlighted = escapeHtml(text);
  terms.forEach(term => {
    const regex = new RegExp(`(${escapeRegex(term)})`, 'gi');
    highlighted = highlighted.replace(regex, '<mark class="bg-yellow-200 dark:bg-yellow-900/50 px-1 rounded">$1</mark>');
  });
  
  return highlighted;
};

const escapeHtml = (text) => {
  if (!text) return '';
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
};

const escapeRegex = (text) => {
  return text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
};
</script>

<style scoped>
mark {
  background-color: rgba(255, 235, 59, 0.3);
  padding: 0 2px;
  border-radius: 2px;
}
</style>

