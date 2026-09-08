package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.EnrollmentDAO;
import com.foxbrain.model.Enrollment;

public class EnrollmentService {

    private final EnrollmentDAO dao;

    public EnrollmentService() {
        this.dao = new EnrollmentDAO();
    }

    public Enrollment findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Enrollment> findAll() {
        return dao.findAll();
    }

    public long save(Enrollment obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Enrollment obj) {
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
