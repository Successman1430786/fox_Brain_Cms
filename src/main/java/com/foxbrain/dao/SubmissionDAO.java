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

import com.foxbrain.model.Submission;
import com.foxbrain.util.DBConnection;

public class SubmissionDAO {

    private Submission mapResultSet(ResultSet rs) throws SQLException {
        Submission obj = new Submission();
        obj.setId(rs.getLong("id"));
        obj.setAssignmentId(rs.getLong("assignment_id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setSubmittedAt(rs.getObject("submitted_at", LocalDateTime.class));
        obj.setSubmissionText(rs.getString("submission_text"));
        obj.setAttachmentUrl(rs.getString("attachment_url"));
        obj.setStatus(rs.getString("status"));
        obj.setMarks(rs.getObject("marks", BigDecimal.class));
        obj.setTeacherFeedback(rs.getString("teacher_feedback"));
        obj.setReviewedByTeacherId(rs.getString("reviewed_by_teacher_id"));
        obj.setReviewedAt(rs.getObject("reviewed_at", LocalDateTime.class));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Submission findById(long id) {
        String sql = "SELECT * FROM submissions WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Submission by id", e);
        }
    }

    public List<Submission> findAll() {
        String sql = "SELECT * FROM submissions ORDER BY id DESC";
        List<Submission> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Submission", e);
        }
    }

    public long insert(Submission obj) {
        String sql = "INSERT INTO submissions (assignment_id, student_id, submitted_at, submission_text, attachment_url, status, marks, teacher_feedback, reviewed_by_teacher_id, reviewed_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getAssignmentId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getSubmittedAt());
            ps.setObject(i++, obj.getSubmissionText());
            ps.setObject(i++, obj.getAttachmentUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getMarks());
            ps.setObject(i++, obj.getTeacherFeedback());
            ps.setObject(i++, obj.getReviewedByTeacherId());
            ps.setObject(i++, obj.getReviewedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Submission", e);
        }
    }

    public boolean update(Submission obj) {
        String sql = "UPDATE submissions SET assignment_id = ?, student_id = ?, submitted_at = ?, submission_text = ?, attachment_url = ?, status = ?, marks = ?, teacher_feedback = ?, reviewed_by_teacher_id = ?, reviewed_at = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getAssignmentId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getSubmittedAt());
            ps.setObject(i++, obj.getSubmissionText());
            ps.setObject(i++, obj.getAttachmentUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getMarks());
            ps.setObject(i++, obj.getTeacherFeedback());
            ps.setObject(i++, obj.getReviewedByTeacherId());
            ps.setObject(i++, obj.getReviewedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Submission", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM submissions WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Submission", e);
        }
    }
}
