package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.foxbrain.model.User;
import com.foxbrain.util.DBConnection;

public class UserDAO {

    // =========================================================
    // MAP RESULT SET
    // =========================================================

    private User mapResultSet(ResultSet rs) throws SQLException {

        User obj = new User();

        obj.setId(rs.getLong("id"));
        obj.setRoleId(rs.getLong("role_id"));
        obj.setUsername(rs.getString("username"));
        obj.setEmail(rs.getString("email"));
        obj.setPasswordHash(rs.getString("password_hash"));
        obj.setFirstName(rs.getString("first_name"));
        obj.setLastName(rs.getString("last_name"));
        obj.setPhone(rs.getString("phone"));
        obj.setStatus(rs.getString("status"));

        obj.setLastLoginAt(
            rs.getObject("last_login_at", LocalDateTime.class)
        );

        obj.setCreatedAt(
            rs.getObject("created_at", LocalDateTime.class)
        );

        obj.setUpdatedAt(
            rs.getObject("updated_at", LocalDateTime.class)
        );

        return obj;
    }

    // =========================================================
    // FIND USER BY ID
    // =========================================================

    public User findById(long id) {

        String sql =
            "SELECT * FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding User by ID: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // FIND ALL USERS
    // =========================================================

    public List<User> findAll() {

        String sql =
            "SELECT * FROM users ORDER BY id DESC";

        List<User> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

            return list;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding all users: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // INSERT USER
    // NORMAL CONNECTION
    // =========================================================

    public long insert(User obj) {

        String sql =
            "INSERT INTO users (" +
            "role_id, " +
            "username, " +
            "email, " +
            "password_hash, " +
            "first_name, " +
            "last_name, " +
            "phone, " +
            "status, " +
            "last_login_at, " +
            "created_at, " +
            "updated_at" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
                 )) {

            int i = 1;

            ps.setLong(i++, obj.getRoleId());
            ps.setString(i++, obj.getUsername());
            ps.setString(i++, obj.getEmail());
            ps.setString(i++, obj.getPasswordHash());
            ps.setString(i++, obj.getFirstName());
            ps.setString(i++, obj.getLastName());
            ps.setString(i++, obj.getPhone());
            ps.setString(i++, obj.getStatus());
            ps.setObject(i++, obj.getLastLoginAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException(
                    "User could not be inserted."
                );
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }

                throw new RuntimeException(
                    "User was inserted, but generated ID was not returned."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error inserting User: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // INSERT USER
    // EXISTING CONNECTION / TRANSACTION
    // =========================================================

    public long insert(Connection con, User obj) {

        String sql =
            "INSERT INTO users (" +
            "role_id, " +
            "username, " +
            "email, " +
            "password_hash, " +
            "first_name, " +
            "last_name, " +
            "phone, " +
            "status" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                 con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
                 )) {

            int i = 1;

            ps.setLong(i++, obj.getRoleId());
            ps.setString(i++, obj.getUsername());
            ps.setString(i++, obj.getEmail());
            ps.setString(i++, obj.getPasswordHash());
            ps.setString(i++, obj.getFirstName());
            ps.setString(i++, obj.getLastName());
            ps.setString(i++, obj.getPhone());
            ps.setString(i++, obj.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException(
                    "User could not be inserted."
                );
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }

                throw new RuntimeException(
                    "User was inserted, but generated ID was not returned."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error inserting User: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // UPDATE USER
    // =========================================================

    public boolean update(User obj) {

        String sql =
            "UPDATE users SET " +
            "role_id = ?, " +
            "username = ?, " +
            "email = ?, " +
            "password_hash = ?, " +
            "first_name = ?, " +
            "last_name = ?, " +
            "phone = ?, " +
            "status = ?, " +
            "last_login_at = ?, " +
            "created_at = ?, " +
            "updated_at = ? " +
            "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(sql)) {

            int i = 1;

            ps.setLong(i++, obj.getRoleId());
            ps.setString(i++, obj.getUsername());
            ps.setString(i++, obj.getEmail());
            ps.setString(i++, obj.getPasswordHash());
            ps.setString(i++, obj.getFirstName());
            ps.setString(i++, obj.getLastName());
            ps.setString(i++, obj.getPhone());
            ps.setString(i++, obj.getStatus());
            ps.setObject(i++, obj.getLastLoginAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error updating User: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // DELETE USER
    // =========================================================

    public boolean delete(long id) {

        String sql =
            "DELETE FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error deleting User: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // FIND USER BY USERNAME
    // =========================================================

    public User findByUsername(String username) {

        String sql =
            "SELECT * FROM users WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding User by username: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // FIND USER BY EMAIL
    // =========================================================

    public User findByEmail(String email) {

        String sql =
            "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding User by email: "
                + e.getMessage(),
                e
            );
        }
    }
}