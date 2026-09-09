package com.foxbrain.controller;

import com.foxbrain.model.Batch;
import com.foxbrain.model.Enrollment;
import com.foxbrain.model.Student;
import com.foxbrain.service.BatchService;
import com.foxbrain.service.EnrollmentService;
import com.foxbrain.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/enrollments")
public class EnrollmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private BatchService batchService;

    @Override
    public void init() {

        enrollmentService = new EnrollmentService();
        studentService = new StudentService();
        batchService = new BatchService();
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
                    deleteEnrollment(request, response);
                    break;

                default:
                    listEnrollments(request, response);
                    break;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/enrollments?error=database"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "create";
        }

        try {

            if ("create".equals(action)) {

                Enrollment enrollment =
                        buildEnrollmentFromRequest(request);

                enrollmentService.create(enrollment);

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/enrollments?success=created"
                );

            } else if ("update".equals(action)) {

                Enrollment enrollment =
                        buildEnrollmentFromRequest(request);

                enrollment.setId(
                        Long.parseLong(
                                request.getParameter("id")
                        )
                );

                enrollmentService.update(enrollment);

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/enrollments?success=updated"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/enrollments"
                );
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute("error", e.getMessage());

            try {

                if ("update".equals(action)) {
                    showEditForm(request, response);
                } else {
                    showAddForm(request, response);
                }

            } catch (SQLException sqlException) {

                sqlException.printStackTrace();

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/enrollments?error=database"
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/enrollments?error=database"
            );
        }
    }

    private void listEnrollments(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Enrollment> enrollments =
                enrollmentService.getAll();

        request.setAttribute(
                "enrollments",
                enrollments
        );

        request.setAttribute(
                "pageTitle",
                "Enrollments"
        );

        request.getRequestDispatcher(
                "/admin/enrollments/list.jsp"
        ).forward(request, response);
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        loadFormData(request);

        request.setAttribute(
                "pageTitle",
                "Add Enrollment"
        );

        request.getRequestDispatcher(
                "/admin/enrollments/add.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idParameter =
                request.getParameter("id");

        if (idParameter == null
                || idParameter.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/enrollments"
            );

            return;
        }

        long id = Long.parseLong(idParameter);

        Enrollment enrollment =
                enrollmentService.getById(id);

        if (enrollment == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Enrollment not found."
            );

            return;
        }

        loadFormData(request);

        request.setAttribute(
                "enrollment",
                enrollment
        );

        request.setAttribute(
                "pageTitle",
                "Edit Enrollment"
        );

        request.getRequestDispatcher(
                "/admin/enrollments/edit.jsp"
        ).forward(request, response);
    }

    private void loadFormData(
            HttpServletRequest request)
            throws SQLException {

        List<Student> students =
                studentService.getAll();

        List<Batch> batches =
                batchService.getAll();

        request.setAttribute(
                "students",
                students
        );

        request.setAttribute(
                "batches",
                batches
        );
    }

    private void deleteEnrollment(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, SQLException {

        String idParameter =
                request.getParameter("id");

        if (idParameter == null
                || idParameter.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/enrollments"
            );

            return;
        }

        long id = Long.parseLong(idParameter);

        enrollmentService.delete(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/enrollments?success=deleted"
        );
    }

    private Enrollment buildEnrollmentFromRequest(
            HttpServletRequest request) {

        Enrollment enrollment =
                new Enrollment();

        String studentId =
                request.getParameter("studentId");

        String batchId =
                request.getParameter("batchId");

        String enrollmentDate =
                request.getParameter("enrollmentDate");

        String completionDate =
                request.getParameter("completionDate");

        enrollment.setStudentId(
                Long.parseLong(studentId)
        );

        enrollment.setBatchId(
                Long.parseLong(batchId)
        );

        enrollment.setEnrollmentNumber(
                request.getParameter("enrollmentNumber")
        );

        if (enrollmentDate != null
                && !enrollmentDate.trim().isEmpty()) {

            enrollment.setEnrollmentDate(
                    LocalDate.parse(enrollmentDate)
            );
        }

        enrollment.setStatus(
                request.getParameter("status")
        );

        if (completionDate != null
                && !completionDate.trim().isEmpty()) {

            enrollment.setCompletionDate(
                    LocalDate.parse(completionDate)
            );
        }

        enrollment.setNotes(
                request.getParameter("notes")
        );

        return enrollment;
    }
}