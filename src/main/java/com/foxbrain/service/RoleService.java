package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.RoleDAO;
import com.foxbrain.model.Role;

public class RoleService {

    private final RoleDAO dao;

    public RoleService() {
        this.dao = new RoleDAO();
    }

    public Role findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Role> findAll() {
        return dao.findAll();
    }

    public long save(Role obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Role obj) {
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
