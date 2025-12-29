<template>
  <div class="relative" ref="containerRef">
    <div class="relative">
      <slot name="input">
        <input
          :value="modelValue"
          @input="handleInput"
          @focus="showSuggestions = true"
          @blur="handleBlur"
          @keydown.enter="handleEnter"
          @keydown.arrow-down="navigateDown"
          @keydown.arrow-up="navigateUp"
          @keydown.escape="hideSuggestions"
          type="text"
          :placeholder="placeholder"
          :class="inputClass"
        />
      </slot>
    </div>

    <!-- Suggestions Dropdown -->
    <Transition name="fade-slide">
      <div
        v-if="showSuggestions && (suggestions.history.length > 0 || suggestions.popular.length > 0 || filteredResults.length > 0)"
        class="absolute z-50 w-full mt-2 bg-white dark:bg-surface-dark rounded-xl shadow-xl border border-border-light dark:border-border-dark max-h-96 overflow-y-auto"
      >
        <!-- Search Results -->
        <div v-if="filteredResults.length > 0" class="py-2">
          <div class="px-4 py-2 text-xs font-semibold text-subtext-light dark:text-subtext-dark uppercase">
            Kết quả tìm kiếm
          </div>
          <button
            v-for="(result, index) in filteredResults"
            :key="`result-${index}`"
            @click="selectResult(result)"
            @mouseenter="selectedIndex = index"
            :class="[
              'w-full text-left px-4 py-3 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors',
              selectedIndex === index ? 'bg-gray-100 dark:bg-gray-800' : ''
            ]"
          >
            <div class="flex items-center gap-3">
              <span class="material-symbols-outlined text-primary text-lg">
                {{ result.icon || 'search' }}
              </span>
              <div class="flex-1">
                <div class="font-medium text-text-light dark:text-text-dark">
                  {{ result.text }}
                </div>
                <div v-if="result.subtitle" class="text-xs text-subtext-light dark:text-subtext-dark">
                  {{ result.subtitle }}
                </div>
              </div>
            </div>
          </button>
        </div>

        <!-- Search History -->
        <div v-if="suggestions.history.length > 0 && filteredResults.length === 0" class="py-2">
          <div class="flex items-center justify-between px-4 py-2">
            <div class="text-xs font-semibold text-subtext-light dark:text-subtext-dark uppercase">
              Lịch sử tìm kiếm
            </div>
            <button
              @click.stop="clearHistory"
              class="text-xs text-primary hover:underline"
            >
              Xóa
            </button>
          </div>
          <button
            v-for="(item, index) in suggestions.history"
            :key="`history-${index}`"
            @click="selectHistory(item)"
            @mouseenter="selectedIndex = suggestions.history.length + index"
            @contextmenu.prevent="removeHistoryItem(item)"
            :class="[
              'w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors flex items-center justify-between',
              selectedIndex === suggestions.history.length + index ? 'bg-gray-100 dark:bg-gray-800' : ''
            ]"
          >
            <div class="flex items-center gap-3 flex-1">
              <span class="material-symbols-outlined text-subtext-light dark:text-subtext-dark text-base">
                history
              </span>
              <span class="text-text-light dark:text-text-dark">{{ item }}</span>
            </div>
            <button
              @click.stop="removeHistoryItem(item)"
              class="text-subtext-light dark:text-subtext-dark hover:text-red-500 p-1"
            >
              <span class="material-symbols-outlined text-sm">close</span>
            </button>
          </button>
        </div>

        <!-- Popular Searches -->
        <div v-if="suggestions.popular.length > 0 && filteredResults.length === 0 && suggestions.history.length === 0" class="py-2">
          <div class="px-4 py-2 text-xs font-semibold text-subtext-light dark:text-subtext-dark uppercase">
            Tìm kiếm phổ biến
          </div>
          <button
            v-for="(item, index) in suggestions.popular"
            :key="`popular-${index}`"
            @click="selectPopular(item)"
            @mouseenter="selectedIndex = suggestions.history.length + suggestions.popular.length + index"
            :class="[
              'w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors flex items-center gap-3',
              selectedIndex === suggestions.history.length + suggestions.popular.length + index ? 'bg-gray-100 dark:bg-gray-800' : ''
            ]"
          >
            <span class="material-symbols-outlined text-primary text-base">trending_up</span>
            <span class="text-text-light dark:text-text-dark">{{ item }}</span>
          </button>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import {ref, computed, watch, onMounted, onUnmounted} from "vue";

const props = defineProps({
  modelValue: {
    type: String,
    default: "",
  },
  placeholder: {
    type: String,
    default: "Tìm kiếm...",
  },
  inputClass: {
    type: String,
    default: "",
  },
  suggestions: {
    type: Object,
    default: () => ({history: [], popular: []}),
  },
  searchResults: {
    type: Array,
    default: () => [],
  },
  debounceMs: {
    type: Number,
    default: 300,
  },
});

const emit = defineEmits(["update:modelValue", "select", "search"]);

const showSuggestions = ref(false);
const selectedIndex = ref(-1);
const containerRef = ref(null);
let searchTimeout = null;

const filteredResults = computed(() => {
  if (!props.modelValue || props.modelValue.trim().length === 0) {
    return [];
  }
  return props.searchResults || [];
});

const handleInput = (event) => {
  const value = event.target.value;
  emit("update:modelValue", value);
  showSuggestions.value = true;
  selectedIndex.value = -1;

  // Debounce search
  if (searchTimeout) {
    clearTimeout(searchTimeout);
  }
  searchTimeout = setTimeout(() => {
    if (value.trim().length > 0) {
      emit("search", value);
    }
  }, props.debounceMs);
};

const handleBlur = (event) => {
  // Delay to allow click events
  setTimeout(() => {
    if (!containerRef.value?.contains(document.activeElement)) {
      showSuggestions.value = false;
    }
  }, 200);
};

const handleEnter = () => {
  if (selectedIndex.value >= 0) {
    const allItems = [
      ...filteredResults.value,
      ...props.suggestions.history,
      ...props.suggestions.popular,
    ];
    if (allItems[selectedIndex.value]) {
      selectItem(allItems[selectedIndex.value]);
    }
  } else if (props.modelValue.trim().length > 0) {
    emit("select", props.modelValue);
  }
};

const navigateDown = (event) => {
  event.preventDefault();
  const maxIndex = filteredResults.value.length + props.suggestions.history.length + props.suggestions.popular.length - 1;
  selectedIndex.value = selectedIndex.value < maxIndex ? selectedIndex.value + 1 : 0;
};

const navigateUp = (event) => {
  event.preventDefault();
  const maxIndex = filteredResults.value.length + props.suggestions.history.length + props.suggestions.popular.length - 1;
  selectedIndex.value = selectedIndex.value > 0 ? selectedIndex.value - 1 : maxIndex;
};

const hideSuggestions = () => {
  showSuggestions.value = false;
  selectedIndex.value = -1;
};

const selectItem = (item) => {
  const value = typeof item === "string" ? item : item.text || item.value;
  emit("update:modelValue", value);
  emit("select", value);
  showSuggestions.value = false;
};

const selectResult = (result) => {
  selectItem(result);
};

const selectHistory = (item) => {
  selectItem(item);
};

const selectPopular = (item) => {
  selectItem(item);
};

const removeHistoryItem = (item) => {
  emit("remove-history", item);
};

const clearHistory = () => {
  emit("clear-history");
};

watch(() => props.modelValue, (newValue) => {
  if (newValue.trim().length === 0) {
    selectedIndex.value = -1;
  }
});

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
  if (searchTimeout) {
    clearTimeout(searchTimeout);
  }
});

const handleClickOutside = (event) => {
  if (containerRef.value && !containerRef.value.contains(event.target)) {
    showSuggestions.value = false;
  }
};
</script>

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.2s ease;
}

.fade-slide-enter-from,
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>


