package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.BatchTeacher;
import com.foxbrain.util.DBConnection;

public class BatchTeacherDAO {

    private BatchTeacher mapResultSet(ResultSet rs) throws SQLException {
        BatchTeacher obj = new BatchTeacher();
        obj.setId(rs.getLong("id"));
        obj.setBatchId(rs.getLong("batch_id"));
        obj.setTeacherId(rs.getLong("teacher_id"));
        obj.setIsPrimaryTeacher(rs.getBoolean("is_primary_teacher"));
        obj.setAssignedAt(rs.getObject("assigned_at", LocalDateTime.class));
        return obj;
    }

    public BatchTeacher findById(long id) {
        String sql = "SELECT * FROM batch_teachers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding BatchTeacher by id", e);
        }
    }

    public List<BatchTeacher> findAll() {
        String sql = "SELECT * FROM batch_teachers ORDER BY id DESC";
        List<BatchTeacher> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all BatchTeacher", e);
        }
    }

    public long insert(BatchTeacher obj) {
        String sql = "INSERT INTO batch_teachers (batch_id, teacher_id, is_primary_teacher, assigned_at) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTeacherId());
            ps.setObject(i++, obj.getIsPrimaryTeacher());
            ps.setObject(i++, obj.getAssignedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting BatchTeacher", e);
        }
    }

    public boolean update(BatchTeacher obj) {
        String sql = "UPDATE batch_teachers SET batch_id = ?, teacher_id = ?, is_primary_teacher = ?, assigned_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTeacherId());
            ps.setObject(i++, obj.getIsPrimaryTeacher());
            ps.setObject(i++, obj.getAssignedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating BatchTeacher", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM batch_teachers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting BatchTeacher", e);
        }
    }
}
