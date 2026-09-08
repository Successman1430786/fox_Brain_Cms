package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.CourseDAO;
import com.foxbrain.model.Course;

public class CourseService {

    private final CourseDAO dao;

    public CourseService() {
        this.dao = new CourseDAO();
    }

    public Course findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Course> findAll() {
        return dao.findAll();
    }

    public long save(Course obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Course obj) {
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
