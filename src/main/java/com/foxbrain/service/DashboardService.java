package com.foxbrain.service;

import com.foxbrain.dao.DashboardDAO;
import com.foxbrain.model.Dashboard;

public class DashboardService {

    private final DashboardDAO dashboardDAO;

    public DashboardService() {
        dashboardDAO = new DashboardDAO();
    }

    public Dashboard getDashboardData() {

        return dashboardDAO.getDashboardData();
    }
}