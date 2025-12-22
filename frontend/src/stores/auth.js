import {defineStore} from "pinia";
import {ref, computed} from "vue";
import {authApi} from "@/api";

export const useAuthStore = defineStore("auth", () => {
  // State
  const user = ref(null);
  const accessToken = ref(localStorage.getItem("accessToken") || null);
  const refreshToken = ref(localStorage.getItem("refreshToken") || null);
  const isLoading = ref(false);
  const error = ref(null);

  // Getters
  const isAuthenticated = computed(() => !!accessToken.value);
  const isAdmin = computed(
    () => user.value?.roleName === "ROLE_ADMIN" || user.value?.role === "ADMIN"
  );
  const isOwner = computed(
    () => user.value?.roleName === "ROLE_OWNER" || user.value?.role === "OWNER"
  );
  const isUser = computed(
    () => user.value?.roleName === "ROLE_USER" || user.value?.role === "USER"
  );

  // Actions
  async function login(credentials) {
    console.log("🟢 [AUTH STORE] login called with:", credentials);
    isLoading.value = true;
    error.value = null;
    try {
      // Backend returns: { accessToken, refreshToken, roles }
      console.log("🟢 Calling authApi.login...");
      const response = await authApi.login(credentials);
      console.log("🟢 Login response:", response);

      // Save tokens
      accessToken.value = response.accessToken;
      refreshToken.value = response.refreshToken;

      localStorage.setItem("accessToken", response.accessToken);
      localStorage.setItem("refreshToken", response.refreshToken);
      console.log("🟢 Tokens saved to localStorage");

      // Fetch user profile from /api/v1/profile/me
      console.log("🟢 Fetching user profile...");
      await fetchUserProfile();
      console.log("🟢 User profile fetched:", user.value);

      return response;
    } catch (err) {
      console.error("❌ [AUTH STORE] Login error:", err);
      error.value = err.message || "Đăng nhập thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  async function register(userData) {
    isLoading.value = true;
    error.value = null;
    try {
      // Backend returns: { accessToken, refreshToken, roles }
      const response = await authApi.register(userData);

      // Auto login after register
      accessToken.value = response.accessToken;
      refreshToken.value = response.refreshToken;

      localStorage.setItem("accessToken", response.accessToken);
      localStorage.setItem("refreshToken", response.refreshToken);

      // Fetch user profile from /api/v1/profile/me
      await fetchUserProfile();

      return response;
    } catch (err) {
      error.value = err.message || "Đăng ký thất bại";
      throw err;
    } finally {
      isLoading.value = false;
    }
  }

  function logout() {
    user.value = null;
    accessToken.value = null;
    refreshToken.value = null;

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("user");
  }

  async function refresh() {
    if (!refreshToken.value) return;

    try {
      const response = await authApi.refreshToken(refreshToken.value);
      accessToken.value = response.accessToken;
      localStorage.setItem("accessToken", response.accessToken);
    } catch (err) {
      logout();
      throw err;
    }
  }

  // Initialize user from localStorage
  function initializeAuth() {
    const storedToken = localStorage.getItem("accessToken");
    const storedUser = localStorage.getItem("user");

    if (storedToken && storedUser) {
      try {
        // Restore user from localStorage first for instant UI
        user.value = JSON.parse(storedUser);
      } catch (err) {
        console.error("Failed to parse stored user:", err);
        logout();
      }
    }
  }

  // Fetch user profile from backend
  async function fetchUserProfile() {
    console.log("🔵 fetchUserProfile called");
    try {
      console.log("🔵 Importing userApi...");
      const {userApi} = await import("@/api");
      console.log("🔵 Calling userApi.getMyProfile()...");
      const profile = await userApi.getMyProfile();
      console.log("✅ Profile received:", profile);
      user.value = profile;
      localStorage.setItem("user", JSON.stringify(profile));
      console.log("✅ User saved to store and localStorage");
      return profile;
    } catch (err) {
      console.error("❌ Fetch profile error:", err);
      throw err;
    }
  }

  return {
    user,
    accessToken,
    refreshToken,
    isLoading,
    error,
    isAuthenticated,
    isAdmin,
    isOwner,
    isUser,
    login,
    register,
    logout,
    refresh,
    initializeAuth,
    fetchUserProfile,
  };
});
