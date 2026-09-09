package com.foxbrain.service;

import java.math.BigDecimal;
import java.util.List;

import com.foxbrain.dao.QuestionDAO;
import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;

import java.sql.*;

public class QuestionService {

    private final QuestionDAO questionDAO = new QuestionDAO();

    public List<Question> getAllQuestions() {
        return questionDAO.getAll();
    }

    public Question getQuestionById(long id) {

        if (id <= 0) {
            return null;
        }

        return questionDAO.getById(id);
    }

    public List<Question> getQuestionsByCourse(long courseId) {

        if (courseId <= 0) {
            return List.of();
        }

        return questionDAO.getByCourseId(courseId);
    }

    public List<Question> getQuestionsByType(String type) {

        if (type == null || type.trim().isEmpty()) {
            return List.of();
        }

        return questionDAO.getByType(type.trim().toUpperCase());
    }

    public boolean createQuestion(Question question) {

        if (!validateQuestion(question)) {
            return false;
        }

        if (question.getStatus() == null ||
            question.getStatus().trim().isEmpty()) {

            question.setStatus("ACTIVE");
        }

        question.setStatus(question.getStatus().toUpperCase());

        long id = questionDAO.create(question);

        if (id <= 0) {
            return false;
        }

        if (question.getOptions() != null) {

            for (QuestionOption option : question.getOptions()) {

                if (!validateOption(option)) {
                    continue;
                }

                option.setQuestionId(id);

                questionDAO.addOption(option);
            }
        }

        return true;
    }

    public boolean updateQuestion(Question question) {

        if (question == null || question.getId() <= 0) {
            return false;
        }

        if (!validateQuestion(question)) {
            return false;
        }

        if (question.getStatus() == null ||
            question.getStatus().trim().isEmpty()) {

            question.setStatus("ACTIVE");
        }

        question.setStatus(question.getStatus().toUpperCase());

        if (!questionDAO.update(question)) {
            return false;
        }

        if (question.getOptions() != null) {

            for (QuestionOption option : question.getOptions()) {

                if (!validateOption(option)) {
                    continue;
                }

                option.setQuestionId(question.getId());

                if (option.getId() > 0) {
                    questionDAO.updateOption(option);
                } else {
                    questionDAO.addOption(option);
                }
            }
        }

        return true;
    }

    public boolean deleteQuestion(long id) {

        if (id <= 0) {
            return false;
        }

        Question question = questionDAO.getById(id);

        if (question == null) {
            return false;
        }

        /*
         * DAO performs a soft delete by changing status to INACTIVE.
         * This protects historical exam records.
         */
        return questionDAO.delete(id);
    }

    public boolean addOption(QuestionOption option) {

        if (!validateOption(option)) {
            return false;
        }

        return questionDAO.addOption(option);
    }

    public boolean updateOption(QuestionOption option) {

        if (option == null || option.getId() <= 0) {
            return false;
        }

        if (!validateOption(option)) {
            return false;
        }

        return questionDAO.updateOption(option);
    }

    public boolean deleteOption(long optionId) {

        if (optionId <= 0) {
            return false;
        }

        return questionDAO.deleteOption(optionId);
    }

    public List<QuestionOption> getOptions(long questionId) {

        if (questionId <= 0) {
            return List.of();
        }

        return questionDAO.getOptions(questionId);
    }

    private boolean validateQuestion(Question question) {

        if (question == null) {
            return false;
        }

        if (question.getQuestionText() == null ||
            question.getQuestionText().trim().isEmpty()) {

            return false;
        }

        if (!isValidQuestionType(question.getQuestionType())) {
            return false;
        }

        if (!isValidDifficulty(question.getDifficulty())) {
            return false;
        }

        BigDecimal marks = question.getDefaultMarks();

        if (marks == null || marks.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal negativeMarks = question.getNegativeMarks();

        if (negativeMarks == null ||
            negativeMarks.compareTo(BigDecimal.ZERO) < 0) {

            return false;
        }

        if (question.getStatus() != null &&
            !isValidStatus(question.getStatus())) {

            return false;
        }

        return validateOptionsForType(question);
    }

    private boolean validateOptionsForType(Question question) {

        String type = question.getQuestionType().toUpperCase();

        if (type.equals("MCQ") ||
            type.equals("TRUE_FALSE")) {

            if (question.getOptions() == null ||
                question.getOptions().isEmpty()) {

                return false;
            }

            int correctCount = 0;

            for (QuestionOption option : question.getOptions()) {

                if (!validateOption(option)) {
                    return false;
                }

                if (option.isCorrect()) {
                    correctCount++;
                }
            }

            if (type.equals("MCQ")) {
                return correctCount >= 1;
            }

            if (type.equals("TRUE_FALSE")) {
                return correctCount == 1;
            }
        }

        return true;
    }

    private boolean validateOption(QuestionOption option) {

        if (option == null) {
            return false;
        }

        if (option.getQuestionId() < 0) {
            return false;
        }

        if (option.getOptionText() == null ||
            option.getOptionText().trim().isEmpty()) {

            return false;
        }

        if (option.getOptionOrder() <= 0) {
            return false;
        }

        return true;
    }

    private boolean isValidQuestionType(String type) {

        if (type == null) {
            return false;
        }

        String value = type.toUpperCase();

        return value.equals("MCQ")
                || value.equals("TRUE_FALSE")
                || value.equals("SHORT_ANSWER")
                || value.equals("LONG_ANSWER")
                || value.equals("CODING");
    }

    private boolean isValidDifficulty(String difficulty) {

        if (difficulty == null) {
            return false;
        }

        String value = difficulty.toUpperCase();

        return value.equals("EASY")
                || value.equals("MEDIUM")
                || value.equals("HARD");
    }

    private boolean isValidStatus(String status) {

        String value = status.toUpperCase();

        return value.equals("ACTIVE")
                || value.equals("INACTIVE");
    }
}