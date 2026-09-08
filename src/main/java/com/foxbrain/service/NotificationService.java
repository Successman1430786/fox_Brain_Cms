package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.NotificationDAO;
import com.foxbrain.model.Notification;

public class NotificationService {

    private final NotificationDAO dao;

    public NotificationService() {
        this.dao = new NotificationDAO();
    }

    public Notification findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Notification> findAll() {
        return dao.findAll();
    }

    public long save(Notification obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Notification obj) {
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
