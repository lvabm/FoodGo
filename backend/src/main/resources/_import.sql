-- Ngoài trừ refresh_token và password_reset_token (36/38)

-- COUNTRY (NOTE: DONE)
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

-- PROVINCE (NOTE: DONE)
INSERT INTO province (id, name, country_id) VALUES (1, 'Hà Nội', 1);
INSERT INTO province (id, name, country_id) VALUES (2, 'TP.HCM', 1);
INSERT INTO province (id, name, country_id) VALUES (3, 'Osaka', 2);
INSERT INTO province (id, name, country_id) VALUES (4, 'Seoul', 3);
INSERT INTO province (id, name, country_id) VALUES (5, 'Đà Nẵng', 3);
INSERT INTO province (id, name, country_id) VALUES (6, 'Paris', 7);

-- DISTRICT (NOTE: DONE)
INSERT INTO district (id, name, province_id) VALUES (1, 'Hoàn Kiếm', 1);
INSERT INTO district (id, name, province_id) VALUES (2, 'Ba Đình', 1);
INSERT INTO district (id, name, province_id) VALUES (3, 'Cầu Giấy', 1);
INSERT INTO district (id, name, province_id) VALUES (4, 'Quận 1', 2);
INSERT INTO district (id, name, province_id) VALUES (5, 'Quận 3', 2);
INSERT INTO district (id, name, province_id) VALUES (6, 'Bình Thạnh', 2);
INSERT INTO district (id, name, province_id) VALUES (7, 'Hải Châu', 5);
INSERT INTO district (id, name, province_id) VALUES (8, 'Sơn Trà', 6);
INSERT INTO district (id, name, province_id) VALUES (9, 'Shinsaibashi', 3);
INSERT INTO district (id, name, province_id) VALUES (10, 'Gangnam', 4);
INSERT INTO district (id, name, province_id) VALUES (11, 'Phú Nhuận', 2);

-- ROLE & PERMISSION (NOTE: DONE)
INSERT INTO role (id, name, description) VALUES (1, 'USER', 'Người dùng');
INSERT INTO role (id, name, description) VALUES (2, 'OWNER', 'Chủ quán');
INSERT INTO role (id, name, description) VALUES (3, 'ADMIN', 'Quản trị viên');
INSERT INTO role (id, name, description) VALUES (4, 'SYSTEM_ADMIN', 'Quản trị hệ thống');
INSERT INTO role (id, name, description) VALUES (5, 'GUEST', 'Khách vãng lai');

INSERT INTO permission (id, name, description) VALUES (1, 'READ', 'Quyền đọc');
INSERT INTO permission (id, name, description) VALUES (2, 'WRITE', 'Quyền ghi');
INSERT INTO permission (id, name, description) VALUES (3, 'DELETE', 'Quyền xóa');
INSERT INTO permission (id, name, description) VALUES (4, 'UPDATE', 'Quyền cập nhật');
INSERT INTO permission (id, name, description) VALUES (5, 'MANAGE', 'Quyền quản lý');

-- OUTLET TYPE & CATEGORY (NOTE: DONE)
INSERT INTO outlet_type (id, name, description) VALUES (1, 'Quán cà phê', 'Cafe, không gian làm việc (Work shop)');
INSERT INTO outlet_type (id, name, description) VALUES (2, 'Quán ăn/Lẩu', 'Quán ăn bình dân, lẩu, món địa phương');
INSERT INTO outlet_type (id, name, description) VALUES (3, 'Bar/Club', 'Bar, Club, Nơi vui chơi về đêm');
INSERT INTO outlet_type (id, name, description) VALUES (4, 'Chuỗi cà phê/Trà', 'Chuỗi cửa hàng lớn (Highlands, TCH)');
INSERT INTO outlet_type (id, name, description) VALUES (5, 'Pub/Bar', 'Quán rượu, bia thủ công');
INSERT INTO outlet_type (id, name, description) VALUES (7, 'Ẩm thực đường phố', 'Các món ăn đường phố, đặc sản địa phương');
INSERT INTO outlet_type (id, name, description) VALUES (9, 'Quán ăn Châu Á', 'Cơm Niêu, các món ăn theo phần');
INSERT INTO outlet_type (id, name, description) VALUES (10, 'Trà/Trà sữa', 'Cửa hàng chuyên trà và trà sữa');
INSERT INTO outlet_type (id, name, description) VALUES (11, 'Nhà hàng', 'Nhà hàng Ẩm thực cao cấp, Steakhouse');
INSERT INTO outlet_type (id, name, description) VALUES (12, 'Pizza/Ý', 'Nhà hàng chuyên Pizza và ẩm thực Ý');
INSERT INTO outlet_type (id, name, description) VALUES (13, 'Coffee Roasters', 'Quán cà phê tự rang xay (Specialty Coffee)');
INSERT INTO outlet_type (id, name, description) VALUES (14, 'Nhà hàng Chay', 'Nhà hàng phục vụ món chay');
INSERT INTO outlet_type (id, name, description) VALUES (15, 'Skybar', 'Bar trên cao có view đẹp');
INSERT INTO outlet_type (id, name, description) VALUES (16, 'Speakeasy Bar', 'Bar chuyên đồ uống mạnh (Gin, Whiskey)');

-- Type ID = 1 (Quán cà phê)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (1, 'Specialty Coffee', 1, 'Cafe rang xay, thủ công (Manual Brew)');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (2, 'Cafe view đẹp', 1, 'Cafe có không gian/view lý tưởng');

-- Type ID = 2 (Quán ăn/Lẩu)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (3, 'Lẩu & Nướng bình dân', 2, 'Lẩu, nướng vỉa hè hoặc bình dân');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (4, 'Món Việt truyền thống', 2, 'Phở, bún, cơm tấm...');

-- Type ID = 3 (Bar/Club)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (5, 'Club đêm', 3, 'Các quán bar sàn, club lớn');

-- Type ID = 4 (Chuỗi cà phê/Trà)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (6, 'Chuỗi cà phê (TCH, HL)', 4, 'Các chuỗi cà phê lớn tại Việt Nam');

-- Type ID = 5 (Pub/Bar)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (7, 'Craft Beer Pub', 5, 'Quán Pub chuyên bia thủ công');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (8, 'Irish Pub', 5, 'Quán Pub phong cách Ireland/châu Âu');

-- Type ID = 7 (Ẩm thực đường phố)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (9, 'Bánh & Đồ chiên', 7, 'Bánh xèo, bánh khọt, bánh mì...');

-- Type ID = 9 (Quán ăn Châu Á)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (10, 'Ẩm thực Singapore/Thái', 9, 'Cơm Niêu, các món ăn đặc trưng Châu Á');

-- Type ID = 10 (Trà/Trà sữa)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (11, 'Trà sữa/Topping', 10, 'Cửa hàng chuyên các loại trà sữa');

-- Type ID = 11 (Nhà hàng Ẩm thực cao cấp)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (12, 'Steakhouse', 11, 'Nhà hàng chuyên bò bít tết cao cấp');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (13, 'Fine Dining', 11, 'Nhà hàng ẩm thực cao cấp, phong cách Âu');

-- Type ID = 12 (Pizza/Ý)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (14, 'Pizza (Nướng củi)', 12, 'Pizza kiểu Ý truyền thống');

-- Type ID = 13 (Coffee Roasters)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (15, 'Sản phẩm rang xay', 13, 'Cà phê được rang xay trực tiếp tại quán');

-- Type ID = 14 (Nhà hàng Chay)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (16, 'Ẩm thực Chay dưỡng sinh', 14, 'Món chay có lợi cho sức khỏe, thanh tịnh');

-- Type ID = 15 (Skybar)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (17, 'Skybar view đêm', 15, 'Quán bar trên cao có view thành phố');

-- Type ID = 16 (Speakeasy Bar)
INSERT INTO outlet_category (id, name, type_id, description) VALUES (18, 'Cocktail/Gin Bar', 16, 'Quán bar chuyên cocktail cổ điển và Gin');

-- FNB TYPES, CATEGORIES, SUB-CATEGORIES (NOTE: DONE)
INSERT INTO menu_item_type (id, name, description) VALUES (1, 'Món chính', 'Món ăn chính');
INSERT INTO menu_item_type (id, name, description) VALUES (2, 'Món phụ', 'Món ăn phụ');
INSERT INTO menu_item_type (id, name, description) VALUES (3, 'Đồ uống', 'Nước uống');
INSERT INTO menu_item_type (id, name, description) VALUES (4, 'Tráng miệng', 'Món tráng miệng');
INSERT INTO menu_item_type (id, name, description) VALUES (5, 'Ăn vặt', 'Đồ ăn vặt');

INSERT INTO menu_item_category (id, name, type_id, description) VALUES (1, 'Cơm', 1, 'Các món cơm');
INSERT INTO menu_item_category (id, name, type_id, description) VALUES (2, 'Mì', 1, 'Các món mì');
INSERT INTO menu_item_category (id, name, type_id, description) VALUES (3, 'Nước ngọt', 3, 'Đồ uống ngọt');
INSERT INTO menu_item_category (id, name, type_id, description) VALUES (4, 'Bánh ngọt', 4, 'Bánh tráng miệng');
INSERT INTO menu_item_category (id, name, type_id, description) VALUES (5, 'Snack', 5, 'Đồ ăn vặt');

INSERT INTO menu_item_sub_category (id, name, category_id, description) VALUES (1, 'Cơm tấm', 1, 'Cơm tấm Sài Gòn');
INSERT INTO menu_item_sub_category (id, name, category_id, description) VALUES (2, 'Mì Quảng', 2, 'Đặc sản miền Trung');
INSERT INTO menu_item_sub_category (id, name, category_id, description) VALUES (3, 'Pepsi', 3, 'Nước ngọt Pepsi');
INSERT INTO menu_item_sub_category (id, name, category_id, description) VALUES (4, 'Bánh flan', 4, 'Bánh flan tráng miệng');
INSERT INTO menu_item_sub_category (id, name, category_id, description) VALUES (5, 'Khoai tây chiên', 5, 'Snack khoai tây');

-- FEATURES (NOTE: DONE)
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

-- MEMBERSHIP PLAN & SHARING LIST (NOTE: DONE)
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (1, 'Miễn Phí (Free)', 'Truy cập cơ bản các tính năng', 0, 1, 10, 'Xem review, theo dõi quán');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (2, 'Bạc (Silver)', 'Ưu đãi và giảm giá chọn lọc', 49000, 3, 50, 'Giảm giá nhẹ, hỗ trợ thường');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (3, 'Vàng (Gold)', 'Ưu đãi giảm giá mạnh', 199000, 6, 100, 'Ưu đãi cao, tích điểm nhanh');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (4, 'Bạch Kim (Platinum)', 'Ưu đãi cao cấp & được ưu tiên', 399000, 6, 200, 'Ưu tiên hỗ trợ, quà tặng');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (5, 'Doanh Nghiệp (Business)', 'Gói dành cho các chủ quán nhỏ', 999000, 12, 500, 'Cung cấp Công cụ Owner, quản lý cơ bản');
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (6, 'Tập Đoàn (Enterprise)', 'Gói giải pháp cho chuỗi/quy mô lớn', 2499000, 12, 2000, 'Giải pháp doanh nghiệp tùy chỉnh');

--(NOTE: NODONE)
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (1, (SELECT id FROM user_account WHERE username='customer1'), 'Quán cà phê yên tĩnh học tập', 'Các quán cafe tĩnh mịch phù hợp để học bài', true, true);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (2, (SELECT id FROM user_account WHERE username='customer1'), 'Quán ăn sáng ngon', 'Những quán ăn sáng yêu thích', false, false);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (3, (SELECT id FROM user_account WHERE username='customer1'), 'Nhà hàng hẹn hò lãng mạn', 'Nhà hàng sang trọng cho buổi tối lãng mạn', true, false);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (4, (SELECT id FROM user_account WHERE username='customer1'), 'Quán nhậu cuối tuần', 'Quán ăn nhậu với bạn bè', false, true);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (5, (SELECT id FROM user_account WHERE username='customer1'), 'Quán chay Hà Nội', 'Các quán ăn chay tại Hà Nội', true, true);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (6, (SELECT id FROM user_account WHERE username='customer2'), 'Street Food Sài Gòn', 'Ẩm thực đường phố Sài Gòn', true, true);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (7, (SELECT id FROM user_account WHERE username='customer2'), 'Coffee Shops HN', 'Các quán cafe đẹp Hà Nội', true, false);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (8, (SELECT id FROM user_account WHERE username='customer2'), 'Budget Eats HCMC', 'Quán ăn giá rẻ TP.HCM', true, true);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (9, (SELECT id FROM user_account WHERE username='customer2'), 'Da Nang Must Try', 'Phải thử khi đến Đà Nẵng', true, false);
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (10, (SELECT id FROM user_account WHERE username='customer2'), 'Late Night Spots', 'Quán mở cửa đến khuya', false, true);

-- USER ACCOUNT (NOTE: DONE)
    -- Customer
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000001', 'customer1', 'plain_password_123', 'customer1@example.com', '0901234501', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000002', 'customer2', 'plain_password_123', 'customer2@example.com', '0901234502', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000003', 'customer3', 'plain_password_123', 'customer3@example.com', '0901234503', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000004', 'customer4', 'plain_password_123', 'customer4@example.com', '0901234504', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000005', 'customer5', 'plain_password_123', 'customer5@example.com', '0901234505', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000006', 'customer6', 'plain_password_123', 'customer6@example.com', '0901234506', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000007', 'customer7', 'plain_password_123', 'customer7@example.com', '0901234507', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000008', 'customer8', 'plain_password_123', 'customer8@example.com', '0901234508', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000009', 'customer9', 'plain_password_123', 'customer9@example.com', '0901234509', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000010', 'customer10', 'plain_password_123', 'customer10@example.com', '0901234510', true);

    -- Owner
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000011', 'owner1', 'plain_password_123', 'owner1@example.com', '0901234511', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000012', 'owner2', 'plain_password_123', 'owner2@example.com', '0901234512', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000013', 'owner3', 'plain_password_123', 'owner3@example.com', '0901234513', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000014', 'owner4', 'plain_password_123', 'owner4@example.com', '0901234514', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000015', 'owner5', 'plain_password_123', 'owner5@example.com', '0901234515', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000016', 'owner6', 'plain_password_123', 'owner6@example.com', '0901234516', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000017', 'owner7', 'plain_password_123', 'owner7@example.com', '0901234517', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000018', 'owner8', 'plain_password_123', 'owner8@example.com', '0901234518', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000019', 'owner9', 'plain_password_123', 'owner9@example.com', '0901234519', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('00000000-0000-0000-0000-000000000020', 'owner10', 'plain_password_123', 'owner10@example.com', '0901234520', true);

-- PROFILE (NOTE: DONE) (avatar_url default "https://default-avatar.png")
    -- Customer
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer1'), 'Nguyễn Văn An', '1995-03-15', 'Hoàn Kiếm, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer2'), 'Trần Thị Bình', '1992-07-22', 'Quận 1, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer3'), 'Lê Minh Cường', '1998-11-08', 'Ba Đình, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer4'), 'Phạm Thu Dung', '1996-05-30', 'Quận 3, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer5'), 'Hoàng Văn Em', '1994-09-12', 'Cầu Giấy, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer6'), 'Đỗ Thị Phượng', '1997-02-18', 'Bình Thạnh, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer7'), 'Vũ Minh Giang', '1993-12-25', 'Hoàn Kiếm, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer8'), 'Bùi Thị Hoa', '1999-06-14', 'Quận 1, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer9'), 'Đinh Văn Ích', '1991-04-09', 'Hải Châu, Đà Nẵng', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='customer10'), 'Mai Thị Kim', '2000-08-20', 'Sơn Trà, Đà Nẵng', 1);

    -- Owner
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner1'), 'Nguyễn Văn Khánh', '1985-01-10', 'Hoàn Kiếm, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner2'), 'Trần Văn Long', '1988-05-15', 'Quận 1, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner3'), 'Lê Thị Mai', '1987-09-22', 'Ba Đình, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner4'), 'Phạm Văn Nam', '1986-03-28', 'Quận 3, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner5'), 'Hoàng Thị Oanh', '1989-07-11', 'Cầu Giấy, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner6'), 'Đỗ Văn Phong', '1984-11-05', 'Bình Thạnh, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner7'), 'Vũ Thị Quỳnh', '1990-02-14', 'Hoàn Kiếm, Hà Nội', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner8'), 'Bùi Văn Sơn', '1983-06-19', 'Quận 1, TP.HCM', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner9'), 'Đinh Thị Tâm', '1991-10-23', 'Hải Châu, Đà Nẵng', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, country_id) VALUES ((SELECT id FROM user_account WHERE username='owner10'), 'Mai Văn Uy', '1982-12-30', 'Sơn Trà, Đà Nẵng', 1);

-- OUTLET (NOTE: DONE)
    -- -- Cafe outlets (1-10)
        -- Quận 1 (District_id = 4)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000001-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username='owner2'), 1, 'The Workshop Coffee', '27 Ngô Đức Kế, Bến Nghé, Quận 1', '02838246801', 'https://www.facebook.com/the.workshop.coffee', 4, 10.77347640, 106.70560370, true, 4.5, 1250);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000002-0000-0000-0000-000000000002', (SELECT id FROM user_account WHERE username='owner2'), 1, 'Highlands Coffee Lý Tự Trọng', '71 Lý Tự Trọng, Bến Thành, Quận 1', '02871080071', 'https://highlandscoffee.com.vn', 4, 10.77515200, 106.70180300, true, 4.0, 980);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000003-0000-0000-0000-000000000003', (SELECT id FROM user_account WHERE username='owner1'), 1, 'Starbucks Nguyễn Huệ', '99 Nguyễn Huệ, Bến Nghé, Quận 1', '02838210105', 'https://starbucks.vn', 4, 10.77412100, 106.70196700, true, 4.1, 1560);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000004-0000-0000-0000-000000000004', (SELECT id FROM user_account WHERE username='owner3'), 1, 'Vintage Emporium Coffee', '95B Nguyễn Văn Thủ, Đa Kao, Quận 1', '0904413148', NULL, 4, 10.77112300, 106.69865400, true, 4.6, 1123);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000005-0000-0000-0000-000000000005', (SELECT id FROM user_account WHERE username='owner2'), 1, 'LUsine Cafe', '19 Lê Thánh Tôn, Bến Nghé, Quận 1', '02838227188', 'https://lusinespace.com', 4, 10.77879400, 106.70192800, true, 4.3, 2100);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000006-0000-0000-0000-000000000006', (SELECT id FROM user_account WHERE username='owner1'), 1, 'Trung Nguyên Legend Cafe', '12 Alexandre de Rhodes, Bến Nghé, Quận 1', '19006616', 'https://trungnguyenlegend.com', 4, 10.77678900, 106.70341200, true, 4.2, 756);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000007-0000-0000-0000-000000000007', (SELECT id FROM user_account WHERE username='owner1'), 1, 'Soo Kafe', '10 Phan Kế Bính, Đa Kao, Quận 1', '0985939258', 'https://www.facebook.com/sookafe', 4, 10.78156700, 106.69998900, true, 4.6, 2250);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000008-0000-0000-0000-000000000008', (SELECT id FROM user_account WHERE username='owner4'), 1, 'Okkio Caffe', '8 Nguyễn Siêu, Bến Nghé, Quận 1', '0848693711', 'https://okkiocaffe.com', 4, 10.77523400, 106.70254300, true, 4.7, 3100);
    -- Quận 3 (District_id = 5)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000009-0000-0000-0000-000000000009', (SELECT id FROM user_account WHERE username='owner1'), 10, 'Phúc Long Coffee & Tea', '43 Phạm Ngọc Thạch, Phường Võ Thị Sáu, Quận 3', '19006779', '[https://phuclong.com.vn](https://phuclong.com.vn)', 5, 10.78125600, 106.68456200, true, 4.2, 1890);

    -- Quận Bình Thạnh (District_id = 6)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000a-0000-0000-0000-00000000000a', (SELECT id FROM user_account WHERE username='owner3'), 13, 'Bosgaurus Coffee Roasters', 'Villa 1, Saigon Pearl, 92 Nguyễn Hữu Cảnh, Phường 22, Quận Bình Thạnh', '0901426877', 'https://www.facebook.com/bosgaurus', 6, 10.78856700, 106.71965400, true, 4.8, 987);

    -- Restaurant outlets (11-20)
        -- Quận 1 (District_id = 4)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000b-0000-0000-0000-00000000000b', (SELECT id FROM user_account WHERE username='owner2'), 11, 'Moo Beef Steak Prime', '35-37 Ngô Đức Kế, Bến Nghé, Quận 1', '02838274647', 'https://moobeefsteak.com', 4, 10.77456700, 106.70291100, true, 4.5, 2900);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000c-0000-0000-0000-00000000000c', (SELECT id FROM user_account WHERE username='owner4'), 11, 'El Gaucho Argentinian Steakhouse', '74/1 Hai Bà Trưng, Bến Nghé, Quận 1', '02838272090', 'https://elgaucho.com.vn', 4, 10.77610000, 106.70310000, true, 4.6, 3250);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000d-0000-0000-0000-00000000000d', (SELECT id FROM user_account WHERE username='owner1'), 11, 'Secret Garden Restaurant', '158 Pasteur, Bến Nghé, Quận 1', '0903328242', 'https://secretgarden.vn', 4, 10.77810000, 106.69750000, true, 4.4, 4500);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000e-0000-0000-0000-00000000000e', (SELECT id FROM user_account WHERE username='owner3'), 11, 'The Log Restaurant', 'Tầng 8, GEM Center, 8 Nguyễn Bỉnh Khiêm, Quận 1', '0975003189', 'https://thelog.com.vn', 4, 10.78712300, 106.70321000, true, 4.7, 1890);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000000f-0000-0000-0000-00000000000f', (SELECT id FROM user_account WHERE username='owner1'), 12, 'Pizza 4P''s Le Thanh Ton', '8/15 Lê Thánh Tôn, Bến Nghé, Quận 1', '02836220500', 'https://pizza4ps.com', 4, 10.77700000, 106.70300000, true, 4.5, 7800);

        -- Quận 3 (District_id = 5)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000010-0000-0000-0000-000000000010', (SELECT id FROM user_account WHERE username='owner2'), 11, 'Nhà hàng Gạo', '33 Lê Quý Đôn, Phường 7, Quận 3', '02839303355', NULL, 5, 10.77912300, 106.69123400, true, 4.3, 1950);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000011-0000-0000-0000-000000000011', (SELECT id FROM user_account WHERE username='owner3'), 14, 'Hum Vegetarian, Cafe & Restaurant', '32 Võ Văn Tần, Phường 6, Quận 3', '0901460300', '[https://humvietnam.vn](https://humvietnam.vn)', 5, 10.77712300, 106.69012300, true, 4.7, 1450);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000012-0000-0000-0000-000000000012', (SELECT id FROM user_account WHERE username='owner4'), 11, 'Phố 79 Phạm Ngọc Thạch', '5 Phạm Ngọc Thạch, Phường 6, Quận 3', '0966773279', '[https://pho79.vn](https://pho79.vn)', 5, 10.78110000, 106.68550000, true, 4.2, 1150);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000013-0000-0000-0000-000000000013', (SELECT id FROM user_account WHERE username='owner1'), 11, 'Nhà hàng Hội Ngộ', '22B Nguyễn Thị Diệu, Phường 6, Quận 3', '02839315931', NULL, 5, 10.77512300, 106.69321000, true, 4.3, 1650);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000014-0000-0000-0000-000000000014', (SELECT id FROM user_account WHERE username='owner2'), 11, 'Út Cà Mau', '215 Điện Biên Phủ, Phường 6, Quận 3', '02839300001', NULL, 5, 10.78180000, 106.68880000, true, 4.1, 990);

    -- Bar outlets (21-25)
        -- Quận 1 (District_id = 4)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000015-0000-0000-0000-000000000015', (SELECT id FROM user_account WHERE username='owner4'), 3, 'Broma Not A Bar', '41 Nguyễn Huệ, Quận 1', '0902158080', NULL, 4, 10.77412300, 106.70196400, true, 4.6, 2341);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000016-0000-0000-0000-000000000016', (SELECT id FROM user_account WHERE username='owner4'), 15, 'Chill Skybar', 'Tầng 26, AB Tower, 76 Lê Lai, Quận 1', '0283827838', NULL, 4, 10.76912300, 106.69865400, true, 4.7, 3456);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000017-0000-0000-0000-000000000017', (SELECT id FROM user_account WHERE username='owner4'), 3, 'Apocalypse Now', '2C Thi Sách, Quận 1', '0838252966', NULL, 4, 10.77812300, 106.70365400, true, 4.3, 1678);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000018-0000-0000-0000-000000000018', (SELECT id FROM user_account WHERE username='owner4'), 16, 'The Gin House', '169 Bùi Viện, Quận 1', '0938123789', NULL, 4, 10.76712300, 106.69265400, true, 4.5, 2123);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('00000019-0000-0000-0000-000000000019', (SELECT id FROM user_account WHERE username='owner1'), 5, 'The Shamrock Irish Pub', '33 Lê Duẩn, Quận 1', '0838234074', NULL, 4, 10.78012300, 106.69965400, true, 4.4, 1890);

    -- Street Food & Others (26-30)
        -- Quận 1 (District_id = 4)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000001a-0000-0000-0000-00000000001a', (SELECT id FROM user_account WHERE username='owner1'), 7, 'Bánh Xèo 46A', '46A Đinh Công Tráng, Quận 1', '0838242810', NULL, 4, 10.77112300, 106.69165400, true, 4.0, 1234);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000001b-0000-0000-0000-00000000001b', (SELECT id FROM user_account WHERE username='owner1'), 7, 'Bánh Mì Huỳnh Hoa', '26 Lê Thị Riêng, Quận 1', '0909678234', NULL, 4, 10.76312300, 106.68965400, true, 4.2, 3456);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000001c-0000-0000-0000-00000000001c', (SELECT id FROM user_account WHERE username='owner2'), 4, 'The Coffee House Signature', '26 Lý Tự Trọng, Quận 1', '18006936', '[https://thecoffeehouse.com](https://thecoffeehouse.com)', 4, 10.77615200, 106.70080300, true, 4.4, 2890);

        -- Quận 3 (District_id = 5)
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000001d-0000-0000-0000-00000000001d', (SELECT id FROM user_account WHERE username='owner3'), 2, 'Lẩu Bò Tơ Hòn Sơn', '25 Cao Thắng, Quận 3', '0838327729', NULL, 5, 10.78212300, 106.68665400, true, 4.3, 1567);
INSERT INTO outlet (id, owner_id, type_id, name, address, phone_number, website, district_id, latitude, longitude, is_active, average_rating, total_reviews) VALUES ('0000001e-0000-0000-0000-00000000001e', (SELECT id FROM user_account WHERE username='owner3'), 9, 'Cơm Niêu Singapore', '6-8 Công Trường Quốc Tế, Quận 3', '0838231281', NULL, 5, 10.78512300, 106.68865400, true, 4.1, 1123);

-- FNB (NOTE: NODONE)
    -- Phở (Hanoi, sub_category_id = 1)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000001', 1, (SELECT id FROM province WHERE id=1), 'Phở bò Hà Nội', 'Traditional Hanoi beef noodle soup', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000002', 1, (SELECT id FROM province WHERE id=1), 'Phở gà', 'Chicken noodle soup', false);

    -- Bún (Hanoi & Hue, sub_category_id = 2)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000003', 2, (SELECT id FROM province WHERE id=1), 'Bún chả Hà Nội', 'Grilled pork with vermicelli', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000004', 2, (SELECT id FROM province WHERE id=4), 'Bún bò Huế', 'Hue-style spicy beef noodle soup', true);

    -- Cơm (Saigon & Long An, sub_category_id = 3)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000005', 3, (SELECT id FROM province WHERE id=2), 'Cơm tấm Sài Gòn', 'Saigon broken rice with grilled pork', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000006', 3, (SELECT id FROM province WHERE id=3), 'Cơm tấm Long Xuyên', 'Long Xuyen-style broken rice', true);

    -- Bánh (sub_category_id = 4)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000007', 4, (SELECT id FROM province WHERE id=1), 'Bánh mì Hà Nội', 'Hanoi-style baguette sandwich', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000008', 4, (SELECT id FROM province WHERE id=2), 'Bánh xèo', 'Vietnamese sizzling pancake', false);

    -- Western - Pasta (sub_category_id = 5) & Burgers (sub_category_id = 6)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000009', 5, (SELECT id FROM province WHERE id=2), 'Carbonara', 'Creamy pasta with bacon', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000010', 6, (SELECT id FROM province WHERE id=2), 'Cheeseburger', 'Classic cheeseburger', false);

    -- Asian - Sushi (sub_category_id = 7) & Ramen (sub_category_id = 8)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000011', 7, (SELECT id FROM province WHERE id=2), 'Salmon Sushi Set', 'Fresh salmon sushi platter', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000012', 8, (SELECT id FROM province WHERE id=2), 'Tonkotsu Ramen', 'Rich pork bone broth ramen', false);

    -- Coffee (sub_category_id = 9, 10)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000013', 9, (SELECT id FROM province WHERE id=2), 'Cappuccino', 'Classic Italian cappuccino', false);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000014', 10, (SELECT id FROM province WHERE id=2), 'Cà phê sữa đá', 'Vietnamese iced coffee with milk', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000015', 10, (SELECT id FROM province WHERE id=1), 'Cà phê đen', 'Vietnamese black coffee', false);

    -- Tea (sub_category_id = 11, 12)
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000016', 11, (SELECT id FROM province WHERE id=2), 'Trà sữa trân châu', 'Bubble milk tea', true);
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('10000000-0000-0000-0000-000000000017', 12, (SELECT id FROM province WHERE id=1), 'Trà sen', 'Lotus tea', false);

-- OUTLET LIÊN QUAN (FIXED: Added ID)
-- 1. The Workshop Coffee
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (1, '00000001-0000-0000-0000-000000000001', 'https://img1.jpg', 'Ảnh tổng quan');

-- 2. Highlands Coffee Lý Tự Trọng
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (2, '00000002-0000-0000-0000-000000000002', 'https://img2.jpg', 'Ảnh mặt tiền');

-- 3. Starbucks Nguyễn Huệ
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (3, '00000003-0000-0000-0000-000000000003', 'https://img3.jpg', 'Ảnh không gian bên trong');

-- 4. Vintage Emporium Coffee
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (4, '00000004-0000-0000-0000-000000000004', 'https://img4.jpg', 'Ảnh đồ uống');

-- 5. LUsine Cafe
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (5, '00000005-0000-0000-0000-000000000005', 'https://img5.jpg', 'Ảnh chi tiết trang trí');

-- OPERATING HOURS (FIXED: Added ID)
-- 1. The Workshop Coffee
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (1, '00000001-0000-0000-0000-000000000001', 1, '08:00', '22:00');

-- 2. Highlands Coffee Lý Tự Trọng
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (2, '00000002-0000-0000-0000-000000000002', 2, '09:00', '21:00');

-- 3. Starbucks Nguyễn Huệ
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (3, '00000003-0000-0000-0000-000000000003', 3, '07:00', '23:00');

-- 4. Vintage Emporium Coffee
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (4, '00000004-0000-0000-0000-000000000004', 4, '10:00', '20:00');

-- 5. LUsine Cafe
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (5, '00000005-0000-0000-0000-000000000005', 5, '11:00', '19:00');

-- OUTLET HAS FEATURE

-- 1. The Workshop Coffee (00000001-...)
INSERT INTO outlet_feature_mapping (outlet_id, feature_id) VALUES ('00000001-0000-0000-0000-000000000001', 1);

-- 2. Highlands Coffee Lý Tự Trọng (00000002-...)
INSERT INTO outlet_feature_mapping (outlet_id, feature_id) VALUES ('00000002-0000-0000-0000-000000000002', 2);

-- 3. Starbucks Nguyễn Huệ (00000003-...)
INSERT INTO outlet_feature_mapping (outlet_id, feature_id) VALUES ('00000003-0000-0000-0000-000000000003', 3);

-- 4. Vintage Emporium Coffee (00000004-...)
INSERT INTO outlet_feature_mapping (outlet_id, feature_id) VALUES ('00000004-0000-0000-0000-000000000004', 4);

-- 5. LUsine Cafe (00000005-...)
INSERT INTO outlet_feature_mapping (outlet_id, feature_id) VALUES ('00000005-0000-0000-0000-000000000005', 5);

-- 1. The Workshop Coffee (00000001-...)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (1, '00000001-0000-0000-0000-000000000001', 'Quảng cáo 1', 'Nội dung QC 1', 'https://ad1.jpg');

-- 2. Highlands Coffee Lý Tự Trọng (00000002-...)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (2, '00000002-0000-0000-0000-000000000002', 'Quảng cáo 2', 'Nội dung QC 2', 'https://ad2.jpg');

-- 3. Starbucks Nguyễn Huệ (00000003-...)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (3, '00000003-0000-0000-0000-000000000003', 'Quảng cáo 3', 'Nội dung QC 3', 'https://ad3.jpg');

-- 4. Vintage Emporium Coffee (00000004-...)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (4, '00000004-0000-0000-0000-000000000004', 'Quảng cáo 4', 'Nội dung QC 4', 'https://ad4.jpg');

-- 5. LUsine Cafe (00000005-...)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (5, '00000005-0000-0000-0000-000000000005', 'Quảng cáo 5', 'Nội dung QC 5', 'https://ad5.jpg');

-- OUTLET HAS FNB
-- 1. The Workshop Coffee (Món 1)
INSERT INTO outlet_menu_item (outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (1, '00000001-0000-0000-0000-000000000001', '00000001-0000-0000-0000-000000000001', 35000);

-- 2. Highlands Coffee Lý Tự Trọng (Món 2)
INSERT INTO outlet_menu_item (outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (2, '00000002-0000-0000-0000-000000000002', '00000002-0000-0000-0000-000000000002', 40000);

-- 3. Starbucks Nguyễn Huệ (Món 3)
INSERT INTO outlet_menu_item (outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (3, '00000003-0000-0000-0000-000000000003', '00000003-0000-0000-0000-000000000003', 15000);

-- 4. Vintage Emporium Coffee (Món 4)
INSERT INTO outlet_menu_item (outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (4, '00000004-0000-0000-0000-000000000004', '00000004-0000-0000-0000-000000000004', 20000);

-- 5. LUsine Cafe (Món 5)
INSERT INTO outlet_menu_item (outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (5, '00000005-0000-0000-0000-000000000005', '00000005-0000-0000-0000-000000000005', 25000);

-- OUTLET FNB HAS FEATURE (Sử dụng ID của bảng outlet_menu_item đã được cố định)
INSERT INTO outlet_menu_item_feature (outlet_menu_item_id, feature_id) VALUES (1, 1);
INSERT INTO outlet_menu_item_feature (outlet_menu_item_id, feature_id) VALUES (2, 2);
INSERT INTO outlet_menu_item_feature (outlet_menu_item_id, feature_id) VALUES (3, 3);
INSERT INTO outlet_menu_item_feature (outlet_menu_item_id, feature_id) VALUES (4, 4);
INSERT INTO outlet_menu_item_feature (outlet_menu_item_id, feature_id) VALUES (5, 5);

-- BOOKING
-- 1. The Workshop Coffee (00000001-...)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('11111111-1111-1111-1111-111111111111', (SELECT id FROM user_account WHERE username='customer1'), '00000001-0000-0000-0000-000000000001', CURRENT_TIMESTAMP, 2, 'PENDING', 'Ghi chú 1');

-- 2. Highlands Coffee Lý Tự Trọng (00000002-...)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('22222222-2222-2222-2222-222222222222', (SELECT id FROM user_account WHERE username='customer1'), '00000002-0000-0000-0000-000000000002', CURRENT_TIMESTAMP, 4, 'CONFIRMED', 'Ghi chú 2');

-- 3. Starbucks Nguyễn Huệ (00000003-...)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('33333333-3333-3333-3333-333333333333', (SELECT id FROM user_account WHERE username='customer2'), '00000003-0000-0000-0000-000000000003', CURRENT_TIMESTAMP, 3, 'COMPLETED', 'Ghi chú 3');

-- 4. Vintage Emporium Coffee (00000004-...)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('44444444-4444-4444-4444-444444444444', (SELECT id FROM user_account WHERE username='customer3'), '00000004-0000-0000-0000-000000000004', CURRENT_TIMESTAMP, 5, 'CANCELLED', 'Ghi chú 4');

-- 5. LUsine Cafe (00000005-...)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('55555555-5555-5555-5555-555555555555', (SELECT id FROM user_account WHERE username='customer4'), '00000005-0000-0000-0000-000000000005', CURRENT_TIMESTAMP, 1, 'PENDING', 'Ghi chú 5');

-- PAYMENT
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 100000, 'PENDING', 'MOMO', 'TXN1');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 200000, 'COMPLETED', 'VNPAY', 'TXN2');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', 150000, 'COMPLETED', 'ZALOPAY', 'TXN3');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444444', 50000, 'FAILED', 'MOMO', 'TXN4');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '55555555-5555-5555-5555-555555555555', 120000, 'PENDING', 'VNPAY', 'TXN5');

-- REVIEW (Sử dụng UUID tường minh, giữ nguyên)
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('11111111-1111-1111-1111-111111111111', (SELECT id FROM user_account WHERE username='customer1'), '00000001-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 5, 'Rất ngon', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('22222222-2222-2222-2222-222222222222', (SELECT id FROM user_account WHERE username='customer1'), '00000001-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222', 4, 'Ổn', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('33333333-3333-3333-3333-333333333333', (SELECT id FROM user_account WHERE username='customer2'), '00000001-0000-0000-0000-000000000003', '33333333-3333-3333-3333-333333333333', 3, 'Bình thường', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('44444444-4444-4444-4444-444444444444', (SELECT id FROM user_account WHERE username='customer3'), '00000001-0000-0000-0000-000000000004', '44444444-4444-4444-4444-444444444444', 2, 'Không ngon', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('55555555-5555-5555-5555-555555555555', (SELECT id FROM user_account WHERE username='customer4'), '00000001-0000-0000-0000-000000000005

-- REVIEW LIÊN QUAN (FIXED: Added ID cho các bảng)
INSERT INTO review_image (id, review_id, image_url) VALUES (1, '11111111-1111-1111-1111-111111111111', 'https://review1.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (2, '22222222-2222-2222-2222-222222222222', 'https://review2.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (3, '33333333-3333-3333-3333-333333333333', 'https://review3.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (4, '44444444-4444-4444-4444-444444444444', 'https://review4.jpg');
INSERT INTO review_image (id, review_id, image_url) VALUES (5, '55555555-5555-5555-5555-555555555555', 'https://review5.jpg');

INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (1, '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'Cảm ơn bạn!');
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (2, '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'Xin lỗi vì trải nghiệm chưa tốt');
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (3, '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'Cảm ơn góp ý');
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (4, '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'Chúng tôi sẽ cải thiện');
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (5, '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'Hẹn gặp lại!');

INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (1, '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'LIKE');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (2, '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'LOVE');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (3, '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'HAHA');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (4, '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'SAD');
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (5, '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'WOW');

INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (1, '11111111-1111-1111-1111-111111111111', (SELECT id FROM user_account WHERE username='customer1'), 'Spam');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (2, '22222222-2222-2222-2222-222222222222', (SELECT id FROM user_account WHERE username='customer1'), 'Nội dung không phù hợp');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (3, '33333333-3333-3333-3333-333333333333', (SELECT id FROM user_account WHERE username='customer2'), 'Quảng cáo');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (4, '44444444-4444-4444-4444-444444444444', (SELECT id FROM user_account WHERE username='customer3'), 'Ngôn từ thô tục');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (5, '55555555-5555-5555-5555-555555555555', (SELECT id FROM user_account WHERE username='customer4'), 'Khác');

-- QUAN HỆ KHÁC (NOTE: DONE)
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user_account WHERE username='customer1'), (SELECT id FROM role WHERE name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user_account WHERE username='customer2'), (SELECT id FROM role WHERE name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user_account WHERE username='customer3'), (SELECT id FROM role WHERE name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user_account WHERE username='customer4'), (SELECT id FROM role WHERE name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user_account WHERE username='customer5'), (SELECT id FROM role WHERE name='USER'));

INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name='USER'), 1);
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name='USER'), 2);
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name='USER'), 3);
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name='USER'), 4);
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name='USER'), 5);

-- (NOTE: NODONE)
INSERT INTO sharing_list_collaborator (user_id, sharing_list_id) VALUES ((SELECT id FROM user_account WHERE username='customer1'), 1);
INSERT INTO sharing_list_collaborator (user_id, sharing_list_id) VALUES ((SELECT id FROM user_account WHERE username='customer2'), 1);
INSERT INTO sharing_list_collaborator (user_id, sharing_list_id) VALUES ((SELECT id FROM user_account WHERE username='customer3'), 1);
INSERT INTO sharing_list_collaborator (user_id, sharing_list_id) VALUES ((SELECT id FROM user_account WHERE username='customer4'), 2);
INSERT INTO sharing_list_collaborator (user_id, sharing_list_id) VALUES ((SELECT id FROM user_account WHERE username='customer5'), 2);

-- USER MEMBERSHIP (NOTE: DONE)
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (1, (SELECT id FROM user_account WHERE username='customer1'), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (2, (SELECT id FROM user_account WHERE username='customer2'), 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (3, (SELECT id FROM user_account WHERE username='customer3'), 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (4, (SELECT id FROM user_account WHERE username='customer4'), 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (5, (SELECT id FROM user_account WHERE username='customer5'), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');

-- NOTIFICATION (NOTE: NODONE)
INSERT INTO notification (id, user_id, type, title, content, related_id, is_read) VALUES
  (1, (SELECT id FROM user_account WHERE username='customer1'), 'BOOKING', 'Phản hồi', 'Lịch dùng bữa tối nay lúc 19:00.', NULL, 0),
  (2, (SELECT id FROM user_account WHERE username='customer1'), 'MEMBERSHIP', 'Nâng cấp', 'Đơn đặt bàn của bạn đã được xác nhận.', NULL, 0),
  (3, (SELECT id FROM user_account WHERE username='customer1'), 'SYSTEM', 'Phản hồi', 'Bạn nhận voucher -20% tuần này.', NULL, 0),
  (4, (SELECT id FROM user_account WHERE username='customer1'), 'PROMO', 'Nâng cấp', 'Lịch dùng bữa tối nay lúc 19:00.', NULL, 0),
  (5, (SELECT id FROM user_account WHERE username='customer1'), 'SYSTEM', 'Đặt bàn', 'Bạn nhận voucher -20% tuần này.', NULL, 1),
  (6, (SELECT id FROM user_account WHERE username='customer1'), 'MEMBERSHIP', 'Nhắc lịch', 'Bạn nhận voucher -20% tuần này.', NULL, 0),
  (7, (SELECT id FROM user_account WHERE username='customer1'), 'MEMBERSHIP', 'Phản hồi', 'Bình luận của bạn đã được chủ quán phản hồi.', NULL, 0),
  (8, (SELECT id FROM user_account WHERE username='customer1'), 'REVIEW', 'Nâng cấp', 'Tài khoản của bạn đã nâng cấp hội viên.', NULL, 0),
  (9, (SELECT id FROM user_account WHERE username='customer1'), 'SYSTEM', 'Nhắc lịch', 'Đơn đặt bàn của bạn đã được xác nhận.', NULL, 1),
  (10, (SELECT id FROM user_account WHERE username='customer1'), 'BOOKING', 'Đặt bàn', 'Bạn nhận voucher -20% tuần này.', NULL, 0);