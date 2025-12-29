<template>
  <div
    :class="[
      'border-t border-border-light dark:border-border-dark',
      vertical ? 'border-l h-full w-px' : 'w-full',
      spacingClass,
      customClass
    ]"
    role="separator"
    :aria-orientation="vertical ? 'vertical' : 'horizontal'"
  >
    <span
      v-if="label && !vertical"
      class="absolute left-1/2 -translate-x-1/2 px-3 bg-background-light dark:bg-background-dark text-subtext-light dark:text-subtext-dark text-sm"
    >
      {{ label }}
    </span>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  vertical: {
    type: Boolean,
    default: false,
  },
  label: {
    type: String,
    default: null,
  },
  spacing: {
    type: String,
    default: "md",
    validator: (value) => ["none", "sm", "md", "lg"].includes(value),
  },
  customClass: {
    type: String,
    default: "",
  },
});

const spacingClass = computed(() => {
  if (props.vertical) {
    const spacings = {
      none: "",
      sm: "my-2",
      md: "my-4",
      lg: "my-6",
    };
    return spacings[props.spacing];
  } else {
    const spacings = {
      none: "",
      sm: "mx-2 my-4",
      md: "mx-4 my-6",
      lg: "mx-6 my-8",
    };
    return spacings[props.spacing];
  }
});
</script>


