package com.foxbrain.dao;

import com.foxbrain.model.Batch;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchDAO {

    private Batch mapResultSet(ResultSet rs) throws SQLException {

        Batch batch = new Batch();

        batch.setId(rs.getLong("id"));
        batch.setCourseId(rs.getLong("course_id"));
        batch.setCourseName(rs.getString("course_name"));

        batch.setBatchCode(rs.getString("batch_code"));
        batch.setName(rs.getString("name"));

        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            batch.setStartDate(startDate.toLocalDate());
        }

        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            batch.setEndDate(endDate.toLocalDate());
        }

        Time startTime = rs.getTime("start_time");
        if (startTime != null) {
            batch.setStartTime(startTime.toLocalTime());
        }

        Time endTime = rs.getTime("end_time");
        if (endTime != null) {
            batch.setEndTime(endTime.toLocalTime());
        }

        batch.setRoomName(rs.getString("room_name"));

        int capacity = rs.getInt("capacity");
        if (!rs.wasNull()) {
            batch.setCapacity(capacity);
        }

        batch.setStatus(rs.getString("status"));
        batch.setNotes(rs.getString("notes"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            batch.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            batch.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return batch;
    }

    public Batch findById(long id) throws SQLException {

        String sql =
                "SELECT b.*, c.name AS course_name " +
                "FROM batches b " +
                "INNER JOIN courses c ON b.course_id = c.id " +
                "WHERE b.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<Batch> findAll() throws SQLException {

        List<Batch> batches = new ArrayList<>();

        String sql =
                "SELECT b.*, c.name AS course_name " +
                "FROM batches b " +
                "INNER JOIN courses c ON b.course_id = c.id " +
                "ORDER BY b.start_date DESC, b.id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                batches.add(mapResultSet(rs));
            }
        }

        return batches;
    }

    public List<Batch> findActive() throws SQLException {

        List<Batch> batches = new ArrayList<>();

        String sql =
                "SELECT b.*, c.name AS course_name " +
                "FROM batches b " +
                "INNER JOIN courses c ON b.course_id = c.id " +
                "WHERE b.status IN ('PLANNED','ACTIVE') " +
                "ORDER BY b.start_date ASC, b.name ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                batches.add(mapResultSet(rs));
            }
        }

        return batches;
    }

    public boolean existsByCode(String batchCode) throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM batches WHERE batch_code = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, batchCode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsByCodeExceptId(String batchCode, long id)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM batches " +
                "WHERE batch_code = ? AND id <> ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, batchCode);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public long insert(Batch batch) throws SQLException {

        String sql =
                "INSERT INTO batches (" +
                "course_id, batch_code, name, start_date, end_date, " +
                "start_time, end_time, room_name, capacity, status, notes" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, batch.getCourseId());
            ps.setString(2, batch.getBatchCode());
            ps.setString(3, batch.getName());

            if (batch.getStartDate() != null) {
                ps.setDate(4, Date.valueOf(batch.getStartDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (batch.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(batch.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            if (batch.getStartTime() != null) {
                ps.setTime(6, Time.valueOf(batch.getStartTime()));
            } else {
                ps.setNull(6, Types.TIME);
            }

            if (batch.getEndTime() != null) {
                ps.setTime(7, Time.valueOf(batch.getEndTime()));
            } else {
                ps.setNull(7, Types.TIME);
            }

            if (batch.getRoomName() != null &&
                    !batch.getRoomName().trim().isEmpty()) {
                ps.setString(8, batch.getRoomName());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }

            if (batch.getCapacity() != null) {
                ps.setInt(9, batch.getCapacity());
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            ps.setString(10, batch.getStatus());
            ps.setString(11, batch.getNotes());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(Batch batch) throws SQLException {

        String sql =
                "UPDATE batches SET " +
                "course_id = ?, " +
                "batch_code = ?, " +
                "name = ?, " +
                "start_date = ?, " +
                "end_date = ?, " +
                "start_time = ?, " +
                "end_time = ?, " +
                "room_name = ?, " +
                "capacity = ?, " +
                "status = ?, " +
                "notes = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, batch.getCourseId());
            ps.setString(2, batch.getBatchCode());
            ps.setString(3, batch.getName());

            if (batch.getStartDate() != null) {
                ps.setDate(4, Date.valueOf(batch.getStartDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (batch.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(batch.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            if (batch.getStartTime() != null) {
                ps.setTime(6, Time.valueOf(batch.getStartTime()));
            } else {
                ps.setNull(6, Types.TIME);
            }

            if (batch.getEndTime() != null) {
                ps.setTime(7, Time.valueOf(batch.getEndTime()));
            } else {
                ps.setNull(7, Types.TIME);
            }

            if (batch.getRoomName() != null &&
                    !batch.getRoomName().trim().isEmpty()) {
                ps.setString(8, batch.getRoomName());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }

            if (batch.getCapacity() != null) {
                ps.setInt(9, batch.getCapacity());
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            ps.setString(10, batch.getStatus());
            ps.setString(11, batch.getNotes());
            ps.setLong(12, batch.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql = "DELETE FROM batches WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}