package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.BatchDAO;
import com.foxbrain.model.Batch;

public class BatchService {

    private final BatchDAO dao;

    public BatchService() {
        this.dao = new BatchDAO();
    }

    public Batch findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Batch> findAll() {
        return dao.findAll();
    }

    public long save(Batch obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Batch obj) {
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
