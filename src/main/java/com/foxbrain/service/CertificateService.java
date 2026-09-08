package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.CertificateDAO;
import com.foxbrain.model.Certificate;

public class CertificateService {

    private final CertificateDAO dao;

    public CertificateService() {
        this.dao = new CertificateDAO();
    }

    public Certificate findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Certificate> findAll() {
        return dao.findAll();
    }

    public long save(Certificate obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Certificate obj) {
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
