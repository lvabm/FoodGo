<template>
  <div
    :class="[
      'inline-flex items-center gap-2 px-3 py-1.5 rounded-full text-sm font-medium transition-all duration-200',
      variantClasses,
      removable ? 'pr-2' : ''
    ]"
  >
    <span v-if="icon" class="material-symbols-outlined text-base">{{ icon }}</span>
    <span>{{ label }}</span>
    <button
      v-if="removable"
      @click="$emit('remove')"
      class="ml-1 hover:bg-black/10 dark:hover:bg-white/10 rounded-full p-0.5 transition-colors"
      aria-label="Remove filter"
    >
      <span class="material-symbols-outlined text-sm">close</span>
    </button>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  label: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    default: null
  },
  variant: {
    type: String,
    default: 'primary',
    validator: (value) => ['primary', 'secondary', 'success', 'warning', 'danger', 'info'].includes(value)
  },
  removable: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['remove']);

const variantClasses = computed(() => {
  const variants = {
    primary: 'bg-primary/10 text-primary dark:bg-primary/20 dark:text-primary',
    secondary: 'bg-gray-100 text-gray-700 dark:bg-gray-700 dark:text-gray-300',
    success: 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400',
    warning: 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400',
    danger: 'bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400',
    info: 'bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400'
  };
  return variants[props.variant] || variants.primary;
});
</script>


