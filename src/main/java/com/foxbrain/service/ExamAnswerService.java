package com.foxbrain.service;

import java.math.BigDecimal;
import java.util.List;

import com.foxbrain.dao.ExamAnswerDAO;
import com.foxbrain.dao.ExamAttemptDAO;
import com.foxbrain.dao.ExamQuestionDAO;
import com.foxbrain.model.ExamAnswer;
import com.foxbrain.model.ExamAttempt;
import com.foxbrain.model.ExamQuestion;
import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;

public class ExamAnswerService {

    private final ExamAnswerDAO answerDAO = new ExamAnswerDAO();
    private final ExamAttemptDAO attemptDAO = new ExamAttemptDAO();
    private final ExamQuestionDAO examQuestionDAO =
            new ExamQuestionDAO();

    public ExamAnswer getAnswerById(long id) {

        if (id <= 0) {
            return null;
        }

        return answerDAO.getById(id);
    }

    public List<ExamAnswer> getAnswersByAttempt(long attemptId) {

        if (attemptId <= 0) {
            return List.of();
        }

        return answerDAO.getByAttemptId(attemptId);
    }

    public ExamAnswer getAnswer(long attemptId,
                                long examQuestionId) {

        if (attemptId <= 0 || examQuestionId <= 0) {
            return null;
        }

        return answerDAO.getByAttemptAndQuestion(
                attemptId,
                examQuestionId);
    }

    public boolean saveAnswer(ExamAnswer answer) {

        if (!validateAnswer(answer)) {
            return false;
        }

        ExamAttempt attempt =
                attemptDAO.getById(
                        answer.getAttemptId());

        if (attempt == null) {
            return false;
        }

        if (!"IN_PROGRESS".equalsIgnoreCase(
                attempt.getStatus())) {

            return false;
        }

        ExamQuestion examQuestion =
                examQuestionDAO.getById(
                        answer.getExamQuestionId());

        if (examQuestion == null) {
            return false;
        }

        /*
         * Make sure the selected question belongs
         * to the current exam attempt.
         */
        if (examQuestion.getExamId() !=
            attempt.getExamId()) {

            return false;
        }

        answer.setAnsweredAt(
                new java.sql.Timestamp(
                        System.currentTimeMillis()));

        /*
         * Objective questions can be automatically evaluated.
         */
        if (isObjectiveQuestion(examQuestion)) {

            autoEvaluateAnswer(
                    answer,
                    examQuestion);
        }

        return answerDAO.saveOrUpdate(answer);
    }

    public boolean autoEvaluateAnswer(ExamAnswer answer) {

        if (answer == null ||
            answer.getAttemptId() <= 0 ||
            answer.getExamQuestionId() <= 0) {

            return false;
        }

        ExamQuestion examQuestion =
                examQuestionDAO.getById(
                        answer.getExamQuestionId());

        if (examQuestion == null) {
            return false;
        }

        autoEvaluateAnswer(
                answer,
                examQuestion);

        return answerDAO.saveOrUpdate(answer);
    }

    private void autoEvaluateAnswer(
            ExamAnswer answer,
            ExamQuestion examQuestion) {

        Question question =
                examQuestion.getQuestion();

        if (question == null) {
            return;
        }

        String type =
                question.getQuestionType();

        if (type == null) {
            return;
        }

        /*
         * MCQ and TRUE_FALSE can be automatically evaluated.
         */
        if (!type.equalsIgnoreCase("MCQ") &&
            !type.equalsIgnoreCase("TRUE_FALSE")) {

            return;
        }

        Long selectedOptionId =
                answer.getSelectedOptionId();

        if (selectedOptionId == null ||
            selectedOptionId <= 0) {

            answer.setCorrect(false);
            answer.setMarksObtained(
                    BigDecimal.ZERO);
            answer.setEvaluated(true);
            return;
        }

        List<QuestionOption> options =
                question.getOptions();

        if (options == null) {
            return;
        }

        boolean correct = false;

        for (QuestionOption option : options) {

            if (option.getId() == selectedOptionId) {
                correct = option.isCorrect();
                break;
            }
        }

        answer.setCorrect(correct);
        answer.setEvaluated(true);

        if (correct) {

            answer.setMarksObtained(
                    examQuestion.getMarks());

        } else {

            BigDecimal negative =
                    examQuestion.getNegativeMarks();

            if (negative != null &&
                negative.compareTo(BigDecimal.ZERO) > 0) {

                answer.setMarksObtained(
                        negative.negate());

            } else {

                answer.setMarksObtained(
                        BigDecimal.ZERO);
            }
        }

        answer.setEvaluatedAt(
                new java.sql.Timestamp(
                        System.currentTimeMillis()));
    }

    public boolean evaluateAnswer(
            long answerId,
            BigDecimal marksObtained,
            Boolean correct,
            String teacherRemarks) {

        if (answerId <= 0) {
            return false;
        }

        if (marksObtained == null) {
            return false;
        }

        if (marksObtained.compareTo(
                BigDecimal.ZERO) < 0) {

            return false;
        }

        return answerDAO.evaluate(
                answerId,
                marksObtained,
                correct,
                teacherRemarks);
    }

    public List<ExamAnswer> getUnevaluatedAnswers(
            long attemptId) {

        if (attemptId <= 0) {
            return List.of();
        }

        return answerDAO.getUnevaluatedByAttemptId(
                attemptId);
    }

    private boolean validateAnswer(ExamAnswer answer) {

        if (answer == null) {
            return false;
        }

        if (answer.getAttemptId() <= 0) {
            return false;
        }

        if (answer.getExamQuestionId() <= 0) {
            return false;
        }

        /*
         * At least one answer value should be supplied.
         */
        boolean hasOption =
                answer.getSelectedOptionId() != null &&
                answer.getSelectedOptionId() > 0;

        boolean hasText =
                answer.getAnswerText() != null &&
                !answer.getAnswerText().trim().isEmpty();

        return hasOption || hasText;
    }

    private boolean isObjectiveQuestion(
            ExamQuestion examQuestion) {

        if (examQuestion.getQuestion() == null ||
            examQuestion.getQuestion().getQuestionType() == null) {

            return false;
        }

        String type =
                examQuestion.getQuestion()
                        .getQuestionType();

        return type.equalsIgnoreCase("MCQ")
                || type.equalsIgnoreCase("TRUE_FALSE");
    }
}