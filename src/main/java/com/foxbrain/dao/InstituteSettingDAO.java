package com.foxbrain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.foxbrain.model.InstituteSetting;
import com.foxbrain.util.DBConnection;

public class InstituteSettingDAO {

    private InstituteSetting mapResultSet(ResultSet rs) throws SQLException {
        InstituteSetting obj = new InstituteSetting();
        obj.setId(rs.getLong("id"));
        obj.setInstituteName(rs.getString("institute_name"));
        obj.setTagline(rs.getString("tagline"));
        obj.setAboutText(rs.getString("about_text"));
        obj.setAddressLine1(rs.getString("address_line1"));
        obj.setAddressLine2(rs.getString("address_line2"));
        obj.setCity(rs.getString("city"));
        obj.setState(rs.getString("state"));
        obj.setPostalCode(rs.getString("postal_code"));
        obj.setCountry(rs.getString("country"));
        obj.setPhone(rs.getString("phone"));
        obj.setAlternatePhone(rs.getString("alternate_phone"));
        obj.setEmail(rs.getString("email"));
        obj.setWebsite(rs.getString("website"));
        obj.setLogoUrl(rs.getString("logo_url"));
        obj.setFaviconUrl(rs.getString("favicon_url"));
        obj.setFacebookUrl(rs.getString("facebook_url"));
        obj.setInstagramUrl(rs.getString("instagram_url"));
        obj.setYoutubeUrl(rs.getString("youtube_url"));
        obj.setLinkedinUrl(rs.getString("linkedin_url"));
        obj.setOfficeHours(rs.getString("office_hours"));
        obj.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        obj.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return obj;
    }

    public InstituteSetting findById(long id) {
        String sql = "SELECT * FROM institute_settings WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding InstituteSetting by id", e);
        }
    }

    public List<InstituteSetting> findAll() {
        String sql = "SELECT * FROM institute_settings ORDER BY id DESC";
        List<InstituteSetting> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all InstituteSetting", e);
        }
    }

    public long insert(InstituteSetting obj) {
        String sql = "INSERT INTO institute_settings (institute_name, tagline, about_text, address_line1, address_line2, city, state, postal_code, country, phone, alternate_phone, email, website, logo_url, favicon_url, facebook_url, instagram_url, youtube_url, linkedin_url, office_hours, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setObject(i++, obj.getInstituteName());
            ps.setObject(i++, obj.getTagline());
            ps.setObject(i++, obj.getAboutText());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getPhone());
            ps.setObject(i++, obj.getAlternatePhone());
            ps.setObject(i++, obj.getEmail());
            ps.setObject(i++, obj.getWebsite());
            ps.setObject(i++, obj.getLogoUrl());
            ps.setObject(i++, obj.getFaviconUrl());
            ps.setObject(i++, obj.getFacebookUrl());
            ps.setObject(i++, obj.getInstagramUrl());
            ps.setObject(i++, obj.getYoutubeUrl());
            ps.setObject(i++, obj.getLinkedinUrl());
            ps.setObject(i++, obj.getOfficeHours());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting InstituteSetting", e);
        }
    }

    public boolean update(InstituteSetting obj) {
        String sql = "UPDATE institute_settings SET institute_name = ?, tagline = ?, about_text = ?, address_line1 = ?, address_line2 = ?, city = ?, state = ?, postal_code = ?, country = ?, phone = ?, alternate_phone = ?, email = ?, website = ?, logo_url = ?, favicon_url = ?, facebook_url = ?, instagram_url = ?, youtube_url = ?, linkedin_url = ?, office_hours = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setObject(i++, obj.getInstituteName());
            ps.setObject(i++, obj.getTagline());
            ps.setObject(i++, obj.getAboutText());
            ps.setObject(i++, obj.getAddressLine1());
            ps.setObject(i++, obj.getAddressLine2());
            ps.setObject(i++, obj.getCity());
            ps.setObject(i++, obj.getState());
            ps.setObject(i++, obj.getPostalCode());
            ps.setObject(i++, obj.getCountry());
            ps.setObject(i++, obj.getPhone());
            ps.setObject(i++, obj.getAlternatePhone());
            ps.setObject(i++, obj.getEmail());
            ps.setObject(i++, obj.getWebsite());
            ps.setObject(i++, obj.getLogoUrl());
            ps.setObject(i++, obj.getFaviconUrl());
            ps.setObject(i++, obj.getFacebookUrl());
            ps.setObject(i++, obj.getInstagramUrl());
            ps.setObject(i++, obj.getYoutubeUrl());
            ps.setObject(i++, obj.getLinkedinUrl());
            ps.setObject(i++, obj.getOfficeHours());
            ps.setObject(i++, obj.getCreatedAt());
            ps.setObject(i++, obj.getUpdatedAt());
            ps.setLong(i++, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating InstituteSetting", e);
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM institute_settings WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting InstituteSetting", e);
        }
    }
}
