package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.Enquiry;
import com.foxbrain.util.DBConnection;

public class EnquiryDAO {

    private Enquiry mapResultSet(ResultSet rs) throws SQLException {
        Enquiry obj = new Enquiry();
        obj.setId(rs.getLong("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setPhone(rs.getString("phone"));
        obj.setSubject(rs.getString("subject"));
        obj.setMessage(rs.getString("message"));
        obj.setCourseId(rs.getString("course_id"));
        obj.setStatus(rs.getString("status"));
        obj.setAssignedToUserId(rs.getString("assigned_to_user_id"));
        obj.setResponseNotes(rs.getString("response_notes"));
        obj.setRespondedAt(rs.getObject("responded_at", LocalDateTime.class));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Enquiry findById(long id) {
        String sql = "SELECT * FROM enquiries WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Enquiry by id", e);
        }
    }

    public List<Enquiry> findAll() {
        String sql = "SELECT * FROM enquiries ORDER BY id DESC";
        List<Enquiry> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Enquiry", e);
        }
    }

    public long insert(Enquiry obj) {
        String sql = "INSERT INTO enquiries (name, email, phone, subject, message, course_id, status, assigned_to_user_id, response_notes, responded_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getEmail());
            ps.setObject(i++, obj.getPhone());
            ps.setObject(i++, obj.getSubject());
            ps.setObject(i++, obj.getMessage());
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getAssignedToUserId());
            ps.setObject(i++, obj.getResponseNotes());
            ps.setObject(i++, obj.getRespondedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Enquiry", e);
        }
    }

    public boolean update(Enquiry obj) {
        String sql = "UPDATE enquiries SET name = ?, email = ?, phone = ?, subject = ?, message = ?, course_id = ?, status = ?, assigned_to_user_id = ?, response_notes = ?, responded_at = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getEmail());
            ps.setObject(i++, obj.getPhone());
            ps.setObject(i++, obj.getSubject());
            ps.setObject(i++, obj.getMessage());
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getAssignedToUserId());
            ps.setObject(i++, obj.getResponseNotes());
            ps.setObject(i++, obj.getRespondedAt());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Enquiry", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM enquiries WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Enquiry", e);
        }
    }
}
