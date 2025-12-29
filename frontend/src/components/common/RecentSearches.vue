<template>
  <div class="w-full space-y-4">
    <!-- Header -->
    <div class="flex items-center justify-between">
      <h3 class="text-lg font-semibold text-text-light dark:text-text-dark flex items-center gap-2">
        <span class="material-symbols-outlined text-primary">history</span>
        Tìm kiếm gần đây
      </h3>
      <div class="flex items-center gap-2">
        <button
          v-if="showSaved && savedSearches.length > 0"
          @click="showSaved = false"
          :class="[
            'px-3 py-1 rounded-lg text-sm font-medium transition-colors',
            !showSaved ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
          ]"
        >
          Gần đây
        </button>
        <button
          v-if="savedSearches.length > 0"
          @click="showSaved = true"
          :class="[
            'px-3 py-1 rounded-lg text-sm font-medium transition-colors',
            showSaved ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-700 text-text-light dark:text-text-dark hover:bg-gray-200 dark:hover:bg-gray-600'
          ]"
        >
          <span class="material-symbols-outlined text-sm align-middle mr-1">bookmark</span>
          Đã lưu ({{ savedSearches.length }})
        </button>
        <button
          v-if="!showSaved && recentSearches.length > 0"
          @click="clearHistory"
          class="text-sm text-subtext-light dark:text-subtext-dark hover:text-red-500 transition-colors"
          title="Xóa lịch sử"
        >
          <span class="material-symbols-outlined text-base">delete</span>
        </button>
      </div>
    </div>

    <!-- Recent Searches -->
    <div v-if="!showSaved && recentSearches.length > 0" class="space-y-2">
      <div
        v-for="(item, index) in recentSearches"
        :key="`recent-${index}`"
        class="group flex items-center justify-between p-3 bg-gray-50 dark:bg-gray-800 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors cursor-pointer"
        @click="handleSearch(item)"
      >
        <div class="flex items-center gap-3 flex-1 min-w-0">
          <span class="material-symbols-outlined text-subtext-light dark:text-subtext-dark text-base flex-shrink-0">
            {{ getItemIcon(item) }}
          </span>
          <div class="flex-1 min-w-0">
            <div class="font-medium text-text-light dark:text-text-dark truncate">
              {{ getItemQuery(item) }}
            </div>
            <div class="flex items-center gap-2 text-xs text-subtext-light dark:text-subtext-dark mt-1">
              <span>{{ formatTimeAgo(getItemTimestamp(item)) }}</span>
              <span v-if="getItemCount(item) > 1" class="flex items-center gap-1">
                <span class="material-symbols-outlined text-xs">repeat</span>
                {{ getItemCount(item) }} lần
              </span>
            </div>
          </div>
        </div>
        <div class="flex items-center gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
          <button
            @click.stop="saveAsBookmark(item)"
            class="p-1.5 text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors"
            title="Lưu tìm kiếm"
          >
            <span class="material-symbols-outlined text-sm">bookmark_add</span>
          </button>
          <button
            @click.stop="removeItem(item)"
            class="p-1.5 text-subtext-light dark:text-subtext-dark hover:text-red-500 transition-colors"
            title="Xóa"
          >
            <span class="material-symbols-outlined text-sm">close</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Saved Searches -->
    <div v-if="showSaved && savedSearches.length > 0" class="space-y-2">
      <div
        v-for="(item, index) in savedSearches"
        :key="`saved-${index}`"
        class="group flex items-center justify-between p-3 bg-yellow-50 dark:bg-yellow-900/20 border border-yellow-200 dark:border-yellow-800 rounded-lg hover:bg-yellow-100 dark:hover:bg-yellow-900/30 transition-colors cursor-pointer"
        @click="handleSearch(item)"
      >
        <div class="flex items-center gap-3 flex-1 min-w-0">
          <span class="material-symbols-outlined text-yellow-600 dark:text-yellow-400 text-base flex-shrink-0 fill">
            bookmark
          </span>
          <div class="flex-1 min-w-0">
            <div class="font-medium text-text-light dark:text-text-dark truncate">
              {{ getItemQuery(item) }}
            </div>
            <div class="text-xs text-subtext-light dark:text-subtext-dark mt-1">
              Đã lưu {{ formatTimeAgo(getItemTimestamp(item)) }}
            </div>
          </div>
        </div>
        <button
          @click.stop="removeSavedItem(item)"
          class="p-1.5 text-subtext-light dark:text-subtext-dark hover:text-red-500 transition-colors opacity-0 group-hover:opacity-100"
          title="Bỏ lưu"
        >
          <span class="material-symbols-outlined text-sm">bookmark_remove</span>
        </button>
      </div>
    </div>

    <!-- Most Searched -->
    <div v-if="!showSaved && mostSearched.length > 0" class="mt-6">
      <h4 class="text-sm font-semibold text-text-light dark:text-text-dark mb-3 flex items-center gap-2">
        <span class="material-symbols-outlined text-primary text-base">trending_up</span>
        Tìm kiếm nhiều nhất
      </h4>
      <div class="flex flex-wrap gap-2">
        <button
          v-for="(item, index) in mostSearched"
          :key="`most-${index}`"
          @click="handleSearch(item)"
          class="px-3 py-1.5 bg-primary/10 dark:bg-primary/20 text-primary rounded-lg text-sm font-medium hover:bg-primary/20 dark:hover:bg-primary/30 transition-colors flex items-center gap-2"
        >
          <span class="material-symbols-outlined text-sm">search</span>
          {{ getItemQuery(item) }}
          <span class="text-xs bg-primary/20 dark:bg-primary/30 px-1.5 py-0.5 rounded">
            {{ getItemCount(item) }}
          </span>
        </button>
      </div>
    </div>

    <!-- Empty State -->
    <div
      v-if="(!showSaved && recentSearches.length === 0) || (showSaved && savedSearches.length === 0)"
      class="text-center py-8 text-subtext-light dark:text-subtext-dark"
    >
      <span class="material-symbols-outlined text-4xl mb-2 block">
        {{ showSaved ? 'bookmark_border' : 'history' }}
      </span>
      <p class="text-sm">
        {{ showSaved ? 'Chưa có tìm kiếm nào được lưu' : 'Chưa có lịch sử tìm kiếm' }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  recentSearches: {
    type: Array,
    default: () => []
  },
  savedSearches: {
    type: Array,
    default: () => []
  },
  mostSearched: {
    type: Array,
    default: () => []
  },
  formatTimeAgo: {
    type: Function,
    required: true
  }
});

const emit = defineEmits(['search', 'remove', 'save', 'remove-saved', 'clear']);

const showSaved = ref(false);

const getItemQuery = (item) => {
  return typeof item === 'string' ? item : item.query;
};

const getItemTimestamp = (item) => {
  return typeof item === 'string' ? Date.now() : (item.lastUsed || item.timestamp);
};

const getItemCount = (item) => {
  return typeof item === 'string' ? 1 : (item.count || 1);
};

const getItemIcon = (item) => {
  const count = getItemCount(item);
  if (count > 5) return 'trending_up';
  if (count > 1) return 'repeat';
  return 'history';
};

const handleSearch = (item) => {
  const query = getItemQuery(item);
  const filters = typeof item === 'object' ? item.filters : null;
  emit('search', { query, filters });
};

const removeItem = (item) => {
  const query = getItemQuery(item);
  emit('remove', query);
};

const saveAsBookmark = (item) => {
  const query = getItemQuery(item);
  const filters = typeof item === 'object' ? item.filters : null;
  emit('save', { query, filters });
};

const removeSavedItem = (item) => {
  const query = getItemQuery(item);
  emit('remove-saved', query);
};

const clearHistory = () => {
  if (confirm('Bạn có chắc muốn xóa toàn bộ lịch sử tìm kiếm?')) {
    emit('clear');
  }
};
</script>


