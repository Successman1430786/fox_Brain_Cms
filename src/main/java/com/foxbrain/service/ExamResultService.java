package com.foxbrain.service;

import com.foxbrain.dao.ExamResultDAO;
import com.foxbrain.model.ExamResult;

import java.util.List;

public class ExamResultService {

    private final ExamResultDAO resultDAO;

    public ExamResultService() {
        this.resultDAO = new ExamResultDAO();
    }

    // =====================================================
    // GET ALL RESULTS
    // =====================================================

    public List<ExamResult> getAllResults() {

        try {
            return resultDAO.getAll();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load results.",
                    e
            );
        }
    }

    // =====================================================
    // GET RESULT
    // =====================================================

    public ExamResult getResult(long id) {

        validateId(id);

        try {
            return resultDAO.getById(id);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load result.",
                    e
            );
        }
    }

    // =====================================================
    // GET RESULT BY EXAM + STUDENT
    // =====================================================

    public ExamResult getResultByExamAndStudent(
            long examId,
            long studentId) {

        validateId(examId);
        validateId(studentId);

        try {

            return resultDAO.getByExamAndStudent(
                    examId,
                    studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load student result.",
                    e
            );
        }
    }

    // =====================================================
    // CREATE RESULT
    // =====================================================

    public long createResult(
            ExamResult result) {

        validateResult(result);

        try {

            boolean exists =
                    resultDAO.exists(
                            result.getExamId(),
                            result.getStudentId()
                    );

            if (exists) {
                throw new IllegalArgumentException(
                        "Result already exists for this student and exam."
                );
            }

            return resultDAO.create(
                    result
            );

        } catch (Exception e) {

            e.printStackTrace();

            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }

            throw new RuntimeException(
                    "Unable to create result.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE RESULT
    // =====================================================

    public boolean updateResult(
            ExamResult result) {

        validateResult(result);

        validateId(result.getId());

        try {

            return resultDAO.update(
                    result
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to update result.",
                    e
            );
        }
    }

    // =====================================================
    // PUBLISH RESULT
    // =====================================================

    public boolean publishResult(long id) {

        validateId(id);

        try {

            return resultDAO.publish(id);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to publish result.",
                    e
            );
        }
    }

    // =====================================================
    // UNPUBLISH RESULT
    // =====================================================

    public boolean unpublishResult(long id) {

        validateId(id);

        try {

            return resultDAO.unpublish(id);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to unpublish result.",
                    e
            );
        }
    }

    // =====================================================
    // GET RESULTS BY EXAM
    // =====================================================

    public List<ExamResult> getResultsByExam(
            long examId) {

        validateId(examId);

        try {

            return resultDAO.getByExamId(
                    examId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam results.",
                    e
            );
        }
    }

    // =====================================================
    // GET RESULTS BY STUDENT
    // =====================================================

    public List<ExamResult> getResultsByStudent(
            long studentId) {

        validateId(studentId);

        try {

            return resultDAO.getByStudentId(
                    studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load student results.",
                    e
            );
        }
    }

    // =====================================================
    // CALCULATE OBTAINED MARKS
    // =====================================================

    public double calculateObtainedMarks(
            long examId,
            long studentId) {

        validateId(examId);
        validateId(studentId);

        try {

            return resultDAO.getTotalObtainedMarks(
                    examId,
                    studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to calculate obtained marks.",
                    e
            );
        }
    }

    // =====================================================
    // GRADE CALCULATION
    // =====================================================

    public String calculateGrade(
            double marks,
            double totalMarks) {

        if (totalMarks <= 0) {
            return "N/A";
        }

        double percentage =
                (marks / totalMarks) * 100.0;

        if (percentage >= 90) {
            return "A+";
        }

        if (percentage >= 80) {
            return "A";
        }

        if (percentage >= 70) {
            return "B+";
        }

        if (percentage >= 60) {
            return "B";
        }

        if (percentage >= 50) {
            return "C";
        }

        if (percentage >= 40) {
            return "D";
        }

        return "F";
    }

    // =====================================================
    // PASS / FAIL
    // =====================================================

    public String calculateResultStatus(
            double marks,
            Double passingMarks) {

        if (passingMarks == null) {
            return "PASS";
        }

        return marks >= passingMarks
                ? "PASS"
                : "FAIL";
    }

    // =====================================================
    // VALIDATE RESULT
    // =====================================================

    private void validateResult(
            ExamResult result) {

        if (result == null) {
            throw new IllegalArgumentException(
                    "Result cannot be null."
            );
        }

        validateId(result.getExamId());
        validateId(result.getStudentId());

        if (result.getMarksObtained() < 0) {
            throw new IllegalArgumentException(
                    "Marks obtained cannot be negative."
            );
        }

        if (result.getResultStatus() == null ||
                result.getResultStatus()
                        .trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Result status is required."
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