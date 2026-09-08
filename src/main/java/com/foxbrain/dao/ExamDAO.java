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
import java.time.LocalTime;

import com.foxbrain.model.Exam;
import com.foxbrain.util.DBConnection;

public class ExamDAO {

    private Exam mapResultSet(ResultSet rs) throws SQLException {
        Exam obj = new Exam();
        obj.setId(rs.getLong("id"));
        obj.setBatchId(rs.getLong("batch_id"));
        obj.setTitle(rs.getString("title"));
        obj.setExamType(rs.getString("exam_type"));
        obj.setExamDate(rs.getObject("exam_date", LocalDate.class));
        obj.setStartTime(rs.getObject("start_time", LocalTime.class));
        obj.setEndTime(rs.getObject("end_time", LocalTime.class));
        obj.setTotalMarks(rs.getObject("total_marks", BigDecimal.class));
        obj.setPassingMarks(rs.getObject("passing_marks", BigDecimal.class));
        obj.setRoomName(rs.getString("room_name"));
        obj.setInstructions(rs.getString("instructions"));
        obj.setStatus(rs.getString("status"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Exam findById(long id) {
        String sql = "SELECT * FROM exams WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Exam by id", e);
        }
    }

    public List<Exam> findAll() {
        String sql = "SELECT * FROM exams ORDER BY id DESC";
        List<Exam> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Exam", e);
        }
    }

    public long insert(Exam obj) {
        String sql = "INSERT INTO exams (batch_id, title, exam_type, exam_date, start_time, end_time, total_marks, passing_marks, room_name, instructions, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getExamType());
            ps.setObject(i++, obj.getExamDate());
            ps.setObject(i++, obj.getStartTime());
            ps.setObject(i++, obj.getEndTime());
            ps.setObject(i++, obj.getTotalMarks());
            ps.setObject(i++, obj.getPassingMarks());
            ps.setObject(i++, obj.getRoomName());
            ps.setObject(i++, obj.getInstructions());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Exam", e);
        }
    }

    public boolean update(Exam obj) {
        String sql = "UPDATE exams SET batch_id = ?, title = ?, exam_type = ?, exam_date = ?, start_time = ?, end_time = ?, total_marks = ?, passing_marks = ?, room_name = ?, instructions = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getBatchId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getExamType());
            ps.setObject(i++, obj.getExamDate());
            ps.setObject(i++, obj.getStartTime());
            ps.setObject(i++, obj.getEndTime());
            ps.setObject(i++, obj.getTotalMarks());
            ps.setObject(i++, obj.getPassingMarks());
            ps.setObject(i++, obj.getRoomName());
            ps.setObject(i++, obj.getInstructions());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Exam", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM exams WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Exam", e);
        }
    }
}
