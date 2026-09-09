package com.foxbrain.controller;

import com.foxbrain.model.CourseCategory;
import com.foxbrain.service.CourseCategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.SQLException;

@WebServlet("/admin/course-categories")
public class CourseCategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CourseCategoryService categoryService;

    @Override
    public void init() throws ServletException {

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
                deleteCategory(request, response);
                break;

            case "list":
            default:
                showList(request, response);
                break;
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

        if (action == null || action.trim().isEmpty()) {
            action = "create";
        }

        try {

            switch (action) {

                case "create":
                    createCategory(request, response);
                    break;

                case "update":
                    updateCategory(request, response);
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
                "/admin/course-categories/list.jsp"
            ).forward(request, response);
        }
    }

    private void showList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                "categories",
                categoryService.getAll()
            );

            request.getRequestDispatcher(
                "/admin/course-categories/list.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/course-categories/list.jsp"
            ).forward(request, response);
        }
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
            "/admin/course-categories/add.jsp"
        ).forward(request, response);
    }

  private void showEditForm(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    String idParam = request.getParameter("id");

    if (idParam == null || idParam.trim().isEmpty()) {

        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Category ID is required."
        );

        return;
    }

    try {

        long id = Long.parseLong(idParam);

        CourseCategory category =
            categoryService.getById(id);

        if (category == null) {

            response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Category not found."
            );

            return;
        }

        request.setAttribute(
            "category",
            category
        );

        request.getRequestDispatcher(
            "/admin/course-categories/edit.jsp"
        ).forward(request, response);

    } catch (NumberFormatException e) {

        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid category ID."
        );

    } catch (SQLException e) {

        e.printStackTrace();

        response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Database error while loading category."
        );
    }
} 
   private void createCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            CourseCategory category =
                buildCategoryFromRequest(request);

            long id =
                categoryService.create(category);

            response.sendRedirect(
                request.getContextPath()
                + "/admin/course-categories?created="
                + id
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/course-categories/add.jsp"
            ).forward(request, response);
        }
    }

    private void updateCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idParam =
                request.getParameter("id");

            if (idParam == null ||
                idParam.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Category ID is required."
                );
            }

            long id = Long.parseLong(idParam);

            CourseCategory existing =
                categoryService.getById(id);

            if (existing == null) {

                throw new IllegalArgumentException(
                    "Category not found."
                );
            }

            CourseCategory category =
                buildCategoryFromRequest(request);

            category.setId(id);

            boolean updated =
                categoryService.update(category);

            if (!updated) {

                throw new RuntimeException(
                    "Category could not be updated."
                );
            }

            response.sendRedirect(
                request.getContextPath()
                + "/admin/course-categories?updated="
                + id
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                "errorMessage",
                "Invalid category ID."
            );

            request.getRequestDispatcher(
                "/admin/course-categories/edit.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "errorMessage",
                e.getMessage()
            );

            request.getRequestDispatcher(
                "/admin/course-categories/edit.jsp"
            ).forward(request, response);
        }
    }

    private void deleteCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParam =
            request.getParameter("id");

        if (idParam == null ||
            idParam.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Category ID is required."
            );

            return;
        }

        try {

            long id = Long.parseLong(idParam);

            boolean deleted =
                categoryService.delete(id);

            if (deleted) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/course-categories?deleted="
                    + id
                );

            } else {

                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/course-categories?error="
                    + "Category could not be deleted"
                );
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid category ID."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                request.getContextPath()
                + "/admin/course-categories?error="
                + "Unable to delete category"
            );
        }
    }

    private CourseCategory buildCategoryFromRequest(
            HttpServletRequest request) {

        CourseCategory category =
            new CourseCategory();

        category.setName(
            getParameter(request, "name")
        );

        category.setSlug(
            getParameter(request, "slug")
        );

        category.setDescription(
            getParameter(request, "description")
        );

        String order =
            getParameter(request, "displayOrder");

        int displayOrder = 0;

        if (order != null && !order.isEmpty()) {

            try {
                displayOrder =
                    Integer.parseInt(order);
            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                    "Display order must be a valid number."
                );
            }
        }

        category.setDisplayOrder(displayOrder);

        String status =
            getParameter(request, "status");

        if (status == null || status.isEmpty()) {
            status = "ACTIVE";
        }

        category.setStatus(status);

        return category;
    }

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