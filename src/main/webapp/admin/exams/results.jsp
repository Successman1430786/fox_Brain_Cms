<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.ExamResult" %>

<%
    List<ExamResult> results =
            (List<ExamResult>) request.getAttribute("results");

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Exam Results - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>

            <h1>Exam Results</h1>

            <p>
                Manage, review and publish examination results.
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/admin/exams?action=list"
           class="btn btn-secondary">
            Exams
        </a>

    </div>

    <% if (success != null) { %>

        <div class="alert alert-success">
            Result operation: <%= success %>
        </div>

    <% } %>

    <% if (error != null) { %>

        <div class="alert alert-danger">
            <%= error %>
        </div>

    <% } %>

    <div class="card">

        <table class="admin-table">

            <thead>

            <tr>
                <th>ID</th>
                <th>Exam</th>
                <th>Student</th>
                <th>Marks</th>
                <th>Grade</th>
                <th>Status</th>
                <th>Published</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody>

            <% if (results == null || results.isEmpty()) { %>

                <tr>
                    <td colspan="8"
                        style="text-align:center;">
                        No results found.
                    </td>
                </tr>

            <% } else { %>

                <% for (ExamResult result : results) { %>

                    <tr>

                        <td>
                            <%= result.getId() %>
                        </td>

                        <td>
                            <%= result.getExamTitle() == null
                                    ? result.getExamId()
                                    : result.getExamTitle() %>
                        </td>

                        <td>
                            <%= result.getStudentName() == null
                                    ? result.getStudentId()
                                    : result.getStudentName() %>
                        </td>

                        <td>
                            <%= result.getMarksObtained() %>
                        </td>

                        <td>
                            <%= result.getGrade() == null
                                    ? "-"
                                    : result.getGrade() %>
                        </td>

                        <td>
                            <%= result.getResultStatus() %>
                        </td>

                        <td>
                            <%= result.getPublishedAt() == null
                                    ? "NO"
                                    : "YES" %>
                        </td>

                        <td>

                            <a href="<%= request.getContextPath() %>/admin/exam-results?action=view&id=<%= result.getId() %>"
                               class="btn btn-sm">
                                View
                            </a>

                            <% if (result.getPublishedAt() == null) { %>

                                <a href="<%= request.getContextPath() %>/admin/exam-results?action=publish&id=<%= result.getId() %>"
                                   class="btn btn-sm btn-primary"
                                   onclick="return confirm('Publish this result?');">
                                    Publish
                                </a>

                            <% } else { %>

                                <a href="<%= request.getContextPath() %>/admin/exam-results?action=unpublish&id=<%= result.getId() %>"
                                   class="btn btn-sm"
                                   onclick="return confirm('Unpublish this result?');">
                                    Unpublish
                                </a>

                            <% } %>

                        </td>

                    </tr>

                <% } %>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>