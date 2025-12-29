<template>
  <div class="relative inline-block" @mouseenter="showTooltip" @mouseleave="hideTooltip">
    <slot></slot>
    <Transition name="tooltip">
      <div
        v-if="isVisible"
        :class="[
          'absolute z-50 px-3 py-2 text-sm rounded-lg shadow-lg pointer-events-none',
          positionClasses,
          colorClass
        ]"
        role="tooltip"
      >
        {{ text }}
        <div
          :class="[
            'absolute w-2 h-2 rotate-45',
            arrowClasses
          ]"
        ></div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import {ref, computed} from "vue";

const props = defineProps({
  text: {
    type: String,
    required: true,
  },
  position: {
    type: String,
    default: "top",
    validator: (value) => ["top", "bottom", "left", "right"].includes(value),
  },
  variant: {
    type: String,
    default: "dark",
    validator: (value) => ["dark", "light"].includes(value),
  },
  delay: {
    type: Number,
    default: 200,
  },
});

const isVisible = ref(false);
let timeoutId = null;

const showTooltip = () => {
  timeoutId = setTimeout(() => {
    isVisible.value = true;
  }, props.delay);
};

const hideTooltip = () => {
  if (timeoutId) {
    clearTimeout(timeoutId);
    timeoutId = null;
  }
  isVisible.value = false;
};

const positionClasses = computed(() => {
  const positions = {
    top: "bottom-full left-1/2 -translate-x-1/2 mb-2",
    bottom: "top-full left-1/2 -translate-x-1/2 mt-2",
    left: "right-full top-1/2 -translate-y-1/2 mr-2",
    right: "left-full top-1/2 -translate-y-1/2 ml-2",
  };
  return positions[props.position];
});

const arrowClasses = computed(() => {
  const arrows = {
    top: "bottom-0 left-1/2 -translate-x-1/2 translate-y-1/2 bg-gray-800 dark:bg-gray-700",
    bottom: "top-0 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-gray-800 dark:bg-gray-700",
    left: "right-0 top-1/2 -translate-y-1/2 translate-x-1/2 bg-gray-800 dark:bg-gray-700",
    right: "left-0 top-1/2 -translate-y-1/2 -translate-x-1/2 bg-gray-800 dark:bg-gray-700",
  };
  return arrows[props.position];
});

const colorClass = computed(() => {
  if (props.variant === "light") {
    return "bg-white dark:bg-gray-800 text-gray-800 dark:text-gray-200 border border-gray-200 dark:border-gray-700";
  }
  return "bg-gray-800 dark:bg-gray-700 text-white";
});
</script>

<style scoped>
.tooltip-enter-active,
.tooltip-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.tooltip-enter-from,
.tooltip-leave-to {
  opacity: 0;
  transform: scale(0.9);
}
</style>


