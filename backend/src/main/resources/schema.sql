-- USER & AUTHENTICATION
CREATE TABLE user_account (
    id CHAR(36) PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    is_active BOOLEAN NOT NULL
);

CREATE TABLE profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    date_of_birth DATE,
    address VARCHAR(255),
    avatar_url VARCHAR(255),
    country_id INT
);

CREATE TABLE refresh_token (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    device_info VARCHAR(255),
    ip_address VARCHAR(50),
    is_revoked BOOLEAN NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE password_reset_token (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL
);

-- ROLE & PERMISSION
CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE permission (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    role_id INT NOT NULL
);

CREATE TABLE role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    permission_id INT NOT NULL
);

CREATE TABLE "GROUP" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE user_group (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    group_id BIGINT NOT NULL
);

-- OUTLET
CREATE TABLE outlet (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    owner_id CHAR(36) NOT NULL,
    type_id INT,
    district_id INT,
    description VARCHAR(1000),
    is_active BOOLEAN NOT NULL
);

CREATE TABLE outlet_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE outlet_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    type_id BIGINT,
    description VARCHAR(255)
);

CREATE TABLE feature_of_outlet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE outlet_has_feature (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_id CHAR(36) NOT NULL,
    feature_id BIGINT NOT NULL
);

CREATE TABLE outlet_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_id CHAR(36) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE operating_hours (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_id CHAR(36) NOT NULL,
    day_of_week INT NOT NULL,
    open_time VARCHAR(10),
    close_time VARCHAR(10)
);

-- FNB
CREATE TABLE fnb (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    description VARCHAR(1000),
    sub_category_id BIGINT,
    province_id INT
);

CREATE TABLE fnb_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE fnb_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    type_id BIGINT,
    description VARCHAR(255)
);

CREATE TABLE fnb_sub_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    category_id BIGINT,
    description VARCHAR(255)
);

CREATE TABLE outlet_has_fnb (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_id CHAR(36) NOT NULL,
    fnb_id BIGINT NOT NULL,
    price DECIMAL(12,2)
);

CREATE TABLE feature_of_fnb (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE outlet_fnb_has_feature (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_fnb_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL
);

-- BOOKING
CREATE TABLE booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    outlet_id CHAR(36) NOT NULL,
    booking_time TIMESTAMP NOT NULL,
    number_of_people INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    note VARCHAR(500)
);

-- PAYMENT
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    method VARCHAR(20) NOT NULL,
    transaction_id VARCHAR(100)
);

-- REVIEW
CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    outlet_id CHAR(36) NOT NULL,
    booking_id BIGINT,
    rating INT NOT NULL,
    content VARCHAR(1000),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE review_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    review_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE review_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    review_id BIGINT NOT NULL,
    owner_id CHAR(36) NOT NULL,
    content VARCHAR(1000)
);

CREATE TABLE review_reaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    review_id BIGINT NOT NULL,
    user_id CHAR(36) NOT NULL,
    reaction_type VARCHAR(50)
);

CREATE TABLE review_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    review_id BIGINT NOT NULL,
    reporter_id CHAR(36) NOT NULL,
    reason VARCHAR(255)
);

-- NOTIFICATION
CREATE TABLE notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    content VARCHAR(500) NOT NULL,
    is_read BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- MEMBERSHIP
CREATE TABLE membership_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    price DECIMAL(12,2) NOT NULL,
    feature_limit INT NOT NULL
);

CREATE TABLE user_membership (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    plan_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL
);

-- ADVERTISEMENT
CREATE TABLE advertisement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outlet_id CHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(1000),
    image_url VARCHAR(255)
);

-- LOCATION
CREATE TABLE country (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE province (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    country_id BIGINT NOT NULL
);

CREATE TABLE district (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    province_id BIGINT NOT NULL
);
