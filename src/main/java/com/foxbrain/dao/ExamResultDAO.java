package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.foxbrain.model.ExamResult;
import com.foxbrain.util.DBConnection;

public class ExamResultDAO {

    private ExamResult mapResultSet(ResultSet rs) throws SQLException {
        ExamResult obj = new ExamResult();
        obj.setId(rs.getLong("id"));
        obj.setExamId(rs.getLong("exam_id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setMarksObtained(rs.getObject("marks_obtained", BigDecimal.class));
        obj.setGrade(rs.getString("grade"));
        obj.setResultStatus(rs.getString("result_status"));
        obj.setRemarks(rs.getString("remarks"));
        obj.setPublishedAt(rs.getObject("published_at", LocalDateTime.class));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public ExamResult findById(long id) {
        String sql = "SELECT * FROM exam_results WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding ExamResult by id", e);
        }
    }

    public List<ExamResult> findAll() {
        String sql = "SELECT * FROM exam_results ORDER BY id DESC";
        List<ExamResult> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all ExamResult", e);
        }
    }

    public long insert(ExamResult obj) {
        String sql = "INSERT INTO exam_results (exam_id, student_id, marks_obtained, grade, result_status, remarks, published_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getExamId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getMarksObtained());
            ps.setObject(i++, obj.getGrade());
            ps.setObject(i++, obj.getResultStatus());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getPublishedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting ExamResult", e);
        }
    }

    public boolean update(ExamResult obj) {
        String sql = "UPDATE exam_results SET exam_id = ?, student_id = ?, marks_obtained = ?, grade = ?, result_status = ?, remarks = ?, published_at = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getExamId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getMarksObtained());
            ps.setObject(i++, obj.getGrade());
            ps.setObject(i++, obj.getResultStatus());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getPublishedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating ExamResult", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM exam_results WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ExamResult", e);
        }
    }
}
