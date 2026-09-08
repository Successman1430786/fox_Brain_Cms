package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.ParentDAO;
import com.foxbrain.model.Parent;

public class ParentService {

    private final ParentDAO dao;

    public ParentService() {
        this.dao = new ParentDAO();
    }

    public Parent findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Parent> findAll() {
        return dao.findAll();
    }

    public long save(Parent obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Parent obj) {
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
