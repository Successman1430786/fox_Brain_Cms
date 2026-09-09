<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Student" %>
<%@ page import="com.foxbrain.model.Batch" %>

<%
    request.setAttribute("pageTitle", "Add Enrollment");

    List<Student> students =
            (List<Student>) request.getAttribute("students");

    List<Batch> batches =
            (List<Batch>) request.getAttribute("batches");

    String error =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Add Enrollment - FoxBrain</title>

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
                    <h2>Add Enrollment</h2>
                    <p>Create a new student enrollment.</p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/enrollments"
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
                      action="<%= request.getContextPath() %>/admin/enrollments">

                    <input type="hidden"
                           name="action"
                           value="create">

                    <div class="form-grid">

                        <div class="form-group">

                            <label for="enrollmentNumber">
                                Enrollment Number *
                            </label>

                            <input type="text"
                                   id="enrollmentNumber"
                                   name="enrollmentNumber"
                                   maxlength="80"
                                   required
                                   placeholder="ENR-2026-0001">

                        </div>

                        <div class="form-group">

                            <label for="enrollmentDate">
                                Enrollment Date *
                            </label>

                            <input type="date"
                                   id="enrollmentDate"
                                   name="enrollmentDate"
                                   required>

                        </div>

                        <div class="form-group">

                            <label for="studentId">
                                Student *
                            </label>

                            <select id="studentId"
                                    name="studentId"
                                    required>

                                <option value="">
                                    -- Select Student --
                                </option>

                                <% if (students != null) {
                                    for (Student student : students) { %>

                                    <option value="<%= student.getId() %>">
                                        <%= student.getAdmissionNumber() %>
                                    </option>

                                <% }} %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="batchId">
                                Batch *
                            </label>

                            <select id="batchId"
                                    name="batchId"
                                    required>

                                <option value="">
                                    -- Select Batch --
                                </option>

                                <% if (batches != null) {
                                    for (Batch batch : batches) { %>

                                    <option value="<%= batch.getId() %>">
                                        <%= batch.getName() %>
                                        -
                                        <%= batch.getBatchCode() %>
                                    </option>

                                <% }} %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="status">
                                Status *
                            </label>

                            <select id="status"
                                    name="status"
                                    required>

                                <option value="ACTIVE">
                                    ACTIVE
                                </option>

                                <option value="COMPLETED">
                                    COMPLETED
                                </option>

                                <option value="CANCELLED">
                                    CANCELLED
                                </option>

                                <option value="TRANSFERRED">
                                    TRANSFERRED
                                </option>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="completionDate">
                                Completion Date
                            </label>

                            <input type="date"
                                   id="completionDate"
                                   name="completionDate">

                        </div>

                    </div>

                    <div class="form-group">

                        <label for="notes">
                            Notes
                        </label>

                        <textarea id="notes"
                                  name="notes"
                                  rows="5"
                                  maxlength="5000"
                                  placeholder="Additional enrollment notes..."></textarea>

                    </div>

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/enrollments"
                           class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Save Enrollment
                        </button>

                    </div>

                </form>

            </div>

        </main>

    </div>

</div>

<script>

    document.addEventListener("DOMContentLoaded", function () {

        const dateInput =
            document.getElementById("enrollmentDate");

        if (dateInput && !dateInput.value) {

            const today =
                new Date().toISOString().split("T")[0];

            dateInput.value = today;
        }

    });

</script>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>
</html>