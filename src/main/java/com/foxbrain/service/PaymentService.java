package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.PaymentDAO;
import com.foxbrain.model.Payment;

public class PaymentService {

    private final PaymentDAO dao;

    public PaymentService() {
        this.dao = new PaymentDAO();
    }

    public Payment findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Payment> findAll() {
        return dao.findAll();
    }

    public long save(Payment obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Payment obj) {
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
