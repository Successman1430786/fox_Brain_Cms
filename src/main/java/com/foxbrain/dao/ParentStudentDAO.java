package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.ParentStudent;
import com.foxbrain.util.DBConnection;

public class ParentStudentDAO {

    private ParentStudent mapResultSet(ResultSet rs) throws SQLException {
        ParentStudent obj = new ParentStudent();
        obj.setId(rs.getLong("id"));
        obj.setParentId(rs.getLong("parent_id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setRelationshipType(rs.getString("relationship_type"));
        obj.setIsPrimaryContact(rs.getBoolean("is_primary_contact"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        return obj;
    }

    public ParentStudent findById(long id) {
        String sql = "SELECT * FROM parent_students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding ParentStudent by id", e);
        }
    }

    public List<ParentStudent> findAll() {
        String sql = "SELECT * FROM parent_students ORDER BY id DESC";
        List<ParentStudent> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all ParentStudent", e);
        }
    }

    public long insert(ParentStudent obj) {
        String sql = "INSERT INTO parent_students (parent_id, student_id, relationship_type, is_primary_contact, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getParentId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getRelationshipType());
            ps.setObject(i++, obj.getIsPrimaryContact());
            ps.setObject(i++, obj.getCreatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting ParentStudent", e);
        }
    }

    public boolean update(ParentStudent obj) {
        String sql = "UPDATE parent_students SET parent_id = ?, student_id = ?, relationship_type = ?, is_primary_contact = ?, created_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getParentId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getRelationshipType());
            ps.setObject(i++, obj.getIsPrimaryContact());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating ParentStudent", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM parent_students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ParentStudent", e);
        }
    }
}
