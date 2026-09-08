package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.Parent;
import com.foxbrain.util.DBConnection;

public class ParentDAO {

    private Parent mapResultSet(ResultSet rs) throws SQLException {
        Parent obj = new Parent();
        obj.setId(rs.getLong("id"));
        obj.setUserId(rs.getLong("user_id"));
        obj.setOccupation(rs.getString("occupation"));
        obj.setAddressLine1(rs.getString("address_line1"));
        obj.setAddressLine2(rs.getString("address_line2"));
        obj.setCity(rs.getString("city"));
        obj.setState(rs.getString("state"));
        obj.setPostalCode(rs.getString("postal_code"));
        obj.setCountry(rs.getString("country"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public Parent findById(long id) {
        String sql = "SELECT * FROM parents WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Parent by id", e);
        }
    }

    public List<Parent> findAll() {
        String sql = "SELECT * FROM parents ORDER BY id DESC";
        List<Parent> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Parent", e);
        }
    }

    public long insert(Parent obj) {
        String sql = "INSERT INTO parents (user_id, occupation, address_line1, address_line2, city, state, postal_code, country, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getOccupation());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Parent", e);
        }
    }

    public boolean update(Parent obj) {
        String sql = "UPDATE parents SET user_id = ?, occupation = ?, address_line1 = ?, address_line2 = ?, city = ?, state = ?, postal_code = ?, country = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getUserId());
            ps.setObject(i++, obj.getOccupation());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Parent", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM parents WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Parent", e);
        }
    }
}
