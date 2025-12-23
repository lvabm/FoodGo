<template>
  <div class="w-full" :style="{height: height + 'px'}">
    <svg :width="width" :height="height" class="w-full h-full">
      <g v-for="(val, i) in values" :key="i">
        <rect
          :x="i * (barWidth + gap) + padding"
          :y="height - padding - (val === 0 ? 1 : (val / maxVal) * innerHeight)"
          :width="barWidth"
          :height="val === 0 ? 1 : (val / maxVal) * innerHeight"
          :fill="color"
          class="transition-transform hover:scale-y-105 cursor-pointer"
          @mouseenter="onHover(i, $event)"
          @mouseleave="onLeave"
        />
      </g>
      <!-- Labels -->
      <g v-for="(label, i) in labels" :key="'lbl-' + i">
        <text
          :x="i * (barWidth + gap) + padding + barWidth / 2"
          :y="height - 6"
          text-anchor="middle"
          class="text-subtext-light text-xs"
          fill="#A77B6B"
        >
          {{ label }}
        </text>
      </g>
    </svg>

    <div
      v-if="hoverIndex !== null"
      class="absolute z-10 bg-white border px-2 py-1 rounded shadow-md text-xs"
    >
      <div class="font-medium">{{ labels[hoverIndex] }}</div>
      <div>{{ values[hoverIndex] }} đặt</div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";

const props = defineProps({
  labels: {type: Array, default: () => []},
  values: {type: Array, default: () => []},
  height: {type: Number, default: 220},
  color: {type: String, default: "#F97316"}, // primary-ish
});

const hoverIndex = ref(null);
const tooltipX = ref(0);
const tooltipY = ref(0);

const width = "100%";
const padding = 20; // left/right padding
const gap = 14;
const barWidth = computed(() => {
  const n = Math.max(1, props.values.length);
  const totalGap = (n - 1) * gap + padding * 2;
  // assume container width; we'll rely on relative positions for SVG
  return Math.max(20, (220 - totalGap) / n);
});
const innerHeight = computed(() => props.height - padding * 2 - 24);

const maxVal = computed(() => Math.max(...props.values, 1));

function onHover(i, ev) {
  hoverIndex.value = i;
}
function onLeave() {
  hoverIndex.value = null;
}
</script>

<style scoped>
/* minimal scoped styles */
</style>
