<template>
  <div class="w-full">
    <h3 class="text-sm font-semibold text-text-light dark:text-text-dark mb-3 flex items-center gap-2">
      <span class="material-symbols-outlined text-base">bolt</span>
      Bộ lọc nhanh
    </h3>
    <div class="flex flex-wrap gap-2">
      <button
        v-for="preset in presets"
        :key="preset.id"
        @click="applyPreset(preset)"
        :class="[
          'px-4 py-2 rounded-full text-sm font-medium transition-all duration-200 flex items-center gap-2',
          activePreset === preset.id
            ? 'bg-primary text-white shadow-md scale-105'
            : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
        ]"
      >
        <span class="material-symbols-outlined text-base">{{ preset.icon }}</span>
        {{ preset.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    default: null
  }
});

const emit = defineEmits(['update:modelValue', 'apply']);

const activePreset = ref(props.modelValue);

const presets = [
  {
    id: 'open-now',
    label: 'Đang mở cửa',
    icon: 'schedule',
    filters: { isOpenNow: true }
  },
  {
    id: 'nearby',
    label: 'Gần tôi',
    icon: 'near_me',
    filters: { sortByDistance: true }
  },
  {
    id: 'highly-rated',
    label: 'Đánh giá cao',
    icon: 'star',
    filters: { minRating: 4.0, sortBy: 'rating', sortDirection: 'desc' }
  },
  {
    id: 'available',
    label: 'Còn chỗ',
    icon: 'event_available',
    filters: { hasAvailableCapacity: true }
  },
  {
    id: 'budget',
    label: 'Giá rẻ',
    icon: 'payments',
    filters: { priceRange: 'Dưới 100k', sortBy: 'price', sortDirection: 'asc' }
  },
  {
    id: 'popular',
    label: 'Phổ biến',
    icon: 'trending_up',
    filters: { sortBy: 'reviews', sortDirection: 'desc' }
  },
  {
    id: 'romantic',
    label: 'Lãng mạn',
    icon: 'favorite',
    filters: { minRating: 4.0, priceRange: '200k - 500k' }
  },
  {
    id: 'family',
    label: 'Gia đình',
    icon: 'family_restroom',
    filters: { hasParking: true, minRating: 4.0 }
  },
  {
    id: 'business',
    label: 'Công việc',
    icon: 'business_center',
    filters: { hasWifi: true, hasParking: true }
  },
  {
    id: 'date-night',
    label: 'Hẹn hò',
    icon: 'celebration',
    filters: { minRating: 4.5, priceRange: '200k - 500k', sortBy: 'rating', sortDirection: 'desc' }
  }
];

const applyPreset = (preset) => {
  if (activePreset.value === preset.id) {
    activePreset.value = null;
    emit('update:modelValue', null);
    emit('apply', {});
  } else {
    activePreset.value = preset.id;
    emit('update:modelValue', preset.id);
    emit('apply', preset.filters);
  }
};
</script>


