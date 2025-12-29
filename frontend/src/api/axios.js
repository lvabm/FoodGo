import axios from "axios";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1",
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Kiểm tra môi trường để quyết định có log hay không
const isDevelopment = import.meta.env.DEV || import.meta.env.VITE_APP_ENV === "development";

// Request interceptor - Thêm token vào header
apiClient.interceptors.request.use(
  (config) => {
    if (isDevelopment) {
      console.log("🚀 API Request:", config.method?.toUpperCase(), config.url);
      if (config.data) {
        console.log("🚀 Request data:", config.data);
      }
    }
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      if (isDevelopment) {
        console.log("🚀 Token added to header");
      }
    } else if (isDevelopment) {
      console.log("⚠️ No token found");
    }
    return config;
  },
  (error) => {
    if (isDevelopment) {
      console.error("❌ Request interceptor error:", error);
    }
    return Promise.reject(error);
  }
);

// Response interceptor - Xử lý lỗi chung
apiClient.interceptors.response.use(
  (response) => {
    if (isDevelopment) {
      console.log("✅ API Response:", response.config.url);
      console.log("✅ Status:", response.status);
    }

    // Backend có 3 loại response:
    // 1. BaseResponse<T>: { success, message, data: T, timestamp }
    // 2. PageResponse<T> extends BaseResponse<List<T>>: { pageNumber, pageSize, totalElements, totalPages, success, message, data: List<T>, timestamp }
    // 3. Spring Data Page<T>: { content: [...], totalElements, totalPages, ... } (trả về trực tiếp, không wrap)

    const responseData = response.data;

    // Nếu là Spring Data Page (có content array), trả về toàn bộ object
    if (responseData?.content && Array.isArray(responseData.content)) {
      if (isDevelopment) {
        console.log("📊 Spring Data Page detected, returning full object");
      }
      return responseData;
    }

    // Nếu là PageResponse (có pageNumber), trả về toàn bộ object
    if (
      responseData?.pageNumber !== undefined ||
      (responseData?.totalPages !== undefined && responseData?.data)
    ) {
      if (isDevelopment) {
        console.log("📊 PageResponse detected, returning full object");
      }
      return responseData;
    }

    // Nếu là SearchResultResponse (có results array và totalElements), trả về toàn bộ object
    if (responseData?.results !== undefined && responseData?.totalElements !== undefined) {
      if (isDevelopment) {
        console.log("🔍 SearchResultResponse detected, returning full object");
      }
      return responseData;
    }

    // Nếu là String response (từ ResponseEntity.ok(String)), trả về trực tiếp
    if (typeof responseData === 'string') {
      if (isDevelopment) {
        console.log("📝 String response detected, returning as is");
      }
      return responseData;
    }
    
    // Nếu là BaseResponse bình thường, extract data field
    const extractedData =
      responseData?.data !== undefined ? responseData.data : responseData;
    if (isDevelopment) {
      console.log("📦 BaseResponse detected, extracted data:", extractedData);
    }
    return extractedData;
  },
  (error) => {
    const url = error.config?.url || "";
    const isAdminApi = url.includes("/admin/");
    const status = error.response?.status;

    if (error.response) {
      // Server responded with error
      const {status, data} = error.response;
      
      // For admin APIs, 403/500 from permission issues are expected for non-admin users
      if (isAdminApi && (status === 403 || status === 500)) {
        if (isDevelopment) {
          console.log("ℹ️ Admin API access denied (expected for non-admin users):", url);
        }
      } else if (isDevelopment) {
        // Log other errors normally in development
        console.error("❌ API Error:", url);
        console.error("❌ Status:", status);
        console.error("❌ Response data:", data);
      }

      if (status === 401) {
        // Token expired or invalid
        if (isDevelopment) {
          console.error("❌ 401 Unauthorized - clearing tokens");
        }
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        
        // Only redirect to login if not on a public page
        // Public pages: home (/), search (/search), outlet detail (/outlet/:id), auth pages (/auth/*)
        const currentPath = window.location.pathname;
        const isPublicPage = 
          currentPath === "/" || 
          currentPath === "/search" || 
          currentPath.startsWith("/outlet/") ||
          currentPath.startsWith("/auth/");
        
        // Don't redirect if already on a public page or auth page
        if (!isPublicPage) {
          window.location.href = "/auth/login";
        }
      }

      // Backend error response structure: { success: false, message: string, data: any }
      const errorMessage = data?.message || data?.error || error.message;
      return Promise.reject(new Error(errorMessage));
    } else if (error.request) {
      // Request made but no response
      if (isDevelopment) {
        console.error("❌ No response from server");
      }
      return Promise.reject(new Error("Không thể kết nối đến server"));
    } else {
      // Something else happened
      if (isDevelopment) {
        console.error("❌ Unknown error:", error.message);
      }
      return Promise.reject(new Error(error.message));
    }
  }
);

export default apiClient;
