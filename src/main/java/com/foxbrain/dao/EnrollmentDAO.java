package com.foxbrain.dao;

import com.foxbrain.model.Enrollment;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    private Enrollment mapResultSet(ResultSet rs) throws SQLException {

        Enrollment enrollment = new Enrollment();

        enrollment.setId(rs.getLong("id"));
        enrollment.setStudentId(rs.getLong("student_id"));
        enrollment.setBatchId(rs.getLong("batch_id"));

        enrollment.setEnrollmentNumber(
                rs.getString("enrollment_number")
        );

        Date enrollmentDate = rs.getDate("enrollment_date");
        if (enrollmentDate != null) {
            enrollment.setEnrollmentDate(
                    enrollmentDate.toLocalDate()
            );
        }

        enrollment.setStatus(rs.getString("status"));

        Date completionDate = rs.getDate("completion_date");
        if (completionDate != null) {
            enrollment.setCompletionDate(
                    completionDate.toLocalDate()
            );
        }

        enrollment.setNotes(rs.getString("notes"));

        enrollment.setStudentName(rs.getString("student_name"));
        enrollment.setAdmissionNumber(rs.getString("admission_number"));
        enrollment.setBatchName(rs.getString("batch_name"));
        enrollment.setCourseName(rs.getString("course_name"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            enrollment.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            enrollment.setUpdatedAt(
                    updatedAt.toLocalDateTime()
            );
        }

        return enrollment;
    }

    private String baseSelect() {

        return """
            SELECT
                e.id,
                e.student_id,
                e.batch_id,
                e.enrollment_number,
                e.enrollment_date,
                e.status,
                e.completion_date,
                e.notes,
                e.created_at,
                e.updated_at,

                CONCAT(
                    u.first_name,
                    CASE
                        WHEN u.last_name IS NULL OR u.last_name = ''
                        THEN ''
                        ELSE CONCAT(' ', u.last_name)
                    END
                ) AS student_name,

                s.admission_number AS admission_number,
                b.name AS batch_name,
                c.name AS course_name

            FROM enrollments e

            INNER JOIN students s
                ON e.student_id = s.id

            INNER JOIN users u
                ON s.user_id = u.id

            INNER JOIN batches b
                ON e.batch_id = b.id

            INNER JOIN courses c
                ON b.course_id = c.id
            """;
    }

    public Enrollment findById(long id) throws SQLException {

        String sql = baseSelect() + " WHERE e.id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<Enrollment> findAll() throws SQLException {

        List<Enrollment> enrollments = new ArrayList<>();

        String sql = baseSelect()
                + " ORDER BY e.enrollment_date DESC, e.id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                enrollments.add(mapResultSet(rs));
            }
        }

        return enrollments;
    }

    public List<Enrollment> findByStatus(String status)
            throws SQLException {

        List<Enrollment> enrollments = new ArrayList<>();

        String sql = baseSelect()
                + " WHERE e.status = ?"
                + " ORDER BY e.enrollment_date DESC, e.id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    enrollments.add(mapResultSet(rs));
                }
            }
        }

        return enrollments;
    }

    public boolean existsByEnrollmentNumber(
            String enrollmentNumber) throws SQLException {

        String sql = """
            SELECT id
            FROM enrollments
            WHERE enrollment_number = ?
            LIMIT 1
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, enrollmentNumber);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsByEnrollmentNumberExceptId(
            String enrollmentNumber,
            long id) throws SQLException {

        String sql = """
            SELECT id
            FROM enrollments
            WHERE enrollment_number = ?
              AND id <> ?
            LIMIT 1
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, enrollmentNumber);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsByStudentAndBatch(
            long studentId,
            long batchId) throws SQLException {

        String sql = """
            SELECT id
            FROM enrollments
            WHERE student_id = ?
              AND batch_id = ?
            LIMIT 1
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, studentId);
            ps.setLong(2, batchId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsByStudentAndBatchExceptId(
            long studentId,
            long batchId,
            long id) throws SQLException {

        String sql = """
            SELECT id
            FROM enrollments
            WHERE student_id = ?
              AND batch_id = ?
              AND id <> ?
            LIMIT 1
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, studentId);
            ps.setLong(2, batchId);
            ps.setLong(3, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countActiveByBatch(long batchId)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM enrollments
            WHERE batch_id = ?
              AND status = 'ACTIVE'
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, batchId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public int countActiveByBatchExceptId(
            long batchId,
            long enrollmentId) throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM enrollments
            WHERE batch_id = ?
              AND status = 'ACTIVE'
              AND id <> ?
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, batchId);
            ps.setLong(2, enrollmentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public long insert(Enrollment enrollment)
            throws SQLException {

        String sql = """
            INSERT INTO enrollments (
                student_id,
                batch_id,
                enrollment_number,
                enrollment_date,
                status,
                completion_date,
                notes
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setLong(1, enrollment.getStudentId());
            ps.setLong(2, enrollment.getBatchId());
            ps.setString(3, enrollment.getEnrollmentNumber());

            if (enrollment.getEnrollmentDate() != null) {
                ps.setDate(
                        4,
                        Date.valueOf(
                                enrollment.getEnrollmentDate()
                        )
                );
            } else {
                ps.setDate(4, Date.valueOf(LocalDate.now()));
            }

            ps.setString(5, enrollment.getStatus());

            if (enrollment.getCompletionDate() != null) {
                ps.setDate(
                        6,
                        Date.valueOf(
                                enrollment.getCompletionDate()
                        )
                );
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, enrollment.getNotes());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(Enrollment enrollment)
            throws SQLException {

        String sql = """
            UPDATE enrollments
            SET
                student_id = ?,
                batch_id = ?,
                enrollment_number = ?,
                enrollment_date = ?,
                status = ?,
                completion_date = ?,
                notes = ?
            WHERE id = ?
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, enrollment.getStudentId());
            ps.setLong(2, enrollment.getBatchId());
            ps.setString(3, enrollment.getEnrollmentNumber());

            if (enrollment.getEnrollmentDate() != null) {
                ps.setDate(
                        4,
                        Date.valueOf(
                                enrollment.getEnrollmentDate()
                        )
                );
            } else {
                ps.setDate(4, Date.valueOf(LocalDate.now()));
            }

            ps.setString(5, enrollment.getStatus());

            if (enrollment.getCompletionDate() != null) {
                ps.setDate(
                        6,
                        Date.valueOf(
                                enrollment.getCompletionDate()
                        )
                );
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, enrollment.getNotes());
            ps.setLong(8, enrollment.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql = """
            DELETE FROM enrollments
            WHERE id = ?
            """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}