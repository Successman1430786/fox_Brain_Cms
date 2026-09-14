package com.foxbrain.service;

import com.foxbrain.dao.ExamDAO;
import com.foxbrain.model.Exam;

import java.sql.SQLException;
import java.util.List;

public class ExamService {

    private final ExamDAO examDAO;

    public ExamService() {
        this.examDAO = new ExamDAO();
    }

    // =====================================================
    // GET ALL EXAMS
    // =====================================================

    public List<Exam> getAllExams() {

        try {
            return examDAO.getAll();

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exams.",
                    e
            );
        }
    }

    // =====================================================
    // GET EXAM BY ID
    // =====================================================

    public Exam getExamById(long id) {

        try {
            return examDAO.getById(id);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exam.",
                    e
            );
        }
    }

    // =====================================================
    // CREATE EXAM
    // =====================================================

    public long createExam(Exam exam) {

        validateExam(exam);

        try {
            return examDAO.create(exam);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to create exam.",
                    e
            );
        }
    }

    // =====================================================
    // UPDATE EXAM
    // =====================================================

    public boolean updateExam(Exam exam) {

        validateExam(exam);

        try {
            return examDAO.update(exam);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to update exam.",
                    e
            );
        }
    }

    // =====================================================
    // DELETE EXAM
    // =====================================================

    public boolean deleteExam(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid exam ID."
            );
        }

        try {
            return examDAO.delete(id);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to delete exam.",
                    e
            );
        }
    }

    // =====================================================
    // GET EXAMS BY BATCH
    // =====================================================

    public List<Exam> getExamsByBatch(long batchId) {

        if (batchId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid batch ID."
            );
        }

        try {
            return examDAO.getByBatchId(batchId);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load batch exams.",
                    e
            );
        }
    }

    // =====================================================
    // GET EXAMS BY STATUS
    // =====================================================

    public List<Exam> getExamsByStatus(String status) {

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Exam status is required."
            );
        }

        try {
            return examDAO.getByStatus(status);

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load exams by status.",
                    e
            );
        }
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private void validateExam(Exam exam) {

        if (exam == null) {
            throw new IllegalArgumentException(
                    "Exam cannot be null."
            );
        }

        if (exam.getBatchId() <= 0) {
            throw new IllegalArgumentException(
                    "Please select a valid batch."
            );
        }

        if (exam.getTitle() == null ||
                exam.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Exam title is required."
            );
        }

        if (exam.getExamType() == null ||
                exam.getExamType().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Exam type is required."
            );
        }

        if (exam.getExamMode() == null ||
                exam.getExamMode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Exam mode is required."
            );
        }

        if (!exam.getExamMode().equals("ONLINE") &&
                !exam.getExamMode().equals("OFFLINE")) {

            throw new IllegalArgumentException(
                    "Invalid exam mode."
            );
        }

        if (exam.getTotalMarks() <= 0) {
            throw new IllegalArgumentException(
                    "Total marks must be greater than zero."
            );
        }

        if (exam.getPassingMarks() != null) {

            if (exam.getPassingMarks() < 0 ||
                    exam.getPassingMarks()
                            > exam.getTotalMarks()) {

                throw new IllegalArgumentException(
                        "Passing marks must be between 0 and total marks."
                );
            }
        }

        if (exam.getDurationMinutes() != null &&
                exam.getDurationMinutes() <= 0) {

            throw new IllegalArgumentException(
                    "Duration must be greater than zero."
            );
        }
    }
}