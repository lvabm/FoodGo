<template>
  <div
    :class="[
      'transition-all duration-300',
      shadowClass,
      hoverClass,
      paddingClass,
      backgroundClass,
      customClass
    ]"
    style="border-radius: 20px;"
  >
    <div v-if="title || $slots.header" class="mb-4">
      <slot name="header">
        <h3 v-if="title" class="text-lg font-bold text-text-light dark:text-text-dark">
          {{ title }}
        </h3>
      </slot>
    </div>
    <slot></slot>
    <div v-if="$slots.footer" class="mt-4 pt-4 border-t border-border-light dark:border-border-dark">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  title: {
    type: String,
    default: null,
  },
  shadow: {
    type: String,
    default: "md",
    validator: (value) => ["none", "sm", "md", "lg", "xl"].includes(value),
  },
  hover: {
    type: Boolean,
    default: false,
  },
  padding: {
    type: String,
    default: "md",
    validator: (value) => ["none", "sm", "md", "lg", "xl"].includes(value),
  },
  variant: {
    type: String,
    default: "default",
    validator: (value) => ["default", "outlined", "elevated"].includes(value),
  },
  customClass: {
    type: String,
    default: "",
  },
});

const shadowClass = computed(() => {
  if (props.variant === "outlined") {
    return "border-[0.5px] border-[rgba(0,0,0,0.08)] dark:border-[rgba(255,255,255,0.1)] hover:border-primary/30 transition-colors duration-300";
  }
  if (props.variant === "elevated") {
    const shadows = {
      none: "",
      sm: "shadow-[0_1px_3px_rgba(0,0,0,0.04),0_1px_2px_rgba(0,0,0,0.06)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08),0_2px_4px_rgba(0,0,0,0.08)]",
      md: "shadow-[0_2px_8px_rgba(0,0,0,0.04),0_1px_2px_rgba(0,0,0,0.06)] hover:shadow-[0_8px_24px_rgba(0,0,0,0.08),0_2px_4px_rgba(0,0,0,0.08)]",
      lg: "shadow-[0_4px_16px_rgba(0,0,0,0.06),0_2px_4px_rgba(0,0,0,0.08)] hover:shadow-[0_12px_32px_rgba(0,0,0,0.1),0_4px_8px_rgba(0,0,0,0.1)]",
      xl: "shadow-[0_8px_24px_rgba(0,0,0,0.08),0_4px_8px_rgba(0,0,0,0.1)] hover:shadow-[0_16px_48px_rgba(0,0,0,0.12),0_8px_16px_rgba(0,0,0,0.12)]",
    };
    return shadows[props.shadow] + " transition-shadow duration-300";
  }
  const shadows = {
    none: "",
    sm: "shadow-[0_1px_3px_rgba(0,0,0,0.04),0_1px_2px_rgba(0,0,0,0.06)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08),0_2px_4px_rgba(0,0,0,0.08)]",
    md: "shadow-[0_2px_8px_rgba(0,0,0,0.04),0_1px_2px_rgba(0,0,0,0.06)] hover:shadow-[0_8px_24px_rgba(0,0,0,0.08),0_2px_4px_rgba(0,0,0,0.08)]",
    lg: "shadow-[0_4px_16px_rgba(0,0,0,0.06),0_2px_4px_rgba(0,0,0,0.08)] hover:shadow-[0_12px_32px_rgba(0,0,0,0.1),0_4px_8px_rgba(0,0,0,0.1)]",
    xl: "shadow-[0_8px_24px_rgba(0,0,0,0.08),0_4px_8px_rgba(0,0,0,0.1)] hover:shadow-[0_16px_48px_rgba(0,0,0,0.12),0_8px_16px_rgba(0,0,0,0.12)]",
  };
  return shadows[props.shadow] + " transition-shadow duration-300";
});

const hoverClass = computed(() => {
  if (!props.hover) return "";
  return "hover:-translate-y-[2px] cursor-pointer transition-all duration-300";
});

const paddingClass = computed(() => {
  const paddings = {
    none: "",
    sm: "p-3",
    md: "p-4",
    lg: "p-6",
    xl: "p-8",
  };
  return paddings[props.padding];
});

const backgroundClass = computed(() => {
  if (props.variant === "outlined") return "bg-transparent";
  return "bg-white dark:bg-surface-dark";
});
</script>

