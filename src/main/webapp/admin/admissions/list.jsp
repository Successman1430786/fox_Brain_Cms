<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Admission" %>

<%
    request.setAttribute("pageTitle", "Admissions");

    List<Admission> admissions =
            (List<Admission>) request.getAttribute("admissions");

    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admissions - FoxBrain Admin</title>

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
                    <h2>Admissions</h2>
                    <p>Manage student admission applications.</p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/admissions?action=add"
                   class="btn btn-primary">
                    + New Admission
                </a>

            </div>

            <% if ("created".equals(success)) { %>

                <div class="alert alert-success">
                    Admission application created successfully.
                </div>

            <% } else if ("updated".equals(success)) { %>

                <div class="alert alert-success">
                    Admission application updated successfully.
                </div>

            <% } else if ("deleted".equals(success)) { %>

                <div class="alert alert-success">
                    Admission application deleted successfully.
                </div>

            <% } %>

            <% if (error != null && !error.trim().isEmpty()) { %>

                <div class="alert alert-danger">
                    <%= error %>
                </div>

            <% } %>

            <div class="content-card">

                <div class="table-wrapper">

                    <table class="admin-table">

                        <thead>

                        <tr>
                            <th>#</th>
                            <th>Application</th>
                            <th>Applicant</th>
                            <th>Course</th>
                            <th>Batch</th>
                            <th>Application Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>

                        </thead>

                        <tbody>

                        <% if (admissions != null
                                && !admissions.isEmpty()) {

                            int index = 1;

                            for (Admission admission : admissions) {

                                String status =
                                        admission.getStatus();

                                String statusClass =
                                        "status-" +
                                        status.toLowerCase()
                                              .replace("_", "-");
                        %>

                        <tr>

                            <td>
                                <%= index++ %>
                            </td>

                            <td>
                                <strong>
                                    <%= admission.getApplicationNumber() %>
                                </strong>
                            </td>

                            <td>
                                <strong>
                                    <%= admission.getFirstName() %>
                                    <%= admission.getLastName() != null
                                            ? admission.getLastName()
                                            : "" %>
                                </strong>

                                <br>

                                <small>
                                    <%= admission.getPhone() %>
                                </small>
                            </td>

                            <td>
                                <%= admission.getCourseName() != null
                                        ? admission.getCourseName()
                                        : "-" %>
                            </td>

                            <td>
                                <%= admission.getBatchName() != null
                                        ? admission.getBatchName()
                                        : "-" %>
                            </td>

                            <td>
                                <%= admission.getApplicationDate() != null
                                        ? admission.getApplicationDate()
                                        : "-" %>
                            </td>

                            <td>
                                <span class="status-badge <%= statusClass %>">
                                    <%= status %>
                                </span>
                            </td>

                            <td>

                                <div class="table-actions">

                                    <a href="<%= request.getContextPath() %>/admin/admissions?action=edit&id=<%= admission.getId() %>"
                                       class="btn btn-sm btn-secondary">
                                        Edit
                                    </a>

                                    <a href="<%= request.getContextPath() %>/admin/admissions?action=delete&id=<%= admission.getId() %>"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Delete this admission application?');">
                                        Delete
                                    </a>

                                </div>

                            </td>

                        </tr>

                        <%
                            }

                        } else {
                        %>

                        <tr>

                            <td colspan="8"
                                class="empty-state">

                                <div class="empty-state-icon">
                                    🎓
                                </div>

                                <h3>No admissions found</h3>

                                <p>
                                    Start by creating a new admission application.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/admissions?action=add"
                                   class="btn btn-primary">
                                    + New Admission
                                </a>

                            </td>

                        </tr>

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