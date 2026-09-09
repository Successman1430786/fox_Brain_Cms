package com.foxbrain.controller;

import com.foxbrain.model.Admission;
import com.foxbrain.model.Batch;
import com.foxbrain.model.Course;
import com.foxbrain.service.AdmissionService;
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
import java.util.List;

@WebServlet("/admin/admissions")
public class AdmissionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AdmissionService admissionService;
    private CourseService courseService;
    private BatchService batchService;

    @Override
    public void init() throws ServletException {

        admissionService = new AdmissionService();
        courseService = new CourseService();
        batchService = new BatchService();
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

                deleteAdmission(request, response);

            } else {

                listAdmissions(request, response);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Database error. Please try again."
            );

            request.getRequestDispatcher(
                    "/admin/admissions/list.jsp"
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

                createAdmission(request, response);

            } else if ("update".equals(action)) {

                updateAdmission(request, response);

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/admissions"
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Database error. Please try again."
            );

            request.getRequestDispatcher(
                    "/admin/admissions/list.jsp"
            ).forward(request, response);

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            String id = request.getParameter("id");

            if ("update".equals(action)
                    && id != null
                    && !id.trim().isEmpty()) {

                try {

                    showEditForm(request, response);

                } catch (SQLException sqlException) {

                    sqlException.printStackTrace();

                    request.setAttribute(
                            "error",
                            "Database error. Please try again."
                    );

                    request.getRequestDispatcher(
                            "/admin/admissions/list.jsp"
                    ).forward(request, response);
                }

            } else {

                try {

                    showAddForm(request, response);

                } catch (SQLException sqlException) {

                    sqlException.printStackTrace();

                    request.setAttribute(
                            "error",
                            "Database error. Please try again."
                    );

                    request.getRequestDispatcher(
                            "/admin/admissions/list.jsp"
                    ).forward(request, response);
                }
            }
        }
    }

    private void listAdmissions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Admission> admissions =
                admissionService.getAll();

        request.setAttribute(
                "admissions",
                admissions
        );

        request.getRequestDispatcher(
                "/admin/admissions/list.jsp"
        ).forward(request, response);
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Course> courses =
                courseService.getAll();

        List<Batch> batches =
                batchService.getAll();

        request.setAttribute(
                "courses",
                courses
        );

        request.setAttribute(
                "batches",
                batches
        );

        request.getRequestDispatcher(
                "/admin/admissions/add.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/admissions"
            );

            return;
        }

        long id = Long.parseLong(idParam);

        Admission admission =
                admissionService.getById(id);

        if (admission == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Admission not found."
            );

            return;
        }

        List<Course> courses =
                courseService.getAll();

        List<Batch> batches =
                batchService.getAll();

        request.setAttribute(
                "admission",
                admission
        );

        request.setAttribute(
                "courses",
                courses
        );

        request.setAttribute(
                "batches",
                batches
        );

        request.getRequestDispatcher(
                "/admin/admissions/edit.jsp"
        ).forward(request, response);
    }

    private void createAdmission(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, SQLException {

        Admission admission =
                buildAdmissionFromRequest(request);

        admissionService.create(admission);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/admissions?success=created"
        );
    }

    private void updateAdmission(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, SQLException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invalid admission ID."
            );
        }

        Admission admission =
                buildAdmissionFromRequest(request);

        admission.setId(
                Long.parseLong(idParam)
        );

        admissionService.update(admission);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/admissions?success=updated"
        );
    }

    private void deleteAdmission(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, SQLException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invalid admission ID."
            );
        }

        long id = Long.parseLong(idParam);

        admissionService.delete(id);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/admissions?success=deleted"
        );
    }

    private Admission buildAdmissionFromRequest(
            HttpServletRequest request) {

        Admission admission = new Admission();

        admission.setApplicationNumber(
                request.getParameter("applicationNumber")
        );

        String courseId =
                request.getParameter("courseId");

        if (courseId != null
                && !courseId.trim().isEmpty()) {

            admission.setCourseId(
                    Long.parseLong(courseId)
            );
        }

        String batchId =
                request.getParameter("batchId");

        if (batchId != null
                && !batchId.trim().isEmpty()) {

            admission.setBatchId(
                    Long.parseLong(batchId)
            );
        }

        String applicationDate =
                request.getParameter("applicationDate");

        if (applicationDate != null
                && !applicationDate.trim().isEmpty()) {

            admission.setApplicationDate(
                    LocalDate.parse(applicationDate)
            );
        }

        admission.setFirstName(
                request.getParameter("firstName")
        );

        admission.setLastName(
                request.getParameter("lastName")
        );

        admission.setEmail(
                request.getParameter("email")
        );

        admission.setPhone(
                request.getParameter("phone")
        );

        String dob =
                request.getParameter("dateOfBirth");

        if (dob != null
                && !dob.trim().isEmpty()) {

            admission.setDateOfBirth(
                    LocalDate.parse(dob)
            );
        }

        admission.setGender(
                request.getParameter("gender")
        );

        admission.setAddressLine1(
                request.getParameter("addressLine1")
        );

        admission.setAddressLine2(
                request.getParameter("addressLine2")
        );

        admission.setCity(
                request.getParameter("city")
        );

        admission.setState(
                request.getParameter("state")
        );

        admission.setPostalCode(
                request.getParameter("postalCode")
        );

        admission.setCountry(
                request.getParameter("country")
        );

        admission.setQualification(
                request.getParameter("qualification")
        );

        admission.setSource(
                request.getParameter("source")
        );

        admission.setStatus(
                request.getParameter("status")
        );

        admission.setNotes(
                request.getParameter("notes")
        );

        return admission;
    }
}