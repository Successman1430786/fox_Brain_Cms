<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Assignment" %>
<%@ page import="com.foxbrain.model.Batch" %>
<%@ page import="com.foxbrain.model.Teacher" %>

<%
    Assignment assignment =
        (Assignment) request.getAttribute("assignment");

    List<Batch> batches =
        (List<Batch>) request.getAttribute("batches");

    List<Teacher> teachers =
        (List<Teacher>) request.getAttribute("teachers");




    String error =
        (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Assignment - FoxBrain</title>

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
                    <h1>Edit Assignment</h1>
                    <p>Update assignment information.</p>
                </div>

                <a href="<%= contextPath %>/admin/assignments"
                   class="btn btn-secondary">
                    Back
                </a>

            </div>

            <% if (error != null) { %>

                <div class="alert alert-danger">
                    <%= error %>
                </div>

            <% } %>

            <div class="content-card">

                <form method="post"
                      action="<%= contextPath %>/admin/assignments">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           name="id"
                           value="<%= assignment.getId() %>">

                    <div class="form-grid">

                        <div class="form-group">

                            <label for="batchId">
                                Batch <span>*</span>
                            </label>

                            <select id="batchId"
                                    name="batchId"
                                    required>

                                <option value="">
                                    Select Batch
                                </option>

                                <% if (batches != null) {
                                    for (Batch batch : batches) { %>

                                    <option value="<%= batch.getId() %>"
                                        <%= assignment.getBatchId() ==
                                            batch.getId()
                                            ? "selected" : "" %>>

                                        <%= batch.getName() %>
                                        (<%= batch.getBatchCode() %>)

                                    </option>

                                <%  }
                                   } %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="teacherId">
                                Teacher <span>*</span>
                            </label>

                            <select id="teacherId"
                                    name="teacherId"
                                    required>

                                <option value="">
                                    Select Teacher
                                </option>

                                <% if (teachers != null) {
                                    for (Teacher teacher : teachers) { %>

                                    <option value="<%= teacher.getId() %>"
                                        <%= assignment.getTeacherId() ==
                                            teacher.getId()
                                            ? "selected" : "" %>>

                                        <%= teacher.getEmployeeNumber() %>

                                    </option>

                                <%  }
                                   } %>

                            </select>

                        </div>

                        <div class="form-group form-group-full">

                            <label for="title">
                                Assignment Title <span>*</span>
                            </label>

                            <input type="text"
                                   id="title"
                                   name="title"
                                   maxlength="255"
                                   required
                                   value="<%= assignment.getTitle() != null
                                            ? assignment.getTitle()
                                            : "" %>">

                        </div>

                        <div class="form-group form-group-full">

                            <label for="description">
                                Description
                            </label>

                            <textarea id="description"
                                      name="description"
                                      rows="4"><%= assignment.getDescription() != null
                                      ? assignment.getDescription()
                                      : "" %></textarea>

                        </div>

                        <div class="form-group form-group-full">

                            <label for="instructions">
                                Instructions
                            </label>

                            <textarea id="instructions"
                                      name="instructions"
                                      rows="5"><%= assignment.getInstructions() != null
                                      ? assignment.getInstructions()
                                      : "" %></textarea>

                        </div>

                        <div class="form-group">

                            <label for="assignedDate">
                                Assigned Date <span>*</span>
                            </label>

                            <input type="date"
                                   id="assignedDate"
                                   name="assignedDate"
                                   required
                                   value="<%= assignment.getAssignedDate() != null
                                            ? assignment.getAssignedDate()
                                            : "" %>">

                        </div>

                        <div class="form-group">

                            <label for="dueDate">
                                Due Date
                            </label>

                            <input type="date"
                                   id="dueDate"
                                   name="dueDate"
                                   value="<%= assignment.getDueDate() != null
                                            ? assignment.getDueDate()
                                            : "" %>">

                        </div>

                        <div class="form-group">

                            <label for="maxMarks">
                                Maximum Marks
                            </label>

                            <input type="number"
                                   id="maxMarks"
                                   name="maxMarks"
                                   min="0"
                                   max="999999.99"
                                   step="0.01"
                                   value="<%= assignment.getMaxMarks() != null
                                            ? assignment.getMaxMarks()
                                            : "" %>">

                        </div>

                        <div class="form-group">

                            <label for="submissionType">
                                Submission Type <span>*</span>
                            </label>

                            <select id="submissionType"
                                    name="submissionType"
                                    required>

                                <option value="FILE"
                                    <%= "FILE".equals(
                                        assignment.getSubmissionType())
                                        ? "selected" : "" %>>
                                    File
                                </option>

                                <option value="TEXT"
                                    <%= "TEXT".equals(
                                        assignment.getSubmissionType())
                                        ? "selected" : "" %>>
                                    Text
                                </option>

                                <option value="BOTH"
                                    <%= "BOTH".equals(
                                        assignment.getSubmissionType())
                                        ? "selected" : "" %>>
                                    File + Text
                                </option>

                            </select>

                        </div>

                        <div class="form-group form-group-full">

                            <label for="attachmentUrl">
                                Attachment URL
                            </label>

                            <input type="url"
                                   id="attachmentUrl"
                                   name="attachmentUrl"
                                   maxlength="500"
                                   value="<%= assignment.getAttachmentUrl() != null
                                            ? assignment.getAttachmentUrl()
                                            : "" %>">

                        </div>

                        <div class="form-group">

                            <label for="allowLateSubmission">
                                Allow Late Submission
                            </label>

                            <select id="allowLateSubmission"
                                    name="allowLateSubmission">

                                <option value="0"
                                    <%= !assignment.isAllowLateSubmission()
                                        ? "selected" : "" %>>
                                    No
                                </option>

                                <option value="1"
                                    <%= assignment.isAllowLateSubmission()
                                        ? "selected" : "" %>>
                                    Yes
                                </option>

                            </select>

                        </div>

                        <div class="form-group">

                            <label for="latePenalty">
                                Late Penalty
                            </label>

                            <input type="number"
                                   id="latePenalty"
                                   name="latePenalty"
                                   min="0"
                                   max="999.99"
                                   step="0.01"
                                   value="<%= assignment.getLatePenalty() != null
                                            ? assignment.getLatePenalty()
                                            : "" %>">

                        </div>

                        <div class="form-group">

                            <label for="status">
                                Status <span>*</span>
                            </label>

                            <select id="status"
                                    name="status"
                                    required>

                                <option value="DRAFT"
                                    <%= "DRAFT".equals(
                                        assignment.getStatus())
                                        ? "selected" : "" %>>
                                    Draft
                                </option>

                                <option value="PUBLISHED"
                                    <%= "PUBLISHED".equals(
                                        assignment.getStatus())
                                        ? "selected" : "" %>>
                                    Published
                                </option>

                                <option value="CLOSED"
                                    <%= "CLOSED".equals(
                                        assignment.getStatus())
                                        ? "selected" : "" %>>
                                    Closed
                                </option>

                                <option value="ARCHIVED"
                                    <%= "ARCHIVED".equals(
                                        assignment.getStatus())
                                        ? "selected" : "" %>>
                                    Archived
                                </option>

                            </select>

                        </div>

                    </div>

                    <div class="form-actions">

                        <a href="<%= contextPath %>/admin/assignments"
                           class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Update Assignment
                        </button>

                    </div>

                </form>

            </div>

        </main>

    </div>

</div>

<script src="<%= contextPath %>/assets/js/admin.js"></script>

</body>
</html>