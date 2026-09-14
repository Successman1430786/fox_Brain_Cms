package com.foxbrain.service;

import com.foxbrain.dao.QuestionDAO;
import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;

import java.sql.SQLException;
import java.util.List;

public class QuestionService {

    private final QuestionDAO questionDAO;

    public QuestionService() {
        questionDAO = new QuestionDAO();
    }

    // =====================================================
    // GET ALL QUESTIONS
    // =====================================================

    public List<Question> getAllQuestions() {
        try {
            return questionDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error loading questions", e);
        }
    }

    // =====================================================
    // GET QUESTION BY ID
    // =====================================================

    public Question getQuestionById(long id) {

        validateId(id);

        try {
            return questionDAO.getById(id);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to load question.",
                    e
            );
        }
    }

    // =====================================================
    // CREATE QUESTION
    // =====================================================

    public long createQuestion(Question question) {

        validateQuestion(question);

        try {
            return questionDAO.create(question);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to create question.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE QUESTION
    // =====================================================

    public void updateQuestion(Question question) {

        if (question == null) {
            throw new IllegalArgumentException(
                    "Question is required."
            );
        }

        validateId(question.getId());
        validateQuestion(question);

        try {
            questionDAO.update(question);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to update question.",
                    e
            );
        }
    }

    // =====================================================
    // DELETE QUESTION
    // =====================================================

    public void deleteQuestion(long id) {

        validateId(id);

        try {
            questionDAO.delete(id);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to delete question.",
                    e
            );
        }
    }

    // =====================================================
    // GET QUESTIONS BY COURSE
    // =====================================================

    public List<Question> getQuestionsByCourse(
            long courseId) {

        validateId(courseId);

        try {
            return questionDAO.getByCourseId(courseId);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to load course questions.",
                    e
            );
        }
    }

    // =====================================================
    // GET QUESTIONS BY TYPE
    // =====================================================

    public List<Question> getQuestionsByType(
            String questionType) {

        if (questionType == null ||
                questionType.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Question type is required."
            );
        }

        try {
            return questionDAO.getByType(
                    questionType
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to load questions.",
                    e
            );
        }
    }

    // =====================================================
    // OPTIONS
    // =====================================================

    public List<QuestionOption> getOptions(
            long questionId) {

        validateId(questionId);

        try {
            return questionDAO.getOptions(
                    questionId
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to load question options.",
                    e
            );
        }
    }

    // =====================================================
    // ADD OPTION
    // =====================================================

    public long addOption(
            QuestionOption option) {

        if (option == null) {
            throw new IllegalArgumentException(
                    "Option is required."
            );
        }

        validateId(option.getQuestionId());

        if (option.getOptionText() == null ||
                option.getOptionText()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "Option text is required."
            );
        }

        if (option.getOptionOrder() <= 0) {
            option.setOptionOrder(1);
        }

        try {
            return questionDAO.addOption(option);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to add question option.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE OPTION
    // =====================================================

    public void updateOption(
            QuestionOption option) {

        if (option == null) {
            throw new IllegalArgumentException(
                    "Option is required."
            );
        }

        validateId(option.getId());
        validateId(option.getQuestionId());

        if (option.getOptionText() == null ||
                option.getOptionText()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "Option text is required."
            );
        }

        try {
            questionDAO.updateOption(option);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to update question option.",
                    e
            );
        }
    }

    // =====================================================
    // DELETE OPTION
    // =====================================================

    public void deleteOption(long optionId) {

        validateId(optionId);

        try {
            questionDAO.deleteOption(optionId);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Unable to delete question option.",
                    e
            );
        }
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private void validateQuestion(
            Question question) {

        if (question == null) {
            throw new IllegalArgumentException(
                    "Question is required."
            );
        }

        if (question.getQuestionText() == null ||
                question.getQuestionText()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "Question text is required."
            );
        }

        if (question.getQuestionType() == null ||
                question.getQuestionType()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "Question type is required."
            );
        }

        if (question.getDifficulty() == null ||
                question.getDifficulty()
                        .trim()
                        .isEmpty()) {

            question.setDifficulty("MEDIUM");
        }

        if (question.getDefaultMarks() <= 0) {
            throw new IllegalArgumentException(
                    "Default marks must be greater than zero."
            );
        }

        if (question.getNegativeMarks() < 0) {
            throw new IllegalArgumentException(
                    "Negative marks cannot be negative."
            );
        }
    }

    private void validateId(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid ID."
            );
        }
    }
}