DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS outlets;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    phone_number VARCHAR(20),
    role_id INT NOT NULL,
    CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE outlets (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    phone_number VARCHAR(20),
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_outlets_users FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE menu_items (
    id UUID PRIMARY KEY,
    outlet_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_menu_items_outlets FOREIGN KEY (outlet_id) REFERENCES outlets(id)
);

CREATE TABLE bookings (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    outlet_id UUID NOT NULL,
    booking_time TIMESTAMP NOT NULL,
    number_of_people INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'pending',
    CONSTRAINT fk_bookings_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_bookings_outlets FOREIGN KEY (outlet_id) REFERENCES outlets(id)
);
