
-- ----------------------------
-- 1. unverified user table before user registration unverified_users
-- ----------------------------
CREATE TABLE unverified_users (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    username VARCHAR(100) UNIQUE,
    phone VARCHAR(10) UNIQUE,
    password VARCHAR(255),
    pin VARCHAR(10),
    user_type ENUM('CUSTOMER', 'OWNER', 'ADMIN') NOT NULL,
    user_id CHAR(36) UNIQUE,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ----------------------------
-- 2. OTP Token table before Vendor or Customer registration
-- ----------------------------
CREATE TABLE otp_tokens (
    id CHAR(36) PRIMARY KEY,
    otp VARCHAR(10) NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    delivered BOOLEAN NOT NULL DEFAULT FALSE,
    purpose VARCHAR(50),
    token_status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unverified_user_id CHAR(36) NOT NULL,
    CONSTRAINT fk_otp_tokens_unverified_users
        FOREIGN KEY (unverified_user_id)
        REFERENCES unverified_users(id)
        ON DELETE CASCADE
);

CREATE TABLE user_login_update_details (
    id CHAR(36) PRIMARY KEY,
    unverified_user_id CHAR(36) NOT NULL,  -- Foreign key to unverified_users
    old_email VARCHAR(255),
    old_phone VARCHAR(20),
    old_username VARCHAR(100),
    new_email VARCHAR(255),
    new_phone VARCHAR(20),
    new_username VARCHAR(100),

    status ENUM('PENDING', 'VERIFIED', 'EXPIRED', 'CANCELLED') DEFAULT 'PENDING',

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_unverified_user FOREIGN KEY (unverified_user_id) REFERENCES unverified_users(id)
);

-- ----------------------------
-- 3. USERS
-- ----------------------------
CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE,
    password VARCHAR(255),
    pin VARCHAR(10),
    status VARCHAR(10),
    user_type ENUM('CUSTOMER', 'OWNER', 'ADMIN') NOT NULL,
    user_category VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ----------------------------
-- 4. USERS
-- ----------------------------
CREATE TABLE personal_information (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    full_name VARCHAR(100),
    gender VARCHAR(15),
    marital_status VARCHAR(100),
    profile_pic_url VARCHAR(500),
    date_of_birth DATE,
    marriage_anniversary DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_personal_info_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- ----------------------------
-- 5. USER ADDRESSES
-- ----------------------------
CREATE TABLE user_addresses (
    id CHAR(36)  PRIMARY KEY,
    user_id CHAR(36)  NOT NULL,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_address_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 11. FAMILY INFORMATION
-- ----------------------------
CREATE TABLE family_information (
    id CHAR(36) PRIMARY KEY,
    personal_information_id CHAR(36) NOT NULL,
    name VARCHAR(100),
    relationship VARCHAR(100),
    marital_status VARCHAR(100),
    date_of_birth DATE,
    marriage_anniversary DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_family_information_personal_info
        FOREIGN KEY (personal_information_id)
        REFERENCES personal_information(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 6. RESTAURANTS
-- ----------------------------
CREATE TABLE restaurants (
    id CHAR(36) PRIMARY KEY,
    owner_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    gst_number VARCHAR(50),
    contact_email VARCHAR(150),
    contact_phone VARCHAR(15),
    license_doc_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_restaurants_user
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 7. RESTAURANT ADDRESSES
-- ----------------------------
CREATE TABLE restaurant_addresses (
    id CHAR(36) PRIMARY KEY,
    restaurant_id CHAR(36)  NOT NULL,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    CONSTRAINT fk_restaurant_addresses_user
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 8. PARTY REQUESTS (created by customers)
-- ----------------------------
CREATE TABLE party_requests (
    id CHAR(36)  PRIMARY KEY,
    customer_id CHAR(36) NOT NULL,
    title VARCHAR(255),
    description TEXT,
    event_date DATE,
    expected_guests INT,
    preferred_location VARCHAR(255),
    budget_range VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_party_requests_user
        FOREIGN KEY (customer_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 9. BIDS (placed by restaurant owners)
-- ----------------------------
CREATE TABLE bids (
    id CHAR(36) PRIMARY KEY,
    restaurant_id CHAR(36) NOT NULL,
    party_request_id CHAR(36) NOT NULL,
    proposed_price DECIMAL(10,2),
    message TEXT,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bids_restaurants
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_bids_party_requests
        FOREIGN KEY (party_request_id)
        REFERENCES party_requests(id)
        ON DELETE CASCADE
);

-- ----------------------------
-- 12. ADMIN LOGS (optional, for moderation/audit)
-- ----------------------------
CREATE TABLE admin_logs (
    id CHAR(36)  PRIMARY KEY,
    admin_id CHAR(36) NOT NULL,
    action VARCHAR(255),
    target_type VARCHAR(100), -- e.g., 'user', 'restaurant'
    target_id BIGINT,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_admin_logs_users
        FOREIGN KEY (admin_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);



