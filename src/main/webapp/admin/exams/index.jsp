<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    String contextPath = request.getContextPath();

    List<Exam> exams =
            (List<Exam>) request.getAttribute("exams");

    String error =
            (String) request.getAttribute("error");

    String success =
            (String) request.getAttribute("success");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Exams - FoxBrain</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #f5f6fa;
            color: #111827;
        }

        /* MAIN CONTENT */

        .exam-content {
            margin-left: 260px;
            padding: 30px;
            min-height: 100vh;
        }

        /* TOP BAR */

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .topbar h1 {
            margin: 0 0 5px 0;
            font-size: 30px;
        }

        .topbar p {
            margin: 0;
            color: #6b7280;
            font-size: 14px;
        }

        /* BUTTON */

        .btn {
            display: inline-block;
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
            font-weight: 600;
            font-size: 14px;
        }

        .btn:hover {
            background: #1d4ed8;
        }

        /* ALERT */

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
        }

        .success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
        }

        /* TABLE */

        .table-box {
            background: white;
            padding: 20px;
            border-radius: 10px;
            overflow-x: auto;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .table-title {
            margin-bottom: 15px;
        }

        .table-title h2 {
            margin: 0;
            font-size: 20px;
        }

        .table-title p {
            margin: 5px 0 0;
            color: #6b7280;
            font-size: 13px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            min-width: 950px;
        }

        th,
        td {
            padding: 12px;
            border-bottom: 1px solid #e5e7eb;
            text-align: left;
        }

        th {
            background: #f1f5f9;
            color: #374151;
            font-size: 13px;
        }

        td {
            font-size: 14px;
        }

        tr:hover td {
            background: #f9fafb;
        }

        /* BADGES */

        .badge {
            display: inline-block;
            padding: 5px 9px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: 600;
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

        /* ACTIONS */

        .actions {
            white-space: nowrap;
        }

        .actions a {
            display: inline-block;
            margin-right: 6px;
            margin-bottom: 4px;
            padding: 6px 9px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 12px;
            background: #f1f5f9;
            color: #334155;
        }

        .actions a:hover {
            background: #e2e8f0;
        }

        .actions .danger {
            color: #dc2626;
            background: #fef2f2;
        }

        .actions .danger:hover {
            background: #fee2e2;
        }

        .empty {
            text-align: center;
            padding: 40px;
            color: #6b7280;
        }

        /* RESPONSIVE */

        @media (max-width: 900px) {

            .exam-content {
                margin-left: 220px;
                padding: 20px;
            }

        }

        @media (max-width: 700px) {

            .exam-content {
                margin-left: 70px;
                padding: 15px;
            }

            .topbar {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }

        }

    </style>

</head>


<body>


<!-- ========================= -->
<!-- SIDEBAR -->
<!-- ========================= -->

<%@ include file="/includes/admin-sidebar.jsp" %>


<!-- ========================= -->
<!-- MAIN CONTENT -->
<!-- ========================= -->

<div class="exam-content">


    <!-- TOP BAR -->

    <div class="topbar">

        <div>

            <h1>Exams</h1>

            <p>
                Manage online and offline examinations.
            </p>

        </div>


        <a class="btn"
           href="<%= contextPath %>/admin/exams/add.jsp">

            + Create Exam

        </a>

    </div>


    <!-- SUCCESS -->

    <% if (success != null && !success.trim().isEmpty()) { %>

        <div class="success">

            <%= success %>

        </div>

    <% } %>


    <!-- ERROR -->

    <% if (error != null && !error.trim().isEmpty()) { %>

        <div class="error">

            <%= error %>

        </div>

    <% } %>


    <!-- TABLE -->

    <div class="table-box">


        <div class="table-title">

            <h2>All Exams</h2>

            <p>
                View, edit, manage questions and results.
            </p>

        </div>


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


            <% if (exams != null && !exams.isEmpty()) { %>


                <% for (Exam exam : exams) { %>


                <tr>


                    <!-- ID -->

                    <td>

                        <%= exam.getId() %>

                    </td>


                    <!-- TITLE -->

                    <td>

                        <strong>

                            <%= exam.getTitle() %>

                        </strong>

                    </td>


                    <!-- TYPE -->

                    <td>

                        <%= exam.getExamType() != null
                                ? exam.getExamType()
                                : "-" %>

                    </td>


                    <!-- MODE -->

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


                    <!-- DATE -->

                    <td>

                        <%= exam.getExamDate() != null
                                ? exam.getExamDate()
                                : "-" %>

                    </td>


                    <!-- MARKS -->

                    <td>

                        <%= exam.getTotalMarks() %>

                    </td>


                    <!-- STATUS -->

                    <td>

                        <%

                            String status = exam.getStatus();

                            if (status == null ||
                                status.trim().isEmpty()) {

                                status = "DRAFT";

                            }

                            String css = "draft";


                            if ("SCHEDULED".equalsIgnoreCase(status)) {

                                css = "scheduled";

                            }

                            else if ("COMPLETED".equalsIgnoreCase(status)) {

                                css = "completed";

                            }

                            else if ("CANCELLED".equalsIgnoreCase(status)) {

                                css = "cancelled";

                            }

                        %>


                        <span class="badge <%= css %>">

                            <%= status %>

                        </span>


                    </td>


                    <!-- ACTIONS -->

                    <td class="actions">


                        <!-- VIEW -->

                        <a href="<%= contextPath %>/admin/exams?action=view&id=<%= exam.getId() %>">

                            View

                        </a>


                        <!-- EDIT -->

                        <a href="<%= contextPath %>/admin/exams?action=edit&id=<%= exam.getId() %>">

                            Edit

                        </a>


                        <!-- QUESTIONS -->

                        <a href="<%= contextPath %>/admin/exams/exam-questions.jsp?examId=<%= exam.getId() %>">

                            Questions

                        </a>


                        <!-- RESULTS -->

                        <a href="<%= contextPath %>/admin/exam-results?examId=<%= exam.getId() %>">

                            Results

                        </a>


                        <!-- DELETE -->

                        <a class="danger"
                           href="<%= contextPath %>/admin/exams?action=delete&id=<%= exam.getId() %>"
                           onclick="return confirm('Delete this exam?');">

                            Delete

                        </a>


                    </td>


                </tr>


                <% } %>


            <% } else { %>


                <tr>

                    <td colspan="8">

                        <div class="empty">

                            No exams found.

                        </div>

                    </td>

                </tr>


            <% } %>


            </tbody>

        </table>


    </div>


</div>


</body>

</html>