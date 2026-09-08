package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.ParentStudentDAO;
import com.foxbrain.model.ParentStudent;

public class ParentStudentService {

    private final ParentStudentDAO dao;

    public ParentStudentService() {
        this.dao = new ParentStudentDAO();
    }

    public ParentStudent findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<ParentStudent> findAll() {
        return dao.findAll();
    }

    public long save(ParentStudent obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(ParentStudent obj) {
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
