package com.foxbrain.dao;

import com.foxbrain.model.Course;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {


    private Course mapResultSet(ResultSet rs) throws SQLException {

        Course course = new Course();

        course.setId(rs.getLong("id"));

        long categoryId = rs.getLong("category_id");

        if (rs.wasNull()) {
            course.setCategoryId(null);
        } else {
            course.setCategoryId(categoryId);
        }

        try {
            course.setCategoryName(rs.getString("category_name"));
        } catch (SQLException ignored) {
            course.setCategoryName(null);
        }

        course.setName(rs.getString("name"));
        course.setSlug(rs.getString("slug"));
        course.setShortDescription(
            rs.getString("short_description")
        );
        course.setDescription(
            rs.getString("description")
        );

        int durationValue = rs.getInt("duration_value");

        if (rs.wasNull()) {
            course.setDurationValue(null);
        } else {
            course.setDurationValue(durationValue);
        }

        course.setDurationUnit(
            rs.getString("duration_unit")
        );

        course.setFee(rs.getBigDecimal("fee"));

        course.setCurrencyCode(
            rs.getString("currency_code")
        );

        int maxStudents = rs.getInt("max_students");

        if (rs.wasNull()) {
            course.setMaxStudents(null);
        } else {
            course.setMaxStudents(maxStudents);
        }

        course.setSyllabus(
            rs.getString("syllabus")
        );

        course.setImageUrl(
            rs.getString("image_url")
        );

        course.setStatus(
            rs.getString("status")
        );

        course.setFeatured(
            rs.getBoolean("is_featured")
        );

        course.setDisplayOrder(
            rs.getInt("display_order")
        );

        Timestamp created = rs.getTimestamp("created_at");

        if (created != null) {
            course.setCreatedAt(
                created.toLocalDateTime()
            );
        }

        Timestamp updated = rs.getTimestamp("updated_at");

        if (updated != null) {
            course.setUpdatedAt(
                updated.toLocalDateTime()
            );
        }

        return course;
    }


    public Course findById(long id) throws SQLException {

        String sql =
            "SELECT c.*, cc.name AS category_name " +
            "FROM courses c " +
            "LEFT JOIN course_categories cc " +
            "ON c.category_id = cc.id " +
            "WHERE c.id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
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


    public List<Course> findAll() throws SQLException {

        List<Course> courses = new ArrayList<>();

        String sql =
            "SELECT c.*, cc.name AS category_name " +
            "FROM courses c " +
            "LEFT JOIN course_categories cc " +
            "ON c.category_id = cc.id " +
            "ORDER BY c.display_order ASC, c.name ASC";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {
                courses.add(mapResultSet(rs));
            }
        }

        return courses;
    }


    public List<Course> findActive() throws SQLException {

        List<Course> courses = new ArrayList<>();

        String sql =
            "SELECT c.*, cc.name AS category_name " +
            "FROM courses c " +
            "LEFT JOIN course_categories cc " +
            "ON c.category_id = cc.id " +
            "WHERE c.status = 'ACTIVE' " +
            "ORDER BY c.display_order ASC, c.name ASC";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {
                courses.add(mapResultSet(rs));
            }
        }

        return courses;
    }


    public boolean existsBySlug(String slug)
            throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM courses WHERE slug = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setString(1, slug);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }


    public boolean existsBySlugExceptId(
            String slug,
            long id)
            throws SQLException {

        String sql =
            "SELECT COUNT(*) " +
            "FROM courses " +
            "WHERE slug = ? AND id <> ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            ps.setString(1, slug);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }


    public long insert(Course course)
            throws SQLException {

        String sql =
            "INSERT INTO courses (" +
            "category_id, name, slug, short_description, " +
            "description, duration_value, duration_unit, " +
            "fee, currency_code, max_students, syllabus, " +
            "image_url, status, is_featured, display_order" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                )
        ) {

            if (course.getCategoryId() == null) {
                ps.setNull(1, Types.BIGINT);
            } else {
                ps.setLong(
                    1,
                    course.getCategoryId()
                );
            }

            ps.setString(2, course.getName());
            ps.setString(3, course.getSlug());

            ps.setString(
                4,
                course.getShortDescription()
            );

            ps.setString(
                5,
                course.getDescription()
            );

            if (course.getDurationValue() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(
                    6,
                    course.getDurationValue()
                );
            }

            ps.setString(
                7,
                course.getDurationUnit()
            );

            if (course.getFee() == null) {
                ps.setNull(8, Types.DECIMAL);
            } else {
                ps.setBigDecimal(
                    8,
                    course.getFee()
                );
            }

            ps.setString(
                9,
                course.getCurrencyCode()
            );

            if (course.getMaxStudents() == null) {
                ps.setNull(10, Types.INTEGER);
            } else {
                ps.setInt(
                    10,
                    course.getMaxStudents()
                );
            }

            ps.setString(
                11,
                course.getSyllabus()
            );

            ps.setString(
                12,
                course.getImageUrl()
            );

            ps.setString(
                13,
                course.getStatus()
            );

            ps.setBoolean(
                14,
                course.isFeatured()
            );

            ps.setInt(
                15,
                course.getDisplayOrder()
            );

            ps.executeUpdate();

            try (ResultSet rs =
                     ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        throw new SQLException(
            "Creating course failed."
        );
    }


    public boolean update(Course course)
            throws SQLException {

        String sql =
            "UPDATE courses SET " +
            "category_id = ?, " +
            "name = ?, " +
            "slug = ?, " +
            "short_description = ?, " +
            "description = ?, " +
            "duration_value = ?, " +
            "duration_unit = ?, " +
            "fee = ?, " +
            "currency_code = ?, " +
            "max_students = ?, " +
            "syllabus = ?, " +
            "image_url = ?, " +
            "status = ?, " +
            "is_featured = ?, " +
            "display_order = ? " +
            "WHERE id = ?";

        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement ps =
                connection.prepareStatement(sql)
        ) {

            if (course.getCategoryId() == null) {
                ps.setNull(1, Types.BIGINT);
            } else {
                ps.setLong(
                    1,
                    course.getCategoryId()
                );
            }

            ps.setString(2, course.getName());
            ps.setString(3, course.getSlug());

            ps.setString(
                4,
                course.getShortDescription()
            );

            ps.setString(
                5,
                course.getDescription()
            );

            if (course.getDurationValue() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(
                    6,
                    course.getDurationValue()
                );
            }

            ps.setString(
                7,
                course.getDurationUnit()
            );

            if (course.getFee() == null) {
                ps.setNull(8, Types.DECIMAL);
            } else {
                ps.setBigDecimal(
                    8,
                    course.getFee()
                );
            }

            ps.setString(
                9,
                course.getCurrencyCode()
            );

            if (course.getMaxStudents() == null) {
                ps.setNull(10, Types.INTEGER);
            } else {
                ps.setInt(
                    10,
                    course.getMaxStudents()
                );
            }

            ps.setString(
                11,
                course.getSyllabus()
            );

            ps.setString(
                12,
                course.getImageUrl()
            );

            ps.setString(
                13,
                course.getStatus()
            );

            ps.setBoolean(
                14,
                course.isFeatured()
            );

            ps.setInt(
                15,
                course.getDisplayOrder()
            );

            ps.setLong(
                16,
                course.getId()
            );

            return ps.executeUpdate() > 0;
        }
    }


    public boolean delete(long id)
            throws SQLException {

        String sql =
            "DELETE FROM courses WHERE id = ?";

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