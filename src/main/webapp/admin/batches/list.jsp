<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Batch" %>

<%
    request.setAttribute("pageTitle", "Batches");

    List<Batch> batches =
            (List<Batch>) request.getAttribute("batches");

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");

    String servletError =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Batches - FoxBrain Admin</title>

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
                    <h2>Batches</h2>
                    <p>
                        Manage course batches, schedules,
                        rooms and capacities.
                    </p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/batches?action=add"
                   class="btn btn-primary">
                    + Add Batch
                </a>

            </div>

            <% if ("created".equals(success)) { %>

                <div class="alert alert-success">
                    Batch created successfully.
                </div>

            <% } else if ("updated".equals(success)) { %>

                <div class="alert alert-success">
                    Batch updated successfully.
                </div>

            <% } else if ("deleted".equals(success)) { %>

                <div class="alert alert-success">
                    Batch deleted successfully.
                </div>

            <% } %>

            <% if ("database".equals(error) ||
                   servletError != null) { %>

                <div class="alert alert-danger">
                    <%= servletError != null
                            ? servletError
                            : "Database error. Please try again." %>
                </div>

            <% } else if ("invalid".equals(error)) { %>

                <div class="alert alert-danger">
                    Invalid batch request.
                </div>

            <% } %>

            <div class="card">

                <div class="table-responsive">

                    <table class="admin-table">

                        <thead>

                        <tr>
                            <th>#</th>
                            <th>Batch</th>
                            <th>Course</th>
                            <th>Schedule</th>
                            <th>Room</th>
                            <th>Capacity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>

                        </thead>

                        <tbody>

                        <% if (batches != null &&
                               !batches.isEmpty()) { %>

                            <% int index = 1; %>

                            <% for (Batch batch : batches) { %>

                                <tr>

                                    <td>
                                        <%= index++ %>
                                    </td>

                                    <td>

                                        <strong>
                                            <%= batch.getName() %>
                                        </strong>

                                        <div class="table-subtext">
                                            <%= batch.getBatchCode() %>
                                        </div>

                                    </td>

                                    <td>
                                        <%= batch.getCourseName() %>
                                    </td>

                                    <td>

                                        <%
                                            String dateText = "-";

                                            if (batch.getStartDate() != null &&
                                                batch.getEndDate() != null) {

                                                dateText =
                                                    batch.getStartDate()
                                                    + " → "
                                                    + batch.getEndDate();

                                            } else if (batch.getStartDate() != null) {

                                                dateText =
                                                    batch.getStartDate().toString();
                                            }

                                            String timeText = "";

                                            if (batch.getStartTime() != null &&
                                                batch.getEndTime() != null) {

                                                timeText =
                                                    batch.getStartTime()
                                                    + " - "
                                                    + batch.getEndTime();

                                            } else if (batch.getStartTime() != null) {

                                                timeText =
                                                    batch.getStartTime().toString();
                                            }
                                        %>

                                        <div>
                                            <%= dateText %>
                                        </div>

                                        <% if (!timeText.isEmpty()) { %>
                                            <div class="table-subtext">
                                                <%= timeText %>
                                            </div>
                                        <% } %>

                                    </td>

                                    <td>
                                        <%= batch.getRoomName() != null
                                                ? batch.getRoomName()
                                                : "-" %>
                                    </td>

                                    <td>
                                        <%= batch.getCapacity() != null
                                                ? batch.getCapacity()
                                                : "Unlimited" %>
                                    </td>

                                    <td>

                                        <span class="status-badge status-<%= 
                                            batch.getStatus()
                                                .toLowerCase()
                                                .replace("_", "-") %>">

                                            <%= batch.getStatus() %>

                                        </span>

                                    </td>

                                    <td>

                                        <div class="table-actions">

                                            <a href="<%= request.getContextPath() %>/admin/batches?action=edit&id=<%= batch.getId() %>"
                                               class="btn btn-sm btn-secondary">
                                                Edit
                                            </a>

                                            <a href="<%= request.getContextPath() %>/admin/batches?action=delete&id=<%= batch.getId() %>"
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Are you sure you want to delete this batch?');">
                                                Delete
                                            </a>

                                        </div>

                                    </td>

                                </tr>

                            <% } %>

                        <% } else { %>

                            <tr>

                                <td colspan="8">

                                    <div class="empty-state">

                                        <div class="empty-state-icon">
                                            📦
                                        </div>

                                        <h3>No batches found</h3>

                                        <p>
                                            Create your first batch
                                            to get started.
                                        </p>

                                        <a href="<%= request.getContextPath() %>/admin/batches?action=add"
                                           class="btn btn-primary">
                                            + Add Batch
                                        </a>

                                    </div>

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