-- Ngoài trừ refresh_token và password_reset_token (36/38)

-- COUNTRY (FIXED: Added ID)
INSERT INTO country (id, name, code) VALUES (1, 'Việt Nam', 'VN');
INSERT INTO country (id, name, code) VALUES (2, 'Nhật Bản', 'JP');
INSERT INTO country (id, name, code) VALUES (3, 'Hàn Quốc', 'KR');
INSERT INTO country (id, name, code) VALUES (4, 'Mỹ', 'US');
INSERT INTO country (id, name, code) VALUES (5, 'Pháp', 'FR');

-- PROVINCE (FIXED: Added ID)
INSERT INTO province (id, name, country_id) VALUES (1, 'Hà Nội', 1);
INSERT INTO province (id, name, country_id) VALUES (2, 'TP.HCM', 1);
INSERT INTO province (id, name, country_id) VALUES (3, 'Osaka', 2);
INSERT INTO province (id, name, country_id) VALUES (4, 'Seoul', 3);
INSERT INTO province (id, name, country_id) VALUES (5, 'Paris', 5);

-- DISTRICT (FIXED: Added ID)
INSERT INTO district (id, name, province_id) VALUES (1, 'Quận 1', 2);
INSERT INTO district (id, name, province_id) VALUES (2, 'Quận 3', 2);
INSERT INTO district (id, name, province_id) VALUES (3, 'Ba Đình', 1);
INSERT INTO district (id, name, province_id) VALUES (4, 'Shinsaibashi', 3);
INSERT INTO district (id, name, province_id) VALUES (5, 'Gangnam', 4);

-- ROLE & PERMISSION (FIXED: Added ID)
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

-- OUTLET TYPE & CATEGORY (FIXED: Added ID)
INSERT INTO outlet_type (id, name, description) VALUES (1, 'Nhà hàng', 'Nhà hàng sang trọng');
INSERT INTO outlet_type (id, name, description) VALUES (2, 'Quán ăn', 'Quán ăn bình dân');
INSERT INTO outlet_type (id, name, description) VALUES (3, 'Cafe', 'Quán cafe');
INSERT INTO outlet_type (id, name, description) VALUES (4, 'Trà sữa', 'Quán trà sữa');
INSERT INTO outlet_type (id, name, description) VALUES (5, 'Buffet', 'Nhà hàng buffet');

INSERT INTO outlet_category (id, name, type_id, description) VALUES (1, 'Ẩm thực Việt', 1, 'Ẩm thực truyền thống');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (2, 'Ẩm thực Nhật', 1, 'Ẩm thực Nhật Bản');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (3, 'Ăn vặt', 2, 'Đồ ăn vặt');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (4, 'Cafe view đẹp', 3, 'Cafe có view đẹp');
INSERT INTO outlet_category (id, name, type_id, description) VALUES (5, 'Buffet lẩu', 5, 'Buffet lẩu ngon');

-- FNB TYPES, CATEGORIES, SUB-CATEGORIES (FIXED: Added ID)
INSERT INTO fnb_type (id, name, description) VALUES (1, 'Món chính', 'Món ăn chính');
INSERT INTO fnb_type (id, name, description) VALUES (2, 'Món phụ', 'Món ăn phụ');
INSERT INTO fnb_type (id, name, description) VALUES (3, 'Đồ uống', 'Nước uống');
INSERT INTO fnb_type (id, name, description) VALUES (4, 'Tráng miệng', 'Món tráng miệng');
INSERT INTO fnb_type (id, name, description) VALUES (5, 'Ăn vặt', 'Đồ ăn vặt');

-- ********** ĐÃ SỬA: category_id trỏ về chính ID để thỏa mãn NOT NULL **********
INSERT INTO fnb_category (id, name, type_id, description) VALUES (1, 'Cơm', 1, 'Các món cơm');
INSERT INTO fnb_category (id, name, type_id, description) VALUES (2, 'Mì', 1, 'Các món mì');
INSERT INTO fnb_category (id, name, type_id, description) VALUES (3, 'Nước ngọt', 3, 'Đồ uống ngọt');
INSERT INTO fnb_category (id, name, type_id, description) VALUES (4, 'Bánh ngọt', 4, 'Bánh tráng miệng');
INSERT INTO fnb_category (id, name, type_id, description) VALUES (5, 'Snack', 5, 'Đồ ăn vặt');
-- *****************************************************************************************

INSERT INTO fnb_sub_category (id, name, category_id, description) VALUES (1, 'Cơm tấm', 1, 'Cơm tấm Sài Gòn');
INSERT INTO fnb_sub_category (id, name, category_id, description) VALUES (2, 'Mì Quảng', 2, 'Đặc sản miền Trung');
INSERT INTO fnb_sub_category (id, name, category_id, description) VALUES (3, 'Pepsi', 3, 'Nước ngọt Pepsi');
INSERT INTO fnb_sub_category (id, name, category_id, description) VALUES (4, 'Bánh flan', 4, 'Bánh flan tráng miệng');
INSERT INTO fnb_sub_category (id, name, category_id, description) VALUES (5, 'Khoai tây chiên', 5, 'Snack khoai tây');

-- FEATURES (FIXED: Added ID)
INSERT INTO feature_of_fnb (id, name, description) VALUES (1, 'Chay', 'Món chay');
INSERT INTO feature_of_fnb (id, name, description) VALUES (2, 'Cay', 'Món cay');
INSERT INTO feature_of_fnb (id, name, description) VALUES (3, 'Không gluten', 'Không chứa gluten');
INSERT INTO feature_of_fnb (id, name, description) VALUES (4, 'Ít đường', 'Ít đường');
INSERT INTO feature_of_fnb (id, name, description) VALUES (5, 'Không lactose', 'Không lactose');

INSERT INTO feature_of_outlet (id, name, description) VALUES (1, 'Wifi miễn phí', 'Có wifi');
INSERT INTO feature_of_outlet (id, name, description) VALUES (2, 'Chỗ đậu xe', 'Có bãi đậu xe');
INSERT INTO feature_of_outlet (id, name, description) VALUES (3, 'Phòng riêng', 'Có phòng riêng');
INSERT INTO feature_of_outlet (id, name, description) VALUES (4, 'Điều hòa', 'Có điều hòa');
INSERT INTO feature_of_outlet (id, name, description) VALUES (5, 'Thanh toán thẻ', 'Chấp nhận thẻ');

-- MEMBERSHIP PLAN & GROUP (FIXED: Added ID)
INSERT INTO membership_plan (id, name, description, price, feature_limit) VALUES (1, 'Basic', 'Gói cơ bản', 0, 1);
INSERT INTO membership_plan (id, name, description, price, feature_limit) VALUES (2, 'Silver', 'Gói bạc', 100000, 3);
INSERT INTO membership_plan (id, name, description, price, feature_limit) VALUES (3, 'Gold', 'Gói vàng', 200000, 5);
INSERT INTO membership_plan (id, name, description, price, feature_limit) VALUES (4, 'Platinum', 'Gói bạch kim', 500000, 10);
INSERT INTO membership_plan (id, name, description, price, feature_limit) VALUES (5, 'Diamond', 'Gói kim cương', 1000000, 20);

INSERT INTO sharing_list (id, name, description) VALUES (1, 'Group 1', 'Nhóm 1');
INSERT INTO sharing_list (id, name, description) VALUES (2, 'Group 2', 'Nhóm 2');
INSERT INTO sharing_list (id, name, description) VALUES (3, 'Group 3', 'Nhóm 3');
INSERT INTO sharing_list (id, name, description) VALUES (4, 'Group 4', 'Nhóm 4');
INSERT INTO sharing_list (id, name, description) VALUES (5, 'Group 5', 'Nhóm 5');

-- User UUIDs (Sử dụng UUID tường minh, giữ nguyên)
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('11111111-1111-1111-1111-111111111111', 'user1', 'hash1', 'user1@email.com', '0900000001', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('22222222-2222-2222-2222-222222222222', 'user2', 'hash2', 'user2@email.com', '0900000002', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('33333333-3333-3333-3333-333333333333', 'user3', 'hash3', 'user3@email.com', '0900000003', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('44444444-4444-4444-4444-444444444444', 'user4', 'hash4', 'user4@email.com', '0900000004', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('55555555-5555-5555-5555-555555555555', 'user5', 'hash5', 'user5@email.com', '0900000005', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('66666666-6666-6666-6666-666666666666', 'user6', 'hash6', 'user6@email.com', '0900000006', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('77777777-7777-7777-7777-777777777777', 'user7', 'hash7', 'user7@email.com', '0900000007', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('88888888-8888-8888-8888-888888888888', 'user8', 'hash8', 'user8@email.com', '0900000008', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('99999999-9999-9999-9999-999999999999', 'user9', 'hash9', 'user9@email.com', '0900000009', true);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user10', 'hash10', 'user10@email.com', '0900000010', true);

-- PROFILE (Giữ nguyên Subquery)
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user1'), 'User One', '1990-01-01', 'Address 1', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user2'), 'User Two', '1991-01-01', 'Address 2', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user3'), 'User Three', '1992-01-01', 'Address 3', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user4'), 'User Four', '1993-01-01', 'Address 4', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user5'), 'User Five', '1994-01-01', 'Address 5', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user6'), 'User Six', '1995-01-01', 'Address 6', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user7'), 'User Seven', '1996-01-01', 'Address 7', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user8'), 'User Eight', '1997-01-01', 'Address 8', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user9'), 'User Nine', '1998-01-01', 'Address 9', 'https://default-avatar.png', 1);
INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES ((SELECT id FROM user_account WHERE username='user10'), 'User Ten', '1999-01-01', 'Address 10', 'https://default-avatar.png', 1);

-- OUTLET (Sử dụng UUID tường minh, giữ nguyên)
INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Nhà hàng A', '123 Đường A', '11111111-1111-1111-1111-111111111111', 1, 1, 'Nhà hàng sang trọng', true);
INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Quán ăn B', '456 Đường B', '22222222-2222-2222-2222-222222222222', 2, 2, 'Quán ăn bình dân', true);
INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Cafe C', '789 Đường C', '33333333-3333-3333-3333-333333333333', 3, 3, 'Cafe view đẹp', true);
INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Trà sữa D', '101 Đường D', '44444444-4444-4444-4444-444444444444', 4, 4, 'Quán trà sữa', true);
INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Buffet E', '202 Đường E', '55555555-5555-5555-5555-555555555555', 5, 5, 'Buffet lẩu', true);

-- FNB (FIXED: Added ID)
INSERT INTO fnb (id, name, price, description, sub_category_id, province_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Cơm tấm', 35000, 'Cơm tấm Sài Gòn', 1, 2);
INSERT INTO fnb (id, name, price, description, sub_category_id, province_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Mì Quảng', 40000, 'Mì Quảng Đà Nẵng', 2, 1);
INSERT INTO fnb (id, name, price, description, sub_category_id, province_id) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Pepsi', 15000, 'Nước ngọt Pepsi', 3, 2);
INSERT INTO fnb (id, name, price, description, sub_category_id, province_id) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Bánh flan', 20000, 'Bánh flan tráng miệng', 4, 1);
INSERT INTO fnb (id, name, price, description, sub_category_id, province_id) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Khoai tây chiên', 25000, 'Snack khoai tây', 5, 2);

-- OUTLET LIÊN QUAN (FIXED: Added ID)
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'https://img1.jpg', 'Ảnh 1');
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (2, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'https://img2.jpg', 'Ảnh 2');
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (3, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'https://img3.jpg', 'Ảnh 3');
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (4, 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'https://img4.jpg', 'Ảnh 4');
INSERT INTO outlet_image (id, outlet_id, image_url, description) VALUES (5, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'https://img5.jpg', 'Ảnh 5');

-- OPERATING HOURS (FIXED: Added ID)
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, '08:00', '22:00');
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (2, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2, '09:00', '21:00');
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (3, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 3, '07:00', '23:00');
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (4, 'dddddddd-dddd-dddd-dddd-dddddddddddd', 4, '10:00', '20:00');
INSERT INTO operating_hours (id, outlet_id, day_of_week, open_time, close_time) VALUES (5, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5, '11:00', '19:00');

INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1);
INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2);
INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 3);
INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 4);
INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5);

-- ADVERTISEMENT (FIXED: Added ID)
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Quảng cáo 1', 'Nội dung QC 1', 'https://ad1.jpg');
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (2, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Quảng cáo 2', 'Nội dung QC 2', 'https://ad2.jpg');
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (3, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Quảng cáo 3', 'Nội dung QC 3', 'https://ad3.jpg');
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (4, 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Quảng cáo 4', 'Nội dung QC 4', 'https://ad4.jpg');
INSERT INTO advertisement (id, outlet_id, title, content, image_url) VALUES (5, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Quảng cáo 5', 'Nội dung QC 5', 'https://ad5.jpg');

-- OUTLET HAS FNB (FIXED: Added ID)
INSERT INTO outlet_has_fnb (id, outlet_id, fnb_id, price) VALUES (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 35000);
INSERT INTO outlet_has_fnb (id, outlet_id, fnb_id, price) VALUES (2, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 40000);
INSERT INTO outlet_has_fnb (id, outlet_id, fnb_id, price) VALUES (3, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 15000);
INSERT INTO outlet_has_fnb (id, outlet_id, fnb_id, price) VALUES (4, 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 20000);
INSERT INTO outlet_has_fnb (id, outlet_id, fnb_id, price) VALUES (5, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 25000);

-- OUTLET FNB HAS FEATURE (Sử dụng ID của bảng outlet_has_fnb đã được cố định)
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES (1, 1);
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES (2, 2);
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES (3, 3);
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES (4, 4);
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES (5, 5);

-- BOOKING (Sử dụng UUID tường minh, giữ nguyên)
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', CURRENT_TIMESTAMP, 2, 'PENDING', 'Ghi chú 1');
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', CURRENT_TIMESTAMP, 4, 'CONFIRMED', 'Ghi chú 2');
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', CURRENT_TIMESTAMP, 3, 'COMPLETED', 'Ghi chú 3');
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'dddddddd-dddd-dddd-dddd-dddddddddddd', CURRENT_TIMESTAMP, 5, 'CANCELLED', 'Ghi chú 4');
INSERT INTO booking (id, user_id, outlet_id, booking_time, number_of_people, status, note) VALUES ('55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', CURRENT_TIMESTAMP, 1, 'PENDING', 'Ghi chú 5');

-- PAYMENT (FIXED: Added ID)
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 100000, 'PENDING', 'MOMO', 'TXN1');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 200000, 'COMPLETED', 'VNPAY', 'TXN2');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', 150000, 'COMPLETED', 'ZALOPAY', 'TXN3');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444444', 50000, 'FAILED', 'MOMO', 'TXN4');
INSERT INTO payment (id, booking_id, amount, status, method, transaction_id) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '55555555-5555-5555-5555-555555555555', 120000, 'PENDING', 'VNPAY', 'TXN5');

-- REVIEW (Sử dụng UUID tường minh, giữ nguyên)
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 5, 'Rất ngon', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 4, 'Ổn', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', 3, 'Bình thường', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444444', 2, 'Không ngon', 10, 20);
INSERT INTO review (id, user_id, outlet_id, booking_id, rating, comment, likes_count, dislikes_count) VALUES ('55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '55555555-5555-5555-5555-555555555555', 5, 'Tuyệt vời', 10, 20);

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

INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (1, '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'Spam');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (2, '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'Nội dung không phù hợp');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (3, '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'Quảng cáo');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (4, '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'Ngôn từ thô tục');
INSERT INTO review_report (id, review_id, reporter_id, reason) VALUES (5, '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'Khác');

-- QUAN HỆ KHÁC (Bảng N-N, giữ nguyên)
INSERT INTO user_role (user_id, role_id) VALUES ('11111111-1111-1111-1111-111111111111', 1);
INSERT INTO user_role (user_id, role_id) VALUES ('22222222-2222-2222-2222-222222222222', 2);
INSERT INTO user_role (user_id, role_id) VALUES ('33333333-3333-3333-3333-333333333333', 3);
INSERT INTO user_role (user_id, role_id) VALUES ('44444444-4444-4444-4444-444444444444', 4);
INSERT INTO user_role (user_id, role_id) VALUES ('55555555-5555-5555-5555-555555555555', 1);

INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 3);
INSERT INTO role_permission (role_id, permission_id) VALUES (4, 4);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 5);

INSERT INTO sharing_list_member (user_id, sharing_list_id) VALUES ('11111111-1111-1111-1111-111111111111', 1);
INSERT INTO sharing_list_member (user_id, sharing_list_id) VALUES ('22222222-2222-2222-2222-222222222222', 2);
INSERT INTO sharing_list_member (user_id, sharing_list_id) VALUES ('33333333-3333-3333-3333-333333333333', 3);
INSERT INTO sharing_list_member (user_id, sharing_list_id) VALUES ('44444444-4444-4444-4444-444444444444', 4);
INSERT INTO sharing_list_member (user_id, sharing_list_id) VALUES ('55555555-5555-5555-5555-555555555555', 5);

-- USER MEMBERSHIP (FIXED: Added ID)
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (1, '11111111-1111-1111-1111-111111111111', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (2, '22222222-2222-2222-2222-222222222222', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (3, '33333333-3333-3333-3333-333333333333', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (4, '44444444-4444-4444-4444-444444444444', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');
INSERT INTO user_membership (id, user_id, plan_id, start_date, end_date) VALUES (5, '55555555-5555-5555-5555-555555555555', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 days');

-- NOTIFICATION (FIXED: Added ID)
INSERT INTO notification (id, user_id, content, is_read, created_at) VALUES (1, '11111111-1111-1111-1111-111111111111', 'Bạn có thông báo mới', false, CURRENT_TIMESTAMP);
INSERT INTO notification (id, user_id, content, is_read, created_at) VALUES (2, '22222222-2222-2222-2222-222222222222', 'Đặt bàn thành công', true, CURRENT_TIMESTAMP);
INSERT INTO notification (id, user_id, content, is_read, created_at) VALUES (3, '33333333-3333-3333-3333-333333333333', 'Thanh toán thành công', false, CURRENT_TIMESTAMP);
INSERT INTO notification (id, user_id, content, is_read, created_at) VALUES (4, '44444444-4444-4444-4444-444444444444', 'Có đánh giá mới', true, CURRENT_TIMESTAMP);
INSERT INTO notification (id, user_id, content, is_read, created_at) VALUES (5, '55555555-5555-5555-5555-555555555555', 'Khuyến mãi mới', false, CURRENT_TIMESTAMP);