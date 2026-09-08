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

import com.foxbrain.model.Attendance;
import com.foxbrain.util.DBConnection;

public class AttendanceDAO {

    private Attendance mapResultSet(ResultSet rs) throws SQLException {
        Attendance obj = new Attendance();
        obj.setId(rs.getLong("id"));
        obj.setEnrollmentId(rs.getLong("enrollment_id"));
        obj.setAttendanceDate(rs.getObject("attendance_date", LocalDate.class));
        obj.setStatus(rs.getString("status"));
        obj.setCheckInTime(rs.getObject("check_in_time", LocalTime.class));
        obj.setCheckOutTime(rs.getObject("check_out_time", LocalTime.class));
        obj.setRemarks(rs.getString("remarks"));
        obj.setMarkedByTeacherId(rs.getString("marked_by_teacher_id"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Attendance findById(long id) {
        String sql = "SELECT * FROM attendance WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Attendance by id", e);
        }
    }

    public List<Attendance> findAll() {
        String sql = "SELECT * FROM attendance ORDER BY id DESC";
        List<Attendance> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Attendance", e);
        }
    }

    public long insert(Attendance obj) {
        String sql = "INSERT INTO attendance (enrollment_id, attendance_date, status, check_in_time, check_out_time, remarks, marked_by_teacher_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getAttendanceDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCheckInTime());
            ps.setObject(i++, obj.getCheckOutTime());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getMarkedByTeacherId());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Attendance", e);
        }
    }

    public boolean update(Attendance obj) {
        String sql = "UPDATE attendance SET enrollment_id = ?, attendance_date = ?, status = ?, check_in_time = ?, check_out_time = ?, remarks = ?, marked_by_teacher_id = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getAttendanceDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCheckInTime());
            ps.setObject(i++, obj.getCheckOutTime());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getMarkedByTeacherId());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Attendance", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM attendance WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Attendance", e);
        }
    }
}
