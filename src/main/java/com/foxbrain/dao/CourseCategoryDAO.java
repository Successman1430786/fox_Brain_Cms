package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.CourseCategory;
import com.foxbrain.util.DBConnection;

public class CourseCategoryDAO {

    private CourseCategory mapResultSet(ResultSet rs) throws SQLException {
        CourseCategory obj = new CourseCategory();
        obj.setId(rs.getLong("id"));
        obj.setName(rs.getString("name"));
        obj.setSlug(rs.getString("slug"));
        obj.setDescription(rs.getString("description"));
        obj.setDisplayOrder(rs.getInt("display_order"));
        obj.setStatus(rs.getString("status"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public CourseCategory findById(long id) {
        String sql = "SELECT * FROM course_categories WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding CourseCategory by id", e);
        }
    }

    public List<CourseCategory> findAll() {
        String sql = "SELECT * FROM course_categories ORDER BY id DESC";
        List<CourseCategory> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all CourseCategory", e);
        }
    }

    public long insert(CourseCategory obj) {
        String sql = "INSERT INTO course_categories (name, slug, description, display_order, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getSlug());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting CourseCategory", e);
        }
    }

    public boolean update(CourseCategory obj) {
        String sql = "UPDATE course_categories SET name = ?, slug = ?, description = ?, display_order = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getSlug());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating CourseCategory", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM course_categories WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting CourseCategory", e);
        }
    }
}
