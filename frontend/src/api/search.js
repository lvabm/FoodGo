import axios from './axios';

/**
 * Advanced Search API
 */
export const searchApi = {
  /**
   * Advanced search với filters và relevance scoring
   */
  advancedSearch: async (request, page = 0, size = 20) => {
    const response = await axios.post('/search/advanced', request, {
      params: { page, size }
    });
    // Axios interceptor đã extract data, nên response đã là data rồi
    return response;
  },

  /**
   * Quick search với autocomplete
   */
  quickSearch: async (query, limit = 10) => {
    const response = await axios.get('/search/advanced/quick', {
      params: { q: query, limit }
    });
    // Axios interceptor đã extract data, nên response đã là data rồi
    return response;
  },

  /**
   * Lấy popular searches
   */
  getPopularSearches: async (limit = 10) => {
    const response = await axios.get('/search/advanced/popular', {
      params: { limit }
    });
    // Axios interceptor đã extract data, nên response đã là data rồi
    return response;
  },

  /**
   * Lấy search suggestions (từ SearchController cũ)
   */
  getSuggestions: async (query, limit = 5) => {
    const response = await axios.get('/search/suggestions', {
      params: { q: query, limit }
    });
    // Axios interceptor đã extract data, nên response đã là data rồi
    return response;
  },

  /**
   * Autocomplete search (từ SearchController cũ)
   */
  getAutocomplete: async (query, limit = 5) => {
    const response = await axios.get('/search/autocomplete', {
      params: { q: query, limit }
    });
    // Axios interceptor đã extract data, nên response đã là data rồi
    return response;
  }
};


