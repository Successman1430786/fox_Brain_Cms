<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.foxbrain.model.Teacher" %>
<%@ page import="java.util.List" %>

<%
    request.setAttribute(
        "pageTitle",
        "Teachers"
    );

    List<Teacher> teachers =
        (List<Teacher>) request.getAttribute(
            "teachers"
        );

    String errorMessage =
        (String) request.getAttribute(
            "errorMessage"
        );

    String success =
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

    <title>
        FoxBrain - Teachers
    </title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="container">


                <!-- PAGE HEADER -->

                <div class="page-header">

                    <div>

                        <h2>
                            Teachers
                        </h2>

                        <p>
                            Manage FoxBrain Institute
                            teachers and faculty members.
                        </p>

                    </div>

                    <div>

                        <a href="<%= request.getContextPath() %>/admin/teachers?action=add"
                           class="btn btn-primary">

                            + Add Teacher

                        </a>

                    </div>

                </div>


                <!-- SUCCESS -->

                <% if (success != null) { %>

                    <div class="alert success">
                        Teacher created successfully.
                    </div>

                <% } %>


                <% if (updated != null) { %>

                    <div class="alert success">
                        Teacher updated successfully.
                    </div>

                <% } %>


                <% if (deleted != null) { %>

                    <div class="alert success">
                        Teacher deleted successfully.
                    </div>

                <% } %>


                <!-- ERROR -->

                <% if (error != null) { %>

                    <div class="alert error">
                        <%= error %>
                    </div>

                <% } %>


                <% if (errorMessage != null) { %>

                    <div class="alert error">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <!-- TEACHER CARD -->

                <div class="card">

                    <div class="card-header">

                        <div>

                            <h3>
                                All Teachers
                            </h3>

                            <p>
                                Registered teachers and faculty.
                            </p>

                        </div>

                        <div class="student-count">

                            <strong>
                                <%= teachers != null
                                    ? teachers.size()
                                    : 0 %>
                            </strong>

                            <span>
                                Teachers
                            </span>

                        </div>

                    </div>


                    <% if (teachers == null ||
                           teachers.isEmpty()) { %>


                        <div class="empty">

                            <div class="empty-icon">
                                👨‍🏫
                            </div>

                            <h3>
                                No Teachers Found
                            </h3>

                            <p>
                                Start by adding your first
                                teacher.
                            </p>

                            <a href="<%= request.getContextPath() %>/admin/teachers?action=add"
                               class="btn btn-primary">

                                + Add Teacher

                            </a>

                        </div>


                    <% } else { %>


                        <div class="table-responsive">

                            <table>

                                <thead>

                                    <tr>

                                        <th>
                                            ID
                                        </th>

                                        <th>
                                            Employee Number
                                        </th>

                                        <th>
                                            Qualification
                                        </th>

                                        <th>
                                            Specialization
                                        </th>

                                        <th>
                                            Joining Date
                                        </th>

                                        <th>
                                            Status
                                        </th>

                                        <th>
                                            Actions
                                        </th>

                                    </tr>

                                </thead>


                                <tbody>

                                <% for (
                                    Teacher teacher :
                                    teachers
                                ) { %>


                                    <tr>

                                        <td>
                                            #<%= teacher.getId() %>
                                        </td>


                                        <td>

                                            <strong>
                                                <%= teacher.getEmployeeNumber() != null
                                                    ? teacher.getEmployeeNumber()
                                                    : "-" %>
                                            </strong>

                                        </td>


                                        <td>
                                            <%= teacher.getQualification() != null
                                                ? teacher.getQualification()
                                                : "-" %>
                                        </td>


                                        <td>
                                            <%= teacher.getSpecialization() != null
                                                ? teacher.getSpecialization()
                                                : "-" %>
                                        </td>


                                        <td>
                                            <%= teacher.getJoiningDate() != null
                                                ? teacher.getJoiningDate()
                                                : "-" %>
                                        </td>


                                        <td>

                                            <span class="status status-<%= 
                                                teacher.getStatus() != null
                                                    ? teacher.getStatus().toLowerCase()
                                                    : "unknown"
                                            %>">

                                                <%= teacher.getStatus() != null
                                                    ? teacher.getStatus()
                                                    : "UNKNOWN" %>

                                            </span>

                                        </td>


                                        <td>

                                            <div class="table-actions">

                                                <a href="<%= request.getContextPath() %>/admin/teachers?action=edit&id=<%= teacher.getId() %>"
                                                   class="btn btn-edit">

                                                    Edit

                                                </a>


                                                <a href="<%= request.getContextPath() %>/admin/teachers?action=delete&id=<%= teacher.getId() %>"
                                                   class="btn btn-delete"
                                                   onclick="return confirm('Are you sure you want to delete this teacher?');">

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