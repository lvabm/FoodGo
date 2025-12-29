<template>
  <div :class="containerClass">
    <!-- Card Skeleton -->
    <div v-if="type === 'card'" class="animate-pulse">
      <div class="bg-gray-200 dark:bg-gray-700 rounded-xl overflow-hidden">
        <div :class="[imageClass, 'bg-gray-300 dark:bg-gray-600']"></div>
        <div class="p-4 space-y-3">
          <div :class="[titleClass, 'bg-gray-300 dark:bg-gray-600 rounded']"></div>
          <div :class="[textClass, 'bg-gray-300 dark:bg-gray-600 rounded']"></div>
          <div :class="[textClass, 'bg-gray-300 dark:bg-gray-600 rounded w-3/4']"></div>
        </div>
      </div>
    </div>

    <!-- List Item Skeleton -->
    <div v-else-if="type === 'list'" class="animate-pulse space-y-4">
      <div
        v-for="i in count"
        :key="i"
        class="flex gap-4 items-center"
      >
        <div :class="[imageClass, 'bg-gray-300 dark:bg-gray-600 rounded-lg']"></div>
        <div class="flex-1 space-y-2">
          <div :class="[titleClass, 'bg-gray-300 dark:bg-gray-600 rounded']"></div>
          <div :class="[textClass, 'bg-gray-300 dark:bg-gray-600 rounded w-2/3']"></div>
        </div>
      </div>
    </div>

    <!-- Text Skeleton -->
    <div v-else-if="type === 'text'" class="animate-pulse space-y-2">
      <div
        v-for="i in count"
        :key="i"
        :class="[
          textClass,
          'bg-gray-300 dark:bg-gray-600 rounded',
          i === count ? 'w-3/4' : 'w-full'
        ]"
      ></div>
    </div>

    <!-- Image Skeleton -->
    <div v-else-if="type === 'image'" class="animate-pulse">
      <div :class="[imageClass, 'bg-gray-300 dark:bg-gray-600 rounded-xl']"></div>
    </div>

    <!-- Custom Skeleton -->
    <div v-else class="animate-pulse">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  type: {
    type: String,
    default: "card",
    validator: (value) => ["card", "list", "text", "image", "custom"].includes(value),
  },
  count: {
    type: Number,
    default: 1,
  },
  imageClass: {
    type: String,
    default: "h-48 w-full",
  },
  titleClass: {
    type: String,
    default: "h-5 w-3/4",
  },
  textClass: {
    type: String,
    default: "h-4 w-full",
  },
  containerClass: {
    type: String,
    default: "",
  },
});
</script>


