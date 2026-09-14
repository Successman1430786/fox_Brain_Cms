package com.foxbrain.service;

import com.foxbrain.dao.ExamAttemptDAO;
import com.foxbrain.model.ExamAttempt;

import java.sql.Timestamp;
import java.util.List;

public class ExamAttemptService {

    private final ExamAttemptDAO attemptDAO;

    public ExamAttemptService() {
        this.attemptDAO = new ExamAttemptDAO();
    }

    // =====================================================
    // START EXAM
    // =====================================================

    public long startExam(
            long examId,
            long studentId) {

        validateId(examId);
        validateId(studentId);

        try {

            ExamAttempt active =
                    attemptDAO.getActiveAttempt(
                            examId,
                            studentId
                    );

            if (active != null) {
                return active.getId();
            }

            int attemptNumber =
                    attemptDAO.getNextAttemptNumber(
                            examId,
                            studentId
                    );

            ExamAttempt attempt =
                    new ExamAttempt();

            attempt.setExamId(examId);
            attempt.setStudentId(studentId);

            attempt.setAttemptNumber(
                    attemptNumber
            );

            attempt.setStartedAt(
                    new Timestamp(
                            System.currentTimeMillis()
                    )
            );

            attempt.setStatus(
                    "IN_PROGRESS"
            );

            return attemptDAO.create(attempt);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to start exam.",
                    e
            );
        }
    }

    // =====================================================
    // GET ATTEMPT
    // =====================================================

    public ExamAttempt getAttempt(long id) {

        validateId(id);

        try {
            return attemptDAO.getById(id);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam attempt.",
                    e
            );
        }
    }

    // =====================================================
    // GET ACTIVE ATTEMPT
    // =====================================================

    public ExamAttempt getActiveAttempt(
            long examId,
            long studentId) {

        validateId(examId);
        validateId(studentId);

        try {
            return attemptDAO.getActiveAttempt(
                    examId,
                    studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load active exam attempt.",
                    e
            );
        }
    }

    // =====================================================
    // GET STUDENT ATTEMPTS
    // =====================================================

    public List<ExamAttempt> getStudentAttempts(
            long studentId) {

        validateId(studentId);

        try {
            return attemptDAO.getByStudentId(
                    studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load student attempts.",
                    e
            );
        }
    }

    // =====================================================
    // GET EXAM ATTEMPTS
    // =====================================================

    public List<ExamAttempt> getExamAttempts(
            long examId) {

        validateId(examId);

        try {
            return attemptDAO.getByExamId(
                    examId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam attempts.",
                    e
            );
        }
    }

    // =====================================================
    // SUBMIT EXAM
    // =====================================================

    public boolean submitExam(
            long attemptId,
            boolean autoSubmitted) {

        validateId(attemptId);

        try {

            return attemptDAO.submit(
                    attemptId,
                    autoSubmitted
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to submit exam.",
                    e
            );
        }
    }

    // =====================================================
    // ABANDON ATTEMPT
    // =====================================================

    public boolean abandonAttempt(
            long attemptId) {

        validateId(attemptId);

        try {
            return attemptDAO.abandon(
                    attemptId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to abandon exam attempt.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE STATUS
    // =====================================================

    public boolean updateStatus(
            long attemptId,
            String status) {

        validateId(attemptId);

        if (status == null ||
                status.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Attempt status is required."
            );
        }

        try {
            return attemptDAO.updateStatus(
                    attemptId,
                    status
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to update attempt status.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE MARKS
    // =====================================================

    public boolean updateMarks(
            long attemptId,
            double totalMarks,
            double obtainedMarks) {

        validateId(attemptId);

        if (totalMarks < 0) {
            throw new IllegalArgumentException(
                    "Total marks cannot be negative."
            );
        }

        if (obtainedMarks < 0) {
            throw new IllegalArgumentException(
                    "Obtained marks cannot be negative."
            );
        }

        try {
            return attemptDAO.updateMarks(
                    attemptId,
                    totalMarks,
                    obtainedMarks
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to update attempt marks.",
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