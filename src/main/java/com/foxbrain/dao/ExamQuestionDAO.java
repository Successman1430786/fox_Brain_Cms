package com.foxbrain.dao;

import com.foxbrain.model.ExamQuestion;
import com.foxbrain.model.Question;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionDAO {

    public List<ExamQuestion> getByExamId(long examId)
            throws SQLException {

        String sql =
                "SELECT eq.*, " +
                "q.question_text, q.question_type, q.difficulty, " +
                "q.default_marks, q.negative_marks AS q_negative_marks, " +
                "q.explanation, q.status " +
                "FROM exam_questions eq " +
                "INNER JOIN question_bank q ON eq.question_id=q.id " +
                "WHERE eq.exam_id=? " +
                "ORDER BY eq.question_order ASC, eq.id ASC";

        List<ExamQuestion> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ExamQuestion eq = new ExamQuestion();

                    eq.setId(rs.getLong("id"));
                    eq.setExamId(rs.getLong("exam_id"));
                    eq.setQuestionId(rs.getLong("question_id"));

                    eq.setQuestionOrder(
                            rs.getInt("question_order"));

                    eq.setMarks(
                            rs.getDouble("marks"));

                    eq.setNegativeMarks(
                            rs.getDouble("negative_marks"));

                    eq.setSectionName(
                            rs.getString("section_name"));

                    eq.setRequired(
                            rs.getBoolean("is_required"));

                    eq.setCreatedAt(
                            rs.getTimestamp("created_at"));

                    Question q = new Question();

                    q.setId(eq.getQuestionId());
                    q.setQuestionText(
                            rs.getString("question_text"));
                    q.setQuestionType(
                            rs.getString("question_type"));
                    q.setDifficulty(
                            rs.getString("difficulty"));
                    q.setDefaultMarks(
                            rs.getDouble("default_marks"));
                    q.setNegativeMarks(
                            rs.getDouble("q_negative_marks"));
                    q.setExplanation(
                            rs.getString("explanation"));
                    q.setStatus(
                            rs.getString("status"));

                    eq.setQuestion(q);

                    list.add(eq);
                }
            }
        }

        return list;
    }

    public ExamQuestion getById(long id)
            throws SQLException {

        String sql =
                "SELECT eq.*, " +
                "q.question_text, q.question_type, q.difficulty, " +
                "q.default_marks, q.negative_marks AS q_negative_marks, " +
                "q.explanation, q.status " +
                "FROM exam_questions eq " +
                "INNER JOIN question_bank q ON eq.question_id=q.id " +
                "WHERE eq.id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ExamQuestion eq = new ExamQuestion();

                    eq.setId(rs.getLong("id"));
                    eq.setExamId(rs.getLong("exam_id"));
                    eq.setQuestionId(rs.getLong("question_id"));

                    eq.setQuestionOrder(
                            rs.getInt("question_order"));

                    eq.setMarks(
                            rs.getDouble("marks"));

                    eq.setNegativeMarks(
                            rs.getDouble("negative_marks"));

                    eq.setSectionName(
                            rs.getString("section_name"));

                    eq.setRequired(
                            rs.getBoolean("is_required"));

                    eq.setCreatedAt(
                            rs.getTimestamp("created_at"));

                    Question q = new Question();

                    q.setId(eq.getQuestionId());
                    q.setQuestionText(
                            rs.getString("question_text"));
                    q.setQuestionType(
                            rs.getString("question_type"));
                    q.setDifficulty(
                            rs.getString("difficulty"));
                    q.setDefaultMarks(
                            rs.getDouble("default_marks"));
                    q.setNegativeMarks(
                            rs.getDouble("q_negative_marks"));
                    q.setExplanation(
                            rs.getString("explanation"));
                    q.setStatus(
                            rs.getString("status"));

                    eq.setQuestion(q);

                    return eq;
                }
            }
        }

        return null;
    }

    public long add(ExamQuestion eq)
            throws SQLException {

        String sql =
                "INSERT INTO exam_questions " +
                "(exam_id, question_id, question_order, marks, " +
                "negative_marks, section_name, is_required) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, eq.getExamId());
            ps.setLong(2, eq.getQuestionId());
            ps.setInt(3, eq.getQuestionOrder());
            ps.setDouble(4, eq.getMarks());
            ps.setDouble(5, eq.getNegativeMarks());

            ps.setString(6, eq.getSectionName());
            ps.setBoolean(7, eq.isRequired());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public boolean update(ExamQuestion eq)
            throws SQLException {

        String sql =
                "UPDATE exam_questions SET " +
                "question_order=?, marks=?, negative_marks=?, " +
                "section_name=?, is_required=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eq.getQuestionOrder());
            ps.setDouble(2, eq.getMarks());
            ps.setDouble(3, eq.getNegativeMarks());

            ps.setString(4, eq.getSectionName());
            ps.setBoolean(5, eq.isRequired());

            ps.setLong(6, eq.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean remove(long id)
            throws SQLException {

        String sql =
                "DELETE FROM exam_questions WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeByExamId(long examId)
            throws SQLException {

        String sql =
                "DELETE FROM exam_questions WHERE exam_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean exists(long examId, long questionId)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM exam_questions " +
                "WHERE exam_id=? AND question_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);
            ps.setLong(2, questionId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int getNextQuestionOrder(long examId)
            throws SQLException {

        String sql =
                "SELECT COALESCE(MAX(question_order),0)+1 " +
                "FROM exam_questions " +
                "WHERE exam_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 1;
    }

    public boolean updateQuestionOrder(
            long id,
            int order)
            throws SQLException {

        String sql =
                "UPDATE exam_questions " +
                "SET question_order=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, order);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean reorder(
            long examQuestionId,
            int newOrder)
            throws SQLException {

        return updateQuestionOrder(
                examQuestionId,
                newOrder);
    }
}