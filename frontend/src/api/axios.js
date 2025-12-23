import axios from "axios";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1",
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor - Thêm token vào header
apiClient.interceptors.request.use(
  (config) => {
    console.log("🚀 API Request:", config.method?.toUpperCase(), config.url);
    console.log("🚀 Request data:", config.data);
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log("🚀 Token added to header");
    } else {
      console.log("⚠️ No token found");
    }
    return config;
  },
  (error) => {
    console.error("❌ Request interceptor error:", error);
    return Promise.reject(error);
  }
);

// Response interceptor - Xử lý lỗi chung
apiClient.interceptors.response.use(
  (response) => {
    console.log("✅ API Response:", response.config.url);
    console.log("✅ Status:", response.status);
    console.log("✅ Raw response.data:", response.data);

    // Backend có 3 loại response:
    // 1. BaseResponse<T>: { success, message, data: T, timestamp }
    // 2. PageResponse<T> extends BaseResponse<List<T>>: { pageNumber, pageSize, totalElements, totalPages, success, message, data: List<T>, timestamp }
    // 3. Spring Data Page<T>: { content: [...], totalElements, totalPages, ... } (trả về trực tiếp, không wrap)

    const responseData = response.data;

    // Nếu là Spring Data Page (có content array), trả về toàn bộ object
    if (responseData?.content && Array.isArray(responseData.content)) {
      console.log("📊 Spring Data Page detected, returning full object");
      return responseData;
    }

    // Nếu là PageResponse (có pageNumber), trả về toàn bộ object
    if (
      responseData?.pageNumber !== undefined ||
      (responseData?.totalPages !== undefined && responseData?.data)
    ) {
      console.log("📊 PageResponse detected, returning full object");
      return responseData;
    }

    // Nếu là BaseResponse bình thường, extract data field
    const extractedData =
      responseData?.data !== undefined ? responseData.data : responseData;
    console.log("📦 BaseResponse detected, extracted data:", extractedData);
    return extractedData;
  },
  (error) => {
    const url = error.config?.url || "";
    const isAdminApi = url.includes("/admin/");
    const status = error.response?.status;

    // For admin APIs, 403/500 from permission issues are expected for non-admin users
    // Don't log them as errors to reduce console noise
    if (isAdminApi && (status === 403 || status === 500)) {
      // Silently handle permission errors for admin APIs
      console.log("ℹ️ Admin API access denied (expected for non-admin users):", url);
    } else {
      // Log other errors normally
      console.error("❌ API Error:", url);
      console.error("❌ Error details:", error.response || error);
    }

    if (error.response) {
      // Server responded with error
      const {status, data} = error.response;
      
      if (!isAdminApi || (status !== 403 && status !== 500)) {
        console.error("❌ Status:", status);
        console.error("❌ Response data:", data);
      }

      if (status === 401) {
        // Token expired or invalid
        console.error("❌ 401 Unauthorized - clearing tokens");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        window.location.href = "/auth/login";
      }

      // Backend error response structure: { success: false, message: string, data: any }
      const errorMessage = data?.message || data?.error || error.message;
      if (!isAdminApi || (status !== 403 && status !== 500)) {
        console.error("❌ Error message:", errorMessage);
      }
      return Promise.reject(new Error(errorMessage));
    } else if (error.request) {
      // Request made but no response
      console.error("❌ No response from server");
      return Promise.reject(new Error("Không thể kết nối đến server"));
    } else {
      // Something else happened
      console.error("❌ Unknown error:", error.message);
      return Promise.reject(new Error(error.message));
    }
  }
);

export default apiClient;
