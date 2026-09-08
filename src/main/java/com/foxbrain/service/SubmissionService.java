package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.SubmissionDAO;
import com.foxbrain.model.Submission;

public class SubmissionService {

    private final SubmissionDAO dao;

    public SubmissionService() {
        this.dao = new SubmissionDAO();
    }

    public Submission findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Submission> findAll() {
        return dao.findAll();
    }

    public long save(Submission obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Submission obj) {
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
