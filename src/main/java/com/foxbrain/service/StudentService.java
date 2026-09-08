package com.foxbrain.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.foxbrain.dao.StudentDAO;
import com.foxbrain.dao.UserDAO;
import com.foxbrain.model.Student;
import com.foxbrain.model.User;
import com.foxbrain.util.DBConnection;
import com.foxbrain.util.PasswordHashUtil;

public class StudentService {

    private final StudentDAO studentDAO;
    private final UserDAO userDAO;

    // Role ID 3 = STUDENT
    private static final long STUDENT_ROLE_ID = 3;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.userDAO = new UserDAO();
    }

    // =========================================================
    // GET STUDENT BY ID
    // =========================================================

    public Student getById(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Invalid student ID."
            );
        }

        return studentDAO.findById(id);
    }

    // =========================================================
    // GET ALL STUDENTS
    // =========================================================

    public List<Student> getAll() {
        return studentDAO.findAll();
    }

    // =========================================================
    // GET STUDENT BY ADMISSION NUMBER
    // =========================================================

    public Student getByAdmissionNumber(
            String admissionNumber) {

        if (admissionNumber == null ||
                admissionNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Admission number is required."
            );
        }

        return studentDAO.findByAdmissionNumber(
            admissionNumber.trim()
        );
    }

    // =========================================================
    // CREATE STUDENT - OLD METHOD
    // =========================================================
    // This method remains for existing functionality.
    // New student registration should use:
    // createStudentWithAccount(...)
    // =========================================================

    public long create(Student student) {

        validateStudent(student);

        if (student.getUserId() <= 0) {

            throw new IllegalArgumentException(
                "A valid user ID is required for the student."
            );
        }

        prepareStudent(student);

        return studentDAO.insert(student);
    }

    // =========================================================
    // CREATE STUDENT + USER ACCOUNT
    // =========================================================
    // This is the NEW main method.
    //
    // 1. Create users record
    // 2. Get generated users.id
    // 3. Set students.user_id
    // 4. Create students record
    // 5. COMMIT
    //
    // If anything fails:
    // ROLLBACK everything.
    // =========================================================

    public long createStudentWithAccount(
            Student student,
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String phone) {

        // -----------------------------------------------------
        // Validate account information
        // -----------------------------------------------------

        if (student == null) {

            throw new IllegalArgumentException(
                "Student data is required."
            );
        }

        if (username == null ||
                username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Username is required."
            );
        }

        if (username.trim().length() > 100) {

            throw new IllegalArgumentException(
                "Username cannot exceed 100 characters."
            );
        }

        if (email == null ||
                email.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Email is required."
            );
        }

        if (email.trim().length() > 150) {

            throw new IllegalArgumentException(
                "Email cannot exceed 150 characters."
            );
        }

        if (password == null ||
                password.isEmpty()) {

            throw new IllegalArgumentException(
                "Password is required."
            );
        }

        if (firstName == null ||
                firstName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "First name is required."
            );
        }

        if (firstName.trim().length() > 100) {

            throw new IllegalArgumentException(
                "First name cannot exceed 100 characters."
            );
        }

        if (lastName != null &&
                lastName.trim().length() > 100) {

            throw new IllegalArgumentException(
                "Last name cannot exceed 100 characters."
            );
        }

        if (phone != null &&
                phone.trim().length() > 30) {

            throw new IllegalArgumentException(
                "Phone cannot exceed 30 characters."
            );
        }

        // -----------------------------------------------------
        // Validate student information
        // -----------------------------------------------------

        validateStudent(student);

        // -----------------------------------------------------
        // Check duplicate username/email BEFORE transaction
        // -----------------------------------------------------

        User existingUsername =
            userDAO.findByUsername(
                username.trim()
            );

        if (existingUsername != null) {

            throw new IllegalArgumentException(
                "Username already exists."
            );
        }

        User existingEmail =
            userDAO.findByEmail(
                email.trim()
            );

        if (existingEmail != null) {

            throw new IllegalArgumentException(
                "Email already exists."
            );
        }

        // -----------------------------------------------------
        // Check duplicate admission number
        // -----------------------------------------------------

        Student existingStudent =
            studentDAO.findByAdmissionNumber(
                student.getAdmissionNumber().trim()
            );

        if (existingStudent != null) {

            throw new IllegalArgumentException(
                "Admission number already exists."
            );
        }

        // -----------------------------------------------------
        // Prepare User
        // -----------------------------------------------------

        User user = new User();

        user.setRoleId(STUDENT_ROLE_ID);

        user.setUsername(
            username.trim()
        );

        user.setEmail(
            email.trim()
        );

        // NEVER store plain password
        user.setPasswordHash(
            PasswordHashUtil.hashPassword(password)
        );

        user.setFirstName(
            firstName.trim()
        );

        if (lastName != null &&
                !lastName.trim().isEmpty()) {

            user.setLastName(
                lastName.trim()
            );

        } else {

            user.setLastName(null);
        }

        if (phone != null &&
                !phone.trim().isEmpty()) {

            user.setPhone(
                phone.trim()
            );

        } else {

            user.setPhone(null);
        }

        user.setStatus("ACTIVE");

        // -----------------------------------------------------
        // DATABASE TRANSACTION
        // -----------------------------------------------------

        try (Connection con =
                 DBConnection.getConnection()) {

            try {

                // Start transaction
                con.setAutoCommit(false);

                // -------------------------------------------------
                // STEP 1: Create USER
                // -------------------------------------------------

                long userId =
                    userDAO.insert(
                        con,
                        user
                    );

                if (userId <= 0) {

                    throw new RuntimeException(
                        "Student user account could not be created."
                    );
                }

                // -------------------------------------------------
                // STEP 2: Connect Student to User
                // -------------------------------------------------

                student.setUserId(userId);

                prepareStudent(student);

                // -------------------------------------------------
                // STEP 3: Create STUDENT
                // -------------------------------------------------

                long studentId =
                    studentDAO.insert(
                        con,
                        student
                    );

                if (studentId <= 0) {

                    throw new RuntimeException(
                        "Student profile could not be created."
                    );
                }

                // -------------------------------------------------
                // STEP 4: COMMIT
                // -------------------------------------------------

                con.commit();

                return studentId;

            } catch (Exception e) {

                // -------------------------------------------------
                // ROLLBACK
                // -------------------------------------------------

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }

                throw new RuntimeException(
                    "Student creation failed: "
                    + e.getMessage(),
                    e
                );

            } finally {

                try {
                    con.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // Nothing to do
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Database connection error: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    public boolean update(Student student) {

        if (student == null ||
                student.getId() <= 0) {

            throw new IllegalArgumentException(
                "Invalid student."
            );
        }

        validateStudent(student);

        if (student.getUserId() <= 0) {

            throw new IllegalArgumentException(
                "A valid user ID is required for the student."
            );
        }

        prepareStudent(student);

        return studentDAO.update(student);
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    public boolean delete(long id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                "Invalid student ID."
            );
        }

        return studentDAO.delete(id);
    }

    // =========================================================
    // PREPARE STUDENT
    // =========================================================

    private void prepareStudent(Student student) {

        // Default status
        if (student.getStatus() == null ||
                student.getStatus().trim().isEmpty()) {

            student.setStatus("ACTIVE");

        } else {

            student.setStatus(
                student.getStatus().trim()
            );
        }

        // Clean admission number
        if (student.getAdmissionNumber() != null) {

            student.setAdmissionNumber(
                student.getAdmissionNumber().trim()
            );
        }

        // Clean text fields
        if (student.getGender() != null) {
            student.setGender(
                student.getGender().trim()
            );
        }

        if (student.getAddressLine1() != null) {
            student.setAddressLine1(
                student.getAddressLine1().trim()
            );
        }

        if (student.getAddressLine2() != null) {
            student.setAddressLine2(
                student.getAddressLine2().trim()
            );
        }

        if (student.getCity() != null) {
            student.setCity(
                student.getCity().trim()
            );
        }

        if (student.getState() != null) {
            student.setState(
                student.getState().trim()
            );
        }

        if (student.getPostalCode() != null) {
            student.setPostalCode(
                student.getPostalCode().trim()
            );
        }

        if (student.getCountry() != null) {
            student.setCountry(
                student.getCountry().trim()
            );
        }
    }

    // =========================================================
    // VALIDATE STUDENT
    // =========================================================

    private void validateStudent(Student student) {

        if (student == null) {

            throw new IllegalArgumentException(
                "Student data is required."
            );
        }

        // Admission Number
        if (student.getAdmissionNumber() == null ||
                student.getAdmissionNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Admission number is required."
            );
        }

        if (student.getAdmissionNumber().trim().length() > 50) {

            throw new IllegalArgumentException(
                "Admission number cannot exceed 50 characters."
            );
        }

        // Gender
        if (student.getGender() != null &&
                student.getGender().length() > 30) {

            throw new IllegalArgumentException(
                "Gender cannot exceed 30 characters."
            );
        }

        // Address Line 1
        if (student.getAddressLine1() != null &&
                student.getAddressLine1().length() > 255) {

            throw new IllegalArgumentException(
                "Address Line 1 cannot exceed 255 characters."
            );
        }

        // Address Line 2
        if (student.getAddressLine2() != null &&
                student.getAddressLine2().length() > 255) {

            throw new IllegalArgumentException(
                "Address Line 2 cannot exceed 255 characters."
            );
        }

        // City
        if (student.getCity() != null &&
                student.getCity().length() > 100) {

            throw new IllegalArgumentException(
                "City cannot exceed 100 characters."
            );
        }

        // State
        if (student.getState() != null &&
                student.getState().length() > 100) {

            throw new IllegalArgumentException(
                "State cannot exceed 100 characters."
            );
        }

        // Postal Code
        if (student.getPostalCode() != null &&
                student.getPostalCode().length() > 20) {

            throw new IllegalArgumentException(
                "Postal code cannot exceed 20 characters."
            );
        }

        // Country
        if (student.getCountry() != null &&
                student.getCountry().length() > 100) {

            throw new IllegalArgumentException(
                "Country cannot exceed 100 characters."
            );
        }

        // Status
        if (student.getStatus() != null &&
                student.getStatus().trim().length() > 30) {

            throw new IllegalArgumentException(
                "Invalid student status."
            );
        }
    }
}