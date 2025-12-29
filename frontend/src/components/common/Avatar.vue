<template>
  <div
    :class="[
      'rounded-full flex items-center justify-center font-semibold text-white overflow-hidden',
      sizeClass,
      customClass
    ]"
    :style="avatarStyle"
  >
    <img
      v-if="src"
      :src="src"
      :alt="alt"
      class="w-full h-full object-cover"
      @error="handleImageError"
    />
    <span v-else-if="name" class="select-none">
      {{ initials }}
    </span>
    <span v-else class="material-symbols-outlined">
      account_circle
    </span>
  </div>
</template>

<script setup>
import {computed, ref} from "vue";

const props = defineProps({
  src: {
    type: String,
    default: null,
  },
  name: {
    type: String,
    default: null,
  },
  alt: {
    type: String,
    default: "Avatar",
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg", "xl"].includes(value),
  },
  color: {
    type: String,
    default: "primary",
  },
  customClass: {
    type: String,
    default: "",
  },
});

const imageError = ref(false);

const sizeClass = computed(() => {
  const sizes = {
    sm: "w-8 h-8 text-xs",
    md: "w-10 h-10 text-sm",
    lg: "w-12 h-12 text-base",
    xl: "w-16 h-16 text-lg",
  };
  return sizes[props.size];
});

const initials = computed(() => {
  if (!props.name) return "?";
  const parts = props.name.trim().split(" ");
  if (parts.length >= 2) {
    return (parts[0][0] + parts[parts.length - 1][0]).toUpperCase();
  }
  return props.name.substring(0, 2).toUpperCase();
});

const avatarStyle = computed(() => {
  if (props.src && !imageError.value) return {};
  
  const colors = {
    primary: "bg-primary",
    success: "bg-green-500",
    warning: "bg-yellow-500",
    danger: "bg-red-500",
    info: "bg-blue-500",
    gray: "bg-gray-500",
  };
  
  return {
    backgroundColor: colors[props.color] || colors.primary,
  };
});

const handleImageError = () => {
  imageError.value = true;
};
</script>


