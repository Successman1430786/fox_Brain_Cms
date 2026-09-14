<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Enrollment" %>

<%
    request.setAttribute("pageTitle", "Enrollments");

    List<Enrollment> enrollments =
            (List<Enrollment>) request.getAttribute("enrollments");

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Enrollments - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="page-header">

                <div>
                    <h2>Enrollments</h2>
                    <p>Manage student enrollments in batches.</p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/enrollments?action=add"
                   class="btn btn-primary">
                    + Add Enrollment
                </a>

            </div>

            <% if ("created".equals(success)) { %>

                <div class="alert alert-success">
                    Enrollment created successfully.
                </div>

            <% } else if ("updated".equals(success)) { %>

                <div class="alert alert-success">
                    Enrollment updated successfully.
                </div>

            <% } else if ("deleted".equals(success)) { %>

                <div class="alert alert-success">
                    Enrollment deleted successfully.
                </div>

            <% } else if ("database".equals(error)) { %>

                <div class="alert alert-danger">
                    A database error occurred. Please try again.
                </div>

            <% } %>

            <div class="card">

                <div class="table-responsive">

                    <table class="admin-table">

                        <thead>

                        <tr>
                            <th>#</th>
                            <th>Enrollment No.</th>
                            <th>Student</th>
                            <th>Admission No.</th>
                            <th>Course</th>
                            <th>Batch</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>

                        </thead>

                        <tbody>

                        <% if (enrollments == null
                                || enrollments.isEmpty()) { %>

                            <tr>
                                <td colspan="9"
                                    class="text-center">
                                    No enrollments found.
                                </td>
                            </tr>

                        <% } else { %>

                            <% for (Enrollment enrollment : enrollments) { %>

                                <tr>

                                    <td>
                                        <%= enrollment.getId() %>
                                    </td>

                                    <td>
                                        <strong>
                                            <%= enrollment.getEnrollmentNumber() %>
                                        </strong>
                                    </td>

                                    <td>
                                        <%= enrollment.getStudentName() %>
                                    </td>

                                    <td>
                                        <%= enrollment.getAdmissionNumber() %>
                                    </td>

                                    <td>
                                        <%= enrollment.getCourseName() %>
                                    </td>

                                    <td>
                                        <%= enrollment.getBatchName() %>
                                    </td>

                                    <td>
                                        <%= enrollment.getEnrollmentDate() %>
                                    </td>

                                    <td>

                                        <span class="status-badge status-<%= enrollment.getStatus().toLowerCase() %>">
                                            <%= enrollment.getStatus() %>
                                        </span>

                                    </td>

                                    <td>

                                        <div class="table-actions">

                                            <a href="<%= request.getContextPath() %>/admin/enrollments?action=edit&id=<%= enrollment.getId() %>"
                                               class="btn btn-sm btn-secondary">
                                                Edit
                                            </a>

                                            <a href="<%= request.getContextPath() %>/admin/enrollments?action=delete&id=<%= enrollment.getId() %>"
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Delete this enrollment?');">
                                                Delete
                                            </a>

                                        </div>

                                    </td>

                                </tr>

                            <% } %>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </main>

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>
</html>