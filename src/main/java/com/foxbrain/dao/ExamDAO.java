package com.foxbrain.dao;

import com.foxbrain.model.Exam;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public List<Exam> getAll() throws SQLException {

        String sql =
                "SELECT e.*, b.batch_name, b.batch_code " +
                "FROM exams e " +
                "LEFT JOIN batches b ON e.batch_id = b.id " +
                "ORDER BY e.exam_date DESC, e.start_time DESC, e.id DESC";

        List<Exam> exams = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                exams.add(mapRow(rs));
            }
        }

        return exams;
    }

    public Exam getById(long id) throws SQLException {

        String sql =
                "SELECT e.*, b.batch_name, b.batch_code " +
                "FROM exams e " +
                "LEFT JOIN batches b ON e.batch_id = b.id " +
                "WHERE e.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public long create(Exam exam) throws SQLException {

        String sql =
                "INSERT INTO exams " +
                "(batch_id, title, exam_type, exam_mode, exam_date, " +
                "start_time, end_time, duration_minutes, total_marks, " +
                "passing_marks, room_name, instructions, allow_navigation, " +
                "shuffle_questions, shuffle_options, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, exam.getBatchId());
            ps.setString(2, exam.getTitle());
            ps.setString(3, exam.getExamType());
            ps.setString(4, exam.getExamMode());

            if (exam.getExamDate() != null)
                ps.setDate(5, exam.getExamDate());
            else
                ps.setNull(5, Types.DATE);

            if (exam.getStartTime() != null)
                ps.setTime(6, exam.getStartTime());
            else
                ps.setNull(6, Types.TIME);

            if (exam.getEndTime() != null)
                ps.setTime(7, exam.getEndTime());
            else
                ps.setNull(7, Types.TIME);

            if (exam.getDurationMinutes() != null)
                ps.setInt(8, exam.getDurationMinutes());
            else
                ps.setNull(8, Types.INTEGER);

            ps.setDouble(9, exam.getTotalMarks());

            if (exam.getPassingMarks() != null)
                ps.setDouble(10, exam.getPassingMarks());
            else
                ps.setNull(10, Types.DECIMAL);

            ps.setString(11, exam.getRoomName());
            ps.setString(12, exam.getInstructions());

            ps.setBoolean(13, exam.isAllowNavigation());
            ps.setBoolean(14, exam.isShuffleQuestions());
            ps.setBoolean(15, exam.isShuffleOptions());

            ps.setString(16, exam.getStatus());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(Exam exam) throws SQLException {

        String sql =
                "UPDATE exams SET " +
                "batch_id=?, title=?, exam_type=?, exam_mode=?, " +
                "exam_date=?, start_time=?, end_time=?, duration_minutes=?, " +
                "total_marks=?, passing_marks=?, room_name=?, instructions=?, " +
                "allow_navigation=?, shuffle_questions=?, shuffle_options=?, " +
                "status=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, exam.getBatchId());
            ps.setString(2, exam.getTitle());
            ps.setString(3, exam.getExamType());
            ps.setString(4, exam.getExamMode());

            if (exam.getExamDate() != null)
                ps.setDate(5, exam.getExamDate());
            else
                ps.setNull(5, Types.DATE);

            if (exam.getStartTime() != null)
                ps.setTime(6, exam.getStartTime());
            else
                ps.setNull(6, Types.TIME);

            if (exam.getEndTime() != null)
                ps.setTime(7, exam.getEndTime());
            else
                ps.setNull(7, Types.TIME);

            if (exam.getDurationMinutes() != null)
                ps.setInt(8, exam.getDurationMinutes());
            else
                ps.setNull(8, Types.INTEGER);

            ps.setDouble(9, exam.getTotalMarks());

            if (exam.getPassingMarks() != null)
                ps.setDouble(10, exam.getPassingMarks());
            else
                ps.setNull(10, Types.DECIMAL);

            ps.setString(11, exam.getRoomName());
            ps.setString(12, exam.getInstructions());

            ps.setBoolean(13, exam.isAllowNavigation());
            ps.setBoolean(14, exam.isShuffleQuestions());
            ps.setBoolean(15, exam.isShuffleOptions());

            ps.setString(16, exam.getStatus());

            ps.setLong(17, exam.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql = "DELETE FROM exams WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Exam> getByBatchId(long batchId) throws SQLException {

        String sql =
                "SELECT e.*, b.batch_name, b.batch_code " +
                "FROM exams e " +
                "LEFT JOIN batches b ON e.batch_id=b.id " +
                "WHERE e.batch_id=? " +
                "ORDER BY e.exam_date DESC, e.id DESC";

        List<Exam> exams = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, batchId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exams.add(mapRow(rs));
                }
            }
        }

        return exams;
    }

    public List<Exam> getByStatus(String status) throws SQLException {

        String sql =
                "SELECT e.*, b.batch_name, b.batch_code " +
                "FROM exams e " +
                "LEFT JOIN batches b ON e.batch_id=b.id " +
                "WHERE e.status=? " +
                "ORDER BY e.exam_date DESC, e.id DESC";

        List<Exam> exams = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exams.add(mapRow(rs));
                }
            }
        }

        return exams;
    }

    private Exam mapRow(ResultSet rs) throws SQLException {

        Exam exam = new Exam();

        exam.setId(rs.getLong("id"));
        exam.setBatchId(rs.getLong("batch_id"));

        exam.setTitle(rs.getString("title"));
        exam.setExamType(rs.getString("exam_type"));
        exam.setExamMode(rs.getString("exam_mode"));

        exam.setExamDate(rs.getDate("exam_date"));
        exam.setStartTime(rs.getTime("start_time"));
        exam.setEndTime(rs.getTime("end_time"));

        int duration = rs.getInt("duration_minutes");
        if (rs.wasNull()) {
            exam.setDurationMinutes(null);
        } else {
            exam.setDurationMinutes(duration);
        }

        exam.setTotalMarks(rs.getDouble("total_marks"));

        double passingMarks = rs.getDouble("passing_marks");
        if (rs.wasNull()) {
            exam.setPassingMarks(null);
        } else {
            exam.setPassingMarks(passingMarks);
        }

        exam.setRoomName(rs.getString("room_name"));
        exam.setInstructions(rs.getString("instructions"));

        exam.setAllowNavigation(rs.getBoolean("allow_navigation"));
        exam.setShuffleQuestions(rs.getBoolean("shuffle_questions"));
        exam.setShuffleOptions(rs.getBoolean("shuffle_options"));

        exam.setStatus(rs.getString("status"));

        exam.setBatchName(rs.getString("batch_name"));
        exam.setBatchCode(rs.getString("batch_code"));

        exam.setCreatedAt(rs.getTimestamp("created_at"));
        exam.setUpdatedAt(rs.getTimestamp("updated_at"));

        return exam;
    }
}