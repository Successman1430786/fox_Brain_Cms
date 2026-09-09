package com.foxbrain.service;

import java.math.BigDecimal;
import java.util.List;

import com.foxbrain.dao.ExamQuestionDAO;
import com.foxbrain.model.ExamQuestion;

public class ExamQuestionService {

    private final ExamQuestionDAO examQuestionDAO = new ExamQuestionDAO();

    public List<ExamQuestion> getQuestionsByExam(long examId) {

        if (examId <= 0) {
            return List.of();
        }

        return examQuestionDAO.getByExamId(examId);
    }

    public ExamQuestion getById(long id) {

        if (id <= 0) {
            return null;
        }

        return examQuestionDAO.getById(id);
    }

    public boolean addQuestionToExam(ExamQuestion examQuestion) {

        if (!validate(examQuestion)) {
            return false;
        }

        if (examQuestionDAO.exists(
                examQuestion.getExamId(),
                examQuestion.getQuestionId())) {

            return false;
        }

        if (examQuestion.getQuestionOrder() <= 0) {

            examQuestion.setQuestionOrder(
                    examQuestionDAO.getNextQuestionOrder(
                            examQuestion.getExamId()));
        }

        return examQuestionDAO.add(examQuestion);
    }

    public boolean updateExamQuestion(ExamQuestion examQuestion) {

        if (examQuestion == null || examQuestion.getId() <= 0) {
            return false;
        }

        if (!validate(examQuestion)) {
            return false;
        }

        return examQuestionDAO.update(examQuestion);
    }

    public boolean removeQuestionFromExam(long id) {

        if (id <= 0) {
            return false;
        }

        return examQuestionDAO.remove(id);
    }

    public boolean removeAllQuestions(long examId) {

        if (examId <= 0) {
            return false;
        }

        return examQuestionDAO.removeByExamId(examId);
    }

    public boolean reorderQuestion(long examQuestionId,
                                   int newOrder) {

        if (examQuestionId <= 0 || newOrder <= 0) {
            return false;
        }

        ExamQuestion question =
                examQuestionDAO.getById(examQuestionId);

        if (question == null) {
            return false;
        }

        return examQuestionDAO.reorder(
                examQuestionId,
                newOrder);
    }

    public boolean updateQuestionOrder(long examQuestionId,
                                       int questionOrder) {

        if (examQuestionId <= 0 ||
            questionOrder <= 0) {

            return false;
        }

        return examQuestionDAO.updateQuestionOrder(
                examQuestionId,
                questionOrder);
    }

    private boolean validate(ExamQuestion examQuestion) {

        if (examQuestion == null) {
            return false;
        }

        if (examQuestion.getExamId() <= 0) {
            return false;
        }

        if (examQuestion.getQuestionId() <= 0) {
            return false;
        }

        BigDecimal marks = examQuestion.getMarks();

        if (marks == null ||
            marks.compareTo(BigDecimal.ZERO) <= 0) {

            return false;
        }

        BigDecimal negativeMarks =
                examQuestion.getNegativeMarks();

        if (negativeMarks == null ||
            negativeMarks.compareTo(BigDecimal.ZERO) < 0) {

            return false;
        }

        if (examQuestion.getQuestionOrder() < 0) {
            return false;
        }

        return true;
    }
}