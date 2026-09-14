package com.foxbrain.dao;

import com.foxbrain.model.Attendance;
import com.foxbrain.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    private Attendance mapResultSet(ResultSet rs) throws SQLException {

        Attendance attendance = new Attendance();

        attendance.setId(rs.getLong("id"));
        attendance.setEnrollmentId(rs.getLong("enrollment_id"));

        Date attendanceDate = rs.getDate("attendance_date");

        if (attendanceDate != null) {
            attendance.setAttendanceDate(attendanceDate.toLocalDate());
        }

        attendance.setStatus(rs.getString("status"));

        Time checkIn = rs.getTime("check_in_time");

        if (checkIn != null) {
            attendance.setCheckInTime(checkIn.toLocalTime());
        }

        Time checkOut = rs.getTime("check_out_time");

        if (checkOut != null) {
            attendance.setCheckOutTime(checkOut.toLocalTime());
        }

        attendance.setRemarks(rs.getString("remarks"));

        long teacherId = rs.getLong("marked_by_teacher_id");

        if (!rs.wasNull()) {
            attendance.setMarkedByTeacherId(teacherId);
        }

        attendance.setStudentName(rs.getString("student_name"));
        attendance.setAdmissionNumber(rs.getString("admission_number"));
        attendance.setBatchName(rs.getString("batch_name"));
        attendance.setCourseName(rs.getString("course_name"));
        attendance.setTeacherName(rs.getString("teacher_name"));

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            attendance.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");

        if (updatedAt != null) {
            attendance.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return attendance;
    }

    private String baseSelect() {

        return """
            SELECT
                a.id,
                a.enrollment_id,
                a.attendance_date,
                a.status,
                a.check_in_time,
                a.check_out_time,
                a.remarks,
                a.marked_by_teacher_id,
                a.created_at,
                a.updated_at,

                CONCAT(
                    u.first_name,
                    CASE
                        WHEN u.last_name IS NOT NULL
                             AND u.last_name <> ''
                        THEN CONCAT(' ', u.last_name)
                        ELSE ''
                    END
                ) AS student_name,

                s.admission_number,

                b.name AS batch_name,

                c.name AS course_name,

                CONCAT(
                    tu.first_name,
                    CASE
                        WHEN tu.last_name IS NOT NULL
                             AND tu.last_name <> ''
                        THEN CONCAT(' ', tu.last_name)
                        ELSE ''
                    END
                ) AS teacher_name

            FROM attendance a

            INNER JOIN enrollments e
                ON a.enrollment_id = e.id

            INNER JOIN students s
                ON e.student_id = s.id

            INNER JOIN users u
                ON s.user_id = u.id

            INNER JOIN batches b
                ON e.batch_id = b.id

            INNER JOIN courses c
                ON b.course_id = c.id

            LEFT JOIN teachers t
                ON a.marked_by_teacher_id = t.id

            LEFT JOIN users tu
                ON t.user_id = tu.id
            """;
    }

    public Attendance findById(long id) throws SQLException {

        String sql = baseSelect() + """
            WHERE a.id = ?
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<Attendance> findAll() throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = baseSelect() + """
            ORDER BY a.attendance_date DESC, a.id DESC
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {

            while (rs.next()) {
                attendanceList.add(mapResultSet(rs));
            }
        }

        return attendanceList;
    }

    public List<Attendance> findByDate(LocalDate date)
            throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = baseSelect() + """
            WHERE a.attendance_date = ?
            ORDER BY student_name ASC
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setDate(1, Date.valueOf(date));

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    attendanceList.add(mapResultSet(rs));
                }
            }
        }

        return attendanceList;
    }

    public List<Attendance> findByStatus(String status)
            throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = baseSelect() + """
            WHERE a.status = ?
            ORDER BY a.attendance_date DESC, a.id DESC
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    attendanceList.add(mapResultSet(rs));
                }
            }
        }

        return attendanceList;
    }

    public boolean existsByEnrollmentAndDate(
            long enrollmentId,
            LocalDate attendanceDate)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM attendance
            WHERE enrollment_id = ?
              AND attendance_date = ?
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setLong(1, enrollmentId);
            statement.setDate(2, Date.valueOf(attendanceDate));

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsByEnrollmentAndDateExceptId(
            long enrollmentId,
            LocalDate attendanceDate,
            long id)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM attendance
            WHERE enrollment_id = ?
              AND attendance_date = ?
              AND id <> ?
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setLong(1, enrollmentId);
            statement.setDate(2, Date.valueOf(attendanceDate));
            statement.setLong(3, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int insert(Attendance attendance)
            throws SQLException {

        String sql = """
            INSERT INTO attendance (
                enrollment_id,
                attendance_date,
                status,
                check_in_time,
                check_out_time,
                remarks,
                marked_by_teacher_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    )
        ) {

            statement.setLong(
                    1,
                    attendance.getEnrollmentId()
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            statement.setString(
                    3,
                    attendance.getStatus()
            );

            if (attendance.getCheckInTime() != null) {

                statement.setTime(
                        4,
                        Time.valueOf(
                                attendance.getCheckInTime()
                        )
                );

            } else {

                statement.setNull(4, Types.TIME);
            }

            if (attendance.getCheckOutTime() != null) {

                statement.setTime(
                        5,
                        Time.valueOf(
                                attendance.getCheckOutTime()
                        )
                );

            } else {

                statement.setNull(5, Types.TIME);
            }

            if (attendance.getRemarks() != null
                    && !attendance.getRemarks().isBlank()) {

                statement.setString(
                        6,
                        attendance.getRemarks()
                );

            } else {

                statement.setNull(
                        6,
                        Types.VARCHAR
                );
            }

            if (attendance.getMarkedByTeacherId() != null) {

                statement.setLong(
                        7,
                        attendance.getMarkedByTeacherId()
                );

            } else {

                statement.setNull(
                        7,
                        Types.BIGINT
                );
            }

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return 0;
    }

    public boolean update(Attendance attendance)
            throws SQLException {

        String sql = """
            UPDATE attendance
            SET
                enrollment_id = ?,
                attendance_date = ?,
                status = ?,
                check_in_time = ?,
                check_out_time = ?,
                remarks = ?,
                marked_by_teacher_id = ?
            WHERE id = ?
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    attendance.getEnrollmentId()
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            statement.setString(
                    3,
                    attendance.getStatus()
            );

            if (attendance.getCheckInTime() != null) {

                statement.setTime(
                        4,
                        Time.valueOf(
                                attendance.getCheckInTime()
                        )
                );

            } else {

                statement.setNull(4, Types.TIME);
            }

            if (attendance.getCheckOutTime() != null) {

                statement.setTime(
                        5,
                        Time.valueOf(
                                attendance.getCheckOutTime()
                        )
                );

            } else {

                statement.setNull(5, Types.TIME);
            }

            if (attendance.getRemarks() != null
                    && !attendance.getRemarks().isBlank()) {

                statement.setString(
                        6,
                        attendance.getRemarks()
                );

            } else {

                statement.setNull(
                        6,
                        Types.VARCHAR
                );
            }

            if (attendance.getMarkedByTeacherId() != null) {

                statement.setLong(
                        7,
                        attendance.getMarkedByTeacherId()
                );

            } else {

                statement.setNull(
                        7,
                        Types.BIGINT
                );
            }

            statement.setLong(
                    8,
                    attendance.getId()
            );

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(long id)
            throws SQLException {

        String sql = """
            DELETE FROM attendance
            WHERE id = ?
            """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}