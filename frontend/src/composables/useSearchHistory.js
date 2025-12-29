import {ref, onMounted, computed} from "vue";

const SEARCH_HISTORY_KEY = "foodgo_search_history";
const SAVED_SEARCHES_KEY = "foodgo_saved_searches";
const MAX_HISTORY_ITEMS = 20; // Tăng lên 20 items
const MAX_SAVED_SEARCHES = 10;

/**
 * Composable for managing search history với timestamp và saved searches
 */
export function useSearchHistory() {
  const searchHistory = ref([]);
  const savedSearches = ref([]);
  const popularSearches = ref([
    "Cà phê",
    "Bún phở",
    "Trà sữa",
    "Ăn vặt",
    "Nhà hàng",
    "Quán ăn",
    "Đồ uống",
  ]);

  // Load search history from localStorage
  const loadHistory = () => {
    try {
      const stored = localStorage.getItem(SEARCH_HISTORY_KEY);
      if (stored) {
        const parsed = JSON.parse(stored);
        // Migrate old format (array of strings) to new format (array of objects)
        if (parsed.length > 0 && typeof parsed[0] === 'string') {
          searchHistory.value = parsed.map(query => ({
            query,
            timestamp: Date.now(),
            count: 1
          }));
          saveHistoryToStorage();
        } else {
          searchHistory.value = parsed;
        }
      }
    } catch (error) {
      console.error("Failed to load search history:", error);
      searchHistory.value = [];
    }
  };

  // Load saved searches
  const loadSavedSearches = () => {
    try {
      const stored = localStorage.getItem(SAVED_SEARCHES_KEY);
      if (stored) {
        savedSearches.value = JSON.parse(stored);
      }
    } catch (error) {
      console.error("Failed to load saved searches:", error);
      savedSearches.value = [];
    }
  };

  // Save history to localStorage
  const saveHistoryToStorage = () => {
    try {
      localStorage.setItem(SEARCH_HISTORY_KEY, JSON.stringify(searchHistory.value));
    } catch (error) {
      console.error("Failed to save search history:", error);
    }
  };

  // Save search to history với timestamp và count
  const saveSearch = (query, filters = null) => {
    if (!query || query.trim().length === 0) return;

    const trimmedQuery = query.trim();
    const now = Date.now();
    
    // Tìm item đã tồn tại
    const existingIndex = searchHistory.value.findIndex(
      (item) => item.query.toLowerCase() === trimmedQuery.toLowerCase()
    );

    if (existingIndex >= 0) {
      // Update existing item
      const existing = searchHistory.value[existingIndex];
      searchHistory.value.splice(existingIndex, 1);
      searchHistory.value.unshift({
        query: trimmedQuery,
        timestamp: now,
        count: (existing.count || 1) + 1,
        lastUsed: now,
        filters: filters || existing.filters
      });
    } else {
      // Add new item
      searchHistory.value.unshift({
        query: trimmedQuery,
        timestamp: now,
        count: 1,
        lastUsed: now,
        filters: filters
      });
    }

    // Limit to MAX_HISTORY_ITEMS
    if (searchHistory.value.length > MAX_HISTORY_ITEMS) {
      searchHistory.value = searchHistory.value.slice(0, MAX_HISTORY_ITEMS);
    }

    saveHistoryToStorage();
  };

  // Clear search history
  const clearHistory = () => {
    searchHistory.value = [];
    try {
      localStorage.removeItem(SEARCH_HISTORY_KEY);
    } catch (error) {
      console.error("Failed to clear search history:", error);
    }
  };

  // Remove single item from history
  const removeFromHistory = (query) => {
    searchHistory.value = searchHistory.value.filter(
      (item) => {
        const itemQuery = typeof item === 'string' ? item : item.query;
        return itemQuery.toLowerCase() !== query.toLowerCase();
      }
    );
    saveHistoryToStorage();
  };

  // Save search (bookmark)
  const saveSearchAsBookmark = (query, filters = null) => {
    if (!query || query.trim().length === 0) return;

    const trimmedQuery = query.trim();
    const now = Date.now();

    // Check if already saved
    const exists = savedSearches.value.some(
      (item) => item.query.toLowerCase() === trimmedQuery.toLowerCase()
    );

    if (!exists) {
      savedSearches.value.unshift({
        query: trimmedQuery,
        timestamp: now,
        filters: filters
      });

      // Limit to MAX_SAVED_SEARCHES
      if (savedSearches.value.length > MAX_SAVED_SEARCHES) {
        savedSearches.value = savedSearches.value.slice(0, MAX_SAVED_SEARCHES);
      }

      try {
        localStorage.setItem(SAVED_SEARCHES_KEY, JSON.stringify(savedSearches.value));
      } catch (error) {
        console.error("Failed to save search bookmark:", error);
      }
    }
  };

  // Remove saved search
  const removeSavedSearch = (query) => {
    savedSearches.value = savedSearches.value.filter(
      (item) => item.query.toLowerCase() !== query.toLowerCase()
    );
    try {
      localStorage.setItem(SAVED_SEARCHES_KEY, JSON.stringify(savedSearches.value));
    } catch (error) {
      console.error("Failed to remove saved search:", error);
    }
  };

  // Get recent searches (sorted by lastUsed)
  const recentSearches = computed(() => {
    return [...searchHistory.value]
      .sort((a, b) => (b.lastUsed || b.timestamp) - (a.lastUsed || a.timestamp))
      .slice(0, 10);
  });

  // Get most searched (sorted by count)
  const mostSearched = computed(() => {
    return [...searchHistory.value]
      .filter(item => (item.count || 1) > 1)
      .sort((a, b) => (b.count || 1) - (a.count || 1))
      .slice(0, 10);
  });

  // Get suggestions based on query
  const getSuggestions = (query) => {
    if (!query || query.trim().length === 0) {
      return {
        history: recentSearches.value.slice(0, 5).map(item => typeof item === 'string' ? item : item.query),
        popular: popularSearches.value.slice(0, 5),
      };
    }

    const lowerQuery = query.toLowerCase();
    
    // Filter history and popular searches
    const historyMatches = searchHistory.value
      .filter((item) => {
        const itemQuery = typeof item === 'string' ? item : item.query;
        return itemQuery.toLowerCase().includes(lowerQuery);
      })
      .map(item => typeof item === 'string' ? item : item.query);
    
    const popularMatches = popularSearches.value.filter((item) =>
      item.toLowerCase().includes(lowerQuery) &&
      !historyMatches.some((h) => h.toLowerCase() === item.toLowerCase())
    );

    return {
      history: historyMatches.slice(0, 5),
      popular: popularMatches.slice(0, 5),
    };
  };

  // Format timestamp to relative time
  const formatTimeAgo = (timestamp) => {
    if (!timestamp) return '';
    const now = Date.now();
    const diff = now - timestamp;
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);

    if (minutes < 1) return 'Vừa xong';
    if (minutes < 60) return `${minutes} phút trước`;
    if (hours < 24) return `${hours} giờ trước`;
    if (days < 7) return `${days} ngày trước`;
    return new Date(timestamp).toLocaleDateString('vi-VN');
  };

  onMounted(() => {
    loadHistory();
    loadSavedSearches();
  });

  return {
    searchHistory,
    savedSearches,
    recentSearches,
    mostSearched,
    popularSearches,
    saveSearch,
    clearHistory,
    removeFromHistory,
    saveSearchAsBookmark,
    removeSavedSearch,
    getSuggestions,
    formatTimeAgo,
    loadHistory,
    loadSavedSearches,
  };
}

