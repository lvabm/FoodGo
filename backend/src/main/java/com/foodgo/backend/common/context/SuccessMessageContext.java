package com.foodgo.backend.common.context;

/**
 * Sử dụng ThreadLocal để lưu trữ message thành công cho thread hiện tại (request). Giúp truyền
 * message từ Service đến Response Wrapper mà không làm thay đổi chữ ký phương thức.
 */
public final class SuccessMessageContext {
  public SuccessMessageContext() {}

  // Thông báo dùng chung (Chỉ cần nối chuỗi tên Entity đang thao tác)
  public static final String CREATE_SUCCESS = "Tạo mới %s thành công. ID: %s";
  public static final String FETCH_SUCCESS = "Lấy danh sách %s thành công.";
  public static final String UPDATE_SUCCESS = "Cập nhật %s thành công. ID: %s";
  public static final String SOFT_DELETE_SUCCESS = "Xóa %s thành công. ID: %s";
  public static final String FETCH_DETAIL_SUCCESS = "Lấy chi tiết %s thành công. ID: %s";

  // Thông báo đặc thù
  public static final String REGISTRATION_SUCCESS = "Đăng ký tài khoản thành công. ID: %s";
  public static final String LOGIN_SUCCESSFUL = "Đăng nhập tài khoản thành công. ID: %s";

  // ThreadLocal lưu trữ thông báo thành công
  private static final ThreadLocal<String> messageHolder = new ThreadLocal<>();

  public static void setMessage(String message) {
    if (message != null) {
      messageHolder.set(message);
    }
  }

  public static String getMessage() {
    return messageHolder.get();
  }

  /** PHẢI được gọi sau khi response đã được gửi đi để tránh rò rỉ bộ nhớ (memory leak). */
  public static void clear() {
    messageHolder.remove();
  }
}
