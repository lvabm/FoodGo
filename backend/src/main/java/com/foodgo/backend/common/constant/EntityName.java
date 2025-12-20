package com.foodgo.backend.common.constant;

public enum EntityName {
  // ----------------------
  // PHẦN ACCOUNT & AUTH
  // ----------------------
  USER_ACCOUNT("tài khoản người dùng"),
  PROFILE("hồ sơ cá nhân"),
  REFRESH_TOKEN("token làm mới"),
  PASSWORD_RESET_TOKEN("token đặt lại mật khẩu"),
  ROLE("vai trò"),
  PERMISSION("quyền hạn"),
  ROLE_PERMISSION("phân quyền"),

  // ----------------------
  // PHẦN SOCIAL & LISTS
  // ----------------------
  SHARING_LIST("danh sách chia sẻ"),
  SHARING_LIST_COLLABORATOR("cộng tác viên danh sách"),

  // ----------------------
  // PHẦN OUTLET (CỬA HÀNG)
  // ----------------------
  OUTLET("cửa hàng"),
  OUTLET_TYPE("loại cửa hàng"),
  OUTLET_CATEGORY("danh mục cửa hàng"),
  OUTLET_FEATURE("tiện ích cửa hàng"),
  OUTLET_FEATURE_MAPPING("liên kết tiện ích cửa hàng"),
  OUTLET_IMAGE("hình ảnh cửa hàng"),
  OPERATING_HOURS("giờ hoạt động"),

  // ----------------------
  // PHẦN MENU
  // ----------------------
  MENU_ITEM("món ăn/thức uống"),
  MENU_ITEM_TYPE("loại món ăn"),
  MENU_ITEM_CATEGORY("danh mục món ăn"),
  MENU_ITEM_SUB_CATEGORY("danh mục con món ăn"),
  OUTLET_MENU_ITEM("món ăn tại cửa hàng"),
  MENU_ITEM_FEATURE("tính năng món ăn"),
  OUTLET_MENU_ITEM_FEATURE("chi tiết tính năng món ăn"),

  // ----------------------
  // PHẦN BOOKING & PAYMENT
  // ----------------------
  BOOKING("đặt bàn"),
  PAYMENT("thanh toán"),

  // ----------------------
  // PHẦN REVIEW & NOTIFICATION
  // ----------------------
  REVIEW("đánh giá"),
  REVIEW_IMAGE("hình ảnh đánh giá"),
  REVIEW_REPLY("phản hồi đánh giá"),
  REVIEW_REACTION("thao tác đánh giá"),
  REVIEW_REPORT("báo cáo đánh giá"),
  NOTIFICATION("thông báo"),

  // ----------------------
  // PHẦN MEMBERSHIP & ADS
  // ----------------------
  MEMBERSHIP_PLAN("gói thành viên"),
  USER_MEMBERSHIP("thành viên người dùng"),
  ADVERTISEMENT("quảng cáo"),

  // ----------------------
  // PHẦN LOCATION
  // ----------------------
  COUNTRY("quốc gia"),
  PROVINCE("tỉnh/thành phố"),
  DISTRICT("quận/huyện");

  // ----------------------

  private final String friendlyName;

  EntityName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }
}
