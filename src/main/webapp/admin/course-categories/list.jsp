<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.CourseCategory" %>

<%
    request.setAttribute("pageTitle", "Course Categories");

    List<CourseCategory> categories =
        (List<CourseCategory>) request.getAttribute("categories");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    String created =
        request.getParameter("created");

    String updated =
        request.getParameter("updated");

    String deleted =
        request.getParameter("deleted");

    String error =
        request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Course Categories | FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="page-container">

                <div class="page-header">

                    <div>
                        <h2>Course Categories</h2>
                        <p>
                            Manage categories used to organize FoxBrain courses.
                        </p>
                    </div>

                    <div class="page-header-actions">

                        <a class="btn btn-primary"
                           href="<%= request.getContextPath() %>/admin/course-categories?action=add">
                            + Add Category
                        </a>

                    </div>

                </div>

                <% if (created != null) { %>

                    <div class="alert alert-success">
                        Course category created successfully.
                    </div>

                <% } %>

                <% if (updated != null) { %>

                    <div class="alert alert-success">
                        Course category updated successfully.
                    </div>

                <% } %>

                <% if (deleted != null) { %>

                    <div class="alert alert-success">
                        Course category deleted successfully.
                    </div>

                <% } %>

                <% if (error != null) { %>

                    <div class="alert alert-danger">
                        <%= error %>
                    </div>

                <% } %>

                <% if (errorMessage != null) { %>

                    <div class="alert alert-danger">
                        <%= errorMessage %>
                    </div>

                <% } %>

                <div class="card table-card">

                    <div class="card-header">

                        <div>
                            <h3>All Categories</h3>

                            <p>
                                <%= categories != null
                                    ? categories.size()
                                    : 0 %>
                                categories found
                            </p>
                        </div>

                    </div>

                    <% if (categories == null ||
                           categories.isEmpty()) { %>

                        <div class="empty-state">

                            <div class="empty-state-icon">
                                📚
                            </div>

                            <h3>No Course Categories</h3>

                            <p>
                                Create your first course category
                                to organize courses.
                            </p>

                            <a class="btn btn-primary"
                               href="<%= request.getContextPath() %>/admin/course-categories?action=add">
                                + Add Category
                            </a>

                        </div>

                    <% } else { %>

                        <div class="table-responsive">

                            <table class="admin-table">

                                <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Category</th>
                                    <th>Slug</th>
                                    <th>Description</th>
                                    <th>Order</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>

                                </thead>

                                <tbody>

                                <% for (CourseCategory category :
                                        categories) { %>

                                    <tr>

                                        <td>
                                            #<%= category.getId() %>
                                        </td>

                                        <td>
                                            <strong>
                                                <%= category.getName() %>
                                            </strong>
                                        </td>

                                        <td>
                                            <code>
                                                <%= category.getSlug() %>
                                            </code>
                                        </td>

                                        <td>
                                            <%
                                                String description =
                                                    category.getDescription();

                                                if (description == null ||
                                                    description.trim().isEmpty()) {
                                            %>
                                                <span class="text-muted">
                                                    No description
                                                </span>
                                            <%
                                                } else {
                                            %>
                                                <%= description %>
                                            <%
                                                }
                                            %>
                                        </td>

                                        <td>
                                            <%= category.getDisplayOrder() %>
                                        </td>

                                        <td>

                                            <% if ("ACTIVE".equals(
                                                    category.getStatus())) { %>

                                                <span class="status-badge status-active">
                                                    Active
                                                </span>

                                            <% } else { %>

                                                <span class="status-badge status-inactive">
                                                    Inactive
                                                </span>

                                            <% } %>

                                        </td>

                                        <td>

                                            <div class="table-actions">

                                                <a class="action-btn action-edit"
                                                   href="<%= request.getContextPath() %>/admin/course-categories?action=edit&id=<%= category.getId() %>">
                                                    Edit
                                                </a>

                                                <a class="action-btn action-delete"
                                                   href="<%= request.getContextPath() %>/admin/course-categories?action=delete&id=<%= category.getId() %>"
                                                   onclick="return confirm('Are you sure you want to delete this category?');">
                                                    Delete
                                                </a>

                                            </div>

                                        </td>

                                    </tr>

                                <% } %>

                                </tbody>

                            </table>

                        </div>

                    <% } %>

                </div>

            </div>

        </main>

        <%@ include file="/includes/admin-footer.jsp" %>

    </div>

</div>

</body>
</html>