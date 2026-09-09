package com.foxbrain.service;

import com.foxbrain.dao.CourseDAO;
import com.foxbrain.dao.CourseCategoryDAO;
import com.foxbrain.model.Course;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CourseService {

    private final CourseDAO courseDAO;
    private final CourseCategoryDAO categoryDAO;


    public CourseService() {
        courseDAO = new CourseDAO();
        categoryDAO = new CourseCategoryDAO();
    }


    public java.util.List<Course> getAll()
            throws SQLException {

        return courseDAO.findAll();
    }


    public java.util.List<Course> getActive()
            throws SQLException {

        return courseDAO.findActive();
    }


    public Course getById(long id)
            throws SQLException {

        if (id <= 0) {
            return null;
        }

        return courseDAO.findById(id);
    }


    public long create(Course course)
            throws SQLException {

        validate(course);

        if (courseDAO.existsBySlug(
                course.getSlug())) {

            throw new IllegalArgumentException(
                "A course with this slug already exists."
            );
        }

        validateCategory(course);

        return courseDAO.insert(course);
    }


    public boolean update(Course course)
            throws SQLException {

        if (course == null ||
            course.getId() <= 0) {

            throw new IllegalArgumentException(
                "Invalid course."
            );
        }

        validate(course);

        if (courseDAO.existsBySlugExceptId(
                course.getSlug(),
                course.getId())) {

            throw new IllegalArgumentException(
                "A course with this slug already exists."
            );
        }

        validateCategory(course);

        return courseDAO.update(course);
    }


    public boolean delete(long id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Invalid course ID."
            );
        }

        return courseDAO.delete(id);
    }


    private void validate(Course course) {

        if (course == null) {
            throw new IllegalArgumentException(
                "Course is required."
            );
        }


        if (course.getName() == null ||
            course.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Course name is required."
            );
        }

        if (course.getName().length() > 200) {
            throw new IllegalArgumentException(
                "Course name cannot exceed 200 characters."
            );
        }


        if (course.getSlug() == null ||
            course.getSlug().trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Course slug is required."
            );
        }

        if (course.getSlug().length() > 220) {
            throw new IllegalArgumentException(
                "Course slug cannot exceed 220 characters."
            );
        }


        if (course.getShortDescription() != null &&
            course.getShortDescription().length() > 500) {

            throw new IllegalArgumentException(
                "Short description cannot exceed 500 characters."
            );
        }


        if (course.getDurationValue() != null &&
            course.getDurationValue() <= 0) {

            throw new IllegalArgumentException(
                "Duration must be greater than zero."
            );
        }


        if (course.getDurationValue() != null) {

            String unit =
                course.getDurationUnit();

            if (unit == null ||
                !isValidDurationUnit(unit)) {

                throw new IllegalArgumentException(
                    "Please select a valid duration unit."
                );
            }

        } else {
            course.setDurationUnit(null);
        }


        if (course.getFee() != null &&
            course.getFee().compareTo(
                BigDecimal.ZERO
            ) < 0) {

            throw new IllegalArgumentException(
                "Fee cannot be negative."
            );
        }


        if (course.getCurrencyCode() == null ||
            course.getCurrencyCode().trim().isEmpty()) {

            course.setCurrencyCode("INR");
        }

        course.setCurrencyCode(
            course.getCurrencyCode()
                .trim()
                .toUpperCase()
        );

        if (course.getCurrencyCode().length() != 3) {
            throw new IllegalArgumentException(
                "Currency code must contain 3 characters."
            );
        }


        if (course.getMaxStudents() != null &&
            course.getMaxStudents() <= 0) {

            throw new IllegalArgumentException(
                "Maximum students must be greater than zero."
            );
        }


        if (course.getImageUrl() != null &&
            course.getImageUrl().length() > 500) {

            throw new IllegalArgumentException(
                "Image URL cannot exceed 500 characters."
            );
        }


        if (course.getStatus() == null ||
            !isValidStatus(course.getStatus())) {

            throw new IllegalArgumentException(
                "Please select a valid course status."
            );
        }


        if (course.getDisplayOrder() < 0) {
            throw new IllegalArgumentException(
                "Display order cannot be negative."
            );
        }
    }


    private boolean isValidDurationUnit(
            String unit) {

        return
            "DAYS".equals(unit) ||
            "WEEKS".equals(unit) ||
            "MONTHS".equals(unit) ||
            "YEARS".equals(unit);
    }


    private boolean isValidStatus(
            String status) {

        return
            "DRAFT".equals(status) ||
            "ACTIVE".equals(status) ||
            "INACTIVE".equals(status) ||
            "ARCHIVED".equals(status);
    }


    private void validateCategory(Course course)
            throws SQLException {

        if (course.getCategoryId() == null) {
            return;
        }

        if (categoryDAO.findById(
                course.getCategoryId()) == null) {

            throw new IllegalArgumentException(
                "Selected category does not exist."
            );
        }
    }
}