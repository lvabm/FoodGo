<template>
  <div
    :class="containerClass"
    class="relative overflow-hidden"
    :style="containerStyle"
  >
    <!-- Loading State -->
    <div
      v-if="isLoading"
      class="absolute inset-0 flex items-center justify-center bg-gray-100 dark:bg-gray-800"
    >
      <LoadingSpinner size="sm" />
    </div>

    <!-- Image -->
    <img
      v-if="imageUrl && !imageError"
      :src="processedImageUrl"
      :alt="alt"
      :class="imageClass"
      class="w-full h-full object-cover transition-opacity duration-300"
      :style="imageStyle"
      @load="onImageLoad"
      @error="onImageError"
      :loading="lazy ? 'lazy' : 'eager'"
    />

    <!-- Placeholder/Error State -->
    <div
      v-if="!imageUrl || imageError"
      :class="placeholderClass"
      class="w-full h-full flex items-center justify-center bg-gray-200 dark:bg-gray-700"
      :style="placeholderStyle"
    >
      <span
        v-if="placeholderIcon"
        class="material-symbols-outlined text-gray-400 dark:text-gray-500"
        :style="{fontSize: iconSize}"
      >
        {{ placeholderIcon }}
      </span>
      <span
        v-else-if="placeholderText"
        class="text-gray-400 dark:text-gray-500 text-sm text-center px-2"
      >
        {{ placeholderText }}
      </span>
    </div>
  </div>
</template>

<script setup>
import {ref, computed} from "vue";
import LoadingSpinner from "./LoadingSpinner.vue";

const props = defineProps({
  imageUrl: {
    type: String,
    default: null,
  },
  alt: {
    type: String,
    default: "Image",
  },
  // Container props
  containerClass: {
    type: String,
    default: "",
  },
  containerStyle: {
    type: Object,
    default: () => ({}),
  },
  // Image props
  imageClass: {
    type: String,
    default: "",
  },
  imageStyle: {
    type: Object,
    default: () => ({}),
  },
  // Placeholder props
  placeholderIcon: {
    type: String,
    default: "image",
  },
  placeholderText: {
    type: String,
    default: "",
  },
  placeholderClass: {
    type: String,
    default: "",
  },
  placeholderStyle: {
    type: Object,
    default: () => ({}),
  },
  iconSize: {
    type: String,
    default: "48px",
  },
  // Behavior props
  lazy: {
    type: Boolean,
    default: true,
  },
  baseUrl: {
    type: String,
    default: "",
  },
});

const isLoading = ref(true);
const imageError = ref(false);

const processedImageUrl = computed(() => {
  if (!props.imageUrl) return null;

  // If imageUrl is already a full URL, return as is
  if (props.imageUrl.startsWith("http://") || props.imageUrl.startsWith("https://") || props.imageUrl.startsWith("data:")) {
    return props.imageUrl;
  }

  // If baseUrl is provided, prepend it
  if (props.baseUrl) {
    const base = props.baseUrl.endsWith("/") ? props.baseUrl.slice(0, -1) : props.baseUrl;
    const url = props.imageUrl.startsWith("/") ? props.imageUrl : `/${props.imageUrl}`;
    return `${base}${url}`;
  }

  // Try to use environment variable for base URL
  const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1";
  const imageBaseUrl = import.meta.env.VITE_IMAGE_BASE_URL || apiBaseUrl.replace("/api/v1", "");
  const base = imageBaseUrl.endsWith("/") ? imageBaseUrl.slice(0, -1) : imageBaseUrl;
  const url = props.imageUrl.startsWith("/") ? props.imageUrl : `/${props.imageUrl}`;
  return `${base}${url}`;
});

const onImageLoad = () => {
  isLoading.value = false;
  imageError.value = false;
};

const onImageError = () => {
  isLoading.value = false;
  imageError.value = true;
};
</script>

