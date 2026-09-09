package com.foxbrain.controller;

import com.foxbrain.model.Teacher;
import com.foxbrain.service.TeacherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import java.sql.SQLException;

@WebServlet("/admin/teachers")
public class TeacherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TeacherService teacherService;


    @Override
    public void init()
            throws ServletException {

        teacherService =
            new TeacherService();
    }


    // =========================================================
    // GET
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
            request.getParameter("action");


        if (action == null ||
            action.trim().isEmpty()) {

            action = "list";
        }


        switch (action) {

            case "add":
                showAddForm(
                    request,
                    response
                );
                break;


            case "edit":
                showEditForm(
                    request,
                    response
                );
                break;


            case "delete":
                deleteTeacher(
                    request,
                    response
                );
                break;


            case "list":
            default:
                showTeacherList(
                    request,
                    response
                );
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


        String action =
            request.getParameter("action");


        if (action == null ||
            action.trim().isEmpty()) {

            action = "create";
        }


        try {

            switch (action) {

                case "create":
                    createTeacher(
                        request,
                        response
                    );
                    break;


                case "update":
                    updateTeacher(
                        request,
                        response
                    );
                    break;


                default:

                    response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid action."
                    );
            }


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/teachers/list.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    // =========================================================
    // LIST
    // =========================================================

    private void showTeacherList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                "teachers",
                teacherService.getAll()
            );


            request.getRequestDispatcher(
                "/admin/teachers/list.jsp"
            ).forward(
                request,
                response
            );


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );


            request.getRequestDispatcher(
                "/admin/teachers/list.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    // =========================================================
    // ADD FORM
    // =========================================================

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
            "/admin/teachers/add.jsp"
        ).forward(
            request,
            response
        );
    }


    // =========================================================
    // EDIT FORM
    // =========================================================

   private void showEditForm(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    String idParam = request.getParameter("id");

    if (idParam == null || idParam.trim().isEmpty()) {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Teacher ID is required."
        );
        return;
    }

    try {

        long id = Long.parseLong(idParam);

        Teacher teacher = teacherService.getById(id);

        if (teacher == null) {
            response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Teacher not found."
            );
            return;
        }

        request.setAttribute("teacher", teacher);

        request.getRequestDispatcher(
            "/admin/teachers/edit.jsp"
        ).forward(request, response);

    } catch (NumberFormatException e) {

        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid teacher ID."
        );

    } catch (SQLException e) {

        e.printStackTrace();

        response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Database error while loading teacher."
        );
    }
}
    // =========================================================
    // CREATE
    // =========================================================

    private void createTeacher(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String username =
                getParameter(
                    request,
                    "username"
                );

            String email =
                getParameter(
                    request,
                    "email"
                );

            String password =
                request.getParameter(
                    "password"
                );

            String firstName =
                getParameter(
                    request,
                    "firstName"
                );

            String lastName =
                getParameter(
                    request,
                    "lastName"
                );

            String phone =
                getParameter(
                    request,
                    "phone"
                );


            Teacher teacher =
                buildTeacherFromRequest(
                    request
                );


            long teacherId =
                teacherService
                    .createTeacherWithAccount(
                        teacher,
                        username,
                        email,
                        password,
                        firstName,
                        lastName,
                        phone
                    );


            response.sendRedirect(
                request.getContextPath()
                + "/admin/teachers?created="
                + teacherId
            );


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );


            request.getRequestDispatcher(
                "/admin/teachers/add.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    // =========================================================
    // UPDATE
    // =========================================================

    private void updateTeacher(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idParam =
                request.getParameter("id");


            if (idParam == null ||
                idParam.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Teacher ID is required."
                );
            }


            long id =
                Long.parseLong(idParam);


            Teacher existing =
                teacherService.getById(id);


            if (existing == null) {

                throw new IllegalArgumentException(
                    "Teacher not found."
                );
            }


            Teacher teacher =
                buildTeacherFromRequest(
                    request
                );


            teacher.setId(
                existing.getId()
            );


            teacher.setUserId(
                existing.getUserId()
            );


            boolean updated =
                teacherService.update(
                    teacher
                );


            if (!updated) {

                throw new RuntimeException(
                    "Teacher could not be updated."
                );
            }


            response.sendRedirect(
                request.getContextPath()
                + "/admin/teachers?updated="
                + id
            );


        } catch (NumberFormatException e) {

            request.setAttribute(
                "errorMessage",
                "Invalid teacher ID."
            );


            request.getRequestDispatcher(
                "/admin/teachers/edit.jsp"
            ).forward(
                request,
                response
            );


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );


            request.getRequestDispatcher(
                "/admin/teachers/edit.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    // =========================================================
    // DELETE
    // =========================================================

    private void deleteTeacher(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParam =
            request.getParameter("id");


        if (idParam == null ||
            idParam.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Teacher ID is required."
            );

            return;
        }


        try {

            long id =
                Long.parseLong(idParam);


            boolean deleted =
                teacherService.delete(id);


            if (deleted) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/teachers?deleted="
                    + id
                );

            } else {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/teachers?error="
                    + "Teacher could not be deleted"
                );
            }


        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid teacher ID."
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                request.getContextPath()
                + "/admin/teachers?error="
                + "Unable to delete teacher"
            );
        }
    }


    // =========================================================
    // BUILD TEACHER
    // =========================================================

    private Teacher buildTeacherFromRequest(
            HttpServletRequest request) {

        Teacher teacher =
            new Teacher();


        teacher.setEmployeeNumber(
            getParameter(
                request,
                "employeeNumber"
            )
        );


        teacher.setQualification(
            getParameter(
                request,
                "qualification"
            )
        );


        teacher.setSpecialization(
            getParameter(
                request,
                "specialization"
            )
        );


        String joiningDate =
            getParameter(
                request,
                "joiningDate"
            );


        if (joiningDate != null &&
            !joiningDate.isEmpty()) {

            teacher.setJoiningDate(
                LocalDate.parse(
                    joiningDate
                )
            );
        }


        teacher.setAddressLine1(
            getParameter(
                request,
                "addressLine1"
            )
        );


        teacher.setAddressLine2(
            getParameter(
                request,
                "addressLine2"
            )
        );


        teacher.setCity(
            getParameter(
                request,
                "city"
            )
        );


        teacher.setState(
            getParameter(
                request,
                "state"
            )
        );


        teacher.setPostalCode(
            getParameter(
                request,
                "postalCode"
            )
        );


        teacher.setCountry(
            getParameter(
                request,
                "country"
            )
        );


        String status =
            getParameter(
                request,
                "status"
            );


        if (status == null ||
            status.isEmpty()) {

            status = "ACTIVE";
        }


        teacher.setStatus(status);


        teacher.setBio(
            getParameter(
                request,
                "bio"
            )
        );


        return teacher;
    }


    // =========================================================
    // PARAMETER
    // =========================================================

    private String getParameter(
            HttpServletRequest request,
            String name) {

        String value =
            request.getParameter(name);


        if (value == null) {
            return null;
        }


        value =
            value.trim();


        if (value.isEmpty()) {
            return null;
        }


        return value;
    }
}