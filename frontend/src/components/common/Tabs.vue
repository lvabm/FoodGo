<template>
  <div class="w-full">
    <!-- Tab Headers -->
    <div class="flex border-b border-border-light dark:border-border-dark overflow-x-auto">
      <button
        v-for="(tab, index) in tabs"
        :key="index"
        @click="selectTab(index)"
        :class="[
          'px-6 py-3 text-sm font-medium transition-all duration-200 whitespace-nowrap',
          index === activeTab
            ? 'text-primary border-b-2 border-primary'
            : 'text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark'
        ]"
      >
        <span v-if="tab.icon" class="material-symbols-outlined mr-2 align-middle">
          {{ tab.icon }}
        </span>
        {{ tab.label }}
        <span
          v-if="tab.badge"
          class="ml-2 px-2 py-0.5 text-xs rounded-full bg-primary/10 text-primary"
        >
          {{ tab.badge }}
        </span>
      </button>
    </div>

    <!-- Tab Content -->
    <div class="mt-4">
      <Transition name="fade" mode="out-in">
        <div :key="activeTab">
          <slot :name="`tab-${activeTab}`" :tab="tabs[activeTab]">
            {{ tabs[activeTab]?.content }}
          </slot>
        </div>
      </Transition>
    </div>
  </div>
</template>

<script setup>
import {ref, watch} from "vue";

const props = defineProps({
  tabs: {
    type: Array,
    required: true,
    validator: (tabs) => {
      return tabs.every(tab => tab.label);
    },
  },
  defaultTab: {
    type: Number,
    default: 0,
  },
});

const emit = defineEmits(["tab-change"]);

const activeTab = ref(props.defaultTab);

const selectTab = (index) => {
  activeTab.value = index;
  emit("tab-change", index, props.tabs[index]);
};

watch(() => props.defaultTab, (newValue) => {
  activeTab.value = newValue;
});
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>


