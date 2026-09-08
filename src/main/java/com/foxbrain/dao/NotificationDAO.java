package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.Notification;
import com.foxbrain.util.DBConnection;

public class NotificationDAO {

    private Notification mapResultSet(ResultSet rs) throws SQLException {
        Notification obj = new Notification();
        obj.setId(rs.getLong("id"));
        obj.setUserId(rs.getLong("user_id"));
        obj.setAnnouncementId(rs.getString("announcement_id"));
        obj.setTitle(rs.getString("title"));
        obj.setMessage(rs.getString("message"));
        obj.setNotificationType(rs.getString("notification_type"));
        obj.setIsRead(rs.getBoolean("is_read"));
        obj.setReadAt(rs.getObject("read_at", LocalDateTime.class));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        return obj;
    }

    public Notification findById(long id) {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Notification by id", e);
        }
    }

    public List<Notification> findAll() {
        String sql = "SELECT * FROM notifications ORDER BY id DESC";
        List<Notification> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Notification", e);
        }
    }

    public long insert(Notification obj) {
        String sql = "INSERT INTO notifications (user_id, announcement_id, title, message, notification_type, is_read, read_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getAnnouncementId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getMessage());
            ps.setObject(i++, obj.getNotificationType());
            ps.setObject(i++, obj.getIsRead());
            ps.setObject(i++, obj.getReadAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Notification", e);
        }
    }

    public boolean update(Notification obj) {
        String sql = "UPDATE notifications SET user_id = ?, announcement_id = ?, title = ?, message = ?, notification_type = ?, is_read = ?, read_at = ?, created_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getAnnouncementId());
            ps.setObject(i++, obj.getTitle());
            ps.setObject(i++, obj.getMessage());
            ps.setObject(i++, obj.getNotificationType());
            ps.setObject(i++, obj.getIsRead());
            ps.setObject(i++, obj.getReadAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Notification", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Notification", e);
        }
    }
}
