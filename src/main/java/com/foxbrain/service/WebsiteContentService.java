package com.foxbrain.service;

import java.util.List;

import com.foxbrain.dao.WebsiteContentDAO;
import com.foxbrain.model.WebsiteContent;

public class WebsiteContentService {

    private final WebsiteContentDAO dao;

    public WebsiteContentService() {
        this.dao = new WebsiteContentDAO();
    }

    public WebsiteContent findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return dao.findById(id);
    }

    public List<WebsiteContent> findAll() {
        return dao.findAll();
    }

    public long save(WebsiteContent obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        return dao.insert(obj);
    }

    public boolean update(WebsiteContent obj) {
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
