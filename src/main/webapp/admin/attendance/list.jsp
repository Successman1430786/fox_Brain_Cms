<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Attendance" %>

<%
    request.setAttribute("pageTitle", "Attendance");

    List<Attendance> attendanceList =
            (List<Attendance>) request.getAttribute("attendanceList");

    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");

    String selectedDate =
            request.getAttribute("selectedDate") != null
                    ? request.getAttribute("selectedDate").toString()
                    : "";

    String selectedStatus =
            request.getAttribute("selectedStatus") != null
                    ? request.getAttribute("selectedStatus").toString()
                    : "";
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Attendance - FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <!-- PAGE HEADER -->

            <div class="page-header">

                <div>

                    <h2>Attendance</h2>

                    <p>
                        Manage student attendance records.
                    </p>

                </div>

                <a href="<%= request.getContextPath() %>/admin/attendance?action=add"
                   class="btn btn-primary">

                    + Mark Attendance

                </a>

            </div>


            <!-- SUCCESS MESSAGE -->

            <% if ("created".equals(success)) { %>

                <div class="alert alert-success">

                    Attendance record created successfully.

                </div>

            <% } else if ("updated".equals(success)) { %>

                <div class="alert alert-success">

                    Attendance record updated successfully.

                </div>

            <% } else if ("deleted".equals(success)) { %>

                <div class="alert alert-success">

                    Attendance record deleted successfully.

                </div>

            <% } %>


            <!-- ERROR MESSAGE -->

            <% if (error != null && !error.trim().isEmpty()) { %>

                <div class="alert alert-danger">

                    <%= error %>

                </div>

            <% } %>


            <!-- FILTER CARD -->

            <div class="content-card">

                <form method="get"
                      action="<%= request.getContextPath() %>/admin/attendance">

                    <div class="form-grid">

                        <div class="form-group">

                            <label>
                                Attendance Date
                            </label>

                            <input type="date"
                                   name="date"
                                   value="<%= selectedDate %>">

                        </div>


                        <div class="form-group">

                            <label>
                                Status
                            </label>

                            <select name="status">

                                <option value="">
                                    All Status
                                </option>

                                <option value="PRESENT"
                                    <%= "PRESENT".equals(selectedStatus)
                                            ? "selected" : "" %>>
                                    Present
                                </option>

                                <option value="ABSENT"
                                    <%= "ABSENT".equals(selectedStatus)
                                            ? "selected" : "" %>>
                                    Absent
                                </option>

                                <option value="LATE"
                                    <%= "LATE".equals(selectedStatus)
                                            ? "selected" : "" %>>
                                    Late
                                </option>

                                <option value="EXCUSED"
                                    <%= "EXCUSED".equals(selectedStatus)
                                            ? "selected" : "" %>>
                                    Excused
                                </option>

                            </select>

                        </div>


                        <div class="form-group">

                            <label>
                                &nbsp;
                            </label>

                            <div>

                                <button type="submit"
                                        class="btn btn-primary">

                                    Filter

                                </button>

                                <a href="<%= request.getContextPath() %>/admin/attendance"
                                   class="btn btn-secondary">

                                    Clear

                                </a>

                            </div>

                        </div>

                    </div>

                </form>

            </div>


            <!-- ATTENDANCE TABLE -->

            <div class="content-card">

                <div class="table-wrapper">

                    <table class="admin-table">

                        <thead>

                        <tr>

                            <th>#</th>

                            <th>Student</th>

                            <th>Admission</th>

                            <th>Course</th>

                            <th>Batch</th>

                            <th>Date</th>

                            <th>Status</th>

                            <th>Teacher</th>

                            <th>Actions</th>

                        </tr>

                        </thead>


                        <tbody>

                        <% if (attendanceList != null
                                && !attendanceList.isEmpty()) {

                            int index = 1;

                            for (Attendance attendance : attendanceList) {

                                String status =
                                        attendance.getStatus();

                                String statusClass =
                                        "status-" +
                                        (status != null
                                                ? status.toLowerCase()
                                                : "unknown");

                        %>

                        <tr>

                            <!-- NUMBER -->

                            <td>

                                <%= index++ %>

                            </td>


                            <!-- STUDENT -->

                            <td>

                                <strong>

                                    <%= attendance.getStudentName() != null
                                            ? attendance.getStudentName()
                                            : "-" %>

                                </strong>

                            </td>


                            <!-- ADMISSION NUMBER -->

                            <td>

                                <%= attendance.getAdmissionNumber() != null
                                        ? attendance.getAdmissionNumber()
                                        : "-" %>

                            </td>


                            <!-- COURSE -->

                            <td>

                                <%= attendance.getCourseName() != null
                                        ? attendance.getCourseName()
                                        : "-" %>

                            </td>


                            <!-- BATCH -->

                            <td>

                                <%= attendance.getBatchName() != null
                                        ? attendance.getBatchName()
                                        : "-" %>

                            </td>


                            <!-- DATE -->

                            <td>

                                <%= attendance.getAttendanceDate() != null
                                        ? attendance.getAttendanceDate()
                                        : "-" %>

                            </td>


                            <!-- STATUS -->

                            <td>

                                <span class="status-badge <%= statusClass %>">

                                    <%= status != null
                                            ? status
                                            : "-" %>

                                </span>

                            </td>


                            <!-- TEACHER -->

                            <td>

                                <%= attendance.getTeacherName() != null
                                        ? attendance.getTeacherName()
                                        : "-" %>

                            </td>


                            <!-- ACTIONS -->

                            <td>

                                <div class="table-actions">

                                    <a href="<%= request.getContextPath() %>/admin/attendance?action=edit&id=<%= attendance.getId() %>"
                                       class="btn btn-sm btn-secondary">

                                        Edit

                                    </a>

                                    <a href="<%= request.getContextPath() %>/admin/attendance?action=delete&id=<%= attendance.getId() %>"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Delete this attendance record?');">

                                        Delete

                                    </a>

                                </div>

                            </td>

                        </tr>

                        <%

                            }

                        } else {

                        %>

                        <tr>

                            <td colspan="9"
                                class="empty-state">

                                <div class="empty-state-icon">

                                    📅

                                </div>

                                <h3>No attendance records found</h3>

                                <p>

                                    Start by marking attendance for a student.

                                </p>

                                <a href="<%= request.getContextPath() %>/admin/attendance?action=add"
                                   class="btn btn-primary">

                                    + Mark Attendance

                                </a>

                            </td>

                        </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </main>

    </div>

</div>


<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>