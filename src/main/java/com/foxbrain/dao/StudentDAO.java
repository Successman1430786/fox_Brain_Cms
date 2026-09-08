package com.foxbrain.dao;

import com.foxbrain.model.Student;
import com.foxbrain.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private Student mapResultSet(ResultSet rs) throws SQLException {

        Student student = new Student();

        student.setId(rs.getLong("id"));
        student.setUserId(rs.getLong("user_id"));
        student.setAdmissionNumber(rs.getString("admission_number"));

        if (rs.getDate("date_of_birth") != null) {
            student.setDateOfBirth(
                rs.getDate("date_of_birth").toLocalDate()
            );
        }

        student.setGender(rs.getString("gender"));
        student.setAddressLine1(rs.getString("address_line1"));
        student.setAddressLine2(rs.getString("address_line2"));
        student.setCity(rs.getString("city"));
        student.setState(rs.getString("state"));
        student.setPostalCode(rs.getString("postal_code"));
        student.setCountry(rs.getString("country"));

        if (rs.getDate("admission_date") != null) {
            student.setAdmissionDate(
                rs.getDate("admission_date").toLocalDate()
            );
        }

        student.setStatus(rs.getString("status"));

        if (rs.getTimestamp("created_at") != null) {
            student.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        if (rs.getTimestamp("updated_at") != null) {
            student.setUpdatedAt(
                rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        return student;
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    public Student findById(long id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding student by ID: " + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // FIND ALL
    // =========================================================

    public List<Student> findAll() {

        String sql = "SELECT * FROM students ORDER BY id DESC";

        List<Student> students = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapResultSet(rs));
            }

            return students;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding all students: " + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // FIND BY ADMISSION NUMBER
    // =========================================================

    public Student findByAdmissionNumber(String admissionNumber) {

        String sql =
            "SELECT * FROM students WHERE admission_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, admissionNumber);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error finding student by admission number: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // INSERT STUDENT
    // =========================================================

    public long insert(Student student) {

        String sql =
            "INSERT INTO students (" +
            "user_id, " +
            "admission_number, " +
            "date_of_birth, " +
            "gender, " +
            "address_line1, " +
            "address_line2, " +
            "city, " +
            "state, " +
            "postal_code, " +
            "country, " +
            "admission_date, " +
            "status" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 sql,
                 Statement.RETURN_GENERATED_KEYS
             )) {

            int i = 1;

            // user_id
            ps.setLong(i++, student.getUserId());

            // admission_number
            ps.setString(i++, student.getAdmissionNumber());

            // date_of_birth
            if (student.getDateOfBirth() != null) {
                ps.setDate(
                    i++,
                    java.sql.Date.valueOf(
                        student.getDateOfBirth()
                    )
                );
            } else {
                ps.setNull(
                    i++,
                    java.sql.Types.DATE
                );
            }

            // gender
            ps.setString(i++, student.getGender());

            // address_line1
            ps.setString(i++, student.getAddressLine1());

            // address_line2
            ps.setString(i++, student.getAddressLine2());

            // city
            ps.setString(i++, student.getCity());

            // state
            ps.setString(i++, student.getState());

            // postal_code
            ps.setString(i++, student.getPostalCode());

            // country
            ps.setString(i++, student.getCountry());

            // admission_date
            if (student.getAdmissionDate() != null) {
                ps.setDate(
                    i++,
                    java.sql.Date.valueOf(
                        student.getAdmissionDate()
                    )
                );
            } else {
                ps.setNull(
                    i++,
                    java.sql.Types.DATE
                );
            }

            // status
            ps.setString(i++, student.getStatus());

            // Execute INSERT
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException(
                    "Student could not be inserted."
                );
            }

            // Get generated student ID
            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }

                throw new RuntimeException(
                    "Student was inserted, but generated ID was not returned."
                );
            }

        } catch (SQLException e) {

            // VERY IMPORTANT:
            // Print the real MySQL error in Eclipse Console.
            e.printStackTrace();

            throw new RuntimeException(
                "Error inserting student: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    public boolean update(Student student) {

        String sql =
            "UPDATE students SET " +
            "user_id = ?, " +
            "admission_number = ?, " +
            "date_of_birth = ?, " +
            "gender = ?, " +
            "address_line1 = ?, " +
            "address_line2 = ?, " +
            "city = ?, " +
            "state = ?, " +
            "postal_code = ?, " +
            "country = ?, " +
            "admission_date = ?, " +
            "status = ? " +
            "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;

            ps.setLong(i++, student.getUserId());

            ps.setString(i++, student.getAdmissionNumber());

            if (student.getDateOfBirth() != null) {
                ps.setDate(
                    i++,
                    java.sql.Date.valueOf(
                        student.getDateOfBirth()
                    )
                );
            } else {
                ps.setNull(
                    i++,
                    java.sql.Types.DATE
                );
            }

            ps.setString(i++, student.getGender());
            ps.setString(i++, student.getAddressLine1());
            ps.setString(i++, student.getAddressLine2());
            ps.setString(i++, student.getCity());
            ps.setString(i++, student.getState());
            ps.setString(i++, student.getPostalCode());
            ps.setString(i++, student.getCountry());

            if (student.getAdmissionDate() != null) {
                ps.setDate(
                    i++,
                    java.sql.Date.valueOf(
                        student.getAdmissionDate()
                    )
                );
            } else {
                ps.setNull(
                    i++,
                    java.sql.Types.DATE
                );
            }

            ps.setString(i++, student.getStatus());

            ps.setLong(i++, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error updating student: "
                + e.getMessage(),
                e
            );
        }
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    public boolean delete(long id) {

        String sql =
            "DELETE FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error deleting student: "
                + e.getMessage(),
                e
            );
        }
    }
}