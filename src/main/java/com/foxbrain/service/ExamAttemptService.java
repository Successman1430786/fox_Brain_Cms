package com.foxbrain.service;

import java.math.BigDecimal;
import java.util.List;

import com.foxbrain.dao.ExamAttemptDAO;
import com.foxbrain.dao.ExamDAO;
import com.foxbrain.model.Exam;
import com.foxbrain.model.ExamAttempt;

public class ExamAttemptService {

    private final ExamAttemptDAO attemptDAO = new ExamAttemptDAO();
    private final ExamDAO examDAO = new ExamDAO();

    public ExamAttempt getAttemptById(long id) {

        if (id <= 0) {
            return null;
        }

        return attemptDAO.getById(id);
    }

    public List<ExamAttempt> getAttemptsByExam(long examId) {

        if (examId <= 0) {
            return List.of();
        }

        return attemptDAO.getByExamId(examId);
    }

    public List<ExamAttempt> getAttemptsByStudent(long studentId) {

        if (studentId <= 0) {
            return List.of();
        }

        return attemptDAO.getByStudentId(studentId);
    }

    public ExamAttempt getActiveAttempt(long examId,
                                        long studentId) {

        if (examId <= 0 || studentId <= 0) {
            return null;
        }

        return attemptDAO.getActiveAttempt(
                examId,
                studentId);
    }

    public ExamAttempt startAttempt(long examId,
                                    long studentId) {

        if (examId <= 0 || studentId <= 0) {
            return null;
        }

        Exam exam = examDAO.getById(examId);

        if (exam == null) {
            return null;
        }

        /*
         * Attempts are only for ONLINE exams.
         */
        if (exam.getExamMode() == null ||
            !exam.getExamMode().equalsIgnoreCase("ONLINE")) {

            return null;
        }

        /*
         * Exam must be scheduled.
         */
        if (exam.getStatus() == null ||
            !exam.getStatus().equalsIgnoreCase("SCHEDULED")) {

            return null;
        }

        /*
         * Do not create duplicate active attempts.
         */
        ExamAttempt active =
                attemptDAO.getActiveAttempt(
                        examId,
                        studentId);

        if (active != null) {
            return active;
        }

        int attemptNumber =
                attemptDAO.getNextAttemptNumber(
                        examId,
                        studentId);

        ExamAttempt attempt = new ExamAttempt();

        attempt.setExamId(examId);
        attempt.setStudentId(studentId);
        attempt.setAttemptNumber(attemptNumber);
        attempt.setAutoSubmitted(false);
        attempt.setStatus("IN_PROGRESS");

        /*
         * DAO create implementation should set started_at.
         */
        long id = attemptDAO.create(attempt);

        if (id <= 0) {
            return null;
        }

        return attemptDAO.getById(id);
    }

    public boolean submitAttempt(long attemptId) {

        if (attemptId <= 0) {
            return false;
        }

        ExamAttempt attempt =
                attemptDAO.getById(attemptId);

        if (attempt == null) {
            return false;
        }

        if (!"IN_PROGRESS".equalsIgnoreCase(
                attempt.getStatus())) {

            return false;
        }

        return attemptDAO.submit(
                attemptId,
                false);
    }

    public boolean autoSubmitAttempt(long attemptId) {

        if (attemptId <= 0) {
            return false;
        }

        ExamAttempt attempt =
                attemptDAO.getById(attemptId);

        if (attempt == null) {
            return false;
        }

        if (!"IN_PROGRESS".equalsIgnoreCase(
                attempt.getStatus())) {

            return false;
        }

        return attemptDAO.submit(
                attemptId,
                true);
    }

    public boolean abandonAttempt(long attemptId) {

        if (attemptId <= 0) {
            return false;
        }

        ExamAttempt attempt =
                attemptDAO.getById(attemptId);

        if (attempt == null) {
            return false;
        }

        if (!"IN_PROGRESS".equalsIgnoreCase(
                attempt.getStatus())) {

            return false;
        }

        return attemptDAO.abandon(attemptId);
    }

    public boolean markEvaluated(long attemptId,
                                 BigDecimal totalMarks,
                                 BigDecimal obtainedMarks) {

        if (attemptId <= 0) {
            return false;
        }

        if (totalMarks == null ||
            totalMarks.compareTo(BigDecimal.ZERO) < 0) {

            return false;
        }

        if (obtainedMarks == null) {
            return false;
        }

        return attemptDAO.updateMarks(
                attemptId,
                totalMarks,
                obtainedMarks);
    }

    public boolean updateStatus(long attemptId,
                                String status) {

        if (attemptId <= 0 ||
            status == null ||
            status.trim().isEmpty()) {

            return false;
        }

        String value = status.trim().toUpperCase();

        if (!isValidStatus(value)) {
            return false;
        }

        return attemptDAO.updateStatus(
                attemptId,
                value);
    }

    private boolean isValidStatus(String status) {

        return status.equals("IN_PROGRESS")
                || status.equals("SUBMITTED")
                || status.equals("EVALUATED")
                || status.equals("ABANDONED");
    }
}