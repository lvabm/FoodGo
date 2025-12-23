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

  // Outlet Management - Approve/Lock
  // Note: Backend OutletUpdateRequest doesn't have isActive field
  // These endpoints may need to be added to backend:
  // - PATCH /admin/outlets/{id}/approve
  // - PATCH /admin/outlets/{id}/lock
  // For now, using update endpoint (may not work until backend supports isActive)
  approveOutlet(id) {
    // TODO: Backend needs to add PATCH /admin/outlets/{id}/approve
    // or add isActive field to OutletUpdateRequest
    return apiClient.patch(`/outlets/${id}`, {isActive: true});
  },

  lockOutlet(id) {
    // TODO: Backend needs to add PATCH /admin/outlets/{id}/lock
    // or add isActive field to OutletUpdateRequest
    return apiClient.patch(`/outlets/${id}`, {isActive: false});
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
    return apiClient.post("/admin/outlet-categories", data);
  },

  updateCategory(id, data) {
    return apiClient.patch(`/admin/outlet-categories/${id}`, data);
  },

  deleteCategory(id) {
    return apiClient.delete(`/admin/outlet-categories/${id}`);
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

  // Menu Management (Admin)
  getMenuItems(params) {
    return apiClient.get("/menu-items", {params});
  },

  createMenuItem(data) {
    return apiClient.post("/admin/menu-items", data);
  },

  updateMenuItem(id, data) {
    return apiClient.patch(`/admin/menu-items/${id}`, data);
  },

  deleteMenuItem(id) {
    return apiClient.delete(`/admin/menu-items/${id}`);
  },

  toggleMenuItemStatus(id, isAvailable) {
    // Note: Backend may need separate endpoint for this
    return apiClient.patch(`/admin/menu-items/${id}`, {isAvailable});
  },

  // Order Management (Admin) - Using bookings
  getOrders(params) {
    return apiClient.get("/admin/bookings/search", {params});
  },

  getOrderDetail(id) {
    return apiClient.get(`/admin/bookings/${id}`);
  },

  forceCancelOrder(id) {
    return apiClient.delete(`/admin/bookings/${id}/force-cancel`);
  },

  // Review Management (Admin)
  getReviews(params) {
    return apiClient.get("/reviews/search", {params});
  },

  getReviewDetail(id) {
    return apiClient.get(`/admin/reviews/${id}`);
  },

  hideReview(id) {
    // Soft delete review (set isDeleted = true)
    return apiClient.delete(`/admin/reviews/${id}`);
  },

  showReview(id) {
    // Restore review (set isDeleted = false)
    return apiClient.patch(`/admin/reviews/${id}/restore`);
  },

  // Country Management (Admin)
  getCountries(params) {
    return apiClient.get("/countries", {params});
  },

  createCountry(data) {
    return apiClient.post("/admin/countries", data);
  },

  updateCountry(id, data) {
    return apiClient.patch(`/admin/countries/${id}`, data);
  },

  deleteCountry(id) {
    return apiClient.delete(`/admin/countries/${id}`);
  },
};

