package com.foxbrain.controller;

import com.foxbrain.model.Course;
import com.foxbrain.model.CourseCategory;
import com.foxbrain.service.CourseService;
import com.foxbrain.service.CourseCategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/courses")
public class CourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CourseService courseService;
    private CourseCategoryService categoryService;


    @Override
    public void init() {

        courseService =
            new CourseService();

        categoryService =
            new CourseCategoryService();
    }


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


        try {

            switch (action) {

                case "add":
                    showAddForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "delete":
                    deleteCourse(request, response);
                    break;

                default:
                    listCourses(request, response);
                    break;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Database error."
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action =
            request.getParameter("action");


        try {

            if ("create".equals(action)) {

                createCourse(request, response);

            } else if ("update".equals(action)) {

                updateCourse(request, response);

            } else {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid action."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Database error."
            );
        }
    }


    private void listCourses(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Course> courses =
            courseService.getAll();

        request.setAttribute(
            "courses",
            courses
        );

        request.setAttribute(
            "pageTitle",
            "Courses"
        );

        request.getRequestDispatcher(
            "/admin/courses/list.jsp"
        ).forward(request, response);
    }


    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        loadCategories(request);

        request.setAttribute(
            "pageTitle",
            "Add Course"
        );

        request.getRequestDispatcher(
            "/admin/courses/add.jsp"
        ).forward(request, response);
    }


    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idParam =
            request.getParameter("id");

        if (idParam == null ||
            idParam.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Course ID is required."
            );

            return;
        }


        try {

            long id =
                Long.parseLong(idParam);

            Course course =
                courseService.getById(id);

            if (course == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Course not found."
                );

                return;
            }


            loadCategories(request);

            request.setAttribute(
                "course",
                course
            );

            request.setAttribute(
                "pageTitle",
                "Edit Course"
            );

            request.getRequestDispatcher(
                "/admin/courses/edit.jsp"
            ).forward(request, response);


        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid course ID."
            );
        }
    }


    private void createCourse(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        try {

            Course course =
                buildCourseFromRequest(request);

            courseService.create(course);

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?created=true"
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            loadCategories(request);

            request.setAttribute(
                "pageTitle",
                "Add Course"
            );

            request.getRequestDispatcher(
                "/admin/courses/add.jsp"
            ).forward(request, response);
        }
    }


    private void updateCourse(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        try {

            Course course =
                buildCourseFromRequest(request);

            String idParam =
                request.getParameter("id");

            if (idParam == null ||
                idParam.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Course ID is required."
                );
            }

            course.setId(
                Long.parseLong(idParam)
            );

            courseService.update(course);

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?updated=true"
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                "errorMessage",
                "Invalid course ID."
            );

            loadCategories(request);

            request.setAttribute(
                "pageTitle",
                "Edit Course"
            );

            request.getRequestDispatcher(
                "/admin/courses/edit.jsp"
            ).forward(request, response);

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            loadCategories(request);

            request.setAttribute(
                "pageTitle",
                "Edit Course"
            );

            Course course =
                buildCourseForError(request);

            request.setAttribute(
                "course",
                course
            );

            request.getRequestDispatcher(
                "/admin/courses/edit.jsp"
            ).forward(request, response);
        }
    }


    private void deleteCourse(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParam =
            request.getParameter("id");

        if (idParam == null ||
            idParam.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?error=invalid"
            );

            return;
        }


        try {

            long id =
                Long.parseLong(idParam);

            courseService.delete(id);

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?deleted=true"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?error=invalid"
            );

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendRedirect(
                request.getContextPath() +
                "/admin/courses?error=database"
            );
        }
    }


    private Course buildCourseFromRequest(
            HttpServletRequest request) {

        Course course = new Course();

        String categoryId =
            request.getParameter("categoryId");

        if (categoryId != null &&
            !categoryId.trim().isEmpty()) {

            course.setCategoryId(
                Long.parseLong(categoryId)
            );
        }


        course.setName(
            trim(request.getParameter("name"))
        );

        course.setSlug(
            trim(request.getParameter("slug"))
        );

        course.setShortDescription(
            trim(request.getParameter(
                "shortDescription"
            ))
        );

        course.setDescription(
            trim(request.getParameter(
                "description"
            ))
        );


        String durationValue =
            request.getParameter(
                "durationValue"
            );

        if (durationValue != null &&
            !durationValue.trim().isEmpty()) {

            course.setDurationValue(
                Integer.parseInt(
                    durationValue
                )
            );
        }


        course.setDurationUnit(
            trim(request.getParameter(
                "durationUnit"
            ))
        );


        String fee =
            request.getParameter("fee");

        if (fee != null &&
            !fee.trim().isEmpty()) {

            course.setFee(
                new BigDecimal(fee)
            );
        }


        String currency =
            trim(request.getParameter(
                "currencyCode"
            ));

        if (currency == null ||
            currency.isEmpty()) {

            currency = "INR";
        }

        course.setCurrencyCode(currency);


        String maxStudents =
            request.getParameter(
                "maxStudents"
            );

        if (maxStudents != null &&
            !maxStudents.trim().isEmpty()) {

            course.setMaxStudents(
                Integer.parseInt(maxStudents)
            );
        }


        course.setSyllabus(
            trim(request.getParameter(
                "syllabus"
            ))
        );

        course.setImageUrl(
            trim(request.getParameter(
                "imageUrl"
            ))
        );

        course.setStatus(
            trim(request.getParameter(
                "status"
            ))
        );


        course.setFeatured(
            "true".equals(
                request.getParameter(
                    "featured"
                )
            )
        );


        String displayOrder =
            request.getParameter(
                "displayOrder"
            );

        if (displayOrder == null ||
            displayOrder.trim().isEmpty()) {

            course.setDisplayOrder(0);

        } else {

            course.setDisplayOrder(
                Integer.parseInt(displayOrder)
            );
        }


        return course;
    }


    private Course buildCourseForError(
            HttpServletRequest request) {

        try {
            return buildCourseFromRequest(request);
        } catch (Exception e) {
            return new Course();
        }
    }


    private void loadCategories(
            HttpServletRequest request)
            throws SQLException {

        List<CourseCategory> categories =
            categoryService.getAll();

        request.setAttribute(
            "categories",
            categories
        );
    }


    private String trim(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isEmpty()
            ? null
            : value;
    }
}