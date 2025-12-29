<template>
  <Transition name="fade-slide">
    <div
      v-if="message"
      class="rounded-lg border p-4 animate-slide-down"
      :class="typeClasses"
      role="alert"
    >
      <div class="flex items-start gap-3">
        <span class="material-symbols-outlined flex-shrink-0" :class="iconColor">
          {{ icon }}
        </span>
        <div class="flex-1 min-w-0">
          <p v-if="title" class="font-semibold mb-1 text-sm">{{ title }}</p>
          <p class="text-sm leading-relaxed">{{ message }}</p>
        </div>
        <button
          v-if="dismissible"
          @click="$emit('dismiss')"
          class="flex-shrink-0 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors p-1 rounded hover:bg-gray-100 dark:hover:bg-gray-700"
          aria-label="Đóng"
        >
          <span class="material-symbols-outlined text-lg">close</span>
        </button>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  message: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "",
  },
  type: {
    type: String,
    default: "error",
    validator: (value) =>
      ["error", "success", "warning", "info"].includes(value),
  },
  dismissible: {
    type: Boolean,
    default: true,
  },
});

defineEmits(["dismiss"]);

const typeClasses = computed(() => {
  const classes = {
    error: "bg-red-50 dark:bg-red-900/20 border-red-200 dark:border-red-800 text-red-800 dark:text-red-400",
    success: "bg-green-50 dark:bg-green-900/20 border-green-200 dark:border-green-800 text-green-800 dark:text-green-400",
    warning: "bg-yellow-50 dark:bg-yellow-900/20 border-yellow-200 dark:border-yellow-800 text-yellow-800 dark:text-yellow-400",
    info: "bg-blue-50 dark:bg-blue-900/20 border-blue-200 dark:border-blue-800 text-blue-800 dark:text-blue-400",
  };
  return classes[props.type];
});

const iconColor = computed(() => {
  const colors = {
    error: "text-red-500 dark:text-red-400",
    success: "text-green-500 dark:text-green-400",
    warning: "text-yellow-500 dark:text-yellow-400",
    info: "text-blue-500 dark:text-blue-400",
  };
  return colors[props.type];
});

const icon = computed(() => {
  const icons = {
    error: "error",
    success: "check_circle",
    warning: "warning",
    info: "info",
  };
  return icons[props.type];
});
</script>

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.3s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(-10px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
