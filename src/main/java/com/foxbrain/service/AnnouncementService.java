package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.AnnouncementDAO;
import com.foxbrain.model.Announcement;

public class AnnouncementService {

    private final AnnouncementDAO dao;

    public AnnouncementService() {
        this.dao = new AnnouncementDAO();
    }

    public Announcement findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Announcement> findAll() {
        return dao.findAll();
    }

    public long save(Announcement obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Announcement obj) {
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
