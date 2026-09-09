<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Course" %>

<%
    request.setAttribute("pageTitle", "Add Batch");

    List<Course> courses =
            (List<Course>) request.getAttribute("courses");

    String error =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Batch - FoxBrain Admin</title>

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
                    <h2>Add Batch</h2>
                    <p>Create a new course batch.</p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/batches"
                   class="btn btn-secondary">
                    ← Back
                </a>

            </div>

            <% if (error != null) { %>

                <div class="alert alert-danger">
                    <%= error %>
                </div>

            <% } %>

            <div class="card">

                <form method="post"
                      action="<%= request.getContextPath() %>/admin/batches">

                    <input type="hidden"
                           name="action"
                           value="create">

                    <div class="form-grid">

                        <div class="form-group">

                            <label for="courseId">
                                Course *
                            </label>

                            <select id="courseId"
                                    name="courseId"
                                    required>

                                <option value="">
                                    Select Course
                                </option>

                                <% if (courses != null) { %>

                                    <% for (Course course : courses) { %>

                                        <option value="<%= course.getId() %>">
                                            <%= course.getName() %>
                                        </option>

                                    <% } %>

                                <% } %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="batchCode">
                                Batch Code *
                            </label>

                            <input type="text"
                                   id="batchCode"
                                   name="batchCode"
                                   maxlength="80"
                                   placeholder="Example: JAVA-2026-01"
                                   required>

                        </div>

                        <div class="form-group">

                            <label for="name">
                                Batch Name *
                            </label>

                            <input type="text"
                                   id="name"
                                   name="name"
                                   maxlength="200"
                                   placeholder="Example: Java Full Stack Morning Batch"
                                   required>

                        </div>

                        <div class="form-group">

                            <label for="roomName">
                                Room
                            </label>

                            <input type="text"
                                   id="roomName"
                                   name="roomName"
                                   maxlength="100"
                                   placeholder="Example: Lab 1">

                        </div>

                        <div class="form-group">

                            <label for="startDate">
                                Start Date
                            </label>

                            <input type="date"
                                   id="startDate"
                                   name="startDate">

                        </div>

                        <div class="form-group">

                            <label for="endDate">
                                End Date
                            </label>

                            <input type="date"
                                   id="endDate"
                                   name="endDate">

                        </div>

                        <div class="form-group">

                            <label for="startTime">
                                Start Time
                            </label>

                            <input type="time"
                                   id="startTime"
                                   name="startTime">

                        </div>

                        <div class="form-group">

                            <label for="endTime">
                                End Time
                            </label>

                            <input type="time"
                                   id="endTime"
                                   name="endTime">

                        </div>

                        <div class="form-group">

                            <label for="capacity">
                                Capacity
                            </label>

                            <input type="number"
                                   id="capacity"
                                   name="capacity"
                                   min="1"
                                   placeholder="Example: 30">

                        </div>

                        <div class="form-group">

                            <label for="status">
                                Status *
                            </label>

                            <select id="status"
                                    name="status"
                                    required>

                                <option value="PLANNED">
                                    Planned
                                </option>

                                <option value="ACTIVE">
                                    Active
                                </option>

                                <option value="COMPLETED">
                                    Completed
                                </option>

                                <option value="CANCELLED">
                                    Cancelled
                                </option>

                            </select>

                        </div>

                    </div>

                    <div class="form-group">

                        <label for="notes">
                            Notes
                        </label>

                        <textarea id="notes"
                                  name="notes"
                                  rows="5"
                                  placeholder="Additional batch notes..."></textarea>

                    </div>

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/batches"
                           class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Create Batch
                        </button>

                    </div>

                </form>

            </div>

        </main>

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>