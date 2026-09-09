package com.foxbrain.controller;

import com.foxbrain.model.Batch;
import com.foxbrain.model.Course;
import com.foxbrain.service.BatchService;
import com.foxbrain.service.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/admin/batches")
public class BatchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BatchService batchService;
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        batchService = new BatchService();
        courseService = new CourseService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        try {

            switch (action) {

                case "add":
                    showAddForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "delete":
                    deleteBatch(request, response);
                    break;

                default:
                    listBatches(request, response);
                    break;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Database error. Please try again."
            );

            request.getRequestDispatcher(
                    "/admin/batches/list.jsp"
            ).forward(request, response);
        }
    }

    
 @Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String action = request.getParameter("action");

    try {

        if ("create".equals(action)) {

            createBatch(request, response);

        } else if ("update".equals(action)) {

            updateBatch(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath() +
                    "/admin/batches"
            );
        }

    } catch (SQLException e) {

        e.printStackTrace();

        request.setAttribute(
                "error",
                "Database error. Please try again."
        );

        request.getRequestDispatcher(
                "/admin/batches/list.jsp"
        ).forward(request, response);

    } catch (IllegalArgumentException e) {

        request.setAttribute(
                "error",
                e.getMessage()
        );

        String id = request.getParameter("id");

        try {

            if ("update".equals(action)
                    && id != null
                    && !id.trim().isEmpty()) {

                showEditForm(request, response);

            } else {

                showAddForm(request, response);
            }

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

            request.setAttribute(
                    "error",
                    "Database error. Please try again."
            );

            request.getRequestDispatcher(
                    "/admin/batches/list.jsp"
            ).forward(request, response);
        }
    }
}
    private void listBatches(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Batch> batches = batchService.getAll();

        request.setAttribute("batches", batches);
        request.setAttribute("pageTitle", "Batches");

        request.getRequestDispatcher(
                "/admin/batches/list.jsp"
        ).forward(request, response);
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Course> courses = courseService.getActive();

        request.setAttribute("courses", courses);
        request.setAttribute("pageTitle", "Add Batch");

        request.getRequestDispatcher(
                "/admin/batches/add.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Batch ID is required."
            );
            return;
        }

        long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid batch ID."
            );
            return;
        }

        Batch batch = batchService.getById(id);

        if (batch == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Batch not found."
            );
            return;
        }

        List<Course> courses = courseService.getActive();

        request.setAttribute("batch", batch);
        request.setAttribute("courses", courses);
        request.setAttribute("pageTitle", "Edit Batch");

        request.getRequestDispatcher(
                "/admin/batches/edit.jsp"
        ).forward(request, response);
    }

    private void createBatch(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, IOException {

        Batch batch = buildBatchFromRequest(request);

        batchService.create(batch);

        response.sendRedirect(
                request.getContextPath() +
                "/admin/batches?success=created"
        );
    }

    private void updateBatch(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, IOException {

        Batch batch = buildBatchFromRequest(request);

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Batch ID is required."
            );
        }

        batch.setId(Long.parseLong(idParam));

        batchService.update(batch);

        response.sendRedirect(
                request.getContextPath() +
                "/admin/batches?success=updated"
        );
    }

    private void deleteBatch(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() +
                    "/admin/batches?error=invalid"
            );
            return;
        }

        try {

            long id = Long.parseLong(idParam);

            batchService.delete(id);

            response.sendRedirect(
                    request.getContextPath() +
                    "/admin/batches?success=deleted"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() +
                    "/admin/batches?error=invalid"
            );
        }
    }

    private Batch buildBatchFromRequest(
            HttpServletRequest request) {

        Batch batch = new Batch();

        String courseId = request.getParameter("courseId");

        if (courseId != null &&
                !courseId.trim().isEmpty()) {

            batch.setCourseId(
                    Long.parseLong(courseId)
            );
        }

        batch.setBatchCode(
                trim(request.getParameter("batchCode"))
        );

        batch.setName(
                trim(request.getParameter("name"))
        );

        String startDate =
                request.getParameter("startDate");

        if (startDate != null &&
                !startDate.trim().isEmpty()) {

            batch.setStartDate(
                    LocalDate.parse(startDate)
            );
        }

        String endDate =
                request.getParameter("endDate");

        if (endDate != null &&
                !endDate.trim().isEmpty()) {

            batch.setEndDate(
                    LocalDate.parse(endDate)
            );
        }

        String startTime =
                request.getParameter("startTime");

        if (startTime != null &&
                !startTime.trim().isEmpty()) {

            batch.setStartTime(
                    LocalTime.parse(startTime)
            );
        }

        String endTime =
                request.getParameter("endTime");

        if (endTime != null &&
                !endTime.trim().isEmpty()) {

            batch.setEndTime(
                    LocalTime.parse(endTime)
            );
        }

        batch.setRoomName(
                trim(request.getParameter("roomName"))
        );

        String capacity =
                request.getParameter("capacity");

        if (capacity != null &&
                !capacity.trim().isEmpty()) {

            batch.setCapacity(
                    Integer.parseInt(capacity)
            );
        }

        String status =
                request.getParameter("status");

        batch.setStatus(
                status == null || status.trim().isEmpty()
                        ? "PLANNED"
                        : status
        );

        batch.setNotes(
                trim(request.getParameter("notes"))
        );

        return batch;
    }

    private String trim(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isEmpty() ? null : value;
    }
}