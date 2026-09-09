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

        List<ExamQuestion> questions = new ArrayList<>();

        String sql =
                "SELECT eq.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "q.difficulty, " +
                "q.default_marks, " +
                "q.negative_marks, " +
                "q.explanation, " +
                "q.status AS question_status " +
                "FROM exam_questions eq " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "WHERE eq.exam_id = ? " +
                "ORDER BY eq.question_order ASC, eq.id ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    questions.add(mapRow(rs));
                }
            }
        }

        return questions;
    }

    public ExamQuestion getById(long id) throws SQLException {

        String sql =
                "SELECT eq.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "q.difficulty, " +
                "q.default_marks, " +
                "q.negative_marks, " +
                "q.explanation, " +
                "q.status AS question_status " +
                "FROM exam_questions eq " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "WHERE eq.id = ?";

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

    public boolean add(ExamQuestion examQuestion)
            throws SQLException {

        String sql =
                "INSERT INTO exam_questions (" +
                "exam_id, question_id, question_order, " +
                "marks, negative_marks, section_name, is_required" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examQuestion.getExamId());
            ps.setLong(2, examQuestion.getQuestionId());
            ps.setInt(3, examQuestion.getQuestionOrder());

            ps.setBigDecimal(4, examQuestion.getMarks());
            ps.setBigDecimal(5, examQuestion.getNegativeMarks());

            ps.setString(6, examQuestion.getSectionName());

            ps.setBoolean(7, examQuestion.isRequired());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(ExamQuestion examQuestion)
            throws SQLException {

        String sql =
                "UPDATE exam_questions SET " +
                "question_id = ?, " +
                "question_order = ?, " +
                "marks = ?, " +
                "negative_marks = ?, " +
                "section_name = ?, " +
                "is_required = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examQuestion.getQuestionId());
            ps.setInt(2, examQuestion.getQuestionOrder());

            ps.setBigDecimal(3, examQuestion.getMarks());
            ps.setBigDecimal(4, examQuestion.getNegativeMarks());

            ps.setString(5, examQuestion.getSectionName());

            ps.setBoolean(6, examQuestion.isRequired());

            ps.setLong(7, examQuestion.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean remove(long id) throws SQLException {

        String sql =
                "DELETE FROM exam_questions WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeByExamId(long examId)
            throws SQLException {

        String sql =
                "DELETE FROM exam_questions WHERE exam_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, examId);

            return ps.executeUpdate() >= 0;
        }
    }

    public boolean exists(long examId, long questionId)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM exam_questions " +
                "WHERE exam_id = ? AND question_id = ?";

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
                "SELECT COALESCE(MAX(question_order), 0) + 1 " +
                "FROM exam_questions " +
                "WHERE exam_id = ?";

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
            int questionOrder) throws SQLException {

        String sql =
                "UPDATE exam_questions " +
                "SET question_order = ? " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionOrder);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean reorder(
            long examQuestionId,
            int newOrder) throws SQLException {

        return updateQuestionOrder(
                examQuestionId,
                newOrder
        );
    }

    private ExamQuestion mapRow(ResultSet rs)
            throws SQLException {

        ExamQuestion examQuestion = new ExamQuestion();

        examQuestion.setId(rs.getLong("id"));
        examQuestion.setExamId(rs.getLong("exam_id"));
        examQuestion.setQuestionId(rs.getLong("question_id"));

        examQuestion.setQuestionOrder(
                rs.getInt("question_order")
        );

        examQuestion.setMarks(
                rs.getBigDecimal("marks")
        );

        examQuestion.setNegativeMarks(
                rs.getBigDecimal("negative_marks")
        );

        examQuestion.setSectionName(
                rs.getString("section_name")
        );

        examQuestion.setRequired(
                rs.getBoolean("is_required")
        );

        Question question = new Question();

        question.setId(
                rs.getLong("question_id")
        );

        question.setQuestionText(
                rs.getString("question_text")
        );

        question.setQuestionType(
                rs.getString("question_type")
        );

        question.setDifficulty(
                rs.getString("difficulty")
        );

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
                rs.getString("question_status")
        );

        examQuestion.setQuestion(question);

        return examQuestion;
    }
}