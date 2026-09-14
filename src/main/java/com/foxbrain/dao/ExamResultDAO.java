package com.foxbrain.dao;

import com.foxbrain.model.ExamResult;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamResultDAO {

    public List<ExamResult> getAll()
            throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id=e.id " +
                "INNER JOIN students s ON er.student_id=s.id " +
                "ORDER BY er.id DESC";

        List<ExamResult> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }

        return list;
    }

    public ExamResult getById(long id)
            throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id=e.id " +
                "INNER JOIN students s ON er.student_id=s.id " +
                "WHERE er.id=?";

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
            long studentId)
            throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id=e.id " +
                "INNER JOIN students s ON er.student_id=s.id " +
                "WHERE er.exam_id=? " +
                "AND er.student_id=?";

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

    public long create(ExamResult result)
            throws SQLException {

        String sql =
                "INSERT INTO exam_results " +
                "(exam_id, student_id, marks_obtained, grade, " +
                "result_status, remarks) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, result.getExamId());
            ps.setLong(2, result.getStudentId());

            ps.setDouble(3,
                    result.getMarksObtained());

            ps.setString(4,
                    result.getGrade());

            ps.setString(5,
                    result.getResultStatus());

            ps.setString(6,
                    result.getRemarks());

            ps.executeUpdate();

            try (ResultSet keys =
                         ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(ExamResult result)
            throws SQLException {

        String sql =
                "UPDATE exam_results SET " +
                "marks_obtained=?, grade=?, result_status=?, " +
                "remarks=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1,
                    result.getMarksObtained());

            ps.setString(2,
                    result.getGrade());

            ps.setString(3,
                    result.getResultStatus());

            ps.setString(4,
                    result.getRemarks());

            ps.setLong(5,
                    result.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean publish(long id)
            throws SQLException {

        String sql =
                "UPDATE exam_results " +
                "SET published_at=NOW() " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean unpublish(long id)
            throws SQLException {

        String sql =
                "UPDATE exam_results " +
                "SET published_at=NULL " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public List<ExamResult> getByExamId(long examId)
            throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id=e.id " +
                "INNER JOIN students s ON er.student_id=s.id " +
                "WHERE er.exam_id=? " +
                "ORDER BY er.marks_obtained DESC";

        List<ExamResult> list = new ArrayList<>();

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

    public List<ExamResult> getByStudentId(
            long studentId)
            throws SQLException {

        String sql =
                "SELECT er.*, " +
                "e.title AS exam_title, " +
                "s.first_name, s.last_name, " +
                "s.admission_number " +
                "FROM exam_results er " +
                "INNER JOIN exams e ON er.exam_id=e.id " +
                "INNER JOIN students s ON er.student_id=s.id " +
                "WHERE er.student_id=? " +
                "ORDER BY er.id DESC";

        List<ExamResult> list = new ArrayList<>();

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

    public boolean exists(
            long examId,
            long studentId)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM exam_results " +
                "WHERE exam_id=? AND student_id=?";

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

    public double getTotalObtainedMarks(
            long examId,
            long studentId)
            throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(ea.marks_obtained),0) " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_attempts at " +
                "ON ea.attempt_id=at.id " +
                "WHERE at.exam_id=? " +
                "AND at.student_id=? " +
                "AND ea.evaluated=TRUE";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }

        return 0;
    }

    private ExamResult mapRow(ResultSet rs)
            throws SQLException {

        ExamResult r = new ExamResult();

        r.setId(rs.getLong("id"));

        r.setExamId(
                rs.getLong("exam_id"));

        r.setStudentId(
                rs.getLong("student_id"));

        r.setMarksObtained(
                rs.getDouble("marks_obtained"));

        r.setGrade(
                rs.getString("grade"));

        r.setResultStatus(
                rs.getString("result_status"));

        r.setRemarks(
                rs.getString("remarks"));

        r.setPublishedAt(
                rs.getTimestamp("published_at"));

        r.setCreatedAt(
                rs.getTimestamp("created_at"));

        r.setUpdatedAt(
                rs.getTimestamp("updated_at"));

        r.setExamTitle(
                rs.getString("exam_title"));

        String first =
                rs.getString("first_name");

        String last =
                rs.getString("last_name");

        String name =
                ((first == null ? "" : first) + " " +
                 (last == null ? "" : last)).trim();

        r.setStudentName(name);

        r.setAdmissionNumber(
                rs.getString("admission_number"));

        return r;
    }
}