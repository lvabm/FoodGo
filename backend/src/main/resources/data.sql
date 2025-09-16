-- Roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('CUSTOMER');
INSERT INTO roles (name) VALUES ('OWNER');

-- Users
INSERT INTO users (id, email, password, full_name, phone_number, role_id)
VALUES (RANDOM_UUID(), 'admin@foodgo.com', 'admin123', 'System Admin', '0123456789', 1);

INSERT INTO users (id, email, password, full_name, phone_number, role_id)
VALUES (RANDOM_UUID(), 'user1@foodgo.com', 'user123', 'Nguyen Van A', '0987654321', 2);

-- Outlets
INSERT INTO outlets (id, owner_id, name, address, phone_number, description, is_active)
VALUES (RANDOM_UUID(), (SELECT id FROM users WHERE email = 'user1@foodgo.com'), 'Pho 24', '123 Nguyen Trai, Q1, HCMC', '0909009009', 'Best Pho in town', TRUE);

-- Menu Items
INSERT INTO menu_items (id, outlet_id, name, description, price, image_url, is_available)
VALUES (RANDOM_UUID(), (SELECT id FROM outlets LIMIT 1), 'Pho Bo', 'Traditional beef noodle soup', 45000, 'https://example.com/pho.jpg', TRUE);

-- Bookings
INSERT INTO bookings (id, user_id, outlet_id, booking_time, number_of_people, status)
VALUES (RANDOM_UUID(), (SELECT id FROM users WHERE email = 'user1@foodgo.com'), (SELECT id FROM outlets LIMIT 1), CURRENT_TIMESTAMP, 2, 'pending');
