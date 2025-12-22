import {defineStore} from "pinia";
import {ref} from "vue";
import {outletApi} from "@/api";

export const useOutletStore = defineStore("outlet", () => {
  // State
  const outlets = ref([]);
  const currentOutlet = ref(null);
  const categories = ref([]);
  const types = ref([]);
  const features = ref([]);
  const isLoading = ref(false);
  const error = ref(null);
  const pagination = ref({
    page: 0,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });

  // Actions
  async function fetchOutlets(params = {}) {
    isLoading.value = true;
    error.value = null;
    try {
      // Backend returns PageResponse: { pageNumber, pageSize, totalElements, totalPages, success, message, data: List<Outlet>, timestamp }
      const response = await outletApi.getOutlets(params);

      // Data is in response.data (list of outlets)
      outlets.value = response.data || [];

      pagination.value = {
        page: response.pageNumber || 0,
        size: response.pageSize || 10,
        totalElements: response.totalElements || 0,
        totalPages: response.totalPages || 0,
      };

      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải danh sách địa điểm";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function fetchOutletDetail(id) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await outletApi.getOutletDetail(id);
      currentOutlet.value = response;
      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải chi tiết địa điểm";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function searchOutlets(params = {}) {
    isLoading.value = true;
    error.value = null;
    try {
      // searchOutlets also returns PageResponse
      const response = await outletApi.searchOutlets(params);
      outlets.value = response.data || [];

      pagination.value = {
        page: response.pageNumber || 0,
        size: response.pageSize || 10,
        totalElements: response.totalElements || 0,
        totalPages: response.totalPages || 0,
      };

      return response;
    } catch (err) {
      error.value = err.message || "Tìm kiếm thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function fetchCategories() {
    try {
      const response = await outletApi.getCategories();
      categories.value = response;
      return response;
    } catch (err) {
      console.error("Không thể tải danh mục:", err);
    }
  }

  async function fetchTypes() {
    try {
      const response = await outletApi.getTypes();
      types.value = response;
      return response;
    } catch (err) {
      console.error("Không thể tải loại địa điểm:", err);
    }
  }

  async function fetchFeatures() {
    try {
      const response = await outletApi.getFeatures();
      features.value = response;
      return response;
    } catch (err) {
      console.error("Không thể tải tính năng:", err);
    }
  }

  async function createOutlet(data) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await outletApi.createOutlet(data);
      outlets.value.unshift(response);
      return response;
    } catch (err) {
      error.value = err.message || "Tạo địa điểm thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function updateOutlet(id, data) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await outletApi.updateOutlet(id, data);
      const index = outlets.value.findIndex((o) => o.id === id);
      if (index !== -1) {
        outlets.value[index] = response;
      }
      if (currentOutlet.value?.id === id) {
        currentOutlet.value = response;
      }
      return response;
    } catch (err) {
      error.value = err.message || "Cập nhật địa điểm thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function deleteOutlet(id) {
    isLoading.value = true;
    error.value = null;
    try {
      await outletApi.deleteOutlet(id);
      outlets.value = outlets.value.filter((o) => o.id !== id);
    } catch (err) {
      error.value = err.message || "Xóa địa điểm thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  return {
    outlets,
    currentOutlet,
    categories,
    types,
    features,
    isLoading,
    error,
    pagination,
    fetchOutlets,
    fetchOutletDetail,
    searchOutlets,
    fetchCategories,
    fetchTypes,
    fetchFeatures,
    createOutlet,
    updateOutlet,
    deleteOutlet,
  };
});
