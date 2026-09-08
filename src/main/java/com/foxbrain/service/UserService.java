package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.UserDAO;
import com.foxbrain.model.User;
import com.foxbrain.util.PasswordHashUtil;

public class UserService {

    private static final long STUDENT_ROLE_ID = 3;

    private final UserDAO dao;

    public UserService() {
        this.dao = new UserDAO();
    }

    public User findById(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return dao.findById(id);
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public long save(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {

            throw new IllegalArgumentException("Username is required");
        }

        if (user.getEmail() == null ||
                user.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPasswordHash() == null ||
                user.getPasswordHash().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Password hash is required"
            );
        }

        return dao.insert(user);
    }

    public boolean update(User user) {

        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }

        return dao.update(user);
    }

    public boolean delete(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return dao.delete(id);
    }

    public User authenticate(String username, String password) {

        if (username == null ||
                username.trim().isEmpty()) {
            return null;
        }

        if (password == null ||
                password.isEmpty()) {
            return null;
        }

        User user = dao.findByUsername(username.trim());

        if (user == null) {
            return null;
        }

        if (user.getStatus() == null ||
                !"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            return null;
        }

        if (user.getPasswordHash() == null ||
                user.getPasswordHash().isEmpty()) {
            return null;
        }

        boolean passwordCorrect =
                PasswordHashUtil.verifyPassword(
                        password,
                        user.getPasswordHash()
                );

        if (!passwordCorrect) {
            return null;
        }

        return user;
    }

    // =========================================================
    // CREATE STUDENT USER
    // =========================================================

    public long createStudentUser(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String phone) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student username is required."
            );
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student email is required."
            );
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(
                    "Student password is required."
            );
        }

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student first name is required."
            );
        }

        // Prevent duplicate username
        User existingUser =
                dao.findByUsername(username.trim());

        if (existingUser != null) {
            throw new IllegalArgumentException(
                    "Username already exists."
            );
        }

        User studentUser = new User();

        studentUser.setRoleId(STUDENT_ROLE_ID);
        studentUser.setUsername(username.trim());
        studentUser.setEmail(email.trim());

        String passwordHash =
                PasswordHashUtil.hashPassword(password);

        studentUser.setPasswordHash(passwordHash);

        studentUser.setFirstName(firstName.trim());

        if (lastName != null) {
            studentUser.setLastName(lastName.trim());
        }

        if (phone != null) {
            studentUser.setPhone(phone.trim());
        }

        studentUser.setStatus("ACTIVE");

        return dao.insert(studentUser);
    }
}