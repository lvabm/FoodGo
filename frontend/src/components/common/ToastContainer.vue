<template>
  <div
    class="fixed top-4 right-4 z-50 flex flex-col gap-2 max-w-md w-full"
    v-if="toasts.length > 0"
  >
    <TransitionGroup name="toast" tag="div">
      <div
        v-for="toast in toasts"
        :key="toast.id"
        :class="getToastClasses(toast.type)"
        class="flex items-start gap-3 p-4 rounded-lg shadow-lg border"
      >
        <span class="material-symbols-outlined flex-shrink-0" :class="getIconColor(toast.type)">
          {{ getIcon(toast.type) }}
        </span>
        <div class="flex-1">
          <p class="text-sm font-medium">{{ toast.message }}</p>
        </div>
        <button
          @click="removeToast(toast.id)"
          class="flex-shrink-0 text-gray-400 hover:text-gray-600"
        >
          <span class="material-symbols-outlined text-lg">close</span>
        </button>
      </div>
    </TransitionGroup>
  </div>
</template>

<script setup>
import {useToast} from "@/composables/useToast";

const {toasts, removeToast} = useToast();

const getToastClasses = (type) => {
  const classes = {
    success: "bg-green-50 border-green-200 text-green-800 dark:bg-green-900/20 dark:border-green-800 dark:text-green-400",
    error: "bg-red-50 border-red-200 text-red-800 dark:bg-red-900/20 dark:border-red-800 dark:text-red-400",
    warning: "bg-yellow-50 border-yellow-200 text-yellow-800 dark:bg-yellow-900/20 dark:border-yellow-800 dark:text-yellow-400",
    info: "bg-blue-50 border-blue-200 text-blue-800 dark:bg-blue-900/20 dark:border-blue-800 dark:text-blue-400",
  };
  return classes[type] || classes.info;
};

const getIconColor = (type) => {
  const colors = {
    success: "text-green-500",
    error: "text-red-500",
    warning: "text-yellow-500",
    info: "text-blue-500",
  };
  return colors[type] || colors.info;
};

const getIcon = (type) => {
  const icons = {
    success: "check_circle",
    error: "error",
    warning: "warning",
    info: "info",
  };
  return icons[type] || icons.info;
};
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}
</style>

