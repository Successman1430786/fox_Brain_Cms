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

@WebServlet("/admin/students")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentService();
    }

    // =========================================================
    // GET
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "add":
                showAddForm(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            case "delete":
                deleteStudent(request, response);
                break;

            case "list":
            default:
                showStudentList(request, response);
                break;
        }
    }

    // =========================================================
    // POST
    // =========================================================

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

            switch (action) {

                case "create":
                    createStudent(request, response);
                    break;

                case "update":
                    updateStudent(request, response);
                    break;

                default:
                    response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid action."
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

    // =========================================================
    // SHOW STUDENT LIST
    // =========================================================

    private void showStudentList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                "students",
                studentService.getAll()
            );

            request.getRequestDispatcher(
                "/admin/students/list.jsp"
            ).forward(request, response);

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

    // =========================================================
    // SHOW ADD FORM
    // =========================================================

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
            "/admin/students/add.jsp"
        ).forward(request, response);
    }

    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParam =
            request.getParameter("id");

        if (idParam == null ||
                idParam.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Student ID is required."
            );

            return;
        }

        try {

            long id =
                Long.parseLong(idParam);

            Student student =
                studentService.getById(id);

            if (student == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Student not found."
                );

                return;
            }

            request.setAttribute(
                "student",
                student
            );

            request.getRequestDispatcher(
                "/admin/students/edit.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid student ID."
            );
        }
    }

    // =========================================================
    // CREATE STUDENT + USER ACCOUNT
    // =========================================================

    private void createStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // -------------------------------------------------
            // ACCOUNT INFORMATION
            // -------------------------------------------------

            String username =
                getParameter(request, "username");

            String email =
                getParameter(request, "email");

            String password =
                request.getParameter("password");

            String firstName =
                getParameter(request, "firstName");

            String lastName =
                getParameter(request, "lastName");

            String phone =
                getParameter(request, "phone");

            // -------------------------------------------------
            // STUDENT INFORMATION
            // -------------------------------------------------

            Student student =
                buildStudentFromRequest(request);

            // -------------------------------------------------
            // CREATE USER + STUDENT
            // -------------------------------------------------

            long studentId =
                studentService.createStudentWithAccount(
                    student,
                    username,
                    email,
                    password,
                    firstName,
                    lastName,
                    phone
                );

            // -------------------------------------------------
            // SUCCESS
            // -------------------------------------------------

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?created="
                + studentId
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/students/add.jsp"
            ).forward(request, response);
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    private void updateStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idParam =
                request.getParameter("id");

            if (idParam == null ||
                    idParam.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Student ID is required."
                );
            }

            long id =
                Long.parseLong(idParam);

            Student student =
                buildStudentFromRequest(request);

            student.setId(id);

            // -------------------------------------------------
            // IMPORTANT
            // For update we keep the existing user_id.
            // -------------------------------------------------

            String userIdParam =
                request.getParameter("userId");

            if (userIdParam == null ||
                    userIdParam.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Student user ID is required for update."
                );
            }

            long userId =
                Long.parseLong(userIdParam);

            student.setUserId(userId);

            boolean updated =
                studentService.update(student);

            if (!updated) {

                throw new RuntimeException(
                    "Student could not be updated."
                );
            }

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?updated="
                + id
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/students/edit.jsp"
            ).forward(request, response);
        }
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void deleteStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParam =
            request.getParameter("id");

        if (idParam == null ||
                idParam.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Student ID is required."
            );

            return;
        }

        try {

            long id =
                Long.parseLong(idParam);

            boolean deleted =
                studentService.delete(id);

            if (deleted) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/students?deleted="
                    + id
                );

            } else {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/students?error="
                    + "Student could not be deleted"
                );
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid student ID."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                request.getContextPath()
                + "/admin/students?error="
                + "Unable to delete student"
            );
        }
    }

    // =========================================================
    // BUILD STUDENT FROM REQUEST
    // =========================================================

    private Student buildStudentFromRequest(
            HttpServletRequest request) {

        Student student =
            new Student();

        // -----------------------------------------------------
        // Admission Number
        // -----------------------------------------------------

        student.setAdmissionNumber(
            getParameter(
                request,
                "admissionNumber"
            )
        );

        // -----------------------------------------------------
        // Date of Birth
        // -----------------------------------------------------

        String dob =
            getParameter(
                request,
                "dateOfBirth"
            );

        if (dob != null &&
                !dob.isEmpty()) {

            student.setDateOfBirth(
                LocalDate.parse(dob)
            );
        }

        // -----------------------------------------------------
        // Gender
        // -----------------------------------------------------

        student.setGender(
            getParameter(
                request,
                "gender"
            )
        );

        // -----------------------------------------------------
        // Address
        // -----------------------------------------------------

        student.setAddressLine1(
            getParameter(
                request,
                "addressLine1"
            )
        );

        student.setAddressLine2(
            getParameter(
                request,
                "addressLine2"
            )
        );

        student.setCity(
            getParameter(
                request,
                "city"
            )
        );

        student.setState(
            getParameter(
                request,
                "state"
            )
        );

        student.setPostalCode(
            getParameter(
                request,
                "postalCode"
            )
        );

        student.setCountry(
            getParameter(
                request,
                "country"
            )
        );

        // -----------------------------------------------------
        // Admission Date
        // -----------------------------------------------------

        String admissionDate =
            getParameter(
                request,
                "admissionDate"
            );

        if (admissionDate != null &&
                !admissionDate.isEmpty()) {

            student.setAdmissionDate(
                LocalDate.parse(
                    admissionDate
                )
            );
        }

        // -----------------------------------------------------
        // Status
        // -----------------------------------------------------

        String status =
            getParameter(
                request,
                "status"
            );

        if (status == null ||
                status.isEmpty()) {

            status = "ACTIVE";
        }

        student.setStatus(status);

        return student;
    }

    // =========================================================
    // GET CLEAN PARAMETER
    // =========================================================

    private String getParameter(
            HttpServletRequest request,
            String name) {

        String value =
            request.getParameter(name);

        if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.isEmpty()) {
            return null;
        }

        return value;
    }
}