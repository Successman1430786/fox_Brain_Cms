package com.foxbrain.service;

import com.foxbrain.dao.BatchDAO;
import com.foxbrain.model.Batch;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class BatchService {

    private final BatchDAO batchDAO;
    private final CourseService courseService;

    private static final List<String> VALID_STATUSES =
            Arrays.asList(
                    "PLANNED",
                    "ACTIVE",
                    "COMPLETED",
                    "CANCELLED"
            );

    public BatchService() {
        this.batchDAO = new BatchDAO();
        this.courseService = new CourseService();
    }

    public List<Batch> getAll() throws SQLException {
        return batchDAO.findAll();
    }

    public List<Batch> getActive() throws SQLException {
        return batchDAO.findActive();
    }

    public Batch getById(long id) throws SQLException {

        if (id <= 0) {
            return null;
        }

        return batchDAO.findById(id);
    }

    public long create(Batch batch) throws SQLException {

        validate(batch);

        if (batchDAO.existsByCode(batch.getBatchCode())) {
            throw new IllegalArgumentException(
                    "Batch code already exists."
            );
        }

        validateCourse(batch.getCourseId());

        return batchDAO.insert(batch);
    }

    public boolean update(Batch batch) throws SQLException {

        if (batch.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid batch ID."
            );
        }

        validate(batch);

        if (batchDAO.existsByCodeExceptId(
                batch.getBatchCode(),
                batch.getId())) {

            throw new IllegalArgumentException(
                    "Batch code already exists."
            );
        }

        validateCourse(batch.getCourseId());

        return batchDAO.update(batch);
    }

    public boolean delete(long id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid batch ID."
            );
        }

        return batchDAO.delete(id);
    }

    private void validateCourse(long courseId)
            throws SQLException {

        if (courseId <= 0) {
            throw new IllegalArgumentException(
                    "Please select a course."
            );
        }

        if (courseService.getById(courseId) == null) {
            throw new IllegalArgumentException(
                    "Selected course does not exist."
            );
        }
    }

    private void validate(Batch batch) {

        if (batch == null) {
            throw new IllegalArgumentException(
                    "Batch data is required."
            );
        }

        if (batch.getCourseId() <= 0) {
            throw new IllegalArgumentException(
                    "Please select a course."
            );
        }

        if (batch.getBatchCode() == null ||
                batch.getBatchCode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Batch code is required."
            );
        }

        if (batch.getBatchCode().length() > 80) {
            throw new IllegalArgumentException(
                    "Batch code must not exceed 80 characters."
            );
        }

        if (batch.getName() == null ||
                batch.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Batch name is required."
            );
        }

        if (batch.getName().length() > 200) {
            throw new IllegalArgumentException(
                    "Batch name must not exceed 200 characters."
            );
        }

        if (batch.getStartDate() != null &&
                batch.getEndDate() != null &&
                batch.getEndDate().isBefore(
                        batch.getStartDate())) {

            throw new IllegalArgumentException(
                    "End date cannot be before start date."
            );
        }

        if (batch.getStartTime() != null &&
                batch.getEndTime() != null &&
                batch.getEndTime().isBefore(
                        batch.getStartTime())) {

            throw new IllegalArgumentException(
                    "End time cannot be before start time."
            );
        }

        if (batch.getRoomName() != null &&
                batch.getRoomName().length() > 100) {

            throw new IllegalArgumentException(
                    "Room name must not exceed 100 characters."
            );
        }

        if (batch.getCapacity() != null &&
                batch.getCapacity() <= 0) {

            throw new IllegalArgumentException(
                    "Capacity must be greater than zero."
            );
        }

        if (batch.getStatus() == null ||
                !VALID_STATUSES.contains(batch.getStatus())) {

            throw new IllegalArgumentException(
                    "Invalid batch status."
            );
        }
    }
}