<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Student" %>

<%
    request.setAttribute("pageTitle", "Students");

    List<Student> students =
        (List<Student>) request.getAttribute("students");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    String created =
        request.getParameter("created");

    String updated =
        request.getParameter("updated");

    String deleted =
        request.getParameter("deleted");

    String error =
        request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Students | FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <!-- SIDEBAR -->
    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <!-- HEADER -->
        <%@ include file="/includes/admin-header.jsp" %>

        <!-- MAIN CONTENT -->
        <main class="admin-content">

            <div class="container">

                <!-- PAGE HEADER -->

                <div class="page-header">

                    <div>
                        <h2>Students</h2>

                        <p>
                            Manage all students and their accounts.
                        </p>
                    </div>

                    <a href="<%= request.getContextPath() %>/admin/students?action=add"
                       class="btn btn-primary">
                        + Add Student
                    </a>

                </div>


                <!-- SUCCESS MESSAGES -->

                <% if (created != null) { %>

                    <div class="alert success">
                        Student created successfully.
                    </div>

                <% } %>


                <% if (updated != null) { %>

                    <div class="alert success">
                        Student updated successfully.
                    </div>

                <% } %>


                <% if (deleted != null) { %>

                    <div class="alert success">
                        Student deleted successfully.
                    </div>

                <% } %>


                <!-- ERROR -->

                <% if (errorMessage != null) { %>

                    <div class="alert error">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <% if (error != null) { %>

                    <div class="alert error">
                        <%= error %>
                    </div>

                <% } %>


                <!-- STUDENT CARD -->

                <div class="card">

                    <div class="card-header">

                        <div>
                            <h3>Student List</h3>

                            <p>
                                View and manage registered students.
                            </p>
                        </div>

                        <div class="student-count">

                            <strong>
                                <%= students != null ? students.size() : 0 %>
                            </strong>

                            <span>Students</span>

                        </div>

                    </div>


                    <% if (students == null || students.isEmpty()) { %>

                        <div class="empty">

                            <div class="empty-icon">
                                👨‍🎓
                            </div>

                            <h3>No Students Found</h3>

                            <p>
                                There are currently no students registered.
                            </p>

                            <a href="<%= request.getContextPath() %>/admin/students?action=add"
                               class="btn btn-primary">
                                + Add First Student
                            </a>

                        </div>

                    <% } else { %>

                        <div class="table-responsive">

                            <table>

                                <thead>

                                    <tr>

                                        <th>#</th>

                                        <th>Admission No.</th>

                                        <th>Student ID</th>

                                        <th>User ID</th>

                                        <th>Gender</th>

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
                                                <%= student.getAdmissionNumber() != null
                                                    ? student.getAdmissionNumber()
                                                    : "-" %>
                                            </strong>
                                        </td>

                                        <td>
                                            <%= student.getId() %>
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
                                            <%= student.getAdmissionDate() != null
                                                ? student.getAdmissionDate()
                                                : "-" %>
                                        </td>

                                        <td>

                                            <span class="status status-<%= 
                                                student.getStatus() != null
                                                    ? student.getStatus().toLowerCase()
                                                    : "unknown"
                                            %>">

                                                <%= student.getStatus() != null
                                                    ? student.getStatus()
                                                    : "UNKNOWN" %>

                                            </span>

                                        </td>

                                        <td>

                                            <div class="table-actions">

                                                <a href="<%= request.getContextPath() %>/admin/students?action=edit&id=<%= student.getId() %>"
                                                   class="btn btn-edit">
                                                    Edit
                                                </a>

                                                <a href="<%= request.getContextPath() %>/admin/students?action=delete&id=<%= student.getId() %>"
                                                   class="btn btn-delete"
                                                   onclick="return confirm('Are you sure you want to delete this student?');">
                                                    Delete
                                                </a>

                                            </div>

                                        </td>

                                    </tr>

                                <% } %>

                                </tbody>

                            </table>

                        </div>

                    <% } %>

                </div>

            </div>

        </main>

    </div>

</div>


<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>