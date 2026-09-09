<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Course" %>

<%
    request.setAttribute("pageTitle", "Courses");

    List<Course> courses =
        (List<Course>) request.getAttribute("courses");

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

    <title>Courses | FoxBrain Admin</title>

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
                        <h2>Courses</h2>

                        <p>
                            Manage all courses offered by FoxBrain Institute.
                        </p>
                    </div>

                    <div class="page-header-actions">

                        <a href="<%= request.getContextPath() %>/admin/courses?action=add"
                           class="btn btn-primary">

                            ➕ Add Course

                        </a>

                    </div>

                </div>


                <% if ("true".equals(created)) { %>

                    <div class="alert alert-success">
                        Course created successfully.
                    </div>

                <% } %>


                <% if ("true".equals(updated)) { %>

                    <div class="alert alert-success">
                        Course updated successfully.
                    </div>

                <% } %>


                <% if ("true".equals(deleted)) { %>

                    <div class="alert alert-success">
                        Course deleted successfully.
                    </div>

                <% } %>


                <% if ("database".equals(error)) { %>

                    <div class="alert alert-danger">
                        Unable to delete the course. It may already be
                        used by another part of the system.
                    </div>

                <% } %>


                <% if ("invalid".equals(error)) { %>

                    <div class="alert alert-danger">
                        Invalid course request.
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
                            <h3>Course List</h3>

                            <p>
                                View and manage your institute courses.
                            </p>
                        </div>

                    </div>


                    <% if (courses == null ||
                           courses.isEmpty()) { %>

                        <div class="empty-state">

                            <div class="empty-state-icon">
                                📚
                            </div>

                            <h3>No Courses Found</h3>

                            <p>
                                You haven't created any courses yet.
                            </p>

                            <a href="<%= request.getContextPath() %>/admin/courses?action=add"
                               class="btn btn-primary">

                                Add First Course

                            </a>

                        </div>

                    <% } else { %>

                        <div class="table-responsive">

                            <table class="admin-table">

                                <thead>

                                    <tr>

                                        <th>ID</th>

                                        <th>Course</th>

                                        <th>Category</th>

                                        <th>Duration</th>

                                        <th>Fee</th>

                                        <th>Students</th>

                                        <th>Status</th>

                                        <th>Featured</th>

                                        <th>Actions</th>

                                    </tr>

                                </thead>

                                <tbody>

                                <% for (Course course : courses) { %>

                                    <tr>

                                        <td>
                                            <%= course.getId() %>
                                        </td>


                                        <td>

                                            <strong>
                                                <%= course.getName() %>
                                            </strong>

                                            <br>

                                            <code>
                                                <%= course.getSlug() %>
                                            </code>

                                        </td>


                                        <td>

                                            <% if (course.getCategoryName() != null) { %>

                                                <%= course.getCategoryName() %>

                                            <% } else { %>

                                                <span class="text-muted">
                                                    No Category
                                                </span>

                                            <% } %>

                                        </td>


                                        <td>

                                            <% if (course.getDurationValue() != null) { %>

                                                <%= course.getDurationValue() %>
                                                <%= course.getDurationUnit() %>

                                            <% } else { %>

                                                <span class="text-muted">
                                                    Not set
                                                </span>

                                            <% } %>

                                        </td>


                                        <td>

                                            <% if (course.getFee() != null) { %>

                                                <%= course.getCurrencyCode() %>
                                                <%= course.getFee() %>

                                            <% } else { %>

                                                <span class="text-muted">
                                                    Free / Not set
                                                </span>

                                            <% } %>

                                        </td>


                                        <td>

                                            <% if (course.getMaxStudents() != null) { %>

                                                <%= course.getMaxStudents() %>

                                            <% } else { %>

                                                <span class="text-muted">
                                                    Unlimited
                                                </span>

                                            <% } %>

                                        </td>


                                        <td>

                                            <%
                                                String status =
                                                    course.getStatus();

                                                String statusClass =
                                                    "status-inactive";

                                                if ("ACTIVE".equals(status)) {
                                                    statusClass =
                                                        "status-active";
                                                } else if ("DRAFT".equals(status)) {
                                                    statusClass =
                                                        "status-pending";
                                                } else if ("ARCHIVED".equals(status)) {
                                                    statusClass =
                                                        "status-danger";
                                                }
                                            %>

                                            <span class="status-badge <%= statusClass %>">
                                                <%= status %>
                                            </span>

                                        </td>


                                        <td>

                                            <% if (course.isFeatured()) { %>

                                                <span class="status-badge status-active">
                                                    Yes
                                                </span>

                                            <% } else { %>

                                                <span class="text-muted">
                                                    No
                                                </span>

                                            <% } %>

                                        </td>


                                        <td>

                                            <div class="table-actions">

                                                <a href="<%= request.getContextPath() %>/admin/courses?action=edit&id=<%= course.getId() %>"
                                                   class="action-btn action-edit">

                                                    ✏ Edit

                                                </a>


                                                <a href="<%= request.getContextPath() %>/admin/courses?action=delete&id=<%= course.getId() %>"
                                                   class="action-btn action-delete"
                                                   onclick="return confirm('Are you sure you want to delete this course?');">

                                                    🗑 Delete

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

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>
</html>