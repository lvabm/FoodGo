import apiClient from "./axios";

export const ownerApi = {
  // Get dashboard statistics for all outlets
  getDashboardStats() {
    return apiClient.get("/owner/dashboard/stats");
  },

  // Get dashboard statistics for a specific outlet
  getOutletStats(outletId) {
    return apiClient.get(`/owner/dashboard/stats/${outletId}`);
  },
};
