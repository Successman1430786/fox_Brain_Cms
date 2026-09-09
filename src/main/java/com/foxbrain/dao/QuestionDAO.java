package com.foxbrain.dao;

import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getAll() throws SQLException {

        List<Question> questions = new ArrayList<>();

        String sql =
                "SELECT q.*, c.name AS course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id = c.id " +
                "ORDER BY q.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Question question = mapRow(rs);
                question.setOptions(getOptions(con, question.getId()));
                questions.add(question);
            }
        }

        return questions;
    }

    public Question getById(long id) throws SQLException {

        String sql =
                "SELECT q.*, c.name AS course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id = c.id " +
                "WHERE q.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Question question = mapRow(rs);
                    question.setOptions(getOptions(con, id));

                    return question;
                }
            }
        }

        return null;
    }

    public long create(Question question) throws SQLException {

        String sql =
                "INSERT INTO question_bank (" +
                "course_id, question_text, question_type, difficulty, " +
                "default_marks, negative_marks, explanation, status" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            if (question.getCourseId() != null) {
                ps.setLong(1, question.getCourseId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }

            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getQuestionType());
            ps.setString(4, question.getDifficulty());

            ps.setBigDecimal(5, question.getDefaultMarks());
            ps.setBigDecimal(6, question.getNegativeMarks());

            ps.setString(7, question.getExplanation());
            ps.setString(8, question.getStatus());

            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Creating question failed.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException("Creating question failed. No ID returned.");
    }

    public boolean update(Question question) throws SQLException {

        String sql =
                "UPDATE question_bank SET " +
                "course_id = ?, " +
                "question_text = ?, " +
                "question_type = ?, " +
                "difficulty = ?, " +
                "default_marks = ?, " +
                "negative_marks = ?, " +
                "explanation = ?, " +
                "status = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (question.getCourseId() != null) {
                ps.setLong(1, question.getCourseId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }

            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getQuestionType());
            ps.setString(4, question.getDifficulty());

            ps.setBigDecimal(5, question.getDefaultMarks());
            ps.setBigDecimal(6, question.getNegativeMarks());

            ps.setString(7, question.getExplanation());
            ps.setString(8, question.getStatus());

            ps.setLong(9, question.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {

        String sql =
                "UPDATE question_bank " +
                "SET status = 'INACTIVE' " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean addOption(QuestionOption option) throws SQLException {

        String sql =
                "INSERT INTO question_options (" +
                "question_id, option_text, option_order, is_correct" +
                ") VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, option.getQuestionId());
            ps.setString(2, option.getOptionText());
            ps.setInt(3, option.getOptionOrder());
            ps.setBoolean(4, option.isCorrect());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateOption(QuestionOption option) throws SQLException {

        String sql =
                "UPDATE question_options SET " +
                "option_text = ?, " +
                "option_order = ?, " +
                "is_correct = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, option.getOptionText());
            ps.setInt(2, option.getOptionOrder());
            ps.setBoolean(3, option.isCorrect());
            ps.setLong(4, option.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteOption(long optionId) throws SQLException {

        String sql =
                "DELETE FROM question_options WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, optionId);

            return ps.executeUpdate() > 0;
        }
    }

    public List<QuestionOption> getOptions(long questionId) throws SQLException {

        try (Connection con = DBConnection.getConnection()) {
            return getOptions(con, questionId);
        }
    }

    private List<QuestionOption> getOptions(
            Connection con,
            long questionId) throws SQLException {

        List<QuestionOption> options = new ArrayList<>();

        String sql =
                "SELECT * FROM question_options " +
                "WHERE question_id = ? " +
                "ORDER BY option_order ASC, id ASC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    QuestionOption option = new QuestionOption();

                    option.setId(rs.getLong("id"));
                    option.setQuestionId(rs.getLong("question_id"));
                    option.setOptionText(rs.getString("option_text"));
                    option.setOptionOrder(rs.getInt("option_order"));
                    option.setCorrect(rs.getBoolean("is_correct"));

                    options.add(option);
                }
            }
        }

        return options;
    }

    public List<Question> getByCourseId(long courseId) throws SQLException {

        List<Question> questions = new ArrayList<>();

        String sql =
                "SELECT q.*, c.name AS course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id = c.id " +
                "WHERE q.course_id = ? " +
                "AND q.status = 'ACTIVE' " +
                "ORDER BY q.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Question question = mapRow(rs);

                    question.setOptions(
                            getOptions(con, question.getId())
                    );

                    questions.add(question);
                }
            }
        }

        return questions;
    }

    public List<Question> getByType(String questionType)
            throws SQLException {

        List<Question> questions = new ArrayList<>();

        String sql =
                "SELECT q.*, c.name AS course_name " +
                "FROM question_bank q " +
                "LEFT JOIN courses c ON q.course_id = c.id " +
                "WHERE q.question_type = ? " +
                "AND q.status = 'ACTIVE' " +
                "ORDER BY q.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, questionType);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Question question = mapRow(rs);

                    question.setOptions(
                            getOptions(con, question.getId())
                    );

                    questions.add(question);
                }
            }
        }

        return questions;
    }

    private Question mapRow(ResultSet rs) throws SQLException {

        Question question = new Question();

        question.setId(rs.getLong("id"));

        long courseId = rs.getLong("course_id");

        if (!rs.wasNull()) {
            question.setCourseId(courseId);
        }

        question.setQuestionText(rs.getString("question_text"));
        question.setQuestionType(rs.getString("question_type"));
        question.setDifficulty(rs.getString("difficulty"));

        question.setDefaultMarks(
                rs.getBigDecimal("default_marks")
        );

        question.setNegativeMarks(
                rs.getBigDecimal("negative_marks")
        );

        question.setExplanation(
                rs.getString("explanation")
        );

        question.setStatus(
                rs.getString("status")
        );

        question.setCourseName(
                rs.getString("course_name")
        );

        return question;
    }
}