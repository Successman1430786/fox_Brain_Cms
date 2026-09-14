USE foxbrain;

-- =========================================================
-- FOXBRAIN EXAM MODULE
-- =========================================================

-- =========================================================
-- 1. EXAMS
-- =========================================================

CREATE TABLE IF NOT EXISTS exams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,

    exam_type ENUM(
        'QUIZ',
        'MIDTERM',
        'FINAL',
        'PRACTICAL',
        'PROJECT',
        'OTHER'
    ) NOT NULL,

    exam_mode ENUM(
        'ONLINE',
        'OFFLINE'
    ) NOT NULL,

    exam_date DATE,
    start_time TIME,
    end_time TIME,

    duration_minutes INT NULL,

    total_marks DECIMAL(8,2) NOT NULL,
    passing_marks DECIMAL(8,2),

    room_name VARCHAR(100),

    instructions TEXT,

    allow_navigation BOOLEAN NOT NULL DEFAULT TRUE,
    shuffle_questions BOOLEAN NOT NULL DEFAULT FALSE,
    shuffle_options BOOLEAN NOT NULL DEFAULT FALSE,

    status ENUM(
        'DRAFT',
        'SCHEDULED',
        'COMPLETED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'DRAFT',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_exams_batch
        FOREIGN KEY (batch_id)
        REFERENCES batches(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_exam_total_marks
        CHECK (total_marks > 0),

    CONSTRAINT chk_exam_passing_marks
        CHECK (
            passing_marks IS NULL
            OR (
                passing_marks >= 0
                AND passing_marks <= total_marks
            )
        )

) ENGINE=InnoDB;


-- =========================================================
-- 2. QUESTION BANK
-- =========================================================

CREATE TABLE IF NOT EXISTS question_bank (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    course_id BIGINT NULL,

    question_text TEXT NOT NULL,

    question_type ENUM(
        'MCQ',
        'TRUE_FALSE',
        'SHORT_ANSWER',
        'LONG_ANSWER',
        'CODING'
    ) NOT NULL,

    difficulty ENUM(
        'EASY',
        'MEDIUM',
        'HARD'
    ) NOT NULL DEFAULT 'MEDIUM',

    default_marks DECIMAL(8,2) NOT NULL DEFAULT 1.00,

    negative_marks DECIMAL(8,2) NOT NULL DEFAULT 0.00,

    explanation TEXT NULL,

    status ENUM(
        'ACTIVE',
        'INACTIVE'
    ) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT chk_question_marks
        CHECK (default_marks > 0),

    CONSTRAINT chk_negative_marks
        CHECK (negative_marks >= 0),

    CONSTRAINT fk_question_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL

) ENGINE=InnoDB;


-- =========================================================
-- 3. QUESTION OPTIONS
-- =========================================================

CREATE TABLE IF NOT EXISTS question_options (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    question_id BIGINT NOT NULL,

    option_text VARCHAR(1000) NOT NULL,

    option_order INT NOT NULL DEFAULT 1,

    is_correct BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_question_options_question
        FOREIGN KEY (question_id)
        REFERENCES question_bank(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    INDEX idx_question_options_question (question_id)

) ENGINE=InnoDB;


-- =========================================================
-- 4. EXAM QUESTIONS
-- =========================================================

CREATE TABLE IF NOT EXISTS exam_questions (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    exam_id BIGINT NOT NULL,

    question_id BIGINT NOT NULL,

    question_order INT NOT NULL DEFAULT 1,

    marks DECIMAL(8,2) NOT NULL,

    negative_marks DECIMAL(8,2) NOT NULL DEFAULT 0.00,

    section_name VARCHAR(100) NULL,

    is_required BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_exam_question
        UNIQUE (exam_id, question_id),

    CONSTRAINT fk_exam_questions_exam
        FOREIGN KEY (exam_id)
        REFERENCES exams(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_exam_questions_question
        FOREIGN KEY (question_id)
        REFERENCES question_bank(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_exam_question_marks
        CHECK (marks > 0),

    CONSTRAINT chk_exam_question_negative_marks
        CHECK (negative_marks >= 0),

    INDEX idx_exam_questions_exam (exam_id),

    INDEX idx_exam_questions_question (question_id)

) ENGINE=InnoDB;


-- =========================================================
-- 5. EXAM ATTEMPTS
-- =========================================================

CREATE TABLE IF NOT EXISTS exam_attempts (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    exam_id BIGINT NOT NULL,

    student_id BIGINT NOT NULL,

    attempt_number INT NOT NULL DEFAULT 1,

    started_at DATETIME NOT NULL,

    submitted_at DATETIME NULL,

    auto_submitted BOOLEAN NOT NULL DEFAULT FALSE,

    status ENUM(
        'IN_PROGRESS',
        'SUBMITTED',
        'EVALUATED',
        'ABANDONED'
    ) NOT NULL DEFAULT 'IN_PROGRESS',

    total_marks DECIMAL(8,2) NULL,

    obtained_marks DECIMAL(8,2) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_exam_attempt_exam
        FOREIGN KEY (exam_id)
        REFERENCES exams(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_exam_attempt_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    INDEX idx_attempt_exam (exam_id),

    INDEX idx_attempt_student (student_id)

) ENGINE=InnoDB;


-- =========================================================
-- 6. EXAM ANSWERS
-- =========================================================

CREATE TABLE IF NOT EXISTS exam_answers (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    attempt_id BIGINT NOT NULL,

    exam_question_id BIGINT NOT NULL,

    selected_option_id BIGINT NULL,

    answer_text TEXT NULL,

    marks_obtained DECIMAL(8,2) NULL,

    is_correct BOOLEAN NULL,

    evaluated BOOLEAN NOT NULL DEFAULT FALSE,

    teacher_remarks VARCHAR(500) NULL,

    answered_at DATETIME NULL,

    evaluated_at DATETIME NULL,

    CONSTRAINT uq_attempt_question
        UNIQUE (attempt_id, exam_question_id),

    CONSTRAINT fk_exam_answer_attempt
        FOREIGN KEY (attempt_id)
        REFERENCES exam_attempts(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_exam_answer_question
        FOREIGN KEY (exam_question_id)
        REFERENCES exam_questions(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_exam_answer_option
        FOREIGN KEY (selected_option_id)
        REFERENCES question_options(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    INDEX idx_exam_answers_attempt (attempt_id),

    INDEX idx_exam_answers_question (exam_question_id)

) ENGINE=InnoDB;


-- =========================================================
-- 7. EXAM RESULTS
-- =========================================================

CREATE TABLE IF NOT EXISTS exam_results (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    exam_id BIGINT NOT NULL,

    student_id BIGINT NOT NULL,

    marks_obtained DECIMAL(8,2) NOT NULL,

    grade VARCHAR(20),

    result_status ENUM(
        'PASS',
        'FAIL',
        'ABSENT',
        'WITHHELD'
    ) NOT NULL,

    remarks VARCHAR(500),

    published_at DATETIME NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_exam_student
        UNIQUE (exam_id, student_id),

    CONSTRAINT fk_exam_results_exam
        FOREIGN KEY (exam_id)
        REFERENCES exams(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_exam_results_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_exam_result_marks
        CHECK (marks_obtained >= 0)

) ENGINE=InnoDB;