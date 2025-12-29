<template>
  <span
    :class="[
      baseClasses,
      sizeClasses,
      variantClasses,
      customClass
    ]"
  >
    <span v-if="icon" class="material-symbols-outlined mr-1" :style="{fontSize: iconSize}">
      {{ icon }}
    </span>
    <slot></slot>
  </span>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  variant: {
    type: String,
    default: "default",
    validator: (value) => ["default", "primary", "success", "warning", "danger", "info"].includes(value),
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg"].includes(value),
  },
  icon: {
    type: String,
    default: null,
  },
  iconSize: {
    type: String,
    default: "16px",
  },
  customClass: {
    type: String,
    default: "",
  },
});

const baseClasses = computed(() => {
  return "inline-flex items-center justify-center font-medium rounded-full";
});

const sizeClasses = computed(() => {
  const sizes = {
    sm: "px-2 py-0.5 text-xs",
    md: "px-3 py-1 text-sm",
    lg: "px-4 py-1.5 text-base",
  };
  return sizes[props.size];
});

const variantClasses = computed(() => {
  const variants = {
    default: "bg-gray-100 dark:bg-gray-700 text-gray-800 dark:text-gray-200",
    primary: "bg-primary/10 text-primary dark:bg-primary/20 dark:text-primary",
    success: "bg-green-100 dark:bg-green-900/30 text-green-800 dark:text-green-300",
    warning: "bg-yellow-100 dark:bg-yellow-900/30 text-yellow-800 dark:text-yellow-300",
    danger: "bg-red-100 dark:bg-red-900/30 text-red-800 dark:text-red-300",
    info: "bg-blue-100 dark:bg-blue-900/30 text-blue-800 dark:text-blue-300",
  };
  return variants[props.variant];
});
</script>


