package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.FeeAccountDAO;
import com.foxbrain.model.FeeAccount;

public class FeeAccountService {

    private final FeeAccountDAO dao;

    public FeeAccountService() {
        this.dao = new FeeAccountDAO();
    }

    public FeeAccount findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<FeeAccount> findAll() {
        return dao.findAll();
    }

    public long save(FeeAccount obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(FeeAccount obj) {
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
