package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.AssignmentDAO;
import com.foxbrain.model.Assignment;

public class AssignmentService {

    private final AssignmentDAO dao;

    public AssignmentService() {
        this.dao = new AssignmentDAO();
    }

    public Assignment findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Assignment> findAll() {
        return dao.findAll();
    }

    public long save(Assignment obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Assignment obj) {
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
