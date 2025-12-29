<template>
  <div
    :class="[
      'inline-flex items-center gap-1 px-3 py-1 rounded-full text-xs font-bold backdrop-blur-sm',
      scoreClasses
    ]"
  >
    <span class="material-symbols-outlined text-sm">trending_up</span>
    <span>{{ Math.round(score * 100) }}%</span>
    <span class="text-[10px] opacity-80">khớp</span>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  score: {
    type: Number,
    required: true,
    validator: (value) => value >= 0 && value <= 1
  }
});

const scoreClasses = computed(() => {
  if (props.score >= 0.8) {
    return 'bg-green-500/90 text-white';
  } else if (props.score >= 0.6) {
    return 'bg-primary/90 text-white';
  } else if (props.score >= 0.4) {
    return 'bg-yellow-500/90 text-white';
  } else {
    return 'bg-gray-500/90 text-white';
  }
});
</script>


