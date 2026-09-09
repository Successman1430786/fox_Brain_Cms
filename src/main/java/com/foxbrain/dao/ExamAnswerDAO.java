package com.foxbrain.dao;

import com.foxbrain.model.ExamAnswer;
import com.foxbrain.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamAnswerDAO {

    public boolean saveOrUpdate(ExamAnswer answer)
            throws SQLException {

        String sql =
                "INSERT INTO exam_answers (" +
                "attempt_id, exam_question_id, selected_option_id, " +
                "answer_text, answered_at" +
                ") VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP) " +
                "ON DUPLICATE KEY UPDATE " +
                "selected_option_id = VALUES(selected_option_id), " +
                "answer_text = VALUES(answer_text), " +
                "answered_at = CURRENT_TIMESTAMP";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, answer.getAttemptId());
            ps.setLong(2, answer.getExamQuestionId());

            if (answer.getSelectedOptionId() != null) {
                ps.setLong(3, answer.getSelectedOptionId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }

            ps.setString(4, answer.getAnswerText());

            return ps.executeUpdate() > 0;
        }
    }

    public ExamAnswer getById(long id) throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id = eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id = qo.id " +
                "WHERE ea.id = ?";

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
            long attemptId) throws SQLException {

        List<ExamAnswer> answers = new ArrayList<>();

        String sql =
                "SELECT ea.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id = eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id = qo.id " +
                "WHERE ea.attempt_id = ? " +
                "ORDER BY eq.question_order ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    answers.add(mapRow(rs));
                }
            }
        }

        return answers;
    }

    public ExamAnswer getByAttemptAndQuestion(
            long attemptId,
            long examQuestionId) throws SQLException {

        String sql =
                "SELECT ea.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id = eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id = qo.id " +
                "WHERE ea.attempt_id = ? " +
                "AND ea.exam_question_id = ?";

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
            long answerId,
            java.math.BigDecimal marksObtained,
            Boolean correct,
            String teacherRemarks)
            throws SQLException {

        String sql =
                "UPDATE exam_answers SET " +
                "marks_obtained = ?, " +
                "is_correct = ?, " +
                "evaluated = TRUE, " +
                "teacher_remarks = ?, " +
                "evaluated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (marksObtained != null) {
                ps.setBigDecimal(1, marksObtained);
            } else {
                ps.setNull(1, Types.DECIMAL);
            }

            if (correct != null) {
                ps.setBoolean(2, correct);
            } else {
                ps.setNull(2, Types.BOOLEAN);
            }

            ps.setString(3, teacherRemarks);
            ps.setLong(4, answerId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean autoEvaluate(
            long answerId,
            java.math.BigDecimal marksObtained,
            boolean correct)
            throws SQLException {

        String sql =
                "UPDATE exam_answers SET " +
                "marks_obtained = ?, " +
                "is_correct = ?, " +
                "evaluated = TRUE, " +
                "evaluated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, marksObtained);
            ps.setBoolean(2, correct);
            ps.setLong(3, answerId);

            return ps.executeUpdate() > 0;
        }
    }

    public List<ExamAnswer> getUnevaluatedByAttemptId(
            long attemptId) throws SQLException {

        List<ExamAnswer> answers = new ArrayList<>();

        String sql =
                "SELECT ea.*, " +
                "q.question_text, " +
                "q.question_type, " +
                "qo.option_text AS selected_option_text " +
                "FROM exam_answers ea " +
                "INNER JOIN exam_questions eq " +
                "ON ea.exam_question_id = eq.id " +
                "INNER JOIN question_bank q " +
                "ON eq.question_id = q.id " +
                "LEFT JOIN question_options qo " +
                "ON ea.selected_option_id = qo.id " +
                "WHERE ea.attempt_id = ? " +
                "AND ea.evaluated = FALSE " +
                "ORDER BY eq.question_order ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, attemptId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    answers.add(mapRow(rs));
                }
            }
        }

        return answers;
    }

    private ExamAnswer mapRow(ResultSet rs)
            throws SQLException {

        ExamAnswer answer = new ExamAnswer();

        answer.setId(rs.getLong("id"));
        answer.setAttemptId(rs.getLong("attempt_id"));
        answer.setExamQuestionId(
                rs.getLong("exam_question_id")
        );

        long optionId = rs.getLong("selected_option_id");

        if (!rs.wasNull()) {
            answer.setSelectedOptionId(optionId);
        }

        answer.setAnswerText(
                rs.getString("answer_text")
        );

        answer.setMarksObtained(
                rs.getBigDecimal("marks_obtained")
        );

        boolean correctValue = rs.getBoolean("is_correct");

        if (!rs.wasNull()) {
            answer.setCorrect(correctValue);
        }

        answer.setEvaluated(
                rs.getBoolean("evaluated")
        );

        answer.setTeacherRemarks(
                rs.getString("teacher_remarks")
        );

        Timestamp answeredAt = rs.getTimestamp("answered_at");

        if (answeredAt != null) {
            answer.setAnsweredAt(
                    answeredAt.toLocalDateTime()
            );
        }

        Timestamp evaluatedAt = rs.getTimestamp("evaluated_at");

        if (evaluatedAt != null) {
            answer.setEvaluatedAt(
                    evaluatedAt.toLocalDateTime()
            );
        }

        answer.setQuestionText(
                rs.getString("question_text")
        );

        answer.setQuestionType(
                rs.getString("question_type")
        );

        answer.setSelectedOptionText(
                rs.getString("selected_option_text")
        );

        return answer;
    }
}