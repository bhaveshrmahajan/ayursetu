-- Create databases for all services
CREATE DATABASE IF NOT EXISTS userdb;
CREATE DATABASE IF NOT EXISTS doctordb;
CREATE DATABASE IF NOT EXISTS consultationdb;
CREATE DATABASE IF NOT EXISTS pharmacydb;
CREATE DATABASE IF NOT EXISTS paymentdb;
CREATE DATABASE IF NOT EXISTS notificationdb;

-- Use userdb
USE userdb;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50),
    pincode VARCHAR(10),
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    blood_group VARCHAR(5),
    emergency_contact VARCHAR(15),
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Create user_roles table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create password_reset_tokens table
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert default roles
INSERT IGNORE INTO roles (name) VALUES ('USER'), ('DOCTOR'), ('ADMIN');

-- Use doctordb
USE doctordb;

-- Create doctors table
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification VARCHAR(100) NOT NULL,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    experience VARCHAR(50) NOT NULL,
    bio TEXT,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    consultation_fee DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create doctor_specializations table
CREATE TABLE IF NOT EXISTS doctor_specializations (
    doctor_id BIGINT,
    specialization VARCHAR(100),
    PRIMARY KEY (doctor_id, specialization),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Create doctor_languages table
CREATE TABLE IF NOT EXISTS doctor_languages (
    doctor_id BIGINT,
    language VARCHAR(50),
    PRIMARY KEY (doctor_id, language),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Use consultationdb
USE consultationdb;

-- Create consultations table
CREATE TABLE IF NOT EXISTS consultations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date_time TIMESTAMP NOT NULL,
    status ENUM('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED', 'NO_SHOW') NOT NULL,
    type ENUM('VIDEO_CALL', 'AUDIO_CALL', 'CHAT', 'IN_PERSON') NOT NULL,
    symptoms TEXT,
    diagnosis TEXT,
    prescription TEXT,
    notes TEXT,
    consultation_fee DECIMAL(10,2) NOT NULL,
    meeting_link VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Use pharmacydb
USE pharmacydb;

-- Create medicines table
CREATE TABLE IF NOT EXISTS medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    generic_name VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    dosage_form VARCHAR(50) NOT NULL,
    strength VARCHAR(50) NOT NULL,
    description TEXT,
    side_effects TEXT,
    contraindications TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    requires_prescription BOOLEAN NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    shipping_address TEXT NOT NULL,
    payment_method VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);

-- Use paymentdb
USE paymentdb;

-- Create payments table
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'INR',
    payment_method VARCHAR(50) NOT NULL,
    status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') NOT NULL,
    transaction_id VARCHAR(100) UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Use notificationdb
USE notificationdb;

-- Create notifications table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    type ENUM('CONSULTATION_BOOKED', 'CONSULTATION_REMINDER', 'CONSULTATION_COMPLETED', 'ORDER_PLACED', 'ORDER_CONFIRMED', 'ORDER_SHIPPED', 'ORDER_DELIVERED', 'PAYMENT_SUCCESS', 'PAYMENT_FAILED', 'PRESCRIPTION_UPLOADED', 'MEDICINE_AVAILABLE', 'APPOINTMENT_CANCELLED', 'GENERAL') NOT NULL,
    status ENUM('PENDING', 'SENT', 'DELIVERED', 'READ', 'FAILED') NOT NULL,
    channel ENUM('EMAIL', 'SMS', 'PUSH', 'IN_APP') NOT NULL,
    metadata TEXT,
    read_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data for testing
USE doctordb;
INSERT IGNORE INTO doctors (name, email, phone, specialization, qualification, license_number, experience, bio, address, city, state, country, pincode, consultation_fee) VALUES 
('Dr. John Smith', 'doctor.test@example.com', '+1234567890', 'CARDIOLOGY', 'MBBS, MD', 'MD123456', '10 years', 'Experienced cardiologist with 10 years of practice', '123 Main St', 'New York', 'NY', 'USA', '10001', 150.00);

USE pharmacydb;
INSERT IGNORE INTO medicines (name, generic_name, manufacturer, category, dosage_form, strength, description, side_effects, contraindications, price, stock_quantity, requires_prescription, image_url) VALUES 
('Paracetamol', 'Acetaminophen', 'Generic Pharma', 'Pain Relief', 'Tablet', '500mg', 'Used to treat pain and fever', 'Nausea, stomach upset', 'Liver disease, alcohol use', 5.00, 1000, FALSE, 'https://example.com/paracetamol.jpg'),
('Amoxicillin', 'Amoxicillin', 'Antibiotic Corp', 'Antibiotic', 'Capsule', '250mg', 'Broad-spectrum antibiotic', 'Diarrhea, rash', 'Penicillin allergy', 15.00, 500, TRUE, 'https://example.com/amoxicillin.jpg');
