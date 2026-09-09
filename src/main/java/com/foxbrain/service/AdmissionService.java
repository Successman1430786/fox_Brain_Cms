package com.foxbrain.service;

import com.foxbrain.dao.AdmissionDAO;
import com.foxbrain.model.Admission;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdmissionService {

    private final AdmissionDAO admissionDAO;
    private final CourseService courseService;
    private final BatchService batchService;

    private static final Set<String> VALID_STATUSES =
            new HashSet<>(Arrays.asList(
                    "APPLIED",
                    "UNDER_REVIEW",
                    "APPROVED",
                    "REJECTED",
                    "WAITLISTED",
                    "CANCELLED",
                    "CONVERTED"
            ));

    private static final Set<String> VALID_GENDERS =
            new HashSet<>(Arrays.asList(
                    "MALE",
                    "FEMALE",
                    "OTHER"
            ));

    public AdmissionService() {
        this.admissionDAO = new AdmissionDAO();
        this.courseService = new CourseService();
        this.batchService = new BatchService();
    }

    public List<Admission> getAll() throws SQLException {
        return admissionDAO.findAll();
    }

    public List<Admission> getByStatus(String status)
            throws SQLException {

        return admissionDAO.findByStatus(status);
    }

    public Admission getById(long id) throws SQLException {

        if (id <= 0) {
            return null;
        }

        return admissionDAO.findById(id);
    }

    public long create(Admission admission)
            throws SQLException {

        validate(admission);

        if (admissionDAO.existsByApplicationNumber(
                admission.getApplicationNumber())) {

            throw new IllegalArgumentException(
                    "Application number already exists."
            );
        }

        return admissionDAO.insert(admission);
    }

    public void update(Admission admission)
            throws SQLException {

        if (admission.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid admission ID."
            );
        }

        validate(admission);

        if (admissionDAO.existsByApplicationNumberExceptId(
                admission.getApplicationNumber(),
                admission.getId())) {

            throw new IllegalArgumentException(
                    "Application number already exists."
            );
        }

        admissionDAO.update(admission);
    }

    public void delete(long id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid admission ID."
            );
        }

        admissionDAO.delete(id);
    }

    private void validate(Admission admission)
            throws SQLException {

        if (admission == null) {
            throw new IllegalArgumentException(
                    "Admission data is required."
            );
        }

        String applicationNumber =
                clean(admission.getApplicationNumber());

        if (applicationNumber.isEmpty()) {
            throw new IllegalArgumentException(
                    "Application number is required."
            );
        }

        if (applicationNumber.length() > 80) {
            throw new IllegalArgumentException(
                    "Application number must not exceed 80 characters."
            );
        }

        admission.setApplicationNumber(applicationNumber);

        if (admission.getCourseId() <= 0) {
            throw new IllegalArgumentException(
                    "Please select a course."
            );
        }

        if (courseService.getById(admission.getCourseId()) == null) {
            throw new IllegalArgumentException(
                    "Selected course does not exist."
            );
        }

        if (admission.getBatchId() != null
                && admission.getBatchId() > 0) {

            if (batchService.getById(
                    admission.getBatchId()) == null) {

                throw new IllegalArgumentException(
                        "Selected batch does not exist."
                );
            }
        } else {
            admission.setBatchId(null);
        }

        if (admission.getApplicationDate() == null) {
            admission.setApplicationDate(LocalDate.now());
        }

        String firstName = clean(admission.getFirstName());

        if (firstName.isEmpty()) {
            throw new IllegalArgumentException(
                    "First name is required."
            );
        }

        if (firstName.length() > 100) {
            throw new IllegalArgumentException(
                    "First name must not exceed 100 characters."
            );
        }

        admission.setFirstName(firstName);

        String lastName = clean(admission.getLastName());

        if (lastName.length() > 100) {
            throw new IllegalArgumentException(
                    "Last name must not exceed 100 characters."
            );
        }

        admission.setLastName(
                lastName.isEmpty() ? null : lastName
        );

        String email = clean(admission.getEmail());

        if (email.length() > 150) {
            throw new IllegalArgumentException(
                    "Email must not exceed 150 characters."
            );
        }

        admission.setEmail(
                email.isEmpty() ? null : email
        );

        String phone = clean(admission.getPhone());

        if (phone.isEmpty()) {
            throw new IllegalArgumentException(
                    "Phone number is required."
            );
        }

        if (phone.length() > 30) {
            throw new IllegalArgumentException(
                    "Phone number must not exceed 30 characters."
            );
        }

        admission.setPhone(phone);

        if (admission.getDateOfBirth() != null
                && admission.getDateOfBirth()
                    .isAfter(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "Date of birth cannot be in the future."
            );
        }

        String gender = clean(admission.getGender());

        if (!gender.isEmpty()
                && !VALID_GENDERS.contains(gender)) {

            throw new IllegalArgumentException(
                    "Invalid gender."
            );
        }

        admission.setGender(
                gender.isEmpty() ? null : gender
        );

        validateLength(
                admission.getAddressLine1(),
                255,
                "Address line 1"
        );

        validateLength(
                admission.getAddressLine2(),
                255,
                "Address line 2"
        );

        validateLength(
                admission.getCity(),
                100,
                "City"
        );

        validateLength(
                admission.getState(),
                100,
                "State"
        );

        validateLength(
                admission.getPostalCode(),
                20,
                "Postal code"
        );

        validateLength(
                admission.getCountry(),
                100,
                "Country"
        );

        validateLength(
                admission.getQualification(),
                255,
                "Qualification"
        );

        validateLength(
                admission.getSource(),
                100,
                "Source"
        );

        String status = clean(admission.getStatus());

        if (status.isEmpty()) {
            status = "APPLIED";
        }

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid admission status."
            );
        }

        admission.setStatus(status);
    }

    private void validateLength(
            String value,
            int max,
            String field) {

        if (value != null
                && value.trim().length() > max) {

            throw new IllegalArgumentException(
                    field + " must not exceed "
                    + max + " characters."
            );
        }
    }

    private String clean(String value) {

        return value == null
                ? ""
                : value.trim();
    }
}