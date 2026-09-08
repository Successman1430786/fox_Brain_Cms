package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.foxbrain.model.Batch;
import com.foxbrain.util.DBConnection;

public class BatchDAO {

    private Batch mapResultSet(ResultSet rs) throws SQLException {
        Batch obj = new Batch();
        obj.setId(rs.getLong("id"));
        obj.setCourseId(rs.getLong("course_id"));
        obj.setBatchCode(rs.getString("batch_code"));
        obj.setName(rs.getString("name"));
        obj.setStartDate(rs.getObject("start_date", LocalDate.class));
        obj.setEndDate(rs.getObject("end_date", LocalDate.class));
        obj.setStartTime(rs.getObject("start_time", LocalTime.class));
        obj.setEndTime(rs.getObject("end_time", LocalTime.class));
        obj.setRoomName(rs.getString("room_name"));
        obj.setCapacity(rs.getString("capacity"));
        obj.setStatus(rs.getString("status"));
        obj.setNotes(rs.getString("notes"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Batch findById(long id) {
        String sql = "SELECT * FROM batches WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Batch by id", e);
        }
    }

    public List<Batch> findAll() {
        String sql = "SELECT * FROM batches ORDER BY id DESC";
        List<Batch> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Batch", e);
        }
    }

    public long insert(Batch obj) {
        String sql = "INSERT INTO batches (course_id, batch_code, name, start_date, end_date, start_time, end_time, room_name, capacity, status, notes, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getBatchCode());
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getStartDate());
            ps.setObject(i++, obj.getEndDate());
            ps.setObject(i++, obj.getStartTime());
            ps.setObject(i++, obj.getEndTime());
            ps.setObject(i++, obj.getRoomName());
            ps.setObject(i++, obj.getCapacity());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Batch", e);
        }
    }

    public boolean update(Batch obj) {
        String sql = "UPDATE batches SET course_id = ?, batch_code = ?, name = ?, start_date = ?, end_date = ?, start_time = ?, end_time = ?, room_name = ?, capacity = ?, status = ?, notes = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getBatchCode());
            ps.setObject(i++, obj.getName());
            ps.setObject(i++, obj.getStartDate());
            ps.setObject(i++, obj.getEndDate());
            ps.setObject(i++, obj.getStartTime());
            ps.setObject(i++, obj.getEndTime());
            ps.setObject(i++, obj.getRoomName());
            ps.setObject(i++, obj.getCapacity());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Batch", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM batches WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Batch", e);
        }
    }
}
