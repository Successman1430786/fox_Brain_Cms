package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.ExamResultDAO;
import com.foxbrain.model.ExamResult;

public class ExamResultService {

    private final ExamResultDAO dao;

    public ExamResultService() {
        this.dao = new ExamResultDAO();
    }

    public ExamResult findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<ExamResult> findAll() {
        return dao.findAll();
    }

    public long save(ExamResult obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(ExamResult obj) {
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
