<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Student" %>

<%
    // Security check
    if (session.getAttribute("loggedInUser") == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );
        return;
    }

    List<Student> students =
        (List<Student>) request.getAttribute("students");

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Students - FoxBrain Admin</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #222;
        }

        .header {
            height: 65px;
            background: #1e293b;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
        }

        .header h2 {
            margin: 0;
        }

        .container {
            padding: 30px;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .top-bar h1 {
            margin: 0;
            font-size: 28px;
        }

        .btn {
            display: inline-block;
            padding: 10px 16px;
            border-radius: 6px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-primary {
            background: #2563eb;
            color: white;
        }

        .btn-edit {
            background: #f59e0b;
            color: white;
        }

        .btn-delete {
            background: #dc2626;
            color: white;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .success {
            background: #dcfce7;
            color: #166534;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th,
        td {
            padding: 14px 12px;
            border-bottom: 1px solid #e5e7eb;
            text-align: left;
        }

        th {
            background: #f8fafc;
            font-size: 14px;
        }

        td {
            font-size: 14px;
        }

        .status {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            background: #dcfce7;
            color: #166534;
            font-size: 12px;
            font-weight: bold;
        }

        .actions {
            display: flex;
            gap: 7px;
        }

        .empty {
            text-align: center;
            padding: 40px;
            color: #64748b;
        }

    </style>

</head>

<body>

    <div class="header">

        <h2>FoxBrain Admin</h2>

        <a
            href="<%= request.getContextPath() %>/admin/dashboard.jsp"
            class="btn"
            style="color:white;"
        >
            Dashboard
        </a>

    </div>


    <div class="container">

        <div class="top-bar">

            <h1>Students</h1>

            <a
                href="<%= request.getContextPath() %>/admin/students?action=add"
                class="btn btn-primary"
            >
                + Add Student
            </a>

        </div>


        <!-- SUCCESS MESSAGES -->

        <% if ("created".equals(success)) { %>

            <div class="alert success">
                Student added successfully.
            </div>

        <% } else if ("updated".equals(success)) { %>

            <div class="alert success">
                Student updated successfully.
            </div>

        <% } else if ("deleted".equals(success)) { %>

            <div class="alert success">
                Student deleted successfully.
            </div>

        <% } %>


        <!-- ERROR MESSAGE -->

        <% if ("delete".equals(error)) { %>

            <div class="alert error">
                Student could not be deleted.
            </div>

        <% } %>


        <div class="card">

            <% if (students == null || students.isEmpty()) { %>

                <div class="empty">

                    <h3>No students found</h3>

                    <p>
                        Start by adding your first student.
                    </p>

                    <a
                        href="<%= request.getContextPath() %>/admin/students?action=add"
                        class="btn btn-primary"
                    >
                        + Add Student
                    </a>

                </div>

            <% } else { %>

                <table>

                    <thead>

                        <tr>

                            <th>ID</th>

                            <th>Admission Number</th>

                            <th>User ID</th>

                            <th>Gender</th>

                            <th>City</th>

                            <th>Admission Date</th>

                            <th>Status</th>

                            <th>Actions</th>

                        </tr>

                    </thead>

                    <tbody>

                    <% for (Student student : students) { %>

                        <tr>

                            <td>
                                <%= student.getId() %>
                            </td>

                            <td>
                                <strong>
                                    <%= student.getAdmissionNumber() %>
                                </strong>
                            </td>

                            <td>
                                <%= student.getUserId() %>
                            </td>

                            <td>
                                <%= student.getGender() != null
                                    ? student.getGender()
                                    : "-" %>
                            </td>

                            <td>
                                <%= student.getCity() != null
                                    ? student.getCity()
                                    : "-" %>
                            </td>

                            <td>
                                <%= student.getAdmissionDate() != null
                                    ? student.getAdmissionDate()
                                    : "-" %>
                            </td>

                            <td>

                                <span class="status">
                                    <%= student.getStatus() != null
                                        ? student.getStatus()
                                        : "ACTIVE" %>
                                </span>

                            </td>

                            <td>

                                <div class="actions">

                                    <a
                                        href="<%= request.getContextPath() %>/admin/students?action=edit&id=<%= student.getId() %>"
                                        class="btn btn-edit"
                                    >
                                        Edit
                                    </a>

                                    <a
                                        href="<%= request.getContextPath() %>/admin/students?action=delete&id=<%= student.getId() %>"
                                        class="btn btn-delete"
                                        onclick="return confirm('Are you sure you want to delete this student?');"
                                    >
                                        Delete
                                    </a>

                                </div>

                            </td>

                        </tr>

                    <% } %>

                    </tbody>

                </table>

            <% } %>

        </div>

    </div>

</body>
</html>