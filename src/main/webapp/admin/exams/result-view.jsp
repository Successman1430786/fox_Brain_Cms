<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.ExamResult" %>

<%
    String contextPath = request.getContextPath();

    ExamResult result =
            (ExamResult) request.getAttribute("result");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result Details - FoxBrain</title>

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
            padding: 30px;
            max-width: 700px;
            border-radius: 10px;
        }

        .row {
            display: flex;
            justify-content: space-between;
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }

        .pass {
            color: #15803d;
            font-weight: bold;
        }

        .fail {
            color: #dc2626;
            font-weight: bold;
        }

        button {
            padding: 10px 18px;
            border: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
            cursor: pointer;
            margin-top: 20px;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Result Details</h1>

    <div class="card">

        <% if (result != null) { %>

            <div class="row">
                <strong>Exam</strong>

                <span>
                    <%= result.getExamTitle() != null
                            ? result.getExamTitle()
                            : result.getExamId() %>
                </span>
            </div>

            <div class="row">
                <strong>Student</strong>

                <span>
                    <%= result.getStudentName() != null
                            ? result.getStudentName()
                            : result.getStudentId() %>
                </span>
            </div>

            <div class="row">
                <strong>Admission Number</strong>

                <span>
                    <%= result.getAdmissionNumber() != null
                            ? result.getAdmissionNumber()
                            : "-" %>
                </span>
            </div>

            <div class="row">
                <strong>Marks Obtained</strong>

                <span>
                    <%= result.getMarksObtained() %>
                </span>
            </div>

            <div class="row">
                <strong>Grade</strong>

                <span>
                    <%= result.getGrade() != null
                            ? result.getGrade()
                            : "-" %>
                </span>
            </div>

            <div class="row">
                <strong>Status</strong>

                <span class="<%= "PASS".equals(
                        result.getResultStatus())
                        ? "pass"
                        : "fail" %>">

                    <%= result.getResultStatus() %>

                </span>
            </div>

            <div class="row">
                <strong>Remarks</strong>

                <span>
                    <%= result.getRemarks() != null
                            ? result.getRemarks()
                            : "-" %>
                </span>
            </div>

            <div class="row">
                <strong>Published</strong>

                <span>
                    <%= result.getPublishedAt() != null
                            ? "Yes"
                            : "No" %>
                </span>
            </div>

            <% if (result.getPublishedAt() == null) { %>

                <form method="post"
                      action="<%= contextPath %>/admin/exam-results">

                    <input type="hidden"
                           name="action"
                           value="publish">

                    <input type="hidden"
                           name="id"
                           value="<%= result.getId() %>">

                    <button type="submit">
                        Publish Result
                    </button>

                </form>

            <% } else { %>

                <form method="post"
                      action="<%= contextPath %>/admin/exam-results">

                    <input type="hidden"
                           name="action"
                           value="unpublish">

                    <input type="hidden"
                           name="id"
                           value="<%= result.getId() %>">

                    <button type="submit">
                        Unpublish Result
                    </button>

                </form>

            <% } %>

        <% } else { %>

            <p>Result not found.</p>

        <% } %>

    </div>

</div>

</body>
</html>