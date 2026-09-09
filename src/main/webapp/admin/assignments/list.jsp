<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Assignment" %>

<%
    List<Assignment> assignments =
        (List<Assignment>) request.getAttribute("assignments");

 

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignments - FoxBrain</title>
    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">
</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">


        <main class="admin-content">

            <div class="page-header">

                <div>
                    <h1>Assignments</h1>
                    <p>Create and manage batch assignments.</p>
                </div>

                <a href="<%= contextPath %>/admin/assignments?action=add"
                   class="btn btn-primary">
                    + Add Assignment
                </a>

            </div>

            <% if ("created".equals(success)) { %>

                <div class="alert alert-success">
                    Assignment created successfully.
                </div>

            <% } else if ("updated".equals(success)) { %>

                <div class="alert alert-success">
                    Assignment updated successfully.
                </div>

            <% } else if ("deleted".equals(success)) { %>

                <div class="alert alert-success">
                    Assignment deleted successfully.
                </div>

            <% } else if ("notfound".equals(error)) { %>

                <div class="alert alert-danger">
                    Assignment not found.
                </div>

            <% } %>

            <div class="content-card">

                <div class="table-wrapper">

                    <table class="admin-table">

                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Assignment</th>
                            <th>Batch</th>
                            <th>Teacher</th>
                            <th>Assigned</th>
                            <th>Due</th>
                            <th>Max Marks</th>
                            <th>Submission</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>

                        <% if (assignments != null &&
                               !assignments.isEmpty()) {

                            int count = 1;

                            for (Assignment assignment : assignments) {

                                String statusClass =
                                    "status-" +
                                    assignment.getStatus()
                                    .toLowerCase();
                        %>

                        <tr>

                            <td><%= count++ %></td>

                            <td>
                                <strong>
                                    <%= assignment.getTitle() %>
                                </strong>

                                <% if (assignment.getDescription() != null &&
                                       !assignment.getDescription().trim().isEmpty()) { %>

                                    <div style="margin-top:4px;">
                                        <small>
                                            <%= assignment.getDescription() %>
                                        </small>
                                    </div>

                                <% } %>
                            </td>

                            <td>
                                <%= assignment.getBatchName() != null
                                    ? assignment.getBatchName()
                                    : "—" %>

                                <% if (assignment.getBatchCode() != null) { %>
                                    <br>
                                    <small>
                                        <%= assignment.getBatchCode() %>
                                    </small>
                                <% } %>
                            </td>

                            <td>
                                <%= assignment.getTeacherName() != null
                                    ? assignment.getTeacherName().trim()
                                    : "—" %>
                            </td>

                            <td>
                                <%= assignment.getAssignedDate() != null
                                    ? assignment.getAssignedDate()
                                    : "—" %>
                            </td>

                            <td>
                                <%= assignment.getDueDate() != null
                                    ? assignment.getDueDate()
                                    : "Not set" %>
                            </td>

                            <td>
                                <%= assignment.getMaxMarks() != null
                                    ? assignment.getMaxMarks()
                                    : "—" %>
                            </td>

                            <td>
                                <%= assignment.getSubmissionType() != null
                                    ? assignment.getSubmissionType()
                                    : "—" %>

                                <% if (assignment.isAllowLateSubmission()) { %>
                                    <br>
                                    <small>Late allowed</small>
                                <% } %>
                            </td>

                            <td>
                                <span class="status-badge <%= statusClass %>">
                                    <%= assignment.getStatus() %>
                                </span>
                            </td>

                            <td>

                                <div class="table-actions">

                                    <a href="<%= contextPath %>/admin/assignments?action=edit&id=<%= assignment.getId() %>"
                                       class="btn btn-sm btn-secondary">
                                        Edit
                                    </a>

                                    <a href="<%= contextPath %>/admin/assignments?action=delete&id=<%= assignment.getId() %>"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Delete this assignment?');">
                                        Delete
                                    </a>

                                </div>

                            </td>

                        </tr>

                        <%      }
                           } else { %>

                        <tr>
                            <td colspan="10" class="empty-state">
                                No assignments found.
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

<script src="<%= contextPath %>/assets/js/admin.js"></script>

</body>
</html>