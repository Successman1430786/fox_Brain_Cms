<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    String contextPath = request.getContextPath();

    Exam exam =
            (Exam) request.getAttribute("exam");

    if (exam == null) {
        response.sendRedirect(
                contextPath + "/admin/exams"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Exam - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
        }

        .content {
            padding: 30px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            max-width: 1000px;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }

        .item {
            padding: 12px;
            background: #f8fafc;
            border-radius: 6px;
        }

        .label {
            color: #64748b;
            font-size: 13px;
        }

        .value {
            margin-top: 5px;
            font-weight: bold;
        }

        .actions {
            margin-top: 25px;
        }

        .btn {
            display: inline-block;
            padding: 10px 15px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            margin-right: 8px;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1><%= exam.getTitle() %></h1>

    <div class="card">

        <div class="grid">

            <div class="item">
                <div class="label">Exam ID</div>
                <div class="value"><%= exam.getId() %></div>
            </div>

            <div class="item">
                <div class="label">Batch</div>
                <div class="value">
                    <%= exam.getBatchName() != null
                            ? exam.getBatchName()
                            : exam.getBatchId() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Type</div>
                <div class="value">
                    <%= exam.getExamType() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Mode</div>
                <div class="value">
                    <%= exam.getExamMode() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Date</div>
                <div class="value">
                    <%= exam.getExamDate() != null
                            ? exam.getExamDate()
                            : "-" %>
                </div>
            </div>

            <div class="item">
                <div class="label">Time</div>
                <div class="value">
                    <%= exam.getStartTime() != null
                            ? exam.getStartTime()
                            : "-" %>
                    -
                    <%= exam.getEndTime() != null
                            ? exam.getEndTime()
                            : "-" %>
                </div>
            </div>

            <div class="item">
                <div class="label">Duration</div>
                <div class="value">
                    <%= exam.getDurationMinutes() %> minutes
                </div>
            </div>

            <div class="item">
                <div class="label">Room</div>
                <div class="value">
                    <%= exam.getRoomName() != null
                            ? exam.getRoomName()
                            : "-" %>
                </div>
            </div>

            <div class="item">
                <div class="label">Total Marks</div>
                <div class="value">
                    <%= exam.getTotalMarks() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Passing Marks</div>
                <div class="value">
                    <%= exam.getPassingMarks() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Status</div>
                <div class="value">
                    <%= exam.getStatus() %>
                </div>
            </div>

            <div class="item">
                <div class="label">Navigation</div>
                <div class="value">
                    <%= exam.isAllowNavigation()
                            ? "Allowed"
                            : "Not Allowed" %>
                </div>
            </div>

        </div>

        <hr>

        <h3>Instructions</h3>

        <p>
            <%= exam.getInstructions() != null
                    ? exam.getInstructions()
                    : "No instructions." %>
        </p>

        <div class="actions">

            <a class="btn"
               href="<%= contextPath %>/admin/exams?action=edit&id=<%= exam.getId() %>">
                Edit Exam
            </a>

            <a class="btn"
               href="<%= contextPath %>/admin/exam-questions?examId=<%= exam.getId() %>">
                Manage Questions
            </a>

            <a class="btn"
               href="<%= contextPath %>/admin/exam-results?examId=<%= exam.getId() %>">
                View Results
            </a>

        </div>

    </div>

</div>

</body>
</html>