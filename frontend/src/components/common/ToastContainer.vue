<template>
  <Teleport to="body">
    <div
      class="fixed top-4 right-4 z-[9999] flex flex-col gap-3 max-w-md w-full pointer-events-none"
      v-if="toasts.length > 0"
    >
      <TransitionGroup name="toast" tag="div" class="flex flex-col gap-3">
        <div
          v-for="toast in toasts"
          :key="toast.id"
          :class="getToastClasses(toast.type)"
          class="flex items-start gap-3 p-4 rounded-xl shadow-xl border backdrop-blur-sm pointer-events-auto hover:shadow-2xl transition-all duration-300 hover:-translate-y-1"
        >
          <span class="material-symbols-outlined flex-shrink-0 text-xl" :class="getIconColor(toast.type)">
            {{ getIcon(toast.type) }}
          </span>
          <div class="flex-1 min-w-0">
            <p v-if="toast.title" class="text-sm font-semibold mb-1">{{ toast.title }}</p>
            <p class="text-sm leading-relaxed">{{ toast.message }}</p>
          </div>
          <button
            @click="removeToast(toast.id)"
            class="flex-shrink-0 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors p-1 rounded hover:bg-gray-100 dark:hover:bg-gray-700"
            aria-label="Đóng"
          >
            <span class="material-symbols-outlined text-lg">close</span>
          </button>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<script setup>
import {useToast} from "@/composables/useToast";

const {toasts, removeToast} = useToast();

const getToastClasses = (type) => {
  const classes = {
    success: "bg-green-50/95 dark:bg-green-900/30 border-green-200 dark:border-green-800 text-green-800 dark:text-green-300",
    error: "bg-red-50/95 dark:bg-red-900/30 border-red-200 dark:border-red-800 text-red-800 dark:text-red-300",
    warning: "bg-yellow-50/95 dark:bg-yellow-900/30 border-yellow-200 dark:border-yellow-800 text-yellow-800 dark:text-yellow-300",
    info: "bg-blue-50/95 dark:bg-blue-900/30 border-blue-200 dark:border-blue-800 text-blue-800 dark:text-blue-300",
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
.toast-enter-active {
  transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
}

.toast-leave-active {
  transition: all 0.3s ease-in;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(120%) scale(0.9);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(120%) scale(0.9);
}

.toast-move {
  transition: transform 0.3s ease;
}
</style>


