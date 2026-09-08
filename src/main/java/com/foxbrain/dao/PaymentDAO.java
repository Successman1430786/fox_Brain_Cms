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

import com.foxbrain.model.Payment;
import com.foxbrain.util.DBConnection;

public class PaymentDAO {

    private Payment mapResultSet(ResultSet rs) throws SQLException {
        Payment obj = new Payment();
        obj.setId(rs.getLong("id"));
        obj.setFeeAccountId(rs.getLong("fee_account_id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setReceiptNumber(rs.getString("receipt_number"));
        obj.setAmount(rs.getObject("amount", BigDecimal.class));
        obj.setPaymentDate(rs.getObject("payment_date", LocalDateTime.class));
        obj.setPaymentMethod(rs.getString("payment_method"));
        obj.setTransactionReference(rs.getString("transaction_reference"));
        obj.setStatus(rs.getString("status"));
        obj.setNotes(rs.getString("notes"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        return obj;
    }

    public Payment findById(long id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Payment by id", e);
        }
    }

    public List<Payment> findAll() {
        String sql = "SELECT * FROM payments ORDER BY id DESC";
        List<Payment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Payment", e);
        }
    }

    public long insert(Payment obj) {
        String sql = "INSERT INTO payments (fee_account_id, student_id, receipt_number, amount, payment_date, payment_method, transaction_reference, status, notes, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getFeeAccountId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getReceiptNumber());
            ps.setObject(i++, obj.getAmount());
            ps.setObject(i++, obj.getPaymentDate());
            ps.setObject(i++, obj.getPaymentMethod());
            ps.setObject(i++, obj.getTransactionReference());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Payment", e);
        }
    }

    public boolean update(Payment obj) {
        String sql = "UPDATE payments SET fee_account_id = ?, student_id = ?, receipt_number = ?, amount = ?, payment_date = ?, payment_method = ?, transaction_reference = ?, status = ?, notes = ?, created_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getFeeAccountId());
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getReceiptNumber());
            ps.setObject(i++, obj.getAmount());
            ps.setObject(i++, obj.getPaymentDate());
            ps.setObject(i++, obj.getPaymentMethod());
            ps.setObject(i++, obj.getTransactionReference());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getNotes());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Payment", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Payment", e);
        }
    }
}
