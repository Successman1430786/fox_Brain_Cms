package com.foxbrain.dao;

import com.foxbrain.model.Assignment;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    public List<Assignment> getAll() throws SQLException {

        List<Assignment> assignments = new ArrayList<>();

        String sql =
                "SELECT a.*, " +
                "b.name AS batch_name, b.batch_code, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', COALESCE(u.last_name,'')) AS teacher_name " +
                "FROM assignments a " +
                "LEFT JOIN batches b ON a.batch_id = b.id " +
                "LEFT JOIN teachers t ON a.teacher_id = t.id " +
                "LEFT JOIN users u ON t.user_id = u.id " +
                "ORDER BY a.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                assignments.add(mapRow(rs));
            }
        }

        return assignments;
    }

    public Assignment getById(long id) throws SQLException {

        String sql =
                "SELECT a.*, " +
                "b.name AS batch_name, b.batch_code, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', COALESCE(u.last_name,'')) AS teacher_name " +
                "FROM assignments a " +
                "LEFT JOIN batches b ON a.batch_id = b.id " +
                "LEFT JOIN teachers t ON a.teacher_id = t.id " +
                "LEFT JOIN users u ON t.user_id = u.id " +
                "WHERE a.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public boolean create(Assignment assignment) throws SQLException {

        String sql =
                "INSERT INTO assignments " +
                "(batch_id, teacher_id, title, description, instructions, " +
                "assigned_date, due_date, max_marks, attachment_url, " +
                "submission_type, allow_late_submission, late_penalty, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, assignment.getBatchId());
            ps.setLong(2, assignment.getTeacherId());
            ps.setString(3, assignment.getTitle());
            ps.setString(4, assignment.getDescription());
            ps.setString(5, assignment.getInstructions());

            ps.setDate(6, Date.valueOf(assignment.getAssignedDate()));

            if (assignment.getDueDate() != null) {
                ps.setDate(7, Date.valueOf(assignment.getDueDate()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            if (assignment.getMaxMarks() != null) {
                ps.setBigDecimal(8, assignment.getMaxMarks());
            } else {
                ps.setNull(8, Types.DECIMAL);
            }

            ps.setString(9, assignment.getAttachmentUrl());
            ps.setString(10, assignment.getSubmissionType());
            ps.setBoolean(11, assignment.isAllowLateSubmission());

            if (assignment.getLatePenalty() != null) {
                ps.setBigDecimal(12, assignment.getLatePenalty());
            } else {
                ps.setNull(12, Types.DECIMAL);
            }

            ps.setString(13, assignment.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Assignment assignment) throws SQLException {

        String sql =
                "UPDATE assignments SET " +
                "batch_id = ?, " +
                "teacher_id = ?, " +
                "title = ?, " +
                "description = ?, " +
                "instructions = ?, " +
                "assigned_date = ?, " +
                "due_date = ?, " +
                "max_marks = ?, " +
                "attachment_url = ?, " +
                "submission_type = ?, " +
                "allow_late_submission = ?, " +
                "late_penalty = ?, " +
                "status = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, assignment.getBatchId());
            ps.setLong(2, assignment.getTeacherId());
            ps.setString(3, assignment.getTitle());
            ps.setString(4, assignment.getDescription());
            ps.setString(5, assignment.getInstructions());
            ps.setDate(6, Date.valueOf(assignment.getAssignedDate()));

            if (assignment.getDueDate() != null) {
                ps.setDate(7, Date.valueOf(assignment.getDueDate()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            if (assignment.getMaxMarks() != null) {
                ps.setBigDecimal(8, assignment.getMaxMarks());
            } else {
                ps.setNull(8, Types.DECIMAL);
            }

            ps.setString(9, assignment.getAttachmentUrl());
            ps.setString(10, assignment.getSubmissionType());
            ps.setBoolean(11, assignment.isAllowLateSubmission());

            if (assignment.getLatePenalty() != null) {
                ps.setBigDecimal(12, assignment.getLatePenalty());
            } else {
                ps.setNull(12, Types.DECIMAL);
            }

            ps.setString(13, assignment.getStatus());
            ps.setLong(14, assignment.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql = "DELETE FROM assignments WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    private Assignment mapRow(ResultSet rs) throws SQLException {

        Assignment a = new Assignment();

        a.setId(rs.getLong("id"));
        a.setBatchId(rs.getLong("batch_id"));
        a.setTeacherId(rs.getLong("teacher_id"));

        a.setTitle(rs.getString("title"));
        a.setDescription(rs.getString("description"));
        a.setInstructions(rs.getString("instructions"));

        Date assignedDate = rs.getDate("assigned_date");
        if (assignedDate != null) {
            a.setAssignedDate(assignedDate.toLocalDate());
        }

        Date dueDate = rs.getDate("due_date");
        if (dueDate != null) {
            a.setDueDate(dueDate.toLocalDate());
        }

        a.setMaxMarks(rs.getBigDecimal("max_marks"));
        a.setAttachmentUrl(rs.getString("attachment_url"));

        a.setSubmissionType(rs.getString("submission_type"));
        a.setAllowLateSubmission(rs.getBoolean("allow_late_submission"));
        a.setLatePenalty(rs.getBigDecimal("late_penalty"));

        a.setStatus(rs.getString("status"));

        a.setBatchName(rs.getString("batch_name"));
        a.setBatchCode(rs.getString("batch_code"));
        a.setTeacherName(rs.getString("teacher_name"));

        return a;
    }
}