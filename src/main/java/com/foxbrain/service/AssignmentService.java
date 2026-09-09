package com.foxbrain.service;

import com.foxbrain.dao.AssignmentDAO;
import com.foxbrain.model.Assignment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AssignmentService {

    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    public List<Assignment> getAll() throws SQLException {
        return assignmentDAO.getAll();
    }

    public Assignment getById(long id) throws SQLException {
        return assignmentDAO.getById(id);
    }

    public boolean create(Assignment assignment) throws SQLException {

        validate(assignment);

        return assignmentDAO.create(assignment);
    }

    public boolean update(Assignment assignment) throws SQLException {

        if (assignment.getId() <= 0) {
            throw new IllegalArgumentException("Invalid assignment ID.");
        }

        validate(assignment);

        return assignmentDAO.update(assignment);
    }

    public boolean delete(long id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid assignment ID.");
        }

        return assignmentDAO.delete(id);
    }

    private void validate(Assignment assignment) {

        if (assignment.getBatchId() <= 0) {
            throw new IllegalArgumentException("Please select a batch.");
        }

        if (assignment.getTeacherId() <= 0) {
            throw new IllegalArgumentException("Please select a teacher.");
        }

        if (assignment.getTitle() == null ||
                assignment.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException("Assignment title is required.");
        }

        if (assignment.getTitle().trim().length() > 255) {
            throw new IllegalArgumentException(
                    "Assignment title cannot exceed 255 characters.");
        }

        if (assignment.getAssignedDate() == null) {
            throw new IllegalArgumentException("Assigned date is required.");
        }

        if (assignment.getDueDate() != null &&
                assignment.getDueDate().isBefore(assignment.getAssignedDate())) {

            throw new IllegalArgumentException(
                    "Due date cannot be before assigned date.");
        }

        if (assignment.getMaxMarks() != null) {

            if (assignment.getMaxMarks().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Maximum marks cannot be negative.");
            }

            if (assignment.getMaxMarks()
                    .compareTo(new BigDecimal("999999.99")) > 0) {

                throw new IllegalArgumentException(
                        "Maximum marks are too large.");
            }

            if (assignment.getMaxMarks().scale() > 2) {
                throw new IllegalArgumentException(
                        "Maximum marks can have maximum 2 decimal places.");
            }
        }

        if (assignment.getLatePenalty() != null) {

            if (assignment.getLatePenalty()
                    .compareTo(BigDecimal.ZERO) < 0) {

                throw new IllegalArgumentException(
                        "Late penalty cannot be negative.");
            }

            if (assignment.getLatePenalty()
                    .compareTo(new BigDecimal("999.99")) > 0) {

                throw new IllegalArgumentException(
                        "Late penalty cannot exceed 999.99.");
            }

            if (assignment.getLatePenalty().scale() > 2) {
                throw new IllegalArgumentException(
                        "Late penalty can have maximum 2 decimal places.");
            }
        }

        if (assignment.getSubmissionType() == null ||
                !assignment.getSubmissionType().matches("FILE|TEXT|BOTH")) {

            throw new IllegalArgumentException(
                    "Invalid submission type.");
        }

        if (assignment.getStatus() == null ||
                !assignment.getStatus()
                        .matches("DRAFT|PUBLISHED|CLOSED|ARCHIVED")) {

            throw new IllegalArgumentException(
                    "Invalid assignment status.");
        }
    }
}