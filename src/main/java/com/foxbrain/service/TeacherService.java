package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.TeacherDAO;
import com.foxbrain.model.Teacher;

public class TeacherService {

    private final TeacherDAO dao;

    public TeacherService() {
        this.dao = new TeacherDAO();
    }

    public Teacher findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Teacher> findAll() {
        return dao.findAll();
    }

    public long save(Teacher obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Teacher obj) {
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
