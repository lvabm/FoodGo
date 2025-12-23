import apiClient from "./axios";

export const adminApi = {
  // Dashboard Statistics
  getDashboardStats() {
    return apiClient.get("/admin/dashboard/stats");
  },

  // User Management
  getUsers(params) {
    return apiClient.get("/admin/user-accounts/search", {params});
  },

  getUserDetail(id) {
    return apiClient.get(`/admin/user-accounts/${id}`);
  },

  changeUserStatus(id, data) {
    return apiClient.patch(`/admin/user-accounts/${id}/status`, data);
  },

  assignUserRoles(id, data) {
    return apiClient.patch(`/admin/user-accounts/${id}/roles`, data);
  },

  deleteUser(id) {
    return apiClient.delete(`/admin/user-accounts/${id}`);
  },

  // Outlet Management
  getOutlets(params) {
    return apiClient.get("/outlets/search", {params});
  },

  getOutletDetail(id) {
    return apiClient.get(`/outlets/${id}`);
  },

  // Note: Backend doesn't have approve/lock endpoints yet
  // OutletUpdateRequest doesn't have isActive field
  // TODO: Add these endpoints in backend or add isActive to OutletUpdateRequest
  approveOutlet(id) {
    // For now, this will fail because OutletUpdateRequest doesn't support isActive
    // Backend needs to add this endpoint: PATCH /outlets/{id}/approve
    // Or add isActive field to OutletUpdateRequest
    return apiClient.patch(`/outlets/${id}`, {});
  },

  rejectOutlet(id, reason) {
    // Backend needs: PATCH /outlets/{id}/reject
    return apiClient.patch(`/outlets/${id}`, {});
  },

  lockOutlet(id) {
    // Backend needs: PATCH /outlets/{id}/lock or add isActive to update request
    // For now, trying to update with empty body (will fail)
    return apiClient.patch(`/outlets/${id}`, {});
  },

  unlockOutlet(id) {
    // Backend needs: PATCH /outlets/{id}/unlock
    return apiClient.patch(`/outlets/${id}`, {});
  },

  // Booking Management
  getBookings(params) {
    return apiClient.get("/admin/bookings/search", {params});
  },

  getBookingDetail(id) {
    return apiClient.get(`/admin/bookings/${id}`);
  },

  forceCancelBooking(id) {
    return apiClient.delete(`/admin/bookings/${id}/force-cancel`);
  },

  // Category Management
  getCategories(params) {
    return apiClient.get("/outlet-categories", {params});
  },

  createCategory(data) {
    return apiClient.post("/outlet-categories", data);
  },

  updateCategory(id, data) {
    return apiClient.patch(`/outlet-categories/${id}`, data);
  },

  deleteCategory(id) {
    return apiClient.delete(`/outlet-categories/${id}`);
  },

  // Outlet Type Management
  getOutletTypes(params) {
    return apiClient.get("/outlet-types", {params});
  },

  createOutletType(data) {
    return apiClient.post("/outlet-types", data);
  },

  updateOutletType(id, data) {
    return apiClient.patch(`/outlet-types/${id}`, data);
  },

  deleteOutletType(id) {
    return apiClient.delete(`/outlet-types/${id}`);
  },

  // Feature Management
  getFeatures(params) {
    return apiClient.get("/outlet-features", {params});
  },

  createFeature(data) {
    return apiClient.post("/outlet-features", data);
  },

  updateFeature(id, data) {
    return apiClient.patch(`/outlet-features/${id}`, data);
  },

  deleteFeature(id) {
    return apiClient.delete(`/outlet-features/${id}`);
  },

  // Membership Management
  getMembershipPlans(params) {
    return apiClient.get("/membership-plans/search", {params});
  },

  getMembershipPlanDetail(id) {
    return apiClient.get(`/membership-plans/${id}`);
  },

  createMembershipPlan(data) {
    return apiClient.post("/admin/membership-plans", data);
  },

  updateMembershipPlan(id, data) {
    return apiClient.patch(`/admin/membership-plans/${id}`, data);
  },

  deleteMembershipPlan(id) {
    return apiClient.delete(`/admin/membership-plans/${id}`);
  },

  // Reports
  getReports(params) {
    return apiClient.get("/admin/reports", {params});
  },

  getReportDetail(id) {
    return apiClient.get(`/admin/reports/${id}`);
  },

  // Transactions
  getTransactions(params) {
    return apiClient.get("/admin/transactions", {params});
  },
};

