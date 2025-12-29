<template>
  <div class="flex items-center gap-1" :class="containerClass">
    <span
      v-for="i in 5"
      :key="i"
      class="material-symbols-outlined"
      :class="[
        i <= Math.round(rating) ? 'fill text-yellow-500' : 'text-gray-300 dark:text-gray-600',
        sizeClass
      ]"
    >
      star
    </span>
    <span
      v-if="showValue"
      class="ml-1 text-sm font-semibold text-text-light dark:text-text-dark"
    >
      {{ formattedRating }}
    </span>
    <span
      v-if="showCount && count !== null && count !== undefined"
      class="ml-1 text-sm text-subtext-light dark:text-subtext-dark"
    >
      ({{ formatCount(count) }})
    </span>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  rating: {
    type: [Number, String],
    default: 0,
  },
  count: {
    type: Number,
    default: null,
  },
  showValue: {
    type: Boolean,
    default: true,
  },
  showCount: {
    type: Boolean,
    default: true,
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg"].includes(value),
  },
  containerClass: {
    type: String,
    default: "",
  },
});

const formattedRating = computed(() => {
  const num = Number(props.rating);
  if (isNaN(num) || num === 0) return "0.0";
  return num.toFixed(1);
});

const sizeClass = computed(() => {
  const sizes = {
    sm: "text-base",
    md: "text-lg",
    lg: "text-2xl",
  };
  return sizes[props.size];
});

const formatCount = (count) => {
  if (count >= 1000) {
    return (count / 1000).toFixed(1) + "K";
  }
  return count.toString();
};
</script>


