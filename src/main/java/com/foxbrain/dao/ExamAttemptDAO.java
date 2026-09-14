package com.foxbrain.dao;

import com.foxbrain.model.ExamAttempt;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamAttemptDAO {

    public long create(ExamAttempt attempt)
            throws SQLException {

        String sql =
                "INSERT INTO exam_attempts " +
                "(exam_id, student_id, attempt_number, started_at, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, attempt.getExamId());
            ps.setLong(2, attempt.getStudentId());
            ps.setInt(3, attempt.getAttemptNumber());

            ps.setTimestamp(4, attempt.getStartedAt());

            ps.setString(5,
                    attempt.getStatus() == null
                            ? "IN_PROGRESS"
                            : attempt.getStatus());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public ExamAttempt getById(long id)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id=e.id " +
                "INNER JOIN students s ON ea.student_id=s.id " +
                "WHERE ea.id=?";

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

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id=e.id " +
                "INNER JOIN students s ON ea.student_id=s.id " +
                "WHERE ea.exam_id=? " +
                "ORDER BY ea.id DESC";

        List<ExamAttempt> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    public List<ExamAttempt> getByStudentId(long studentId)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id=e.id " +
                "INNER JOIN students s ON ea.student_id=s.id " +
                "WHERE ea.student_id=? " +
                "ORDER BY ea.id DESC";

        List<ExamAttempt> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    public ExamAttempt getActiveAttempt(
            long examId,
            long studentId)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, s.admission_number " +
                "FROM exam_attempts ea " +
                "INNER JOIN exams e ON ea.exam_id=e.id " +
                "INNER JOIN students s ON ea.student_id=s.id " +
                "WHERE ea.exam_id=? " +
                "AND ea.student_id=? " +
                "AND ea.status='IN_PROGRESS' " +
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
            long studentId)
            throws SQLException {

        String sql =
                "SELECT COALESCE(MAX(attempt_number),0)+1 " +
                "FROM exam_attempts " +
                "WHERE exam_id=? AND student_id=?";

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
            long id,
            boolean autoSubmitted)
            throws SQLException {

        String sql =
                "UPDATE exam_attempts SET " +
                "submitted_at=NOW(), " +
                "auto_submitted=?, " +
                "status='SUBMITTED' " +
                "WHERE id=? " +
                "AND status='IN_PROGRESS'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, autoSubmitted);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(
            long id,
            String status)
            throws SQLException {

        String sql =
                "UPDATE exam_attempts " +
                "SET status=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateMarks(
            long id,
            double totalMarks,
            double obtainedMarks)
            throws SQLException {

        String sql =
                "UPDATE exam_attempts SET " +
                "total_marks=?, obtained_marks=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, totalMarks);
            ps.setDouble(2, obtainedMarks);
            ps.setLong(3, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean abandon(long id)
            throws SQLException {

        return updateStatus(
                id,
                "ABANDONED");
    }

    private ExamAttempt mapRow(ResultSet rs)
            throws SQLException {

        ExamAttempt a = new ExamAttempt();

        a.setId(rs.getLong("id"));
        a.setExamId(rs.getLong("exam_id"));
        a.setStudentId(rs.getLong("student_id"));

        a.setAttemptNumber(
                rs.getInt("attempt_number"));

        a.setStartedAt(
                rs.getTimestamp("started_at"));

        a.setSubmittedAt(
                rs.getTimestamp("submitted_at"));

        a.setAutoSubmitted(
                rs.getBoolean("auto_submitted"));

        a.setStatus(
                rs.getString("status"));

        double total = rs.getDouble("total_marks");
        if (rs.wasNull())
            a.setTotalMarks(null);
        else
            a.setTotalMarks(total);

        double obtained =
                rs.getDouble("obtained_marks");

        if (rs.wasNull())
            a.setObtainedMarks(null);
        else
            a.setObtainedMarks(obtained);

        a.setExamTitle(
                rs.getString("exam_title"));

        String first =
                rs.getString("first_name");

        String last =
                rs.getString("last_name");

        String name =
                ((first == null ? "" : first) + " " +
                 (last == null ? "" : last)).trim();

        a.setStudentName(name);

        a.setAdmissionNumber(
                rs.getString("admission_number"));

        a.setCreatedAt(
                rs.getTimestamp("created_at"));

        a.setUpdatedAt(
                rs.getTimestamp("updated_at"));

        return a;
    }
}