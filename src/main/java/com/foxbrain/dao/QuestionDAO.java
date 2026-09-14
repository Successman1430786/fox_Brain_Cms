package com.foxbrain.dao;

import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getAll() throws SQLException {

        String sql =
                "SELECT q.*, c.course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id=c.id " +
                "ORDER BY q.id DESC";

        List<Question> questions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Question q = mapRow(rs);
                q.setOptions(getOptions(q.getId()));
                questions.add(q);
            }
        }

        return questions;
    }

    public Question getById(long id) throws SQLException {

        String sql =
                "SELECT q.*, c.course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id=c.id " +
                "WHERE q.id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Question q = mapRow(rs);
                    q.setOptions(getOptions(q.getId()));

                    return q;
                }
            }
        }

        return null;
    }

    public long create(Question q) throws SQLException {

        String sql =
                "INSERT INTO question_bank " +
                "(course_id, question_text, question_type, difficulty, " +
                "default_marks, negative_marks, explanation, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (q.getCourseId() != null)
                ps.setLong(1, q.getCourseId());
            else
                ps.setNull(1, Types.BIGINT);

            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getQuestionType());
            ps.setString(4, q.getDifficulty());

            ps.setDouble(5, q.getDefaultMarks());
            ps.setDouble(6, q.getNegativeMarks());

            ps.setString(7, q.getExplanation());
            ps.setString(8, q.getStatus());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(Question q) throws SQLException {

        String sql =
                "UPDATE question_bank SET " +
                "course_id=?, question_text=?, question_type=?, " +
                "difficulty=?, default_marks=?, negative_marks=?, " +
                "explanation=?, status=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (q.getCourseId() != null)
                ps.setLong(1, q.getCourseId());
            else
                ps.setNull(1, Types.BIGINT);

            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getQuestionType());
            ps.setString(4, q.getDifficulty());

            ps.setDouble(5, q.getDefaultMarks());
            ps.setDouble(6, q.getNegativeMarks());

            ps.setString(7, q.getExplanation());
            ps.setString(8, q.getStatus());

            ps.setLong(9, q.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql =
                "UPDATE question_bank " +
                "SET status='INACTIVE' " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public long addOption(QuestionOption option) throws SQLException {

        String sql =
                "INSERT INTO question_options " +
                "(question_id, option_text, option_order, is_correct) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, option.getQuestionId());
            ps.setString(2, option.getOptionText());
            ps.setInt(3, option.getOptionOrder());
            ps.setBoolean(4, option.isCorrect());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean updateOption(QuestionOption option) throws SQLException {

        String sql =
                "UPDATE question_options SET " +
                "option_text=?, option_order=?, is_correct=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, option.getOptionText());
            ps.setInt(2, option.getOptionOrder());
            ps.setBoolean(3, option.isCorrect());
            ps.setLong(4, option.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteOption(long id) throws SQLException {

        String sql = "DELETE FROM question_options WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public List<QuestionOption> getOptions(long questionId)
            throws SQLException {

        String sql =
                "SELECT * FROM question_options " +
                "WHERE question_id=? " +
                "ORDER BY option_order ASC, id ASC";

        List<QuestionOption> options = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    QuestionOption option = new QuestionOption();

                    option.setId(rs.getLong("id"));
                    option.setQuestionId(
                            rs.getLong("question_id"));

                    option.setOptionText(
                            rs.getString("option_text"));

                    option.setOptionOrder(
                            rs.getInt("option_order"));

                    option.setCorrect(
                            rs.getBoolean("is_correct"));

                    option.setCreatedAt(
                            rs.getTimestamp("created_at"));

                    options.add(option);
                }
            }
        }

        return options;
    }

    public List<Question> getByCourseId(long courseId)
            throws SQLException {

        String sql =
                "SELECT q.*, c.course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id=c.id " +
                "WHERE q.course_id=? " +
                "ORDER BY q.id DESC";

        List<Question> questions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    questions.add(mapRow(rs));
                }
            }
        }

        return questions;
    }

    public List<Question> getByType(String type)
            throws SQLException {

        String sql =
                "SELECT q.*, c.course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id=c.id " +
                "WHERE q.question_type=? " +
                "ORDER BY q.id DESC";

        List<Question> questions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    questions.add(mapRow(rs));
                }
            }
        }

        return questions;
    }

    private Question mapRow(ResultSet rs)
            throws SQLException {

        Question q = new Question();

        q.setId(rs.getLong("id"));

        long courseId = rs.getLong("course_id");

        if (rs.wasNull()) {
            q.setCourseId(null);
        } else {
            q.setCourseId(courseId);
        }

        q.setQuestionText(
                rs.getString("question_text"));

        q.setQuestionType(
                rs.getString("question_type"));

        q.setDifficulty(
                rs.getString("difficulty"));

        q.setDefaultMarks(
                rs.getDouble("default_marks"));

        q.setNegativeMarks(
                rs.getDouble("negative_marks"));

        q.setExplanation(
                rs.getString("explanation"));

        q.setStatus(
                rs.getString("status"));

        q.setCourseName(
                rs.getString("course_name"));

        return q;
    }
}