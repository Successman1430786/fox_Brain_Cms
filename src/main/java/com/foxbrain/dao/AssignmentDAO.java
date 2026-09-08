package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.foxbrain.model.Assignment;
import com.foxbrain.util.DBConnection;

public class AssignmentDAO {

    private Assignment mapResultSet(ResultSet rs) throws SQLException {
        Assignment obj = new Assignment();
        obj.setId(rs.getLong("id"));
        obj.setBatchId(rs.getLong("batch_id"));
        obj.setTeacherId(rs.getLong("teacher_id"));
        obj.setTitle(rs.getString("title"));
        obj.setDescription(rs.getString("description"));
        obj.setAssignedDate(rs.getObject("assigned_date", LocalDate.class));
        obj.setDueDate(rs.getObject("due_date", LocalDate.class));
        obj.setMaxMarks(rs.getObject("max_marks", BigDecimal.class));
        obj.setAttachmentUrl(rs.getString("attachment_url"));
        obj.setStatus(rs.getString("status"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Assignment findById(long id) {
        String sql = "SELECT * FROM assignments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Assignment by id", e);
        }
    }

    public List<Assignment> findAll() {
        String sql = "SELECT * FROM assignments ORDER BY id DESC";
        List<Assignment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Assignment", e);
        }
    }

    public long insert(Assignment obj) {
        String sql = "INSERT INTO assignments (batch_id, teacher_id, title, description, assigned_date, due_date, max_marks, attachment_url, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTeacherId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getAssignedDate());
            ps.setObject(i++, obj.getDueDate());
            ps.setObject(i++, obj.getMaxMarks());
            ps.setObject(i++, obj.getAttachmentUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Assignment", e);
        }
    }

    public boolean update(Assignment obj) {
        String sql = "UPDATE assignments SET batch_id = ?, teacher_id = ?, title = ?, description = ?, assigned_date = ?, due_date = ?, max_marks = ?, attachment_url = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTeacherId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getAssignedDate());
            ps.setObject(i++, obj.getDueDate());
            ps.setObject(i++, obj.getMaxMarks());
            ps.setObject(i++, obj.getAttachmentUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Assignment", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM assignments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Assignment", e);
        }
    }
}
