package com.foxbrain.service;

import com.foxbrain.dao.ExamQuestionDAO;
import com.foxbrain.model.ExamQuestion;

import java.sql.SQLException;
import java.util.List;

public class ExamQuestionService {

    private final ExamQuestionDAO examQuestionDAO;

    public ExamQuestionService() {
        this.examQuestionDAO = new ExamQuestionDAO();
    }

    // =====================================================
    // GET QUESTIONS OF EXAM
    // =====================================================

    public List<ExamQuestion> getQuestionsByExam(
            long examId) {

        validateId(examId);

        try {
            return examQuestionDAO.getByExamId(examId);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam questions.",
                    e
            );
        }
    }

    // =====================================================
    // GET EXAM QUESTION
    // =====================================================

    public ExamQuestion getById(long id) {

        validateId(id);

        try {
            return examQuestionDAO.getById(id);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam question.",
                    e
            );
        }
    }

    // =====================================================
    // ADD QUESTION TO EXAM
    // =====================================================

    public long addQuestion(
            ExamQuestion examQuestion) {

        if (examQuestion == null) {
            throw new IllegalArgumentException(
                    "Exam question cannot be null."
            );
        }

        validateId(examQuestion.getExamId());
        validateId(examQuestion.getQuestionId());

        if (examQuestion.getMarks() <= 0) {
            throw new IllegalArgumentException(
                    "Question marks must be greater than zero."
            );
        }

        if (examQuestion.getNegativeMarks() < 0) {
            throw new IllegalArgumentException(
                    "Negative marks cannot be negative."
            );
        }

        try {

            boolean exists =
                    examQuestionDAO.exists(
                            examQuestion.getExamId(),
                            examQuestion.getQuestionId()
                    );

            if (exists) {
                throw new IllegalArgumentException(
                        "This question is already added to the exam."
                );
            }

            if (examQuestion.getQuestionOrder() <= 0) {

                int nextOrder =
                        examQuestionDAO.getNextQuestionOrder(
                                examQuestion.getExamId()
                        );

                examQuestion.setQuestionOrder(nextOrder);
            }

            return examQuestionDAO.add(examQuestion);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to add question to exam.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE EXAM QUESTION
    // =====================================================

    public boolean updateQuestion(
            ExamQuestion examQuestion) {

        if (examQuestion == null) {
            throw new IllegalArgumentException(
                    "Exam question cannot be null."
            );
        }

        validateId(examQuestion.getId());

        if (examQuestion.getMarks() <= 0) {
            throw new IllegalArgumentException(
                    "Question marks must be greater than zero."
            );
        }

        if (examQuestion.getNegativeMarks() < 0) {
            throw new IllegalArgumentException(
                    "Negative marks cannot be negative."
            );
        }

        try {
            return examQuestionDAO.update(
                    examQuestion
            );

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to update exam question.",
                    e
            );
        }
    }

    // =====================================================
    // REMOVE QUESTION
    // =====================================================

    public boolean removeQuestion(long id) {

        validateId(id);

        try {
            return examQuestionDAO.remove(id);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to remove question from exam.",
                    e
            );
        }
    }

    // =====================================================
    // REMOVE ALL QUESTIONS
    // =====================================================

    public boolean removeAllQuestions(long examId) {

        validateId(examId);

        try {
            return examQuestionDAO.removeByExamId(
                    examId
            );

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to remove exam questions.",
                    e
            );
        }
    }

    // =====================================================
    // CHANGE QUESTION ORDER
    // =====================================================

    public boolean updateQuestionOrder(
            long id,
            int order) {

        validateId(id);

        if (order <= 0) {
            throw new IllegalArgumentException(
                    "Question order must be greater than zero."
            );
        }

        try {
            return examQuestionDAO.updateQuestionOrder(
                    id,
                    order
            );

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to reorder question.",
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