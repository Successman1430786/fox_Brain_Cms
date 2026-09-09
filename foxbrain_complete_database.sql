-- FoxBrain Institute Management & Learning System
-- Complete MySQL schema
-- No real FoxBrain business data is invented; Admin can add it later.

DROP DATABASE IF EXISTS foxbrain_db;
CREATE DATABASE foxbrain_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE foxbrain_db;

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO roles (name, description) VALUES
('ADMIN','Administrator'),
('TEACHER','Teacher or faculty member'),
('STUDENT','Student'),
('PARENT','Parent or guardian');

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    phone VARCHAR(30),
    status ENUM('ACTIVE','INACTIVE','LOCKED','PENDING') NOT NULL DEFAULT 'ACTIVE',
    last_login_at DATETIME NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_users_role_id ON users(role_id);
CREATE INDEX idx_users_status ON users(status);

CREATE TABLE institute_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    institute_name VARCHAR(200),
    tagline VARCHAR(255),
    about_text TEXT,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    phone VARCHAR(30),
    alternate_phone VARCHAR(30),
    email VARCHAR(150),
    website VARCHAR(255),
    logo_url VARCHAR(500),
    favicon_url VARCHAR(500),
    facebook_url VARCHAR(500),
    instagram_url VARCHAR(500),
    youtube_url VARCHAR(500),
    linkedin_url VARCHAR(500),
    office_hours VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    admission_number VARCHAR(50) NOT NULL UNIQUE,
    date_of_birth DATE,
    gender VARCHAR(30),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    admission_date DATE,
    status ENUM('ACTIVE','INACTIVE','SUSPENDED','GRADUATED','LEFT') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_students_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_students_status ON students(status);

CREATE TABLE parents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    occupation VARCHAR(150),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_parents_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE parent_students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    relationship_type VARCHAR(50) NOT NULL,
    is_primary_contact BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_parent_student UNIQUE (parent_id, student_id),
    CONSTRAINT fk_parent_students_parent FOREIGN KEY (parent_id) REFERENCES parents(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_parent_students_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_parent_students_parent ON parent_students(parent_id);
CREATE INDEX idx_parent_students_student ON parent_students(student_id);

CREATE TABLE teachers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    employee_number VARCHAR(50) NOT NULL UNIQUE,
    qualification VARCHAR(255),
    specialization VARCHAR(255),
    joining_date DATE,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    status ENUM('ACTIVE','INACTIVE','ON_LEAVE','LEFT') NOT NULL DEFAULT 'ACTIVE',
    bio TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_teachers_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE course_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    slug VARCHAR(180) NOT NULL UNIQUE,
    description TEXT,
    display_order INT NOT NULL DEFAULT 0,
    status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NULL,
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL UNIQUE,
    short_description VARCHAR(500),
    description TEXT,
    duration_value INT,
    duration_unit ENUM('DAYS','WEEKS','MONTHS','YEARS'),
    fee DECIMAL(12,2),
    currency_code CHAR(3) NOT NULL DEFAULT 'INR',
    max_students INT,
    syllabus TEXT,
    image_url VARCHAR(500),
    status ENUM('DRAFT','ACTIVE','INACTIVE','ARCHIVED') NOT NULL DEFAULT 'DRAFT',
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    display_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_courses_category FOREIGN KEY (category_id) REFERENCES course_categories(id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_courses_fee CHECK (fee IS NULL OR fee >= 0),
    CONSTRAINT chk_courses_capacity CHECK (max_students IS NULL OR max_students > 0)
) ENGINE=InnoDB;

CREATE TABLE batches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    batch_code VARCHAR(80) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    start_date DATE,
    end_date DATE,
    start_time TIME,
    end_time TIME,
    room_name VARCHAR(100),
    capacity INT,
    status ENUM('PLANNED','ACTIVE','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PLANNED',
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_batches_course FOREIGN KEY (course_id) REFERENCES courses(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_batches_dates CHECK (end_date IS NULL OR start_date IS NULL OR end_date >= start_date),
    CONSTRAINT chk_batches_capacity CHECK (capacity IS NULL OR capacity > 0)
) ENGINE=InnoDB;

CREATE TABLE batch_teachers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    is_primary_teacher BOOLEAN NOT NULL DEFAULT FALSE,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_batch_teacher UNIQUE (batch_id, teacher_id),
    CONSTRAINT fk_batch_teachers_batch FOREIGN KEY (batch_id) REFERENCES batches(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_batch_teachers_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    batch_id BIGINT NOT NULL,
    enrollment_number VARCHAR(80) NOT NULL UNIQUE,
    enrollment_date DATE NOT NULL,
    status ENUM('ACTIVE','COMPLETED','CANCELLED','TRANSFERRED') NOT NULL DEFAULT 'ACTIVE',
    completion_date DATE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_student_batch UNIQUE (student_id, batch_id),
    CONSTRAINT fk_enrollments_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_enrollments_batch FOREIGN KEY (batch_id) REFERENCES batches(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT','ABSENT','LATE','EXCUSED') NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    remarks VARCHAR(500),
    marked_by_teacher_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_attendance_enrollment_date UNIQUE (enrollment_id, attendance_date),
    CONSTRAINT fk_attendance_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_attendance_teacher FOREIGN KEY (marked_by_teacher_id) REFERENCES teachers(id)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    assigned_date DATE NOT NULL,
    due_date DATE,
    max_marks DECIMAL(8,2),
    attachment_url VARCHAR(500),
    status ENUM('DRAFT','PUBLISHED','CLOSED','ARCHIVED') NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_assignments_batch FOREIGN KEY (batch_id) REFERENCES batches(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_assignments_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_assignment_marks CHECK (max_marks IS NULL OR max_marks >= 0),
    CONSTRAINT chk_assignment_dates CHECK (due_date IS NULL OR due_date >= assigned_date)
) ENGINE=InnoDB;

CREATE TABLE submissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    submitted_at DATETIME,
    submission_text TEXT,
    attachment_url VARCHAR(500),
    status ENUM('DRAFT','SUBMITTED','LATE','REVIEWED','RETURNED') NOT NULL DEFAULT 'DRAFT',
    marks DECIMAL(8,2),
    teacher_feedback TEXT,
    reviewed_by_teacher_id BIGINT,
    reviewed_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_assignment_student UNIQUE (assignment_id, student_id),
    CONSTRAINT fk_submissions_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_submissions_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_submissions_reviewer FOREIGN KEY (reviewed_by_teacher_id) REFERENCES teachers(id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_submission_marks CHECK (marks IS NULL OR marks >= 0)
) ENGINE=InnoDB;

CREATE TABLE exams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    exam_type ENUM('QUIZ','MIDTERM','FINAL','PRACTICAL','PROJECT','OTHER') NOT NULL,
    exam_date DATE,
    start_time TIME,
    end_time TIME,
    total_marks DECIMAL(8,2) NOT NULL,
    passing_marks DECIMAL(8,2),
    room_name VARCHAR(100),
    instructions TEXT,
    status ENUM('DRAFT','SCHEDULED','COMPLETED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_exams_batch FOREIGN KEY (batch_id) REFERENCES batches(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_exam_total_marks CHECK (total_marks > 0),
    CONSTRAINT chk_exam_passing_marks CHECK (passing_marks IS NULL OR (passing_marks >= 0 AND passing_marks <= total_marks))
) ENGINE=InnoDB;

CREATE TABLE exam_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    marks_obtained DECIMAL(8,2) NOT NULL,
    grade VARCHAR(20),
    result_status ENUM('PASS','FAIL','ABSENT','WITHHELD') NOT NULL,
    remarks VARCHAR(500),
    published_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_exam_student UNIQUE (exam_id, student_id),
    CONSTRAINT fk_exam_results_exam FOREIGN KEY (exam_id) REFERENCES exams(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_exam_results_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_exam_result_marks CHECK (marks_obtained >= 0)
) ENGINE=InnoDB;

CREATE TABLE fee_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    fee_type VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    due_date DATE,
    status ENUM('PENDING','PARTIAL','PAID','OVERDUE','CANCELLED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_fee_accounts_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_fee_accounts_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_fee_total CHECK (total_amount >= 0),
    CONSTRAINT chk_fee_discount CHECK (discount_amount >= 0 AND discount_amount <= total_amount)
) ENGINE=InnoDB;

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fee_account_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    receipt_number VARCHAR(100) NOT NULL UNIQUE,
    amount DECIMAL(12,2) NOT NULL,
    payment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_method ENUM('CASH','BANK_TRANSFER','CARD','UPI','ONLINE','OTHER') NOT NULL,
    transaction_reference VARCHAR(255),
    status ENUM('PENDING','SUCCESS','FAILED','REFUNDED') NOT NULL DEFAULT 'SUCCESS',
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payments_fee_account FOREIGN KEY (fee_account_id) REFERENCES fee_accounts(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_payments_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_payment_amount CHECK (amount > 0)
) ENGINE=InnoDB;

CREATE TABLE certificates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    certificate_number VARCHAR(100) NOT NULL UNIQUE,
    issue_date DATE NOT NULL,
    certificate_type VARCHAR(100) NOT NULL DEFAULT 'COURSE_COMPLETION',
    grade VARCHAR(50),
    verification_code VARCHAR(150) NOT NULL UNIQUE,
    certificate_url VARCHAR(500),
    status ENUM('DRAFT','ISSUED','REVOKED') NOT NULL DEFAULT 'DRAFT',
    remarks VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_certificates_student FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_certificates_course FOREIGN KEY (course_id) REFERENCES courses(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_certificates_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    target_role ENUM('ALL','ADMIN','TEACHER','STUDENT','PARENT') NOT NULL DEFAULT 'ALL',
    publish_at DATETIME,
    expire_at DATETIME,
    status ENUM('DRAFT','PUBLISHED','ARCHIVED') NOT NULL DEFAULT 'DRAFT',
    created_by_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_announcements_creator FOREIGN KEY (created_by_user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_announcement_dates CHECK (expire_at IS NULL OR publish_at IS NULL OR expire_at >= publish_at)
) ENGINE=InnoDB;

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    announcement_id BIGINT,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(50) NOT NULL DEFAULT 'GENERAL',
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    read_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_notifications_announcement FOREIGN KEY (announcement_id) REFERENCES announcements(id)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE enquiries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(30),
    subject VARCHAR(255),
    message TEXT NOT NULL,
    course_id BIGINT,
    status ENUM('NEW','IN_PROGRESS','RESPONDED','CLOSED','SPAM') NOT NULL DEFAULT 'NEW',
    assigned_to_user_id BIGINT,
    response_notes TEXT,
    responded_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_enquiries_course FOREIGN KEY (course_id) REFERENCES courses(id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_enquiries_assignee FOREIGN KEY (assigned_to_user_id) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE website_content (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_key VARCHAR(100) NOT NULL UNIQUE,
    title VARCHAR(255),
    content TEXT,
    image_url VARCHAR(500),
    status ENUM('DRAFT','PUBLISHED','ARCHIVED') NOT NULL DEFAULT 'DRAFT',
    display_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Useful indexes
CREATE INDEX idx_students_status ON students(status);
CREATE INDEX idx_teachers_status ON teachers(status);
CREATE INDEX idx_courses_category ON courses(category_id);
CREATE INDEX idx_courses_status ON courses(status);
CREATE INDEX idx_batches_course ON batches(course_id);
CREATE INDEX idx_batches_status ON batches(status);
CREATE INDEX idx_enrollments_student ON enrollments(student_id);
CREATE INDEX idx_enrollments_batch ON enrollments(batch_id);
CREATE INDEX idx_attendance_date ON attendance(attendance_date);
CREATE INDEX idx_assignments_batch ON assignments(batch_id);
CREATE INDEX idx_assignments_teacher ON assignments(teacher_id);
CREATE INDEX idx_submissions_student ON submissions(student_id);
CREATE INDEX idx_exams_batch ON exams(batch_id);
CREATE INDEX idx_exams_date ON exams(exam_date);
CREATE INDEX idx_exam_results_student ON exam_results(student_id);
CREATE INDEX idx_fee_accounts_student ON fee_accounts(student_id);
CREATE INDEX idx_fee_accounts_status ON fee_accounts(status);
CREATE INDEX idx_payments_student ON payments(student_id);
CREATE INDEX idx_payments_date ON payments(payment_date);
CREATE INDEX idx_certificates_student ON certificates(student_id);
CREATE INDEX idx_notifications_user_read ON notifications(user_id, is_read);
CREATE INDEX idx_enquiries_status ON enquiries(status);
CREATE INDEX idx_enquiries_created_at ON enquiries(created_at);
CREATE INDEX idx_website_content_status ON website_content(status);

-- Verification
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'foxbrain_db'
ORDER BY TABLE_NAME;
