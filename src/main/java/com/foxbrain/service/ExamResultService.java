package com.foxbrain.service;

import java.math.BigDecimal;
import java.util.List;

import com.foxbrain.dao.ExamAttemptDAO;
import com.foxbrain.dao.ExamDAO;
import com.foxbrain.dao.ExamResultDAO;
import com.foxbrain.model.Exam;
import com.foxbrain.model.ExamAttempt;
import com.foxbrain.model.ExamResult;

public class ExamResultService {

    private final ExamResultDAO resultDAO =
            new ExamResultDAO();

    private final ExamAttemptDAO attemptDAO =
            new ExamAttemptDAO();

    private final ExamDAO examDAO =
            new ExamDAO();

    public List<ExamResult> getAllResults() {
        return resultDAO.getAll();
    }

    public ExamResult getResultById(long id) {

        if (id <= 0) {
            return null;
        }

        return resultDAO.getById(id);
    }

    public ExamResult getResult(long examId,
                                long studentId) {

        if (examId <= 0 || studentId <= 0) {
            return null;
        }

        return resultDAO.getByExamAndStudent(
                examId,
                studentId);
    }

    public List<ExamResult> getResultsByExam(long examId) {

        if (examId <= 0) {
            return List.of();
        }

        return resultDAO.getByExamId(examId);
    }

    public List<ExamResult> getResultsByStudent(
            long studentId) {

        if (studentId <= 0) {
            return List.of();
        }

        return resultDAO.getByStudentId(studentId);
    }

    public boolean createResult(ExamResult result) {

        if (!validateResult(result)) {
            return false;
        }

        if (resultDAO.exists(
                result.getExamId(),
                result.getStudentId())) {

            return false;
        }

        Exam exam =
                examDAO.getById(
                        result.getExamId());

        if (exam == null) {
            return false;
        }

        calculateResultStatus(
                result,
                exam);

        return resultDAO.create(result);
    }

    public boolean createResultFromAttempt(
            long attemptId) {

        if (attemptId <= 0) {
            return false;
        }

        ExamAttempt attempt =
                attemptDAO.getById(attemptId);

        if (attempt == null) {
            return false;
        }

        if (!"EVALUATED".equalsIgnoreCase(
                attempt.getStatus())) {

            return false;
        }

        if (resultDAO.exists(
                attempt.getExamId(),
                attempt.getStudentId())) {

            return false;
        }

        Exam exam =
                examDAO.getById(
                        attempt.getExamId());

        if (exam == null) {
            return false;
        }

        ExamResult result =
                new ExamResult();

        result.setExamId(
                attempt.getExamId());

        result.setStudentId(
                attempt.getStudentId());

        result.setMarksObtained(
                attempt.getObtainedMarks());

        calculateResultStatus(
                result,
                exam);

        return resultDAO.create(result);
    }

    public boolean updateResult(ExamResult result) {

        if (result == null ||
            result.getId() <= 0) {

            return false;
        }

        if (!validateResult(result)) {
            return false;
        }

        Exam exam =
                examDAO.getById(
                        result.getExamId());

        if (exam == null) {
            return false;
        }

        calculateResultStatus(
                result,
                exam);

        return resultDAO.update(result);
    }

    public boolean publishResult(long resultId) {

        if (resultId <= 0) {
            return false;
        }

        ExamResult result =
                resultDAO.getById(resultId);

        if (result == null) {
            return false;
        }

        return resultDAO.publish(resultId);
    }

    public boolean unpublishResult(long resultId) {

        if (resultId <= 0) {
            return false;
        }

        ExamResult result =
                resultDAO.getById(resultId);

        if (result == null) {
            return false;
        }

        return resultDAO.unpublish(resultId);
    }

    private void calculateResultStatus(
            ExamResult result,
            Exam exam) {

        BigDecimal obtained =
                result.getMarksObtained();

        BigDecimal passing =
                exam.getPassingMarks();

        if (obtained == null) {
            result.setResultStatus("WITHHELD");
            return;
        }

        if (passing == null) {
            /*
             * If passing_marks is NULL, do not
             * automatically mark PASS/FAIL.
             */
            result.setResultStatus("WITHHELD");
            return;
        }

        if (obtained.compareTo(passing) >= 0) {
            result.setResultStatus("PASS");
        } else {
            result.setResultStatus("FAIL");
        }

        result.setGrade(
                calculateGrade(
                        obtained,
                        exam.getTotalMarks()));
    }

    private String calculateGrade(
            BigDecimal obtained,
            BigDecimal totalMarks) {

        if (obtained == null ||
            totalMarks == null ||
            totalMarks.compareTo(BigDecimal.ZERO) <= 0) {

            return null;
        }

        BigDecimal percentage =
                obtained
                        .multiply(new BigDecimal("100"))
                        .divide(
                                totalMarks,
                                2,
                                java.math.RoundingMode.HALF_UP);

        if (percentage.compareTo(
                new BigDecimal("90")) >= 0) {

            return "A+";

        } else if (percentage.compareTo(
                new BigDecimal("80")) >= 0) {

            return "A";

        } else if (percentage.compareTo(
                new BigDecimal("70")) >= 0) {

            return "B";

        } else if (percentage.compareTo(
                new BigDecimal("60")) >= 0) {

            return "C";

        } else if (percentage.compareTo(
                new BigDecimal("50")) >= 0) {

            return "D";

        } else {

            return "F";
        }
    }

    private boolean validateResult(
            ExamResult result) {

        if (result == null) {
            return false;
        }

        if (result.getExamId() <= 0) {
            return false;
        }

        if (result.getStudentId() <= 0) {
            return false;
        }

        if (result.getMarksObtained() == null) {
            return false;
        }

        if (result.getMarksObtained()
                .compareTo(BigDecimal.ZERO) < 0) {

            return false;
        }

        if (!isValidResultStatus(
                result.getResultStatus())) {

            return false;
        }

        return true;
    }

    private boolean isValidResultStatus(
            String status) {

        if (status == null ||
            status.trim().isEmpty()) {

            return true;
        }

        String value =
                status.trim().toUpperCase();

        return value.equals("PASS")
                || value.equals("FAIL")
                || value.equals("ABSENT")
                || value.equals("WITHHELD");
    }
}