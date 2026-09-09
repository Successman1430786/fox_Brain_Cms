package com.foxbrain.service;

import com.foxbrain.dao.TeacherDAO;
import com.foxbrain.dao.UserDAO;
import com.foxbrain.model.Teacher;
import com.foxbrain.model.User;
import com.foxbrain.util.DBConnection;
import com.foxbrain.util.PasswordHashUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeacherService {

    private static final long TEACHER_ROLE_ID = 2;

    private final TeacherDAO teacherDAO;
    private final UserDAO userDAO;


    public TeacherService() {

        teacherDAO =
            new TeacherDAO();

        userDAO =
            new UserDAO();
    }


    // =========================================================
    // GET ALL
    // =========================================================

    public List<Teacher> getAll()
            throws SQLException {

        return teacherDAO.findAll();
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    public Teacher getById(long id)
            throws SQLException {

        return teacherDAO.findById(id);
    }


    // =========================================================
    // CREATE TEACHER + USER ACCOUNT
    // =========================================================

    public long createTeacherWithAccount(
            Teacher teacher,
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String phone)
            throws Exception {


        if (teacher == null) {
            throw new IllegalArgumentException(
                "Teacher information is required."
            );
        }


        if (isEmpty(username)) {
            throw new IllegalArgumentException(
                "Username is required."
            );
        }


        if (isEmpty(email)) {
            throw new IllegalArgumentException(
                "Email is required."
            );
        }


        if (isEmpty(password)) {
            throw new IllegalArgumentException(
                "Password is required."
            );
        }


        if (isEmpty(firstName)) {
            throw new IllegalArgumentException(
                "First name is required."
            );
        }


        if (isEmpty(
                teacher.getEmployeeNumber())) {

            throw new IllegalArgumentException(
                "Employee number is required."
            );
        }


        if (userDAO.findByUsername(username)
                != null) {

            throw new IllegalArgumentException(
                "Username already exists."
            );
        }


        if (userDAO.findByEmail(email)
                != null) {

            throw new IllegalArgumentException(
                "Email already exists."
            );
        }


        if (teacherDAO.findByEmployeeNumber(
                teacher.getEmployeeNumber())
                != null) {

            throw new IllegalArgumentException(
                "Employee number already exists."
            );
        }


        String passwordHash =
            PasswordHashUtil.hashPassword(
                password
            );


        User user =
            new User();

        user.setRoleId(
            TEACHER_ROLE_ID
        );

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setStatus("ACTIVE");


        Connection connection = null;


        try {

            connection =
                DBConnection.getConnection();

            connection.setAutoCommit(false);


            long userId =
                userDAO.insert(
                    connection,
                    user
                );


            teacher.setUserId(userId);


            if (teacher.getStatus() == null ||
                teacher.getStatus().trim().isEmpty()) {

                teacher.setStatus("ACTIVE");
            }


            long teacherId =
                teacherDAO.insert(
                    connection,
                    teacher
                );


            connection.commit();

            return teacherId;


        } catch (Exception e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            throw e;


        } finally {

            if (connection != null) {

                try {
                    connection.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }


    // =========================================================
    // UPDATE
    // =========================================================

    public boolean update(
            Teacher teacher)
            throws SQLException {

        if (teacher == null ||
            teacher.getId() <= 0) {

            throw new IllegalArgumentException(
                "Invalid teacher."
            );
        }


        if (isEmpty(
                teacher.getEmployeeNumber())) {

            throw new IllegalArgumentException(
                "Employee number is required."
            );
        }


        Teacher existing =
            teacherDAO.findById(
                teacher.getId()
            );


        if (existing == null) {

            throw new IllegalArgumentException(
                "Teacher not found."
            );
        }


        Teacher employeeCheck =
            teacherDAO.findByEmployeeNumber(
                teacher.getEmployeeNumber()
            );


        if (employeeCheck != null &&
            employeeCheck.getId()
                != teacher.getId()) {

            throw new IllegalArgumentException(
                "Employee number already exists."
            );
        }


        return teacherDAO.update(
            teacher
        );
    }


    // =========================================================
    // DELETE
    // =========================================================

    public boolean delete(long id)
            throws SQLException {

        Teacher teacher =
            teacherDAO.findById(id);

        if (teacher == null) {
            return false;
        }


        /*
         * We delete only the teacher record.
         *
         * The users record remains because
         * users is referenced by teachers and
         * other parts of the system may depend
         * on that account.
         */

        return teacherDAO.delete(id);
    }


    // =========================================================
    // HELPER
    // =========================================================

    private boolean isEmpty(String value) {

        return value == null ||
               value.trim().isEmpty();
    }
}