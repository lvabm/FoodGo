<template>
  <div class="flex flex-col items-center justify-center py-12 px-4 text-center">
    <div class="mb-4">
      <span
        v-if="icon"
        class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600"
        :style="{fontSize: iconSize}"
      >
        {{ icon }}
      </span>
      <div
        v-else-if="customIcon"
        class="text-6xl text-gray-300 dark:text-gray-600"
        v-html="customIcon"
      ></div>
    </div>
    <h3
      v-if="title"
      class="text-xl font-bold text-text-light dark:text-text-dark mb-2"
    >
      {{ title }}
    </h3>
    <p
      v-if="message"
      class="text-subtext-light dark:text-subtext-dark mb-6 max-w-md"
    >
      {{ message }}
    </p>
    <slot name="action">
      <button
        v-if="actionLabel && actionHandler"
        @click="actionHandler"
        class="px-6 py-3 bg-primary text-white rounded-lg font-semibold hover:bg-opacity-90 transition-colors shadow-md hover:shadow-lg"
      >
        {{ actionLabel }}
      </button>
    </slot>
  </div>
</template>

<script setup>
defineProps({
  icon: {
    type: String,
    default: "inbox",
  },
  customIcon: {
    type: String,
    default: null,
  },
  iconSize: {
    type: String,
    default: "64px",
  },
  title: {
    type: String,
    default: "Không có dữ liệu",
  },
  message: {
    type: String,
    default: "Hiện tại chưa có dữ liệu để hiển thị.",
  },
  actionLabel: {
    type: String,
    default: null,
  },
  actionHandler: {
    type: Function,
    default: null,
  },
});
</script>


