package com.foxbrain.dao;

import com.foxbrain.model.ExamResult;
import com.foxbrain.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamResultDAO {

    public List<ExamResult> getAll() throws SQLException {

        List<ExamResult> results = new ArrayList<>();

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id = e.id " +
                "INNER JOIN students s ON er.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "ORDER BY er.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
        }

        return results;
    }

    public ExamResult getById(long id) throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id = e.id " +
                "INNER JOIN students s ON er.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE er.id = ?";

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

    public ExamResult getByExamAndStudent(
            long examId,
            long studentId) throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id = e.id " +
                "INNER JOIN students s ON er.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE er.exam_id = ? " +
                "AND er.student_id = ?";

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

    public long create(ExamResult result) throws SQLException {

        String sql =
                "INSERT INTO exam_results (" +
                "exam_id, student_id, marks_obtained, grade, " +
                "result_status, remarks, published_at" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, result.getExamId());
            ps.setLong(2, result.getStudentId());

            ps.setBigDecimal(
                    3,
                    result.getMarksObtained()
            );

            ps.setString(4, result.getGrade());
            ps.setString(5, result.getResultStatus());
            ps.setString(6, result.getRemarks());

            if (result.getPublishedAt() != null) {
                ps.setTimestamp(
                        7,
                        Timestamp.valueOf(
                                result.getPublishedAt()
                        )
                );
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }

            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Creating exam result failed.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException(
                "Creating exam result failed. No ID returned."
        );
    }

    public boolean update(ExamResult result)
            throws SQLException {

        String sql =
                "UPDATE exam_results SET " +
                "marks_obtained = ?, " +
                "grade = ?, " +
                "result_status = ?, " +
                "remarks = ?, " +
                "published_at = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(
                    1,
                    result.getMarksObtained()
            );

            ps.setString(2, result.getGrade());
            ps.setString(3, result.getResultStatus());
            ps.setString(4, result.getRemarks());

            if (result.getPublishedAt() != null) {
                ps.setTimestamp(
                        5,
                        Timestamp.valueOf(
                                result.getPublishedAt()
                        )
                );
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.setLong(6, result.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean publish(long resultId)
            throws SQLException {

        String sql =
                "UPDATE exam_results SET " +
                "published_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, resultId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean unpublish(long resultId)
            throws SQLException {

        String sql =
                "UPDATE exam_results SET " +
                "published_at = NULL " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, resultId);

            return ps.executeUpdate() > 0;
        }
    }

    public List<ExamResult> getByExamId(
            long examId) throws SQLException {

        List<ExamResult> results = new ArrayList<>();

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id = e.id " +
                "INNER JOIN students s ON er.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE er.exam_id = ? " +
                "ORDER BY s.admission_number ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }

        return results;
    }

    public List<ExamResult> getByStudentId(
            long studentId) throws SQLException {

        List<ExamResult> results = new ArrayList<>();

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "CONCAT(COALESCE(u.first_name,''), ' ', " +
                "COALESCE(u.last_name,'')) AS student_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id = e.id " +
                "INNER JOIN students s ON er.student_id = s.id " +
                "LEFT JOIN users u ON s.user_id = u.id " +
                "WHERE er.student_id = ? " +
                "ORDER BY er.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }

        return results;
    }

    public boolean exists(
            long examId,
            long studentId) throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM exam_results " +
                "WHERE exam_id = ? " +
                "AND student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public BigDecimal getTotalObtainedMarks(
            long examId,
            long studentId) throws SQLException {

        String sql =
                "SELECT marks_obtained " +
                "FROM exam_results " +
                "WHERE exam_id = ? " +
                "AND student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getBigDecimal("marks_obtained");
                }
            }
        }

        return null;
    }

    private ExamResult mapRow(ResultSet rs)
            throws SQLException {

        ExamResult result = new ExamResult();

        result.setId(rs.getLong("id"));
        result.setExamId(rs.getLong("exam_id"));
        result.setStudentId(rs.getLong("student_id"));

        result.setExamTitle(
                rs.getString("exam_title")
        );

        result.setStudentName(
                rs.getString("student_name")
        );

        result.setAdmissionNumber(
                rs.getString("admission_number")
        );

        result.setMarksObtained(
                rs.getBigDecimal("marks_obtained")
        );

        result.setGrade(
                rs.getString("grade")
        );

        result.setResultStatus(
                rs.getString("result_status")
        );

        result.setRemarks(
                rs.getString("remarks")
        );

        Timestamp publishedAt =
                rs.getTimestamp("published_at");

        if (publishedAt != null) {
            result.setPublishedAt(
                    publishedAt.toLocalDateTime()
            );
        }

        Timestamp createdAt =
                rs.getTimestamp("created_at");

        if (createdAt != null) {
            result.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        Timestamp updatedAt =
                rs.getTimestamp("updated_at");

        if (updatedAt != null) {
            result.setUpdatedAt(
                    updatedAt.toLocalDateTime()
            );
        }

        return result;
    }
}