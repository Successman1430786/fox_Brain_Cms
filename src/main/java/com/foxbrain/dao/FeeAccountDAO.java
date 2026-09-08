package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.foxbrain.model.FeeAccount;
import com.foxbrain.util.DBConnection;

public class FeeAccountDAO {

    private FeeAccount mapResultSet(ResultSet rs) throws SQLException {
        FeeAccount obj = new FeeAccount();
        obj.setId(rs.getLong("id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setEnrollmentId(rs.getString("enrollment_id"));
        obj.setFeeType(rs.getString("fee_type"));
        obj.setDescription(rs.getString("description"));
        obj.setTotalAmount(rs.getObject("total_amount", BigDecimal.class));
        obj.setDiscountAmount(rs.getObject("discount_amount", BigDecimal.class));
        obj.setDueDate(rs.getObject("due_date", LocalDate.class));
        obj.setStatus(rs.getString("status"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public FeeAccount findById(long id) {
        String sql = "SELECT * FROM fee_accounts WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding FeeAccount by id", e);
        }
    }

    public List<FeeAccount> findAll() {
        String sql = "SELECT * FROM fee_accounts ORDER BY id DESC";
        List<FeeAccount> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all FeeAccount", e);
        }
    }

    public long insert(FeeAccount obj) {
        String sql = "INSERT INTO fee_accounts (student_id, enrollment_id, fee_type, description, total_amount, discount_amount, due_date, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getFeeType());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getTotalAmount());
            ps.setObject(i++, obj.getDiscountAmount());
            ps.setObject(i++, obj.getDueDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting FeeAccount", e);
        }
    }

    public boolean update(FeeAccount obj) {
        String sql = "UPDATE fee_accounts SET student_id = ?, enrollment_id = ?, fee_type = ?, description = ?, total_amount = ?, discount_amount = ?, due_date = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getFeeType());
            ps.setObject(i++, obj.getDescription());
            ps.setObject(i++, obj.getTotalAmount());
            ps.setObject(i++, obj.getDiscountAmount());
            ps.setObject(i++, obj.getDueDate());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating FeeAccount", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM fee_accounts WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting FeeAccount", e);
        }
    }
}
