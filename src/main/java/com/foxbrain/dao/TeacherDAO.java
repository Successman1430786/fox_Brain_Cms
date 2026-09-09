package com.foxbrain.dao;

import com.foxbrain.model.Teacher;
import com.foxbrain.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {


    // =========================================================
    // MAP RESULT SET
    // =========================================================

    private Teacher mapResultSet(ResultSet rs)
            throws SQLException {

        Teacher teacher = new Teacher();

        teacher.setId(
            rs.getLong("id")
        );

        teacher.setUserId(
            rs.getLong("user_id")
        );

        teacher.setEmployeeNumber(
            rs.getString("employee_number")
        );

        teacher.setQualification(
            rs.getString("qualification")
        );

        teacher.setSpecialization(
            rs.getString("specialization")
        );


        Date joiningDate =
            rs.getDate("joining_date");

        if (joiningDate != null) {
            teacher.setJoiningDate(
                joiningDate.toLocalDate()
            );
        }


        teacher.setAddressLine1(
            rs.getString("address_line1")
        );

        teacher.setAddressLine2(
            rs.getString("address_line2")
        );

        teacher.setCity(
            rs.getString("city")
        );

        teacher.setState(
            rs.getString("state")
        );

        teacher.setPostalCode(
            rs.getString("postal_code")
        );

        teacher.setCountry(
            rs.getString("country")
        );

        teacher.setStatus(
            rs.getString("status")
        );

        teacher.setBio(
            rs.getString("bio")
        );


        Timestamp createdAt =
            rs.getTimestamp("created_at");

        if (createdAt != null) {
            teacher.setCreatedAt(
                createdAt.toLocalDateTime()
            );
        }


        Timestamp updatedAt =
            rs.getTimestamp("updated_at");

        if (updatedAt != null) {
            teacher.setUpdatedAt(
                updatedAt.toLocalDateTime()
            );
        }


        return teacher;
    }


    // =========================================================
    // FIND ALL
    // =========================================================

    public List<Teacher> findAll()
            throws SQLException {

        List<Teacher> teachers =
            new ArrayList<>();

        String sql =
            "SELECT * " +
            "FROM teachers " +
            "ORDER BY id DESC";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {

                teachers.add(
                    mapResultSet(rs)
                );
            }
        }

        return teachers;
    }


    // =========================================================
    // FIND BY ID
    // =========================================================

    public Teacher findById(long id)
            throws SQLException {

        String sql =
            "SELECT * " +
            "FROM teachers " +
            "WHERE id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs =
                     ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }


    // =========================================================
    // FIND BY USER ID
    // =========================================================

    public Teacher findByUserId(long userId)
            throws SQLException {

        String sql =
            "SELECT * " +
            "FROM teachers " +
            "WHERE user_id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setLong(1, userId);

            try (ResultSet rs =
                     ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }


    // =========================================================
    // FIND BY EMPLOYEE NUMBER
    // =========================================================

    public Teacher findByEmployeeNumber(
            String employeeNumber)
            throws SQLException {

        String sql =
            "SELECT * " +
            "FROM teachers " +
            "WHERE employee_number = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setString(1, employeeNumber);

            try (ResultSet rs =
                     ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }


    // =========================================================
    // INSERT
    // =========================================================

    public long insert(
            Connection connection,
            Teacher teacher)
            throws SQLException {

        String sql =
            "INSERT INTO teachers (" +
            "user_id, " +
            "employee_number, " +
            "qualification, " +
            "specialization, " +
            "joining_date, " +
            "address_line1, " +
            "address_line2, " +
            "city, " +
            "state, " +
            "postal_code, " +
            "country, " +
            "status, " +
            "bio" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            PreparedStatement ps =
                connection.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {

            setParameters(ps, teacher);

            ps.setLong(
                1,
                teacher.getUserId()
            );

            ps.setString(
                2,
                teacher.getEmployeeNumber()
            );

            ps.setString(
                3,
                teacher.getQualification()
            );

            ps.setString(
                4,
                teacher.getSpecialization()
            );

            if (teacher.getJoiningDate() != null) {
                ps.setDate(
                    5,
                    Date.valueOf(
                        teacher.getJoiningDate()
                    )
                );
            } else {
                ps.setDate(5, null);
            }

            ps.setString(
                6,
                teacher.getAddressLine1()
            );

            ps.setString(
                7,
                teacher.getAddressLine2()
            );

            ps.setString(
                8,
                teacher.getCity()
            );

            ps.setString(
                9,
                teacher.getState()
            );

            ps.setString(
                10,
                teacher.getPostalCode()
            );

            ps.setString(
                11,
                teacher.getCountry()
            );

            ps.setString(
                12,
                teacher.getStatus()
            );

            ps.setString(
                13,
                teacher.getBio()
            );

            ps.executeUpdate();

            try (ResultSet keys =
                     ps.getGeneratedKeys()) {

                if (keys.next()) {

                    long id =
                        keys.getLong(1);

                    teacher.setId(id);

                    return id;
                }
            }
        }

        throw new SQLException(
            "Unable to create teacher."
        );
    }


    private void setParameters(
            PreparedStatement ps,
            Teacher teacher)
            throws SQLException {

        // Intentionally empty.
        // Parameters are set explicitly above
        // to keep their SQL positions clear.
    }


    // =========================================================
    // UPDATE
    // =========================================================

    public boolean update(
            Teacher teacher)
            throws SQLException {

        String sql =
            "UPDATE teachers SET " +
            "employee_number = ?, " +
            "qualification = ?, " +
            "specialization = ?, " +
            "joining_date = ?, " +
            "address_line1 = ?, " +
            "address_line2 = ?, " +
            "city = ?, " +
            "state = ?, " +
            "postal_code = ?, " +
            "country = ?, " +
            "status = ?, " +
            "bio = ? " +
            "WHERE id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setString(
                1,
                teacher.getEmployeeNumber()
            );

            ps.setString(
                2,
                teacher.getQualification()
            );

            ps.setString(
                3,
                teacher.getSpecialization()
            );

            if (teacher.getJoiningDate() != null) {
                ps.setDate(
                    4,
                    Date.valueOf(
                        teacher.getJoiningDate()
                    )
                );
            } else {
                ps.setDate(4, null);
            }

            ps.setString(
                5,
                teacher.getAddressLine1()
            );

            ps.setString(
                6,
                teacher.getAddressLine2()
            );

            ps.setString(
                7,
                teacher.getCity()
            );

            ps.setString(
                8,
                teacher.getState()
            );

            ps.setString(
                9,
                teacher.getPostalCode()
            );

            ps.setString(
                10,
                teacher.getCountry()
            );

            ps.setString(
                11,
                teacher.getStatus()
            );

            ps.setString(
                12,
                teacher.getBio()
            );

            ps.setLong(
                13,
                teacher.getId()
            );

            return ps.executeUpdate() > 0;
        }
    }


    // =========================================================
    // DELETE
    // =========================================================

    public boolean delete(long id)
            throws SQLException {

        String sql =
            "DELETE FROM teachers " +
            "WHERE id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}