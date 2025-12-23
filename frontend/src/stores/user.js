import {defineStore} from "pinia";
import {ref} from "vue";
import {userApi} from "@/api";

export const useUserStore = defineStore("user", () => {
  // State
  const profile = ref(null);
  const users = ref([]);
  const isLoading = ref(false);
  const error = ref(null);

  // Actions
  async function fetchMyProfile() {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await userApi.getMyProfile();
      profile.value = response;
      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải thông tin cá nhân";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function updateMyProfile(data) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await userApi.updateMyProfile(data);
      profile.value = response;
      return response;
    } catch (err) {
      error.value = err.message || "Cập nhật thông tin thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function fetchUsers(params = {}) {
    isLoading.value = true;
    error.value = null;
    try {
      const response = await userApi.getUsers(params);
      users.value = response.content || response;
      return response;
    } catch (err) {
      error.value = err.message || "Không thể tải danh sách người dùng";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  return {
    profile,
    users,
    isLoading,
    error,
    fetchMyProfile,
    updateMyProfile,
    fetchUsers,
  };
});
