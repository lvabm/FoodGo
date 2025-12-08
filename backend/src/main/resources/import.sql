--========================================================
--1. COUNTRY
--========================================================
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
--9. MENU_ITEM (Level 4): Biến thế của các món ăn hiện hữu
--========================================================
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000001', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Tái Gân', 'Phở được nấu từ xương bò 8 tiếng, kèm gân bò giòn', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000002', (SELECT id FROM menu_item_sub_category WHERE name = 'Nước Ngọt Có Ga'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Coca Cola Lon', 'Nước ngọt có ga Coca Cola dạng lon 330ml',true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000003', (SELECT id FROM menu_item_sub_category WHERE name = 'Cơm Chiên'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Cơm Chiên Dương Châu Đặc Biệt', 'Cơm chiên tơi xốp với trứng, tôm, lạp xưởng', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000004', (SELECT id FROM menu_item_sub_category WHERE name = 'Trà Sữa'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Trà Sữa Trân Châu Đường Đen', 'Trà sữa thơm béo, trân châu dai giòn', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000005', (SELECT id FROM menu_item_sub_category WHERE name = 'Bánh Kem Mousse'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Bánh Mousse Chanh Leo', 'Bánh Mousse vị chanh leo chua ngọt, mát lạnh', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000006', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò Viên Nước Trong', 'Phở truyền thống với thịt bò viên dai ngon và nước dùng trong', false);

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
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('10000000-0000-0000-0000-000000000000', 'salvabm', '22022022', 'salvabm@foodgo.com', '0911295205', true, (SELECT id FROM role WHERE name = 'ROLE_SYSTEM_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('10000000-0000-0000-0000-000000000001', 'admin1', 'plain_password_123', 'admin1@example.com', '0901234568', true, (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('10000000-0000-0000-0000-000000000002', 'owner1', 'plain_password_123', 'owner1@example.com', '0901234587', true, (SELECT id FROM role WHERE name = 'ROLE_OWNER'));
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active, role_id) VALUES ('10000000-0000-0000-0000-000000000003', 'user1', 'plain_password_123', 'user1@example.com', '0901234569', true, (SELECT id FROM role WHERE name = 'ROLE_USER'));

--========================================================
--14. PROFILE
--========================================================
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'salvabm'), 'Lê Văn An', '2005-03-22', 'Quận 8, TP. HCM', 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (2, (SELECT id FROM user_account WHERE username = 'admin1'), 'Admin 1', '1995-05-08', 'Quận 1, TP. HCM', 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (3, (SELECT id FROM user_account WHERE username = 'owner1'), 'Owner 1', '1995-05-10', 'Quận 3, TP. HCM', 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (4, (SELECT id FROM user_account WHERE username = 'user1'), 'User 1', '1995-05-09', 'Quận 2, TP. HCM', 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));

--========================================================
--15. REFRESH_TOKEN
--========================================================
INSERT INTO refresh_token (id, user_id, token, device_info, ip_address, is_revoked, expires_at, created_at) VALUES (1, (SELECT id FROM user_account WHERE username = 'owner1'), 'refresh-token-sample-1', 'Chrome on Windows', '127.0.0.1', false, NOW() + INTERVAL '14 days', NOW());

--========================================================
--16. PASSWORD_RESET_TOKEN
--========================================================
INSERT INTO password_reset_token (id, user_id, token, expires_at, is_used) VALUES (1, (SELECT id FROM user_account WHERE username = 'owner1'), 'pw-reset-token-1', NOW() + INTERVAL '30 minutes', false);

--========================================================
--17. MEMBERSHIP_PLAN
--========================================================
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (1, 'Free', 'Gói miễn phí', 0.00, 0, 10, '["basic-listing"]');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (2, 'Basic', 'Gói đăng ký cơ bản dành cho cá nhân.', 9.99, 12, 50, '["basic-listing", "priority-email"]');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (3, 'Standard', 'Gói tiêu chuẩn, phù hợp cho các nhóm nhỏ (small teams).', 29.99, 12, 100, '["basic-listing", "priority-email", "analytics-dashboard"]');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (4, 'Premium', 'Gói cao cấp với đầy đủ tính năng và hỗ trợ chuyên biệt (dedicated support).', 49.99, 12, 300, '["all-standard-features", "dedicated-support", "early-access-features"]');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (5, 'Enterprise', 'Gói tùy chỉnh dành cho các tổ chức lớn (large organizations).', 99.99, 0, 0, '["all-premium-features", "custom-sla", "account-manager"]');

--========================================================
--18. USER_MEMBERSHIP
--========================================================
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date, is_active) VALUES (1, (SELECT id FROM user_account WHERE username = 'user1'), (SELECT id FROM membership_plan WHERE name = 'Free'), CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', true);

--========================================================
--19. SHARING_LIST
--========================================================
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (1, (SELECT id FROM user_account WHERE username = 'user1'), 'My Favorites', 'Danh sách địa điểm yêu thích', false, false);

--========================================================
--20. SHARING_LIST_COLLABORATOR
--========================================================
INSERT INTO sharing_list_collaborator (id, user_id, sharing_list_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'user1'), (SELECT id FROM sharing_list WHERE name = 'My Favorites'));

--========================================================
--21. OUTLET
--========================================================
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews) VALUES ('20000000-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username = 'owner1'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Quán Phở Alice', 'Quán phở ngon, phục vụ nhanh', '123 Lê Lợi, Quận 1, TP.HCM', 'pho.owner1@example.com', '0909000002', 'https://example.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.775839, 106.700806, 'moderate', 80, true, 4.85, 120);
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews) VALUES ('20000000-0000-0000-0000-000000000002', (SELECT id FROM user_account WHERE username = 'owner1'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Quán Phở Isa', 'Quán phở ngon, phục vụ tốt', '22 Lê Lợi, Quận 1, TP.HCM', 'pho.owner1@example.com', '0909000002', 'https://example.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.775839, 106.700806, 'moderate', 80, true, 4.85, 120);

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
--23. OUTLET_FEATURE_MAPPING
--========================================================
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM outlet_feature WHERE name = 'Wifi Miễn Phí'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (2, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM outlet_feature WHERE name = 'Khu Vực Ngoài Trời'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (3, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM outlet_feature WHERE name = 'Máy Lạnh'));
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (4, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM outlet_feature WHERE name = 'Chỗ Đậu Xe'));

--========================================================
--24. OUTLET_IMAGE
--========================================================
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), 'https://cdn.example.com/outlet/pho-owner1-1.jpg', 1, true);

--========================================================
--25. OPERATING_HOURS
--========================================================
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), 1, '07:00', '21:00', false);

--========================================================
--26. OUTLET_MENU_ITEM
--========================================================
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'),  (SELECT id FROM menu_item WHERE name = 'Phở Bò Tái Gân'), 'Phở Bò Tái Alice', 'Phở bò tái ngon', 75000.00, 'https://cdn.example.com/menu/pho-bo-1.jpg', true);

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

--========================================================
--29. BOOKING
--========================================================
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM user_account WHERE username = 'user1'), CURRENT_DATE + INTERVAL '3 days', '19:00', 4, 'PENDING', 100000.00, 'Ngồi tầng 1, gần cửa sổ', NULL);

--========================================================
--30. PAYMENT
--========================================================
INSERT INTO payment (id, booking_id, amount, payment_method, payment_status, transaction_id) VALUES ('40000000-0000-0000-0000-000000000001', (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000001'), 100000.00, 'MOMO', 'PENDING', 'TXN123456789');

--========================================================
--31. REVIEW
--========================================================
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES ('50000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM user_account WHERE username = 'user1'), (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000001'), 5, 5, 4, 4, 5, 'Nước dùng đậm đà, thịt mềm', 3, 0);

--========================================================
--32. REVIEW_IMAGE
--========================================================
INSERT INTO review_image (id, review_id, image_url) VALUES (1, (SELECT id FROM review WHERE comment = 'Nước dùng đậm đà, thịt mềm'), 'https://cdn.example.com/review/5001.jpg');

--========================================================
--33. REVIEW_REPLY
--========================================================
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (1, (SELECT id FROM review WHERE comment = 'Nước dùng đậm đà, thịt mềm'), (SELECT owner_id FROM outlet WHERE name = 'Quán Phở Alice'), 'Cảm ơn bạn đã ủng hộ! ❤️');

--========================================================
--34. REVIEW_REACTION
--========================================================
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (1, (SELECT id FROM review WHERE comment = 'Nước dùng đậm đà, thịt mềm'), (SELECT id FROM user_account WHERE username = 'user1'), 'like');

--========================================================
--35. REVIEW_REPORT
--========================================================
INSERT INTO review_report (id, review_id, reporter_id, reason, status) VALUES (1, (SELECT id FROM review WHERE comment = 'Nước dùng đậm đà, thịt mềm'), (SELECT id FROM user_account WHERE username = 'user1'), 'Nội dung không phù hợp', 'pending');

--========================================================
--36. NOTIFICATION
--========================================================
INSERT INTO notification (id, user_id, type, title, content, related_id, is_read) VALUES (1, (SELECT id FROM user_account WHERE username = 'user1'), 'review', 'Bạn có phản hồi mới!', 'Chủ quán đã trả lời review của bạn.', '50000000-0000-0000-0000-000000000001', false);

--========================================================
--37. ADVERTISEMENT
--========================================================
INSERT INTO advertisement (id, outlet_id, position, start_date, end_date, is_active) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), 'featured', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', true);

--================================================================================================================
--================================================================================================================
-- Các lệnh này đảm bảo Sequence trong DB được đồng bộ với ID lớn nhất hiện tại, tránh lỗi duplicate key.
--================================================================================================================
--================================================================================================================

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