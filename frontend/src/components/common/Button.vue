<template>
  <button
    :type="type"
    :disabled="disabled || loading"
    :class="[
      baseClasses,
      sizeClasses,
      variantClasses,
      disabled || loading ? 'opacity-50 cursor-not-allowed' : '',
      customClass
    ]"
    @click="handleClick"
  >
    <span v-if="loading" class="mr-2">
      <LoadingSpinner size="sm" :color="loadingColor" />
    </span>
    <span v-if="icon && !iconRight" class="mr-2">
      <span class="material-symbols-outlined" :style="{fontSize: iconSize}">
        {{ icon }}
      </span>
    </span>
    <slot></slot>
    <span v-if="icon && iconRight" class="ml-2">
      <span class="material-symbols-outlined" :style="{fontSize: iconSize}">
        {{ icon }}
      </span>
    </span>
  </button>
</template>

<script setup>
import {computed} from "vue";
import LoadingSpinner from "./LoadingSpinner.vue";

const props = defineProps({
  type: {
    type: String,
    default: "button",
  },
  variant: {
    type: String,
    default: "primary",
    validator: (value) => ["primary", "secondary", "outline", "ghost", "danger"].includes(value),
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg", "xl"].includes(value),
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  loading: {
    type: Boolean,
    default: false,
  },
  icon: {
    type: String,
    default: null,
  },
  iconRight: {
    type: Boolean,
    default: false,
  },
  iconSize: {
    type: String,
    default: "20px",
  },
  customClass: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["click"]);

const baseClasses = computed(() => {
  return "inline-flex items-center justify-center font-semibold transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2";
});

const sizeClasses = computed(() => {
  const sizes = {
    sm: "px-3 py-1.5 text-sm",
    md: "px-4 py-2 text-base",
    lg: "px-6 py-3 text-lg",
    xl: "px-8 py-4 text-xl",
  };
  return sizes[props.size];
});

const variantClasses = computed(() => {
  const variants = {
    primary: "relative overflow-hidden bg-gradient-to-r from-primary via-primary to-primary/90 text-white focus:ring-primary rounded-[14px] shadow-[0_4px_14px_rgba(245,110,61,0.3)] hover:shadow-[0_6px_20px_rgba(245,110,61,0.4)] hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 before:absolute before:inset-0 before:bg-gradient-to-r before:from-transparent before:via-white/20 before:to-transparent before:translate-x-[-100%] hover:before:translate-x-[100%] before:transition-transform before:duration-500",
    secondary: "bg-[rgba(142,142,147,0.12)] dark:bg-[rgba(142,142,147,0.2)] backdrop-blur-[20px] text-text-light dark:text-text-dark border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] rounded-[14px] hover:bg-[rgba(142,142,147,0.18)] dark:hover:bg-[rgba(142,142,147,0.25)] focus:ring-gray-500 hover:scale-[1.02] active:scale-[0.98] transition-all duration-200",
    outline: "border-[1.5px] border-primary text-primary hover:bg-primary/10 dark:hover:bg-primary/20 focus:ring-primary rounded-[14px] hover:scale-[1.02] active:scale-[0.98] transition-all duration-200",
    ghost: "text-primary hover:bg-primary/10 dark:hover:bg-primary/20 focus:ring-primary rounded-[14px] hover:scale-[1.02] active:scale-[0.98] transition-all duration-200",
    danger: "bg-gradient-to-r from-red-600 to-red-700 text-white hover:from-red-700 hover:to-red-800 focus:ring-red-500 rounded-[14px] shadow-[0_4px_14px_rgba(239,68,68,0.3)] hover:shadow-[0_6px_20px_rgba(239,68,68,0.4)] hover:scale-[1.02] active:scale-[0.98] transition-all duration-200",
  };
  return variants[props.variant];
});

const loadingColor = computed(() => {
  if (props.variant === "primary" || props.variant === "danger") {
    return "white";
  }
  return "primary";
});

const handleClick = (event) => {
  if (!props.disabled && !props.loading) {
    emit("click", event);
  }
};
</script>

