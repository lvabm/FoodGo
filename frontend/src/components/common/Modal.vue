<template>
  <Teleport to="body">
    <Transition name="modal">
      <div
        v-if="show"
        @click.self="handleClose"
        class="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm"
        @keydown.esc="handleClose"
      >
        <div
          :class="[
            'relative bg-white dark:bg-surface-dark rounded-2xl shadow-2xl max-h-[90vh] overflow-hidden',
            sizeClasses,
            customClass
          ]"
          @click.stop
        >
          <!-- Header -->
          <div
            v-if="title || $slots.header"
            class="flex items-center justify-between p-6 border-b border-border-light dark:border-border-dark"
          >
            <slot name="header">
              <h3 v-if="title" class="text-xl font-bold text-text-light dark:text-text-dark">
                {{ title }}
              </h3>
            </slot>
            <button
              v-if="closable"
              @click="handleClose"
              class="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark"
              aria-label="Đóng"
            >
              <span class="material-symbols-outlined">close</span>
            </button>
          </div>

          <!-- Body -->
          <div class="p-6 overflow-y-auto max-h-[calc(90vh-140px)]">
            <slot></slot>
          </div>

          <!-- Footer -->
          <div
            v-if="$slots.footer"
            class="flex items-center justify-end gap-3 p-6 border-t border-border-light dark:border-border-dark bg-gray-50 dark:bg-gray-900/50"
          >
            <slot name="footer"></slot>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import {computed, watch, onMounted, onUnmounted} from "vue";

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: null,
  },
  size: {
    type: String,
    default: "md",
    validator: (value) => ["sm", "md", "lg", "xl", "full"].includes(value),
  },
  closable: {
    type: Boolean,
    default: true,
  },
  customClass: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["update:show", "close"]);

const sizeClasses = computed(() => {
  const sizes = {
    sm: "w-full max-w-md",
    md: "w-full max-w-lg",
    lg: "w-full max-w-2xl",
    xl: "w-full max-w-4xl",
    full: "w-full max-w-7xl",
  };
  return sizes[props.size];
});

const handleClose = () => {
  if (props.closable) {
    emit("update:show", false);
    emit("close");
  }
};

// Prevent body scroll when modal is open
watch(() => props.show, (newValue) => {
  if (newValue) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "";
  }
});

onMounted(() => {
  if (props.show) {
    document.body.style.overflow = "hidden";
  }
});

onUnmounted(() => {
  document.body.style.overflow = "";
});
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-from > div,
.modal-leave-to > div {
  transform: scale(0.9) translateY(-20px);
}

.modal-enter-to > div,
.modal-leave-from > div {
  transform: scale(1) translateY(0);
}
</style>


