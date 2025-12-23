import apiClient from "./axios";

export const membershipApi = {
  // Get membership plans (with filter and pagination)
  getMembershipPlans(params) {
    return apiClient.get("/membership-plans/search", {params});
  },

  // Get membership plan detail
  getMembershipPlanDetail(id) {
    return apiClient.get(`/membership-plans/${id}`);
  },

  // Subscribe to a membership plan (or upgrade)
  subscribeToPlan(planId) {
    console.log("🎯 Subscribing to plan:", planId);
    return apiClient.post(`/membership-plans/${planId}/subscribe`);
  },

  // Cancel current subscription
  cancelCurrentSubscription() {
    console.log("🚫 Cancelling current subscription");
    return apiClient.post("/membership-plans/current/cancel");
  },
};
