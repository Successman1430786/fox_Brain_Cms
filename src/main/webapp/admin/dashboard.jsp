<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.foxbrain.model.Dashboard" %>
<%@ page import="com.foxbrain.model.Student" %>
<%@ page import="java.util.List" %>

<%
    request.setAttribute(
        "pageTitle",
        "Dashboard"
    );

    Dashboard dashboard =
        (Dashboard) request.getAttribute("dashboard");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    if (dashboard == null) {

        dashboard =
            new Dashboard();

        dashboard.setRecentStudents(
            new java.util.ArrayList<Student>()
        );
    }

    List<Student> recentStudents =
        dashboard.getRecentStudents();

    String dashboardFirstName =
            (String) session.getAttribute("firstName");

        if (dashboardFirstName == null ||
            dashboardFirstName.trim().isEmpty()) {

            dashboardFirstName = "Admin";
        }
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        FoxBrain Admin Dashboard
    </title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">


    <!-- =====================================================
         SIDEBAR
         ===================================================== -->

    <%@ include file="/includes/admin-sidebar.jsp" %>


    <!-- =====================================================
         MAIN AREA
         ===================================================== -->

    <div class="admin-main-area">


        <!-- HEADER -->

        <%@ include file="/includes/admin-header.jsp" %>


        <!-- MAIN CONTENT -->

        <main class="admin-content">

            <div class="container">


                <!-- =================================================
                     WELCOME
                     ================================================= -->

                <section class="dashboard-welcome">

                    <h2>
                        Welcome back,
                       <%= dashboardFirstName %>👋
                    </h2>

                    <p>
                        Here's what's happening at
                        FoxBrain Institute today.
                    </p>

                </section>


                <!-- ERROR -->

                <% if (errorMessage != null) { %>

                    <div class="alert error">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <!-- =================================================
                     KPI CARDS
                     ================================================= -->

                <section class="kpi-grid">


                    <!-- STUDENTS -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            👨‍🎓
                        </div>

                        <h3>
                            Total Students
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getTotalStudents() %>
                        </span>

                    </div>


                    <!-- TEACHERS -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            👨‍🏫
                        </div>

                        <h3>
                            Total Teachers
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getTotalTeachers() %>
                        </span>

                    </div>


                    <!-- COURSES -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            📚
                        </div>

                        <h3>
                            Total Courses
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getTotalCourses() %>
                        </span>

                    </div>


                    <!-- BATCHES -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            🏫
                        </div>

                        <h3>
                            Total Batches
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getTotalBatches() %>
                        </span>

                    </div>

                </section>


                <!-- =================================================
                     SECONDARY STATISTICS
                     ================================================= -->

                <section class="kpi-grid">


                    <!-- ADMISSIONS -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            📝
                        </div>

                        <h3>
                            Pending Admissions
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getPendingAdmissions() %>
                        </span>

                    </div>


                    <!-- ATTENDANCE -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            📋
                        </div>

                        <h3>
                            Today's Attendance
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getTodayAttendance() %>
                        </span>

                    </div>


                    <!-- FEES -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            💰
                        </div>

                        <h3>
                            Pending Fees
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getPendingFees() %>
                        </span>

                    </div>


                    <!-- EXAMS -->

                    <div class="kpi-card">

                        <div class="kpi-icon">
                            📝
                        </div>

                        <h3>
                            Upcoming Exams
                        </h3>

                        <span class="kpi-number">
                            <%= dashboard.getUpcomingExams() %>
                        </span>

                    </div>

                </section>


                <!-- =================================================
                     DASHBOARD GRID
                     ================================================= -->

                <section class="dashboard-grid">


                    <!-- =============================================
                         RECENT STUDENTS
                         ============================================= -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Recent Students
                                </h3>

                                <p>
                                    Latest student registrations
                                </p>

                            </div>

                            <a href="<%= request.getContextPath() %>/admin/students"
                               class="btn btn-edit">
                                View All
                            </a>

                        </div>


                        <% if (recentStudents == null ||
                               recentStudents.isEmpty()) { %>


                            <div class="empty">

                                <div class="empty-icon">
                                    👨‍🎓
                                </div>

                                <h3>
                                    No Students Yet
                                </h3>

                                <p>
                                    New students will appear here.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/students?action=add"
                                   class="btn btn-primary">
                                    + Add Student
                                </a>

                            </div>


                        <% } else { %>


                            <div class="table-responsive">

                                <table>

                                    <thead>

                                        <tr>

                                            <th>
                                                Admission
                                            </th>

                                            <th>
                                                Student ID
                                            </th>

                                            <th>
                                                Status
                                            </th>

                                            <th>
                                                Action
                                            </th>

                                        </tr>

                                    </thead>


                                    <tbody>

                                    <% for (
                                        Student student :
                                        recentStudents
                                    ) { %>


                                        <tr>

                                            <td>

                                                <strong>
                                                    <%= student.getAdmissionNumber() != null
                                                        ? student.getAdmissionNumber()
                                                        : "-" %>
                                                </strong>

                                            </td>


                                            <td>
                                                #<%= student.getId() %>
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

                                                <a href="<%= request.getContextPath() %>/admin/students?action=edit&id=<%= student.getId() %>"
                                                   class="btn btn-edit">

                                                    View

                                                </a>

                                            </td>

                                        </tr>


                                    <% } %>

                                    </tbody>

                                </table>

                            </div>


                        <% } %>

                    </div>


                    <!-- =============================================
                         QUICK ACTIONS
                         ============================================= -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Quick Actions
                                </h3>

                                <p>
                                    Frequently used actions
                                </p>

                            </div>

                        </div>


                        <div class="quick-actions">


                            <a href="<%= request.getContextPath() %>/admin/students?action=add"
                               class="quick-action">

                                <span>
                                    👨‍🎓
                                </span>

                                <div>
                                    <strong>
                                        Add Student
                                    </strong>

                                    <small>
                                        Create student account
                                    </small>
                                </div>

                            </a>


                            <a href="<%= request.getContextPath() %>/admin/teachers?action=add"
                               class="quick-action">

                                <span>
                                    👨‍🏫
                                </span>

                                <div>
                                    <strong>
                                        Add Teacher
                                    </strong>

                                    <small>
                                        Register new teacher
                                    </small>
                                </div>

                            </a>


                            <a href="<%= request.getContextPath() %>/admin/courses?action=add"
                               class="quick-action">

                                <span>
                                    📚
                                </span>

                                <div>
                                    <strong>
                                        Add Course
                                    </strong>

                                    <small>
                                        Create new course
                                    </small>

                                </div>

                            </a>


                            <a href="<%= request.getContextPath() %>/admin/admissions"
                               class="quick-action">

                                <span>
                                    📝
                                </span>

                                <div>

                                    <strong>
                                        Admissions
                                    </strong>

                                    <small>
                                        Manage applications
                                    </small>

                                </div>

                            </a>


                            <a href="<%= request.getContextPath() %>/admin/announcements"
                               class="quick-action">

                                <span>
                                    📢
                                </span>

                                <div>

                                    <strong>
                                        Announcement
                                    </strong>

                                    <small>
                                        Publish announcement
                                    </small>

                                </div>

                            </a>


                        </div>

                    </div>


                </section>


                <!-- =================================================
                     SYSTEM OVERVIEW
                     ================================================= -->

                <div class="card">

                    <div class="card-header">

                        <div>

                            <h3>
                                FoxBrain Institute Overview
                            </h3>

                            <p>
                                Quick overview of your institute
                                management system.
                            </p>

                        </div>

                    </div>


                    <div class="overview-grid">


                        <div class="overview-item">

                            <span class="overview-icon">
                                🎓
                            </span>

                            <div>

                                <strong>
                                    Student Management
                                </strong>

                                <small>
                                    Accounts, admissions and
                                    student records
                                </small>

                            </div>

                        </div>


                        <div class="overview-item">

                            <span class="overview-icon">
                                📖
                            </span>

                            <div>

                                <strong>
                                    Academic Management
                                </strong>

                                <small>
                                    Courses, batches,
                                    assignments and exams
                                </small>

                            </div>

                        </div>


                        <div class="overview-item">

                            <span class="overview-icon">
                                💳
                            </span>

                            <div>

                                <strong>
                                    Finance Management
                                </strong>

                                <small>
                                    Fees, payments and
                                    financial records
                                </small>

                            </div>

                        </div>


                        <div class="overview-item">

                            <span class="overview-icon">
                                📊
                            </span>

                            <div>

                                <strong>
                                    Reports & Analytics
                                </strong>

                                <small>
                                    Institute performance
                                    and reports
                                </small>

                            </div>

                        </div>


                    </div>

                </div>


            </div>

        </main>

    </div>

</div>


<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>