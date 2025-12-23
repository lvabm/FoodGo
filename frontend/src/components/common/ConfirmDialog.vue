<template>
  <div
    v-if="confirmState.show"
    class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
    @click.self="confirmState.onCancel?.()"
  >
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
    >
      <h3 class="text-lg font-bold mb-2">{{ confirmState.title }}</h3>
      <p class="text-sm text-subtext-light dark:text-subtext-dark mb-6">
        {{ confirmState.message }}
      </p>
      <div class="flex gap-2 justify-end">
        <button
          @click="confirmState.onCancel?.()"
          class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
        >
          Hủy
        </button>
        <button
          @click="confirmState.onConfirm?.()"
          class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
        >
          Xác nhận
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {confirmState} from "@/composables/useConfirm";
</script>

