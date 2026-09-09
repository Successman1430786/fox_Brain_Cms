<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.ExamResult" %>

<%
    ExamResult result =
            (ExamResult) request.getAttribute("result");

    if (result == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/admin/exam-results?action=list"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <title>Result - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>

            <h1>Exam Result</h1>

            <p>
                Result #<%= result.getId() %>
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/admin/exam-results?action=list"
           class="btn btn-secondary">
            Back
        </a>

    </div>

    <div class="card">

        <table class="admin-table">

            <tr>
                <th>Result ID</th>
                <td><%= result.getId() %></td>
            </tr>

            <tr>
                <th>Exam</th>
                <td>
                    <%= result.getExamTitle() == null
                            ? result.getExamId()
                            : result.getExamTitle() %>
                </td>
            </tr>

            <tr>
                <th>Student</th>
                <td>
                    <%= result.getStudentName() == null
                            ? result.getStudentId()
                            : result.getStudentName() %>
                </td>
            </tr>

            <tr>
                <th>Admission Number</th>
                <td>
                    <%= result.getAdmissionNumber() == null
                            ? "-"
                            : result.getAdmissionNumber() %>
                </td>
            </tr>

            <tr>
                <th>Marks Obtained</th>
                <td>
                    <%= result.getMarksObtained() %>
                </td>
            </tr>

            <tr>
                <th>Grade</th>
                <td>
                    <%= result.getGrade() == null
                            ? "-"
                            : result.getGrade() %>
                </td>
            </tr>

            <tr>
                <th>Result Status</th>
                <td>
                    <%= result.getResultStatus() %>
                </td>
            </tr>

            <tr>
                <th>Remarks</th>
                <td>
                    <%= result.getRemarks() == null
                            ? "-"
                            : result.getRemarks() %>
                </td>
            </tr>

            <tr>
                <th>Published At</th>
                <td>
                    <%= result.getPublishedAt() == null
                            ? "Not Published"
                            : result.getPublishedAt() %>
                </td>
            </tr>

        </table>

    </div>

    <div class="card">

        <% if (result.getPublishedAt() == null) { %>

            <a href="<%= request.getContextPath() %>/admin/exam-results?action=publish&id=<%= result.getId() %>"
               class="btn btn-primary"
               onclick="return confirm('Publish this result?');">
                Publish Result
            </a>

        <% } else { %>

            <a href="<%= request.getContextPath() %>/admin/exam-results?action=unpublish&id=<%= result.getId() %>"
               class="btn btn-secondary"
               onclick="return confirm('Unpublish this result?');">
                Unpublish Result
            </a>

        <% } %>

    </div>

</div>

</body>
</html>