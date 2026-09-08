package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.EnquiryDAO;
import com.foxbrain.model.Enquiry;

public class EnquiryService {

    private final EnquiryDAO dao;

    public EnquiryService() {
        this.dao = new EnquiryDAO();
    }

    public Enquiry findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Enquiry> findAll() {
        return dao.findAll();
    }

    public long save(Enquiry obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Enquiry obj) {
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
