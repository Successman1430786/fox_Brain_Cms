package com.foxbrain.service;

import com.foxbrain.dao.ExamAnswerDAO;
import com.foxbrain.model.ExamAnswer;

import java.util.List;

public class ExamAnswerService {

    private final ExamAnswerDAO answerDAO;

    public ExamAnswerService() {
        this.answerDAO = new ExamAnswerDAO();
    }

    // =====================================================
    // SAVE ANSWER
    // =====================================================

    public long saveAnswer(
            ExamAnswer answer) {

        if (answer == null) {
            throw new IllegalArgumentException(
                    "Answer cannot be null."
            );
        }

        if (answer.getAttemptId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid attempt."
            );
        }

        if (answer.getExamQuestionId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid exam question."
            );
        }

        try {

            return answerDAO.saveOrUpdate(
                    answer
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to save answer.",
                    e
            );
        }
    }

    // =====================================================
    // GET ANSWER
    // =====================================================

    public ExamAnswer getAnswer(long id) {

        validateId(id);

        try {
            return answerDAO.getById(id);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load answer.",
                    e
            );
        }
    }

    // =====================================================
    // GET ATTEMPT ANSWERS
    // =====================================================

    public List<ExamAnswer> getAttemptAnswers(
            long attemptId) {

        validateId(attemptId);

        try {
            return answerDAO.getByAttemptId(
                    attemptId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load answers.",
                    e
            );
        }
    }

    // =====================================================
    // GET ANSWER FOR QUESTION
    // =====================================================

    public ExamAnswer getAnswerForQuestion(
            long attemptId,
            long examQuestionId) {

        validateId(attemptId);
        validateId(examQuestionId);

        try {
            return answerDAO.getByAttemptAndQuestion(
                    attemptId,
                    examQuestionId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load answer.",
                    e
            );
        }
    }

    // =====================================================
    // AUTO EVALUATE
    // =====================================================

    public boolean autoEvaluate(
            long answerId) {

        validateId(answerId);

        try {
            return answerDAO.autoEvaluate(
                    answerId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to auto evaluate answer.",
                    e
            );
        }
    }

    // =====================================================
    // TEACHER EVALUATION
    // =====================================================

    public boolean evaluateAnswer(
            long answerId,
            double marks,
            boolean correct,
            String remarks) {

        validateId(answerId);

        if (marks < 0) {
            throw new IllegalArgumentException(
                    "Marks cannot be negative."
            );
        }

        try {

            return answerDAO.evaluate(
                    answerId,
                    marks,
                    correct,
                    remarks
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to evaluate answer.",
                    e
            );
        }
    }

    // =====================================================
    // UNEVALUATED ANSWERS
    // =====================================================

    public List<ExamAnswer> getUnevaluatedAnswers(
            long attemptId) {

        validateId(attemptId);

        try {

            return answerDAO
                    .getUnevaluatedByAttemptId(
                            attemptId
                    );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load unevaluated answers.",
                    e
            );
        }
    }

    // =====================================================
    // VALIDATE ID
    // =====================================================

    private void validateId(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid ID."
            );
        }
    }
}