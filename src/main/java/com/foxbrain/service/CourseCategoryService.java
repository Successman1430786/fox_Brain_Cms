package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.CourseCategoryDAO;
import com.foxbrain.model.CourseCategory;

public class CourseCategoryService {

    private final CourseCategoryDAO dao;

    public CourseCategoryService() {
        this.dao = new CourseCategoryDAO();
    }

    public CourseCategory findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<CourseCategory> findAll() {
        return dao.findAll();
    }

    public long save(CourseCategory obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(CourseCategory obj) {
        if (obj == null || obj.getId() <= 0) {
            throw new IllegalArgumentException("Invalid object or ID");
        }
        return dao.update(obj);
    }

    public boolean delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.delete(id);
    }
}
