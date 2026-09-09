<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>

<%@ page import="com.foxbrain.model.Attendance" %>

<%@ page import="com.foxbrain.model.Enrollment" %>

<%@ page import="com.foxbrain.model.Teacher" %>

<%
    request.setAttribute("pageTitle", "Edit Attendance");

    Attendance attendance =
            (Attendance) request.getAttribute("attendance");

    List<Enrollment> enrollments =
            (List<Enrollment>) request.getAttribute("enrollments");

    List<Teacher> teachers =
            (List<Teacher>) request.getAttribute("teachers");

    String error =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Edit Attendance - FoxBrain Admin</title>

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

                    <h2>Edit Attendance</h2>

                    <p>
                        Update student attendance details.
                    </p>

                </div>

                <a href="<%= request.getContextPath() %>/admin/attendance"
                   class="btn btn-secondary">

                    ← Back

                </a>

            </div>


            <!-- ERROR -->

            <% if (error != null && !error.trim().isEmpty()) { %>

                <div class="alert alert-danger">

                    <%= error %>

                </div>

            <% } %>


            <!-- FORM CARD -->

            <div class="content-card">

                <form method="post"
                      action="<%= request.getContextPath() %>/admin/attendance">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           name="id"
                           value="<%= attendance.getId() %>">


                    <div class="form-grid">


                        <!-- ENROLLMENT -->

                        <div class="form-group">

                            <label>
                                Student / Enrollment *
                            </label>

                            <select name="enrollmentId"
                                    required>

                                <option value="">
                                    Select Student
                                </option>

                                <% if (enrollments != null) {

                                    for (Enrollment enrollment : enrollments) {

                                        boolean selected =
                                                enrollment.getId()
                                                        == attendance.getEnrollmentId();

                                %>

                                <option value="<%= enrollment.getId() %>"
                                    <%= selected ? "selected" : "" %>>

                                    <%= enrollment.getEnrollmentNumber() %>

                                </option>

                                <% }

                                } %>

                            </select>

                        </div>


                        <!-- DATE -->

                        <div class="form-group">

                            <label>
                                Attendance Date *
                            </label>

                            <input type="date"
                                   name="attendanceDate"
                                   value="<%= attendance.getAttendanceDate() != null
                                           ? attendance.getAttendanceDate()
                                           : "" %>"
                                   required>

                        </div>


                        <!-- STATUS -->

                        <div class="form-group">

                            <label>
                                Status *
                            </label>

                            <select name="status"
                                    required>

                                <option value="PRESENT"
                                    <%= "PRESENT".equals(attendance.getStatus())
                                            ? "selected"
                                            : "" %>>

                                    Present

                                </option>

                                <option value="ABSENT"
                                    <%= "ABSENT".equals(attendance.getStatus())
                                            ? "selected"
                                            : "" %>>

                                    Absent

                                </option>

                                <option value="LATE"
                                    <%= "LATE".equals(attendance.getStatus())
                                            ? "selected"
                                            : "" %>>

                                    Late

                                </option>

                                <option value="EXCUSED"
                                    <%= "EXCUSED".equals(attendance.getStatus())
                                            ? "selected"
                                            : "" %>>

                                    Excused

                                </option>

                            </select>

                        </div>


                        <!-- TEACHER -->

                        <div class="form-group">

                            <label>
                                Marked By Teacher
                            </label>

                            <select name="markedByTeacherId">

                                <option value="">
                                    Select Teacher
                                </option>

                                <% if (teachers != null) {

                                    for (Teacher teacher : teachers) {

                                        boolean selected =
                                                attendance.getMarkedByTeacherId() != null
                                                && attendance.getMarkedByTeacherId()
                                                        .longValue()
                                                        == teacher.getId();

                                %>

                                <option value="<%= teacher.getId() %>"
                                    <%= selected ? "selected" : "" %>>

                                    <%= teacher.getEmployeeNumber() %>

                                </option>

                                <% }

                                } %>

                            </select>

                        </div>


                        <!-- CHECK IN -->

                        <div class="form-group">

                            <label>
                                Check In Time
                            </label>

                            <input type="time"
                                   name="checkInTime"
                                   value="<%= attendance.getCheckInTime() != null
                                           ? attendance.getCheckInTime()
                                           : "" %>">

                        </div>


                        <!-- CHECK OUT -->

                        <div class="form-group">

                            <label>
                                Check Out Time
                            </label>

                            <input type="time"
                                   name="checkOutTime"
                                   value="<%= attendance.getCheckOutTime() != null
                                           ? attendance.getCheckOutTime()
                                           : "" %>">

                        </div>


                        <!-- REMARKS -->

                        <div class="form-group form-group-full">

                            <label>
                                Remarks
                            </label>

                            <textarea name="remarks"
                                      rows="5"
                                      maxlength="500"
                                      placeholder="Attendance remarks..."><%= attendance.getRemarks() != null
                                            ? attendance.getRemarks()
                                            : "" %></textarea>

                        </div>

                    </div>


                    <!-- FORM ACTIONS -->

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/attendance"
                           class="btn btn-secondary">

                            Cancel

                        </a>

                        <button type="submit"
                                class="btn btn-primary">

                            Update Attendance

                        </button>

                    </div>

                </form>

            </div>

        </main>

    </div>

</div>


<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>