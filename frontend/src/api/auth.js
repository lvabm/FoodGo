import apiClient from "./axios";

export const authApi = {
  // Đăng ký - Backend expects: { fullName, email, plainTextPassword, passwordConfirmation }
  register(data) {
    return apiClient.post("/auth/register", {
      fullName: data.name || data.fullName,
      email: data.email,
      plainTextPassword: data.password,
      passwordConfirmation: data.confirmPassword || data.password,
    });
  },

  // Đăng nhập - Backend expects: { email, plainTextPassword }
  login(data) {
    console.log("🔑 [AUTH API] login called with:", data);
    const payload = {
      email: data.emailOrPhone || data.email,
      plainTextPassword: data.password,
    };
    console.log("🔑 [AUTH API] Sending payload:", payload);
    return apiClient.post("/auth/login", payload);
  },

  // Refresh token
  refreshToken(refreshToken) {
    return apiClient.post("/auth/refresh", {refreshToken});
  },

  // Đăng xuất
  logout() {
    return apiClient.post("/auth/logout");
  },

  // Quên mật khẩu
  forgotPassword(email) {
    return apiClient.post("/auth/forgot-password", {email});
  },

  // Reset mật khẩu
  resetPassword(data) {
    return apiClient.post("/auth/reset-password", data);
  },
};
