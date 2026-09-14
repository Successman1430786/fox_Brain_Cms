package com.foxbrain.dao;

import com.foxbrain.model.ExamAnswer;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamAnswerDAO {

    public long saveOrUpdate(ExamAnswer answer)
            throws SQLException {

        String sql =
                "INSERT INTO exam_answers " +
                "(attempt_id, exam_question_id, selected_option_id, " +
                "answer_text, answered_at) " +
                "VALUES (?, ?, ?, ?, NOW()) " +
                "ON DUPLICATE KEY UPDATE " +
                "selected_option_id=VALUES(selected_option_id), " +
                "answer_text=VALUES(answer_text), " +
                "answered_at=NOW()";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, answer.getAttemptId());
            ps.setLong(2, answer.getExamQuestionId());

            if (answer.getSelectedOptionId() != null)
                ps.setLong(3,
                        answer.getSelectedOptionId());
            else
                ps.setNull(3, Types.BIGINT);

            ps.setString(4, answer.getAnswerText());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        return 0;
    }

    public ExamAnswer getById(long id)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id=eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id=q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id=qo.id " +
                "WHERE ea.id=?";

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

    public List<ExamAnswer> getByAttemptId(
            long attemptId)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id=eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id=q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id=qo.id " +
                "WHERE ea.attempt_id=? " +
                "ORDER BY eq.question_order ASC";

        List<ExamAnswer> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    public ExamAnswer getByAttemptAndQuestion(
            long attemptId,
            long examQuestionId)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id=eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id=q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id=qo.id " +
                "WHERE ea.attempt_id=? " +
                "AND ea.exam_question_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);
            ps.setLong(2, examQuestionId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public boolean evaluate(
            long id,
            double marksObtained,
            boolean correct,
            String remarks)
            throws SQLException {

        String sql =
                "UPDATE exam_answers SET " +
                "marks_obtained=?, " +
                "is_correct=?, " +
                "evaluated=TRUE, " +
                "teacher_remarks=?, " +
                "evaluated_at=NOW() " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, marksObtained);
            ps.setBoolean(2, correct);
            ps.setString(3, remarks);
            ps.setLong(4, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean autoEvaluate(
            long answerId)
            throws SQLException {

        String sql =
                "UPDATE exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id=eq.id " +
                "INNER JOIN question_options qo " +
                "ON ea.selected_option_id=qo.id " +
                "SET " +
                "ea.marks_obtained = " +
                "CASE " +
                "WHEN qo.is_correct=TRUE THEN eq.marks " +
                "ELSE -eq.negative_marks " +
                "END, " +
                "ea.is_correct=qo.is_correct, " +
                "ea.evaluated=TRUE, " +
                "ea.evaluated_at=NOW() " +
                "WHERE ea.id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, answerId);

            return ps.executeUpdate() > 0;
        }
    }

    public List<ExamAnswer> getUnevaluatedByAttemptId(
            long attemptId)
            throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id=eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id=q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id=qo.id " +
                "WHERE ea.attempt_id=? " +
                "AND ea.evaluated=FALSE " +
                "ORDER BY eq.question_order ASC";

        List<ExamAnswer> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    private ExamAnswer mapRow(ResultSet rs)
            throws SQLException {

        ExamAnswer a = new ExamAnswer();

        a.setId(rs.getLong("id"));
        a.setAttemptId(
                rs.getLong("attempt_id"));

        a.setExamQuestionId(
                rs.getLong("exam_question_id"));

        long optionId =
                rs.getLong("selected_option_id");

        if (rs.wasNull())
            a.setSelectedOptionId(null);
        else
            a.setSelectedOptionId(optionId);

        a.setAnswerText(
                rs.getString("answer_text"));

        double marks =
                rs.getDouble("marks_obtained");

        if (rs.wasNull())
            a.setMarksObtained(null);
        else
            a.setMarksObtained(marks);

        boolean correct =
                rs.getBoolean("is_correct");

        if (rs.wasNull())
            a.setCorrect(null);
        else
            a.setCorrect(correct);

        a.setEvaluated(
                rs.getBoolean("evaluated"));

        a.setTeacherRemarks(
                rs.getString("teacher_remarks"));

        a.setAnsweredAt(
                rs.getTimestamp("answered_at"));

        a.setEvaluatedAt(
                rs.getTimestamp("evaluated_at"));

        a.setQuestionText(
                rs.getString("question_text"));

        a.setQuestionType(
                rs.getString("question_type"));

        a.setSelectedOptionText(
                rs.getString("selected_option_text"));

        return a;
    }
}