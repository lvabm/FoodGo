<template>
  <div class="w-full" :class="containerClass">
    <div
      v-if="label"
      class="flex items-center justify-between mb-2"
    >
      <span class="text-sm font-medium text-text-light dark:text-text-dark">
        {{ label }}
      </span>
      <span
        v-if="showValue"
        class="text-sm font-semibold text-text-light dark:text-text-dark"
      >
        {{ value }}%
      </span>
    </div>
    <div
      class="w-full bg-gray-200 dark:bg-gray-700 rounded-full overflow-hidden"
      :class="heightClass"
    >
      <div
        :class="[
          'h-full rounded-full transition-all duration-500 ease-out',
          colorClass
        ]"
        :style="{width: `${Math.min(100, Math.max(0, value))}%`}"
      >
        <div
          v-if="animated"
          class="h-full w-full shimmer"
        ></div>
      </div>
    </div>
    <p
      v-if="hint"
      class="mt-1 text-xs text-subtext-light dark:text-subtext-dark"
    >
      {{ hint }}
    </p>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  value: {
    type: Number,
    default: 0,
    validator: (value) => value >= 0 && value <= 100,
  },
  label: {
    type: String,
    default: null,
  },
  hint: {
    type: String,
    default: null,
  },
  showValue: {
    type: Boolean,
    default: true,
  },
  variant: {
    type: String,
    default: "primary",
    validator: (value) => ["primary", "success", "warning", "danger", "info"].includes(value),
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg"].includes(value),
  },
  animated: {
    type: Boolean,
    default: false,
  },
  containerClass: {
    type: String,
    default: "",
  },
});

const heightClass = computed(() => {
  const heights = {
    sm: "h-1",
    md: "h-2",
    lg: "h-3",
  };
  return heights[props.size];
});

const colorClass = computed(() => {
  const colors = {
    primary: "bg-primary",
    success: "bg-green-500",
    warning: "bg-yellow-500",
    danger: "bg-red-500",
    info: "bg-blue-500",
  };
  return colors[props.variant];
});
</script>


