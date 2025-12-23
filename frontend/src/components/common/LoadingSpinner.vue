<template>
  <div class="flex items-center justify-center" :class="sizeClass">
    <div
      class="animate-spin rounded-full border-b-2"
      :class="[colorClass, sizeClass]"
    ></div>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg", "xl"].includes(value),
  },
  color: {
    type: String,
    default: "primary",
  },
});

const sizeClass = computed(() => {
  const sizes = {
    sm: "h-4 w-4",
    md: "h-8 w-8",
    lg: "h-12 w-12",
    xl: "h-16 w-16",
  };
  return sizes[props.size];
});

const colorClass = computed(() => {
  const colors = {
    primary: "border-primary",
    white: "border-white",
    gray: "border-gray-500",
  };
  return colors[props.color] || colors.primary;
});
</script>
