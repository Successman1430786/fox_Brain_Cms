package com.foxbrain.dao;

import com.foxbrain.model.Admission;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdmissionDAO {

    private Admission mapResultSet(ResultSet rs) throws SQLException {

        Admission admission = new Admission();

        admission.setId(rs.getLong("id"));
        admission.setApplicationNumber(rs.getString("application_number"));

        long studentId = rs.getLong("student_id");
        if (!rs.wasNull()) {
            admission.setStudentId(studentId);
        }

        admission.setCourseId(rs.getLong("course_id"));

        long batchId = rs.getLong("batch_id");
        if (!rs.wasNull()) {
            admission.setBatchId(batchId);
        }

        admission.setCourseName(rs.getString("course_name"));
        admission.setBatchName(rs.getString("batch_name"));

        Date applicationDate = rs.getDate("application_date");
        if (applicationDate != null) {
            admission.setApplicationDate(applicationDate.toLocalDate());
        }

        admission.setFirstName(rs.getString("first_name"));
        admission.setLastName(rs.getString("last_name"));
        admission.setEmail(rs.getString("email"));
        admission.setPhone(rs.getString("phone"));

        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            admission.setDateOfBirth(dob.toLocalDate());
        }

        admission.setGender(rs.getString("gender"));

        admission.setAddressLine1(rs.getString("address_line1"));
        admission.setAddressLine2(rs.getString("address_line2"));
        admission.setCity(rs.getString("city"));
        admission.setState(rs.getString("state"));
        admission.setPostalCode(rs.getString("postal_code"));
        admission.setCountry(rs.getString("country"));

        admission.setQualification(rs.getString("qualification"));
        admission.setSource(rs.getString("source"));
        admission.setStatus(rs.getString("status"));
        admission.setNotes(rs.getString("notes"));

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            admission.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp updated = rs.getTimestamp("updated_at");
        if (updated != null) {
            admission.setUpdatedAt(updated.toLocalDateTime());
        }

        return admission;
    }

    public Admission findById(long id) throws SQLException {

        String sql =
                "SELECT a.*, " +
                "c.name AS course_name, " +
                "b.name AS batch_name " +
                "FROM admissions a " +
                "LEFT JOIN courses c ON a.course_id = c.id " +
                "LEFT JOIN batches b ON a.batch_id = b.id " +
                "WHERE a.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<Admission> findAll() throws SQLException {

        List<Admission> admissions = new ArrayList<>();

        String sql =
                "SELECT a.*, " +
                "c.name AS course_name, " +
                "b.name AS batch_name " +
                "FROM admissions a " +
                "LEFT JOIN courses c ON a.course_id = c.id " +
                "LEFT JOIN batches b ON a.batch_id = b.id " +
                "ORDER BY a.application_date DESC, a.id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                admissions.add(mapResultSet(rs));
            }
        }

        return admissions;
    }

    public List<Admission> findByStatus(String status) throws SQLException {

        List<Admission> admissions = new ArrayList<>();

        String sql =
                "SELECT a.*, " +
                "c.name AS course_name, " +
                "b.name AS batch_name " +
                "FROM admissions a " +
                "LEFT JOIN courses c ON a.course_id = c.id " +
                "LEFT JOIN batches b ON a.batch_id = b.id " +
                "WHERE a.status = ? " +
                "ORDER BY a.application_date DESC, a.id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    admissions.add(mapResultSet(rs));
                }
            }
        }

        return admissions;
    }

    public boolean existsByApplicationNumber(String applicationNumber)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM admissions " +
                "WHERE application_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, applicationNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsByApplicationNumberExceptId(
            String applicationNumber,
            long id) throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM admissions " +
                "WHERE application_number = ? AND id <> ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, applicationNumber);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public long insert(Admission admission) throws SQLException {

        String sql =
                "INSERT INTO admissions (" +
                "application_number, student_id, course_id, batch_id, " +
                "application_date, first_name, last_name, email, phone, " +
                "date_of_birth, gender, address_line1, address_line2, " +
                "city, state, postal_code, country, qualification, source, " +
                "status, notes" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            setStatementValues(ps, admission);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public void update(Admission admission) throws SQLException {

        String sql =
                "UPDATE admissions SET " +
                "application_number = ?, " +
                "student_id = ?, " +
                "course_id = ?, " +
                "batch_id = ?, " +
                "application_date = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "date_of_birth = ?, " +
                "gender = ?, " +
                "address_line1 = ?, " +
                "address_line2 = ?, " +
                "city = ?, " +
                "state = ?, " +
                "postal_code = ?, " +
                "country = ?, " +
                "qualification = ?, " +
                "source = ?, " +
                "status = ?, " +
                "notes = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            setStatementValues(ps, admission);
            ps.setLong(22, admission.getId());

            ps.executeUpdate();
        }
    }

    private void setStatementValues(
            PreparedStatement ps,
            Admission admission) throws SQLException {

        ps.setString(1, admission.getApplicationNumber());

        if (admission.getStudentId() != null) {
            ps.setLong(2, admission.getStudentId());
        } else {
            ps.setNull(2, Types.BIGINT);
        }

        ps.setLong(3, admission.getCourseId());

        if (admission.getBatchId() != null) {
            ps.setLong(4, admission.getBatchId());
        } else {
            ps.setNull(4, Types.BIGINT);
        }

        if (admission.getApplicationDate() != null) {
            ps.setDate(
                    5,
                    Date.valueOf(admission.getApplicationDate())
            );
        } else {
            ps.setNull(5, Types.DATE);
        }

        ps.setString(6, admission.getFirstName());
        ps.setString(7, admission.getLastName());
        ps.setString(8, admission.getEmail());
        ps.setString(9, admission.getPhone());

        if (admission.getDateOfBirth() != null) {
            ps.setDate(
                    10,
                    Date.valueOf(admission.getDateOfBirth())
            );
        } else {
            ps.setNull(10, Types.DATE);
        }

        ps.setString(11, admission.getGender());
        ps.setString(12, admission.getAddressLine1());
        ps.setString(13, admission.getAddressLine2());
        ps.setString(14, admission.getCity());
        ps.setString(15, admission.getState());
        ps.setString(16, admission.getPostalCode());
        ps.setString(17, admission.getCountry());
        ps.setString(18, admission.getQualification());
        ps.setString(19, admission.getSource());
        ps.setString(20, admission.getStatus());
        ps.setString(21, admission.getNotes());
    }

    public void delete(long id) throws SQLException {

        String sql = "DELETE FROM admissions WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}