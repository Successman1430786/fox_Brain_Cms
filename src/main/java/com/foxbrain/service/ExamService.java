package com.foxbrain.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.foxbrain.dao.ExamDAO;
import com.foxbrain.model.Exam;

public class ExamService {

    private final ExamDAO examDAO = new ExamDAO();

    public List<Exam> getAllExams() {

        try {
            return examDAO.getAll();

        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Exam getExamById(long id) {

        if (id <= 0) {
            return null;
        }

        try {
            return examDAO.getById(id);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Exam> getExamsByBatch(long batchId) {

        if (batchId <= 0) {
            return List.of();
        }

        try {
            return examDAO.getByBatchId(batchId);

        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Exam> getExamsByStatus(String status) {

        if (status == null || status.trim().isEmpty()) {
            return List.of();
        }

        try {
            return examDAO.getByStatus(
                    status.trim().toUpperCase()
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean createExam(Exam exam) {

        if (!validateExam(exam)) {
            return false;
        }

        if (exam.getStatus() == null ||
                exam.getStatus().trim().isEmpty()) {

            exam.setStatus("DRAFT");
        }

        exam.setStatus(
                exam.getStatus().toUpperCase()
        );

        try {

            return examDAO.create(exam);

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExam(Exam exam) {

        if (exam == null || exam.getId() <= 0) {
            return false;
        }

        if (!validateExam(exam)) {
            return false;
        }

        if (exam.getStatus() == null ||
                exam.getStatus().trim().isEmpty()) {

            exam.setStatus("DRAFT");
        }

        exam.setStatus(
                exam.getStatus().toUpperCase()
        );

        try {

            return examDAO.update(exam);

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExam(long id) {

        if (id <= 0) {
            return false;
        }

        try {

            Exam exam =
                    examDAO.getById(id);

            if (exam == null) {
                return false;
            }

            return examDAO.delete(id);

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    private boolean validateExam(Exam exam) {

        if (exam == null) {
            return false;
        }

        if (exam.getBatchId() <= 0) {
            return false;
        }

        if (exam.getTitle() == null ||
                exam.getTitle().trim().isEmpty()) {

            return false;
        }

        if (exam.getExamType() == null ||
                exam.getExamType().trim().isEmpty()) {

            return false;
        }

        if (exam.getExamMode() == null ||
                exam.getExamMode().trim().isEmpty()) {

            return false;
        }

        if (!isValidExamType(
                exam.getExamType())) {

            return false;
        }

        if (!isValidExamMode(
                exam.getExamMode())) {

            return false;
        }

        BigDecimal totalMarks =
                exam.getTotalMarks();

        if (totalMarks == null ||
                totalMarks.compareTo(
                        BigDecimal.ZERO) <= 0) {

            return false;
        }

        BigDecimal passingMarks =
                exam.getPassingMarks();

        if (passingMarks != null) {

            if (passingMarks.compareTo(
                    BigDecimal.ZERO) < 0) {

                return false;
            }

            if (passingMarks.compareTo(
                    totalMarks) > 0) {

                return false;
            }
        }

        if (exam.getDurationMinutes() != null &&
                exam.getDurationMinutes() <= 0) {

            return false;
        }

        if (exam.getExamMode()
                .equalsIgnoreCase("OFFLINE")) {

            if (exam.getRoomName() == null ||
                    exam.getRoomName()
                            .trim()
                            .isEmpty()) {

                return false;
            }
        }

        if (!isValidStatus(
                exam.getStatus())) {

            return false;
        }

        return validateDateTime(exam);
    }

    private boolean validateDateTime(Exam exam) {

        Date examDate =
                exam.getExamDate();

        Time startTime =
                exam.getStartTime();

        Time endTime =
                exam.getEndTime();

        if (examDate == null) {
            return true;
        }

        if (startTime != null &&
                endTime != null) {

            if (!endTime.after(startTime)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidExamType(
            String type) {

        String value =
                type.toUpperCase();

        return value.equals("QUIZ")
                || value.equals("MIDTERM")
                || value.equals("FINAL")
                || value.equals("PRACTICAL")
                || value.equals("PROJECT")
                || value.equals("OTHER");
    }

    private boolean isValidExamMode(
            String mode) {

        String value =
                mode.toUpperCase();

        return value.equals("ONLINE")
                || value.equals("OFFLINE");
    }

    private boolean isValidStatus(
            String status) {

        if (status == null ||
                status.trim().isEmpty()) {

            return true;
        }

        String value =
                status.toUpperCase();

        return value.equals("DRAFT")
                || value.equals("SCHEDULED")
                || value.equals("COMPLETED")
                || value.equals("CANCELLED");
    }
}