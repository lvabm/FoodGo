-- Predefine UUIDs for user_account and outlet for FK consistency
-- User UUIDs
-- user1: '11111111-1111-1111-1111-111111111111'
-- user2: '22222222-2222-2222-2222-222222222222'
-- user3: '33333333-3333-3333-3333-333333333333'
-- user4: '44444444-4444-4444-4444-444444444444'
-- user5: '55555555-5555-5555-5555-555555555555'

-- Outlet UUIDs
-- outlet1: 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
-- outlet2: 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'
-- outlet3: 'cccccccc-cccc-cccc-cccc-cccccccccccc'
-- outlet4: 'dddddddd-dddd-dddd-dddd-dddddddddddd'
-- outlet5: 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'

INSERT INTO outlet (id, name, address, owner_id, type_id, district_id, description, is_active) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Nhà hàng A', '123 Đường A', '11111111-1111-1111-1111-111111111111', 1, 1, 'Nhà hàng sang trọng', true),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Quán ăn B', '456 Đường B', '22222222-2222-2222-2222-222222222222', 2, 2, 'Quán ăn bình dân', true),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Cafe C', '789 Đường C', '33333333-3333-3333-3333-333333333333', 3, 3, 'Cafe view đẹp', true),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Trà sữa D', '101 Đường D', '44444444-4444-4444-4444-444444444444', 4, 4, 'Quán trà sữa', true),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Buffet E', '202 Đường E', '55555555-5555-5555-5555-555555555555', 5, 5, 'Buffet lẩu', true);

INSERT INTO outlet_image (outlet_id, image_url, description) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'https://img1.jpg', 'Ảnh 1'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'https://img2.jpg', 'Ảnh 2'),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'https://img3.jpg', 'Ảnh 3'),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'https://img4.jpg', 'Ảnh 4'),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'https://img5.jpg', 'Ảnh 5');

INSERT INTO operating_hours (outlet_id, day_of_week, open_time, close_time) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, '08:00', '22:00'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2, '09:00', '21:00'),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 3, '07:00', '23:00'),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 4, '10:00', '20:00'),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5, '11:00', '19:00');

INSERT INTO outlet_has_feature (outlet_id, feature_id) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 3),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 4),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5);

-- FNB
INSERT INTO fnb (name, price, description, sub_category_id, province_id) VALUES
  ('Cơm tấm', 35000, 'Cơm tấm Sài Gòn', 1, 2),
  ('Mì Quảng', 40000, 'Mì Quảng Đà Nẵng', 2, 1),
  ('Pepsi', 15000, 'Nước ngọt Pepsi', 3, 2),
  ('Bánh flan', 20000, 'Bánh flan tráng miệng', 4, 1),
  ('Khoai tây chiên', 25000, 'Snack khoai tây', 5, 2);

INSERT INTO outlet_has_fnb (outlet_id, fnb_id, price) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, 35000),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2, 40000),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 3, 15000),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 4, 20000),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5, 25000);

-- FEATURE_OF_FNB
INSERT INTO feature_of_fnb (name, description) VALUES
  ('Chay', 'Món chay'),
  ('Cay', 'Món cay'),
  ('Không gluten', 'Không chứa gluten'),
  ('Ít đường', 'Ít đường'),
  ('Không lactose', 'Không lactose');

-- OUTLET_FNB_HAS_FEATURE
INSERT INTO outlet_fnb_has_feature (outlet_fnb_id, feature_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5);

INSERT INTO booking (user_id, outlet_id, booking_time, number_of_people, status, note) VALUES
  ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', CURRENT_TIMESTAMP, 2, 'PENDING', 'Ghi chú 1'),
  ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', CURRENT_TIMESTAMP, 4, 'CONFIRMED', 'Ghi chú 2'),
  ('33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', CURRENT_TIMESTAMP, 3, 'COMPLETED', 'Ghi chú 3'),
  ('44444444-4444-4444-4444-444444444444', 'dddddddd-dddd-dddd-dddd-dddddddddddd', CURRENT_TIMESTAMP, 5, 'CANCELLED', 'Ghi chú 4'),
  ('55555555-5555-5555-5555-555555555555', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', CURRENT_TIMESTAMP, 1, 'PENDING', 'Ghi chú 5');

-- PAYMENT
INSERT INTO payment (booking_id, amount, status, method, transaction_id) VALUES
  (1, 100000, 'PENDING', 'MOMO', 'TXN1'),
  (2, 200000, 'COMPLETED', 'VNPAY', 'TXN2'),
  (3, 150000, 'COMPLETED', 'ZALOPAY', 'TXN3'),
  (4, 50000, 'FAILED', 'MOMO', 'TXN4'),
  (5, 120000, 'PENDING', 'VNPAY', 'TXN5');

INSERT INTO review (user_id, outlet_id, booking_id, rating, content, created_at) VALUES
  ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, 5, 'Rất ngon', CURRENT_TIMESTAMP),
  ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 2, 4, 'Ổn', CURRENT_TIMESTAMP),
  ('33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 3, 3, 'Bình thường', CURRENT_TIMESTAMP),
  ('44444444-4444-4444-4444-444444444444', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 4, 2, 'Không ngon', CURRENT_TIMESTAMP),
  ('55555555-5555-5555-5555-555555555555', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 5, 5, 'Tuyệt vời', CURRENT_TIMESTAMP);

-- REVIEW_IMAGE
INSERT INTO review_image (review_id, image_url) VALUES
  (1, 'https://review1.jpg'),
  (2, 'https://review2.jpg'),
  (3, 'https://review3.jpg'),
  (4, 'https://review4.jpg'),
  (5, 'https://review5.jpg');

INSERT INTO review_reply (review_id, owner_id, content) VALUES
  (1, '11111111-1111-1111-1111-111111111111', 'Cảm ơn bạn!'),
  (2, '22222222-2222-2222-2222-222222222222', 'Xin lỗi vì trải nghiệm chưa tốt'),
  (3, '33333333-3333-3333-3333-333333333333', 'Cảm ơn góp ý'),
  (4, '44444444-4444-4444-4444-444444444444', 'Chúng tôi sẽ cải thiện'),
  (5, '55555555-5555-5555-5555-555555555555', 'Hẹn gặp lại!');

INSERT INTO review_reaction (review_id, user_id, reaction_type) VALUES
  (1, '11111111-1111-1111-1111-111111111111', 'LIKE'),
  (2, '22222222-2222-2222-2222-222222222222', 'LOVE'),
  (3, '33333333-3333-3333-3333-333333333333', 'HAHA'),
  (4, '44444444-4444-4444-4444-444444444444', 'SAD'),
  (5, '55555555-5555-5555-5555-555555555555', 'WOW');

INSERT INTO review_report (review_id, reporter_id, reason) VALUES
  (1, '11111111-1111-1111-1111-111111111111', 'Spam'),
  (2, '22222222-2222-2222-2222-222222222222', 'Nội dung không phù hợp'),
  (3, '33333333-3333-3333-3333-333333333333', 'Quảng cáo'),
  (4, '44444444-4444-4444-4444-444444444444', 'Ngôn từ thô tục'),
  (5, '55555555-5555-5555-5555-555555555555', 'Khác');

INSERT INTO notification (user_id, content, is_read, created_at) VALUES
  ('11111111-1111-1111-1111-111111111111', 'Bạn có thông báo mới', false, CURRENT_TIMESTAMP),
  ('22222222-2222-2222-2222-222222222222', 'Đặt bàn thành công', true, CURRENT_TIMESTAMP),
  ('33333333-3333-3333-3333-333333333333', 'Thanh toán thành công', false, CURRENT_TIMESTAMP),
  ('44444444-4444-4444-4444-444444444444', 'Có đánh giá mới', true, CURRENT_TIMESTAMP),
  ('55555555-5555-5555-5555-555555555555', 'Khuyến mãi mới', false, CURRENT_TIMESTAMP);

-- MEMBERSHIP_PLAN
INSERT INTO membership_plan (name, description, price, feature_limit) VALUES
  ('Basic', 'Gói cơ bản', 0, 1),
  ('Silver', 'Gói bạc', 100000, 3),
  ('Gold', 'Gói vàng', 200000, 5),
  ('Platinum', 'Gói bạch kim', 500000, 10),
  ('Diamond', 'Gói kim cương', 1000000, 20);

INSERT INTO user_membership (user_id, plan_id, start_date, end_date) VALUES
  ('11111111-1111-1111-1111-111111111111', 1, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP)),
  ('22222222-2222-2222-2222-222222222222', 2, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP)),
  ('33333333-3333-3333-3333-333333333333', 3, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP)),
  ('44444444-4444-4444-4444-444444444444', 4, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP)),
  ('55555555-5555-5555-5555-555555555555', 5, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP));

INSERT INTO advertisement (outlet_id, title, content, image_url) VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Quảng cáo 1', 'Nội dung QC 1', 'https://ad1.jpg'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Quảng cáo 2', 'Nội dung QC 2', 'https://ad2.jpg'),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Quảng cáo 3', 'Nội dung QC 3', 'https://ad3.jpg'),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Quảng cáo 4', 'Nội dung QC 4', 'https://ad4.jpg'),
  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Quảng cáo 5', 'Nội dung QC 5', 'https://ad5.jpg');

INSERT INTO user_role (user_id, role_id) VALUES
  ('11111111-1111-1111-1111-111111111111', 1),
  ('22222222-2222-2222-2222-222222222222', 2),
  ('33333333-3333-3333-3333-333333333333', 3),
  ('44444444-4444-4444-4444-444444444444', 4),
  ('55555555-5555-5555-5555-555555555555', 1);

-- ROLE_PERMISSION
INSERT INTO role_permission (role_id, permission_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (1, 5);

-- GROUP
INSERT INTO "GROUP" (name, description) VALUES
  ('Group 1', 'Nhóm 1'),
  ('Group 2', 'Nhóm 2'),
  ('Group 3', 'Nhóm 3'),
  ('Group 4', 'Nhóm 4'),
  ('Group 5', 'Nhóm 5');

INSERT INTO user_group (user_id, group_id) VALUES
  ('11111111-1111-1111-1111-111111111111', 1),
  ('22222222-2222-2222-2222-222222222222', 2),
  ('33333333-3333-3333-3333-333333333333', 3),
  ('44444444-4444-4444-4444-444444444444', 4),
  ('55555555-5555-5555-5555-555555555555', 5);
INSERT INTO user_account (id, username, password_hash, email, phone_number, is_active) VALUES
  ('11111111-1111-1111-1111-111111111111', 'user1', 'hash1', 'user1@email.com', '0900000001', true),
  ('22222222-2222-2222-2222-222222222222', 'user2', 'hash2', 'user2@email.com', '0900000002', true),
  ('33333333-3333-3333-3333-333333333333', 'user3', 'hash3', 'user3@email.com', '0900000003', true),
  ('44444444-4444-4444-4444-444444444444', 'user4', 'hash4', 'user4@email.com', '0900000004', true),
  ('55555555-5555-5555-5555-555555555555', 'user5', 'hash5', 'user5@email.com', '0900000005', true),
  ('66666666-6666-6666-6666-666666666666', 'user6', 'hash6', 'user6@email.com', '0900000006', true),
  ('77777777-7777-7777-7777-777777777777', 'user7', 'hash7', 'user7@email.com', '0900000007', true),
  ('88888888-8888-8888-8888-888888888888', 'user8', 'hash8', 'user8@email.com', '0900000008', true),
  ('99999999-9999-9999-9999-999999999999', 'user9', 'hash9', 'user9@email.com', '0900000009', true),
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user10', 'hash10', 'user10@email.com', '0900000010', true);

INSERT INTO profile (user_id, full_name, date_of_birth, address, avatar_url, country_id) VALUES
  ((SELECT id FROM user_account WHERE username='user1'), 'User One', '1990-01-01', 'Address 1', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user2'), 'User Two', '1991-01-01', 'Address 2', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user3'), 'User Three', '1992-01-01', 'Address 3', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user4'), 'User Four', '1993-01-01', 'Address 4', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user5'), 'User Five', '1994-01-01', 'Address 5', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user6'), 'User Six', '1995-01-01', 'Address 6', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user7'), 'User Seven', '1996-01-01', 'Address 7', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user8'), 'User Eight', '1997-01-01', 'Address 8', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user9'), 'User Nine', '1998-01-01', 'Address 9', 'https://default-avatar.png', 1),
  ((SELECT id FROM user_account WHERE username='user10'), 'User Ten', '1999-01-01', 'Address 10', 'https://default-avatar.png', 1);


-- ROLE
INSERT INTO role (name, description) VALUES
  ('USER', 'Người dùng'),
  ('OWNER', 'Chủ quán'),
  ('ADMIN', 'Quản trị viên'),
  ('SYSTEM_ADMIN', 'Quản trị hệ thống'),
  ('GUEST', 'Khách vãng lai');

-- PERMISSION
INSERT INTO permission (name, description) VALUES
  ('READ', 'Quyền đọc'),
  ('WRITE', 'Quyền ghi'),
  ('DELETE', 'Quyền xóa'),
  ('UPDATE', 'Quyền cập nhật'),
  ('MANAGE', 'Quyền quản lý');

-- OUTLET_TYPE
INSERT INTO outlet_type (name, description) VALUES
  ('Nhà hàng', 'Nhà hàng sang trọng'),
  ('Quán ăn', 'Quán ăn bình dân'),
  ('Cafe', 'Quán cafe'),
  ('Trà sữa', 'Quán trà sữa'),
  ('Buffet', 'Nhà hàng buffet');

-- OUTLET_CATEGORY
INSERT INTO outlet_category (name, type_id, description) VALUES
  ('Ẩm thực Việt', 1, 'Ẩm thực truyền thống'),
  ('Ẩm thực Nhật', 1, 'Ẩm thực Nhật Bản'),
  ('Ăn vặt', 2, 'Đồ ăn vặt'),
  ('Cafe view đẹp', 3, 'Cafe có view đẹp'),
  ('Buffet lẩu', 5, 'Buffet lẩu ngon');

-- FEATURE_OF_OUTLET
INSERT INTO feature_of_outlet (name, description) VALUES
  ('Wifi miễn phí', 'Có wifi'),
  ('Chỗ đậu xe', 'Có bãi đậu xe'),
  ('Phòng riêng', 'Có phòng riêng'),
  ('Điều hòa', 'Có điều hòa'),
  ('Thanh toán thẻ', 'Chấp nhận thẻ');

-- FNB_TYPE
INSERT INTO fnb_type (name, description) VALUES
  ('Món chính', 'Món ăn chính'),
  ('Món phụ', 'Món ăn phụ'),
  ('Đồ uống', 'Nước uống'),
  ('Tráng miệng', 'Món tráng miệng'),
  ('Ăn vặt', 'Đồ ăn vặt');

-- FNB_CATEGORY
INSERT INTO fnb_category (name, type_id, description) VALUES
  ('Cơm', 1, 'Các món cơm'),
  ('Mì', 1, 'Các món mì'),
  ('Nước ngọt', 3, 'Đồ uống ngọt'),
  ('Bánh ngọt', 4, 'Bánh tráng miệng'),
  ('Snack', 5, 'Đồ ăn vặt');

-- FNB_SUB_CATEGORY
INSERT INTO fnb_sub_category (name, category_id, description) VALUES
  ('Cơm tấm', 1, 'Cơm tấm Sài Gòn'),
  ('Mì Quảng', 2, 'Đặc sản miền Trung'),
  ('Pepsi', 3, 'Nước ngọt Pepsi'),
  ('Bánh flan', 4, 'Bánh flan tráng miệng'),
  ('Khoai tây chiên', 5, 'Snack khoai tây');

-- COUNTRY
INSERT INTO country (name, code) VALUES
  ('Việt Nam', 'VN'),
  ('Nhật Bản', 'JP'),
  ('Hàn Quốc', 'KR'),
  ('Mỹ', 'US'),
  ('Pháp', 'FR');

-- PROVINCE
INSERT INTO province (name, country_id) VALUES
  ('Hà Nội', 1),
  ('TP.HCM', 1),
  ('Osaka', 2),
  ('Seoul', 3),
  ('Paris', 5);

-- DISTRICT
INSERT INTO district (name, province_id) VALUES
  ('Quận 1', 2),
  ('Quận 3', 2),
  ('Ba Đình', 1),
  ('Shinsaibashi', 3),
  ('Gangnam', 4);
