<template>
  <div class="w-full space-y-6">
    <!-- Location Filter -->
    <div v-if="showLocation" class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">location_on</span>
        Vị trí
      </label>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
        <Input
          v-model.number="localFilters.latitude"
          type="number"
          step="0.000001"
          placeholder="Vĩ độ"
          class="w-full"
        />
        <Input
          v-model.number="localFilters.longitude"
          type="number"
          step="0.000001"
          placeholder="Kinh độ"
          class="w-full"
        />
        <Input
          v-model.number="localFilters.radiusKm"
          type="number"
          step="0.1"
          min="0"
          placeholder="Bán kính (km)"
          class="w-full"
        />
      </div>
      <button
        @click="getCurrentLocation"
        class="text-sm text-primary hover:underline flex items-center gap-1"
      >
        <span class="material-symbols-outlined text-base">my_location</span>
        Lấy vị trí hiện tại
      </button>
    </div>

    <!-- Price Range Filter -->
    <div class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">payments</span>
        Mức giá
      </label>
      <div class="flex flex-wrap gap-2">
        <button
          v-for="range in priceRanges"
          :key="range.value"
          @click="localFilters.priceRange = range.value"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-all',
            localFilters.priceRange === range.value
              ? 'bg-primary text-white shadow-md'
              : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
          ]"
        >
          {{ range.label }}
        </button>
      </div>
      <div v-if="showCustomPrice" class="grid grid-cols-2 gap-3 mt-3">
        <Input
          v-model.number="localFilters.minPrice"
          type="number"
          placeholder="Giá tối thiểu"
          class="w-full"
        />
        <Input
          v-model.number="localFilters.maxPrice"
          type="number"
          placeholder="Giá tối đa"
          class="w-full"
        />
      </div>
    </div>

    <!-- Rating Filter -->
    <div class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">star</span>
        Đánh giá tối thiểu
      </label>
      <div class="flex items-center gap-3">
        <input
          v-model.number="localFilters.minRating"
          type="range"
          min="0"
          max="5"
          step="0.5"
          class="flex-1 h-2 bg-gray-200 dark:bg-gray-700 rounded-lg appearance-none cursor-pointer accent-primary"
        />
        <span class="text-lg font-bold text-primary min-w-[3rem] text-center">
          {{ localFilters.minRating || 0 }} ⭐
        </span>
      </div>
    </div>

    <!-- Features Filter -->
    <div v-if="features && features.length > 0" class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">check_circle</span>
        Tiện ích
      </label>
      <div class="flex flex-wrap gap-2">
        <button
          v-for="feature in features"
          :key="feature.id"
          @click="toggleFeature(feature.id)"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-all flex items-center gap-2',
            localFilters.featureIds?.includes(feature.id)
              ? 'bg-primary text-white shadow-md'
              : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
          ]"
        >
          <span class="material-symbols-outlined text-base">
            {{ localFilters.featureIds?.includes(feature.id) ? 'check_circle' : 'circle' }}
          </span>
          {{ feature.name }}
        </button>
      </div>
    </div>

    <!-- Search Options -->
    <div class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">tune</span>
        Tùy chọn tìm kiếm
      </label>
      <div class="space-y-2">
        <label class="flex items-center gap-2 cursor-pointer">
          <input
            v-model="localFilters.fuzzySearch"
            type="checkbox"
            class="w-4 h-4 text-primary rounded focus:ring-primary"
          />
          <span class="text-sm text-text-light dark:text-text-dark">Tìm kiếm mờ (fuzzy matching)</span>
        </label>
        <label class="flex items-center gap-2 cursor-pointer">
          <input
            v-model="localFilters.exactMatch"
            type="checkbox"
            class="w-4 h-4 text-primary rounded focus:ring-primary"
          />
          <span class="text-sm text-text-light dark:text-text-dark">Tìm kiếm chính xác</span>
        </label>
        <label class="flex items-center gap-2 cursor-pointer">
          <input
            v-model="localFilters.sortByDistance"
            type="checkbox"
            class="w-4 h-4 text-primary rounded focus:ring-primary"
          />
          <span class="text-sm text-text-light dark:text-text-dark">Sắp xếp theo khoảng cách</span>
        </label>
      </div>
    </div>

    <!-- Sort Options -->
    <div class="space-y-3">
      <label class="block text-sm font-semibold text-text-light dark:text-text-dark">
        <span class="material-symbols-outlined align-middle mr-2">sort</span>
        Sắp xếp
      </label>
      <div class="grid grid-cols-2 gap-2">
        <select
          v-model="localFilters.sortBy"
          class="px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
        >
          <option value="relevance">Độ liên quan</option>
          <option value="rating">Đánh giá</option>
          <option value="distance">Khoảng cách</option>
          <option value="reviews">Số đánh giá</option>
          <option value="price">Giá</option>
        </select>
        <select
          v-model="localFilters.sortDirection"
          class="px-3 py-2 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark text-sm focus:ring-2 focus:ring-primary/50 focus:border-primary"
        >
          <option value="desc">Giảm dần</option>
          <option value="asc">Tăng dần</option>
        </select>
      </div>
    </div>

    <!-- Action Buttons -->
    <div class="flex gap-3 pt-4 border-t border-border-light dark:border-border-dark">
      <Button
        @click="applyFilters"
        variant="primary"
        class="flex-1"
      >
        Áp dụng bộ lọc
      </Button>
      <Button
        @click="resetFilters"
        variant="outline"
      >
        Đặt lại
      </Button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import Button from './Button.vue';
import Input from './Input.vue';

const props = defineProps({
  filters: {
    type: Object,
    default: () => ({})
  },
  features: {
    type: Array,
    default: () => []
  },
  showLocation: {
    type: Boolean,
    default: true
  },
  showCustomPrice: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:filters', 'apply', 'reset']);

const priceRanges = [
  { label: 'Tất cả', value: null },
  { label: 'Dưới 100k', value: 'Dưới 100k' },
  { label: '100k - 200k', value: '100k - 200k' },
  { label: '200k - 500k', value: '200k - 500k' },
  { label: 'Trên 500k', value: 'Trên 500k' }
];

const localFilters = ref({
  latitude: props.filters.latitude || null,
  longitude: props.filters.longitude || null,
  radiusKm: props.filters.radiusKm || null,
  priceRange: props.filters.priceRange || null,
  minPrice: props.filters.minPrice || null,
  maxPrice: props.filters.maxPrice || null,
  minRating: props.filters.minRating || null,
  featureIds: props.filters.featureIds || [],
  fuzzySearch: props.filters.fuzzySearch || false,
  exactMatch: props.filters.exactMatch || false,
  sortByDistance: props.filters.sortByDistance || false,
  sortBy: props.filters.sortBy || 'relevance',
  sortDirection: props.filters.sortDirection || 'desc'
});

watch(() => props.filters, (newFilters) => {
  localFilters.value = { ...localFilters.value, ...newFilters };
}, { deep: true });

const toggleFeature = (featureId) => {
  if (!localFilters.value.featureIds) {
    localFilters.value.featureIds = [];
  }
  const index = localFilters.value.featureIds.indexOf(featureId);
  if (index > -1) {
    localFilters.value.featureIds.splice(index, 1);
  } else {
    localFilters.value.featureIds.push(featureId);
  }
};

const getCurrentLocation = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        localFilters.value.latitude = position.coords.latitude;
        localFilters.value.longitude = position.coords.longitude;
        emit('update:filters', localFilters.value);
      },
      (error) => {
        console.error('Error getting location:', error);
        alert('Không thể lấy vị trí. Vui lòng nhập thủ công.');
      }
    );
  } else {
    alert('Trình duyệt không hỗ trợ lấy vị trí.');
  }
};

const applyFilters = () => {
  emit('update:filters', localFilters.value);
  emit('apply', localFilters.value);
};

const resetFilters = () => {
  localFilters.value = {
    latitude: null,
    longitude: null,
    radiusKm: null,
    priceRange: null,
    minPrice: null,
    maxPrice: null,
    minRating: null,
    featureIds: [],
    fuzzySearch: false,
    exactMatch: false,
    sortByDistance: false,
    sortBy: 'relevance',
    sortDirection: 'desc'
  };
  emit('update:filters', localFilters.value);
  emit('reset');
};
</script>


