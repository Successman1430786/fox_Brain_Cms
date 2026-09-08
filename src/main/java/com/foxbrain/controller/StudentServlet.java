package com.foxbrain.controller;

import com.foxbrain.model.Student;
import com.foxbrain.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/students")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentService();
    }

    // =========================
    // GET
    // =========================
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

                case "list":
                    listStudents(request, response);
                    break;

                case "add":
                    showAddForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "delete":
                    deleteStudent(request, response);
                    break;

                default:
                    response.sendRedirect(
                        request.getContextPath() + "/admin/students"
                    );
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                "An error occurred while processing the request."
            );

            request.getRequestDispatcher(
                "/admin/students/list.jsp"
            ).forward(request, response);
        }
    }

    // =========================
    // POST
    // =========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "create";
        }

        try {

            switch (action) {

                case "create":
                    createStudent(request, response);
                    break;

                case "update":
                    updateStudent(request, response);
                    break;

                default:
                    response.sendRedirect(
                        request.getContextPath() + "/admin/students"
                    );
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/students/list.jsp"
            ).forward(request, response);
        }
    }

    // =========================
    // LIST STUDENTS
    // =========================
    private void listStudents(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = studentService.getAll();

        request.setAttribute("students", students);

        request.getRequestDispatcher(
            "/admin/students/list.jsp"
        ).forward(request, response);
    }

    // =========================
    // SHOW ADD FORM
    // =========================
    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
            "/admin/students/add.jsp"
        ).forward(request, response);
    }

    // =========================
    // SHOW EDIT FORM
    // =========================
    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/admin/students"
            );

            return;
        }

        long id = Long.parseLong(idParameter);

        Student student = studentService.getById(id);

        if (student == null) {

            request.setAttribute(
                "errorMessage",
                "Student not found."
            );

            listStudents(request, response);

            return;
        }

        request.setAttribute("student", student);

        request.getRequestDispatcher(
            "/admin/students/edit.jsp"
        ).forward(request, response);
    }

    // =========================
    // CREATE STUDENT
    // =========================
    private void createStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Student student = buildStudentFromRequest(request);

        try {

            long studentId = studentService.create(student);

            if (studentId > 0) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/students?success=created"
                );

            } else {

                request.setAttribute(
                    "errorMessage",
                    "Student could not be created."
                );

                request.getRequestDispatcher(
                    "/admin/students/add.jsp"
                ).forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            String message = e.getMessage();

            if (message == null || message.trim().isEmpty()) {
                message = "Unknown error while creating student.";
            }

            request.setAttribute(
                "errorMessage",
                message
            );

            request.getRequestDispatcher(
                "/admin/students/add.jsp"
            ).forward(request, response);
        }
    }

    // =========================
    // UPDATE STUDENT
    // =========================
    private void updateStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/admin/students"
            );

            return;
        }

        Student student = buildStudentFromRequest(request);

        student.setId(Long.parseLong(idParameter));

        boolean updated = studentService.update(student);

        if (updated) {

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?success=updated"
            );

        } else {

            request.setAttribute(
                "errorMessage",
                "Student could not be updated."
            );

            request.setAttribute(
                "student",
                student
            );

            request.getRequestDispatcher(
                "/admin/students/edit.jsp"
            ).forward(request, response);
        }
    }

    // =========================
    // DELETE STUDENT
    // =========================
    private void deleteStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/admin/students"
            );

            return;
        }

        long id = Long.parseLong(idParameter);

        boolean deleted = studentService.delete(id);

        if (deleted) {

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?success=deleted"
            );

        } else {

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?error=delete"
            );
        }
    }

    // =========================
    // BUILD STUDENT OBJECT
    // =========================
    private Student buildStudentFromRequest(
            HttpServletRequest request) {

        Student student = new Student();

        String userId = request.getParameter("userId");

        if (userId != null && !userId.trim().isEmpty()) {
            student.setUserId(Long.parseLong(userId));
        }

        student.setAdmissionNumber(
            request.getParameter("admissionNumber")
        );

        String dateOfBirth =
            request.getParameter("dateOfBirth");

        if (dateOfBirth != null &&
            !dateOfBirth.trim().isEmpty()) {

            student.setDateOfBirth(
                LocalDate.parse(dateOfBirth)
            );
        }

        student.setGender(
            request.getParameter("gender")
        );

        student.setAddressLine1(
            request.getParameter("addressLine1")
        );

        student.setAddressLine2(
            request.getParameter("addressLine2")
        );

        student.setCity(
            request.getParameter("city")
        );

        student.setState(
            request.getParameter("state")
        );

        student.setPostalCode(
            request.getParameter("postalCode")
        );

        student.setCountry(
            request.getParameter("country")
        );

        String admissionDate =
            request.getParameter("admissionDate");

        if (admissionDate != null &&
            !admissionDate.trim().isEmpty()) {

            student.setAdmissionDate(
                LocalDate.parse(admissionDate)
            );
        }

        student.setStatus(
            request.getParameter("status")
        );

        return student;
    }
}