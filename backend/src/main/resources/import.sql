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
--6. MENU_ITEM_TYPE
--========================================================
INSERT INTO menu_item_type (id, name, description) VALUES (1, 'Món chính', 'Món ăn chính');
INSERT INTO menu_item_type (id, name, description) VALUES (2, 'Món phụ', 'Món ăn phụ');
INSERT INTO menu_item_type (id, name, description) VALUES (3, 'Đồ uống', 'Nước uống');
INSERT INTO menu_item_type (id, name, description) VALUES (4, 'Tráng miệng', 'Món tráng miệng');
INSERT INTO menu_item_type (id, name, description) VALUES (5, 'Ăn vặt', 'Đồ ăn vặt');

--========================================================
--7. MENU_ITEM_CATEGORY
--========================================================
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (1, (SELECT id FROM menu_item_type WHERE name = 'Món chính'), 'Phở', 'Các món phở');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (2, (SELECT id FROM menu_item_type WHERE name = 'Món chính'), 'Mì', 'Các món mì');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (3, (SELECT id FROM menu_item_type WHERE name = 'Đồ uống'), 'Nước ngọt', 'Đồ uống ngọt');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (4, (SELECT id FROM menu_item_type WHERE name = 'Tráng miệng'), 'Bánh ngọt', 'Bánh tráng miệng');
INSERT INTO menu_item_category (id, type_id, name, description) VALUES (5, (SELECT id FROM menu_item_type WHERE name = 'Ăn vặt'), 'Snack', 'Đồ ăn vặt');

--========================================================
--8. MENU_ITEM_SUB_CATEGORY
--========================================================
INSERT INTO menu_item_sub_category (id, category_id, name, description) VALUES (1, (SELECT id FROM menu_item_category WHERE name = 'Phở'), 'Phở bò', 'Phở bò - Vietnamese noodle soup');

--========================================================
--9. MENU_ITEM
--========================================================
INSERT INTO menu_item (id, sub_category_id, province_id, name, description, is_popular) VALUES ('60000000-0000-0000-0000-000000000001', (SELECT id FROM menu_item_sub_category WHERE name = 'Phở bò'), (SELECT id FROM province WHERE name = 'Hồ Chí Minh'), 'Phở Bò', 'Phở bò Hà Nội', false);

--========================================================
--10. USER_ACCOUNT
--========================================================
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('10000000-0000-0000-0000-000000000001', 'lvabm', 'plaintextpassword', 'lvabm@example.com', '0911295205', true);

--========================================================
--11. PROFILE
--========================================================
INSERT INTO profile (id, user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), 'Alice Nguyễn', '1995-05-10', 'Quận 1, TP. HCM', 'https://default-avatar.png', (SELECT id FROM country WHERE code = 'VN'));

--========================================================
--12. REFRESH_TOKEN
--========================================================
INSERT INTO refresh_token (id, user_id, token, device_info, ip_address, is_revoked, expires_at, created_at) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), 'refresh-token-sample-1', 'Chrome on Windows', '127.0.0.1', false, NOW() + INTERVAL '14 days', NOW());

--========================================================
--13. PASSWORD_RESET_TOKEN
--========================================================
INSERT INTO password_reset_token (id, user_id, token, expires_at, is_used) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), 'pw-reset-token-1', NOW() + INTERVAL '30 minutes', false);

--========================================================
--14. ROLE
--========================================================
INSERT INTO role (id, name, description) VALUES (1, 'USER', 'Người dùng');
INSERT INTO role (id, name, description) VALUES (2, 'OWNER', 'Chủ quán');
INSERT INTO role (id, name, description) VALUES (3, 'ADMIN', 'Quản trị viên');
INSERT INTO role (id, name, description) VALUES (4, 'SYSTEM_ADMIN', 'Quản trị hệ thống');
INSERT INTO role (id, name, description) VALUES (5, 'GUEST', 'Khách vãng lai');

--========================================================
--15. PERMISSION
--========================================================
INSERT INTO permission (id, name, description) VALUES (1, 'OUTLET_CREATE', 'Create outlet');
INSERT INTO permission (id, name, description) VALUES (2, 'OUTLET_UPDATE', 'Update outlet');

--========================================================
--16. USER_ROLE
--========================================================
INSERT INTO user_role (id, user_id, role_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), (SELECT id FROM role WHERE name = 'USER'));

--========================================================
--17. ROLE_PERMISSION
--========================================================
INSERT INTO role_permission (id, role_id, permission_id) VALUES (1, (SELECT id FROM role WHERE name = 'OWNER'), (SELECT id FROM permission WHERE name = 'OUTLET_CREATE'));

--========================================================
--18. MEMBERSHIP_PLAN
--========================================================
INSERT INTO membership_plan (id, name, description, price, duration_months, dish_limit, features) VALUES (1, 'Free', 'Free plan', 0.00, 0, 10, '["basic-listing"]');

--========================================================
--19. USER_MEMBERSHIP
--========================================================
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date, is_active) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), (SELECT id FROM membership_plan WHERE name = 'Free'), CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', true);

--========================================================
--20. SHARING_LIST
--========================================================
INSERT INTO sharing_list (id, owner_id, name, description, is_public, is_collaborative) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), 'My Favorites', 'Danh sách địa điểm yêu thích', false, false);

--========================================================
--21. SHARING_LIST_COLLABORATOR
--========================================================
INSERT INTO sharing_list_collaborator (id, user_id, sharing_list_id) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), (SELECT id FROM sharing_list WHERE name = 'My Favorites'));

--========================================================
--22. OUTLET
--========================================================
INSERT INTO outlet (id, owner_id, type_id, name, description, address, email, phone_number, website, district_id, latitude, longitude, price_range, capacity, is_active, average_rating, total_reviews) VALUES ('20000000-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username = 'lvabm'), (SELECT id FROM outlet_type WHERE name = 'Restaurant'), 'Quán Phở Alice', 'Quán phở ngon, phục vụ nhanh', '123 Lê Lợi, Quận 1, TP.HCM', 'pho.lvabm@example.com', '0909000002', 'https://example.com', (SELECT id FROM district WHERE name = 'Quận 1'), 10.775839, 106.700806, 'moderate', 80, true, 4.85, 120);

--========================================================
--23. OUTLET_FEATURE (Wifi)
--========================================================
INSERT INTO outlet_feature (id, name, description) VALUES (1, 'Wifi', 'Miễn phí Wifi');
INSERT INTO outlet_feature (id, name, description) VALUES (2, 'Parking', 'Có chỗ đỗ xe');

--========================================================
--24. OUTLET_FEATURE_MAPPING
--========================================================
INSERT INTO outlet_feature_mapping (id, outlet_id, feature_id) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM outlet_feature WHERE name = 'Wifi'));

--========================================================
--25. OUTLET_IMAGE
--========================================================
INSERT INTO outlet_image (id, outlet_id, image_url, display_order, is_primary) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), 'https://cdn.example.com/outlet/pho-lvabm-1.jpg', 1, true);

--========================================================
--26. OPERATING_HOURS
--========================================================
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time, is_closed) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), 1, '07:00', '21:00', false);

--========================================================
--27. OUTLET_MENU_ITEM
--========================================================
INSERT INTO outlet_menu_item (id, outlet_id, menu_item_id, name, description, price, image_url, is_available) VALUES (1, (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'),  (SELECT id FROM menu_item WHERE name = 'Phở Bò'), 'Phở Bò Tái', 'Phở bò tái ngon', 75000.00, 'https://cdn.example.com/menu/pho-bo-1.jpg', true);

--========================================================
--28. MENU_ITEM_FEATURE
--========================================================
INSERT INTO menu_item_feature (id, name, feature_type, value_type, possible_values, description) VALUES (1, 'Spicy Level', 'attribute', 'number', NULL, 'Mức độ cay');

--========================================================
--29. OUTLET_MENU_ITEM_FEATURE
--========================================================
INSERT INTO outlet_menu_item_feature (id, outlet_menu_item_id, feature_id, value) VALUES (1, (SELECT id FROM outlet_menu_item WHERE id = 1), (SELECT id FROM menu_item_feature WHERE name = 'Spicy Level'), '1');

--========================================================
--30. BOOKING
--========================================================
INSERT INTO booking (id, outlet_id, user_id, booking_date, booking_time, number_of_guests, status, deposit_amount, user_notes, owner_notes) VALUES ('30000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM user_account WHERE username = 'lvabm'), CURRENT_DATE + INTERVAL '3 days', '19:00', 4, 'PENDING', 100000.00, 'Ngồi tầng 1, gần cửa sổ', NULL);

--========================================================
--31. PAYMENT
--========================================================
INSERT INTO payment (id, booking_id, amount, payment_method, payment_status, transaction_id) VALUES ('40000000-0000-0000-0000-000000000001', (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000001'), 100000.00, 'MOMO', 'PENDING', 'TXN123456789');

--========================================================
--32. REVIEW
--========================================================
INSERT INTO review (id, outlet_id, user_id, booking_id, food_rating, service_rating, ambiance_rating, price_rating, overall_rating, comment, likes_count, dislikes_count) VALUES ('50000000-0000-0000-0000-000000000001', (SELECT id FROM outlet WHERE name = 'Quán Phở Alice'), (SELECT id FROM user_account WHERE username = 'lvabm'), (SELECT id FROM booking WHERE id = '30000000-0000-0000-0000-000000000001'), 5, 5, 4, 4, 5, 'Nước dùng đậm đà, thịt mềm', 3, 0);

--========================================================
--33. REVIEW_IMAGE
--========================================================
INSERT INTO review_image (id, review_id, image_url) VALUES (1, (SELECT id FROM review WHERE id = '50000000-0000-0000-0000-000000000001'), 'https://cdn.example.com/review/5001.jpg');

--========================================================
--34. REVIEW_REPLY
--========================================================
INSERT INTO review_reply (id, review_id, owner_id, reply_text) VALUES (1, (SELECT id FROM review WHERE id = '50000000-0000-0000-0000-000000000001'), (SELECT owner_id FROM outlet WHERE name = 'Quán Phở Alice'), 'Cảm ơn bạn đã ủng hộ! ❤️');

--========================================================
--35. REVIEW_REACTION
--========================================================
INSERT INTO review_reaction (id, review_id, user_id, reaction_type) VALUES (1, '50000000-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username = 'lvabm'), 'like');

--========================================================
--36. REVIEW_REPORT
--========================================================
INSERT INTO review_report (id, review_id, reporter_id, reason, status) VALUES (1, '50000000-0000-0000-0000-000000000001', (SELECT id FROM user_account WHERE username = 'lvabm'), 'Nội dung không phù hợp', 'pending');

--========================================================
--37. NOTIFICATION
--========================================================
INSERT INTO notification (id, user_id, type, title, content, related_id, is_read) VALUES (1, (SELECT id FROM user_account WHERE username = 'lvabm'), 'review', 'Bạn có phản hồi mới!', 'Chủ quán đã trả lời review của bạn.', '50000000-0000-0000-0000-000000000001', false);

--========================================================
--38. ADVERTISEMENT
--========================================================
INSERT INTO advertisement (id, outlet_id, position, start_date, end_date, is_active) VALUES (1, '20000000-0000-0000-0000-000000000001', 'featured', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', true);
