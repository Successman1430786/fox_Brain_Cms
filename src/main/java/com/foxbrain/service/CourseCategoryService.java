package com.foxbrain.service;

import com.foxbrain.dao.CourseCategoryDAO;
import com.foxbrain.model.CourseCategory;

import java.sql.SQLException;
import java.util.List;

public class CourseCategoryService {

    private final CourseCategoryDAO categoryDAO;

    public CourseCategoryService() {
        categoryDAO = new CourseCategoryDAO();
    }

    public List<CourseCategory> getAll()
            throws SQLException {

        return categoryDAO.findAll();
    }

    public List<CourseCategory> getActive()
            throws SQLException {

        return categoryDAO.findActive();
    }

    public CourseCategory getById(long id)
            throws SQLException {

        return categoryDAO.findById(id);
    }

    public long create(CourseCategory category)
            throws SQLException {

        validate(category);

        if (categoryDAO.existsByName(
                category.getName())) {

            throw new IllegalArgumentException(
                "Category name already exists."
            );
        }

        if (categoryDAO.existsBySlug(
                category.getSlug())) {

            throw new IllegalArgumentException(
                "Category slug already exists."
            );
        }

        return categoryDAO.insert(category);
    }

    public boolean update(CourseCategory category)
            throws SQLException {

        if (category.getId() <= 0) {
            throw new IllegalArgumentException(
                "Invalid category ID."
            );
        }

        validate(category);

        if (categoryDAO.existsByNameExceptId(
                category.getName(),
                category.getId())) {

            throw new IllegalArgumentException(
                "Category name already exists."
            );
        }

        if (categoryDAO.existsBySlugExceptId(
                category.getSlug(),
                category.getId())) {

            throw new IllegalArgumentException(
                "Category slug already exists."
            );
        }

        return categoryDAO.update(category);
    }

    public boolean delete(long id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Invalid category ID."
            );
        }

        return categoryDAO.delete(id);
    }

    private void validate(CourseCategory category) {

        if (category == null) {
            throw new IllegalArgumentException(
                "Category is required."
            );
        }

        if (isBlank(category.getName())) {
            throw new IllegalArgumentException(
                "Category name is required."
            );
        }

        if (category.getName().length() > 150) {
            throw new IllegalArgumentException(
                "Category name cannot exceed 150 characters."
            );
        }

        if (isBlank(category.getSlug())) {
            throw new IllegalArgumentException(
                "Category slug is required."
            );
        }

        if (category.getSlug().length() > 180) {
            throw new IllegalArgumentException(
                "Category slug cannot exceed 180 characters."
            );
        }

        if (category.getDisplayOrder() < 0) {
            throw new IllegalArgumentException(
                "Display order cannot be negative."
            );
        }

        if (isBlank(category.getStatus())) {
            category.setStatus("ACTIVE");
        }

        if (!category.getStatus().equals("ACTIVE")
                && !category.getStatus().equals("INACTIVE")) {

            throw new IllegalArgumentException(
                "Invalid category status."
            );
        }
    }

    private boolean isBlank(String value) {

        return value == null
                || value.trim().isEmpty();
    }
}