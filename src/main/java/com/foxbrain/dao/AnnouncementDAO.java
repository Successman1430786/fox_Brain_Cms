package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.Announcement;
import com.foxbrain.util.DBConnection;

public class AnnouncementDAO {

    private Announcement mapResultSet(ResultSet rs) throws SQLException {
        Announcement obj = new Announcement();
        obj.setId(rs.getLong("id"));
        obj.setTitle(rs.getString("title"));
        obj.setContent(rs.getString("content"));
        obj.setTargetRole(rs.getString("target_role"));
        obj.setPublishAt(rs.getObject("publish_at", LocalDateTime.class));
        obj.setExpireAt(rs.getObject("expire_at", LocalDateTime.class));
        obj.setStatus(rs.getString("status"));
        obj.setCreatedByUserId(rs.getLong("created_by_user_id"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Announcement findById(long id) {
        String sql = "SELECT * FROM announcements WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Announcement by id", e);
        }
    }

    public List<Announcement> findAll() {
        String sql = "SELECT * FROM announcements ORDER BY id DESC";
        List<Announcement> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Announcement", e);
        }
    }

    public long insert(Announcement obj) {
        String sql = "INSERT INTO announcements (title, content, target_role, publish_at, expire_at, status, created_by_user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getContent());
            ps.setObject(i++, obj.getTargetRole());
            ps.setObject(i++, obj.getPublishAt());
            ps.setObject(i++, obj.getExpireAt());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedByUserId());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Announcement", e);
        }
    }

    public boolean update(Announcement obj) {
        String sql = "UPDATE announcements SET title = ?, content = ?, target_role = ?, publish_at = ?, expire_at = ?, status = ?, created_by_user_id = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getContent());
            ps.setObject(i++, obj.getTargetRole());
            ps.setObject(i++, obj.getPublishAt());
            ps.setObject(i++, obj.getExpireAt());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedByUserId());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Announcement", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM announcements WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Announcement", e);
        }
    }
}
