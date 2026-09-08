package com.foxbrain.dao;

import com.foxbrain.model.Dashboard;
import com.foxbrain.model.Student;
import com.foxbrain.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public Dashboard getDashboardData() {

        Dashboard dashboard = new Dashboard();

        dashboard.setTotalStudents(
            countStudents()
        );

        dashboard.setTotalTeachers(
            countTeachers()
        );

        dashboard.setTotalCourses(
            countTable("courses")
        );

        dashboard.setTotalBatches(
            countTable("batches")
        );

        dashboard.setPendingAdmissions(
            countTableWhere(
                "admissions",
                "status",
                "PENDING"
            )
        );

        dashboard.setTodayAttendance(
            countTableWhereDate(
                "attendance",
                "attendance_date"
            )
        );

        dashboard.setPendingFees(
            countTableWhere(
                "fees",
                "status",
                "PENDING"
            )
        );

        dashboard.setUpcomingExams(
            countUpcomingExams()
        );

        dashboard.setRecentStudents(
            getRecentStudents()
        );

        return dashboard;
    }


    // =========================================================
    // STUDENTS
    // =========================================================

    private long countStudents() {

        String sql =
            "SELECT COUNT(*) FROM students";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // TEACHERS
    // =========================================================

    private long countTeachers() {

        String sql =
            "SELECT COUNT(*) " +
            "FROM users " +
            "WHERE role_id = 2";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // GENERIC TABLE COUNT
    // =========================================================

    private long countTable(String tableName) {

        if (!isAllowedTable(tableName)) {
            return 0;
        }

        String sql =
            "SELECT COUNT(*) FROM " + tableName;

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {

            /*
             * The table may not exist yet.
             * This is intentionally handled so the
             * dashboard can still load.
             */

            System.out.println(
                "Dashboard table unavailable: "
                + tableName
            );
        }

        return 0;
    }


    // =========================================================
    // TABLE COUNT WITH STATUS
    // =========================================================

    private long countTableWhere(
            String tableName,
            String columnName,
            String value) {

        if (!isAllowedTable(tableName)) {
            return 0;
        }

        String sql =
            "SELECT COUNT(*) " +
            "FROM " + tableName +
            " WHERE " + columnName + " = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                "Dashboard table unavailable: "
                + tableName
            );
        }

        return 0;
    }


    // =========================================================
    // TODAY ATTENDANCE
    // =========================================================

    private long countTableWhereDate(
            String tableName,
            String dateColumn) {

        if (!isAllowedTable(tableName)) {
            return 0;
        }

        String sql =
            "SELECT COUNT(*) " +
            "FROM " + tableName +
            " WHERE " + dateColumn + " = CURDATE()";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {

            System.out.println(
                "Dashboard attendance table unavailable."
            );
        }

        return 0;
    }


    // =========================================================
    // UPCOMING EXAMS
    // =========================================================

    private long countUpcomingExams() {

        String sql =
            "SELECT COUNT(*) " +
            "FROM exams " +
            "WHERE exam_date >= CURDATE()";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {

            System.out.println(
                "Dashboard exams table unavailable."
            );
        }

        return 0;
    }


    // =========================================================
    // RECENT STUDENTS
    // =========================================================

    private List<Student> getRecentStudents() {

        List<Student> students =
            new ArrayList<>();

        String sql =
            "SELECT * " +
            "FROM students " +
            "ORDER BY id DESC " +
            "LIMIT 5";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student =
                    new Student();

                student.setId(
                    rs.getLong("id")
                );

                student.setUserId(
                    rs.getLong("user_id")
                );

                student.setAdmissionNumber(
                    rs.getString("admission_number")
                );

                if (rs.getDate("date_of_birth") != null) {

                    student.setDateOfBirth(
                        rs.getDate("date_of_birth")
                            .toLocalDate()
                    );
                }

                student.setGender(
                    rs.getString("gender")
                );

                student.setAddressLine1(
                    rs.getString("address_line1")
                );

                student.setAddressLine2(
                    rs.getString("address_line2")
                );

                student.setCity(
                    rs.getString("city")
                );

                student.setState(
                    rs.getString("state")
                );

                student.setPostalCode(
                    rs.getString("postal_code")
                );

                student.setCountry(
                    rs.getString("country")
                );

                if (rs.getDate("admission_date") != null) {

                    student.setAdmissionDate(
                        rs.getDate("admission_date")
                            .toLocalDate()
                    );
                }

                student.setStatus(
                    rs.getString("status")
                );

                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }


    // =========================================================
    // TABLE WHITELIST
    // =========================================================

    private boolean isAllowedTable(
            String tableName) {

        return
            "courses".equals(tableName)
            || "batches".equals(tableName)
            || "admissions".equals(tableName)
            || "attendance".equals(tableName)
            || "fees".equals(tableName)
            || "exams".equals(tableName);
    }
}