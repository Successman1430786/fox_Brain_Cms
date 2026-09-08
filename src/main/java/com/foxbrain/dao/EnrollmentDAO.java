package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.foxbrain.model.Enrollment;
import com.foxbrain.util.DBConnection;

public class EnrollmentDAO {

    private Enrollment mapResultSet(ResultSet rs) throws SQLException {
        Enrollment obj = new Enrollment();
        obj.setId(rs.getLong("id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setBatchId(rs.getLong("batch_id"));
        obj.setEnrollmentNumber(rs.getString("enrollment_number"));
        obj.setEnrollmentDate(rs.getObject("enrollment_date", LocalDate.class));
        obj.setStatus(rs.getString("status"));
        obj.setCompletionDate(rs.getObject("completion_date", LocalDate.class));
        obj.setNotes(rs.getString("notes"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Enrollment findById(long id) {
        String sql = "SELECT * FROM enrollments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Enrollment by id", e);
        }
    }

    public List<Enrollment> findAll() {
        String sql = "SELECT * FROM enrollments ORDER BY id DESC";
        List<Enrollment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Enrollment", e);
        }
    }

    public long insert(Enrollment obj) {
        String sql = "INSERT INTO enrollments (student_id, batch_id, enrollment_number, enrollment_date, status, completion_date, notes, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getEnrollmentNumber());
            ps.setObject(i++, obj.getEnrollmentDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCompletionDate());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Enrollment", e);
        }
    }

    public boolean update(Enrollment obj) {
        String sql = "UPDATE enrollments SET student_id = ?, batch_id = ?, enrollment_number = ?, enrollment_date = ?, status = ?, completion_date = ?, notes = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getEnrollmentNumber());
            ps.setObject(i++, obj.getEnrollmentDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCompletionDate());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Enrollment", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM enrollments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Enrollment", e);
        }
    }
}
