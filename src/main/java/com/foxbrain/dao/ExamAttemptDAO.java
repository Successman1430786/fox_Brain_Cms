package com.foxbrain.dao;

import com.foxbrain.model.ExamAttempt;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamAttemptDAO {

    public long create(ExamAttempt attempt) throws SQLException {

        String sql =
                "INSERT INTO exam_attempts (" +
                "exam_id, student_id, attempt_number, started_at, " +
                "auto_submitted, status, total_marks" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, attempt.getExamId());
            ps.setLong(2, attempt.getStudentId());
            ps.setInt(3, attempt.getAttemptNumber());

            if (attempt.getStartedAt() != null) {
                ps.setTimestamp(
                        4,
                        Timestamp.valueOf(attempt.getStartedAt())
                );
            } else {
                ps.setTimestamp(
                        4,
                        new Timestamp(System.currentTimeMillis())
                );
            }

            ps.setBoolean(5, attempt.isAutoSubmitted());
            ps.setString(6, attempt.getStatus());

            if (attempt.getTotalMarks() != null) {
                ps.setBigDecimal(7, attempt.getTotalMarks());
            } else {
                ps.setNull(7, Types.DECIMAL);
            }

            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Creating exam attempt failed.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException(
                "Creating exam attempt failed. No ID returned."
        );
    }

    public ExamAttempt getById(long id) throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id = e.id " +
                "INNER JOIN students s ON ea.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE ea.id = ?";

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

    public List<ExamAttempt> getByExamId(long examId)
            throws SQLException {

        List<ExamAttempt> attempts = new ArrayList<>();

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id = e.id " +
                "INNER JOIN students s ON ea.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE ea.exam_id = ? " +
                "ORDER BY ea.started_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    attempts.add(mapRow(rs));
                }
            }
        }

        return attempts;
    }

    public List<ExamAttempt> getByStudentId(long studentId)
            throws SQLException {

        List<ExamAttempt> attempts = new ArrayList<>();

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id = e.id " +
                "INNER JOIN students s ON ea.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE ea.student_id = ? " +
                "ORDER BY ea.started_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    attempts.add(mapRow(rs));
                }
            }
        }

        return attempts;
    }

    public ExamAttempt getActiveAttempt(
            long examId,
            long studentId) throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id = e.id " +
                "INNER JOIN students s ON ea.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE ea.exam_id = ? " +
                "AND ea.student_id = ? " +
                "AND ea.status = 'IN_PROGRESS' " +
                "ORDER BY ea.id DESC " +
                "LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public int getNextAttemptNumber(
            long examId,
            long studentId) throws SQLException {

        String sql =
                "SELECT COALESCE(MAX(attempt_number), 0) + 1 " +
                "FROM exam_attempts " +
                "WHERE exam_id = ? AND student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 1;
    }

    public boolean submit(
            long attemptId,
            boolean autoSubmitted) throws SQLException {

        String sql =
                "UPDATE exam_attempts SET " +
                "submitted_at = CURRENT_TIMESTAMP, " +
                "auto_submitted = ?, " +
                "status = 'SUBMITTED' " +
                "WHERE id = ? " +
                "AND status = 'IN_PROGRESS'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, autoSubmitted);
            ps.setLong(2, attemptId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(
            long attemptId,
            String status) throws SQLException {

        String sql =
                "UPDATE exam_attempts " +
                "SET status = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, attemptId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateMarks(
            long attemptId,
            java.math.BigDecimal totalMarks,
            java.math.BigDecimal obtainedMarks)
            throws SQLException {

        String sql =
                "UPDATE exam_attempts SET " +
                "total_marks = ?, " +
                "obtained_marks = ?, " +
                "status = 'EVALUATED' " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, totalMarks);
            ps.setBigDecimal(2, obtainedMarks);
            ps.setLong(3, attemptId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean abandon(long attemptId) throws SQLException {

        String sql =
                "UPDATE exam_attempts " +
                "SET status = 'ABANDONED' " +
                "WHERE id = ? " +
                "AND status = 'IN_PROGRESS'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);

            return ps.executeUpdate() > 0;
        }
    }

    private ExamAttempt mapRow(ResultSet rs)
            throws SQLException {

        ExamAttempt attempt = new ExamAttempt();

        attempt.setId(rs.getLong("id"));
        attempt.setExamId(rs.getLong("exam_id"));
        attempt.setStudentId(rs.getLong("student_id"));

        attempt.setExamTitle(
                rs.getString("exam_title")
        );

        attempt.setStudentName(
                rs.getString("student_name")
        );

        attempt.setAdmissionNumber(
                rs.getString("admission_number")
        );

        attempt.setAttemptNumber(
                rs.getInt("attempt_number")
        );

        Timestamp startedAt = rs.getTimestamp("started_at");

        if (startedAt != null) {
            attempt.setStartedAt(
                    startedAt.toLocalDateTime()
            );
        }

        Timestamp submittedAt = rs.getTimestamp("submitted_at");

        if (submittedAt != null) {
            attempt.setSubmittedAt(
                    submittedAt.toLocalDateTime()
            );
        }

        attempt.setAutoSubmitted(
                rs.getBoolean("auto_submitted")
        );

        attempt.setStatus(
                rs.getString("status")
        );

        attempt.setTotalMarks(
                rs.getBigDecimal("total_marks")
        );

        attempt.setObtainedMarks(
                rs.getBigDecimal("obtained_marks")
        );

        return attempt;
    }
}