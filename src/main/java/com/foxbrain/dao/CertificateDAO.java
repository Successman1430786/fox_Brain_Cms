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

import com.foxbrain.model.Certificate;
import com.foxbrain.util.DBConnection;

public class CertificateDAO {

    private Certificate mapResultSet(ResultSet rs) throws SQLException {
        Certificate obj = new Certificate();
        obj.setId(rs.getLong("id"));
        obj.setStudentId(rs.getLong("student_id"));
        obj.setCourseId(rs.getLong("course_id"));
        obj.setEnrollmentId(rs.getString("enrollment_id"));
        obj.setCertificateNumber(rs.getString("certificate_number"));
        obj.setIssueDate(rs.getObject("issue_date", LocalDate.class));
        obj.setCertificateType(rs.getString("certificate_type"));
        obj.setGrade(rs.getString("grade"));
        obj.setVerificationCode(rs.getString("verification_code"));
        obj.setCertificateUrl(rs.getString("certificate_url"));
        obj.setStatus(rs.getString("status"));
        obj.setRemarks(rs.getString("remarks"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Certificate findById(long id) {
        String sql = "SELECT * FROM certificates WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Certificate by id", e);
        }
    }

    public List<Certificate> findAll() {
        String sql = "SELECT * FROM certificates ORDER BY id DESC";
        List<Certificate> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Certificate", e);
        }
    }

    public long insert(Certificate obj) {
        String sql = "INSERT INTO certificates (student_id, course_id, enrollment_id, certificate_number, issue_date, certificate_type, grade, verification_code, certificate_url, status, remarks, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getCertificateNumber());
            ps.setObject(i++, obj.getIssueDate());
            ps.setObject(i++, obj.getCertificateType());
            ps.setObject(i++, obj.getGrade());
            ps.setObject(i++, obj.getVerificationCode());
            ps.setObject(i++, obj.getCertificateUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Certificate", e);
        }
    }

    public boolean update(Certificate obj) {
        String sql = "UPDATE certificates SET student_id = ?, course_id = ?, enrollment_id = ?, certificate_number = ?, issue_date = ?, certificate_type = ?, grade = ?, verification_code = ?, certificate_url = ?, status = ?, remarks = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getStudentId());
            ps.setObject(i++, obj.getCourseId());
            ps.setObject(i++, obj.getEnrollmentId());
            ps.setObject(i++, obj.getCertificateNumber());
            ps.setObject(i++, obj.getIssueDate());
            ps.setObject(i++, obj.getCertificateType());
            ps.setObject(i++, obj.getGrade());
            ps.setObject(i++, obj.getVerificationCode());
            ps.setObject(i++, obj.getCertificateUrl());
            ps.setObject(i++, obj.getStatus());
            ps.setObject(i++, obj.getRemarks());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Certificate", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM certificates WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Certificate", e);
        }
    }
}
