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

import com.foxbrain.model.Teacher;
import com.foxbrain.util.DBConnection;

public class TeacherDAO {

    private Teacher mapResultSet(ResultSet rs) throws SQLException {
        Teacher obj = new Teacher();
        obj.setId(rs.getLong("id"));
        obj.setUserId(rs.getLong("user_id"));
        obj.setEmployeeNumber(rs.getString("employee_number"));
        obj.setQualification(rs.getString("qualification"));
        obj.setSpecialization(rs.getString("specialization"));
        obj.setJoiningDate(rs.getObject("joining_date", LocalDate.class));
        obj.setAddressLine1(rs.getString("address_line1"));
        obj.setAddressLine2(rs.getString("address_line2"));
        obj.setCity(rs.getString("city"));
        obj.setState(rs.getString("state"));
        obj.setPostalCode(rs.getString("postal_code"));
        obj.setCountry(rs.getString("country"));
        obj.setStatus(rs.getString("status"));
        obj.setBio(rs.getString("bio"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Teacher findById(long id) {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Teacher by id", e);
        }
    }

    public List<Teacher> findAll() {
        String sql = "SELECT * FROM teachers ORDER BY id DESC";
        List<Teacher> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Teacher", e);
        }
    }

    public long insert(Teacher obj) {
        String sql = "INSERT INTO teachers (user_id, employee_number, qualification, specialization, joining_date, address_line1, address_line2, city, state, postal_code, country, status, bio, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getEmployeeNumber());
            ps.setObject(i++, obj.getQualification());
            ps.setObject(i++, obj.getSpecialization());
            ps.setObject(i++, obj.getJoiningDate());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getBio());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Teacher", e);
        }
    }

    public boolean update(Teacher obj) {
        String sql = "UPDATE teachers SET user_id = ?, employee_number = ?, qualification = ?, specialization = ?, joining_date = ?, address_line1 = ?, address_line2 = ?, city = ?, state = ?, postal_code = ?, country = ?, status = ?, bio = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getEmployeeNumber());
            ps.setObject(i++, obj.getQualification());
            ps.setObject(i++, obj.getSpecialization());
            ps.setObject(i++, obj.getJoiningDate());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getBio());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Teacher", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Teacher", e);
        }
    }
}
