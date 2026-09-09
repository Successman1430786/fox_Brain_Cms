package com.foxbrain.controller;

import com.foxbrain.model.Assignment;
import com.foxbrain.model.Batch;
import com.foxbrain.model.Teacher;
import com.foxbrain.service.AssignmentService;
import com.foxbrain.service.BatchService;
import com.foxbrain.service.TeacherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/assignments")
public class AssignmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AssignmentService assignmentService;
    private BatchService batchService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {

        assignmentService = new AssignmentService();
        batchService = new BatchService();
        teacherService = new TeacherService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {

            if ("add".equals(action)) {
                showAddForm(request, response);

            } else if ("edit".equals(action)) {
                showEditForm(request, response);

            } else if ("delete".equals(action)) {
                deleteAssignment(request, response);

            } else {
                showList(request, response);
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Database error in Assignment module.", e);
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

                Assignment assignment =
                        buildAssignmentFromRequest(request);

                assignmentService.create(assignment);

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/assignments?success=created");

            } else if ("update".equals(action)) {

                Assignment assignment =
                        buildAssignmentFromRequest(request);

                assignment.setId(
                        Long.parseLong(request.getParameter("id")));

                assignmentService.update(assignment);

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/assignments?success=updated");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/assignments");
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute("error", e.getMessage());

            try {
				loadFormData(request);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            if ("update".equals(action)) {
                request.setAttribute("assignment",
                        buildAssignmentFromRequestSafely(request));

                request.getRequestDispatcher(
                        "/admin/assignments/edit.jsp")
                        .forward(request, response);
            } else {

                request.setAttribute("assignment",
                        buildAssignmentFromRequestSafely(request));

                request.getRequestDispatcher(
                        "/admin/assignments/add.jsp")
                        .forward(request, response);
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Database error while saving assignment.", e);
        }
    }

    private void showList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Assignment> assignments =
                assignmentService.getAll();

        request.setAttribute("assignments", assignments);

        request.getRequestDispatcher(
                "/admin/assignments/list.jsp")
                .forward(request, response);
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        Assignment assignment = new Assignment();

        assignment.setAssignedDate(LocalDate.now());
        assignment.setSubmissionType("FILE");
        assignment.setAllowLateSubmission(false);
        assignment.setStatus("DRAFT");

        request.setAttribute("assignment", assignment);

        loadFormData(request);

        request.getRequestDispatcher(
                "/admin/assignments/add.jsp")
                .forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        long id = Long.parseLong(
                request.getParameter("id"));

        Assignment assignment =
                assignmentService.getById(id);

        if (assignment == null) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/assignments?error=notfound");
            return;
        }

        request.setAttribute("assignment", assignment);

        loadFormData(request);

        request.getRequestDispatcher(
                "/admin/assignments/edit.jsp")
                .forward(request, response);
    }

    private void deleteAssignment(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, IOException {

        long id = Long.parseLong(
                request.getParameter("id"));

        assignmentService.delete(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/assignments?success=deleted");
    }

    private Assignment buildAssignmentFromRequest(
            HttpServletRequest request) {

        Assignment a = new Assignment();

        a.setBatchId(
                Long.parseLong(request.getParameter("batchId")));

        a.setTeacherId(
                Long.parseLong(request.getParameter("teacherId")));

        a.setTitle(
                request.getParameter("title"));

        a.setDescription(
                request.getParameter("description"));

        a.setInstructions(
                request.getParameter("instructions"));

        String assignedDate =
                request.getParameter("assignedDate");

        if (assignedDate != null &&
                !assignedDate.trim().isEmpty()) {

            a.setAssignedDate(
                    LocalDate.parse(assignedDate));
        }

        String dueDate =
                request.getParameter("dueDate");

        if (dueDate != null &&
                !dueDate.trim().isEmpty()) {

            a.setDueDate(
                    LocalDate.parse(dueDate));
        }

        String maxMarks =
                request.getParameter("maxMarks");

        if (maxMarks != null &&
                !maxMarks.trim().isEmpty()) {

            a.setMaxMarks(
                    new BigDecimal(maxMarks));
        }

        a.setAttachmentUrl(
                request.getParameter("attachmentUrl"));

        String submissionType =
                request.getParameter("submissionType");

        a.setSubmissionType(submissionType);

        a.setAllowLateSubmission(
                "1".equals(request.getParameter("allowLateSubmission")));

        String latePenalty =
                request.getParameter("latePenalty");

        if (latePenalty != null &&
                !latePenalty.trim().isEmpty()) {

            a.setLatePenalty(
                    new BigDecimal(latePenalty));
        }

        a.setStatus(
                request.getParameter("status"));

        return a;
    }

    private Assignment buildAssignmentFromRequestSafely(
            HttpServletRequest request) {

        Assignment a = new Assignment();

        try {
            String batchId = request.getParameter("batchId");
            if (batchId != null && !batchId.isEmpty()) {
                a.setBatchId(Long.parseLong(batchId));
            }

            String teacherId = request.getParameter("teacherId");
            if (teacherId != null && !teacherId.isEmpty()) {
                a.setTeacherId(Long.parseLong(teacherId));
            }

            a.setTitle(request.getParameter("title"));
            a.setDescription(request.getParameter("description"));
            a.setInstructions(request.getParameter("instructions"));

            String assignedDate =
                    request.getParameter("assignedDate");

            if (assignedDate != null && !assignedDate.isEmpty()) {
                a.setAssignedDate(LocalDate.parse(assignedDate));
            }

            String dueDate =
                    request.getParameter("dueDate");

            if (dueDate != null && !dueDate.isEmpty()) {
                a.setDueDate(LocalDate.parse(dueDate));
            }

            String maxMarks =
                    request.getParameter("maxMarks");

            if (maxMarks != null && !maxMarks.isEmpty()) {
                a.setMaxMarks(new BigDecimal(maxMarks));
            }

            a.setAttachmentUrl(
                    request.getParameter("attachmentUrl"));

            a.setSubmissionType(
                    request.getParameter("submissionType"));

            a.setAllowLateSubmission(
                    "1".equals(
                            request.getParameter(
                                    "allowLateSubmission")));

            String latePenalty =
                    request.getParameter("latePenalty");

            if (latePenalty != null && !latePenalty.isEmpty()) {
                a.setLatePenalty(
                        new BigDecimal(latePenalty));
            }

            a.setStatus(
                    request.getParameter("status"));

        } catch (Exception ignored) {
        }

        return a;
    }

    private void loadFormData(
            HttpServletRequest request)
            throws SQLException {

        List<Batch> batches =
                batchService.getAll();

        List<Teacher> teachers =
                teacherService.getAll();

        request.setAttribute("batches", batches);
        request.setAttribute("teachers", teachers);
    }
}