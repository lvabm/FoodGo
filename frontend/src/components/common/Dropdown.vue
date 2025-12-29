<template>
  <div class="relative inline-block" ref="dropdownRef">
    <div @click="toggleDropdown">
      <slot name="trigger">
        <Button :variant="triggerVariant" :size="triggerSize">
          {{ triggerLabel }}
          <span class="material-symbols-outlined ml-1">
            {{ isOpen ? "expand_less" : "expand_more" }}
          </span>
        </Button>
      </slot>
    </div>

    <Transition name="dropdown">
      <div
        v-if="isOpen"
        :class="[
          'absolute z-50 mt-2 rounded-lg shadow-xl border border-border-light dark:border-border-dark bg-white dark:bg-surface-dark overflow-hidden',
          positionClasses,
          widthClass
        ]"
        @click.stop
      >
        <div class="py-1">
          <slot></slot>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import {ref, computed, onMounted, onUnmounted} from "vue";
import Button from "./Button.vue";

const props = defineProps({
  triggerLabel: {
    type: String,
    default: "Menu",
  },
  triggerVariant: {
    type: String,
    default: "outline",
  },
  triggerSize: {
    type: String,
    default: "md",
  },
  position: {
    type: String,
    default: "bottom-left",
    validator: (value) => ["bottom-left", "bottom-right", "top-left", "top-right"].includes(value),
  },
  width: {
    type: String,
    default: "auto",
    validator: (value) => ["auto", "sm", "md", "lg", "full"].includes(value),
  },
});

const isOpen = ref(false);
const dropdownRef = ref(null);

const positionClasses = computed(() => {
  const positions = {
    "bottom-left": "left-0 top-full",
    "bottom-right": "right-0 top-full",
    "top-left": "left-0 bottom-full mb-2",
    "top-right": "right-0 bottom-full mb-2",
  };
  return positions[props.position];
});

const widthClass = computed(() => {
  const widths = {
    auto: "w-auto min-w-[200px]",
    sm: "w-48",
    md: "w-64",
    lg: "w-80",
    full: "w-full",
  };
  return widths[props.width];
});

const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};

const handleClickOutside = (event) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    isOpen.value = false;
  }
};

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
});
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.2s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}
</style>


