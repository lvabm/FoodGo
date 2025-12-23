<template>
  <div
    v-if="message"
    class="rounded-lg border p-4"
    :class="typeClasses"
    role="alert"
  >
    <div class="flex items-start">
      <span class="material-symbols-outlined mr-3" :class="iconColor">
        {{ icon }}
      </span>
      <div class="flex-1">
        <p class="font-semibold" v-if="title">{{ title }}</p>
        <p class="text-sm">{{ message }}</p>
      </div>
      <button
        v-if="dismissible"
        @click="$emit('dismiss')"
        class="ml-3 text-gray-400 hover:text-gray-600"
      >
        <span class="material-symbols-outlined">close</span>
      </button>
    </div>
  </div>
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
    error: "bg-red-50 border-red-200 text-red-800",
    success: "bg-green-50 border-green-200 text-green-800",
    warning: "bg-yellow-50 border-yellow-200 text-yellow-800",
    info: "bg-blue-50 border-blue-200 text-blue-800",
  };
  return classes[props.type];
});

const iconColor = computed(() => {
  const colors = {
    error: "text-red-500",
    success: "text-green-500",
    warning: "text-yellow-500",
    info: "text-blue-500",
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
