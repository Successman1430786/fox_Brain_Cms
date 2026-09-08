package com.foxbrain.service;

import com.foxbrain.dao.StudentDAO;
import com.foxbrain.model.Student;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public Student getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid student ID.");
        }

        return studentDAO.findById(id);
    }

    public List<Student> getAll() {
        return studentDAO.findAll();
    }

    public Student getByAdmissionNumber(String admissionNumber) {
        if (admissionNumber == null || admissionNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Admission number is required.");
        }

        return studentDAO.findByAdmissionNumber(admissionNumber.trim());
    }

    public long create(Student student) {

        validateStudent(student);

        // students.user_id is NOT NULL in the database.
        if (student.getUserId() <= 0) {
            throw new IllegalArgumentException(
                "A valid user ID is required for the student."
            );
        }

        if (student.getStatus() == null ||
            student.getStatus().trim().isEmpty()) {
            student.setStatus("ACTIVE");
        } else {
            student.setStatus(student.getStatus().trim());
        }

        student.setAdmissionNumber(
            student.getAdmissionNumber().trim()
        );

        return studentDAO.insert(student);
    }

    public boolean update(Student student) {

        if (student == null || student.getId() <= 0) {
            throw new IllegalArgumentException("Invalid student.");
        }

        validateStudent(student);

        if (student.getUserId() <= 0) {
            throw new IllegalArgumentException(
                "A valid user ID is required for the student."
            );
        }

        if (student.getStatus() == null ||
            student.getStatus().trim().isEmpty()) {
            student.setStatus("ACTIVE");
        } else {
            student.setStatus(student.getStatus().trim());
        }

        student.setAdmissionNumber(
            student.getAdmissionNumber().trim()
        );

        return studentDAO.update(student);
    }

    public boolean delete(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid student ID.");
        }

        return studentDAO.delete(id);
    }

    private void validateStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException("Student data is required.");
        }

        if (student.getAdmissionNumber() == null ||
            student.getAdmissionNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Admission number is required."
            );
        }

        if (student.getAdmissionNumber().trim().length() > 50) {
            throw new IllegalArgumentException(
                "Admission number cannot exceed 50 characters."
            );
        }

        if (student.getGender() != null &&
            student.getGender().length() > 30) {

            throw new IllegalArgumentException(
                "Gender cannot exceed 30 characters."
            );
        }

        if (student.getCity() != null &&
            student.getCity().length() > 100) {

            throw new IllegalArgumentException(
                "City cannot exceed 100 characters."
            );
        }

        if (student.getState() != null &&
            student.getState().length() > 100) {

            throw new IllegalArgumentException(
                "State cannot exceed 100 characters."
            );
        }

        if (student.getPostalCode() != null &&
            student.getPostalCode().length() > 20) {

            throw new IllegalArgumentException(
                "Postal code cannot exceed 20 characters."
            );
        }

        if (student.getCountry() != null &&
            student.getCountry().length() > 100) {

            throw new IllegalArgumentException(
                "Country cannot exceed 100 characters."
            );
        }
    }
} 