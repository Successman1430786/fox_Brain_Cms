<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    String contextPath = request.getContextPath();

    List<Exam> exams =
            (List<Exam>) request.getAttribute("exams");

    String error =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exams - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            margin: 0;
        }

        .content {
            padding: 30px;
        }

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        h1 {
            margin: 0;
        }

        .btn {
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
        }

        .table-box {
            background: white;
            padding: 20px;
            border-radius: 10px;
            overflow-x: auto;
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

        .badge {
            padding: 5px 9px;
            border-radius: 15px;
            font-size: 12px;
        }

        .online {
            background: #dcfce7;
            color: #166534;
        }

        .offline {
            background: #dbeafe;
            color: #1e40af;
        }

        .draft {
            background: #fef3c7;
            color: #92400e;
        }

        .scheduled {
            background: #dcfce7;
            color: #166534;
        }

        .completed {
            background: #e0e7ff;
            color: #3730a3;
        }

        .cancelled {
            background: #fee2e2;
            color: #991b1b;
        }

        .actions a {
            margin-right: 8px;
            text-decoration: none;
        }

        .danger {
            color: #dc2626;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
        }
    </style>
</head>

<body>


<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <div class="topbar">
        <div>
            <h1>Exams</h1>
            <p>Manage online and offline examinations.</p>
        </div>

        <a class="btn"
           href="<%= contextPath %>/admin/exams/add.jsp">
            + Create Exam
        </a>
    </div>

    <% if (error != null) { %>
        <div class="error">
            <%= error %>
        </div>
    <% } %>

    <div class="table-box">

        <table>

            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Type</th>
                <th>Mode</th>
                <th>Date</th>
                <th>Total Marks</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>

            <% if (exams != null && !exams.isEmpty()) {

                for (Exam exam : exams) {
            %>

            <tr>

                <td><%= exam.getId() %></td>

                <td>
                    <strong>
                        <%= exam.getTitle() %>
                    </strong>
                </td>

                <td>
                    <%= exam.getExamType() %>
                </td>

                <td>

                    <% if ("ONLINE".equalsIgnoreCase(
                            exam.getExamMode())) { %>

                        <span class="badge online">
                            ONLINE
                        </span>

                    <% } else { %>

                        <span class="badge offline">
                            OFFLINE
                        </span>

                    <% } %>

                </td>

                <td>
                    <%= exam.getExamDate() != null
                            ? exam.getExamDate()
                            : "-" %>
                </td>

                <td>
                    <%= exam.getTotalMarks() %>
                </td>

                <td>

                    <%
                        String status = exam.getStatus();

                        String css = "draft";

                        if ("SCHEDULED".equalsIgnoreCase(status))
                            css = "scheduled";
                        else if ("COMPLETED".equalsIgnoreCase(status))
                            css = "completed";
                        else if ("CANCELLED".equalsIgnoreCase(status))
                            css = "cancelled";
                    %>

                    <span class="badge <%= css %>">
                        <%= status %>
                    </span>

                </td>

                <td class="actions">

                    <a href="<%= contextPath %>/admin/exams?action=view&id=<%= exam.getId() %>">
                        View
                    </a>

                    <a href="<%= contextPath %>/admin/exams?action=edit&id=<%= exam.getId() %>">
                        Edit
                    </a>

                    <a href="<%= contextPath %>/admin/exam-questions?examId=<%= exam.getId() %>">
                        Questions
                    </a>

                    <a href="<%= contextPath %>/admin/exam-results?examId=<%= exam.getId() %>">
                        Results
                    </a>

                    <a class="danger"
                       href="<%= contextPath %>/admin/exams?action=delete&id=<%= exam.getId() %>"
                       onclick="return confirm('Delete this exam?');">
                        Delete
                    </a>

                </td>

            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="8">
                    No exams found.
                </td>
            </tr>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>