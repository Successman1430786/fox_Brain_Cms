package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.Role;
import com.foxbrain.util.DBConnection;

public class RoleDAO {

    private Role mapResultSet(ResultSet rs) throws SQLException {
        Role obj = new Role();
        obj.setId(rs.getLong("id"));
        obj.setName(rs.getString("name"));
        obj.setDescription(rs.getString("description"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        return obj;
    }

    public Role findById(long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Role by id", e);
        }
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles ORDER BY id DESC";
        List<Role> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Role", e);
        }
    }

    public long insert(Role obj) {
        String sql = "INSERT INTO roles (name, description, created_at) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getCreatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Role", e);
        }
    }

    public boolean update(Role obj) {
        String sql = "UPDATE roles SET name = ?, description = ?, created_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Role", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Role", e);
        }
    }
}
