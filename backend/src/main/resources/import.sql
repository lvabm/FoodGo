--========================================================
-- FoodGo V2 - Sample Data Import (Fixed Structure)
-- Converted from foodgo_import_sql.sql to match import.sql structure
--
-- NOTE: This is a DATA IMPORT file, not a schema definition file.
-- Table definitions are created automatically by Hibernate/JPA from
-- the entity classes (e.g., Country.java, Province.java, etc.).
-- The IDE may show "Unable to resolve table" warnings, but these are
-- harmless - the tables exist at runtime when this script executes.
--========================================================

--========================================================
--1. COUNTRY
--========================================================
-- Using data from foodgo_import_sql.sql, removing duplicates
INSERT INTO country (id, name, code) VALUES (1, 'Việt Nam', 'VN');
INSERT INTO country (id, name, code) VALUES (2, 'Nhật Bản', 'JP');
INSERT INTO country (id, name, code) VALUES (3, 'Hàn Quốc', 'KR');
INSERT INTO country (id, name, code) VALUES (4, 'Trung Quốc', 'CN');
INSERT INTO country (id, name, code) VALUES (5, 'Thái Lan', 'TH');
INSERT INTO country (id, name, code) VALUES (6, 'Ấn Độ', 'IN');
INSERT INTO country (id, name, code) VALUES (7, 'Pháp', 'FR');
INSERT INTO country (id, name, code) VALUES (8, 'Ý', 'IT');
INSERT INTO country (id, name, code) VALUES (9, 'Mỹ (Hoa Kỳ)', 'US');
INSERT INTO country (id, name, code) VALUES (10, 'Đức', 'DE');
INSERT INTO country (id, name, code) VALUES (11, 'Mexico', 'MX');
INSERT INTO country (id, name, code) VALUES (12, 'Tây Ban Nha', 'ES');
INSERT INTO country (id, name, code) VALUES (13, 'Anh (Vương quốc Liên hiệp Anh)', 'GB');
INSERT INTO country (id, name, code) VALUES (14, 'Úc', 'AU');
INSERT INTO country (id, name, code) VALUES (15, 'Brazil', 'BR');
INSERT INTO country (id, name, code) VALUES (16, 'Argentina', 'AR');
INSERT INTO country (id, name, code) VALUES (17, 'Nga', 'RU');
INSERT INTO country (id, name, code) VALUES (18, 'Hy Lạp', 'GR');
INSERT INTO country (id, name, code) VALUES (19, 'Thổ Nhĩ Kỳ', 'TR');
INSERT INTO country (id, name, code) VALUES (20, 'Ai Cập', 'EG');
INSERT INTO country (id, name, code) VALUES (21, 'Maroc', 'MA');
INSERT INTO country (id, name, code) VALUES (22, 'Nam Phi', 'ZA');
INSERT INTO country (id, name, code) VALUES (23, 'Indonesia', 'ID');
INSERT INTO country (id, name, code) VALUES (24, 'Malaysia', 'MY');
INSERT INTO country (id, name, code) VALUES (25, 'Singapore', 'SG');
INSERT INTO country (id, name, code) VALUES (26, 'Philippines', 'PH');
INSERT INTO country (id, name, code) VALUES (27, 'Lào', 'LA');
INSERT INTO country (id, name, code) VALUES (28, 'Campuchia', 'KH');
INSERT INTO country (id, name, code) VALUES (29, 'Myanmar', 'MM');
INSERT INTO country (id, name, code) VALUES (30, 'Nepal', 'NP');
INSERT INTO country (id, name, code) VALUES (31, 'Sri Lanka', 'LK');
INSERT INTO country (id, name, code) VALUES (32, 'Pakistan', 'PK');
INSERT INTO country (id, name, code) VALUES (33, 'Bangladesh', 'BD');
INSERT INTO country (id, name, code) VALUES (34, 'Iran', 'IR');
INSERT INTO country (id, name, code) VALUES (35, 'Iraq', 'IQ');
INSERT INTO country (id, name, code) VALUES (36, 'Ả Rập Saudi', 'SA');
INSERT INTO country (id, name, code) VALUES (37, 'Israel', 'IL');
INSERT INTO country (id, name, code) VALUES (38, 'Jordan', 'JO');
INSERT INTO country (id, name, code) VALUES (39, 'Palestine', 'PS');
INSERT INTO country (id, name, code) VALUES (40, 'Syria', 'SY');
INSERT INTO country (id, name, code) VALUES (41, 'Kazakhstan', 'KZ');
INSERT INTO country (id, name, code) VALUES (42, 'Uzbekistan', 'UZ');
INSERT INTO country (id, name, code) VALUES (43, 'Azerbaijan', 'AZ');
INSERT INTO country (id, name, code) VALUES (44, 'Gruzia (Georgia)', 'GE');
INSERT INTO country (id, name, code) VALUES (45, 'Armenia', 'AM');
INSERT INTO country (id, name, code) VALUES (46, 'Ukraina', 'UA');
INSERT INTO country (id, name, code) VALUES (47, 'Ba Lan', 'PL');
INSERT INTO country (id, name, code) VALUES (48, 'Hungary', 'HU');
INSERT INTO country (id, name, code) VALUES (49, 'Cộng hòa Séc', 'CZ');
INSERT INTO country (id, name, code) VALUES (50, 'Slovakia', 'SK');
INSERT INTO country (id, name, code) VALUES (51, 'Áo', 'AT');
INSERT INTO country (id, name, code) VALUES (52, 'Thụy Sĩ', 'CH');
INSERT INTO country (id, name, code) VALUES (53, 'Hà Lan', 'NL');
INSERT INTO country (id, name, code) VALUES (54, 'Na Uy', 'NO');
INSERT INTO country (id, name, code) VALUES (55, 'Thụy Điển', 'SE');
INSERT INTO country (id, name, code) VALUES (56, 'Phần Lan', 'FI');
INSERT INTO country (id, name, code) VALUES (57, 'Đan Mạch', 'DK');
INSERT INTO country (id, name, code) VALUES (58, 'Iceland', 'IS');
INSERT INTO country (id, name, code) VALUES (59, 'Ireland', 'IE');
INSERT INTO country (id, name, code) VALUES (60, 'Croatia', 'HR');
INSERT INTO country (id, name, code) VALUES (61, 'Serbia', 'RS');
INSERT INTO country (id, name, code) VALUES (62, 'Romania', 'RO');
INSERT INTO country (id, name, code) VALUES (63, 'Bulgaria', 'BG');
INSERT INTO country (id, name, code) VALUES (64, 'Albania', 'AL');
INSERT INTO country (id, name, code) VALUES (65, 'Estonia', 'EE');
INSERT INTO country (id, name, code) VALUES (66, 'Latvia', 'LV');
INSERT INTO country (id, name, code) VALUES (67, 'Litva', 'LT');
INSERT INTO country (id, name, code) VALUES (68, 'Peru', 'PE');
INSERT INTO country (id, name, code) VALUES (69, 'Chile', 'CL');
INSERT INTO country (id, name, code) VALUES (70, 'Colombia', 'CO');
INSERT INTO country (id, name, code) VALUES (71, 'Venezuela', 'VE');
INSERT INTO country (id, name, code) VALUES (72, 'Ecuador', 'EC');
INSERT INTO country (id, name, code) VALUES (73, 'Bolivia', 'BO');
INSERT INTO country (id, name, code) VALUES (74, 'Paraguay', 'PY');
INSERT INTO country (id, name, code) VALUES (75, 'Uruguay', 'UY');

--========================================================
--2. PROVINCE
--========================================================
INSERT INTO province (id, country_id, name) VALUES (1, (SELECT id FROM country WHERE code = 'VN'), 'Hồ Chí Minh');
INSERT INTO province (id, country_id, name) VALUES (2, (SELECT id FROM country WHERE code = 'VN'), 'Hà Nội');
INSERT INTO province (id, country_id, name) VALUES (3, (SELECT id FROM country WHERE code = 'VN'), 'Đà Nẵng');
INSERT INTO province (id, country_id, name) VALUES (4, (SELECT id FROM country WHERE code = 'VN'), 'Long An');
INSERT INTO province (id, country_id, name) VALUES (5, (SELECT id FROM country WHERE code = 'VN'), 'Thừa Thiên Huế');

--========================================================
--3. DISTRICT
--========================================================

--* Hồ Chí Minh *--
INSERT INTO district (id, province_id, name) VALUES (1, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 1');
INSERT INTO district (id, province_id, name) VALUES (2, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 2');
INSERT INTO district (id, province_id, name) VALUES (3, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 3');
INSERT INTO district (id, province_id, name) VALUES (4, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 4');
INSERT INTO district (id, province_id, name) VALUES (5, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 5');
INSERT INTO district (id, province_id, name) VALUES (6, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 6');
INSERT INTO district (id, province_id, name) VALUES (7, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 7');
INSERT INTO district (id, province_id, name) VALUES (8, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 8');
INSERT INTO district (id, province_id, name) VALUES (9, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 9');
INSERT INTO district (id, province_id, name) VALUES (10, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 10');
INSERT INTO district (id, province_id, name) VALUES (11, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 11');
INSERT INTO district (id, province_id, name) VALUES (12, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận 12');
INSERT INTO district (id, province_id, name) VALUES (13, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Bình Tân');
INSERT INTO district (id, province_id, name) VALUES (14, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Bình Thạnh');
INSERT INTO district (id, province_id, name) VALUES (15, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Tân Bình');
INSERT INTO district (id, province_id, name) VALUES (16, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Tân Phú');
INSERT INTO district (id, province_id, name) VALUES (17, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Gò Vấp');
INSERT INTO district (id, province_id, name) VALUES (18, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Quận Phú Nhuận');
INSERT INTO district (id, province_id, name) VALUES (19, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Huyện Bình Chánh');
INSERT INTO district (id, province_id, name) VALUES (20, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Huyện Nhà Bè');
INSERT INTO district (id, province_id, name) VALUES (21, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Huyện Hóc Môn');
INSERT INTO district (id, province_id, name) VALUES (22, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Huyện Củ Chi');
INSERT INTO district (id, province_id, name) VALUES (23, (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Huyện Cần Giờ');

--* Hà Nội *--
INSERT INTO district (id, province_id, name) VALUES (24, (SELECT id FROM province WHERE name = 'Hà Nội'), 'Quận Hoàn Kiếm');
INSERT INTO district (id, province_id, name) VALUES (25, (SELECT id FROM province WHERE name = 'Hà Nội'), 'Quận Ba Đình');
INSERT INTO district (id, province_id, name) VALUES (26, (SELECT id FROM province WHERE name = 'Hà Nội'), 'Quận Đống Đa');
INSERT INTO district (id, province_id, name) VALUES (27, (SELECT id FROM province WHERE name = 'Hà Nội'), 'Quận Cầu Giấy');
INSERT INTO district (id, province_id, name) VALUES (28, (SELECT id FROM province WHERE name = 'Hà Nội'), 'Quận Nam Từ Liêm');

--* Đà Nẵng *--
INSERT INTO district (id, province_id, name) VALUES (29, (SELECT id FROM province WHERE name = 'Đà Nẵng'), 'Quận Hải Châu');
INSERT INTO district (id, province_id, name) VALUES (30, (SELECT id FROM province WHERE name = 'Đà Nẵng'), 'Quận Sơn Trà');
INSERT INTO district (id, province_id, name) VALUES (31, (SELECT id FROM province WHERE name = 'Đà Nẵng'), 'Quận Ngũ Hành Sơn');
INSERT INTO district (id, province_id, name) VALUES (32, (SELECT id FROM province WHERE name = 'Đà Nẵng'), 'Quận Thanh Khê');

--========================================================
--4. OUTLET_TYPE
--========================================================
INSERT INTO outlet_type (id, name, description) VALUES (1, 'Restaurant', 'Nhà hàng phục vụ các bữa ăn chính');
INSERT INTO outlet_type (id, name, description) VALUES (2, 'Cafe', 'Quán cà phê phục vụ các loại thức uống');
INSERT INTO outlet_type (id, name, description) VALUES (3, 'Bar', 'Quán bar phục vụ đồ uống có cồn');
INSERT INTO outlet_type (id, name, description) VALUES (4, 'Cafe Bar', 'Kết hợp cafe và bar');
INSERT INTO outlet_type (id, name, description) VALUES (5, 'Pub', 'Quán rượu kiểu Anh');
INSERT INTO outlet_type (id, name, description) VALUES (6, 'Lounge Bar', 'Bar cao cấp không gian sang trọng');
INSERT INTO outlet_type (id, name, description) VALUES (7, 'Street Food', 'Quán ăn vỉa hè');
INSERT INTO outlet_type (id, name, description) VALUES (8, 'Food Court', 'Khu ẩm thực tập trung');
INSERT INTO outlet_type (id, name, description) VALUES (9, 'Bistro', 'Nhà hàng nhỏ phong cách Pháp');
INSERT INTO outlet_type (id, name, description) VALUES (10, 'Cafe Take Away', 'Cafe mang đi');
INSERT INTO outlet_type (id, name, description) VALUES (11, 'Buffet Restaurant', 'Nhà hàng buffet');
INSERT INTO outlet_type (id, name, description) VALUES (12, 'Fine Dining', 'Nhà hàng cao cấp');
INSERT INTO outlet_type (id, name, description) VALUES (13, 'Tea House', 'Quán trà');
INSERT INTO outlet_type (id, name, description) VALUES (14, 'Bakery Cafe', 'Cafe kết hợp tiệm bánh');
INSERT INTO outlet_type (id, name, description) VALUES (15, 'Rooftop Bar', 'Bar trên tầng thượng');

--========================================================
--5. OUTLET_CATEGORY
--========================================================
INSERT INTO outlet_category (id, type_id, name, description) VALUES (1, (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Vietnamese', 'Món ăn Việt Nam');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (2, (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Asian', 'Món ăn Châu Á');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (3, (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Western', 'Món ăn phương Tây (Âu, Mỹ)');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (4, (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Korean BBQ', 'Nhà hàng thịt nướng Hàn Quốc');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (5, (SELECT id FROM outlet_type WHERE name = 'Fine Dining'), 'French Cuisine', 'Ẩm thực Pháp cao cấp');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (6, (SELECT id FROM outlet_type WHERE name = 'Buffet Restaurant'), 'Seafood Buffet', 'Buffet hải sản');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (7, (SELECT id FROM outlet_type WHERE name = 'Bar'), 'Cocktail Bar', 'Quán bar chuyên về cocktail');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (8, (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Specialty Coffee', 'Cà phê đặc sản, chú trọng chất lượng hạt');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (9, (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Coffee Shop', 'Cà phê là món chính');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (10, (SELECT id FROM outlet_type WHERE name = 'Cafe Take Away'), 'Smoothies & Juices', 'Nước ép và sinh tố');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (11, (SELECT id FROM outlet_type WHERE name = 'Rooftop Bar'), 'Wine & Cigar', 'Rượu vang và xì gà');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (12, (SELECT id FROM outlet_type WHERE name = 'Tea House'), 'Traditional Tea', 'Trà đạo, trà truyền thống');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (13, (SELECT id FROM outlet_type WHERE name = 'Street Food'), 'Noodle Soup', 'Các món bún, phở, miến');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (14, (SELECT id FROM outlet_type WHERE name = 'Food Court'), 'Fast Food', 'Đồ ăn nhanh (Burger, Pizza)');
INSERT INTO outlet_category (id, type_id, name, description) VALUES (15, (SELECT id FROM outlet_type WHERE name = 'Bakery Cafe'), 'Pastries', 'Các loại bánh ngọt và bánh mì');

--========================================================
--6. MENU_ITEM_TYPE (Level 1): Chung nhất
--========================================================
INSERT INTO menu_item_type (id, name, description) VALUES (1, 'Món chính', 'Món ăn chính');
INSERT INTO menu_item_type (id, name, description) VALUES (2, 'Món phụ', 'Món ăn phụ');
INSERT INTO menu_item_type (id, name, description) VALUES (3, 'Đồ uống', 'Nước uống');
INSERT INTO menu_item_type (id, name, description) VALUES (4, 'Tráng miệng', 'Món tráng miệng');
INSERT INTO menu_item_type (id, name, description) VALUES (5, 'Ăn vặt', 'Đồ ăn vặt');

--========================================================
--7. MENU_ITEM_CATEGORY (Level 2): Loại chi tiết hơn
--========================================================
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (1, (SELECT id FROM menu_item_type WHERE name = 'Món chính'), 'Món Nước', 'Các món ăn có nước dùng');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (2, (SELECT id FROM menu_item_type WHERE name = 'Món chính'), 'Món Khô', 'Các món ăn khô, cơm, xôi');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (3, (SELECT id FROM menu_item_type WHERE name = 'Đồ uống'), 'Nước Giải Khát', 'Đồ uống có đường, không cồn');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (4, (SELECT id FROM menu_item_type WHERE name = 'Đồ uống'), 'Cà Phê & Trà', 'Cà phê và các loại trà');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (5, (SELECT id FROM menu_item_type WHERE name = 'Tráng miệng'), 'Bánh Ngọt Âu Á', 'Các loại bánh tráng miệng');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (6, (SELECT id FROM menu_item_type WHERE name = 'Món chính'), 'Bún/Phở', 'Các món bún, phở truyền thống');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (7, (SELECT id FROM menu_item_type WHERE name = 'Đồ uống'), 'Cà phê', 'Các loại cà phê Việt Nam và thế giới');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (8, (SELECT id FROM menu_item_type WHERE name = 'Đồ uống'), 'Trà sữa', 'Trà sữa và các loại đồ uống trà');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (9, (SELECT id FROM menu_item_type WHERE name = 'Ăn vặt'), 'Ăn vặt', 'Các món ăn vặt, snack');

--========================================================
--8. MENU_ITEM_SUB_CATEGORY (Level 3): Các món hiện hữu
--========================================================
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (1, (SELECT id FROM menu_item_category WHERE name = 'Món Nước'), 'Phở', 'Phở - Vietnamese noodle soup');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (2, (SELECT id FROM menu_item_category WHERE name = 'Món Nước'), 'Mì/Hủ Tiếu', 'Các món Mì/Hủ Tiếu nước');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (3, (SELECT id FROM menu_item_category WHERE name = 'Nước Giải Khát'), 'Nước Ngọt Có Ga', 'Đồ uống giải khát đóng chai/lon có ga');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (4, (SELECT id FROM menu_item_category WHERE name = 'Cà Phê & Trà'), 'Cà Phê Truyền Thống', 'Các loại cà phê Việt Nam');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (5, (SELECT id FROM menu_item_category WHERE name = 'Món Khô'), 'Cơm Chiên', 'Các loại cơm chiên');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (6, (SELECT id FROM menu_item_category WHERE name = 'Món Khô'), 'Xôi/Cơm Gói', 'Các món Xôi hoặc Cơm được gói lại (ví dụ: Cơm cuộn, Xôi mặn)');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (7, (SELECT id FROM menu_item_category WHERE name = 'Nước Giải Khát'), 'Trà Sữa', 'Đồ uống trà sữa');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (8, (SELECT id FROM menu_item_category WHERE name = 'Bánh Ngọt Âu Á'), 'Bánh Kem Mousse', 'Bánh ngọt mềm, lạnh');
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (9, (SELECT id FROM menu_item_category WHERE name = 'Cà Phê & Trà'), 'Trà Trái Cây', 'Các loại trà có hương vị trái cây');

--========================================================
--10. ROLE
--========================================================
INSERT INTO role (id, name, description) VALUES (1, 'ROLE_USER', 'Người dùng');
INSERT INTO role (id, name, description) VALUES (2, 'ROLE_OWNER', 'Chủ quán');
INSERT INTO role (id, name, description) VALUES (3, 'ROLE_ADMIN', 'Quản trị viên');
INSERT INTO role (id, name, description) VALUES (4, 'ROLE_SYSTEM_ADMIN', 'Quản trị hệ thống');
INSERT INTO role (id, name, description) VALUES (5, 'ROLE_GUEST', 'Khách vãng lai');

--========================================================
--11. PERMISSION (Chuẩn Authorization/RBAC = Resource:Action)
--========================================================
-- 1. Quyền Quản lý Tài khoản (User/Profile/Auth)
INSERT INTO permission (id, name, description) VALUES (1, 'AUTH:MANAGE_TOKEN', 'Quản lý Refresh Token (Revoke, List) của chính mình');
INSERT INTO permission (id, name, description) VALUES (2, 'USER:READ_SELF', 'Xem chi tiết hồ sơ cá nhân của chính mình (Profile)');
INSERT INTO permission (id, name, description) VALUES (3, 'USER:UPDATE_SELF', 'Cập nhật thông tin hồ sơ cá nhân của chính mình (Profile)');

-- 2. Quyền Quản lý Outlet (Owner Permissions)
INSERT INTO permission (id, name, description) VALUES (10, 'OUTLET:CREATE', 'Đăng ký / Tạo mới một Outlet');
INSERT INTO permission (id, name, description) VALUES (11, 'OUTLET:READ_OWN', 'Xem chi tiết Outlet do mình sở hữu');
INSERT INTO permission (id, name, description) VALUES (12, 'OUTLET:UPDATE_OWN', 'Cập nhật thông tin chi tiết Outlet do mình sở hữu');
INSERT INTO permission (id, name, description) VALUES (13, 'OUTLET:ACTIVATE_OWN', 'Kích hoạt/Vô hiệu hóa (Active/Deactive) Outlet do mình sở hữu');
INSERT INTO permission (id, name, description) VALUES (14, 'OUTLET_IMAGE:MANAGE_OWN', 'Thêm/Xóa/Cập nhật ảnh của Outlet do mình sở hữu');
INSERT INTO permission (id, name, description) VALUES (15, 'OPERATING_HOURS:MANAGE_OWN', 'Thiết lập giờ hoạt động của Outlet do mình sở hữu');

-- 3. Quyền Quản lý Menu (Owner Permissions)
INSERT INTO permission (id, name, description) VALUES (20, 'MENU_ITEM:CREATE_OWN', 'Thêm mới món ăn/thức uống vào Menu của Outlet');
INSERT INTO permission (id, name, description) VALUES (21, 'MENU_ITEM:READ_OWN', 'Xem chi tiết Menu Item của Outlet do mình sở hữu');
INSERT INTO permission (id, name, description) VALUES (22, 'MENU_ITEM:UPDATE_OWN', 'Cập nhật thông tin/giá/trạng thái của Menu Item trong Outlet');
INSERT INTO permission (id, name, description) VALUES (23, 'MENU_ITEM:DELETE_OWN', 'Xóa Menu Item khỏi Outlet');
INSERT INTO permission (id, name, description) VALUES (24, 'MENU_FEATURE:MANAGE_OWN', 'Quản lý các thuộc tính/feature của Menu Item (tags, attributes)');

-- 4. Quản lý Đặt bàn & Thanh toán (Booking & Payment)
INSERT INTO permission (id, name, description) VALUES (30, 'BOOKING:CREATE', 'Tạo yêu cầu đặt bàn (Booking) mới');
INSERT INTO permission (id, name, description) VALUES (31, 'BOOKING:CANCEL_SELF', 'Hủy Booking do chính mình tạo');
INSERT INTO permission (id, name, description) VALUES (32, 'BOOKING:READ_OWN', 'Xem danh sách Booking đến Outlet do mình sở hữu (Owner)');
INSERT INTO permission (id, name, description) VALUES (33, 'BOOKING:UPDATE_STATUS_OWN', 'Cập nhật trạng thái Booking (Confirm, Cancel, No_Show) (Owner)');
INSERT INTO permission (id, name, description) VALUES (34, 'PAYMENT:PROCESS', 'Thực hiện thanh toán cho Booking/Deposit');
INSERT INTO permission (id, name, description) VALUES (35, 'PAYMENT:READ_SELF', 'Xem lịch sử giao dịch thanh toán của chính mình');

-- 5. Quản lý Đánh giá (Review)
INSERT INTO permission (id, name, description) VALUES (40, 'REVIEW:CREATE', 'Viết đánh giá mới (Review) cho một Outlet');
INSERT INTO permission (id, name, description) VALUES (41, 'REVIEW:UPDATE_SELF', 'Cập nhật đánh giá do chính mình viết');
INSERT INTO permission (id, name, description) VALUES (42, 'REVIEW:REACT', 'Thực hiện Like/Dislike (Reaction) đánh giá');
INSERT INTO permission (id, name, description) VALUES (43, 'REVIEW:REPLY_OWN', 'Phản hồi (Reply) đánh giá của khách hàng về Outlet do mình sở hữu (Owner)');
INSERT INTO permission (id, name, description) VALUES (44, 'REVIEW:REPORT', 'Báo cáo (Report) đánh giá không phù hợp');

-- 6. Quản lý Danh sách (Sharing List) & Membership/Ads
INSERT INTO permission (id, name, description) VALUES (50, 'SHARING_LIST:MANAGE_SELF', 'Quản lý danh sách chia sẻ (Create, Update, Delete, Share) của chính mình');
INSERT INTO permission (id, name, description) VALUES (51, 'MEMBERSHIP:SUBSCRIBE', 'Đăng ký hoặc gia hạn gói thành viên (Membership Plan)');
INSERT INTO permission (id, name, description) VALUES (52, 'MEMBERSHIP:READ_SELF', 'Xem chi tiết gói thành viên hiện tại của mình');
INSERT INTO permission (id, name, description) VALUES (53, 'ADVERTISEMENT:CREATE_OWN', 'Đặt quảng cáo cho Outlet do mình sở hữu (Owner)');
INSERT INTO permission (id, name, description) VALUES (54, 'ADVERTISEMENT:READ_OWN', 'Xem chi tiết các quảng cáo đang chạy của Outlet (Owner)');

-- 7. Quyền Truy cập Công cộng (Public Access)
INSERT INTO permission (id, name, description) VALUES (60, 'PUBLIC:READ_ALL_OUTLET', 'Xem thông tin công khai của tất cả Outlet');
INSERT INTO permission (id, name, description) VALUES (61, 'PUBLIC:READ_ALL_MENU', 'Xem thông tin Menu của tất cả Outlet');
INSERT INTO permission (id, name, description) VALUES (62, 'PUBLIC:READ_ALL_REVIEW', 'Xem tất cả đánh giá công khai');
INSERT INTO permission (id, name, description) VALUES (63, 'PUBLIC:SEARCH', 'Thực hiện các tác vụ tìm kiếm (Search)');

-- 8. Quyền Quản trị Hệ thống (Admin/SystemAdmin)
INSERT INTO permission (id, name, description) VALUES (100, 'ADMIN:MANAGE_USER_ACCOUNT', 'Quản lý toàn bộ tài khoản người dùng và hồ sơ (CRUD)');
INSERT INTO permission (id, name, description) VALUES (101, 'ADMIN:MANAGE_ROLES', 'Quản lý Vai trò (Role) và Quyền (Permission)');
INSERT INTO permission (id, name, description) VALUES (102, 'ADMIN:MANAGE_MASTER_DATA', 'Quản lý các danh mục tĩnh (Country, Province, District)');
INSERT INTO permission (id, name, description) VALUES (103, 'ADMIN:MANAGE_CATEGORIES', 'Quản lý Outlet Type, Category, Feature');
INSERT INTO permission (id, name, description) VALUES (104, 'ADMIN:MANAGE_MENU_MASTER', 'Quản lý Master Menu Item và các Category/Feature chung');
INSERT INTO permission (id, name, description) VALUES (105, 'ADMIN:FULL_ACCESS_OUTLET', 'Xem/Cập nhật/Vô hiệu hóa mọi Outlet trong hệ thống');
INSERT INTO permission (id, name, description) VALUES (106, 'ADMIN:MANAGE_REVIEW_REPORT', 'Duyệt và xử lý các báo cáo đánh giá (Review Report)');
INSERT INTO permission (id, name, description) VALUES (107, 'ADMIN:MANAGE_ADVERTISEMENT', 'Duyệt và quản lý các quảng cáo trong hệ thống');
INSERT INTO permission (id, name, description) VALUES (108, 'ADMIN:MANAGE_MEMBERSHIP', 'Quản lý các gói Membership Plan');
INSERT INTO permission (id, name, description) VALUES (109, 'ADMIN:MANAGE_NOTIFICATIONS', 'Gửi thông báo hệ thống đến người dùng');

--========================================================
--12. ROLE_PERMISSION
--========================================================

-- 1. ROLE_GUEST (4 quyền)
INSERT INTO role_permission (id, role_id, permission_id) VALUES (1, (SELECT id FROM role WHERE name = 'ROLE_GUEST'), (SELECT id FROM permission WHERE name = 'PUBLIC:READ_ALL_OUTLET'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (2, (SELECT id FROM role WHERE name = 'ROLE_GUEST'), (SELECT id FROM permission WHERE name = 'PUBLIC:READ_ALL_MENU'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (3, (SELECT id FROM role WHERE name = 'ROLE_GUEST'), (SELECT id FROM permission WHERE name = 'PUBLIC:READ_ALL_REVIEW'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (4, (SELECT id FROM role WHERE name = 'ROLE_GUEST'), (SELECT id FROM permission WHERE name = 'PUBLIC:SEARCH'));

-- 2. ROLE_USER (14 quyền)
INSERT INTO role_permission (id, role_id, permission_id) VALUES (5, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'AUTH:MANAGE_TOKEN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (6, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'USER:READ_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (7, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'USER:UPDATE_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (8, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'BOOKING:CREATE'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (9, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'BOOKING:CANCEL_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (10, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'PAYMENT:PROCESS'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (11, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'PAYMENT:READ_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (12, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'REVIEW:CREATE'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (13, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'REVIEW:UPDATE_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (14, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'REVIEW:REACT'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (15, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'REVIEW:REPORT'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (16, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'SHARING_LIST:MANAGE_SELF'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (17, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'MEMBERSHIP:SUBSCRIBE'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (18, (SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name = 'MEMBERSHIP:READ_SELF'));

-- 3. ROLE_OWNER (17 quyền)
INSERT INTO role_permission (id, role_id, permission_id) VALUES (19, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET:CREATE'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (20, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET:READ_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (21, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET:UPDATE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (22, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET:ACTIVATE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (23, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET_IMAGE:MANAGE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (24, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'OPERATING_HOURS:MANAGE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (25, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'MENU_ITEM:CREATE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (26, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'MENU_ITEM:READ_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (27, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'MENU_ITEM:UPDATE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (28, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'MENU_ITEM:DELETE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (29, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'MENU_FEATURE:MANAGE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (30, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'BOOKING:READ_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (31, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'BOOKING:UPDATE_STATUS_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (32, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'REVIEW:REPLY_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (33, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'ADVERTISEMENT:CREATE_OWN'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (34, (SELECT id FROM role WHERE name = 'ROLE_OWNER'), (SELECT id FROM permission WHERE name = 'ADVERTISEMENT:READ_OWN'));

-- 4. ROLE_ADMIN (6 quyền)
INSERT INTO role_permission (id, role_id, permission_id) VALUES (35, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_MASTER_DATA'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (36, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_CATEGORIES'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (37, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:FULL_ACCESS_OUTLET'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (38, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_REVIEW_REPORT'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (39, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_ADVERTISEMENT'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (40, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_MEMBERSHIP'));

-- 5. ROLE_SYSTEM_ADMIN (4 quyền)
INSERT INTO role_permission (id, role_id, permission_id) VALUES (41, (SELECT id FROM role WHERE name = 'ROLE_SYSTEM_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_USER_ACCOUNT'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (42, (SELECT id FROM role WHERE name = 'ROLE_SYSTEM_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_ROLES'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (43, (SELECT id FROM role WHERE name = 'ROLE_SYSTEM_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_NOTIFICATIONS'));
INSERT INTO role_permission (id, role_id, permission_id) VALUES (44, (SELECT id FROM role WHERE name = 'ROLE_SYSTEM_ADMIN'), (SELECT id FROM permission WHERE name = 'ADMIN:MANAGE_MENU_MASTER'));

--========================================================
--13. USER_ACCOUNT
--========================================================
-- Note: Converting from old structure (user_role table) to new structure (role_id in user_account)
-- Mapping: role_id 1=ROLE_USER, 2=ROLE_OWNER, 3=ROLE_ADMIN, 4=ROLE_SYSTEM_ADMIN
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000001', 'son.duong_88', 'PLAINTEXT_123', 'admin1@example.com', '0932952112', false, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000002', 'thang.ngo_95', 'PLAINTEXT_123', 'thangngo@gmail.vn', '0926854815', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000003', 'tam.duong_54', 'PLAINTEXT_123', 'tamduong@example.com', '0999442169', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000004', 'gia.vo_19', 'PLAINTEXT_123', 'giavo@foodgo.vn', '0995695455', false, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000005', 'ha.hoang_97', 'PLAINTEXT_123', 'hahoang@example.com', '0959173124', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000006', 'ngoc.do_31', 'PLAINTEXT_123', 'ngocdo@gmail.vn', '0925184985', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000007', 'phuc.duong_37', 'PLAINTEXT_123', 'phucduong@gmail.com', '0957220121', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000008', 'lam.hoang_71', 'PLAINTEXT_123', 'lamhoang@gmail.vn', '0957220122', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000009', 'tin.phan_30', 'PLAINTEXT_123', 'tinphan@foodgo.vn', '0987162456', false, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000010', 'khoi.vo_78', 'PLAINTEXT_123', 'khoivo@gmail.com', '0911998676', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000011', 'bao.duong_36', 'PLAINTEXT_123', 'baoduong@gmail.vn', '0916161461', false, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000012', 'trung.ho_37', 'PLAINTEXT_123', 'trungho@gmail.com', '0974855968', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000013', 'thien.ho_39', 'PLAINTEXT_123', 'thienho@example.com', '0917892264', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000014', 'phong.duong_65', 'PLAINTEXT_123', 'phongduong@gmail.com', '0967553511', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000015', 'thinh.vu_11', 'PLAINTEXT_123', 'thinhvu@example.com', '0941086835', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000016', 'levan.an_14', '123', 'levanan@foodgo.vn', '0911295205', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000017', 'vy.do_79', 'PLAINTEXT_123', 'vydo@gmail.vn', '0961921807', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000018', 'vi.le_71', 'PLAINTEXT_123', 'vile@example.com', '0911431238', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000019', 'an.bui_20', 'PLAINTEXT_123', 'anbui@example.com', '0956409086', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000020', 'kien.vu_99', 'PLAINTEXT_123', 'kienvu@example.com', '0920241922', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000021', 'ngan.tran_44', 'PLAINTEXT_123', 'ngantran@gmail.com', '0942562313', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000022', 'duy.dang_85', 'PLAINTEXT_123', 'duydang@example.com', '0963081509', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000023', 'lam.ho_62', 'PLAINTEXT_123', 'lamho@example.com', '0967112343', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000024', 'nhan.tran_47', 'PLAINTEXT_123', 'nhantran@gmail.com', '0921126743', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000025', 'thien.vu_73', 'PLAINTEXT_123', 'thienvu@gmail.com', '0929178130', false, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000026', 'chau.pham_40', 'PLAINTEXT_123', 'chaupham@foodgo.vn', '0990193081', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000027', 'lam.ly_50', 'PLAINTEXT_123', 'lamly@gmail.com', '0965648057', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000028', 'tin.nguyen_22', 'PLAINTEXT_123', 'tinnguyen@gmail.vn', '0917428274', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000029', 'vi.ngo_27', 'PLAINTEXT_123', 'vingo@gmail.vn', '0968779694', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('00000000-0000-0000-0000-000000000030', 'phong.phan_22', 'PLAINTEXT_123', 'phongphan@gmail.com', '0961332098', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));

--========================================================
--14. PROFILE
--========================================================
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'son.duong_88'), 'Dương Sơn', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (2, (SELECT id FROM user_account WHERE username = 'thang.ngo_95'), 'Ngô Thắng', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (3, (SELECT id FROM user_account WHERE username = 'tam.duong_54'), 'Dương Tâm', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (4, (SELECT id FROM user_account WHERE username = 'gia.vo_19'), 'Võ Gia', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (5, (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), 'Hoàng Hà', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (6, (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), 'Đỗ Ngọc', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (7, (SELECT id FROM user_account WHERE username = 'phuc.duong_37'), 'Dương Phúc', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (8, (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), 'Hoàng Lâm', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (9, (SELECT id FROM user_account WHERE username = 'tin.phan_30'), 'Phan Tín', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (10, (SELECT id FROM user_account WHERE username = 'khoi.vo_78'), 'Võ Khôi', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (11, (SELECT id FROM user_account WHERE username = 'bao.duong_36'), 'Dương Bảo', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (12, (SELECT id FROM user_account WHERE username = 'trung.ho_37'), 'Hồ Trung', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (13, (SELECT id FROM user_account WHERE username = 'thien.ho_39'), 'Hồ Thiện', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (14, (SELECT id FROM user_account WHERE username = 'phong.duong_65'), 'Dương Phong', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (15, (SELECT id FROM user_account WHERE username = 'thinh.vu_11'), 'Vũ Thịnh', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (16, (SELECT id FROM user_account WHERE username = 'nam.vo_14'), 'Võ Nam', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (17, (SELECT id FROM user_account WHERE username = 'vy.do_79'), 'Đỗ Vy', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (18, (SELECT id FROM user_account WHERE username = 'vi.le_71'), 'Lê Vi', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (19, (SELECT id FROM user_account WHERE username = 'an.bui_20'), 'Bùi An', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (20, (SELECT id FROM user_account WHERE username = 'kien.vu_99'), 'Vũ Kiên', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (21, (SELECT id FROM user_account WHERE username = 'ngan.tran_44'), 'Trần Ngân', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (22, (SELECT id FROM user_account WHERE username = 'duy.dang_85'), 'Đặng Duy', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (23, (SELECT id FROM user_account WHERE username = 'lam.ho_62'), 'Hồ Lâm', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (24, (SELECT id FROM user_account WHERE username = 'nhan.tran_47'), 'Trần Nhân', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (25, (SELECT id FROM user_account WHERE username = 'thien.vu_73'), 'Vũ Thiện', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (26, (SELECT id FROM user_account WHERE username = 'chau.pham_40'), 'Phạm Châu', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (27, (SELECT id FROM user_account WHERE username = 'lam.ly_50'), 'Lý Lam', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (28, (SELECT id FROM user_account WHERE username = 'tin.nguyen_22'), 'Nguyễn Tín', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (29, (SELECT id FROM user_account WHERE username = 'vi.ngo_27'), 'Ngô Vi', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (30, (SELECT id FROM user_account WHERE username = 'phong.phan_22'), 'Phan Phong', NULL, NULL, 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));

--========================================================
--22. OUTLET_FEATURE
--========================================================
INSERT INTO outlet_feature (id, name, description) VALUES (1, 'Wifi Miễn Phí', 'Cung cấp truy cập WiFi miễn phí');
INSERT INTO outlet_feature (id, name, description) VALUES (2, 'Chỗ Đậu Xe', 'Có khu vực đậu xe (ô tô/xe máy)');
INSERT INTO outlet_feature (id, name, description) VALUES (3, 'Cho Phép Mang Thú Cưng', 'Địa điểm cho phép khách hàng mang theo thú cưng');
INSERT INTO outlet_feature (id, name, description) VALUES (4, 'Máy Lạnh', 'Không gian có lắp đặt máy lạnh');
INSERT INTO outlet_feature (id, name, description) VALUES (5, 'Khu Vực Ngoài Trời', 'Có khu vực chỗ ngồi ngoài trời');
INSERT INTO outlet_feature (id, name, description) VALUES (6, 'Dịch Vụ Giao Hàng', 'Cung cấp dịch vụ giao hàng tận nơi');
INSERT INTO outlet_feature (id, name, description) VALUES (7, 'Dịch Vụ Mang Đi', 'Cung cấp dịch vụ mua mang đi (Takeaway)');
INSERT INTO outlet_feature (id, name, description) VALUES (8, 'Thanh Toán Thẻ', 'Chấp nhận thanh toán bằng thẻ tín dụng/thẻ ghi nợ');
INSERT INTO outlet_feature (id, name, description) VALUES (9, 'Nhạc Sống (Live Music)', 'Có các buổi biểu diễn nhạc sống');
INSERT INTO outlet_feature (id, name, description) VALUES (10, 'Phòng Riêng', 'Có phòng riêng biệt để dùng bữa');

--========================================================
--21. OUTLET
--========================================================
-- Note: Converting owner_id from string ('1', '2', etc.) to UUID from user_account
-- Mapping: '1' -> first owner user, '2' -> second owner user, etc.
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'The Workshop Coffee', 'Specialty coffee roastery', '27 Ngô Đức Kế, Bến Nghé, Quận 1', 'workshop@example.com', '02838246801', 'https://www.facebook.com/the.workshop.coffee', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77347640, 106.70560370, 'moderate', 70, true, 4.5, 1250, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000002', (SELECT id FROM user_account WHERE username = 'thang.ngo_95'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Highlands Coffee Lý Tự Trọng', 'Popular Vietnamese coffee chain', '71 Lý Tự Trọng, Bến Thành, Quận 1', 'highlands@example.com', '02871080071', 'https://highlandscoffee.com.vn', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77515200, 106.70180300, 'cheap', 60, true, 4.0, 980, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000003', (SELECT id FROM user_account WHERE username = 'tam.duong_54'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Starbucks Nguyễn Huệ', 'International coffee chain', '99 Nguyễn Huệ, Bến Nghé, Quận 1', 'starbucks@example.com', '02838210105', 'https://starbucks.vn', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77412100, 106.70196700, 'moderate', 80, true, 4.1, 1560, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000004', (SELECT id FROM user_account WHERE username = 'tam.duong_54'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Vintage Emporium Coffee', 'Vintage-style coffee shop', '95B Nguyễn Văn Thủ, Đa Kao, Quận 1', 'vintage@example.com', '0904413148', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77112300, 106.69865400, 'moderate', 50, true, 4.6, 1123, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000005', (SELECT id FROM user_account WHERE username = 'tam.duong_54'), (SELECT id FROM outlet_type WHERE name = 'Tea House'), 'Bosgaurus Coffee Roasters', 'Specialty coffee roastery', 'Villa 1, Saigon Pearl, 92 Nguyễn Hữu Cảnh, Phường 22, Quận Bình Thạnh', 'bosgaurus@example.com', '0901426877', 'https://www.facebook.com/bosgaurus', (SELECT id FROM district WHERE name = 'Quận Bình Thạnh'), 10.78856700, 106.71965400, 'moderate', 60, true, 4.8, 987, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000006', (SELECT id FROM user_account WHERE username = 'thang.ngo_95'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'LUsine Cafe', 'Trendy industrial-style cafe', '19 Lê Thánh Tôn, Bến Nghé, Quận 1', 'lusine@example.com', '02838227188', 'https://lusinespace.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77879400, 106.70192800, 'moderate', 80, true, 4.3, 2100, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000007', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Cafe Take Away'), 'Phúc Long Coffee & Tea', 'Vietnamese coffee and tea chain', '43 Phạm Ngọc Thạch, Phường Võ Thị Sáu, Quận 3', 'phuclong@example.com', '19006779', 'https://phuclong.com.vn', (SELECT id FROM district WHERE name = 'Quận 3'), 10.78125600, 106.68456200, 'cheap', 40, true, 4.2, 1890, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000008', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Trung Nguyên Legend Cafe', 'Vietnamese coffee brand', '12 Alexandre de Rhodes, Bến Nghé, Quận 1', 'trungnguyen@example.com', '19006616', 'https://trungnguyenlegend.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77678900, 106.70341200, 'cheap', 50, true, 4.2, 756, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000009', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Soo Kafe', 'Modern coffee shop', '10 Phan Kế Bính, Đa Kao, Quận 1', 'sookafe@example.com', '0985939258', 'https://www.facebook.com/sookafe', (SELECT id FROM district WHERE name = 'Quận 1'), 10.78156700, 106.69998900, 'moderate', 60, true, 4.6, 2250, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000010', (SELECT id FROM user_account WHERE username = 'gia.vo_19'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Okkio Caffe', 'Italian-style cafe', '8 Nguyễn Siêu, Bến Nghé, Quận 1', 'okkio@example.com', '0848693711', 'https://okkiocaffe.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77523400, 106.70254300, 'moderate', 50, true, 4.7, 3100, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000011', (SELECT id FROM user_account WHERE username = 'gia.vo_19'), (SELECT id FROM outlet_type WHERE name = 'Bar'), 'Broma Not A Bar', 'Trendy cocktail bar', '41 Nguyễn Huệ, Quận 1', 'broma@example.com', '0902158080', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77412300, 106.70196400, 'expensive', 100, true, 4.6, 2341, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000012', (SELECT id FROM user_account WHERE username = 'gia.vo_19'), (SELECT id FROM outlet_type WHERE name = 'Rooftop Bar'), 'Chill Skybar', 'Rooftop bar with city view', 'Tầng 26, AB Tower, 76 Lê Lai, Quận 1', 'chill@example.com', '0283827838', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.76912300, 106.69865400, 'expensive', 120, true, 4.7, 3456, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000013', (SELECT id FROM user_account WHERE username = 'gia.vo_19'), (SELECT id FROM outlet_type WHERE name = 'Bar'), 'Apocalypse Now', 'Popular nightlife bar', '2C Thi Sách, Quận 1', 'apocalypse@example.com', '0838252966', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77812300, 106.70365400, 'moderate', 150, true, 4.3, 1678, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000014', (SELECT id FROM user_account WHERE username = 'gia.vo_19'), (SELECT id FROM outlet_type WHERE name = 'Lounge Bar'), 'The Gin House', 'Gin-focused bar', '169 Bùi Viện, Quận 1', 'ginhouse@example.com', '0938123789', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.76712300, 106.69265400, 'expensive', 80, true, 4.5, 2123, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000015', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Pub'), 'The Shamrock Irish Pub', 'Irish pub atmosphere', '33 Lê Duẩn, Quận 1', 'shamrock@example.com', '0838234074', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.78012300, 106.69965400, 'moderate', 100, true, 4.4, 1890, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000016', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Street Food'), 'Bánh Xèo 46A', 'Famous crispy pancakes', '46A Đinh Công Tráng, Quận 1', NULL, '0838242810', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77112300, 106.69165400, 'cheap', 30, true, 4.0, 1234, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000017', (SELECT id FROM user_account WHERE username = 'son.duong_88'), (SELECT id FROM outlet_type WHERE name = 'Street Food'), 'Bánh Mì Huỳnh Hoa', 'Legendary banh mi stall', '26 Lê Thị Riêng, Quận 1', NULL, '0909678234', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.76312300, 106.68965400, 'cheap', 15, true, 4.2, 3456, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000018', (SELECT id FROM user_account WHERE username = 'tam.duong_54'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Lẩu Bò Tơ Hòn Sơn', 'Hot pot restaurant', '25 Cao Thắng, Quận 3', 'lau@example.com', '0838327729', NULL, (SELECT id FROM district WHERE name = 'Quận 3'), 10.78212300, 106.68665400, 'moderate', 60, true, 4.3, 1567, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000019', (SELECT id FROM user_account WHERE username = 'tam.duong_54'), (SELECT id FROM outlet_type WHERE name = 'Bistro'), 'Cơm Niêu Singapore', 'Singapore-style rice pot', '6-8 Công Trường Quốc Tế, Quận 3', 'comnieu@example.com', '0838231281', NULL, (SELECT id FROM district WHERE name = 'Quận 3'), 10.78512300, 106.68865400, 'moderate', 50, true, 4.1, 1123, false);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES ('20000000-0000-0000-0000-000000000020', (SELECT id FROM user_account WHERE username = 'thang.ngo_95'), (SELECT id FROM outlet_type WHERE name = 'Cafe Bar'), 'The Coffee House Signature', 'Modern coffee house chain', '26 Lý Tự Trọng, Quận 1', 'coffeehouse@example.com', '18006936', 'https://thecoffeehouse.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.77615200, 106.70080300, 'moderate', 70, true, 4.4, 2890, false);

--========================================================
--23. OUTLET_FEATURE_MAPPING
--========================================================
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (1, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (2, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (3, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM outlet_feature WHERE name = 'Thanh Toán Thẻ'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (4, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (5, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (6, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (7, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (8, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (9, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (10, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (11, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM outlet_feature WHERE name = 'Khu Vực Ngoài Trời'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (12, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM outlet_feature WHERE name = 'Thanh Toán Thẻ'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (13, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), (SELECT id FROM outlet_feature WHERE name = 'Khu Vực Ngoài Trời'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (14, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), (SELECT id FROM outlet_feature WHERE name = 'Thanh Toán Thẻ'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (15, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), (SELECT id FROM outlet_feature WHERE name = 'Nhạc Sống (Live Music)'));

--========================================================
--24. OUTLET_IMAGE
--========================================================
-- The Workshop Coffee
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (1, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (2, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 'https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=800', 2, false, false);
-- Highlands Coffee
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (3, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 'https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (4, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 'https://images.unsplash.com/photo-1442512595331-e89e73853f31?w=800', 2, false, false);
-- Starbucks
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (5, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), 'https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (6, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=800', 2, false, false);
-- LUsine Cafe
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (7, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800', 1, true, false);
-- Phúc Long Coffee & Tea
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (8, (SELECT id FROM outlet WHERE name = 'Phúc Long Coffee & Tea'), 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800', 1, true, false);
-- Chill Skybar
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (9, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (10, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=800', 2, false, false);
-- Vintage Emporium Coffee
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (11, (SELECT id FROM outlet WHERE name = 'Vintage Emporium Coffee'), 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (12, (SELECT id FROM outlet WHERE name = 'Vintage Emporium Coffee'), 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=800', 2, false, false);
-- Bosgaurus Coffee Roasters
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (13, (SELECT id FROM outlet WHERE name = 'Bosgaurus Coffee Roasters'), 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (14, (SELECT id FROM outlet WHERE name = 'Bosgaurus Coffee Roasters'), 'https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=800', 2, false, false);
-- Trung Nguyên Legend Cafe
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (15, (SELECT id FROM outlet WHERE name = 'Trung Nguyên Legend Cafe'), 'https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (16, (SELECT id FROM outlet WHERE name = 'Trung Nguyên Legend Cafe'), 'https://images.unsplash.com/photo-1442512595331-e89e73853f31?w=800', 2, false, false);
-- Soo Kafe
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (17, (SELECT id FROM outlet WHERE name = 'Soo Kafe'), 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (18, (SELECT id FROM outlet WHERE name = 'Soo Kafe'), 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800', 2, false, false);
-- Okkio Caffe
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (19, (SELECT id FROM outlet WHERE name = 'Okkio Caffe'), 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (20, (SELECT id FROM outlet WHERE name = 'Okkio Caffe'), 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=800', 2, false, false);
-- Broma Not A Bar
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (21, (SELECT id FROM outlet WHERE name = 'Broma Not A Bar'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (22, (SELECT id FROM outlet WHERE name = 'Broma Not A Bar'), 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=800', 2, false, false);
-- Apocalypse Now
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (23, (SELECT id FROM outlet WHERE name = 'Apocalypse Now'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (24, (SELECT id FROM outlet WHERE name = 'Apocalypse Now'), 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=800', 2, false, false);
-- The Gin House
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (25, (SELECT id FROM outlet WHERE name = 'The Gin House'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (26, (SELECT id FROM outlet WHERE name = 'The Gin House'), 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=800', 2, false, false);
-- The Shamrock Irish Pub
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (27, (SELECT id FROM outlet WHERE name = 'The Shamrock Irish Pub'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (28, (SELECT id FROM outlet WHERE name = 'The Shamrock Irish Pub'), 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=800', 2, false, false);
-- Bánh Xèo 46A
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (29, (SELECT id FROM outlet WHERE name = 'Bánh Xèo 46A'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (30, (SELECT id FROM outlet WHERE name = 'Bánh Xèo 46A'), 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800', 2, false, false);
-- Bánh Mì Huỳnh Hoa
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (31, (SELECT id FROM outlet WHERE name = 'Bánh Mì Huỳnh Hoa'), 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (32, (SELECT id FROM outlet WHERE name = 'Bánh Mì Huỳnh Hoa'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 2, false, false);
-- Lẩu Bò Tơ Hòn Sơn
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (33, (SELECT id FROM outlet WHERE name = 'Lẩu Bò Tơ Hòn Sơn'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (34, (SELECT id FROM outlet WHERE name = 'Lẩu Bò Tơ Hòn Sơn'), 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800', 2, false, false);
-- Cơm Niêu Singapore
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (35, (SELECT id FROM outlet WHERE name = 'Cơm Niêu Singapore'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (36, (SELECT id FROM outlet WHERE name = 'Cơm Niêu Singapore'), 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800', 2, false, false);
-- The Coffee House Signature
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (37, (SELECT id FROM outlet WHERE name = 'The Coffee House Signature'), 'https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=800', 1, true, false);
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES (38, (SELECT id FROM outlet WHERE name = 'The Coffee House Signature'), 'https://images.unsplash.com/photo-1442512595331-e89e73853f31?w=800', 2, false, false);

--========================================================
--25. OPERATING_HOURS
--========================================================
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (1, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 1, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (2, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 2, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (3, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 3, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (4, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 4, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (5, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 5, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (6, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 6, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (7, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 0, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (8, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 1, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (9, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 2, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (10, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 3, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (11, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 4, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (12, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 5, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (13, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 6, '07:00', '22:00', false, false);
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES (14, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), 0, '07:00', '22:00', false, false);

--========================================================
--9. MENU_ITEM (Level 4): Biến thế của các món ăn hiện hữu
--========================================================
-- Note: Converting from fnb to menu_item, mapping sub_category_id appropriately
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000001', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Tái', 'Phở bò tái chín, tái nạm', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000002', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Đặc Biệt', 'Phở bò đủ các loại', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000003', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Gà', 'Phở gà thơm ngon', false, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000004', (SELECT id FROM menu_item_sub_category WHERE name = 'Mì/Hủ Tiếu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bún Bò Huế', 'Bún bò Huế cay đậm đà', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000005', (SELECT id FROM menu_item_sub_category WHERE name = 'Mì/Hủ Tiếu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bún Chả Hà Nội', 'Bún chả nướng kiểu Hà Nội', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000006', (SELECT id FROM menu_item_sub_category WHERE name = 'Cơm Chiên'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cơm Tấm Sườn Nướng', 'Cơm tấm sườn nướng đặc biệt', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000007', (SELECT id FROM menu_item_sub_category WHERE name = 'Cơm Chiên'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cơm Tấm Sườn Bì Chả', 'Cơm tấm sườn bì chả trứng', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000008', (SELECT id FROM menu_item_sub_category WHERE name = 'Cà Phê Truyền Thống'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cà Phê Sữa Đá', 'Cà phê phin truyền thống Việt Nam với sữa đặc', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000009', (SELECT id FROM menu_item_sub_category WHERE name = 'Cà Phê Truyền Thống'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Espresso', 'Cà phê Espresso nguyên chất, đậm đà', false, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000010', (SELECT id FROM menu_item_sub_category WHERE name = 'Cà Phê Truyền Thống'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cafe Latte', 'Cà phê latte sữa tươi mịn màng', false, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000011', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Sữa'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Sữa Trân Châu Đường Đen', 'Trà sữa đường đen ngọt thơm với trân châu dai', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000012', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Sữa'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Sữa Trân Châu', 'Trà sữa truyền thống với trân châu đen', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000013', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Trái Cây'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Đào Cam Sả', 'Trà trái cây tươi mát', false, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000014', (SELECT id FROM menu_item_sub_category WHERE name = 'Nước Ngọt Có Ga'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Coca Cola Lon', 'Nước ngọt có ga Coca Cola dạng lon 330ml', true, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000015', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Kem Mousse'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cheesecake', 'Bánh phô mai New York', false, false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES ('60000000-0000-0000-0000-000000000016', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Kem Mousse'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Chocolate Lava Cake', 'Bánh sô-cô-la nóng chảy', false, false);

--========================================================
--26. OUTLET_MENU_ITEM
--========================================================
-- Note: Converting from outlet_has_fnb to outlet_menu_item
-- The Workshop Coffee - Espresso
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (1, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM menu_item WHERE name = 'Espresso'), 'Espresso Single Origin', 'Cà phê espresso hạt Arabica Đà Lạt', 45000.00, 'https://images.unsplash.com/photo-1510591509098-f4fdc6d0ff04?w=600', true);
-- The Workshop Coffee - Cafe Latte
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (2, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM menu_item WHERE name = 'Cafe Latte'), 'Signature Latte', 'Cà phê latte nghệ thuật', 55000.00, 'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=600', true);
-- Highlands Coffee - Cà Phê Sữa Đá
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (3, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM menu_item WHERE name = 'Cà Phê Sữa Đá'), 'Freeze Trà Xanh', 'Freeze trà xanh đặc biệt', 49000.00, 'https://images.unsplash.com/photo-1517487881594-2787fef5ebf7?w=600', true);
-- LUsine Cafe - Cà Phê Sữa Đá
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (4, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM menu_item WHERE name = 'Cà Phê Sữa Đá'), 'Cà Phê Sữa Đá LUsine', 'Cà phê sữa đá kiểu LUsine', 45000.00, 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=600', true);
-- Phúc Long - Trà Sữa
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (5, (SELECT id FROM outlet WHERE name = 'Phúc Long Coffee & Tea'), (SELECT id FROM menu_item WHERE name = 'Trà Sữa Trân Châu Đường Đen'), 'Trà Sữa Ô Long Đường Đen', 'Trà sữa ô long đường đen đặc biệt', 45000.00, 'https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=600', true);
-- Phúc Long - Trà Đào Cam Sả
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (6, (SELECT id FROM outlet WHERE name = 'Phúc Long Coffee & Tea'), (SELECT id FROM menu_item WHERE name = 'Trà Đào Cam Sả'), 'Trà Đào Cam Sả Phúc Long', 'Trà trái cây tươi mát', 42000.00, 'https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=600', true);
-- Thêm các món ăn khác với ảnh
-- Phở Bò
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (7, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM menu_item WHERE name = 'Phở Bò'), 'Phở Bò Đặc Biệt', 'Phở bò truyền thống với thịt bò tái, chín, gân, sách', 85000.00, 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=600', true);
-- Bún Bò Huế
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (8, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM menu_item WHERE name = 'Bún Bò Huế'), 'Bún Bò Huế Cay', 'Bún bò Huế cay đậm đà với chả cua', 75000.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', true);
-- Cơm Tấm
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (9, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM menu_item WHERE name = 'Cơm Tấm Sườn Nướng'), 'Cơm Tấm Sườn Nướng', 'Cơm tấm sườn nướng đặc biệt', 65000.00, 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600', true);
-- Trà Sữa Trân Châu
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (10, (SELECT id FROM outlet WHERE name = 'Phúc Long Coffee & Tea'), (SELECT id FROM menu_item WHERE name = 'Trà Sữa Trân Châu'), 'Trà Sữa Trân Châu Phúc Long', 'Trà sữa truyền thống với trân châu đen', 45000.00, 'https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=600', true);
-- Coca Cola
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (11, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM menu_item WHERE name = 'Coca Cola Lon'), 'Coca Cola Lon', 'Nước ngọt có ga Coca Cola dạng lon 330ml', 25000.00, 'https://images.unsplash.com/photo-1554866585-cd94860890b7?w=600', true);
-- Cheesecake
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (12, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM menu_item WHERE name = 'Cheesecake'), 'New York Cheesecake', 'Bánh phô mai New York', 95000.00, 'https://images.unsplash.com/photo-1524351199678-941a58a3df50?w=600', true);
-- Chocolate Lava Cake
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (13, (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM menu_item WHERE name = 'Chocolate Lava Cake'), 'Chocolate Lava Cake', 'Bánh sô-cô-la nóng chảy', 85000.00, 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=600', true);
-- Bún Chả
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (14, (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM menu_item WHERE name = 'Bún Chả Hà Nội'), 'Bún Chả Hà Nội', 'Bún chả nướng kiểu Hà Nội', 70000.00, 'https://images.unsplash.com/photo-1556910103-1c02745aae4d?w=600', true);
-- Cơm Tấm Sườn Bì Chả
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (15, (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM menu_item WHERE name = 'Cơm Tấm Sườn Bì Chả'), 'Cơm Tấm Sườn Bì Chả', 'Cơm tấm sườn bì chả trứng', 70000.00, 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600', true);

--========================================================
--27. MENU_ITEM_FEATURE
--========================================================
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (1, 'Ăn Chay (Vegetarian)', 'tag', NULL, NULL, 'Phù hợp cho người ăn chay');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (2, 'Thuần Chay (Vegan)', 'tag', NULL, NULL, 'Phù hợp cho người ăn thuần chay');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (3, 'Không Gluten', 'tag', NULL, NULL, 'Không chứa Gluten');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (4, 'Không Sữa', 'tag', NULL, NULL, 'Không chứa các sản phẩm từ sữa');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (5, 'Không Hạt', 'tag', NULL, NULL, 'Không chứa các loại hạt');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (6, 'Không Hải Sản', 'tag', NULL, NULL, 'Không chứa hải sản');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (7, 'Không Trứng', 'tag', NULL, NULL, 'Không chứa trứng');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (8, 'Nguyên liệu Hữu cơ', 'tag', NULL, NULL, 'Được làm bằng nguyên liệu hữu cơ (Organic)');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (9, 'Món Đặc Trưng', 'tag', NULL, NULL, 'Món ăn đặc trưng của nhà hàng');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (10, 'Đầu Bếp Đề Xuất', 'tag', NULL, NULL, 'Món ăn được Đầu bếp đề xuất');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (11, 'Theo Mùa', 'tag', NULL, NULL, 'Chỉ có sẵn theo mùa');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (12, 'Nguyên liệu Địa Phương', 'tag', NULL, NULL, 'Được chế biến từ nguyên liệu địa phương');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (13, 'Độ Cay', 'attribute', 'number', '[0,1,2,3,4,5]', 'Mức độ cay (0=không cay, 5=rất cay)');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (14, 'Kích Thước Phần Ăn', 'attribute', 'enum', '["small","medium","large","extra-large"]', 'Kích thước suất ăn');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (15, 'Calo', 'attribute', 'number', NULL, 'Lượng Calories trên mỗi suất ăn');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (16, 'Protein', 'attribute', 'number', NULL, 'Lượng Protein tính bằng gram');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (17, 'Carbohydrate', 'attribute', 'number', NULL, 'Lượng Carbohydrate tính bằng gram');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (18, 'Chất Béo', 'attribute', 'number', NULL, 'Lượng Chất béo (Fat) tính bằng gram');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (19, 'Chất Xơ', 'attribute', 'number', NULL, 'Lượng Chất xơ (Fiber) tính bằng gram');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (20, 'Đường', 'attribute', 'number', NULL, 'Lượng Đường (Sugar) tính bằng gram');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (21, 'Natri', 'attribute', 'number', NULL, 'Lượng Natri (Sodium) tính bằng mg');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (22, 'Thời Gian Chuẩn Bị', 'attribute', 'number', NULL, 'Thời gian chuẩn bị tính bằng phút');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (23, 'Nhiệt Độ Phục Vụ', 'attribute', 'enum', '["hot","cold","room-temperature"]', 'Nhiệt độ phục vụ món ăn');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (24, 'Loại Bữa Ăn', 'attribute', 'enum', '["appetizer","main-course","dessert","snack","beverage"]', 'Loại món ăn (Khai vị, Món chính, Tráng miệng,...)');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (25, 'Phương Pháp Nấu', 'attribute', 'enum', '["grilled","fried","steamed","boiled","raw","baked"]', 'Phương pháp chế biến');
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (26, 'Độ Ngọt', 'attribute', 'number', '[0,1,2,3,4,5]', 'Mức độ ngọt (0=không ngọt, 5=rất ngọt)');

--========================================================
--28. OUTLET_MENU_ITEM_FEATURE
--========================================================
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (1, (SELECT id FROM outlet_menu_item WHERE id = 1), (SELECT id FROM menu_item_feature WHERE name = 'Độ Cay'), '0');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (2, (SELECT id FROM outlet_menu_item WHERE id = 1), (SELECT id FROM menu_item_feature WHERE name = 'Kích Thước Phần Ăn'), 'medium');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (3, (SELECT id FROM outlet_menu_item WHERE id = 1), (SELECT id FROM menu_item_feature WHERE name = 'Nhiệt Độ Phục Vụ'), 'hot');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (4, (SELECT id FROM outlet_menu_item WHERE id = 1), (SELECT id FROM menu_item_feature WHERE name = 'Loại Bữa Ăn'), 'beverage');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (5, (SELECT id FROM outlet_menu_item WHERE id = 2), (SELECT id FROM menu_item_feature WHERE name = 'Độ Ngọt'), '2');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (6, (SELECT id FROM outlet_menu_item WHERE id = 2), (SELECT id FROM menu_item_feature WHERE name = 'Nhiệt Độ Phục Vụ'), 'hot');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (7, (SELECT id FROM outlet_menu_item WHERE id = 2), (SELECT id FROM menu_item_feature WHERE name = 'Loại Bữa Ăn'), 'beverage');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (8, (SELECT id FROM outlet_menu_item WHERE id = 5), (SELECT id FROM menu_item_feature WHERE name = 'Độ Ngọt'), '4');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (9, (SELECT id FROM outlet_menu_item WHERE id = 5), (SELECT id FROM menu_item_feature WHERE name = 'Nhiệt Độ Phục Vụ'), 'cold');
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (10, (SELECT id FROM outlet_menu_item WHERE id = 5), (SELECT id FROM menu_item_feature WHERE name = 'Loại Bữa Ăn'), 'beverage');

--========================================================
--17. MEMBERSHIP_PLAN
--========================================================
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (1, 'Free Owner', 'Gói miễn phí', 0.00, 0, 10, '["basic-listing"]', 'OWNER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (2, 'Basic Owner', 'Gói đăng ký cơ bản dành cho cá nhân.', 9.99, 12, 50, '["basic-listing", "priority-email"]', 'OWNER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (3, 'Standard Owner', 'Gói tiêu chuẩn, phù hợp cho các nhóm nhỏ.', 29.99, 12, 100, '["basic-listing", "priority-email", "analytics-dashboard"]', 'OWNER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (4, 'Premium Owner', 'Gói cao cấp với đầy đủ tính năng.', 49.99, 12, 300, '["all-standard-features", "dedicated-support", "early-access-features"]', 'OWNER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (5, 'Enterprise Owner', 'Gói tùy chỉnh dành cho các tổ chức lớn.', 99.99, 0, 0, '["all-premium-features", "custom-sla", "account-manager"]', 'OWNER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (6, 'Silver Member', 'Thành viên Bạc: Mở khóa tính năng Đặt bàn', 59000.00, 1, NULL, '["booking-access"]', 'USER', false);
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features, type, is_deleted) VALUES (7, 'Gold Member', 'Thành viên Vàng: Đặt bàn + Ưu đãi đặc quyền', 199000.00, 12, NULL, '["booking-access", "priority-support", "voucher"]', 'USER', false);

--========================================================
--18. USER_MEMBERSHIP
--========================================================
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date, is_active, is_deleted) VALUES (1, (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), (SELECT id FROM membership_plan WHERE name = 'Silver Member'), CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', true, false);
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date, is_active, is_deleted) VALUES (2, (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), (SELECT id FROM membership_plan WHERE name = 'Gold Member'), CURRENT_DATE, CURRENT_DATE + INTERVAL '365 days', true, false);

--========================================================
--19. SHARING_LIST
--========================================================
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (1, (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), 'My Favorites', 'Danh sách địa điểm yêu thích', false, false);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (2, (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), 'Best Coffee Shops', 'Những quán cà phê tốt nhất', true, false);

--========================================================
--20. SHARING_LIST_COLLABORATOR
--========================================================
INSERT INTO sharing_list_collaborator (id, user_id, sharing_list_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), (SELECT id FROM sharing_list WHERE name = 'My Favorites'));
INSERT INTO sharing_list_collaborator (id, user_id, sharing_list_id) VALUES (2, (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), (SELECT id FROM sharing_list WHERE name = 'Best Coffee Shops'));

--========================================================
--29. BOOKING
--========================================================
-- Note: Converting booking_id from integer to UUID, status from string to enum
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), CURRENT_DATE + INTERVAL '3 days', '19:00', 4, 'CONFIRMED', 100000.00, 'Ngồi tầng 1, gần cửa sổ', NULL);
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000002', (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), CURRENT_DATE + INTERVAL '6 days', '21:00', 2, 'CONFIRMED', 50000.00, 'Bàn ngoài trời', NULL);
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000003', (SELECT id FROM outlet WHERE name = 'Starbucks Nguyễn Huệ'), (SELECT id FROM user_account WHERE username = 'phuc.duong_37'), CURRENT_DATE + INTERVAL '1 day', '18:30', 3, 'PENDING', 75000.00, NULL, NULL);
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000004', (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), CURRENT_DATE - INTERVAL '2 days', '20:00', 4, 'COMPLETED', 100000.00, 'Kỷ niệm sinh nhật', NULL);
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000005', (SELECT id FROM outlet WHERE name = 'Chill Skybar'), (SELECT id FROM user_account WHERE username = 'khoi.vo_78'), CURRENT_DATE + INTERVAL '5 days', '21:30', 6, 'CONFIRMED', 200000.00, 'Bàn view đẹp', NULL);

--========================================================
--30. PAYMENT
--========================================================
-- Note: Converting payment_id from integer to UUID, method from string to enum, status from string to enum
INSERT INTO payment (id, related_id, amount, payment_method, payment_status, transaction_id, type, is_deleted) VALUES ('40000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000001', 100000.00, 'BANK_TRANSFER', 'COMPLETED', 'TXN123456789', 'BOOKING', false);
INSERT INTO payment (id, related_id, amount, payment_method, payment_status, transaction_id, type, is_deleted) VALUES ('40000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000002', 50000.00, 'E_WALLET', 'COMPLETED', 'TXN123456790', 'BOOKING', false);
INSERT INTO payment (id, related_id, amount, payment_method, payment_status, transaction_id, type, is_deleted) VALUES ('40000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000003', 75000.00, 'CREDIT_CARD', 'PENDING', 'TXN123456791', 'BOOKING', false);
INSERT INTO payment (id, related_id, amount, payment_method, payment_status, transaction_id, type, is_deleted) VALUES ('40000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000004', 100000.00, 'BANK_TRANSFER', 'COMPLETED', 'TXN123456792', 'BOOKING', false);
INSERT INTO payment (id, related_id, amount, payment_method, payment_status, transaction_id, type, is_deleted) VALUES ('40000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000005', 200000.00, 'E_WALLET', 'COMPLETED', 'TXN123456793', 'BOOKING', false);

--========================================================
--31. REVIEW
--========================================================
-- Note: Converting review_id from integer to UUID, adding food_rating, service_rating, ambiance_rating, price_rating, overall_rating
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES ('50000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000001'), 5, 5, 4, 4, 5, 'Cà phê ngon, không gian đẹp, phục vụ tốt', 3, 0);
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES ('50000000-0000-0000-0000-000000000002', (SELECT id FROM outlet WHERE name = 'Highlands Coffee Lý Tự Trọng'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000002'), 4, 4, 3, 4, 4, 'Cà phê ổn, giá hợp lý', 1, 0);
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES ('50000000-0000-0000-0000-000000000003', (SELECT id FROM outlet WHERE name = 'LUsine Cafe'), (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000004'), 5, 5, 5, 4, 5, 'Không gian đẹp, đồ uống ngon, phục vụ chuyên nghiệp', 5, 0);

--========================================================
--32. REVIEW_IMAGE
--========================================================
INSERT INTO review_image (id, review_id, image_url) VALUES (1, (SELECT id FROM review WHERE comment = 'Cà phê ngon, không gian đẹp, phục vụ tốt'), 'https://cdn.example.com/review/5001.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (2, (SELECT id FROM review WHERE comment = 'Cà phê ngon, không gian đẹp, phục vụ tốt'), 'https://cdn.example.com/review/5002.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (3, (SELECT id FROM review WHERE comment = 'Không gian đẹp, đồ uống ngon, phục vụ chuyên nghiệp'), 'https://cdn.example.com/review/5003.jpg');

--========================================================
--33. REVIEW_REPLY
--========================================================
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (1, (SELECT id FROM review WHERE comment = 'Cà phê ngon, không gian đẹp, phục vụ tốt'), (SELECT owner_id FROM outlet WHERE name = 'The Workshop Coffee'), 'Cảm ơn bạn đã ủng hộ! ❤️');
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (2, (SELECT id FROM review WHERE comment = 'Không gian đẹp, đồ uống ngon, phục vụ chuyên nghiệp'), (SELECT owner_id FROM outlet WHERE name = 'LUsine Cafe'), 'Rất vui được phục vụ bạn!');

--========================================================
--34. REVIEW_REACTION
--========================================================
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (1, (SELECT id FROM review WHERE comment = 'Cà phê ngon, không gian đẹp, phục vụ tốt'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), 'LIKE');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (2, (SELECT id FROM review WHERE comment = 'Cà phê ngon, không gian đẹp, phục vụ tốt'), (SELECT id FROM user_account WHERE username = 'phuc.duong_37'), 'LIKE');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (3, (SELECT id FROM review WHERE comment = 'Cà phê ổn, giá hợp lý'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), 'LIKE');

--========================================================
--35. REVIEW_REPORT
--========================================================
INSERT INTO review_report (id, review_id, reporter_id, reason, status) VALUES (1, (SELECT id FROM review WHERE comment = 'Cà phê ổn, giá hợp lý'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), 'SPAM', 'PENDING');

--========================================================
--36. NOTIFICATION
--========================================================
INSERT INTO notification (id, user_id, type, title, content, related_id, is_read) VALUES (1, (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), 'review', 'Bạn có phản hồi mới!', 'Chủ quán đã trả lời review của bạn.', '50000000-0000-0000-0000-000000000001', false);
INSERT INTO notification (id, user_id, type, title, content, related_id, is_read) VALUES (2, (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), 'booking', 'Đặt bàn thành công', 'Đặt bàn của bạn đã được xác nhận.', '30000000-0000-0000-0000-000000000002', false);

--========================================================
--37. ADVERTISEMENT
--========================================================
INSERT INTO advertisement (id, outlet_id, position, start_date, end_date, is_active) VALUES (1, (SELECT id FROM outlet WHERE name = 'The Workshop Coffee'), 'featured', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', true);
INSERT INTO advertisement (id, outlet_id, position, start_date, end_date, is_active) VALUES (2, (SELECT id FROM outlet WHERE name = 'Chill Skybar'), 'featured', CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', true);

--================================================================================================================
--================================================================================================================
-- Các lệnh này đảm bảo Sequence trong DB được đồng bộ với ID lớn nhất hiện tại, tránh lỗi duplicate key.
--================================================================================================================
--================================================================================================================

--========================================================
--📊 THÊM DỮ LIỆU TEST CHO TÌM KIẾM NÂNG CAO
--========================================================

-- Thêm nhiều outlets đa dạng để test search
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews, is_deleted) VALUES 
('20000000-0000-0000-0000-000000000021', (SELECT id FROM user_account WHERE username = 'thien.ho_39'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Phở Gia Truyền', 'Phở bò truyền thống gia truyền 3 đời', '123 Nguyễn Thị Minh Khai, Quận 1', 'pho@example.com', '02838234567', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.78000000, 106.70000000, 'cheap', 40, true, 4.5, 2340, false),
('20000000-0000-0000-0000-000000000022', (SELECT id FROM user_account WHERE username = 'phong.duong_65'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Bún Bò Huế Cô Ba', 'Bún bò Huế cay nồng đậm đà', '456 Lê Lợi, Quận 1', 'bunbo@example.com', '02838234568', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77500000, 106.70500000, 'cheap', 35, true, 4.3, 1890, false),
('20000000-0000-0000-0000-000000000023', (SELECT id FROM user_account WHERE username = 'thinh.vu_11'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Cơm Tấm Cali', 'Cơm tấm sườn nướng đặc biệt', '789 Điện Biên Phủ, Quận Bình Thạnh', 'comtam@example.com', '02838234569', NULL, (SELECT id FROM district WHERE name = 'Quận Bình Thạnh'), 10.79000000, 106.72000000, 'cheap', 50, true, 4.4, 2567, false),
('20000000-0000-0000-0000-000000000024', (SELECT id FROM user_account WHERE username = 'chau.pham_40'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Cà Phê Sài Gòn Xưa', 'Cà phê phin truyền thống không gian cổ điển', '321 Võ Văn Tần, Quận 3', 'caphe@example.com', '02838234570', NULL, (SELECT id FROM district WHERE name = 'Quận 3'), 10.78500000, 106.69000000, 'cheap', 30, true, 4.6, 3120, false),
('20000000-0000-0000-0000-000000000025', (SELECT id FROM user_account WHERE username = 'lam.ly_50'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Trà Sữa Gong Cha', 'Trà sữa Đài Loan nổi tiếng', '654 Nguyễn Huệ, Quận 1', 'trasua@example.com', '02838234571', NULL, (SELECT id FROM district WHERE name = 'Quận 1'), 10.77400000, 106.70200000, 'moderate', 45, true, 4.2, 1789, false),
('20000000-0000-0000-0000-000000000026', (SELECT id FROM user_account WHERE username = 'tin.nguyen_22'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Bánh Mì Bà Lan', 'Bánh mì thịt nướng đặc biệt', '987 Nguyễn Trãi, Quận 5', 'banhmi@example.com', '02838234572', NULL, (SELECT id FROM district WHERE name = 'Quận 5'), 10.75500000, 106.67000000, 'cheap', 25, true, 4.7, 4230, false),
('20000000-0000-0000-0000-000000000027', (SELECT id FROM user_account WHERE username = 'vi.ngo_27'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Lẩu Cá Kèo', 'Lẩu cá kèo đặc sản miền Tây', '147 Trần Hưng Đạo, Quận 5', 'lau@example.com', '02838234573', NULL, (SELECT id FROM district WHERE name = 'Quận 5'), 10.75200000, 106.66800000, 'moderate', 60, true, 4.5, 1980, false),
('20000000-0000-0000-0000-000000000028', (SELECT id FROM user_account WHERE username = 'phong.phan_22'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Bánh Xèo Mười Xiềm', 'Bánh xèo giòn tan đặc biệt', '258 Võ Văn Tần, Quận 3', 'banhxeo@example.com', '02838234574', NULL, (SELECT id FROM district WHERE name = 'Quận 3'), 10.78300000, 106.68800000, 'cheap', 40, true, 4.4, 1678, false),
('20000000-0000-0000-0000-000000000029', (SELECT id FROM user_account WHERE username = 'thien.ho_39'), (SELECT id FROM outlet_type WHERE name = 'Cafe'), 'Cà Phê Cộng', 'Cà phê vỉa hè phong cách xưa', '369 Điện Biên Phủ, Quận Bình Thạnh', 'cong@example.com', '02838234575', NULL, (SELECT id FROM district WHERE name = 'Quận Bình Thạnh'), 10.79200000, 106.72500000, 'cheap', 20, true, 4.3, 1456, false),
('20000000-0000-0000-0000-000000000030', (SELECT id FROM user_account WHERE username = 'phong.duong_65'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Bún Riêu Cua', 'Bún riêu cua đậm đà hương vị', '741 Nguyễn Văn Cừ, Quận 5', 'bunrieu@example.com', '02838234576', NULL, (SELECT id FROM district WHERE name = 'Quận 5'), 10.75000000, 106.66500000, 'cheap', 35, true, 4.6, 2234, false);

-- Thêm menu items đa dạng
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular, is_deleted) VALUES 
('60000000-0000-0000-0000-000000000017', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Tái Nạm Gân', 'Phở bò đầy đủ tái, nạm, gân', true, false),
('60000000-0000-0000-0000-000000000018', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Viên', 'Phở bò với viên bò thơm ngon', false, false),
('60000000-0000-0000-0000-000000000019', (SELECT id FROM menu_item_sub_category WHERE name = 'Mì/Hủ Tiếu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Hủ Tiếu Nam Vang', 'Hủ tiếu Nam Vang đặc biệt', true, false),
('60000000-0000-0000-0000-000000000020', (SELECT id FROM menu_item_sub_category WHERE name = 'Mì/Hủ Tiếu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bún Mắm', 'Bún mắm miền Tây đậm đà', true, false),
('60000000-0000-0000-0000-000000000021', (SELECT id FROM menu_item_sub_category WHERE name = 'Cơm Chiên'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cơm Tấm Sườn Bì Chả Trứng', 'Cơm tấm đầy đủ sườn, bì, chả, trứng', true, false),
('60000000-0000-0000-0000-000000000022', (SELECT id FROM menu_item_sub_category WHERE name = 'Cơm Chiên'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cơm Gà Hải Nam', 'Cơm gà Hải Nam thơm ngon', false, false),
('60000000-0000-0000-0000-000000000023', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Sữa'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Sữa Matcha', 'Trà sữa matcha Nhật Bản', true, false),
('60000000-0000-0000-0000-000000000024', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Sữa'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Sữa Oolong', 'Trà sữa ô long thơm ngon', false, false),
('60000000-0000-0000-0000-000000000025', (SELECT id FROM menu_item_sub_category WHERE name = 'Cà Phê Truyền Thống'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cà Phê Đen Đá', 'Cà phê đen đá nguyên chất', true, false),
('60000000-0000-0000-0000-000000000026', (SELECT id FROM menu_item_sub_category WHERE name = 'Cà Phê Truyền Thống'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bạc Xỉu', 'Cà phê sữa đặc biệt', true, false),
('60000000-0000-0000-0000-000000000027', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Mì'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bánh Mì Thịt Nướng', 'Bánh mì thịt nướng đặc biệt', true, false),
('60000000-0000-0000-0000-000000000028', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Mì'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bánh Mì Chả Cá', 'Bánh mì chả cá thơm ngon', false, false),
('60000000-0000-0000-0000-000000000029', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Xèo'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bánh Xèo Tôm Thịt', 'Bánh xèo tôm thịt giòn tan', true, false),
('60000000-0000-0000-0000-000000000030', (SELECT id FROM menu_item_sub_category WHERE name = 'Lẩu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Lẩu Thái', 'Lẩu Thái chua cay đậm đà', true, false),
('60000000-0000-0000-0000-000000000031', (SELECT id FROM menu_item_sub_category WHERE name = 'Lẩu'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Lẩu Cá Kèo', 'Lẩu cá kèo miền Tây', true, false);

-- Thêm outlet_menu_item cho các outlets mới
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES 
(16, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), (SELECT id FROM menu_item WHERE name = 'Phở Bò Tái Nạm Gân'), 'Phở Bò Tái Nạm Gân', 'Phở bò đầy đủ tái, nạm, gân', 75000.00, 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=600', true),
(17, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), (SELECT id FROM menu_item WHERE name = 'Bún Bò Huế'), 'Bún Bò Huế Đặc Biệt', 'Bún bò Huế cay đậm đà', 70000.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', true),
(18, (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), (SELECT id FROM menu_item WHERE name = 'Cơm Tấm Sườn Bì Chả Trứng'), 'Cơm Tấm Đặc Biệt', 'Cơm tấm sườn bì chả trứng', 65000.00, 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600', true),
(19, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM menu_item WHERE name = 'Cà Phê Đen Đá'), 'Cà Phê Đen Đá', 'Cà phê đen đá nguyên chất', 25000.00, 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=600', true),
(20, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM menu_item WHERE name = 'Bạc Xỉu'), 'Bạc Xỉu', 'Cà phê sữa đặc biệt', 30000.00, 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=600', true),
(21, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM menu_item WHERE name = 'Trà Sữa Matcha'), 'Trà Sữa Matcha', 'Trà sữa matcha Nhật Bản', 55000.00, 'https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=600', true),
(22, (SELECT id FROM outlet WHERE name = 'Bánh Mì Bà Lan'), (SELECT id FROM menu_item WHERE name = 'Bánh Mì Thịt Nướng'), 'Bánh Mì Thịt Nướng', 'Bánh mì thịt nướng đặc biệt', 35000.00, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600', true),
(23, (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), (SELECT id FROM menu_item WHERE name = 'Lẩu Cá Kèo'), 'Lẩu Cá Kèo Đặc Biệt', 'Lẩu cá kèo miền Tây', 250000.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', true),
(24, (SELECT id FROM outlet WHERE name = 'Bánh Xèo Mười Xiềm'), (SELECT id FROM menu_item WHERE name = 'Bánh Xèo Tôm Thịt'), 'Bánh Xèo Tôm Thịt', 'Bánh xèo tôm thịt giòn tan', 60000.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', true),
(25, (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), (SELECT id FROM menu_item WHERE name = 'Bún Bò Huế'), 'Bún Riêu Cua', 'Bún riêu cua đậm đà', 55000.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', true);

-- Thêm operating hours cho các outlets mới
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed, is_deleted) VALUES 
(15, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 1, '06:00', '22:00', false, false),
(16, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 2, '06:00', '22:00', false, false),
(17, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 3, '06:00', '22:00', false, false),
(18, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 4, '06:00', '22:00', false, false),
(19, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 5, '06:00', '22:00', false, false),
(20, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 6, '06:00', '22:00', false, false),
(21, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 0, '06:00', '22:00', false, false),
(22, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 1, '07:00', '21:00', false, false),
(23, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 2, '07:00', '21:00', false, false),
(24, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 3, '07:00', '21:00', false, false),
(25, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 4, '07:00', '21:00', false, false),
(26, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 5, '07:00', '21:00', false, false),
(27, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 6, '07:00', '21:00', false, false),
(28, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 0, '07:00', '21:00', false, false);

-- Thêm outlet images cho các outlets mới
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary, is_deleted) VALUES 
(39, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=800', 1, true, false),
(40, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false),
(41, (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=800', 1, true, false),
(42, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=800', 1, true, false),
(43, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), 'https://images.unsplash.com/photo-1525385444278-0e7ec8f2e7ed?w=800', 1, true, false),
(44, (SELECT id FROM outlet WHERE name = 'Bánh Mì Bà Lan'), 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800', 1, true, false),
(45, (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false),
(46, (SELECT id FROM outlet WHERE name = 'Bánh Xèo Mười Xiềm'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false),
(47, (SELECT id FROM outlet WHERE name = 'Cà Phê Cộng'), 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=800', 1, true, false),
(48, (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 1, true, false);

-- Thêm outlet_category_mapping cho các outlets mới
INSERT INTO outlet_category_mapping (id, outlet_id, category_id) VALUES 
(11, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(12, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(13, (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(14, (SELECT id FROM outlet WHERE name = 'Bánh Mì Bà Lan'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(15, (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(16, (SELECT id FROM outlet WHERE name = 'Bánh Xèo Mười Xiềm'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(17, (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), (SELECT id FROM outlet_category WHERE name = 'Vietnamese')),
(18, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM outlet_category WHERE name = 'Coffee Shop')),
(19, (SELECT id FROM outlet WHERE name = 'Cà Phê Cộng'), (SELECT id FROM outlet_category WHERE name = 'Coffee Shop')),
(20, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM outlet_category WHERE name = 'Traditional Tea'));

-- Thêm outlet_feature_mapping cho các outlets mới để test filter
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES 
(20, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(21, (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi')),
(22, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(23, (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi')),
(24, (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(25, (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), (SELECT id FROM outlet_feature WHERE name = 'Chỗ Đậu Xe')),
(26, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí')),
(27, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(28, (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM outlet_feature WHERE name = 'Khu Vực Ngoài Trời')),
(29, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí')),
(30, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(31, (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi')),
(32, (SELECT id FROM outlet WHERE name = 'Bánh Mì Bà Lan'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi')),
(33, (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(34, (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), (SELECT id FROM outlet_feature WHERE name = 'Chỗ Đậu Xe')),
(35, (SELECT id FROM outlet WHERE name = 'Bánh Xèo Mười Xiềm'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(36, (SELECT id FROM outlet WHERE name = 'Cà Phê Cộng'), (SELECT id FROM outlet_feature WHERE name = 'Khu Vực Ngoài Trời')),
(37, (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh')),
(38, (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), (SELECT id FROM outlet_feature WHERE name = 'Dịch Vụ Mang Đi'));

-- Thêm reviews cho các outlets mới để có ratings đa dạng
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES 
('50000000-0000-0000-0000-000000000004', (SELECT id FROM outlet WHERE name = 'Phở Gia Truyền'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), NULL, 5, 5, 4, 5, 5, 'Phở ngon đậm đà, nước dùng thơm, thịt mềm', 10, 0),
('50000000-0000-0000-0000-000000000005', (SELECT id FROM outlet WHERE name = 'Bún Bò Huế Cô Ba'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), NULL, 4, 4, 4, 4, 4, 'Bún bò cay nồng đúng vị Huế', 5, 0),
('50000000-0000-0000-0000-000000000006', (SELECT id FROM outlet WHERE name = 'Cơm Tấm Cali'), (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), NULL, 5, 4, 3, 5, 4, 'Cơm tấm ngon, sườn nướng thơm', 8, 0),
('50000000-0000-0000-0000-000000000007', (SELECT id FROM outlet WHERE name = 'Cà Phê Sài Gòn Xưa'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), NULL, 5, 5, 5, 5, 5, 'Cà phê đậm đà, không gian cổ điển đẹp', 15, 0),
('50000000-0000-0000-0000-000000000008', (SELECT id FROM outlet WHERE name = 'Trà Sữa Gong Cha'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), NULL, 4, 4, 4, 3, 4, 'Trà sữa ngon, nhiều topping', 6, 0),
('50000000-0000-0000-0000-000000000009', (SELECT id FROM outlet WHERE name = 'Bánh Mì Bà Lan'), (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), NULL, 5, 4, 3, 5, 5, 'Bánh mì giòn, thịt nướng thơm ngon', 12, 0),
('50000000-0000-0000-0000-000000000010', (SELECT id FROM outlet WHERE name = 'Lẩu Cá Kèo'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), NULL, 5, 4, 4, 4, 4, 'Lẩu cá kèo đậm đà, cá tươi ngon', 7, 0),
('50000000-0000-0000-0000-000000000011', (SELECT id FROM outlet WHERE name = 'Bánh Xèo Mười Xiềm'), (SELECT id FROM user_account WHERE username = 'ngoc.do_31'), NULL, 4, 4, 3, 4, 4, 'Bánh xèo giòn tan, nhân đầy đủ', 5, 0),
('50000000-0000-0000-0000-000000000012', (SELECT id FROM outlet WHERE name = 'Cà Phê Cộng'), (SELECT id FROM user_account WHERE username = 'lam.hoang_71'), NULL, 4, 3, 4, 5, 4, 'Cà phê vỉa hè đúng chất Sài Gòn', 4, 0),
('50000000-0000-0000-0000-000000000013', (SELECT id FROM outlet WHERE name = 'Bún Riêu Cua'), (SELECT id FROM user_account WHERE username = 'ha.hoang_97'), NULL, 5, 4, 3, 4, 5, 'Bún riêu cua đậm đà, cua tươi', 9, 0);

-- 🔑 1. Profile (BIGINT ID)
SELECT setval(pg_get_serial_sequence('profile', 'id'), (SELECT COALESCE(MAX(id), 0) FROM profile) + 1, false);

-- 🔑 2. Refresh Token (BIGINT ID)
SELECT setval(pg_get_serial_sequence('refresh_token', 'id'), (SELECT COALESCE(MAX(id), 0) FROM refresh_token) + 1, false);

-- 🔑 3. Password Reset Token (BIGINT ID)
SELECT setval(pg_get_serial_sequence('password_reset_token', 'id'), (SELECT COALESCE(MAX(id), 0) FROM password_reset_token) + 1, false);

-- 🔑 4. Role (INTEGER ID)
SELECT setval(pg_get_serial_sequence('role', 'id'), (SELECT COALESCE(MAX(id), 0) FROM role) + 1, false);

-- 🔑 5. Permission (INTEGER ID)
SELECT setval(pg_get_serial_sequence('permission', 'id'), (SELECT COALESCE(MAX(id), 0) FROM permission) + 1, false);

-- 🔑 6. Role Permission (BIGINT ID)
SELECT setval(pg_get_serial_sequence('role_permission', 'id'), (SELECT COALESCE(MAX(id), 0) FROM role_permission) + 1, false);

-- 🔑 7. Sharing List (INTEGER ID)
SELECT setval(pg_get_serial_sequence('sharing_list', 'id'), (SELECT COALESCE(MAX(id), 0) FROM sharing_list) + 1, false);

-- 🔑 8. Sharing List Collaborator (BIGINT ID)
SELECT setval(pg_get_serial_sequence('sharing_list_collaborator', 'id'), (SELECT COALESCE(MAX(id), 0) FROM sharing_list_collaborator) + 1, false);

-- 🔑 9. Outlet Type (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_type', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_type) + 1, false);

-- 🔑 10. Outlet Category (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_category', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_category) + 1, false);

-- 🔑 11. Outlet Feature (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_feature', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_feature) + 1, false);

-- 🔑 12. Outlet Feature Mapping (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_feature_mapping', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_feature_mapping) + 1, false);

-- 🔑 13. Outlet Image (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_image', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_image) + 1, false);

-- 🔑 14. Operating Hours (INTEGER ID)
SELECT setval(pg_get_serial_sequence('operating_hours', 'id'), (SELECT COALESCE(MAX(id), 0) FROM operating_hours) + 1, false);

-- 🔑 15. Menu Item Type (INTEGER ID)
SELECT setval(pg_get_serial_sequence('menu_item_type', 'id'), (SELECT COALESCE(MAX(id), 0) FROM menu_item_type) + 1, false);

-- 🔑 16. Menu Item Category (INTEGER ID)
SELECT setval(pg_get_serial_sequence('menu_item_category', 'id'), (SELECT COALESCE(MAX(id), 0) FROM menu_item_category) + 1, false);

-- 🔑 17. Menu Item Sub Category (INTEGER ID)
SELECT setval(pg_get_serial_sequence('menu_item_sub_category', 'id'), (SELECT COALESCE(MAX(id), 0) FROM menu_item_sub_category) + 1, false);

-- 🔑 18. Outlet Menu Item (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_menu_item', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_menu_item) + 1, false);

-- 🔑 19. Menu Item Feature (INTEGER ID)
SELECT setval(pg_get_serial_sequence('menu_item_feature', 'id'), (SELECT COALESCE(MAX(id), 0) FROM menu_item_feature) + 1, false);

-- 🔑 20. Outlet Menu Item Feature (INTEGER ID)
SELECT setval(pg_get_serial_sequence('outlet_menu_item_feature', 'id'), (SELECT COALESCE(MAX(id), 0) FROM outlet_menu_item_feature) + 1, false);

-- 🔑 21. Review Image (INTEGER ID)
SELECT setval(pg_get_serial_sequence('review_image', 'id'), (SELECT COALESCE(MAX(id), 0) FROM review_image) + 1, false);

-- 🔑 22. Review Reply (INTEGER ID)
SELECT setval(pg_get_serial_sequence('review_reply', 'id'), (SELECT COALESCE(MAX(id), 0) FROM review_reply) + 1, false);

-- 🔑 23. Review Reaction (BIGINT ID)
SELECT setval(pg_get_serial_sequence('review_reaction', 'id'), (SELECT COALESCE(MAX(id), 0) FROM review_reaction) + 1, false);

-- 🔑 24. Review Report (INTEGER ID)
SELECT setval(pg_get_serial_sequence('review_report', 'id'), (SELECT COALESCE(MAX(id), 0) FROM review_report) + 1, false);

-- 🔑 25. Notification (BIGINT ID)
SELECT setval(pg_get_serial_sequence('notification', 'id'), (SELECT COALESCE(MAX(id), 0) FROM notification) + 1, false);

-- 🔑 26. Membership Plan (INTEGER ID)
SELECT setval(pg_get_serial_sequence('membership_plan', 'id'), (SELECT COALESCE(MAX(id), 0) FROM membership_plan) + 1, false);

-- 🔑 27. User Membership (BIGINT ID)
SELECT setval(pg_get_serial_sequence('user_membership', 'id'), (SELECT COALESCE(MAX(id), 0) FROM user_membership) + 1, false);

-- 🔑 28. Advertisement (INTEGER ID)
SELECT setval(pg_get_serial_sequence('advertisement', 'id'), (SELECT COALESCE(MAX(id), 0) FROM advertisement) + 1, false);

-- 🔑 29. Country (INTEGER ID)
SELECT setval(pg_get_serial_sequence('country', 'id'), (SELECT COALESCE(MAX(id), 0) FROM country) + 1, false);

-- 🔑 30. Province (INTEGER ID)
SELECT setval(pg_get_serial_sequence('province', 'id'), (SELECT COALESCE(MAX(id), 0) FROM province) + 1, false);

-- 🔑 31. District (INTEGER ID)
SELECT setval(pg_get_serial_sequence('district', 'id'), (SELECT COALESCE(MAX(id), 0) FROM district) + 1, false);

--================================================================================================================
--================================================================================================================
-- Các lệnh này đảm bảo is_deleted trong DB được đồng bộ là false
--================================================================================================================
--================================================================================================================

-- 1. user_account
UPDATE user_account SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 2. profile
UPDATE profile SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 3. refresh_token
UPDATE refresh_token SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 4. password_reset_token
UPDATE password_reset_token SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 5. role
UPDATE role SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 6. permission
UPDATE permission SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 7. role_permission
UPDATE role_permission SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 8. sharing_list
UPDATE sharing_list SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 9. sharing_list_collaborator
UPDATE sharing_list_collaborator SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 10. outlet
UPDATE outlet SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 11. outlet_type
UPDATE outlet_type SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 12. outlet_category
UPDATE outlet_category SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 13. outlet_feature
UPDATE outlet_feature SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 14. outlet_feature_mapping
UPDATE outlet_feature_mapping SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 15. outlet_image
UPDATE outlet_image SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 16. operating_hours
UPDATE operating_hours SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 17. menu_item
UPDATE menu_item SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 18. menu_item_type
UPDATE menu_item_type SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 19. menu_item_category
UPDATE menu_item_category SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 20. menu_item_sub_category
UPDATE menu_item_sub_category SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 21. outlet_menu_item
UPDATE outlet_menu_item SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 22. menu_item_feature
UPDATE menu_item_feature SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 23. outlet_menu_item_feature
UPDATE outlet_menu_item_feature SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 24. booking
UPDATE booking SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 25. payment
UPDATE payment SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 26. review
UPDATE review SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 27. review_image
UPDATE review_image SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 28. review_reply
UPDATE review_reply SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 29. review_reaction
UPDATE review_reaction SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 30. review_report
UPDATE review_report SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 31. notification
UPDATE notification SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 32. membership_plan
UPDATE membership_plan SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 33. user_membership
UPDATE user_membership SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 34. advertisement
UPDATE advertisement SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 35. country
UPDATE country SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 36. province
UPDATE province SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 37. district
UPDATE district SET is_deleted = FALSE WHERE is_deleted IS NULL;

