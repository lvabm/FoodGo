package com.foodgo.backend.common.context;

import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import java.util.UUID;

public final class SecurityContext {

  // Ngăn chặn khởi tạo đối tượng (Utility class)
  private SecurityContext() {}

  private static Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal().equals("anonymousUser")) {
      throw new AccessDeniedException(
          "Quyền truy cập bị từ chối: Chưa đăng nhập hoặc token không hợp lệ.");
    }
    return authentication;
  }

  // Lấy đối tượng UserAccount đầy đủ từ SecurityContext. Đây là hàm nền tảng.
  public static UserAccount getCurrentUserAccount() {
    Authentication authentication = getAuthentication();
    Object principal = authentication.getPrincipal();

    // 🔑 Kiểm tra và ép kiểu: UserAccount chính là Principal của bạn
    if (principal instanceof UserAccount userAccount) {
      return userAccount;
    }

    // Trường hợp không mong muốn (Filter cấu hình sai)
    throw new IllegalStateException(
        "Đối tượng chính không phải là loại UserAccount mong muốn. Kiểm tra cấu hình Bộ lọc JWT.");
  }

  // 2. Hàm tiện ích: Lấy UUID ID (Được sử dụng thường xuyên nhất)
  public static UUID getCurrentUserId() {
    return getCurrentUserAccount().getId();
  }

  // 3. Hàm tiện ích: Lấy Username
  public static String getCurrentUsername() {
    return getCurrentUserAccount().getUsername();
  }

  // 4. Hàm tiện ích: Kiểm tra xem User có Role cụ thể không
  public static boolean hasRole(String roleName) {
    return getCurrentUserAccount().getAuthorities().stream()
        .anyMatch(
            a -> a.getAuthority().equalsIgnoreCase(roleName)); // Dùng equalsIgnoreCase để an toàn
  }

  // 🔑 HELPER THEO YÊU CẦU DỰ ÁN: Kiểm tra nhanh quyền Admin
  public static boolean isAdmin() {
    return hasRole(RoleType.ROLE_ADMIN.getName());
  }

  // 5. Hàm tiện ích: Lấy đối tượng UserDetails (nếu bạn muốn tương thích với các hàm Security
  // chuẩn)
  public static Optional<UserDetails> getCurrentUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof UserDetails userDetails) {
        return Optional.of(userDetails);
      }
    }
    return Optional.empty();
  }
}
