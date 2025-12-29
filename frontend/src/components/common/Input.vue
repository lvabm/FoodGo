<template>
  <div class="w-full">
    <label
      v-if="label"
      :for="inputId"
      class="block text-sm font-medium text-text-light dark:text-text-dark mb-2"
    >
      {{ label }}
      <span v-if="required" class="text-red-500 ml-1">*</span>
    </label>
    <div class="relative">
      <span
        v-if="icon"
        class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-subtext-light dark:text-subtext-dark pointer-events-none"
      >
        {{ icon }}
      </span>
      <input
        :id="inputId"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :required="required"
        :class="[
          baseClasses,
          icon ? 'pl-10' : 'pl-4',
          error ? 'border-red-500 focus:ring-red-500' : '',
          customClass
        ]"
        @input="$emit('update:modelValue', $event.target.value)"
        @blur="$emit('blur', $event)"
        @focus="$emit('focus', $event)"
      />
      <span
        v-if="error"
        class="material-symbols-outlined absolute right-3 top-1/2 -translate-y-1/2 text-red-500 pointer-events-none"
      >
        error
      </span>
    </div>
    <p v-if="error" class="mt-1 text-sm text-red-600 dark:text-red-400">
      {{ error }}
    </p>
    <p v-else-if="hint" class="mt-1 text-sm text-subtext-light dark:text-subtext-dark">
      {{ hint }}
    </p>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: "",
  },
  type: {
    type: String,
    default: "text",
  },
  label: {
    type: String,
    default: null,
  },
  placeholder: {
    type: String,
    default: "",
  },
  icon: {
    type: String,
    default: null,
  },
  error: {
    type: String,
    default: null,
  },
  hint: {
    type: String,
    default: null,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  required: {
    type: Boolean,
    default: false,
  },
  customClass: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["update:modelValue", "blur", "focus"]);

const inputId = computed(() => {
  return `input-${Math.random().toString(36).substr(2, 9)}`;
});

const baseClasses = computed(() => {
  return "w-full pr-4 py-3.5 rounded-[14px] border-[0.5px] border-[rgba(0,0,0,0.1)] dark:border-[rgba(255,255,255,0.1)] bg-white/80 dark:bg-surface-dark/80 backdrop-blur-[20px] text-text-light dark:text-text-dark placeholder:text-subtext-light dark:placeholder:text-subtext-dark focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:border-primary/30";
});
</script>

