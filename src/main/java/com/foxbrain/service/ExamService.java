package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.ExamDAO;
import com.foxbrain.model.Exam;

public class ExamService {

    private final ExamDAO dao;

    public ExamService() {
        this.dao = new ExamDAO();
    }

    public Exam findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Exam> findAll() {
        return dao.findAll();
    }

    public long save(Exam obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Exam obj) {
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
