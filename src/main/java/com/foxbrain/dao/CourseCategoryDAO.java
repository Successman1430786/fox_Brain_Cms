package com.foxbrain.dao;

import com.foxbrain.model.CourseCategory;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseCategoryDAO {

    private CourseCategory mapResultSet(ResultSet rs)
            throws SQLException {

        CourseCategory category = new CourseCategory();

        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setSlug(rs.getString("slug"));
        category.setDescription(rs.getString("description"));
        category.setDisplayOrder(rs.getInt("display_order"));
        category.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            category.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            category.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return category;
    }

    public CourseCategory findById(long id)
            throws SQLException {

        String sql =
            "SELECT * FROM course_categories WHERE id = ?";

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

    public List<CourseCategory> findAll()
            throws SQLException {

        List<CourseCategory> categories =
            new ArrayList<>();

        String sql =
            "SELECT * FROM course_categories " +
            "ORDER BY display_order ASC, name ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSet(rs));
            }
        }

        return categories;
    }

    public List<CourseCategory> findActive()
            throws SQLException {

        List<CourseCategory> categories =
            new ArrayList<>();

        String sql =
            "SELECT * FROM course_categories " +
            "WHERE status = 'ACTIVE' " +
            "ORDER BY display_order ASC, name ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSet(rs));
            }
        }

        return categories;
    }

    public CourseCategory findByName(String name)
            throws SQLException {

        String sql =
            "SELECT * FROM course_categories WHERE name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public CourseCategory findBySlug(String slug)
            throws SQLException {

        String sql =
            "SELECT * FROM course_categories WHERE slug = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, slug);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public boolean existsByName(String name)
            throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM course_categories WHERE name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsBySlug(String slug)
            throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM course_categories WHERE slug = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, slug);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsByNameExceptId(
            String name, long id) throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM course_categories " +
            "WHERE name = ? AND id <> ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsBySlugExceptId(
            String slug, long id) throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM course_categories " +
            "WHERE slug = ? AND id <> ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

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

    public long insert(CourseCategory category)
            throws SQLException {

        String sql =
            "INSERT INTO course_categories " +
            "(name, slug, description, display_order, status) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                 connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getSlug());
            ps.setString(3, category.getDescription());
            ps.setInt(4, category.getDisplayOrder());
            ps.setString(5, category.getStatus());

            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new SQLException(
                    "Category could not be created."
                );
            }

            try (ResultSet keys =
                     ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException(
            "Category ID could not be generated."
        );
    }

    public boolean update(CourseCategory category)
            throws SQLException {

        String sql =
            "UPDATE course_categories SET " +
            "name = ?, " +
            "slug = ?, " +
            "description = ?, " +
            "display_order = ?, " +
            "status = ? " +
            "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                 connection.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getSlug());
            ps.setString(3, category.getDescription());
            ps.setInt(4, category.getDisplayOrder());
            ps.setString(5, category.getStatus());
            ps.setLong(6, category.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id)
            throws SQLException {

        String sql =
            "DELETE FROM course_categories WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                 connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}   	