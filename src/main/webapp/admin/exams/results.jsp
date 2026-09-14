<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.ExamResult" %>

<%
    String contextPath = request.getContextPath();

    List<ExamResult> results =
            (List<ExamResult>)
                    request.getAttribute("results");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam Results - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
        }

        .content {
            padding: 30px;
        }

        .box {
            background: white;
            padding: 20px;
            border-radius: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f1f5f9;
        }

        .pass {
            color: #15803d;
            font-weight: bold;
        }

        .fail {
            color: #dc2626;
            font-weight: bold;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Exam Results</h1>

    <div class="box">

        <table>

            <thead>

            <tr>
                <th>ID</th>
                <th>Exam</th>
                <th>Student</th>
                <th>Admission No.</th>
                <th>Marks</th>
                <th>Grade</th>
                <th>Status</th>
                <th>Published</th>
                <th>Action</th>
            </tr>

            </thead>

            <tbody>

            <% if (results != null &&
                   !results.isEmpty()) {

                for (ExamResult result : results) {
            %>

            <tr>

                <td>
                    <%= result.getId() %>
                </td>

                <td>
                    <%= result.getExamTitle() != null
                            ? result.getExamTitle()
                            : result.getExamId() %>
                </td>

                <td>
                    <%= result.getStudentName() != null
                            ? result.getStudentName()
                            : result.getStudentId() %>
                </td>

                <td>
                    <%= result.getAdmissionNumber() != null
                            ? result.getAdmissionNumber()
                            : "-" %>
                </td>

                <td>
                    <%= result.getMarksObtained() %>
                </td>

                <td>
                    <%= result.getGrade() != null
                            ? result.getGrade()
                            : "-" %>
                </td>

                <td>

                    <span class="<%= "PASS".equals(
                            result.getResultStatus())
                            ? "pass"
                            : "fail" %>">

                        <%= result.getResultStatus() %>

                    </span>

                </td>

                <td>
                    <%= result.getPublishedAt() != null
                            ? "Yes"
                            : "No" %>
                </td>

                <td>

                    <a href="<%= contextPath %>/admin/exam-results?id=<%= result.getId() %>">
                        View
                    </a>

                </td>

            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="9">
                    No results available.
                </td>
            </tr>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>