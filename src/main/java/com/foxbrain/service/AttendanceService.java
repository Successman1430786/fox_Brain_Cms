package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.AttendanceDAO;
import com.foxbrain.model.Attendance;

public class AttendanceService {

    private final AttendanceDAO dao;

    public AttendanceService() {
        this.dao = new AttendanceDAO();
    }

    public Attendance findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<Attendance> findAll() {
        return dao.findAll();
    }

    public long save(Attendance obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(Attendance obj) {
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
