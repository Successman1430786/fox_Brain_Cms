package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.foxbrain.model.Course;
import com.foxbrain.util.DBConnection;

public class CourseDAO {

    private Course mapResultSet(ResultSet rs) throws SQLException {
        Course obj = new Course();
        obj.setId(rs.getLong("id"));
        obj.setCategoryId(rs.getString("category_id"));
        obj.setName(rs.getString("name"));
        obj.setSlug(rs.getString("slug"));
        obj.setShortDescription(rs.getString("short_description"));
        obj.setDescription(rs.getString("description"));
        obj.setDurationValue(rs.getString("duration_value"));
        obj.setDurationUnit(rs.getString("duration_unit"));
        obj.setFee(rs.getObject("fee", BigDecimal.class));
        obj.setCurrencyCode(rs.getString("currency_code"));
        obj.setMaxStudents(rs.getString("max_students"));
        obj.setSyllabus(rs.getString("syllabus"));
        obj.setImageUrl(rs.getString("image_url"));
        obj.setStatus(rs.getString("status"));
        obj.setIsFeatured(rs.getBoolean("is_featured"));
        obj.setDisplayOrder(rs.getInt("display_order"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Course findById(long id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Course by id", e);
        }
    }

    public List<Course> findAll() {
        String sql = "SELECT * FROM courses ORDER BY id DESC";
        List<Course> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Course", e);
        }
    }

    public long insert(Course obj) {
        String sql = "INSERT INTO courses (category_id, name, slug, short_description, description, duration_value, duration_unit, fee, currency_code, max_students, syllabus, image_url, status, is_featured, display_order, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getCategoryId());
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getSlug());
            ps.setObject(i++, obj.getShortDescription());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getDurationValue());
            ps.setObject(i++, obj.getDurationUnit());
            ps.setObject(i++, obj.getFee());
            ps.setObject(i++, obj.getCurrencyCode());
            ps.setObject(i++, obj.getMaxStudents());
            ps.setObject(i++, obj.getSyllabus());
            ps.setObject(i++, obj.getImageUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getIsFeatured());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Course", e);
        }
    }

    public boolean update(Course obj) {
        String sql = "UPDATE courses SET category_id = ?, name = ?, slug = ?, short_description = ?, description = ?, duration_value = ?, duration_unit = ?, fee = ?, currency_code = ?, max_students = ?, syllabus = ?, image_url = ?, status = ?, is_featured = ?, display_order = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getCategoryId());
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getSlug());
            ps.setObject(i++, obj.getShortDescription());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getDurationValue());
            ps.setObject(i++, obj.getDurationUnit());
            ps.setObject(i++, obj.getFee());
            ps.setObject(i++, obj.getCurrencyCode());
            ps.setObject(i++, obj.getMaxStudents());
            ps.setObject(i++, obj.getSyllabus());
            ps.setObject(i++, obj.getImageUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getIsFeatured());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Course", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Course", e);
        }
    }
}
