package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.InstituteSettingDAO;
import com.foxbrain.model.InstituteSetting;

public class InstituteSettingService {

    private final InstituteSettingDAO dao;

    public InstituteSettingService() {
        this.dao = new InstituteSettingDAO();
    }

    public InstituteSetting findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<InstituteSetting> findAll() {
        return dao.findAll();
    }

    public long save(InstituteSetting obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(InstituteSetting obj) {
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
