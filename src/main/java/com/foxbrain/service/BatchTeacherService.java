package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.BatchTeacherDAO;
import com.foxbrain.model.BatchTeacher;

public class BatchTeacherService {

    private final BatchTeacherDAO dao;

    public BatchTeacherService() {
        this.dao = new BatchTeacherDAO();
    }

    public BatchTeacher findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<BatchTeacher> findAll() {
        return dao.findAll();
    }

    public long save(BatchTeacher obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(BatchTeacher obj) {
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
