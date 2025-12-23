import apiClient from "./axios";

export const locationApi = {
  // Countries
  getCountries() {
    return apiClient.get("/countries");
  },

  createCountry(data) {
    return apiClient.post("/countries", data);
  },

  updateCountry(id, data) {
    return apiClient.patch(`/countries/${id}`, data);
  },

  deleteCountry(id) {
    return apiClient.delete(`/countries/${id}`);
  },

  // Provinces
  getProvinces(countryId) {
    return apiClient.get("/provinces", {params: {countryId}});
  },

  createProvince(data) {
    return apiClient.post("/provinces", data);
  },

  updateProvince(id, data) {
    return apiClient.patch(`/provinces/${id}`, data);
  },

  deleteProvince(id) {
    return apiClient.delete(`/provinces/${id}`);
  },

  // Districts
  getDistricts(provinceId) {
    return apiClient.get("/districts", {params: {provinceId}});
  },

  createDistrict(data) {
    return apiClient.post("/districts", data);
  },

  updateDistrict(id, data) {
    return apiClient.patch(`/districts/${id}`, data);
  },

  deleteDistrict(id) {
    return apiClient.delete(`/districts/${id}`);
  },
};
