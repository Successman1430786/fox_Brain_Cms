package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.WebsiteContent;
import com.foxbrain.util.DBConnection;

public class WebsiteContentDAO {

    private WebsiteContent mapResultSet(ResultSet rs) throws SQLException {
        WebsiteContent obj = new WebsiteContent();
        obj.setId(rs.getLong("id"));
        obj.setContentKey(rs.getString("content_key"));
        obj.setTitle(rs.getString("title"));
        obj.setContent(rs.getString("content"));
        obj.setImageUrl(rs.getString("image_url"));
        obj.setStatus(rs.getString("status"));
        obj.setDisplayOrder(rs.getInt("display_order"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public WebsiteContent findById(long id) {
        String sql = "SELECT * FROM website_content WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding WebsiteContent by id", e);
        }
    }

    public List<WebsiteContent> findAll() {
        String sql = "SELECT * FROM website_content ORDER BY id DESC";
        List<WebsiteContent> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all WebsiteContent", e);
        }
    }

    public long insert(WebsiteContent obj) {
        String sql = "INSERT INTO website_content (content_key, title, content, image_url, status, display_order, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getContentKey());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getContent());
            ps.setObject(i++, obj.getImageUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting WebsiteContent", e);
        }
    }

    public boolean update(WebsiteContent obj) {
        String sql = "UPDATE website_content SET content_key = ?, title = ?, content = ?, image_url = ?, status = ?, display_order = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getContentKey());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getContent());
            ps.setObject(i++, obj.getImageUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getDisplayOrder());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating WebsiteContent", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM website_content WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting WebsiteContent", e);
        }
    }
}
