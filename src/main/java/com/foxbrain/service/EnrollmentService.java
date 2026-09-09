package com.foxbrain.service;

import com.foxbrain.dao.EnrollmentDAO;
import com.foxbrain.model.Batch;
import com.foxbrain.model.Enrollment;
import com.foxbrain.model.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final StudentService studentService;
    private final BatchService batchService;

    private static final Set<String> VALID_STATUSES =
            new HashSet<>(
                    Arrays.asList(
                            "ACTIVE",
                            "COMPLETED",
                            "CANCELLED",
                            "TRANSFERRED"
                    )
            );

    public EnrollmentService() {
        this.enrollmentDAO = new EnrollmentDAO();
        this.studentService = new StudentService();
        this.batchService = new BatchService();
    }

    public List<Enrollment> getAll() throws SQLException {
        return enrollmentDAO.findAll();
    }

    public List<Enrollment> getByStatus(String status)
            throws SQLException {

        if (status == null || !VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid enrollment status."
            );
        }

        return enrollmentDAO.findByStatus(status);
    }

    public Enrollment getById(long id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid enrollment ID."
            );
        }

        return enrollmentDAO.findById(id);
    }

    public void create(Enrollment enrollment)
            throws SQLException {

        validate(enrollment, false);

        if (enrollmentDAO.existsByEnrollmentNumber(
                enrollment.getEnrollmentNumber())) {

            throw new IllegalArgumentException(
                    "Enrollment number already exists."
            );
        }

        if (enrollmentDAO.existsByStudentAndBatch(
                enrollment.getStudentId(),
                enrollment.getBatchId())) {

            throw new IllegalArgumentException(
                    "This student is already enrolled in this batch."
            );
        }

        checkBatchCapacity(enrollment);

        enrollmentDAO.insert(enrollment);
    }

    public void update(Enrollment enrollment)
            throws SQLException {

        if (enrollment.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid enrollment ID."
            );
        }

        validate(enrollment, true);

        if (enrollmentDAO.existsByEnrollmentNumberExceptId(
                enrollment.getEnrollmentNumber(),
                enrollment.getId())) {

            throw new IllegalArgumentException(
                    "Enrollment number already exists."
            );
        }

        if (enrollmentDAO.existsByStudentAndBatchExceptId(
                enrollment.getStudentId(),
                enrollment.getBatchId(),
                enrollment.getId())) {

            throw new IllegalArgumentException(
                    "This student is already enrolled in this batch."
            );
        }

        checkBatchCapacityForUpdate(enrollment);

        enrollmentDAO.update(enrollment);
    }

    public void delete(long id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid enrollment ID."
            );
        }

        enrollmentDAO.delete(id);
    }

    private void validate(
            Enrollment enrollment,
            boolean updating) throws SQLException {

        if (enrollment == null) {
            throw new IllegalArgumentException(
                    "Enrollment data is required."
            );
        }

        if (enrollment.getStudentId() <= 0) {
            throw new IllegalArgumentException(
                    "Please select a student."
            );
        }

        if (enrollment.getBatchId() <= 0) {
            throw new IllegalArgumentException(
                    "Please select a batch."
            );
        }

        Student student =
                studentService.getById(
                        enrollment.getStudentId()
                );

        if (student == null) {
            throw new IllegalArgumentException(
                    "Selected student does not exist."
            );
        }

        Batch batch =
                batchService.getById(
                        enrollment.getBatchId()
                );

        if (batch == null) {
            throw new IllegalArgumentException(
                    "Selected batch does not exist."
            );
        }

        String number = enrollment.getEnrollmentNumber();

        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Enrollment number is required."
            );
        }

        number = number.trim();

        if (number.length() > 80) {
            throw new IllegalArgumentException(
                    "Enrollment number must not exceed 80 characters."
            );
        }

        enrollment.setEnrollmentNumber(number);

        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }

        if (enrollment.getCompletionDate() != null
                && enrollment.getCompletionDate()
                .isBefore(enrollment.getEnrollmentDate())) {

            throw new IllegalArgumentException(
                    "Completion date cannot be before enrollment date."
            );
        }

        String status = enrollment.getStatus();

        if (status == null || status.trim().isEmpty()) {
            status = "ACTIVE";
        }

        status = status.trim().toUpperCase();

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid enrollment status."
            );
        }

        enrollment.setStatus(status);

        if (enrollment.getNotes() != null
                && enrollment.getNotes().length() > 5000) {

            throw new IllegalArgumentException(
                    "Notes must not exceed 5000 characters."
            );
        }
    }

    private void checkBatchCapacity(
            Enrollment enrollment) throws SQLException {

        Batch batch =
                batchService.getById(
                        enrollment.getBatchId()
                );

        if (batch == null || batch.getCapacity() == null) {
            return;
        }

        if (!"ACTIVE".equals(enrollment.getStatus())) {
            return;
        }

        int currentCount =
                enrollmentDAO.countActiveByBatch(
                        enrollment.getBatchId()
                );

        if (currentCount >= batch.getCapacity()) {

            throw new IllegalArgumentException(
                    "This batch has reached its capacity."
            );
        }
    }

    private void checkBatchCapacityForUpdate(
            Enrollment enrollment) throws SQLException {

        Batch batch =
                batchService.getById(
                        enrollment.getBatchId()
                );

        if (batch == null || batch.getCapacity() == null) {
            return;
        }

        if (!"ACTIVE".equals(enrollment.getStatus())) {
            return;
        }

        int currentCount =
                enrollmentDAO.countActiveByBatchExceptId(
                        enrollment.getBatchId(),
                        enrollment.getId()
                );

        if (currentCount >= batch.getCapacity()) {

            throw new IllegalArgumentException(
                    "This batch has reached its capacity."
            );
        }
    }
}