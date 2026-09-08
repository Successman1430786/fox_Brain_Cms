package com.foxbrain.controller;

import com.foxbrain.model.Dashboard;
import com.foxbrain.service.DashboardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {

        dashboardService =
            new DashboardService();
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Dashboard dashboard =
                dashboardService.getDashboardData();

            request.setAttribute(
                "dashboard",
                dashboard
            );

            request.getRequestDispatcher(
                "/admin/dashboard.jsp"
            ).forward(
                request,
                response
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                "Unable to load dashboard data."
            );

            request.getRequestDispatcher(
                "/admin/dashboard.jsp"
            ).forward(
                request,
                response
            );
        }
    }
}