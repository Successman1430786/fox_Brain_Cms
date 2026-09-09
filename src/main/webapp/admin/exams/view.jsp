<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    Exam exam = (Exam) request.getAttribute("exam");

    if (exam == null) {
        response.sendRedirect(
            request.getContextPath() + "/admin/exams?action=list"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <title><%= exam.getTitle() %> - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>
            <h1><%= exam.getTitle() %></h1>
            <p>Exam details and management.</p>
        </div>

        <div>

            <a href="<%= request.getContextPath() %>/admin/exams?action=edit&id=<%= exam.getId() %>"
               class="btn btn-primary">
                Edit
            </a>

            <a href="<%= request.getContextPath() %>/admin/exams?action=list"
               class="btn btn-secondary">
                Back
            </a>

        </div>

    </div>

    <div class="card">

        <h2>Exam Information</h2>

        <table class="admin-table">

            <tr>
                <th>ID</th>
                <td><%= exam.getId() %></td>
            </tr>

            <tr>
                <th>Batch</th>
                <td><%= exam.getBatchId() %></td>
            </tr>

            <tr>
                <th>Type</th>
                <td><%= exam.getExamType() %></td>
            </tr>

            <tr>
                <th>Mode</th>
                <td><%= exam.getExamMode() %></td>
            </tr>

            <tr>
                <th>Date</th>
                <td><%= exam.getExamDate() == null ? "-" : exam.getExamDate() %></td>
            </tr>

            <tr>
                <th>Start</th>
                <td><%= exam.getStartTime() == null ? "-" : exam.getStartTime() %></td>
            </tr>

            <tr>
                <th>End</th>
                <td><%= exam.getEndTime() == null ? "-" : exam.getEndTime() %></td>
            </tr>

            <tr>
                <th>Duration</th>
                <td>
                    <%= exam.getDurationMinutes() == null
                            ? "-"
                            : exam.getDurationMinutes() + " minutes" %>
                </td>
            </tr>

            <tr>
                <th>Total Marks</th>
                <td><%= exam.getTotalMarks() %></td>
            </tr>

            <tr>
                <th>Passing Marks</th>
                <td>
                    <%= exam.getPassingMarks() == null
                            ? "-"
                            : exam.getPassingMarks() %>
                </td>
            </tr>

            <tr>
                <th>Room</th>
                <td>
                    <%= exam.getRoomName() == null
                            ? "-"
                            : exam.getRoomName() %>
                </td>
            </tr>

            <tr>
                <th>Status</th>
                <td><%= exam.getStatus() %></td>
            </tr>

        </table>

    </div>

    <div class="card">

        <h2>Exam Management</h2>

        <a href="<%= request.getContextPath() %>/admin/exam-questions?action=list&examId=<%= exam.getId() %>"
           class="btn btn-primary">
            Manage Questions
        </a>

        <a href="<%= request.getContextPath() %>/admin/exam-results?action=list&examId=<%= exam.getId() %>"
           class="btn btn-secondary">
            View Results
        </a>

    </div>

    <div class="card">

        <h2>Instructions</h2>

        <p>
            <%= exam.getInstructions() == null ||
                exam.getInstructions().isEmpty()
                ? "No instructions provided."
                : exam.getInstructions() %>
        </p>

    </div>

</div>

</body>
</html>